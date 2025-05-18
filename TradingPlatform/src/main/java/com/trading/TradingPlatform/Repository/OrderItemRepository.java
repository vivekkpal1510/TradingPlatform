package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
