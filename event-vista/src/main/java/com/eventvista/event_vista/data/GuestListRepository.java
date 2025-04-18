package com.eventvista.event_vista.data;

import com.eventvista.event_vista.model.Client;
import com.eventvista.event_vista.model.Guest;
import com.eventvista.event_vista.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByEmailAddress(String emailAddress);

    List<Guest> findByGuestListId(Integer guestListId);
}
