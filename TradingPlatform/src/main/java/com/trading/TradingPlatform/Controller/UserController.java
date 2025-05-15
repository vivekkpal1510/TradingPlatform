package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Service.EmailService;
import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.Service.VerificationCodeService;
import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/api/user/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/user/verify/{verificationType}/send-otp")
    public ResponseEntity<String> sendVarificationMail(
            @RequestHeader("Authorization") String jwt ,
            @PathVariable VerificationType verificationType)
            throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationByUser(user.getId());
        if(verificationCode == null){
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVarificationMail(user.getEmail(),verificationCode.getOtp());
        }

        return new ResponseEntity<String>("Otp send", HttpStatus.OK);
    }

    @PatchMapping("/api/user/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @RequestHeader("Authorization") String jwt,
            String otp
            ) throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationByUser(user.getId());
        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updateduser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVarificationCodebyId(verificationCode);
            return new ResponseEntity<User>(updateduser, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }

}

