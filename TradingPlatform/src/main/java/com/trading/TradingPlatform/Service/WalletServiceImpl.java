package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.WalletRepository;
import com.trading.TradingPlatform.domain.OrderType;
import com.trading.TradingPlatform.modal.Order;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        balance = balance.add(BigDecimal.valueOf(money));
        wallet.setBalance(balance);
        walletRepository.save(wallet);
        return wallet;
    }

    @Override
    public Wallet findWalletById(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        }
        throw new Exception("Wallet Not found");
    }

    @Override
    public Wallet walletToWalletTransaction(User sender, Wallet recipient, Long amount) throws Exception {
        Wallet senderWallet = walletRepository.findByUserId(sender.getId());
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0 ){
            throw new Exception("Insufficient Balance");
        }
        BigDecimal newBal = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(newBal);
        walletRepository.save(senderWallet);
        BigDecimal recvBal = recipient.getBalance().add(BigDecimal.valueOf(amount));
        recipient.setBalance(recvBal);
        walletRepository.save(recipient);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(BigDecimal.ZERO) < 0){
                throw new  Exception("Insufficient Balance");
            }
            wallet.setBalance(newBalance);
        }else{
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet;
    }
}
