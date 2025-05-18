package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Service.CoinService;
import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.Service.WatchListService;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList
            (@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }
    @GetMapping("/create")
    public ResponseEntity<WatchList> createWatchList
            (@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.createWatchList(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(watchList);
    }
    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchList(
            @PathVariable Long watchListId
    ) throws Exception {
        WatchList watchList = watchListService.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(
            @RequestHeader("Authorization") String jwt ,
            @PathVariable String coinId
    ) throws Exception {
        User user =  userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchList(user.getId(), coin);
        return ResponseEntity.ok(addedCoin);
    }


}
