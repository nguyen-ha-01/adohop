package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.service.QrService;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChuaNhanViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    HoaDonChuaNhanViewModel(Context ctx) {
        spm = new SharedPreferencesManager(ctx);this.ctx=  ctx ;
    }

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
            cl.collection(DbCloud._order)
            .whereEqualTo("uid", uid)
            .whereEqualTo("orderType", "mua")
            .get().addOnCompleteListener(d -> {
                    if(d.getResult().size()>0){
                        List<Order> _data = new ArrayList<>();
                        for (DocumentSnapshot  i : d.getResult()){
                                Order _order = Order.generate( i.getData());
                                _data.add(_order);
                        }
                        data.setValue(_data);
                        call.onPick(null);
                    }
                    else{
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
    void xoa_order(Order data){
        cl.collection(DbCloud._order).whereEqualTo("id",data.id).get().addOnCompleteListener(r->{
            if(!r.getResult().getDocuments().isEmpty()){
                r.getResult().getDocuments().forEach(documentSnapshot ->{
                    String docId = documentSnapshot.getReference().getId();
                    cl.collection(DbCloud._order).document(docId).delete().addOnCompleteListener(e->{
                        //update lai data
                        loadTicket(new Call<Order>() {
                            @Override
                            public void onPick(Order e) {
                            }
                        });
                        cl.collection(DbCloud._da_nhan).add(data);
                    });
                });
            }
        });
    }

    public void scanToNhanDo(Order data, Call<Order> update ){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.item_barcode, null,false);
        ImageView imageView = root.findViewById(R.id.imageView);
        QrService service = new QrService();
        try {
            Bitmap b = service.generateBarcode(data.id);
            imageView.setImageBitmap(b);
        }catch (Exception e){e.printStackTrace();}
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(root);
        builder.setTitle("Scan");
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //remove and add on da nhan
                xoa_order(data);
                update.onPick(data);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}