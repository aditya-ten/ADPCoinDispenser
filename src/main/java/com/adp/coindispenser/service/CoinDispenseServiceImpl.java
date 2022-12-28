package com.adp.coindispenser.service;

import com.adp.coindispenser.data.CoinDataStore;
import com.adp.coindispenser.dto.CoinDispenseRequest;
import com.adp.coindispenser.dto.CoinDispenseResponse;
import com.adp.coindispenser.entities.ChangeType;
import com.adp.coindispenser.exceptions.OutOfCoinsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CoinDispenseServiceImpl implements CoinDispenserService {

    private static String SUCCESS_MESSAGE = "Here is your change!";
    private CoinDataStore coinDataStore;

    @Autowired
    public CoinDispenseServiceImpl(CoinDataStore coinDataStore) {
        this.coinDataStore = coinDataStore;
    }

    @Override
    public CoinDispenseResponse dispenseCoins(CoinDispenseRequest request) {
        return new CoinDispenseResponse(
                calculateChange(request.getBill(), request.getChangeType().orElse(ChangeType.LEAST)),
                SUCCESS_MESSAGE);
    }

    @Override
    public CoinDispenseResponse coinsInStore() {
        return new CoinDispenseResponse(
                coinDataStore.getCoinDataStore(),
                "Coins Left in Store!");
    }

    protected Map<Double, Integer> calculateChange(Integer billPassed, ChangeType changeType) {
        Map<Double, Integer> changeGenerated = new LinkedHashMap<>();
        var bill = Double.valueOf(billPassed);
        var currCoin = 0.0;
        while (true) {
            if (bill != 0) {
                Optional<Map.Entry<Double, Integer>> entry = getNextCoin(changeType, currCoin);
                if (entry.isPresent()) {
                    Map.Entry<Double, Integer> coin = entry.get();
                    int count = (int) (bill / coin.getKey());
                    if (count <= coin.getValue()) {
                        changeGenerated.merge(coin.getKey(), count, (k, v) -> v + count);
                        coin.setValue(coin.getValue() - count);
                        bill -= (count * coin.getKey());
                    } else {
                        bill -= (coin.getValue() * coin.getKey());
                        changeGenerated.merge(coin.getKey(), coin.getValue(), (k, v) -> v + coin.getValue());
                        coin.setValue(0);
                    }
                } else {
                    throw new OutOfCoinsException("Oops! We are out of coins to complete this transaction!");
                }
            }
            if (bill == 0) {
                break;
            }
        }

        return changeGenerated;
    }

    private Optional<Map.Entry<Double, Integer>> getNextCoin(ChangeType changeType, Double currCoin) {
        if (changeType.equals(ChangeType.MOST)) {
            return coinDataStore.getCoinDataStore().entrySet().stream().sorted(Map.Entry.comparingByKey()).filter(val -> val.getValue() > 0 && val.getKey() >= currCoin).findFirst();
        } else {
            return coinDataStore.getCoinDataStore().entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByKey())).filter(val -> val.getValue() > 0 && val.getKey() >= currCoin).findFirst();
        }
    }
}
