package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.WatchListRepository;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
       WatchList watchList =  watchListRepository.findByUserId(userId);
       if(watchList == null){
           throw new Exception("watchList not found ") ;
       }
       return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList = new WatchList();
        watchList.setUser(user);
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchList = watchListRepository.findById(id);
        if(watchList.isEmpty()){
            throw new Exception ("watchList not found");
        }
        return watchList.get();
    }

    @Override
    public Coin addItemToWatchList(Long userId, Coin coin) throws Exception {
        WatchList watchList = findUserWatchList(userId);
        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }
        watchList.getCoins().add(coin);
        watchListRepository.save(watchList);
        return coin;
    }
}
