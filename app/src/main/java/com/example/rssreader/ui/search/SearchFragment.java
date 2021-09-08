package com.example.rssreader.ui.search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rssreader.R;
import com.example.rssreader.ReadRssActivity;
import com.example.rssreader.data.RssInfo;
import com.example.rssreader.databinding.FragmentSearchBinding;
import com.example.rssreader.ui.history.HistoryItem;

import java.net.URL;
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
                String url = improveUrl((binding.textUrlSearch.getText().toString()));
                if (binding.textUrlSearch.getText().length() == 0) showDialog();
                else if (checkUrl(url)) {
                    viewModel.loadRssItems(url);
                    viewModel.openFragment = true;
                    Intent intent = new Intent(getActivity(), ReadRssActivity.class);
                    startActivity(intent);
                }
            }
        });

        viewModel.getRssInfo().observe(getViewLifecycleOwner(), new Observer<RssInfo>() {
            @Override
            public void onChanged(RssInfo rssInfo) {
                if (viewModel.openFragment == true) {
                    Date currentTime = Calendar.getInstance().getTime();
                    viewModel.saveHistory(new HistoryItem(rssInfo.getTitle(), improveUrl(binding.textUrlSearch.getText().toString()), currentTime.getTime(), rssInfo.getLogo()));
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

        String[] addresses = {"thanhnien.vn/video/thoi-su.rss",
                "tinhte.vn/rss",
                "vnexpress.net/rss/the-thao.rss",
                "vnexpress.net/rss/du-lich.rss",
                "vnexpress.net/rss/y-kien.rss",
                "nguoiduatin.vn/rss/goc-nhin-luat-gia.rss",
                "nguoiduatin.vn/rss/ho-so-dieu-tra.rss","eva.vn/rss/rss.php/131","thanhnien.vn/rss/the-gioi/goc-nhin.rss",
                "thanhnien.vn/rss/van-hoa/ha-noi-thanh-pho-toi-yeu.rss",

        };
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addresses);
    }

    private boolean checkUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String improveUrl(String url) {
        String result = url;
        if (!url.contains("https://"))
            result = "https://" + url;
        if (result.charAt(result.length()-1) == '/')
            result = result.substring(0,result.length()-1);
        return result;
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
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}