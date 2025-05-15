package com.trading.TradingPlatform.modal;

import com.trading.TradingPlatform.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String otp ;

    private String email ;

    @OneToOne
    private User user ;

    private String mobile ;

    private VerificationType verificationType ;


}
