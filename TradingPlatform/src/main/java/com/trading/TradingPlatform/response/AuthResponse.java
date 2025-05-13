package com.trading.TradingPlatform.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt ;
    private boolean status ;
    private String message ;
    private String session;
    private boolean isTwoFactorAuthEnabled ;
}
