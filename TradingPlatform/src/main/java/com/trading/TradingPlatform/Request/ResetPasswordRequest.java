package com.trading.TradingPlatform.Request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otp ;
    private String password ;
}
