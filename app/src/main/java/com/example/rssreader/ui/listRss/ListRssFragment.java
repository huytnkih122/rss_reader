package com.example.rssreader.ui.listRss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rssreader.R;
import com.example.rssreader.data.RssInfo;
import com.example.rssreader.data.RssItem;
import com.example.rssreader.databinding.FragmentListRssBinding;
import com.example.rssreader.ui.history.HistoryItem;

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
        binding = FragmentListRssBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel.getRssInfo().observe(getViewLifecycleOwner(), new Observer<RssInfo>() {
            @Override
            public void onChanged(RssInfo rssInfo) {
                if (rssInfo!=null){
                    ListRssAdapter adapter = new ListRssAdapter(viewModel.getRssInfo().getValue().getRssItems(), new ItemRssClickListener() {
                        @Override
                        public void OnClick(RssItem item) {
                            Bundle bundle = new Bundle();
                            bundle.putString("link", item.getLink());
                            Navigation.findNavController(getView()).navigate(R.id.action_listRssFragment_to_webViewFragment, bundle);
                        }
                    });
                    binding.listRssItem.setAdapter(adapter);
                    binding.listRssItem.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.titleRss.setText(viewModel.getRssInfo().getValue().getTitle());
                }
            }
        });

        binding.titleRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return root;
    }


}