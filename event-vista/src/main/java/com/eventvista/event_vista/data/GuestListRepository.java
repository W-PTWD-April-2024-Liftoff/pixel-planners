package com.eventvista.event_vista.data;

import com.eventvista.event_vista.model.User;
import com.eventvista.event_vista.model.GuestList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestListRepository extends JpaRepository<GuestList,Integer> {
    List<GuestList> findAllByEvent_User(User user);

}
