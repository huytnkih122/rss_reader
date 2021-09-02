package com.example.rssreader;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private Repository repository;


    private MutableLiveData<FirebaseUser> mutableLiveData;

    public LoginViewModel(Application application){
       repository = new Repository(application);
       mutableLiveData = repository.getUserMutableLiveData();
    }


    public void register(String email, String password){
        repository.register(email,password);
    }

}
