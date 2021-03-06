package com.example.Mini.Online.Market.controller;

import com.example.Mini.Online.Market.domain.Address;
import com.example.Mini.Online.Market.domain.User;
import com.example.Mini.Online.Market.domain.UserStatus;
import com.example.Mini.Online.Market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable("id") long id){
        return userService.getById(id);
    }
    @GetMapping("/sellers/pending")
    public List<User> getSellersByStatus(){
        return userService.getByStatus();
    }
    @GetMapping("/sellers")
    public List<User> getAllSellers(){
        return userService.getSellers();
    }
    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }
    @PutMapping("/{id}")
    public void approveUser(@PathVariable("id") long id){
        if((userService.getById(id)).isPresent()) {
            User user = userService.getById(id).get();
            user.setStatus(UserStatus.APPROVED);
            userService.save(user);
        }
    }

    @GetMapping("/{id}/address")
    public List<Address> findAddress(@PathVariable("id") long id, Authentication auth){
        return userService.getUserAddress(auth.getName());
    }
}
