package com.example.FiveStarHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDto user;
    private RoomDto room;
    private BookingDto booking;
    private List<UserDto> userList = new ArrayList<>();
    private List<RoomDto> roomList = new ArrayList<>();
    private List<BookingDto> bookingList = new ArrayList<>();
}
