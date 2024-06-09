package com.apolom.aodoshop.fragments.thue;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class ThueViewModel extends ViewModel {
    OrderRepository repository;
    ThueViewModel(Context ctx) {
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        repository = new OrderRepository((Application) ctx.getApplicationContext());
    }

    private MutableLiveData<List<Order>> data = new MutableLiveData<>(new ArrayList<Order>());
    public MutableLiveData<List<Order>> getData() {
        return data;
    }
    SharedPreferencesManager sharedPreferencesManager;
    void loadTicket() {
        try {
            String msv = sharedPreferencesManager.getUID();
            List<Order> _data =repository.getAllOrders(msv,  "thue");
            data.setValue(_data);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void remove(Order e,Call<Order> update) {
        try {
            repository.delete(e);
            update.onPick(e);
            loadTicket();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}