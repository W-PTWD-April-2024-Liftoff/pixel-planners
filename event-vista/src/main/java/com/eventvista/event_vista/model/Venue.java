package com.eventvista.event_vista.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;



@Entity
public class Venue extends AbstractEntity {

    @NotBlank(message = "Field must have valid venue name entered")
    @Size(min = 3, max = 100, message = "Field must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Field must have valid venue location entered")
    @Size(min = 3, max = 100, message = "Field must be between 3 and 100 characters")
    private String location;

    @NotNull(message ="Field must have valid venue capacity entered")
    private Integer capacity;

    @NotNull(message ="Field must have valid venue phone number entered")
    @Embedded
    private PhoneNumber phoneNumber;


    @NotBlank(message ="Field must have valid venue email entered")
    @Email(message = "Field must have valid email entered")
    private String emailAddress;

    @Size(max = 500, message = "Field must be less than 500 characters")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private List<String> photoUrls = new ArrayList<>();

    @OneToMany(mappedBy = "venue")
    private List<Event> events = new ArrayList<>();


    // Constructor
    public Venue() {
    }

    public Venue(String name, String location, int capacity, PhoneNumber phoneNumber, String emailAddress, String notes) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.notes = notes;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return name;
    }

}

