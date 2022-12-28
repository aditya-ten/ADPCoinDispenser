package com.adp.coindispenser.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Data
public class CoinDispenseAPIError {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
