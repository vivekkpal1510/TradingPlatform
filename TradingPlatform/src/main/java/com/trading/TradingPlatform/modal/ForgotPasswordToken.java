package com.trading.TradingPlatform.modal;

import com.trading.TradingPlatform.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String id ;

    @OneToOne
    User user;
    String otp ;
    VerificationType verificationType;
    String sendTo;  // this holds acctual mobile number or email
}
