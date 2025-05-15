package com.trading.TradingPlatform.Service;


import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user , VerificationType verificationType);
    VerificationCode getVerificationById(Long id) throws Exception;
    VerificationCode getVerificationByUser(Long userId);
    void deleteVarificationCodebyId(VerificationCode verificationCode);

}
