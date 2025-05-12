package com.trading.TradingPlatform.modal;

import com.trading.TradingPlatform.domain.VerificationType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class TwoFactAuth {
    private boolean enabled = false;
    private VerificationType sendTo ;

}
