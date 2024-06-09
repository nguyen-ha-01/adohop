package com.apolom.aodoshop.fragments.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;


import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.repo.UserDataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

public class SignupViewModel extends ViewModel {



    private SharedPreferencesManager _share;
    private MutableLiveData<Boolean> dataSavedLiveData = new MutableLiveData<>(false);
    private MutableLiveData<String> userLiveData;
    public MutableLiveData<String> errorLiveData;
    private Context ctx;
    private UserDataRepository repository;
    public SignupViewModel(Context ctx) {
        repository = new UserDataRepository((Application) ctx.getApplicationContext());


        userLiveData = new MutableLiveData<>();
        _share = new SharedPreferencesManager(ctx);
        this.ctx = ctx;
        try {
            _share.clearUID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        errorLiveData = new MutableLiveData<>();
    }

    LiveData<String> getUserState() {
        return userLiveData;
    }

    private Boolean createNewAccount(String name,String msv, String password) {
        UserData userData = new UserData(msv,name,password,0L);
        return  repository.insert(userData);

    }
    public void signup(String msv,String name, String password) {
        try {
            Boolean add = createNewAccount(name, msv, password);
            if(add)
            {
                _share.saveUserData(new UserData(msv,name,password,0L));
                userLiveData.setValue(msv);
            }
            else {
                errorLiveData.setValue("can't create account");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginDataChanged(String username, String password) {

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

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}