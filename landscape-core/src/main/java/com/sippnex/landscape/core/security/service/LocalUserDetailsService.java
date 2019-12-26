package com.sippnex.landscape.core.security.service;

import com.sippnex.landscape.core.security.domain.CustomUserDetails;
import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Can not find username for " + username);
        }
        User user = optionalUser.get();

        return new CustomUserDetails(user);
    }

}
