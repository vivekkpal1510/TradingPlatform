package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page) throws Exception;
    String getMarketChart(String coinId , int days) throws Exception;
    String getCoinDetails(String coinId) throws Exception;
    Coin findById(String CoinId) throws Exception;
    String searchCoin(String Keyword) throws Exception;
    String getTop50CoinsByMarketCapRank() throws Exception;
    String getTreadingCoins() throws Exception;
}
