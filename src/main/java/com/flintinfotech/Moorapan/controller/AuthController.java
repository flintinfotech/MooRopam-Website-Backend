package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.entity.Role;
import com.flintinfotech.Moorapan.entity.User;
import com.flintinfotech.Moorapan.repository.UserRepository;
import com.flintinfotech.Moorapan.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        Response response = new Response(user.getUserId(), "Success", "User registered successfully");

        return ResponseEntity.ok(response);

    }
}