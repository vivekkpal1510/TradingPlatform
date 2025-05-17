package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Request.ForgotPasswordTokenRequest;
import com.trading.TradingPlatform.Request.ResetPasswordRequest;
import com.trading.TradingPlatform.Service.*;
import com.trading.TradingPlatform.Utils.OtpUtils;
import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.ForgotPasswordToken;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.VerificationCode;
import com.trading.TradingPlatform.response.ApiResponse;
import com.trading.TradingPlatform.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

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


    @PostMapping("auth/user/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgetPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req
            ) throws Exception {
        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if(token == null){
            token = forgotPasswordService.createToken(user,id, otp, req.getVerificationType(), req.getSendTo());
        }
        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVarificationMail(user.getEmail(),token.getOtp());
        }
        AuthResponse res = new AuthResponse();
        res.setSession(token.getId());
        res.setMessage("Password reset OTP sent");

        return new ResponseEntity <>(res, HttpStatus.OK);
    }

    @PatchMapping("auth/user/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id ,
            @RequestBody ResetPasswordRequest req ,
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Password reset OTP sent");
            return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
        }
        throw new Exception("Invalid OTP");
    }

}