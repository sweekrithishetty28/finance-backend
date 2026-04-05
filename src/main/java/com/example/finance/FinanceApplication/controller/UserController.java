package com.example.finance.FinanceApplication.controller;

import com.example.finance.FinanceApplication.model.Status;
import com.example.finance.FinanceApplication.model.User;
import com.example.finance.FinanceApplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.finance.FinanceApplication.model.Role;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        User newUser=userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> users=userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        User user=userService.getUserById(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with id: "+id);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){

        User userUpdated=userService.updateUser(id,user);
        if(userUpdated==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with id: "+id );
        }
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        User user=userService.getUserById(id);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with id: "+id);
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted Successfully");
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestBody Role role){
        User user=userService.changeRole(id,role);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found with id: "+id);
        }
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody Status status){
       User user=userService.changeStatus(id,status);
       if(user==null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found with id: "+id);
       }
       return ResponseEntity.ok(user);
    }


}
