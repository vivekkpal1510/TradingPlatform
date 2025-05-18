package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.AssetRepository;
import com.trading.TradingPlatform.modal.Asset;
import com.trading.TradingPlatform.modal.Coin;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;


    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return null;
    }

    @Override
    public Asset getAsserByUserIdAndId(Long userId, Long assetId) throws Exception {
        return assetRepository.findById(assetId).orElseThrow(()->
                new Exception("asset not found"));
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) {
        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(quantity + oldAsset.getQuantity());
        return assetRepository.save(oldAsset);
    }

    @Override
    public Asset findAssetByCoinIdAndUserId(Long userId, String coinId) {

        return assetRepository.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
