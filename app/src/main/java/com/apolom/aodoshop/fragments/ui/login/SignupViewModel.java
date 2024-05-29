package com.apolom.aodoshop.fragments.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;


import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SignupViewModel extends ViewModel {


    private FirebaseAuth mAuth;
    private FirebaseFirestore cl;
    private SharedPreferencesManager _share;
    private MutableLiveData<Boolean> dataSavedLiveData = new MutableLiveData<>(false);

    private MutableLiveData<String> userLiveData;
    private MutableLiveData<String> errorLiveData;
    private Context ctx;

    public SignupViewModel(Context ctx) {
        cl = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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

    private void checkIfEmailExists(final String email, final String password) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                if (task.isSuccessful()) {
                    List<String> signInMethods = task.getResult().getSignInMethods();
                    if (signInMethods != null && signInMethods.isEmpty()) {
                        // Email does not exist, create new account
                        createNewAccount(email, password);
                    } else {
                        // Email already exists
                        Toast.makeText(ctx, "This email is already registered.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("eee", "Error checking if email exists", task.getException());
                    Toast.makeText(ctx, "Error checking if email exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createNewAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String uid = task.getResult().getUser().getUid();
                        _share.saveUID(uid);
                        DbCloud cloud = new DbCloud("");
                        cloud.createUserOnDB(uid,password,email,email);
                    }
                });
    }


    public void signup(String username, String password) {
        try {
            checkIfEmailExists(username, password);

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