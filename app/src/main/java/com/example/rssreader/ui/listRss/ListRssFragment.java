package com.example.rssreader.ui.listRss;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
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
        binding.progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (viewModel.getRssInfo().getValue() == null ) {
                    binding.progressBar.setVisibility(View.GONE);
                    showDialog();
                }
            }
        }, 1000);
        viewModel.getRssInfo().observe(getViewLifecycleOwner(), new Observer<RssInfo>() {
            @Override
            public void onChanged(RssInfo rssInfo) {
                if (rssInfo!=null){
                    binding.progressBar.setVisibility(View.GONE);
                    ListRssAdapter adapter = new ListRssAdapter(viewModel.getRssInfo().getValue().getRssItems(), new ItemRssClickListener() {
                        @Override
                        public void OnClick(RssItem item) {
                            Bundle bundle = new Bundle();
                            bundle.putString("link", item.getLink());
                            Navigation.findNavController(getView()).navigate(R.id.action_listRssFragment_to_webViewFragment, bundle);
                        }
                    });
                    binding.listRss.setAdapter(adapter);
                    binding.listRss.setLayoutManager(new LinearLayoutManager(requireContext()));
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

    private void showDialog(){
        new AlertDialog.Builder(requireContext())
                .setTitle(getResources().getString(R.string.message_title_url))
                .setMessage(getResources().getString(R.string.message_url))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroy() {
        viewModel.resetListRss();
        super.onDestroy();
    }
}