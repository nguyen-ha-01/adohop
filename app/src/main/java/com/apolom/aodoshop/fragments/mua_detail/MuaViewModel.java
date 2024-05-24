package com.apolom.aodoshop.fragments.mua_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.repo.DbCloud;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MuaViewModel extends ViewModel {

    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Integer> total = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> money = new MutableLiveData<>(1);
    private final MutableLiveData<String> size = new MutableLiveData<>();
    private final MutableLiveData<Long> total_pay = new MutableLiveData<>(1L);
    private final MutableLiveData<Integer> total_count = new MutableLiveData<>(1);
    public LiveData<Long> get_total_pay() {
        return total_pay;
    }
    public void updateTotalPay(){
        total_pay.setValue(get_product().getValue().price* get_total().getValue());
    }
    public LiveData<Integer> get_total() {
        return total;
    }
    public LiveData<Product> get_product() {
        return product;
    }
    public void setProduct(Product p){
        product.setValue(p);
    }
    public LiveData<Integer> get_money() {
        return money;
    }
    public void setMoney(int p){
        money.setValue(p);
    }
    public void changeTotal(int count){
        int old = total.getValue();
        total.setValue(old+count);
    }
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }
    void addOrder(String uid){
        try{
            String id = DbCloud._mua_first_tail+generateRandomId();
            Order order = new Order(product.getValue().name,total_pay.getValue(),total_count.getValue().longValue(),uid,id, size.getValue(),"","","mua");
            FirebaseFirestore f= FirebaseFirestore.getInstance();
            f.collection(DbCloud._order).add(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSize(String e) {
        size.setValue(e);
    }
}