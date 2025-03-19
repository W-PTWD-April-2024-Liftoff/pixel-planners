package com.eventvista.event_vista.controller;


import com.eventvista.event_vista.data.CalendarRepository;
import com.eventvista.event_vista.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CalendarController {

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserRepository userRepository;

    //Get calendar for a specific user

    //Get all events for a calendar

    //Create a new calendar for a user

}
