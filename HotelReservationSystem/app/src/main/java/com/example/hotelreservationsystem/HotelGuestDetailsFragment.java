package com.example.hotelreservationsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HotelGuestDetailsFragment  extends Fragment {

    View view;
    String bookingPersonName;

    String noOfGuests;
    TextView name_textView, check_in_date_view, check_out_date_view, no_of_guest_view, priceTextView, availabilityTextView;
    Button bedBooking;
    GuestRecyclerAdapter guestRecyclerAdapter;
    ProgressBar progressBar;
    ArrayList<GuestRecyclerAdapter.ViewHolder> holders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_guest_layout, container, false);
        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //heading text view
        Bundle bundle = getArguments();

        //int numberOfGuestInt = Integer.parseInt(numberOfGuests);
        name_textView = view.findViewById(R.id.hotel_name_edit_text);
        check_in_date_view = view.findViewById(R.id.check_in_date_edit_text);
        check_out_date_view = view.findViewById(R.id.check_out_date_edit_text);
        no_of_guest_view = view.findViewById(R.id.no_of_guests_edit_text);
        priceTextView = view.findViewById(R.id.hotel_price_edit_text);
        bedBooking = view.findViewById(R.id.guest_details_book_button);
        progressBar = view.findViewById(R.id.confirmation_progress_bar);


        name_textView.setText(bundle.getString("hotel name"));

        priceTextView.setText('$' + String.valueOf(Integer.parseInt(bundle.getString("number of guests")) * Integer.parseInt(bundle.getString("hotel price").replace("$", ""))));
        bookingPersonName = bundle.getString("bookingPersonName");
        noOfGuests = bundle.getString("number of guests");
        check_in_date_view.setText(bundle.getString("checkInDate"));
        check_out_date_view.setText(bundle.getString("checkOutDate"));
        no_of_guest_view.setText(bundle.getString("number of guests"));


        bedBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                holders = guestRecyclerAdapter.getHolders();
                ArrayList<Guest> guestData = new ArrayList<>();
                for (int i = 0; i < holders.size(); i++) {
                    GuestRecyclerAdapter.ViewHolder holder = holders.get(i);
                    String name = holder.getGuestName().getText().toString();
                    int genderSelected = holder.getGender().getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) holder.itemView.findViewById(genderSelected);
                    String radioText = radioButton.getText().toString().trim().toLowerCase();

                    Guest guest ;

                    if(radioText.equals("female")) {
                            guest = new Guest(name,"F");
                        } else {
                            guest = new Guest(name,"M");
                        }

                        guestData.add(guest);
                    }
                Reservation reservation = new Reservation();
                reservation.setHotel_name(name_textView.getText().toString());
                reservation.setCheckindate(check_in_date_view.getText().toString());
                reservation.setCheckoutdate(check_out_date_view.getText().toString());
                reservation.setGuests_list(guestData);

                try {
                String datePattern = "dd-MM-yyyy";;
                reservation.setCheckindate(new SimpleDateFormat("yyyy-MM-dd").format(
                        new SimpleDateFormat(datePattern).parse(bundle.getString("checkInDate"))

                ));

                reservation.setCheckoutdate(new SimpleDateFormat("yyyy-MM-dd").format(
                        new SimpleDateFormat(datePattern).parse(bundle.getString("checkOutDate"))
                ));
            } catch (ParseException e) {
                Toast.makeText(getContext(), "INVALID DATE FORMAT", Toast.LENGTH_LONG).show();
            }
            makeReservation(reservation);

        }

});

        setupRecyclerView();
        }


        private void setupRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.guest_details_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        guestRecyclerAdapter = new GuestRecyclerAdapter(getActivity(), noOfGuests, bookingPersonName);
        recyclerView.setAdapter(guestRecyclerAdapter);
    };
    public void makeReservation(Reservation reservation) {
        // Call the makeReservation API method and enqueue a callback to handle the response
        Api.makeReservation(reservation, new Callback<ReservationConfirmationData>() {
            @Override
            public void success(ReservationConfirmationData reservationConfirmationData, Response response) {
                if (reservationConfirmationData != null) {
                    String confirmationNumber = reservationConfirmationData.getConfirmation_number();
                    updateData(confirmationNumber, false);
                } else {
                    updateData("FAILED TO MAKE RESERVATION", true);
                }
                System.out.println("API Response");
                System.out.println("Status Code: " + response.getStatus());
                System.out.println("Headers: " + response.getHeaders());
                System.out.println("Body: " + response.getBody());
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void updateData(String confirmationNumber, boolean isError) {
            Bundle confirmation = new Bundle();

            if (isError) {
                confirmation.putString("confirmationNumberError", confirmationNumber);
                Toast.makeText(getActivity(), confirmationNumber, Toast.LENGTH_LONG).show();
            }
            else {
                confirmation.putString("confirmationNumberError", null);
                confirmation.putString("confirmationNumber", confirmationNumber);
                confirmation.putString("bookingPersonName", getArguments().getString("bookingPersonName"));
                confirmation.putString("hotelName", name_textView.getText().toString());
                confirmation.putString("checkInDate", check_in_date_view.getText().toString());

                SharedPreferences searchData = getActivity().getSharedPreferences(HotelSearchFragment.myPreference, Context.MODE_PRIVATE);
                searchData.edit().clear().commit();
            }

            ReservationConfirmation confirmationFragment = new ReservationConfirmation();
            confirmationFragment.setArguments(confirmation);

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.remove(HotelGuestDetailsFragment.this);
            fragmentTransaction.replace(R.id.frame_layout, confirmationFragment, "CONFIRMATION_FRAGMENT");
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        }

}








