package com.example.Blogging.Application.Security;

import com.example.Blogging.Application.Entities.User;
import com.example.Blogging.Application.Exceptions.UserNmNotFoundException;
import com.example.Blogging.Application.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    whenever spring security user this class then it use loadUserByUsername for load the user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Loading user from database by username, find the user based in username
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNmNotFoundException("User", "email", username));

        return user;
    }
}
