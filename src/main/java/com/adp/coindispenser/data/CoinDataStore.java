package com.adp.coindispenser.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
@Setter
public class CoinDataStore {

    private CoinDataStoreConfig coinDataStoreConfig;
    private Map<Double, Integer> coinDataStore;
    @Autowired
    public CoinDataStore(CoinDataStoreConfig coinDataStoreConfig) {
        this.coinDataStore = new ConcurrentHashMap();
        var defaultCount = coinDataStoreConfig.getDefaultValue();
        coinDataStoreConfig.getCoinsAvailable().forEach(d -> this.coinDataStore.put(d, defaultCount));
    }

}
