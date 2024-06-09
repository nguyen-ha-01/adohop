package com.apolom.aodoshop.fragments.nap_tien;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.repo.UserDataRepository;

public class NapTienViewModel extends ViewModel {
    UserDataRepository repository;
    SharedPreferencesManager _share;
    public NapTienViewModel(Context applicationContext) {
        repository =new UserDataRepository((Application) applicationContext.getApplicationContext());
        _share = new SharedPreferencesManager(applicationContext);
    }
     public boolean nap(long m){
        try{
            UserData u = _share.getUserData() ;
            u.money += m;
            _share.saveUserData(u);
            repository.update(u);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
     }

}