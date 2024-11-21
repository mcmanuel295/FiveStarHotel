package com.example.FiveStarHotel.controller;

import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.service.inteface.BookService;
import com.example.FiveStarHotel.service.inteface.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority( 'ADMIN')")
    public ResponseEntity<ResponseEntity> addNewRoom(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false) MultipartFile roomType,
            @RequestParam(value = "roomPrice", required = false) MultipartFile roomPrice,
            @RequestParam(value = "roomDescription", required = false) MultipartFile roomDescription

    ){
        Response response = ;
        return
    }
}
