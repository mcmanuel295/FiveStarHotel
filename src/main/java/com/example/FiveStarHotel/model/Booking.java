package com.example.FiveStarHotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Check-in date is required")
    private LocalDate checkInDate;

    @Future(message = "checkout date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1,message = "number of adults must not be less than 1")
    private int noOfAdults;

    @Min(value = 0,message = "number of children must not be less than 0")
    private int noOfChildren;

    private int totalNumOfGuest;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id.")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id.")
    private Room room;

    public void calculateTotalNumberOfGuest(){
        this.totalNumOfGuest = this.noOfAdults+this.noOfChildren;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
        calculateTotalNumberOfGuest();
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
        calculateTotalNumberOfGuest();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", noOfAdults=" + noOfAdults +
                ", noOfChildren=" + noOfChildren +
                ", totalNumOfGuest=" + totalNumOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
