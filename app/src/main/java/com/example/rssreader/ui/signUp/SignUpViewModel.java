package com.example.rssreader.ui.signUp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rssreader.repository.Repository;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends AndroidViewModel {
    private Repository repository;


    private MutableLiveData<FirebaseUser> mutableLiveData;

    public SignUpViewModel(Application application){
        super(application);
        repository = new Repository(application);
        mutableLiveData = repository.getUserMutableLiveData();
    }


    public void register(String email, String password){
        repository.register(email,password);
    }
    public MutableLiveData<FirebaseUser> getMutableLiveData() {
        return mutableLiveData;
    }
}
