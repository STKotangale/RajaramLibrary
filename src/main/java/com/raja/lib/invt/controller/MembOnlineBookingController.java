package com.raja.lib.invt.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.objects.OnlineBookingDetails;
import com.raja.lib.invt.request.MembOnlineBookingRequestDTO;
import com.raja.lib.invt.resposne.MembOnlineBookingResponseDTO;
import com.raja.lib.invt.service.MembOnlineBookingService;

@RestController
@RequestMapping("/api/member-bookings")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MembOnlineBookingController {

    @Autowired
    private MembOnlineBookingService bookingService;

    @GetMapping
    public List<MembOnlineBookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembOnlineBookingResponseDTO> getBookingById(@PathVariable int id) {
        MembOnlineBookingResponseDTO booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public MembOnlineBookingResponseDTO createBooking(@RequestBody MembOnlineBookingRequestDTO bookingRequest) {
        return bookingService.saveBooking(bookingRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembOnlineBookingResponseDTO> updateBooking(@PathVariable int id, @RequestBody MembOnlineBookingRequestDTO bookingRequest) {
        MembOnlineBookingResponseDTO booking = bookingService.updateBooking(id, bookingRequest);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/bookings/{userId}")
    public List<OnlineBookingDetails> getBookingsByUserId(@PathVariable int userId) {
        return bookingService.getBookingsByUserId(userId);
    }	

}
