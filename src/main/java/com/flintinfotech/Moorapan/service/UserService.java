package com.flintinfotech.Moorapan.service;

import com.flintinfotech.Moorapan.entity.User;

public interface UserService {
    User getUserById(Integer id);

    User getUserByUsername(String username);
}