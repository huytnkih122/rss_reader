package com.example.rssreader.ui.signIn;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class SignInViewModel extends AndroidViewModel {
    private Repository repository;


    private MutableLiveData<FirebaseUser> mutableLiveData;

    public SignInViewModel(Application application){
        super(application);
        repository = new Repository(application);
        mutableLiveData = repository.getUserMutableLiveData();
    }


    public void login(String email, String password){
        repository.login(email,password);
    }
    public MutableLiveData<FirebaseUser> getMutableLiveData() {
        return mutableLiveData;
    }
}