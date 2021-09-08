package com.example.rssreader.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rssreader.LoginActivity;
import com.example.rssreader.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private AccountViewModel viewModel;
    private FragmentAccountBinding binding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                UpdateUI();
            }
        });

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UpdateUI();
        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.signOut();
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateUI();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void UpdateUI() {
        FirebaseUser user = viewModel.getUserMutableLiveData().getValue();
        if (user == null) {
            binding.infoLayout.setVisibility(View.GONE);
            binding.blackLayout.setVisibility(View.VISIBLE);
        } else {
            binding.infoLayout.setVisibility(View.VISIBLE);
            binding.blackLayout.setVisibility(View.GONE);
            binding.name.setText(user.getDisplayName());
            binding.email.setText(user.getEmail());
            if (user.getPhotoUrl() != null)
                Picasso.get().load(user.getPhotoUrl()).into(binding.avatar);
        }
    }
}