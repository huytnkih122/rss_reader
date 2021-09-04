package com.example.rssreader.ui.listRss;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.databinding.FragmentHistoryBinding;
import com.example.rssreader.databinding.FragmentListRssBinding;
import com.example.rssreader.ui.search.SearchViewModel;

import java.util.List;

public class ListRssFragment extends Fragment {
    private FragmentListRssBinding binding;
    private ListRssViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListRssViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListRssBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListRssAdapter adapter  = new ListRssAdapter(viewModel.getRssItems().getValue(), new ItemClickListener() {
            @Override
            public void OnClick(RssItem item) {
                Bundle bundle = new Bundle();
                bundle.putString("link", item.link);
                Navigation.findNavController(getView()).navigate(R.id.action_listRssFragment_to_webViewFragment, bundle);
            }
        });
        binding.listRssItem.setAdapter(adapter);
        binding.listRssItem.setLayoutManager(new LinearLayoutManager(requireContext()));
        return root;
    }
}