package com.example.hotelreservationsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////
////        // Replace the contents of the container with the new fragment
        fragmentTransaction.replace(R.id.frame_layout, new HotelSearchFragment());
////
////        // Complete the changes added above
        fragmentTransaction.commit();


    }
}
