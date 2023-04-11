package com.example.hotelreservationsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HotelSearchFragment extends Fragment {

    // These are the global variables
    View view;
    ConstraintLayout mainLayout;
    TextView titleTextView, searchTextConfirmationTextView;
    EditText guestsCountEditText, nameEditText;
    Button confirmSearchButton, searchButton, retrieveButton, clearButton;
    DatePicker checkInDatePicker, checkOutDatePicker;
    String checkInDate, checkOutDate, numberOfGuests, guestName;


    // Declaration of shared preferences keys
    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String name = "nameKey";
    public static final String guestsCount = "guestsCount";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hitel_search_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLayout = view.findViewById(R.id.main_layout);
        titleTextView = view.findViewById(R.id.title_text_view);
        searchTextConfirmationTextView = view.findViewById(R.id.search_confirm_text_view);

        guestsCountEditText = view.findViewById(R.id.guests_count_edit_text);

        //For Shared Pref Demo
        nameEditText = view.findViewById(R.id.name_edit_text);
        retrieveButton = view.findViewById(R.id.retrieve_button);
        clearButton = view.findViewById(R.id.clear_button);

        confirmSearchButton = view.findViewById(R.id.confirm_my_search_button);
        searchButton = view.findViewById(R.id.search_button);

        checkInDatePicker = view.findViewById(R.id.checkin_date_picker_view);
        checkOutDatePicker = view.findViewById(R.id.checkout_date_picker_view);

        //set Title Text
        titleTextView.setText(R.string.welcome_text);

        //Set up the text of confirm text box
        confirmSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInDate = getDateFromCalendar(checkInDatePicker);
                checkOutDate = getDateFromCalendar(checkOutDatePicker);
                //Get input of guests count
                numberOfGuests = guestsCountEditText.getText().toString();
                guestName = nameEditText.getText().toString();


                // Saving into shared preferences
                sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(name, guestName);
                editor.putString(guestsCount, numberOfGuests);
                editor.commit();



                searchTextConfirmationTextView.setText("Dear " + guestName +", Your check in date is " + checkInDate + ", " +
                        "your checkout date is " + checkOutDate + ".The number of guests are " + numberOfGuests);
            }
        });

        //Search Button click Listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInDate = getDateFromCalendar(checkInDatePicker);
                checkOutDate = getDateFromCalendar(checkOutDatePicker);
                //Get input of guests count
                numberOfGuests = guestsCountEditText.getText().toString();
                guestName = nameEditText.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("check in date", checkInDate);
                bundle.putString("check out date", checkOutDate);
                bundle.putString("number of guests", numberOfGuests);
                bundle.putString("name of guest", guestName );


                // set Fragment class Arguments
                HotelListFragment hotelsListFragment = new HotelListFragment();
                hotelsListFragment.setArguments(bundle);
                //FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout, hotelsListFragment);
                fragmentTransaction.remove(HotelSearchFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        // Retrieve Button Click Listener

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE);

                if (sharedPreferences.contains(name)) {
                    nameEditText.setText(sharedPreferences.getString(name, ""));
                }
                if (sharedPreferences.contains(guestsCount)) {
                    guestsCountEditText.setText(sharedPreferences.getString(guestsCount, ""));

                }
            }
        });

        //Clear Button Click Listener
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestsCountEditText.setText("");
                nameEditText.setText("");
            }
        });
    }

    // Function to get the date object
    private String getDateFromCalendar(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        return formattedDate;
    }



}