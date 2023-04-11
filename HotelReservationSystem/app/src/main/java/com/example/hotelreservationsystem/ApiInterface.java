package com.example.hotelreservationsystem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {

    // API's endpoints
    @GET("/api/hotels")
    public void getHotelsLists(Callback<List<HotelListData>> callback);

    @POST("/api/makereservation")
    public void makeReservation(@Body Reservation reservation, Callback<ReservationConfirmationData> callback);


}