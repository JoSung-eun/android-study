package com.example.nhnent.exercise3;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by nhnent on 2017. 4. 5..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<MainActivity.Contact> contacts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView phoneNumberText;

        public ViewHolder(View view) {
            super(view);

            nameText = (TextView) view.findViewById(R.id.text_name);
            phoneNumberText = (TextView) view.findViewById(R.id.text_phone_number);

        }
    }

    public CustomAdapter(ArrayList<MainActivity.Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameText.setText(contacts.get(position).name);
        holder.phoneNumberText.setText(contacts.get(position).phoneNumber);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
