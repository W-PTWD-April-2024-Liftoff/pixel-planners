package com.eventvista.event_vista.controller;

import com.eventvista.event_vista.data.UserRepository;
import com.eventvista.event_vista.model.User;
import com.eventvista.event_vista.model.dto.LoginFormDTO;
import com.eventvista.event_vista.model.dto.RegisterFormDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    // The key to store user IDs
    private static final String userSessionKey = "user";

    // Stores key/value pair with session key and user ID
    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
        System.out.println("session: " + session.getAttribute("user"));
    }

    // Look up user with key
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

    // Handlers for registration form
    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registrationFormDTO,
                                          Errors errors,
                                          HttpServletRequest request) {

        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            return "register";
        }

        // Look up user in database using username they provided in the form
        User existingUser = userRepository.findByUsername(registrationFormDTO.getUsername());
        //User existingEmail = userRepository.findByEmail(email);

        // Send user back to form if username already exists
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            return "register";
        }

        // Send user back to form if passwords didn't match
        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        // Send user back to form if email didn't match
        String email = registrationFormDTO.getEmail();
        String verifyEmail = registrationFormDTO.getVerifyEmail();
        if (!email.equals(verifyEmail)) {
            errors.rejectValue("email", "email.mismatch", "Email do not match");
            return "register";
        }

        User existingEmail = userRepository.findByEmail(email);
        //String email = registrationFormDTO.getEmail();
        if (email.isEmpty()) {
            errors.rejectValue("email", "email.isEmpty", "Email is required.");
        } else if (existingEmail != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists.");
        }

        // OTHERWISE, save new username and hashed password in database, start a new session, and redirect to home page
        User newUser = new User(registrationFormDTO.getUsername(), registrationFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
        return "redirect:"; //NEED TO ADD REDIRECT PAGE AKA HOME PAGE
    }

    // Handlers for login form
    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO()); // "loginFormDTO" variable implicit
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors,
                                   HttpServletRequest request) {

        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            return "login";
        }

        // Look up user in database using username they provided in the form
        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        // Get the password the user supplied in the form
        String password = loginFormDTO.getPassword();

        // Send user back to form if username does not exist OR if password hash doesn't match
        // "Security through obscurity" — don't reveal which one was the problem
        if (theUser == null || !theUser.isMatchingPassword(password)) {
            errors.rejectValue(
                    "password",
                    "login.invalid",
                    "Credentials invalid. Please try again with correct username/password combination."
            );
            return "login";
        }

        // OTHERWISE, create a new session for the user and take them to the home page
        setUserInSession(request.getSession(), theUser);
        return "redirect:"; //NEED TO ADD REDIRECT PAGE AKA HOME PAGE!!!!!
    }

    // Handler for logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    //    Delete User
    @PostMapping("/delete")
    public void deleteUser(@RequestParam Integer userId){
        userRepository.deleteById(userId);
    }


}
