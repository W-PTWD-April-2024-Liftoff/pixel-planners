//package com.eventvista.event_vista;
//
//import com.eventvista.event_vista.controller.AuthenticationController;
//import com.eventvista.event_vista.data.UserRepository;
//import com.eventvista.event_vista.model.User;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//
//public class AuthenticationFilter implements HandlerInterceptor {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    AuthenticationController authenticationController;
//
//    // Allow certain pages and static resources to be seen by the public (not logged in)
//    //private static final List<String> whitelist = Arrays.asList("/user/login", "/user", "/user/register", "/user/logout", "/css");
//    private static final List<String> whitelist = Arrays.asList("/", "/api/user/login", "/api/user/registration", "/api/user/logout", "/api/submission/searchandlist");
//
//    // Check all pages and static resources against blacklist
//    private static boolean isWhitelisted(String path) {
//        for (String pathRoot : whitelist) {
//            if (path.startsWith(pathRoot)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) throws IOException {
//
//        // Don't require sign-in for whitelisted pages
//        if (isWhitelisted(request.getRequestURI())) {
//            // Early return to allow request to go through
//            return true;
//        }
//
//        HttpSession session = request.getSession();
//        User user = authenticationController.getUserFromSession(session);
//
//        // The user is logged in
//        if (user != null) {
//            return true;
//        }
//
//        // The user is NOT logged in
//        response.sendRedirect("/login");
//        return false;
//    }
//
//}

