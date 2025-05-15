package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.TwoFactorOtpRepository;
import com.trading.TradingPlatform.modal.TwoFactorOTP;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOTPServiceImp implements TwoFactorOtpService {
    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);
        return twoFactorOtpRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> otp = twoFactorOtpRepository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public TwoFactorOTP findByUserId(Long userId) {
        return twoFactorOtpRepository.findByUserId(userId);
    }

    @Override
    public boolean verifyOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void delete(TwoFactorOTP twoFactorOTP) {
        twoFactorOtpRepository.delete(twoFactorOTP);
    }
}
