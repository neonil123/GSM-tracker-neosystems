package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.dto.UserDto;
import com.example.demo.mapper.Mappers;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(UserDto userDto) {
        User user = Mappers.userDtoToUser(userDto);
        return userRepository.save(user);
    }
}
