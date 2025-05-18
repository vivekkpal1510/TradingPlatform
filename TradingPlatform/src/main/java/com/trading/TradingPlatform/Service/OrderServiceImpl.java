package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.OrderItemRepository;
import com.trading.TradingPlatform.Repository.OrderRepository;
import com.trading.TradingPlatform.domain.OrderStatus;
import com.trading.TradingPlatform.domain.OrderType;
import com.trading.TradingPlatform.modal.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;
    @Autowired
    private OrderService orderService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
        Order order = new Order();
        order.setOrderItem(orderItem);
        order.setUser(user);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                ()->new RuntimeException("Order Not Found")
        );
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin , double quantity ,
                                       double buyPrice,double sellPrice  ){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setSellPrice(sellPrice);
        orderItem.setBuyPrice(buyPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin , double quantity , User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("quantity should be < 0") ;
        }
        double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin , quantity , buyPrice , 0);
        Order order = createOrder(user , orderItem,OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order,user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);
        // edit here required
        Asset oldAsset = assetService.findAssetByCoinIdAndUserId(
                order.getUser().getId(),
                order.getOrderItem().getCoin().getId());
        if(oldAsset == null){
           assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }
        else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return savedOrder ;
    }

    @Transactional
    public Order sellAsset(Coin coin , double quantity , User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("quantity should be < 0") ;
        }
        double sellPrice = coin.getCurrentPrice();
        Asset assetTosell = assetService.findAssetByCoinIdAndUserId(
                user.getId(),
                coin.getId());
        double buyPrice = assetTosell.getBuyPrice();
        if(assetTosell != null) {
            OrderItem orderItem = createOrderItem(coin, quantity,
                                                  buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);
            if (assetTosell.getQuantity() >= quantity) {
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRepository.save(order);
                walletService.payOrderPayment(order, user);
                Asset updatedAsset  = assetService.updateAsset(assetTosell.getId(),-quantity);
                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;
            }
            throw new Exception("Insufficient Asset");
        }
        throw new Exception("Asset Not Found");
    }




    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY)){
                return buyAsset(coin, quantity, user);
        }
        else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin, quantity, user);
        }
        throw new Exception("Invalid Order Type");
    }
}
