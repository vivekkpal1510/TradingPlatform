package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Request.WalletTransaction;
import com.trading.TradingPlatform.Service.OrderService;
import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.Service.WalletService;
import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet, HttpStatus.OK);

    }
    @GetMapping("/api/wallet/{walletId}/tranfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt ,
            @PathVariable Long walletId ,
            @RequestBody WalletTransaction request
            ) throws Exception {
        User sender = userService.findUserProfileByJwt(jwt);
        Wallet recieverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransaction(sender,recieverWallet, request.getAmmount());
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);

    }

    @GetMapping("api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt ,
            @PathVariable Long orderId)
    throws Exception {
        User sender = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order,sender);
        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

}
