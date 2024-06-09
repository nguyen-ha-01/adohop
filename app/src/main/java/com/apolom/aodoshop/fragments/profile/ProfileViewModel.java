package com.apolom.aodoshop.fragments.profile;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.repo.UserDataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {

    UserDataRepository repository;
    private SharedPreferencesManager sharedPreferencesManager ;
    public MutableLiveData<UserData> userData = new MutableLiveData<>();
    ProfileViewModel(Context ctx){
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        repository =  new UserDataRepository((Application) ctx.getApplicationContext());
    }
    public void logout() {
        sharedPreferencesManager.clearUID();

    }
    public  void  getUserName(){
        try{
            UserData data = sharedPreferencesManager.getUserData();
            userData.setValue(data);
        }
        catch (Exception e){     userData.setValue(null);}
    }
    // TODO: Implement the ViewModel
}