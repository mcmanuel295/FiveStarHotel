package com.example.FiveStarHotel.service.inteface;

import com.example.FiveStarHotel.dto.LoginRequest;
import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.model.User;

public interface UserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingsHistory(String userID);

    Response deleteUser(String userID);

    Response getUserById(String userID);

    Response getMyInfo(String email);
}
