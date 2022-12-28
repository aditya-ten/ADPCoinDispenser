package com.adp.coindispenser.controllers;

import com.adp.coindispenser.dto.CoinDispenseRequest;
import com.adp.coindispenser.dto.CoinDispenseResponse;
import com.adp.coindispenser.service.CoinDispenserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispense")
public class CoinDispenseAPIController {

    private CoinDispenserService coinDispenserService;

    @Autowired
    public CoinDispenseAPIController(CoinDispenserService coinDispenserService) {
        this.coinDispenserService = coinDispenserService;
    }

    @PostMapping
    public ResponseEntity<CoinDispenseResponse> dispense(@Valid @RequestBody CoinDispenseRequest coinDispenseRequest) {
        return ResponseEntity.ok(coinDispenserService.dispenseCoins(coinDispenseRequest));
    }

    @GetMapping
    public ResponseEntity<CoinDispenseResponse> coinsInStore() {
        return ResponseEntity.ok(coinDispenserService.coinsInStore());
    }


}
