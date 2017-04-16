package com.example.nhnent.exercise4_search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

/**
 * Created by nhnent on 2017. 4. 13..
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_ITEM = 0;
    private int TYPE_LOADING = 1;

    private List<DaumSearchResult.Item> items;
    private boolean isLoading;
    private boolean isLastPage;

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        ItemViewHolder(View view) {
            super(view);
            titleText = (TextView) view.findViewById(R.id.text_title);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    SearchAdapter(List<DaumSearchResult.Item> items) {
        this.items = items;
        isLoading = false;
        isLastPage = false;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result, parent, false);
            return new ItemViewHolder(view);
        }
        else if (viewType == TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final DaumSearchResult.Item item = items.get(position);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String title = Html.fromHtml(item.getTitle()).toString();
                itemViewHolder.titleText.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY));
            }
            else {
                String title = Html.fromHtml(item.getTitle()).toString();
                itemViewHolder.titleText.setText(Html.fromHtml(title));
            }

            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                    v.getContext().startActivity(intent);
                }
            });
        }
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) == null) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void loadMore(RecyclerView recyclerView, String path, String query, int pageNo) {
        items.add(null);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(items.size() - 1);
            }
        });

        /*UrlConnectionModule.requestDaumSearch(path, query, pageNo, new HttpCallbackListener() {
            @Override
            public void onSuccess(String data) {
                DaumSearchResult daumSearchResult = new Gson().fromJson(data, DaumSearchResult.class);
                items.remove(null);
                notifyDataSetChanged();

                items.addAll(daumSearchResult.getChannel().getItem());
                notifyDataSetChanged();

                setLoading(false);
            }

            @Override
            public void onFail(String data) {
                items.remove(null);
                notifyDataSetChanged();
                setLastPage(true);
                setLoading(false);
            }
        });*/

        VolleyModule.requestDaumSearch(recyclerView.getContext(), path, query, pageNo, new HttpCallbackListener() {
            @Override
            public void onSuccess(String data) {
                DaumSearchResult daumSearchResult = new Gson().fromJson(data, DaumSearchResult.class);
                items.remove(null);
                notifyDataSetChanged();

                items.addAll(daumSearchResult.getChannel().getItem());
                notifyDataSetChanged();

                setLoading(false);
            }

            @Override
            public void onFail(String data) {
                items.remove(null);
                notifyDataSetChanged();
                setLastPage(true);
                setLoading(false);
            }
        });
    }
}
