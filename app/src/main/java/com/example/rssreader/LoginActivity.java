package com.example.rssreader;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rssreader.databinding.ActivityContainerBinding;
import com.example.rssreader.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    static final int RC_SIGN_IN = 120;
    static final String TAG = "MyTag";
    private GoogleSignInClient googleSignInClient;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}


