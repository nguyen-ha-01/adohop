package com.apolom.aodoshop.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.models.db.AppDatabase;
import com.apolom.aodoshop.models.db.UserDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.app.Application;
import java.util.List;


public class UserDataRepository {

    private UserDao userDataDao;
    private ExecutorService executor;
    public UserDataRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDataDao = database.userDao();
        executor = Executors.newFixedThreadPool(2);
    }
    public UserData getUserByMsv(String Msv){
        return userDataDao.getUserById(Msv);
    }
    public boolean insert(UserData userData) {
        try{
            executor.execute(() -> userDataDao.insert(userData));
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public void update(UserData userData) {
        executor.execute(() -> userDataDao.update(userData));
    }
    public void delete(UserData userData) {
        executor.execute(() -> userDataDao.delete(userData));
    }

}