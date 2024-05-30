package com.apolom.aodoshop.fragments.thue;

import static java.lang.System.in;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ThueViewModel extends ViewModel {
    ThueViewModel(Context ctx) {
        spm = new SharedPreferencesManager(ctx);
    }

    private MutableLiveData<List<Order>> data = new MutableLiveData<>(new ArrayList<Order>());

    public MutableLiveData<List<Order>> getData() {
        return data;
    }

    FirebaseFirestore cl = FirebaseFirestore.getInstance();
    SharedPreferencesManager spm;
    void loadTicket() {
        try {
            String uid = spm.getUID();
            //getdata
            cl.collection(DbCloud._order)
                    .whereEqualTo("uid", uid)
                    .whereEqualTo("orderType", "thue")
                    .get().addOnCompleteListener(d -> {
                        if(d.getResult().size()>0){
                            List<Order> _data = new ArrayList<>();
                            for (DocumentSnapshot i : d.getResult()){
                                Order _order = Order.generate( i.getData());
                                _data.add(_order);
                            }
                            data.setValue(_data);
                        }
                    }).addOnFailureListener(f->{
                        Log.e("data","data is null");
                        data.setValue(null);
                    });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void remove(Order e) {
        cl.collection(DbCloud._order).whereEqualTo("id",e.id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                 task.getResult().getDocuments().forEach(e->{
                    cl.collection(DbCloud._order).document(e.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadTicket();
                        }
                    });
                 });
            }
        });
    }
}