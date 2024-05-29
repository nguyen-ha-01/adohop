package com.apolom.aodoshop.fragments.dptc_da_nhan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.DptcDaNhanAdapter;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.service.QrService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class DptcDaNhanViewModel extends ViewModel {
    public DptcDaNhanViewModel(Context _ctx){
        ctx = _ctx;
        spm = new SharedPreferencesManager(ctx);
    }
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Order>> data = new MutableLiveData<>();
    private Context ctx ;


    public LiveData<List<Order>> getData() {
        return data;
    }

    FirebaseFirestore cl = FirebaseFirestore.getInstance();
    SharedPreferencesManager spm;

    void loadTicket(Call<Order> call) {
        try {
            String uid = spm.getUID();
            //getdata
            cl.collection(DbCloud._da_nhan)
                    .whereEqualTo("uid", uid)
                    .whereEqualTo("orderType", "mua")
                    .get().addOnCompleteListener(d -> {
                        if(d.getResult().size()>0){
                            List<Order> _data = new ArrayList<>();
                            for (DocumentSnapshot i : d.getResult()){
                                Order _order = Order.generate( i.getData());
                                _data.add(_order);
                            }
                            data.setValue(_data);
                            Log.e("dptc","load");
                            call.onPick(null);
                        }else{
                            data.setValue(new ArrayList<>());
                        }
                    }).addOnFailureListener(f->{
                        Log.e("data","data is null");
                        data.setValue(null);
                    });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    void tra_hang_call(Order data){
        cl.collection(DbCloud._da_nhan).whereEqualTo("id",data.id).get().addOnCompleteListener(r->{
            if(!r.getResult().getDocuments().isEmpty()){
                r.getResult().getDocuments().forEach(documentSnapshot ->{
                    String docId = documentSnapshot.getReference().getId();
                    cl.collection(DbCloud._da_nhan).document(docId).delete().addOnCompleteListener(e->{
                        cl.collection(DbCloud._tra).add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                loadTicket(new Call<Order>() {
                                    @Override
                                    public void onPick(Order e) {

                                    }
                                });
                            }
                        });
                    });
                });
            }
        });
        loadTicket(new Call() {
            @Override
            public void onPick(Object e) {

            }
        });
    }
    void doi_size_call(Order data,Call<Boolean> onFinish){
        cl.collection(DbCloud._da_nhan).whereEqualTo("id",data.id).get().addOnCompleteListener(r->{
            if(!r.getResult().getDocuments().isEmpty()){
                r.getResult().getDocuments().forEach(documentSnapshot ->{
                    String docId = documentSnapshot.getReference().getId();
                    cl.collection(DbCloud._da_nhan).document(docId).delete().addOnCompleteListener(e->{
                        cl.collection(DbCloud._doi).add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                loadTicket(new Call<Order>() {
                                    @Override
                                    public void onPick(Order e) {

                                    }
                                });
                                onFinish.onPick((task.isSuccessful()));
                            }
                        });
                    });
                });
            }
        });
//        loadTicket(new Call() {
//            @Override
//            public void onPick(Object e) {
//
//            }
//        });
    }
    public void tra_hang(  Order e, Call<Order> onSuccess){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_xac_nhan_tra_hang, null,false);
        MaterialButton huy,tra;
        huy = root.findViewById(R.id.dialog_xac_nhan_tra_hang_huy_btn);
        tra = root.findViewById(R.id.dialog_xac_nhan_tra_hang_tra_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(root);

        AlertDialog dialog = builder.create();
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tra_hang_call(e);
                onSuccess.onPick(e);
                dialog.dismiss();

            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
    }
    MutableLiveData<Integer> newSize = new MutableLiveData<>(0);
    public void doi_size(Order e, LifecycleOwner owner){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_doi_size, null,false);
        TextView _new_size = root.findViewById(R.id.dialog_size);
        Button minus= root.findViewById(R.id.dialog_size_decrease);
        Button  add = root.findViewById(R.id.dialog_size_increase);
        MaterialButton huy = root.findViewById(R.id.dialog_size_huy_btn);
        MaterialButton doi = root.findViewById(R.id.dialog_size_btn_doi);
        newSize.setValue(Integer.valueOf(e.size));
        newSize.observe(owner, new Observer<Integer>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(Integer integer) {
                _new_size.setText(String.format("%d",integer));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newSize.getValue()<44){
                newSize.setValue(1 + newSize.getValue());}
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newSize.getValue()>30) {
                    newSize.setValue(newSize.getValue()-1);
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(root);
        AlertDialog dialog = builder.create();

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doi_size_call(e, new Call<Boolean>() {
                    @Override
                    public void onPick(Boolean e) {
                        dialog.dismiss();
                    }
                });

            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
    }
}