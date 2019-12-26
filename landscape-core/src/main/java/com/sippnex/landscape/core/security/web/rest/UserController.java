package com.sippnex.landscape.core.security.web.rest;

import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.repository.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value="", method= RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userRepository.findById(id).orElse(null);
    }

}
