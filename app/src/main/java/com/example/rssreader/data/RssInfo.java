package com.example.rssreader.data;

import java.util.ArrayList;

public class RssInfo {
    String title;
    String logo;
    ArrayList<RssItem> rssItems;

    public RssInfo() {
        rssItems = new ArrayList<RssItem>();
        logo = "";
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<RssItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(ArrayList<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public void addItem(RssItem item) {
        rssItems.add(item);
    }
}
