package com.example.FiveStarHotel.service;

import com.example.FiveStarHotel.exception.OurException;
import com.example.FiveStarHotel.model.User;
import com.example.FiveStarHotel.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByEmail(username)
                .orElseThrow(()-> new OurException("Username/Email not found"));
    }

}
