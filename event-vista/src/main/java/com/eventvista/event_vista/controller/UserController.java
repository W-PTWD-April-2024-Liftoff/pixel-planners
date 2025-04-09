package com.eventvista.event_vista.controller;

import com.eventvista.event_vista.data.UserRepository;
import com.eventvista.event_vista.model.User;
import com.eventvista.event_vista.model.dto.UserProfileDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {

        // Get user ID from database using key
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        // Get optional back from database
        Optional<User> userOpt = userRepository.findById(userId);

        // Early return with null if user not found
        if (userOpt.isEmpty()) {
            return null;
        }

        // Return user object (unboxed from optional)
        return userOpt.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpSession session) {
        // Get user from session
        User user = getUserFromSession(session);
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // Create UserProfileDTO
        UserProfileDTO userProfileDTO = new UserProfileDTO(user.getId(), user.getName(), user.getEmailAddress(), user.getPictureUrl());
        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        // Get all users from the database
        Iterable<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/delete")
    public void deleteUser(@RequestParam Integer userId){
        userRepository.deleteById(userId);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserProfileDTO userProfileDTO,
                                               HttpSession session, Errors errors) {
        // Check for errors
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

//        User user = userRepository.findById(userProfileDTO.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get user from session
        User user = getUserFromSession(session);
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }
        // Update user profile
        user.setName(userProfileDTO.getName());
        user.setEmailAddress(userProfileDTO.getEmailAddress());
        user.setPictureUrl(userProfileDTO.getPictureUrl());

        // Save updated user to database
        userRepository.save(user);

        // Return updated user profile
        UserProfileDTO updatedUserProfile = new UserProfileDTO(user.getId(), user.getName(), user.getEmailAddress(), user.getPictureUrl());
        return ResponseEntity.ok(updatedUserProfile);


    }




}
