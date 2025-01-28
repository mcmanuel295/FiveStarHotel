package com.example.FiveStarHotel.controller;

import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.model.Booking;
import com.example.FiveStarHotel.service.inteface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hsdAuthority('USER")
    public ResponseEntity<Response> saveBookings(@PathVariable Long roomId,
                                                @PathVariable Long userId,
                                                @RequestBody Booking bookingRequest){
        Response response =  bookService.saveBooking(roomId,userId,bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAvailableBookings() {
        Response response = bookService.getALlBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingsByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hsdAuthority('USER")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
