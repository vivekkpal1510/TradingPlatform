package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    public VerificationCode findByUserId(Long userId);
}
