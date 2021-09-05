package com.example.rssreader.data;

import com.example.rssreader.ui.history.HistoryItem;

import java.util.List;

public class User {

    String id;
    List<HistoryItem> item;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<HistoryItem> getItem() {
        return item;
    }

    public void setItem(List<HistoryItem> item) {
        this.item = item;
    }



}
