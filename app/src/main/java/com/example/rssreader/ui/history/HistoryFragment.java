package com.example.rssreader.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rssreader.R;
import com.example.rssreader.ReadRssActivity;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.databinding.FragmentHistoryBinding;
import com.example.rssreader.ui.listRss.ItemRssClickListener;
import com.example.rssreader.ui.listRss.ListRssAdapter;
import com.example.rssreader.ui.listRss.ListRssViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel viewModel;
    private FragmentHistoryBinding binding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        viewModel.loadData();

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel.getHistoryItems().observe(getViewLifecycleOwner(), new Observer<List<HistoryItem>>() {
            @Override
            public void onChanged(List<HistoryItem> historyItems) {
                if (historyItems!=null){
                    HistoryAdapter adapter = new HistoryAdapter(viewModel.getHistoryItems().getValue(), new HistoryItemClickListener() {
                        @Override
                        public void onClick(HistoryItem item) {
                          viewModel.loadRssItems(item.url);
                          HistoryItem newItems = item;
                            Date currentTime = Calendar.getInstance().getTime();
                          newItems.setDate(currentTime.getTime());
                          viewModel.saveHistory(newItems);
                            Intent intent = new Intent(getActivity(), ReadRssActivity.class);
                            startActivity(intent);
                        }
                    });
                    binding.listHistory.setAdapter(adapter);
                    binding.listHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
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