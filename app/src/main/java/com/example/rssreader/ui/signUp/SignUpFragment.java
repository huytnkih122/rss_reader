package com.example.rssreader.ui.signUp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private SignUpViewModel viewModel;

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        viewModel.getMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    getActivity().finish();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.passwordSignUp.getText().toString().equals(binding.confirmPasswordSignUp.getText().toString())
                        && binding.passwordSignUp.getText().toString().length() > 5
                        && binding.confirmPasswordSignUp.getText().toString().length() > 5
                        && isEmailValid(binding.usernameSignUp.getText().toString()))
                    viewModel.register(binding.usernameSignUp.getText().toString(), binding.passwordSignUp.getText().toString());

                else showDialog();
            }
        });

        binding.backButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
        return root;
    }

    private void showDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(getResources().getString(R.string.wrong_info))
                .setMessage(getResources().getString(R.string.re_enter))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        binding.passwordSignUp.setText("");
                        binding.confirmPasswordSignUp.setText("");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

