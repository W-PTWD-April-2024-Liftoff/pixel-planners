package com.eventvista.event_vista.service;

import com.eventvista.event_vista.model.User;
import com.eventvista.event_vista.data.UserRepository;
import com.eventvista.event_vista.model.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmailAddress(String emailAddress) {
        return userRepository.existsByEmailAddress(emailAddress);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User updateUserProfile(String email, UserProfileDTO dto) {
        Optional<User> userOpt = userRepository.findByEmailAddress(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        user.setName(dto.getName());
        user.setEmailAddress(dto.getEmailAddress());
        user.setPictureUrl(dto.getPictureUrl());

        return userRepository.save(user);
    }

}

