package com.trading.TradingPlatform.Service;

import com.trading.TradingPlatform.Repository.UserRepository;
import com.trading.TradingPlatform.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        // edit might be required as Authority roles are not .. we did not load the roles
        List<GrantedAuthority> authorityList = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorityList);
    }
}
