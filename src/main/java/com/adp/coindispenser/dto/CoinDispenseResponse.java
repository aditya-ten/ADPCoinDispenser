package com.adp.coindispenser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinDispenseResponse {
    private Map<Double, Integer> coinsToDispense;
    private String message;
}
