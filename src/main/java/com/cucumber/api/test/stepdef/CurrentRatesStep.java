package com.cucumber.api.test.stepdef;

import com.alibaba.fastjson2.JSONObject;
import com.cucumber.api.test.dto.CurrentRateDTO;
import com.cucumber.api.test.dto.ErrorMessageDTO;
import com.cucumber.api.test.dto.RateDetailDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cucumber.api.test.util.GlobalSetting.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class CurrentRatesStep {


    private Response response;
    private ValidatableResponse json;
    private String token;

    @Given("get access")
    public void getAccess() {
        token = given()
                .header("Content-Type","application/json")
                .header("x-client-id", X_CLIENT_ID)
                .header("x-api-key",X_API_KEY)
                .when()
                .post(BASE_URL+POST_ACCESS_API)
                .then()
                .extract().path("token");
        log.info("Get access successfully");

    }

    @When("get rates for selling {string} to buy {string} {string}")
    public void getCurrentRates(String sellCurrency, String buyAmount, String buyCurrency) {
        log.info("get rates for selling {} to buy {} {}",sellCurrency,buyAmount,buyCurrency);
        response = given()
                .header("Authorization","Bearer "+token)
                .param("buy_currency", buyCurrency)
                .param("buy_amount",buyAmount)
                .param("sell_currency",sellCurrency)
                .when()
                .get(BASE_URL+GET_CURRENT_RATES_API);
        log.info(response.getBody().asString());

    }

    @Then("should get the response code 200")
    public void verifyResponseCode() {
        response.then().assertThat().statusCode(200);
        log.info("Validated response code successfully");

    }


    @Then("verify main fields in response {string} {string} {string}")
    public void verifyMainFieldsInResponse(String sellCurrency ,String buyAmount ,String buyCurrency) {
        String responseBody = response.getBody().asString();
        CurrentRateDTO currentRateDTO = JSONObject.parseObject(responseBody, CurrentRateDTO.class);

        List<RateDetailDTO> rateDetails =currentRateDTO.getRateDetails();
        assertThat(rateDetails.size()).withFailMessage("'rate_details' is missing in response body").isEqualTo(1);

        String currencyPair = currentRateDTO.getCurrencyPair();
        List<String> currencyPairList = new ArrayList<>(Arrays.asList(sellCurrency+buyCurrency,buyCurrency+sellCurrency));
        assertThat(currencyPairList).withFailMessage("currencyPair is not correct in response body "+currencyPair).contains(currencyPair);
        assertThat(sellCurrency).withFailMessage("sellCurrency is not correct  in response body "+sellCurrency).isEqualTo(currentRateDTO.getSellCurrency());
        assertThat(buyCurrency).withFailMessage("buyCurrency is not correct  in response body "+buyCurrency).isEqualTo(currentRateDTO.getBuyCurrency());

        RateDetailDTO rateDetail = rateDetails.get(0);
        BigDecimal rate  = rateDetail.getRate();
        BigDecimal sellAmount  = rateDetail.getSellAmount();
        assertThat(new BigDecimal(buyAmount)).withFailMessage("buyAmount is not correct "+buyAmount).isEqualByComparingTo(rateDetail.getBuyAmount());

        if(currencyPair.substring(0,3).equals(buyCurrency)){
            assertThat(sellAmount).withFailMessage("'sellAmount' is not correct")
                    .isEqualTo(rate.multiply(new BigDecimal(buyAmount)).setScale(2, RoundingMode.HALF_UP));
        }else{
            assertThat(sellAmount).withFailMessage("'sellAmount' is not correct")
                    .isEqualTo(new BigDecimal(buyAmount).divide(rate,2, RoundingMode.HALF_UP));
        }


    }

    @Then("verify the response with JSON schema {string}")
    public void verifyResponseWithJsonSchema(String jsonSchemaName) {
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+jsonSchemaName));
        log.info("Validated schema from {} successfully ",jsonSchemaName);
    }

    @Then("verify the error message in response {string} {string} {string}")
    public void verifyErrorMessageInResponse(String code,String message,String source) {
        String responseBody = response.getBody().asString();
        ErrorMessageDTO errorMessageDTO = JSONObject.parseObject(responseBody, ErrorMessageDTO.class);
        assertThat(errorMessageDTO.getCode()).withFailMessage("code is not correct "+code).isEqualTo(code);
        assertThat(errorMessageDTO.getMessage()).withFailMessage("message is not correct "+message).isEqualTo(message);
        assertThat(errorMessageDTO.getSource()).withFailMessage("source is not correct "+source).isEqualTo(source);
    }
}
