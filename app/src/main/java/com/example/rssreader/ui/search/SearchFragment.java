package com.example.rssreader.ui.search;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rssreader.ReadRssActivity;
import com.example.rssreader.data.RssInfo;
import com.example.rssreader.databinding.FragmentSearchBinding;
import com.example.rssreader.ui.history.HistoryItem;

import java.util.Calendar;
import java.util.Date;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.textUrlSearch.setAdapter(getUrlRss(requireContext()));
        binding.searchUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadRssItems(binding.textUrlSearch.getText().toString());
                viewModel.openFragment = true;
                Intent intent = new Intent(getActivity(), ReadRssActivity.class);
                startActivity(intent);
            }
        });

        viewModel.getRssInfo().observe(getViewLifecycleOwner(), new Observer<RssInfo>() {
            @Override
            public void onChanged(RssInfo rssInfo) {
                if (viewModel.openFragment == true) {
                    Date currentTime = Calendar.getInstance().getTime();

                    viewModel.saveHistory(new HistoryItem(rssInfo.getTitle(), binding.textUrlSearch.getText().toString(), currentTime.getTime(), rssInfo.getLogo()));
                    viewModel.openFragment = false;
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

    private ArrayAdapter<String> getUrlRss(Context context) {

        String[] addresses = {"https://thanhnien.vn/video/thoi-su.rss","https://tinhte.vn/rss","https://vnexpress.net/rss/the-thao.rss", "https://vnexpress.net/rss/du-lich.rss", "https://vnexpress.net/rss/y-kien.rss"  };
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addresses);
    }
}