package com.apolom.aodoshop.fragments.login_signup.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.login_signup.data.LoginRepository;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.SetOptions;


public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private SharedPreferencesManager _share ;
    private MutableLiveData<Boolean> dataSavedLiveData=  new MutableLiveData<>(false);


    private MutableLiveData<String> userLiveData;
    private MutableLiveData<String> errorLiveData;

    LoginViewModel(Context ctx) {
        mAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        _share= new SharedPreferencesManager(ctx);
        try{
            String uid = _share.getUID();
            if (uid!= null)
            userLiveData.setValue(uid);
            else{
                userLiveData.setValue(mAuth.getCurrentUser().getUid());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        errorLiveData = new MutableLiveData<>();
    }

    LiveData<String> getUserState() {
        return userLiveData;
    }


    public void login(String email, String password) {
        try{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser u = mAuth.getCurrentUser();
                            userLiveData.setValue(u.getUid());
                            _share.saveUID(u.getUid());
                            Log.d("LOGIN","LOGINED WITH"+mAuth.getCurrentUser().getUid());
                        } else {
                            errorLiveData.setValue(task.getException().getMessage());
                        }
                    });
        }catch (Exception e){
            throw e;
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