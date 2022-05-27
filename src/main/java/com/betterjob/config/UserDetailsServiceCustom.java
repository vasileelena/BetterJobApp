package com.betterjob.config;

import com.betterjob.model.User;
import com.betterjob.exception.UserNotFoundException;
import com.betterjob.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public UserDetailsServiceCustom(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UserNotFoundException("The user doesn't exist");
        }
        return new UserDetailsCustom(user);
    }
}
