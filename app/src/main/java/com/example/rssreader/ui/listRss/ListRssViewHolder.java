package com.example.rssreader.ui.listRss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rssreader.R;
import com.example.rssreader.data.RssItem;
import com.squareup.picasso.Picasso;

public class ListRssViewHolder extends RecyclerView.ViewHolder {

    TextView title = itemView.findViewById(R.id.title_rssItem);
    TextView actor = itemView.findViewById(R.id.actor_rssItem);
    TextView date = itemView.findViewById(R.id.date_rssItem);
    ImageView imageRssItem = itemView.findViewById(R.id.image_rssItem);
    LinearLayout layout = itemView.findViewById(R.id.layout_item);
    public ListRssViewHolder(ViewGroup parent, View itemView) {
        super(itemView);
        LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_list_rss_items, parent, false
        );
    }

    public void bind(RssItem item, ItemClickListener listener) {
        title.setText(item.getTitle());
        actor.setText(item.getAuthor());
        date.setText(item.getPubDate());
        if (item.getImage() == null) layout.setWeightSum(3);
        else
            Picasso.get().load(item.getImage()).into(imageRssItem);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(item);
            }
        });
    }

}