package com.trading.TradingPlatform.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.TradingPlatform.Service.CoinService;
import com.trading.TradingPlatform.modal.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinService coinService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/coinList")
    public ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) throws Exception {
        List<Coin> res = coinService.getCoinList(page);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{coinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId, @RequestParam("days") int days) throws Exception {
        String res = coinService.getMarketChart(coinId, days);
        JsonNode node = objectMapper.readTree(res);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchCoin(@RequestParam("keyWord") String keyWord) throws Exception {
        String res = coinService.searchCoin(keyWord);
        JsonNode node = objectMapper.readTree(res);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/top50")
    public ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank() throws Exception {
        String res = coinService.getTop50CoinsByMarketCapRank();
        JsonNode node = objectMapper.readTree(res);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }
    @GetMapping("/trending")
    public ResponseEntity<JsonNode> getTreadingCoins() throws Exception {
        String res = coinService.getTreadingCoins();
        JsonNode node = objectMapper.readTree(res);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }

    @GetMapping("/detail/{coinId}")
    public ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception {
        String res = coinService.getCoinDetails(coinId);
        JsonNode node = objectMapper.readTree(res);
        return new ResponseEntity<>(node, HttpStatus.OK);
    }
}
