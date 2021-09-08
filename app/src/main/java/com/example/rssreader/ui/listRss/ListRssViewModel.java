package com.example.rssreader.ui.listRss;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssInfo;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.repository.Repository;
import com.example.rssreader.ui.history.HistoryItem;

import java.net.MalformedURLException;
import java.util.List;

public class ListRssViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MutableLiveData<RssInfo> rssInfo;
    public MutableLiveData<RssInfo> getRssInfo() {
        return rssInfo;
    }
    public Boolean openFragment;
    public ListRssViewModel(Application application) {
        super(application);
        repository = Repository.getInstance();
        rssInfo = repository.getRssInfoMutableLiveData();
        openFragment = false;
    }
    public void resetListRss(){
        repository.resetRssInfo();
    }

}