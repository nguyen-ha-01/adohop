package com.apolom.aodoshop.fragments.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel signupViewModel;
    private ActivitySignupBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signupViewModel = new SignupViewModel(this.getApplicationContext());
        Context c = this;

        final EditText usernameEditText = binding.username;
        final  EditText msv = binding.msv;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.signupBtn;



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupViewModel.signup(msv.getText().toString(),usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        signupViewModel.getUserState().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try{
                    if(!s.isEmpty()){
                        Intent intent = new Intent(c, MainActivity.class);
                        c.startActivity(intent);
                    }else {
                        Toast.makeText(c,"have error when create account",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        signupViewModel.errorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(c,"s", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}