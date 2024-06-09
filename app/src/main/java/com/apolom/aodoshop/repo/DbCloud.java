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



}
