package com.example.hotelreservationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ReservationConfirmation extends Fragment {
    String guestName, confirmationNumber, hotelName;
    TextView userText, confirmationText;
    Button homeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reservation_confirmation, container, false);
        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //heading text view
        Bundle bundle = getArguments();
        hotelName = bundle.getString("hotelName");
        guestName = bundle.getString("bookingPersonName");
        confirmationNumber = bundle.getString("confirmationNumber");

        userText = view.findViewById(R.id.userView);
        confirmationText = view.findViewById(R.id.confirmationView);
        homeButton = view.findViewById(R.id.homeButton);

        userText.setText("Thank you for choosing us as your travel partner, " + guestName + "!");
        confirmationText.setText("Your Bed Booking Id for " + hotelName + "  is: " + confirmationNumber);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
            }
        });
    }
}

