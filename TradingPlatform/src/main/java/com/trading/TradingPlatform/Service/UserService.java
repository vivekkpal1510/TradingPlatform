package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.User;

public interface UserService  {
    public User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long id) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType , String sendTo , User user);


    User updatePassword(User user , String newPassword);

}

