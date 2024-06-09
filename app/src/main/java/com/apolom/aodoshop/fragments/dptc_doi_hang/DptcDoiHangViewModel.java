package com.apolom.aodoshop.fragments.dptc_doi_hang;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;

import java.util.List;

public class DptcDoiHangViewModel extends ViewModel {
    private MutableLiveData<List<Order>> data = new MutableLiveData<>();

    private SharedPreferencesManager sharedPreferencesManager;
    private Context ctx;

    public LiveData<List<Order>> getData() {
        return data;
    }

    // TODO: Implement the ViewModel
    public DptcDoiHangViewModel(Context ctx) {
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        this.ctx = ctx;

        repository = new OrderRepository((Application) ctx.getApplicationContext());


    }

    OrderRepository repository;

    void loadTicket(Call<Order> call) {
        try {

            String msv = sharedPreferencesManager.getUID();
            List<Order> _data = repository.getAllOrders(msv, "doihang");
            data.setValue(_data);
            call.onPick(null);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void take(Order data) {
        try {
            repository.delete(data);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}



