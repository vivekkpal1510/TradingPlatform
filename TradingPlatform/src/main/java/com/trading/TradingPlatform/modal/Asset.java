package com.trading.TradingPlatform.modal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double buyPrice;
    private double quantity;
    @ManyToOne
    private Coin coin;

    @ManyToOne
    private User user;
}
