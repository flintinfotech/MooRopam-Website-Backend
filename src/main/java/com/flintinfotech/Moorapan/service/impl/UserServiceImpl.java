package com.flintinfotech.Moorapan.service.impl;

import com.flintinfotech.Moorapan.entity.User;
import com.flintinfotech.Moorapan.repository.UserRepository;
import com.flintinfotech.Moorapan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

//    @Override
//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
//    }
}