package com.example.FiveStarHotel.repository;

import com.example.FiveStarHotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BookingRepo extends JpaRepository<Booking,Long> {

    List<Booking> findByRoomId(Long roomID);

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    List<Booking> findByUserId(Long userId);
}
