package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken , String> {
    ForgotPasswordToken findByUserId(Long id);
}
