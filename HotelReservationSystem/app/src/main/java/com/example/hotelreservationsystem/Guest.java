package com.example.hotelreservationsystem;

public class Guest{
    String guest_name;
    String guest_gender;

    public Guest(String guest_name, String gender) {
        this.guest_name = guest_name;
        this.guest_gender = gender;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGender() {
        return guest_gender;
    }

    public void setGender(String gender) {
        this.guest_gender = gender;
    }
}