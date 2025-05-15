package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Config.JwtProvider;
import com.trading.TradingPlatform.Repository.UserRepository;
import com.trading.TradingPlatform.domain.VerificationType;
import com.trading.TradingPlatform.modal.TwoFactAuth;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User Not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User Not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType , String sendTo , User user) {
        TwoFactAuth twoFactAuth = new TwoFactAuth();
        twoFactAuth.setEnabled(true);
        twoFactAuth.setSendTo(verificationType);
        user.setTwoFactAuth(twoFactAuth);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
