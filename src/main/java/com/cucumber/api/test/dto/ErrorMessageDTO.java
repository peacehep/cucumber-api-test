package com.cucumber.api.test.dto;

import lombok.Data;

@Data
public class ErrorMessageDTO {
    private String code;
    private String message;
    private String source;
}
