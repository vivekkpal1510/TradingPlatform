package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Wallet;

import java.math.BigDecimal;

public interface WalletService {
    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransaction(User sender , Wallet recipient, Long amount) throws Exception;
    Wallet payOrderPayment(Order order , User user) throws Exception;
}
