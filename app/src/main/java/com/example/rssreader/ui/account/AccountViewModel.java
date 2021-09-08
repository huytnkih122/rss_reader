package com.example.rssreader.ui.account;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class AccountViewModel extends AndroidViewModel {
    private final Repository repository;


    private final MutableLiveData<FirebaseUser> userMutableLiveData;


    public AccountViewModel(Application application){
        super(application);
        repository = Repository.getInstance();
        userMutableLiveData = repository.getUserMutableLiveData();
    }

    public void signOut(){
        repository.signOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}