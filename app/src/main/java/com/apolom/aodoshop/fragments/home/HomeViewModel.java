package com.apolom.aodoshop.fragments.home;

import static com.apolom.aodoshop.repo.DbCloud._product;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private final MutableLiveData<Product> quocPhongLiveData = new MutableLiveData<>();
    private final MutableLiveData<Product> theChatLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserData> user = new MutableLiveData<>();
    public LiveData<UserData> getUser(){return user; }
    void initUser(Context ctx){
        try{
            SharedPreferencesManager share  = new SharedPreferencesManager(ctx);

            String finalUid = auth.getCurrentUser().getUid();
            if(finalUid!= null) {
                FirebaseFirestore.getInstance().collection("users").document(finalUid)
                        .get()
                        .addOnCompleteListener(e -> {
                            try {
                                UserData d = UserData.fromMap(finalUid, e.getResult().getData());
                                user.setValue(d);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        });
            }else {
                String uid = share.getUID();
                FirebaseFirestore.getInstance().collection("users").document(uid)
                        .get()
                        .addOnCompleteListener(e -> {
                            try {
                                UserData d = UserData.fromMap(finalUid, e.getResult().getData());
                                user.setValue(d);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        });
            }


        }catch (Exception e){
            Log.e("initUser",e.toString());}
    }
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<Product> getQuocPhong() {
        return quocPhongLiveData;
    }
    public LiveData<Product> getTheChat() {
        return theChatLiveData;
    }

    public void fetchProducts() {
        try{
            firestore.collection(DbCloud._quoc_phong).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        if( querySnapshot.size()==1) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                Product product = Product.generate(document.getData());
                                quocPhongLiveData.setValue(product);

                            }
                        }
                    }

                } else {
                    // Handle the error
                    quocPhongLiveData.setValue(null);
                }
            });
            firestore.collection(DbCloud._the_chat).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        if( querySnapshot.size()==1) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                Product product = Product.generate(document.getData());
                                theChatLiveData.setValue(product);

                            }
                        }
                    }

                } else {
                    // Handle the error
                    theChatLiveData.setValue(null);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("can  load");
        }

    }
}