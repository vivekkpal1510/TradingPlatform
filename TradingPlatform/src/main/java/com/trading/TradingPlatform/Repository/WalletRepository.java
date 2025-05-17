package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    public Wallet findByUserId(Long userId);

}
