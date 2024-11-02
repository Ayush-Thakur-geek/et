package com.EaseTravels.et.services;

import com.EaseTravels.et.entity.User;
import com.EaseTravels.et.exceptions.ResourceNotFoundException;
import com.EaseTravels.et.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        log.info("Saving user: {}", user);
        String userId = UUID.randomUUID().toString();
        user.setId(userId);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such user found."));
        userRepo.delete(user);
    }

    @Override
    public boolean isUserExist(String id) {
        return false;
    }

    @Override
    public Optional<User> updateUser(User updatedUser) {

        User user = userRepo.findById(updatedUser.getId()).orElseThrow(() -> new RuntimeException("No such user found."));
        userRepo.save(user);

        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setPhoneNo(updatedUser.getPhoneNo());
        user.setProfilePic(updatedUser.getProfilePic());
        user.setEmailVerified(updatedUser.isEmailVerified());
        user.setPhoneVerified(updatedUser.isPhoneVerified());
        user.setProviderType(updatedUser.getProviderType());
        user.setProviderId(updatedUser.getProviderId());

        return Optional.ofNullable(userRepo.save(user));
    }

    @Override
    public String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
