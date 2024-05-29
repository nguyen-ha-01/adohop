package com.apolom.aodoshop.fragments.profile;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DbCloud db= new  DbCloud("");
    private SharedPreferencesManager sharedPreferencesManager ;
    ProfileViewModel(Context ctx){
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
    }
    public void logout() {
        sharedPreferencesManager.clearUID();
        auth.signOut();
    }
    public  String  getUserName(){
        try{return db.getUserInfo(sharedPreferencesManager.getUID()).displayName;}
        catch (Exception e){return null;}
    }
    // TODO: Implement the ViewModel
}