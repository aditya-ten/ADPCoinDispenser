package com.adp.coindispenser.service;

import com.adp.coindispenser.dto.CoinDispenseRequest;
import com.adp.coindispenser.dto.CoinDispenseResponse;

public interface CoinDispenserService {

    CoinDispenseResponse dispenseCoins(CoinDispenseRequest request);

    CoinDispenseResponse coinsInStore();

}
