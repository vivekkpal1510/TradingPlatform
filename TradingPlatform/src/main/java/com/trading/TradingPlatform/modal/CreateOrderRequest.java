package com.trading.TradingPlatform.modal;

import com.trading.TradingPlatform.domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coinId ;
    private double quantity ;
    private OrderType orderType ;
}
