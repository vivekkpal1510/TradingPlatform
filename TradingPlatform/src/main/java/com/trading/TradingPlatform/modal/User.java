package com.trading.TradingPlatform.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.TradingPlatform.domain.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;
    private String fullname;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private UserRole role = UserRole.CUSTOMER;
    @Embedded
    private TwoFactAuth twoFactAuth = new TwoFactAuth();
}
