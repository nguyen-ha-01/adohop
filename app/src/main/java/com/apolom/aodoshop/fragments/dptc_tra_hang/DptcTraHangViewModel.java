package com.apolom.aodoshop.fragments.dptc_tra_hang;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DptcTraHangViewModel extends ViewModel {
    private MutableLiveData<List<Order>> data = new MutableLiveData<>();

    private SharedPreferencesManager sharedPreferencesManager;
    private Context ctx;

    public LiveData<List<Order>> getData() {
        return data;
    }
    // TODO: Implement the ViewModel
    public DptcTraHangViewModel(Context ctx){
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        this.ctx= ctx;

        this.ctx = ctx;

        repository = new OrderRepository((Application) ctx.getApplicationContext());

    }



    // TODO: Implement the ViewModel

    OrderRepository repository;

    void loadTicket(Call<Order> call) {
        try {

            String msv = sharedPreferencesManager.getUID();
            List<Order> _data = repository.getAllOrders(msv, "trahang");
            data.setValue(_data);
            call.onPick(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void take(Order data) {
        try {
            repository.delete(data);
            loadTicket(new Call<Order>() {
                           @Override
                           public void onPick(Order e) {

                           }
                       }
            );
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}