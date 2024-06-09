package com.apolom.aodoshop.fragments.dptc_da_nhan;

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

public class DptcDaNhanViewModel extends ViewModel {
    OrderRepository repository;


    public DptcDaNhanViewModel(Context _ctx){
        ctx = _ctx;
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
        repository = new OrderRepository((Application) ctx.getApplicationContext());

    }
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Order>> data = new MutableLiveData<>();
    private Context ctx ;


    public LiveData<List<Order>> getData() {
        return data;
    }

      SharedPreferencesManager sharedPreferencesManager;

    void loadTicket(Call<Order> call) {
        try {

            String msv = sharedPreferencesManager.getUID();
            List<Order> _data = repository.getAllOrders(msv, "danhan");
            data.setValue(_data);
            call.onPick(null);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    void tra_hang_call(Order data){
        try {
            data.orderType = "trahang";
            repository.update(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void doi_size_call(Order data,Call<Boolean> onFinish){

        try {
            data.orderType = "doihang";
            repository.update(data);
            onFinish.onPick(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    MutableLiveData<Integer> newSize = new MutableLiveData<>(0);

}