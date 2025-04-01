package com.eventvista.event_vista.data;

import com.eventvista.event_vista.model.Client;
import com.eventvista.event_vista.model.Event;
import com.eventvista.event_vista.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Iterable<Event> findByVenue(Venue venue);
    Iterable<Event> findByClient(Client client);

}
