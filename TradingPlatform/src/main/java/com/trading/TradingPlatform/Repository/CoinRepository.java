package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin,String > {

}
