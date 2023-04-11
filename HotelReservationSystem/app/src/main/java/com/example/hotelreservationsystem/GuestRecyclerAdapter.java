package com.example.hotelreservationsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuestRecyclerAdapter extends RecyclerView.Adapter<GuestRecyclerAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Integer noOfGuests;
    private String userName;
    private ArrayList<ViewHolder> holders;

    public GuestRecyclerAdapter(Context context, String guests, String userName) {
        this.layoutInflater = LayoutInflater.from(context);
        this.noOfGuests = Integer.parseInt(guests);
        this.userName = userName;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hotel_guest_details_layout, parent, false);
        if(holders == null) { holders = new ArrayList<>(this.noOfGuests); }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position==0) {
            holder.guestName.setText(userName);
        }

        holder.guestNameTextView.setText("Guest "+(position+1));
        holders.add(position, holder);
    }

    @Override
    public int getItemCount() {
        return noOfGuests;
    }

    public ArrayList<ViewHolder> getHolders() {
        return holders;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView guestNameTextView;
        EditText guestName;
        RadioButton maleRadio, femaleRadio;
        RadioGroup gender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            guestNameTextView = itemView.findViewById(R.id.name_guest_details_text_view);
            guestName = itemView.findViewById(R.id.name_guest_details_edit_text);
            maleRadio = itemView.findViewById(R.id.male_guest_details_radio_button);
            femaleRadio = itemView.findViewById(R.id.female_guest_details_radio_button);
            gender = itemView.findViewById(R.id.gender_guest_details_radio_group);
        }


        public EditText getGuestName() {
            return guestName;
        }

        public RadioGroup getGender() {
            return gender;
        }
    }
}