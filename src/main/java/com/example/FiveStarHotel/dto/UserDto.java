package com.example.FiveStarHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private List<BookingDto> bookings = new ArrayList<>();
}
