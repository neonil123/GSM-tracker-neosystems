package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public User addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }
    @PostMapping(value = "/login")
    public User LogInUser(@RequestBody UserDto userDto) {
        return userService.logInUser(userDto);
    }


}
