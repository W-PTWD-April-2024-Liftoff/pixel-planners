package com.eventvista.event_vista.model;

import jakarta.persistence.Entity;

import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Skill extends AbstractEntity implements Serializable {

    @NotBlank(message = "Field must have valid service or skill name entered")
    @Size(min = 3, max = 100, message = "Field must be between 3 and 100 characters")
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<Vendor> vendors = new HashSet<>();

    // Constructor
    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }

    @Override
    public String toString() {
        return name;
    }

}
