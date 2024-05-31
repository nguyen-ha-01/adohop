package com.apolom.aodoshop.fragments.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.login_signup.ui.login.LoginActivity;
import com.apolom.aodoshop.models.UserData;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private TextView _changePassword, _logout, username;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ProfileViewModel(getContext());
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _changePassword = view.findViewById(R.id.fragment_profile_change_password);
        _logout = view.findViewById(R.id.fragment_profile_logout);
        username = view.findViewById(R.id.fragment_profile_username);

        Context ctx = requireContext();
        mViewModel.userData.observe(getViewLifecycleOwner(), new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if(userData!= null){
                    username.setText(userData.email);
                }
            }
        });
        mViewModel.getUserName();
//        username.setText(mViewModel.getUserName());
        _logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.logout();
                Intent i = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(i);
            }
        });
        _changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });
    }
    private void showChangePasswordDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.change_password_dialog, null);

        // Get references to the EditText fields
        final EditText editTextCurrentPassword = dialogView.findViewById(R.id.profile__current_password);
        final EditText editTextNewPassword = dialogView.findViewById(R.id.profile__new_password);
        final EditText editTextConfirmNewPassword = dialogView.findViewById(R.id.profile__new_password_confirm);
        final MaterialButton _submit =  dialogView.findViewById(R.id.profile__submit_btn);


        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        AlertDialog dialog= builder.setView(dialogView)
//
//                .setPositiveButton("Change", (dialog, which) -> {
//                    // Get the input values
//                    String currentPassword = editTextCurrentPassword.getText().toString().trim();
//                    String newPassword = editTextNewPassword.getText().toString().trim();
//                    String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();
//
////                    // Validate the inputs
////                    if (TextUtils.isEmpty(currentPassword)) {
////                        Toast.makeText(this, "Please enter current password", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
////
////                    if (TextUtils.isEmpty(newPassword)) {
////                        Toast.makeText(this, "Please enter new password", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
////
////                    if (TextUtils.isEmpty(confirmNewPassword)) {
////                        Toast.makeText(this, "Please confirm new password", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
////
////                    if (!newPassword.equals(confirmNewPassword)) {
////                        Toast.makeText(this, "New password and confirmation do not match", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
////
////                    // Perform password change logic here (e.g., API call)
////                    Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())

                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }



}