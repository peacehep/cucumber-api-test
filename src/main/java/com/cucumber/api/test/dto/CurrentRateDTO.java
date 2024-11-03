package com.cucumber.api.test.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CurrentRateDTO {

        @JSONField(name = "buy_currency")
        private String buyCurrency;
        @JSONField(name = "conversion_date")
        private Date conversionDate;
        @JSONField(name = "created_at")
        private Date createdAt;
        @JSONField(name = "currency_pair")
        private String currencyPair;
        @JSONField(name = "dealt_currency")
        private String dealtCurrency;
        @JSONField(name = "rate")
        private BigDecimal rate;
        @JSONField(name = "rate_details")
        private List<RateDetailDTO> rateDetails;
        @JSONField(name = "sell_currency")
        private String sellCurrency;
}
