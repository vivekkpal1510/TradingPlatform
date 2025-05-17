package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.domain.OrderType;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.OrderItem;
import com.trading.TradingPlatform.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        return null;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return List.of();
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
