package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.Withdrawl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawlRepository extends JpaRepository<Withdrawl , Long> {
        List<Withdrawl> findByUserId(Long userId);

}
