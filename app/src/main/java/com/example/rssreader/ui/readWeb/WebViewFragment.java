package com.example.rssreader.ui.readWeb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentListRssBinding;
import com.example.rssreader.databinding.FragmentWebViewBinding;


public class WebViewFragment extends Fragment {
    private FragmentWebViewBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.webView.loadUrl(getArguments().getString("link"));

        return root;

    }
}