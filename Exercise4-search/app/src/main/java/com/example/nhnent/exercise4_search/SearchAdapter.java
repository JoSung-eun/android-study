package com.example.nhnent.exercise4_search;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nhnent on 2017. 4. 13..
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<DaumSearchResult.Item> items;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        ViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.text_title);
        }
    }

    SearchAdapter(List<DaumSearchResult.Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DaumSearchResult.Item item = items.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String title = Html.fromHtml(item.getTitle()).toString();
            holder.titleText.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            String title = Html.fromHtml(item.getTitle()).toString();
            holder.titleText.setText(Html.fromHtml(title));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
}
