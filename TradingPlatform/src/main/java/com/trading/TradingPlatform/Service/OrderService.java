package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.domain.OrderType;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.OrderItem;
import com.trading.TradingPlatform.modal.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user , OrderItem orderItem , OrderType orderType);

    Order getOrderById(Long orderId);
    List<Order> getAllOrdersOfUser(Long userId , OrderType orderType ,String assetSymbol);
    Order processOrder(Coin coin,double quantity , OrderType orderType , User user);


}
