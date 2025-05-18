package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Withdrawl;

import java.util.List;

public interface WithdrawlService {

    Withdrawl requestWithdrawl(Long ammount , User user);
    Withdrawl proceedWithWithdrawl(Long withdrawlId , boolean isApproved) throws Exception;
    List<Withdrawl> getUserWithdrawlHistory(User user);
    List<Withdrawl> getALLWithdrawlRequest();

}
