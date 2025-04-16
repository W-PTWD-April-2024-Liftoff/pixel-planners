package com.eventvista.event_vista.service;

import com.eventvista.event_vista.data.GuestRepository;
import com.eventvista.event_vista.model.Guest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest addGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(Integer id) {
        return guestRepository.findById(id);
    }

    public Optional<Guest> getGuestByEmail(String email) {
        return guestRepository.findByEmailAddress(email);
    }

    public List<Guest> getGuestsByGuestListId(Integer guestListId) {
        return guestRepository.findByGuestListId(guestListId);
    }

    public Optional<Guest> updateGuest(Integer id, Guest updatedGuest) {
        return guestRepository.findById(id).map(guest -> {
            guest.setName(updatedGuest.getName());
            guest.setEmailAddress(updatedGuest.getEmailAddress());
            guest.setNotes(updatedGuest.getNotes());
            guest.setRsvpStatus(updatedGuest.getRsvpStatus());
            guest.setGuestList(updatedGuest.getGuestList());
            return guestRepository.save(guest);
        });
    }

    public boolean deleteGuest(Integer id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
