package com.trading.TradingPlatform.Repository;

import com.trading.TradingPlatform.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
