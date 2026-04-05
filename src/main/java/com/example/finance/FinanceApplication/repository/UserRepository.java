package com.example.finance.FinanceApplication.repository;

import com.example.finance.FinanceApplication.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
