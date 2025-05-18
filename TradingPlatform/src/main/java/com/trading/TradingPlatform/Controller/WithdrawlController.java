package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.Service.WalletService;
import com.trading.TradingPlatform.Service.WithdrawlService;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Wallet;
import com.trading.TradingPlatform.modal.Withdrawl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawl")
public class WithdrawlController {
    @Autowired
    private WithdrawlService withdrawlService;

    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    @GetMapping("/api/withdrawl/{ammount}")
    public ResponseEntity<Withdrawl> withdrawlRequest(
            @PathVariable Long ammount ,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);
        Withdrawl withdrawl = withdrawlService.requestWithdrawl(ammount,user);
        walletService.addBalance(userWallet , -withdrawl.getAmount());

        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }

    @PatchMapping("/api/admin/withdrawl/{id}/proceed/{isApproved}")
    public ResponseEntity<Withdrawl> proceedPayment(
            @PathVariable Long id ,
            @PathVariable boolean isApproved ,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawl withdrawl = withdrawlService.proceedWithWithdrawl(id,isApproved);
        Wallet userWallet = walletService.getUserWallet(user);
        if(!isApproved){
            walletService.addBalance(userWallet , withdrawl.getAmount());
        }
        return new ResponseEntity<>(withdrawl, HttpStatus.OK);
    }

    @GetMapping("/api/withdrawl")
    public ResponseEntity<List<Withdrawl>> getWithdrawlHistory(
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawl> withdrawl = withdrawlService.getUserWithdrawlHistory(user);
        return new ResponseEntity<>(withdrawl,HttpStatus.OK);
    }

    @GetMapping("/api/withdrawl")
    public ResponseEntity<List<Withdrawl>> getAllWithdrawlRequest(
            @RequestHeader("Authorization") String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawl> res = withdrawlService.getALLWithdrawlRequest();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


}
