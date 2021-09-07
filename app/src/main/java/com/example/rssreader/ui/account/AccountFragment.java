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
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                Toast.makeText(getContext(), "CREATED", Toast.LENGTH_LONG).show();
                UpdateUI();
            }
        });

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UpdateUI();
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.signOut();
                UpdateUI();
            }
        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void UpdateUI() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            binding.infoLayout.setVisibility(View.GONE);
            binding.blankLayout.setVisibility(View.VISIBLE);
        } else {
            binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
                Picasso.get().load(user.getPhotoUrl()).into(binding.avatar);
        }
    }
}