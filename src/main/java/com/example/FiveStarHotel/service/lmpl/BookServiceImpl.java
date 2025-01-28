package com.example.FiveStarHotel.service.lmpl;

import com.example.FiveStarHotel.dto.BookingDto;
import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.exception.OurException;
import com.example.FiveStarHotel.model.Booking;
import com.example.FiveStarHotel.model.Room;
import com.example.FiveStarHotel.model.User;
import com.example.FiveStarHotel.repository.BookingRepo;
import com.example.FiveStarHotel.repository.RoomRepo;
import com.example.FiveStarHotel.repository.UserRepo;
import com.example.FiveStarHotel.service.inteface.BookService;
import com.example.FiveStarHotel.service.inteface.RoomService;
import com.example.FiveStarHotel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            if ( bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check indDate must come after check oiut date");
            }

            Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            User user = userRepo.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            List<Booking> existingBooking = room.getBookings();

            if (roomIsAvailable(bookingRequest,existingBooking)) {
                throw new OurException("Room Not Available For Selected date range");
            }

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingCOnfirmationCode = Utils.generateRandomAConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingCOnfirmationCode);
            bookingRepo.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingConfirmationCode(bookingCOnfirmationCode);
        }
        catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Saving a Booking" +ex.getMessage() );
        }

        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try {
            Booking booking = bookingRepo.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new OurException("Booking Not Found"));
            BookingDto bookingDto = Utils.mapBookingsEntityToBookingsDto(booking);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDto);
        }
        catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Finding a Booking" +ex.getMessage() );
        }

        return response;
    }

    @Override
    public Response getALlBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<BookingDto> bookingDtoList = Utils.mapBookingListEntityToBookingListDto(bookingList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDtoList);
        }
        catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Finding a Booking" +ex.getMessage() );
        }

        return response;
    }

    @Override
    public Response cancelBooking(Long booingId) {
        Response response = new Response();

        try {
            Booking booking = bookingRepo.findById(booingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
             bookingRepo.deleteById(booingId);

            response.setStatusCode(200);
            response.setMessage("successful");
        }
        catch (OurException ex){
            response.setStatusCode(404);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Canceling a Booking" +ex.getMessage() );
        }

        return response;
    }


    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()))
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate())

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
