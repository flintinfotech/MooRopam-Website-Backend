package com.flintinfotech.Moorapan.controller;

import com.flintinfotech.Moorapan.entity.User;
import com.flintinfotech.Moorapan.service.UserService;
import com.flintinfotech.Moorapan.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") Integer id) {
        try {
            User user = userService.getUserById(id);
            Response response = new Response(user, "Success", "User fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response(null, "ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}