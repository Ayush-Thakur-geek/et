package com.EaseTravels.et.services;

import com.EaseTravels.et.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService {
    public void saveUser(User user);
    public User getUserByEmail(String email);
    public User getUserById(String id);
    public void deleteUser(String id);
    public boolean isUserExist(String id);
    public Optional<User> updateUser(User user);
    public String getClientIp(HttpServletRequest request);
}
