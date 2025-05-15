package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.TwoFactorOTP;
import com.trading.TradingPlatform.modal.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user , String otp , String jwt);

    TwoFactorOTP findById(String id);

    TwoFactorOTP findByUserId(Long userId);

    boolean verifyOtp(TwoFactorOTP twoFactorOTP, String otp);

    void delete(TwoFactorOTP twoFactorOTP);

}
