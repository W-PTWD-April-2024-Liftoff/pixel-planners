package com.eventvista.event_vista.service;


import com.eventvista.event_vista.data.SkillRepository;
import com.eventvista.event_vista.data.VendorRepository;
import com.eventvista.event_vista.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final SkillRepository skillRepository;

    // Constructor
    @Autowired
    public VendorService(VendorRepository vendorRepository, SkillRepository skillRepository) {
        this.vendorRepository = vendorRepository;
        this.skillRepository= skillRepository;
    }


    // Query methods
    @PersistenceContext
    private EntityManager entityManager;

    public void flushChanges() {
        entityManager.flush();
    }

    public Vendor addVendor(Vendor vendor, User user) {
        // Set the user
        vendor.setUser(user);

        // Check for duplicate name
        if (vendorRepository.existsByNameIgnoreCase(vendor.getName())) {
            throw new IllegalArgumentException("A vendor with the name '" + vendor.getName() + "' already exists.");
        }

        // Check for duplicate email
        if (vendorRepository.existsByEmailAddressIgnoreCase(vendor.getEmailAddress())) {
            throw new IllegalArgumentException("A vendor with the email '" + vendor.getEmailAddress() + "' already exists.");
        }

        // Check for duplicate phone number
        if (vendorRepository.existsByPhoneNumber(vendor.getPhoneNumber())) {
            throw new IllegalArgumentException("A vendor with the phone number '" + vendor.getPhoneNumber() + "' already exists.");
        }

        // Handle Skill relationship
        List<Skill> incomingSkills = vendor.getSkills();
        if (incomingSkills != null && !incomingSkills.isEmpty()) {
            List<Skill> validSkills = incomingSkills.stream()
                    .map(Skill::getId)
                    .filter(Objects::nonNull)
                    .map(id -> skillRepository.findByIdAndUser(id, user))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            vendor.setSkills(validSkills);
        } else {
            vendor.setSkills(null);
        }

        return vendorRepository.save(vendor);
    }

    public Optional<Vendor> findVendorById(Integer id, User user) {
        return vendorRepository.findByIdAndUser(id, user);
    }

    public Optional<Vendor> findVendorByName(String name, User user) {
        return vendorRepository.findByNameAndUser(name, user)
                .filter(vendor -> vendor.getUser().getId().equals(user.getId()));
    }

    public Optional<Vendor> findVendorByLocation(String location, User user) {
        return vendorRepository.findByLocationAndUser(location, user)
                .filter(vendor -> vendor.getUser().getId().equals(user.getId()));

    }

    public List<Vendor> findVendorBySkillId(Integer skillId, User user) {
        return vendorRepository.findBySkillsIdAndUser(skillId, user);
    }

    public List<Vendor> findVendorBySkillName(String skillName, User user) {
        return vendorRepository.findBySkillsNameAndUser(skillName, user);
    }

    public Optional<Vendor> findVendorByPhoneNumber(PhoneNumber phoneNumber, User user) {
        return vendorRepository.findByPhoneNumberAndUser(phoneNumber, user)
                .filter(vendor -> vendor.getUser().getId().equals(user.getId()));
    }

    public Optional<Vendor> findVendorByEmailAddress(String emailAddress, User user) {
        return vendorRepository.findByEmailAddressAndUser(emailAddress, user)
                .filter(vendor -> vendor.getUser().getId().equals(user.getId()));
    }

    public List<Vendor> findAllVendors(User user) {
        return vendorRepository.findAllByUser(user);
    }

    public Optional<Vendor> updateVendor(Integer id, Vendor updatedVendor, User user) {
        return Optional.ofNullable(vendorRepository.findByIdAndUser(id, user)
                .map(existingVendor -> {
                    // Update basic fields
                    existingVendor.setName(updatedVendor.getName());
                    existingVendor.setLocation(updatedVendor.getLocation());
                    existingVendor.setPhoneNumber(updatedVendor.getPhoneNumber());
                    existingVendor.setEmailAddress(updatedVendor.getEmailAddress());
                    existingVendor.setNotes(updatedVendor.getNotes());

                    // Handle Skill relationship
                    List<Skill> incomingSkills = updatedVendor.getSkills();
                    if (incomingSkills != null && !incomingSkills.isEmpty()) {
                        List<Skill> validSkills = incomingSkills.stream()
                                .map(Skill::getId)
                                .filter(Objects::nonNull)
                                .map(skillId -> skillRepository.findByIdAndUser(skillId, user))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList());

                        existingVendor.setSkills(validSkills);
                    } else {
                        existingVendor.setSkills(null);
                    }

                    return vendorRepository.save(existingVendor);
                })
                .orElseThrow(() -> new RuntimeException("Vendor not found")));
    }

    public boolean deleteVendor(Integer id, User user) {
        Optional<Vendor> vendor = vendorRepository.findByIdAndUser(id, user);
        if (vendor.isPresent()) {
            vendorRepository.delete(vendor.get());
            return true;
        }
        return false;
    }

    @Transactional
    public void removeSkillFromVendors(Integer skillId, User user) {
        List<Vendor> vendors = vendorRepository.findBySkillsIdAndUser(skillId, user);

        for (Vendor vendor : vendors) {
            vendor.getSkills().removeIf(skill -> skill.getId().equals(skillId));
            vendorRepository.save(vendor);
        }
        entityManager.flush();
    }
}
