package com.eventvista.event_vista.controller;


import com.eventvista.event_vista.model.*;
import com.eventvista.event_vista.service.GuestService;
import com.eventvista.event_vista.utilities.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guests")
@CrossOrigin(origins = "http://localhost:3000")
public class GuestController {
    private final GuestService guestService;
    private final AuthUtil authUtil;

    public GuestController(GuestService guestService, AuthUtil authUtil) {
        this.guestService = guestService;
        this.authUtil = authUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Guest>> getAllGuests() {
        User user = authUtil.getUserFromAuthentication();
        List<Guest> guests = GuestService.findAllGuest(user);
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Integer id) {
        return ResponseEntity.of(guestService.getGuestById(id));
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable String email) {
        return ResponseEntity.of(guestService.getGuestByEmail(email));
    }

    @GetMapping("/by-guestlist/{guestListId}")
    public ResponseEntity<List<Guest>> getGuestsByGuestList(@PathVariable Integer guestListId) {
        return ResponseEntity.ok(guestService.getGuestsByGuestListId(guestListId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGuest(@Valid @RequestBody Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        Guest savedGuest = guestService.addGuest(guest);
        return ResponseEntity.ok(savedGuest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable Integer id, @Valid @RequestBody Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        return guestService.updateGuest(id, guest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable Integer id) {
        if (guestService.deleteGuest(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
