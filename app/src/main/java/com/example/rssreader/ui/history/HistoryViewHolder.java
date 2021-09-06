package com.example.rssreader.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView title = itemView.findViewById(R.id.title_history_item);
    TextView url = itemView.findViewById(R.id.url_history_item);
    TextView date = itemView.findViewById(R.id.date_history_item);
    ImageView imageRssItem = itemView.findViewById(R.id.logo_history_item);

    public HistoryViewHolder(ViewGroup parent, View itemView) {
        super(itemView);
        LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_history_item, parent, false
        );
    }

    public void bind(HistoryItem item, HistoryItemClickListener listener) {
        title.setText(item.getTitle());
        url.setText(item.getUrl());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strDate = formatter.format(new Date(item.getDate()));

        date.setText(strDate);
        if (!item.getLogo().isEmpty())
            Picasso.get().load(item.getLogo()).into(imageRssItem);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(item);
            }
        });
    }

}
