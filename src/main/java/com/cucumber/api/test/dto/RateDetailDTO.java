package com.cucumber.api.test.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateDetailDTO {
        @JSONField(name = "buy_amount")
        private BigDecimal buyAmount;
        @JSONField(name = "level")
        private String level;
        @JSONField(name = "rate")
        private BigDecimal rate;
        @JSONField(name = "sell_amount")
        private BigDecimal sellAmount;
}
