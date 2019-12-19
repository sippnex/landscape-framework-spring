package com.sippnex.landscape.core.security.service;

import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

}
