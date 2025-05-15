package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Config.JwtProvider;
import com.trading.TradingPlatform.Repository.TwoFactorOtpRepository;
import com.trading.TradingPlatform.Repository.UserRepository;

import com.trading.TradingPlatform.Service.CustomerDetailService;
import com.trading.TradingPlatform.Service.EmailService;
import com.trading.TradingPlatform.Service.TwoFactorOtpService;
import com.trading.TradingPlatform.Utils.OtpUtils;
import com.trading.TradingPlatform.modal.TwoFactorOTP;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.response.AuthResponse;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullname(user.getFullname());
        User savedUser = userRepository.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception{
        String username = user.getEmail();
        String password = user.getPassword();
        Authentication auth =authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        User authuser = userRepository.findByEmail(username);
        if(user.getTwoFactAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor auth is Enabled");
            res.setStatus(true);
            String otp = OtpUtils.generateOtp();
            TwoFactorOTP oldTwoFactorOtp =  twoFactorOtpService.findByUserId(authuser.getId());
            if(oldTwoFactorOtp != null){
                twoFactorOtpService.delete(oldTwoFactorOtp);
            }
            TwoFactorOTP newtwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authuser,otp,jwt);

            emailService.sendVarificationMail(authuser.getEmail(),otp);
            res.setSession(newtwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Login successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerDetailService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("User not found");
        }
        if(!userDetails.getPassword().equals(password)){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignOtp(
                @PathVariable String otp ,
                @RequestParam String id){
        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
        if(twoFactorOtpService.verifyOtp(twoFactorOTP,otp) ){
            AuthResponse response = new AuthResponse();
            response.setJwt(twoFactorOTP.getJwt());
            response.setMessage("OTP Verified");
            response.setTwoFactorAuthEnabled(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        throw new RuntimeException("Invalid OTP");
    }


}
