package com.example.nhnent.recyclerviewstudy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by nhnent on 2017. 4. 3..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(TextView view) {
            super(view);
            textView = view;
        }
    }

    public MyAdapter(String[] dataSet) {
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

}
