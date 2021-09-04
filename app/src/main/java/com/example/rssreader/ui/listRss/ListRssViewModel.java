package com.example.rssreader.ui.listRss;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssItem;
import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;
import java.util.List;

public class ListRssViewModel extends AndroidViewModel {
    private final Repository repository;
    private MutableLiveData<List<RssItem>> rssItems;
    public MutableLiveData<List<RssItem>> getRssItems() {
        return rssItems;
    }

    public ListRssViewModel(Application application) {
        super(application);
        repository = Repository.getInstance();
        rssItems = repository.getListRssItemMutableLiveData();
    }

}