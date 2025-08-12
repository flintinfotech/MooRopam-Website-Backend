package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.dto.AuthRequest;
import com.flintinfotech.Moorapan.dto.AuthResponse;
import com.flintinfotech.Moorapan.entity.Role;
import com.flintinfotech.Moorapan.entity.User;
import com.flintinfotech.Moorapan.repository.UserRepository;
import com.flintinfotech.Moorapan.service.UserService;
import com.flintinfotech.Moorapan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/saveUserDetail")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use!");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRole(Role.ROLE_USER); // default role
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String username = authentication.getName();
            String token = jwtUtil.generateToken(username);

            User user = userService.getUserByUsername(username); // implement this method
            Integer userId = user.getUserId(); // or user.getUserId()

            return ResponseEntity.ok(new AuthResponse(token, userId));
        } catch (Exception e) {
            // Log the exception for debugging
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed: " + e.getMessage());
        }
    }
}
