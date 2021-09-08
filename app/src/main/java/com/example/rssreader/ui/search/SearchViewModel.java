package com.example.rssreader.ui.search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssInfo;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.repository.Repository;
import com.example.rssreader.ui.history.HistoryItem;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MutableLiveData<RssInfo> rssInfo;
    public Boolean openFragment;
    public MutableLiveData<RssInfo> getRssInfo() {
        return rssInfo;
    }

    private final MutableLiveData<FirebaseUser> mutableLiveData;
    public SearchViewModel(Application application) {
        super(application);
        repository = Repository.getInstance();
        mutableLiveData = repository.getUserMutableLiveData();
        rssInfo = repository.getRssInfoMutableLiveData();
        openFragment = false;
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