package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.VerificationCodeRepository;
import com.trading.TradingPlatform.Utils.OtpUtils;
import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(OtpUtils.generateOtp());
        verificationCode.setVerificationType(verificationType);
        verificationCode.setUser(user);
        return verificationCodeRepository.save(verificationCode);
    }



    @Override
    public VerificationCode getVerificationById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("Verification code not found");
    }

    @Override
    public VerificationCode getVerificationByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVarificationCodebyId(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
