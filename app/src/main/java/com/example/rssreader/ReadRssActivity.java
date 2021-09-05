package com.example.rssreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.rssreader.databinding.ActivityLoginBinding;
import com.example.rssreader.databinding.ActivityReadRssBinding;

public class ReadRssActivity extends AppCompatActivity {
    private ActivityReadRssBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadRssBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}


