package com.apolom.aodoshop.fragments.login_signup.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.databinding.ActivityLoginBinding;
import com.apolom.aodoshop.fragments.ui.login.SignupActivity;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.models.db.UserDao;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new LoginViewModel(this.getApplicationContext());

        final EditText msv = binding.msvLogin;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button signup = binding.signup;

        loginViewModel.getUserState().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(@Nullable UserData data) {
                if(data != null){
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("uid",data.msv);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"user is null",Toast.LENGTH_SHORT);
                }
            }
        });
        Context c = this;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, SignupActivity.class);
                c.startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(msv.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }


}