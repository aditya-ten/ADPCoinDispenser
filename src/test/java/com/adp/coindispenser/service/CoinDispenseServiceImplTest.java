package com.adp.coindispenser.service;

import com.adp.coindispenser.data.CoinDataStore;
import com.adp.coindispenser.data.CoinDataStoreConfig;
import com.adp.coindispenser.dto.CoinDispenseRequest;
import com.adp.coindispenser.dto.CoinDispenseResponse;
import com.adp.coindispenser.entities.ChangeType;
import com.adp.coindispenser.exceptions.OutOfCoinsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CoinDispenseServiceImplTest {

    private CoinDispenseServiceImpl coinDispenseService;

    @BeforeEach
    public void init() {
        CoinDataStoreConfig coinDataStoreConfig = new CoinDataStoreConfig();
        coinDataStoreConfig.setCoinsAvailable(List.of(0.05, 0.10, 0.01, 0.25));
        coinDataStoreConfig.setDefaultValue(100);
        CoinDataStore coinDataStore = new CoinDataStore(coinDataStoreConfig);
        coinDispenseService = new CoinDispenseServiceImpl(coinDataStore);
    }

    @Test
    public void whenBillPassedIs1WithMostCoinsThenValidCoinsAreReturned() {
        CoinDispenseRequest coinDispenseRequest = new CoinDispenseRequest();
        coinDispenseRequest.setBill(1);
        coinDispenseRequest.setChangeType(Optional.ofNullable(ChangeType.MOST));
        CoinDispenseResponse coinDispenseResponse = coinDispenseService.dispenseCoins(coinDispenseRequest);

        Map<Double, Integer> expectedResult = new HashMap<>();
        expectedResult.put(0.01, 100);
        Assertions.assertEquals(expectedResult, coinDispenseResponse.getCoinsToDispense(), "100 coins of 1 cents were expected but was not returned!");
    }

    @Test
    public void whenBillPassedIs2WithLeastCoinsThenValidCoinsAreReturned() {
        CoinDispenseRequest coinDispenseRequest = new CoinDispenseRequest();
        coinDispenseRequest.setBill(2);
        coinDispenseRequest.setChangeType(Optional.ofNullable(ChangeType.LEAST));
        CoinDispenseResponse coinDispenseResponse = coinDispenseService.dispenseCoins(coinDispenseRequest);

        Map<Double, Integer> expectedResult = new HashMap<>();
        expectedResult.put(0.25, 8);
        Assertions.assertEquals(expectedResult, coinDispenseResponse.getCoinsToDispense(), "8 coins of 25 cents were expected but was not returned!");
    }

    @Test
    public void whenBillPassedIs100WithLeastCoinsThenOutOfCoinsExceptionIsThrown() {
        CoinDispenseRequest coinDispenseRequest = new CoinDispenseRequest();
        coinDispenseRequest.setBill(100);
        coinDispenseRequest.setChangeType(Optional.ofNullable(ChangeType.LEAST));
        Assertions.assertThrows(OutOfCoinsException.class, () -> {
            coinDispenseService.dispenseCoins(coinDispenseRequest);
        });
    }

}