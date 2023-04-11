package com.example.hotelreservationsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;



public class HotelListFragment extends Fragment implements ItemClickListener {

    View view;
    TextView headingTextView;
    ProgressBar progressBar;
    List<HotelListData> hotelListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //heading text view
        headingTextView = view.findViewById(R.id.heading_text_view);
        progressBar = view.findViewById(R.id.progress_bar);

        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");
        String bookingPersonName = getArguments().getString("name of guest");
        //Set up the header

        headingTextView.setText("Welcome " + bookingPersonName + ", Explore our hotels for a great stay along with " + numberOfGuests + " guests from " + checkInDate +
                " to " + checkOutDate);


        // Set up the RecyclerView
        hotelListData = initHotelListData();
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), hotelListData);
        recyclerView.setAdapter(hotelListAdapter);
        hotelListAdapter.setClickListener(this);
        //getHotelsListsData();
    }


    public ArrayList<HotelListData> initHotelListData() {

        ArrayList<HotelListData> list = new ArrayList<>();
        list.add(new HotelListData("Mariott", "2000$", "true"));
        list.add(new HotelListData("QueenPlaza", "500$", "false"));
        list.add(new HotelListData("Hotel Connaught", "400$", "true"));
        list.add(new HotelListData("The Park Hyatt", "250$", "false"));
        list.add(new HotelListData("Fairmont", "3000$", "true"));
        list.add(new HotelListData("Hotel Ritz", "500$", "false"));
        list.add(new HotelListData("The Westin", "800$", "true"));
        list.add(new HotelListData("The Langham", "250$", "false"));

        return list;
    }

    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE);
        Api.getClient().getHotelsLists(new Callback<List<HotelListData>>() {
            @Override
            public void success(List<HotelListData> userListResponses, Response response) {
                // in this method we will get the response from API
                //userListResponseData = userListResponses;
                //System.out.println(userListResponseData);


                // Set up the RecyclerView
                setupRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                // if error occurs in network transaction then we can get the error in this method.
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        //recyclerView.setAdapter(hotelListAdapter);

        //Bind the click listener
        //hotelListAdapter.setClickListener(this);
    }

//    @Override
//    public void onClick(View view, int position) {
//
//    }


    @Override
    public void onClick(View view, int position) {
        HotelListData selectedHotel = hotelListData.get(position);

        String hotelName = selectedHotel.getHotel_name();
        String price = selectedHotel.getPrice();
        String availability = selectedHotel.getAvailability();

        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel price", price);
        bundle.putString("hotel availability", availability);
        bundle.putString("number of guests", getArguments().getString("number of guests"));
        bundle.putString("checkInDate", getArguments().getString("check in date"));
        bundle.putString("checkOutDate", getArguments().getString("check out date"));
        bundle.putString("bookingPersonName", getArguments().getString("name of guest"));
        HotelGuestDetailsFragment hotelGuestDetailsFragment = new HotelGuestDetailsFragment();
        hotelGuestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(HotelListFragment.this);
        fragmentTransaction.replace(R.id.main_layout, hotelGuestDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }
}