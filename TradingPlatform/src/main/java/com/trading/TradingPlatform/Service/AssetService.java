package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.Asset;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user , Coin coin , double quantity);
    Asset getAssetById(Long assetId);
    Asset getAsserByUserIdAndId(Long userId , Long assetId) throws Exception;
    List<Asset> getUsersAssets(Long userId);
    Asset updateAsset(Long assetId , double quantity);
    Asset findAssetByCoinIdAndUserId(Long userId , String coinId);
    void deleteAsset(Long assetId);
}
