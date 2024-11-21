package com.example.FiveStarHotel.service.lmpl;

import com.example.FiveStarHotel.dto.LoginRequest;
import com.example.FiveStarHotel.dto.Response;
import com.example.FiveStarHotel.dto.UserDto;
import com.example.FiveStarHotel.exception.OurException;
import com.example.FiveStarHotel.model.User;
import com.example.FiveStarHotel.repository.UserRepo;
import com.example.FiveStarHotel.service.inteface.UserService;
import com.example.FiveStarHotel.util.JWTUtils;
import com.example.FiveStarHotel.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if (user.getRole()==null || user.getRole().isBlank()) {
                user.setRole("USER");
            }

            if (userRepo.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail()+" already exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(savedUser);
            response.setStatusCode(200);
            response.setUser(userDto);

         }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration" +ex.getMessage());
        }

        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new OurException("User Not Found"));

            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("successful");
        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Login" +ex.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();

        try {
            List<User> userList = userRepo.findAll();
            List<UserDto> userDtoLists = Utils.mapUserListEntityToUserListDto(userList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDtoLists);
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Getting All Users" +ex.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserBookingsHistory(String userID) {

        Response response = new Response();
        try {

            User user = userRepo.findById( Long.valueOf(userID)).orElseThrow( ()-> new OurException("User Not Found"));
            UserDto userDto = Utils.mapUserEntityToUseDtoPlusUserBookingsAndUserRoom(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration" +ex.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(String userID) {
        Response response = new Response();
        try {

            userRepo.findById( Long.valueOf(userID)).orElseThrow( ()-> new OurException("User Not Found"));
            userRepo.deleteById(Long.valueOf(userID));
            response.setStatusCode(200);
            response.setMessage("successful");

        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration" +ex.getMessage());
        }


        return null;
    }

    @Override
    public Response getUserById(String userID) {
        Response response = new Response();
        try {

            User user = userRepo.findById( Long.valueOf(userID)).orElseThrow( ()-> new OurException("User Not Found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration" +ex.getMessage());
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {

            User user = userRepo.findByEmail(email).orElseThrow( ()-> new OurException("User Not Found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }
        catch (OurException ex){
            response.setStatusCode(400);
            response.setMessage(ex.getMessage());
        }
        catch (Exception ex){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration" +ex.getMessage());
        }

        return response;
    }

}
