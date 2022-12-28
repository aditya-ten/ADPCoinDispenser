package com.adp.coindispenser.data;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class CoinDataStoreConfig {

    @Value("${config.coins.available}")
    private List<Double> coinsAvailable;

    @Value("${config.coins.default.count}")
    private Integer defaultValue;
}
