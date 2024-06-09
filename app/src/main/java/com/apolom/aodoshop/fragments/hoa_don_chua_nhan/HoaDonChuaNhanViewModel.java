package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChuaNhanViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    OrderRepository repository;

    HoaDonChuaNhanViewModel(Context ctx) {
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        this.ctx = ctx;
        repository = new OrderRepository((Application) ctx.getApplicationContext());

    }

    private MutableLiveData<List<Order>> data = new MutableLiveData<>();
    private Context ctx;

    public LiveData<List<Order>> getData() {
        return data;
    }


    SharedPreferencesManager sharedPreferencesManager;

    void loadTicket(Call<Order> call) {
        try {
            String msv = sharedPreferencesManager.getUID();
            List<Order> _data = repository.getAllOrders(msv, "chuanhan");
            data.setValue(_data);
            call.onPick(null);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void xoa_order(Order data) {
        try{
            data.orderType = "danhan";
            repository.update(data);
        }catch  (Exception e) {

            e.printStackTrace();
        }
    }


}