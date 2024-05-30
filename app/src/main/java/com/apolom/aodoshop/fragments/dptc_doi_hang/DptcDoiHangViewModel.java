package com.apolom.aodoshop.fragments.dptc_doi_hang;

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

public class DptcDoiHangViewModel extends ViewModel {
    private MutableLiveData<List<Order>> data = new MutableLiveData<>();

    private SharedPreferencesManager spm ;
    private Context ctx;
    private FirebaseFirestore cl;
    public LiveData<List<Order>> getData() {
        return data;
    }
    // TODO: Implement the ViewModel
    public DptcDoiHangViewModel(Context ctx){
        spm = new SharedPreferencesManager(ctx);
        this.ctx= ctx;
        cl =  FirebaseFirestore.getInstance();
    }
    void take(Order data){
        cl.collection(DbCloud._doi).whereEqualTo("id",data.id).get().addOnCompleteListener(r->{
            if(!r.getResult().getDocuments().isEmpty()){
                r.getResult().getDocuments().forEach(documentSnapshot ->{
                    String docId = documentSnapshot.getId();
                    cl.collection(DbCloud._doi).document(docId).delete().addOnCompleteListener(e->{
                        loadTicket(new Call<Order>() {
                            @Override
                            public void onPick(Order e) {

                            }
                        });
                    });
                });
            }
        });
    }
    public void scanToNhanDo(Order e, Call<Order> o) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.item_barcode, null,false);
        ImageView imageView = root.findViewById(R.id.imageView);
        QrService service = new QrService();
        try {
            Bitmap b = service.generateBarcode(e.id);
            imageView.setImageBitmap(b);
        }catch (Exception ex){ex.printStackTrace();}
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(root);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //remove and add on da nhan
                take(e);
                o.onPick(e);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
    }
    void loadTicket(Call<Order> call) {
            try {
                String uid = spm.getUID();
                //todo:
                cl.collection(DbCloud._doi)
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

}