package com.example.hotelreservationsystem;

import java.util.List;

public class Reservation {
    String reservation_id;
    String hotel_name;
    String check_in_date;
    String check_out_date;
    List<Guest> guests_list;

    public String getReservation_id() {
        return reservation_id;
    }

    public String getHotel_name() { return hotel_name;
    }

    public void setHotel_name(String hotel_name) {this.hotel_name = hotel_name;
    }


    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheckindate(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheckoutdate(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public List<Guest> getGuests_list() {
        return guests_list;
    }

    public void setGuests_list(List<Guest> guests_list) {
        this.guests_list = guests_list;
    }
}