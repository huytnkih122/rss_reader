package com.example.rssreader.ui.history;

public class HistoryItem {
    String title;
    String logo;
    String url;
    Long date;

    public HistoryItem(String title, String url, Long date, String logo) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.logo = logo;
    }

    public HistoryItem() {
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
}
