package com.example.rssreader.data;

public class RssItem {

    String title;
    String link;
    String pubDate;
    String description;
    String category;

    public RssItem(String title, String link, String pubDate, String description, String category) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.category = category;
    }
}
