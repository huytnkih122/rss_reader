package com.example.rssreader.ui.account;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountViewModel extends AndroidViewModel {
    private Repository repository;


    private MutableLiveData<FirebaseUser> mutableLiveData;

    public AccountViewModel(Application application){
        super(application);
        repository = Repository.getInstance();
        mutableLiveData = repository.getUserMutableLiveData();
    }

    public void signOut(){
        repository.signOut();
    }

    public MutableLiveData<FirebaseUser> getMutableLiveData() {
        return mutableLiveData;
    }
}