package com.example.FiveStarHotel.service.inteface;

import com.example.FiveStarHotel.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice,String roomDescription);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(long roomId);

    Response updateRoom(long roomId,String roomType, BigDecimal roomPrice,String roomDescription, MultipartFile photo);

    Response getRoomById(long roomId);

    Response getAvailableRoomsByDateAndType(LocalDate checkInDate,LocalDate checkOutDate,String roomType);

    Response getAllAvailableRooms();


}
