package com.example.rssreader.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.repository.Repository;

import java.net.MalformedURLException;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final Repository repository;

    public MutableLiveData<List<HistoryItem>> getHistoryItems() {
        return historyItems;
    }

    private MutableLiveData<List<HistoryItem>> historyItems;


    public HistoryViewModel(Application application) {
        super(application);
        repository = Repository.getInstance();
        historyItems = repository.getHistoryItemsMutableLiveData();
    }

    public void loadData(){
        repository.loadData();
    }
    public void loadRssItems(String url) {
        try {
            repository.fetchRSSData(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void saveHistory(HistoryItem item){
        repository.addHistoryItem(item);
    }
}