package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.ForgotPasswordToken;
import com.trading.TradingPlatform.modal.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user ,
                                    String id , String otp,
                                    VerificationType verificationType,
                                    String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);
}
