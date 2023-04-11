package com.example.hotelreservationsystem;

import retrofit.Callback;
import retrofit.RestAdapter;

public class Api {

    private static final String BASE_URL = "http://192.168.4.24:8000/";
    //private static final String BASE_URL = "http://192.168.232.2:8000/";
    public static ApiInterface getClient() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
        return adapter.create(ApiInterface.class);
    }

    public static void makeReservation(Reservation reservation, Callback<ReservationConfirmationData> callback) {
        ApiInterface api = getClient();
        api.makeReservation(reservation, callback);
    }
}