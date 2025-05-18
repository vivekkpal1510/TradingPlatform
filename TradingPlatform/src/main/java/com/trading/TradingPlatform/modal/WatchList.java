package com.trading.TradingPlatform.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @OneToOne
    private User user ;
    @ManyToMany
    private List<Coin> coins ;
}
