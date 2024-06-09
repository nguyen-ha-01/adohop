package com.apolom.aodoshop.fragments.home;

import static com.apolom.aodoshop.repo.DbCloud._product;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Product> quocPhongLiveData = new MutableLiveData<>();
    private final MutableLiveData<Product> theChatLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserData> user = new MutableLiveData<>();
    SharedPreferencesManager share;

    public LiveData<UserData> getUser() {
        return user;
    }

    void initUser(Context ctx) {
        try {
            share = new SharedPreferencesManager(ctx);
            UserData u = share.getUserData();
            if(u!= null){
                user.setValue(u);
            }

        } catch (Exception e) {
            Log.e("initUser", e.toString());
        }
    }


    public LiveData<Product> getQuocPhong() {
        return quocPhongLiveData;
    }

    public LiveData<Product> getTheChat() {
        return theChatLiveData;
    }

    public void fetchProducts() {
        try {
            List<Long> size = new ArrayList<>();
            for (int i = 30; i < 43; i++) {
                size.add((long) i);
            }
            Product product = new Product("đồng phục quốc phòng", size, 1000l, "đồng/ngày", "", "thuê ngay");
            quocPhongLiveData.setValue(product);
            Product product2 = new Product("đồng phục thể chất", size, 90000l, "đồng", "", "mua ngay");
            theChatLiveData.setValue(product2);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("can  load");
        }

    }
}