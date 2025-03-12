package com.eventvista.event_vista.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
public class Event extends AbstractEntity{

    @NotBlank(message = "Field must have valid event name entered")
    @Size(min = 3, max = 100, message = "Field must be between 3 and 100 characters")
    private String name;

    private String date;
    private String time;
    private String notes;

    @ManyToOne
    private Venue venue;

//    @ManyToMany
//    private Vendor vendor;
    @ManyToMany
    private List<Vendor> vendors;

//    @ManyToMany
//    private Client client;

    @OneToMany(mappedBy = "event")
    private List<Client> clients;

    public Event() {
    }

    public Event(String name, String date, String time, String notes, Venue venue, List<Vendor> vendors) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.venue = venue;
        this.vendors = vendors;
        //this.client = client;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

//    public Vendor getVendor() {
//        return vendor;
//    }
//
//    public void setVendor(Vendor vendor) {
//        this.vendor = vendor;
//    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }

    @Override
    public String toString() {
        return name;
    }

}
