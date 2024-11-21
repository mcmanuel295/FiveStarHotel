package com.example.FiveStarHotel.controller;

import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.service.inteface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN'")
    public ResponseEntity<Response> getAllUsers(){
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/get-By-Id/{userID}")
    public ResponseEntity<Response> getById(@PathVariable("userId") String userId){
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/delete/{userID}")
    public ResponseEntity<Response> deleteById(@PathVariable("userId") String userId){
        Response response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-logged-in-profile-info/{userID}")
    public ResponseEntity<Response> getloggedInUserProfile(@PathVariable("userId") String userId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email  =authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-bookings/{userID}")
    public ResponseEntity<Response> getUserBookingsHistory(@PathVariable("userId") String userId){
        Response response = userService.getUserBookingsHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
