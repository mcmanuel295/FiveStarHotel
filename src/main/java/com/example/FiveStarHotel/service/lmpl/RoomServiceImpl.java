package com.example.FiveStarHotel.service.lmpl;

import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.dto.RoomDto;
import com.example.FiveStarHotel.exception.OurException;
import com.example.FiveStarHotel.model.Room;
import com.example.FiveStarHotel.repository.BookingRepo;
import com.example.FiveStarHotel.repository.RoomRepo;
import com.example.FiveStarHotel.service.inteface.RoomService;
import com.example.FiveStarHotel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
        @Autowired
        private RoomRepo roomRepo;

        @Autowired
        private BookingRepo bookingRepo;

        @Autowired
        AwsS3Service awsS3Service;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String roomDescription) {



        Response response = new Response();

        try {
            String imageUrl =awsS3Service.saveImage(photo);

            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(roomDescription);

            Room savedRoom = roomRepo.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(savedRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDto);
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error saving a room "+ ex.getMessage());
        }

        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepo.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomListt = roomRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDto> roomDtoList = Utils.mapRoomListEntityToRoomListDto(roomListt);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDtoList);
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error saving a room "+ ex.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteRoom(long roomId) {
        Response response = new Response();

        try {
            roomRepo.findById(roomId).orElseThrow(()-> new OurException("Room Not Found"));
            roomRepo.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successful");
        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error saving a room "+ ex.getMessage());
        }

        return response;
    }

    @Override
    public Response updateRoom(long roomId, String roomDescription, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();

        try {
            String imageUrl = null;

            if (photo != null && !photo.isEmpty()) {
                imageUrl = awsS3Service.saveImage(photo);
            }

            Room room = roomRepo.findById(roomId).orElseThrow(()-> new OurException("Room Not Found"));

            if (roomType != null) room.setRoomType(roomType);
            if(roomPrice !=null) room.setRoomPrice(roomPrice);
            if (roomDescription !=null) room.setRoomDescription(roomDescription);
            if (imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updateRoom = roomRepo.save(room);
            RoomDto roomDto = Utils.mapRoomEntityToRoomDto(updateRoom);


            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDto);
        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error saving a room "+ ex.getMessage());
        }

        return response;
    }


    @Override
    public Response getRoomById(long roomId) {
        Response response
                 = new Response();
        try{
            Room room= roomRepo.findById(roomId).orElseThrow(()-> new OurException("Room Not Found"));
            RoomDto roomDto = Utils.mapRoomEntityToRoomDtoPlusBookings(room);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDto);
        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage("");
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try{
            List<Room> availableRooms = roomRepo.findAvailableRoomsByDateAndType(checkInDate,checkOutDate,roomType);
            List<RoomDto> roomDtoList = Utils.mapRoomListEntityToRoomListDto(availableRooms);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDtoList);
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailabeRooms() {
        Response response = new Response();
        try{
            List<Room> availableRooms = roomRepo.getAllAvailableRooms();
            List<RoomDto> roomDtoList = Utils.mapRoomListEntityToRoomListDto(availableRooms);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDtoList);
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage(ex.getMessage());
        }
        return response;
    }
}
