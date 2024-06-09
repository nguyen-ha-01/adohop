package com.apolom.aodoshop.fragments.mua_detail;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.OrderRepository;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.apolom.aodoshop.repo.UserDataRepository;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class MuaViewModel extends ViewModel {
    OrderRepository repository;
    UserDataRepository userDataRepository;
    public MuaViewModel(Context context) {
        sharedPreferencesManager = new SharedPreferencesManager(context);
        repository = new OrderRepository((Application) context.getApplicationContext());
        userDataRepository = new UserDataRepository((Application) context.getApplicationContext());
    }

    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Integer> total = new MutableLiveData<>(1);
    public final MutableLiveData<String> idOrder = new MutableLiveData<>("");
    private final MutableLiveData<String> size = new MutableLiveData<>();
    private final MutableLiveData<Long> total_pay = new MutableLiveData<>(1L);
    private final MutableLiveData<Integer> total_count = new MutableLiveData<>(1);

    public LiveData<Long> get_total_pay() {
        return total_pay;
    }

    SharedPreferencesManager sharedPreferencesManager;

    public void updateTotalPay() {
        total_pay.setValue(get_product().getValue().price * get_total().getValue());
    }

    public LiveData<Integer> get_total() {
        return total;
    }

    public LiveData<Product> get_product() {
        return product;
    }

    public void setProduct(Product p) {
        product.setValue(p);
    }

    public void changeTotal(int count) {
        int old = total.getValue();
        total.setValue(old + count);
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public void addOrder(Call<String> onError) {

        try {
            long oldMoney = sharedPreferencesManager.getMoney();
            String uid = sharedPreferencesManager.getUID();
            if (oldMoney >= total_pay.getValue()) {
                Product p = product.getValue();
                Order order = new Order(p.name, total_pay.getValue(), Long.valueOf(total.getValue()), uid, generateRandomId(), size.getValue(), "", "", "chuanhan");

                repository.insert(order);
                thanh_toan(total_pay.getValue());
                idOrder.setValue(order.id);


            }else {
                onError.onPick("");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setSize(String e) {
        size.setValue(e);
    }
    public boolean thanh_toan(long m){
        try{
            UserData u = sharedPreferencesManager.getUserData() ;
            u.money += m;
            sharedPreferencesManager.saveUserData(u);
            userDataRepository.update(u);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}