package com.example.rssreader.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.rssreader.LoginActivity;
import com.example.rssreader.databinding.FragmentAccountBinding;
import com.example.rssreader.ui.signUp.SignUpViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private AccountViewModel viewModel;
    private FragmentAccountBinding binding;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        viewModel.getMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Toast.makeText(getContext(), "CREATED", Toast.LENGTH_LONG).show();

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        viewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                Toast.makeText(getContext(), "User CHanged State", Toast.LENGTH_LONG).show();
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.signOut();
            }
        });
        if (user== null) binding.button.setVisibility(View.VISIBLE);
        else {
            binding.textView3.setText(user.getDisplayName());
            binding.textNotifications.setText(user.getEmail());

        }
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
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