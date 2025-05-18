package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.WithdrawlRepository;
import com.trading.TradingPlatform.modal.User;
import com.trading.TradingPlatform.modal.Withdrawl;
import com.trading.TradingPlatform.modal.WithdrawlStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawlServiceImpl implements WithdrawlService {

    @Autowired
    private WithdrawlRepository withdrawlRepository;

    @Override
    public Withdrawl requestWithdrawl(Long ammount, User user) {
       Withdrawl withdrawl = new Withdrawl();
       withdrawl.setAmount(ammount);
       withdrawl.setUser(user);
       withdrawl.setWithdrawlStatus(WithdrawlStatus.PENDING);
       return withdrawlRepository.save(withdrawl);
    }

    @Override
    public Withdrawl proceedWithWithdrawl(Long withdrawlId, boolean isApproved) throws Exception {
        Optional<Withdrawl> withdrawl = withdrawlRepository.findById(withdrawlId);
        if(withdrawl.isEmpty()){
            throw new Exception("withdrawl not found");
        }
        Withdrawl wit = withdrawl.get();
        wit.setDate(LocalDateTime.now());
        if(isApproved){
            wit.setWithdrawlStatus(WithdrawlStatus.SUCCESSFUL);
        }
        else{
            wit.setWithdrawlStatus(WithdrawlStatus.FAILED);
        }
        return withdrawlRepository.save(wit);
    }

    @Override
    public List<Withdrawl> getUserWithdrawlHistory(User user) {
        return withdrawlRepository.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawl> getALLWithdrawlRequest() {
        return withdrawlRepository.findAll();
    }

}
