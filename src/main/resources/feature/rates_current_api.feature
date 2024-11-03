Feature: Rates Api Testing
  Scenario Outline: positive testcases for rates api
    Given get access
    When get rates for selling "<sell_currency>" to buy "<buy_amount>" "<buy_currency>"
    Then should get the response code 200
    And verify the response with JSON schema "ratesSchema.json"
    And verify main fields in response "<sell_currency>" "<buy_amount>" "<buy_currency>"

    Examples:
      | sell_currency | buy_currency | buy_amount |
      | USD           | AUD          | 100        |
      | EUR           | HKD          | 100        |
      | SGD           | CNY          | 100        |

  Scenario Outline: negative testcases for rates api
    Given get access
    When get rates for selling "<sell_currency>" to buy "<buy_amount>" "<buy_currency>"
    Then verify the error message in response "<code>" "<message>" "<source>"

    Examples:
      | sell_currency | buy_currency | buy_amount | code             | message                                         | source        |
      |               | CNY          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | sell_currency |
      | SGD           |              | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | buy_currency  |
#      | SGD           | CNY          |            | invalid_argument | buy_amount is missing                           | buy_amount    |
      | SGDA          | CNY          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | sell_currency |
      | SGD           | CNYA         | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | buy_currency  |
      | SGD           | CNY          | A100       | invalid_argument | buy_amount is invalid                           | buy_amount    |
      | QQQ           | CNY          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | sell_currency |
      | SGD           | QQQ          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | buy_currency  |


    Scenario Outline: security testcases for rates api
    Given get access
    When get rates for selling "<sell_currency>" to buy "<buy_amount>" "<buy_currency>"
    Then verify the error message in response "<code>" "<message>" "<source>"

    Examples:
      | sell_currency | buy_currency | buy_amount | code             | message                                         | source        |
      | SGD           | CNY or 1=1   | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | buy_currency  |
      | SGD --        | CNY          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | sell_currency |
      | SGD           | ___          | 100        | invalid_argument | The currency code is not 3-letter ISO-4217 code | buy_currency  |