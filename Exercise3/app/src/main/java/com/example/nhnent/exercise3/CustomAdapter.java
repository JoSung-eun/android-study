package com.example.nhnent.exercise3;

import android.database.Cursor;
import android.provider.ContactsContract;
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
    private Cursor cursor;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView phoneNumberText;

        ViewHolder(View view) {
            super(view);

            nameText = (TextView) view.findViewById(R.id.text_name);
            phoneNumberText = (TextView) view.findViewById(R.id.text_phone_number);

        }
    }

    CustomAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cursor currentCursor = getItem(position);
        holder.nameText.setText(currentCursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
        holder.phoneNumberText.setText(currentCursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    private Cursor getItem(int position) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.moveToPosition(position);
        }
        return cursor;
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }
}
