package com.example.rssreader.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.R;
import com.example.rssreader.ui.listRss.ItemRssClickListener;
import com.example.rssreader.ui.listRss.ListRssViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryViewHolder> {

    private List<HistoryItem> historyItems;
    private Context context;
    private HistoryItemClickListener listener;
    public HistoryAdapter(List<HistoryItem> historyItems, HistoryItemClickListener listener) {
        Collections.sort(historyItems, new Comparator<HistoryItem>() {
            @Override
            public int compare(HistoryItem u1, HistoryItem u2) {
                return u1.getDate().compareTo(u2.getDate());
            }
        });
        Collections.reverse(historyItems);
        this.historyItems = historyItems;
        this.listener = listener;
    }


    @NotNull
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View historyItemView =
                inflater.inflate(R.layout.fragment_history_item, parent, false);

        return new HistoryViewHolder(parent,historyItemView);
    }



    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.bind(this.historyItems.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }
}


