package com.example.nhnent.recyclerviewstudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nhnent on 2017. 4. 3..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] dataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(TextView view) {
            super(view);
            /*view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Log.d("ADAP", "Clicked item : " + getAdapterPosition() + " " + textView.getText());
                        Toast.makeText(v.getContext(), "Clicked item : " + getAdapterPosition() + " " + textView.getText(), Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });*/
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ADAP", "Clicked item : " + getAdapterPosition() + " " + textView.getText());
                }
            });

            textView = view;

        }
    }

    public MyAdapter(Context context, String[] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("ADAPTER", "ViewHolder - onCreate " + viewHolder.toString().substring(11, 18));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(dataSet[position]);

        Log.d("ADAPTER", "ViewHolder - onBind ");
        //Log.d("ADAPTER", "ViewHolder - onBind " + holder.toString().substring(11, 18));
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        view.setAnimation(animation);
    }
}
