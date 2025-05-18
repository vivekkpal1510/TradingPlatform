package com.trading.TradingPlatform.Controller;

import com.trading.TradingPlatform.Service.AssetService;
import com.trading.TradingPlatform.Service.UserService;
import com.trading.TradingPlatform.modal.Asset;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @PathVariable String coinId ,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
     User user  = userService.findUserProfileByJwt(jwt);
     Asset aasset = assetService.findAssetByCoinIdAndUserId(user.getId() , coinId);
    return ResponseEntity.ok().body(aasset);
    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetForUser(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user  = userService.findUserProfileByJwt(jwt);
        List<Asset> asset = assetService.getUsersAssets(user.getId());
        return ResponseEntity.ok().body(asset);
    }
}
