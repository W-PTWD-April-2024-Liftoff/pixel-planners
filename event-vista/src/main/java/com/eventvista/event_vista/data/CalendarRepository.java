package com.eventvista.event_vista.data;

import com.eventvista.event_vista.model.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Integer> {
}
