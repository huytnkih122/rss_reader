package com.example.rssreader.ui.listRss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.R;
import com.example.rssreader.data.RssItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListRssAdapter extends  RecyclerView.Adapter<ListRssViewHolder> {

    private List<RssItem> rssItems;
    private Context context;
    private ItemClickListener listener;
    public ListRssAdapter(List<RssItem> rssItems, ItemClickListener listener) {
        this.rssItems = rssItems;
        this.listener = listener;
    }


    @NotNull
    @Override
    public ListRssViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View listRssView =
                inflater.inflate(R.layout.fragment_list_rss_items, parent, false);

        return new ListRssViewHolder(parent,listRssView);
    }



    @Override
    public void onBindViewHolder(ListRssViewHolder holder, int position) {
        holder.bind(this.rssItems.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }
}
