package com.apolom.aodoshop.repo;

import static com.google.firebase.firestore.util.Util.autoId;

import android.util.Log;

import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.models.UserData;
import com.google.firebase.FirebaseException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.util.Util;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class DbCloud {
    private FirebaseFirestore db ;
    public DbCloud(String init){
        db = FirebaseFirestore.getInstance();
    }
    public static  String _product = "PRODUCT";
    public static String user = "users";
    public static  String _quoc_phong = "quoc_phong";
    public static String _the_chat = "the_chat";
    public  static String _order = "order";
    public static String _thue_first_tail = "TQS";
    public static String _mua_first_tail = "MTC";
    public static String _da_nhan = "da_nhan";
    public static String _doi = "doi";
    public static String _tra = "tra";



    public Boolean createUserOnDB(String userId, String pass, String name, String email) {
        UserData userData = new UserData(userId,email,name,pass,0L);
        try {
            db.collection("users").document(userId)
                    .set(userData.toMap(), SetOptions.merge());
            return true;
        }catch (Exception e){return false;}
    }
    public void initProduct() {
            List<Long> size =   new ArrayList<>();
        for(int i=30;i<=40;i++)size.add(Long.valueOf(i));
        Product p2 = new Product("đồng phục thể chất",size,90000,"đồng","nam","mua ngay");
        Product p1 = new Product("đồng phục quốc phòng",size,1000,"đồng/ngày","nam","thuê ngay");
        db.collection(_quoc_phong).add(p1.toMap());
        db.collection(_the_chat).document().set(p2.toMap(),SetOptions.merge());
    }
    public void addMoney(String uid, int money){
        Map<String, Object> map = new HashMap<>();
        db.collection("users").document(uid).get().addOnCompleteListener(
                e->{
                    long m = (long)e.getResult().get("money");
                    map.put("money",money+m);
                    db.collection("users").document(uid)
                            .set(map,SetOptions.merge());
                }
        );

    }
    public UserData getUserInfo(String uid){
        try {
            Map<String ,Object> map = db.collection(user).document(uid)
                    .get()
                    .addOnCompleteListener(e->{}).getResult().getData();
            return UserData.fromMap(uid,map);
        }catch (Exception e){
            return null;
        }
    }
}
