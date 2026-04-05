package com.example.finance.FinanceApplication.service;

import com.example.finance.FinanceApplication.model.Status;
import com.example.finance.FinanceApplication.model.User;
import com.example.finance.FinanceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.finance.FinanceApplication.model.Role;
import com.example.finance.FinanceApplication.model.Status;


import java.util.List;


@Service
public class UserService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
       this.userRepository=userRepository;
       this.passwordEncoder=passwordEncoder;
   }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public  User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id,User user){

        User existing=userRepository.findById(id).orElse(null);
        if(existing==null){
            return null;
        }
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        existing.setRole(user.getRole());
        existing.setStatus(user.getStatus());
        return userRepository.save(existing);

    }

    public String deleteUserById(Long id){
        userRepository.deleteById(id);
        return "User"+id+"has been deleted";

    }
    public User changeRole(Long id, Role role){
        User user= userRepository.findById(id).orElse(null);
        user.setRole(role);
        return userRepository.save(user);
    }
    public User changeStatus(Long id, Status status){
        User user=userRepository.findById(id).orElse(null);
        user.setStatus(status);
        return userRepository.save(user);
    }

}
