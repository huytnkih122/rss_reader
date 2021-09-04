package com.example.rssreader.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rssreader.ReadRssActivity;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.databinding.FragmentSearchBinding;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.searchUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadRssItems("https://vnexpress.net/rss/the-gioi.rss");

            }
        });

        viewModel.getRssItems().observe(getViewLifecycleOwner(), new Observer<List<RssItem>>() {
            @Override
            public void onChanged(List<RssItem> rssItems) {
                for(int i = 0; i < rssItems.size() ; i++)
                    Log.i("MYTAG", rssItems.get(i).title);
                if(rssItems != null) {
                    Intent intent = new Intent(getActivity(), ReadRssActivity.class);
                    startActivity(intent);

                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}