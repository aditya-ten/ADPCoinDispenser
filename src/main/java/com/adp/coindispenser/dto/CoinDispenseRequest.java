package com.adp.coindispenser.dto;

import com.adp.coindispenser.entities.ChangeType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Data
public class CoinDispenseRequest {
    @NotNull
    private Integer bill;
    private Optional<ChangeType> changeType;
}
