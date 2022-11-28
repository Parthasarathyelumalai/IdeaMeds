package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmailId(emailId);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserDetailsServiceImpl(user.get());
    }
}
