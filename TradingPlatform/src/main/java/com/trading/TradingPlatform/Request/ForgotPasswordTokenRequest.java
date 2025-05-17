package com.trading.TradingPlatform.Request;

import com.trading.TradingPlatform.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo ;
    private VerificationType verificationType ;
}
