package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Service.CoinService;
import com.trading.TradingPlatform.Service.OrderService;
import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.domain.OrderType;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.CreateOrderRequest;
import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment (
            @RequestHeader("Authorization") String jwt ,
            @RequestBody CreateOrderRequest request
    )throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(request.getCoinId());
        Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(),user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt ,
            @PathVariable Long orderId) throws Exception {
        if(jwt == null){
            throw new Exception("Invalid JWT");
        }
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId() == user.getId()){
            return new ResponseEntity<>(order, HttpStatus.OK);
        }else{
            throw new Exception("ACCESS DENIED");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrdersForUser(
            @RequestHeader("Authorization") String jwt ,
            @RequestParam( required = false) OrderType orderType,
            @RequestParam( required = false) String assetSymbol
    )throws Exception{
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        List<Order> userOrder = orderService.getAllOrdersOfUser(userId,orderType,assetSymbol);
            return new ResponseEntity<>(userOrder, HttpStatus.OK);
    }


}
