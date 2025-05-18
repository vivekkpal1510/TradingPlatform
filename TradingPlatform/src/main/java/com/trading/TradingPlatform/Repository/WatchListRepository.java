package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList,Long> {
    WatchList findByUserId(Long id);
}
