package com.eventvista.event_vista.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Calendar extends AbstractEntity {

    @OneToOne
    @NotNull
    private User user;

    @OneToMany(mappedBy = "calendar")
    private List<Event> events = new ArrayList<>();


    @NotNull
    private String timezone;

    private String googleCalendarId;

    public Calendar() {

    }

    public Calendar(User user, String timezone) {
        this.user = user;
        this.timezone = timezone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getGoogleCalendarId() {
        return googleCalendarId;
    }

    public void setGoogleCalendarId(String googleCalendarId) {
        this.googleCalendarId = googleCalendarId;
    }

}

