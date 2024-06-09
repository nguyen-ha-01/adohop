package com.apolom.aodoshop.fragments.login_signup.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.login_signup.data.LoginRepository;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.repo.UserDataRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.SetOptions;

import java.util.concurrent.CompletableFuture;


public class LoginViewModel extends ViewModel {


    private UserDataRepository repository;
    private SharedPreferencesManager _share;
    private MutableLiveData<Boolean> dataSavedLiveData = new MutableLiveData<>(false);


    private MutableLiveData<UserData> userLiveData;
    private MutableLiveData<String> errorLiveData;

    LoginViewModel(Context ctx) {
        repository =new UserDataRepository((Application) ctx.getApplicationContext());

        userLiveData = new MutableLiveData<>();
        _share = new SharedPreferencesManager(ctx);
        errorLiveData = new MutableLiveData<>();
    }

    LiveData<UserData> getUserState() {
        return userLiveData;
    }


    public void login(String msv, String password) {
        try {
            CompletableFuture<UserData> data =  CompletableFuture.supplyAsync(()->{return repository.getUserByMsv(msv);});
            _share.saveUserData(data.get());
            userLiveData.setValue(data.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}