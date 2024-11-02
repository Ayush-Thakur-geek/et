package com.EaseTravels.et.repositories;

import com.EaseTravels.et.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
