package com.example.rssreader.ui.history;

public class HistoryItem {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public HistoryItem(String title, String url, Long date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public HistoryItem() {
    }

    String url;
    Long date;
}
