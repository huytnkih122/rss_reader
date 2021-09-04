package com.example.rssreader.ui.search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssItem;
import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private final Repository repository;
    private  MutableLiveData<List<RssItem>> rssItems;
    public MutableLiveData<List<RssItem>> getRssItems() {
        return rssItems;
    }

    private MutableLiveData<FirebaseUser> mutableLiveData;
    public SearchViewModel(Application application) {
        super(application);
        repository = Repository.getInstance();
        mutableLiveData = repository.getUserMutableLiveData();
        rssItems = repository.getListRssItemMutableLiveData();
    }


    public void loadRssItems(String url) {
        try {
            repository.fetchRSSData(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}