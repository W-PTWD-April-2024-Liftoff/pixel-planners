package com.eventvista.event_vista.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Calendar extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Calendar must be associated with a user")
    private User user;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @NotBlank(message = "Calendar name is required")
    @Size(max = 255)
    private String name = "My Calendar"; // default value

    @Column(name = "sync_enabled", nullable = false)
    private boolean syncEnabled = false;

    @Column(nullable = false)
    private String timezone = "UTC";

    public Calendar() {
    }

    // Getters and setters
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSyncEnabled() {
        return syncEnabled;
    }

    public void setSyncEnabled(boolean syncEnabled) {
        this.syncEnabled = syncEnabled;
    }

    public String getTimezone() {
        return timezone;
    }
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}

