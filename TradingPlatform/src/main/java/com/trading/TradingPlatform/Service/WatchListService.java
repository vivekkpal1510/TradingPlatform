package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.WatchList;

public interface WatchListService {

    WatchList  findUserWatchList(Long userId) throws Exception;
    WatchList  createWatchList(User user);
    WatchList findById(Long id) throws Exception;
    Coin addItemToWatchList(Long userId , Coin coin) throws Exception;
}
