package com.example.FiveStarHotel.service.inteface;

import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.model.Booking;

public interface BookService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getALlBookings();
    Response cancelBooking(Long booingId);
}
