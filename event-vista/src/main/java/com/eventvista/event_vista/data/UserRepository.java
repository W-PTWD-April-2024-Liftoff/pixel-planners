package com.eventvista.event_vista.data;

import com.eventvista.event_vista.model.User;
//import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
//@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
//    User findByUsername(String username);
//    User findByEmailAddress(String emailAddress);
//changed to authenticate by email address and password. can add username later
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findByVerificationToken(String token);
    boolean existsByEmailAddress(String emailAddress);
    List<User> findByEmailVerifiedTrue();
}






