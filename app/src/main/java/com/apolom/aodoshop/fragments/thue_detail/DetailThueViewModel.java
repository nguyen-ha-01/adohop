package com.apolom.aodoshop.fragments.thue_detail;

import android.icu.text.SimpleDateFormat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.repo.DbCloud;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetailThueViewModel extends ViewModel {
    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Integer> total_count = new MutableLiveData<>(1);
    private final MutableLiveData<Long> total_pay = new MutableLiveData<>(1L);
    private final MutableLiveData<String> sex = new MutableLiveData<>("nam");
    private final MutableLiveData<String> date_start = new MutableLiveData<>("");
    private final MutableLiveData<String> date_end = new MutableLiveData<>("");
    private final MutableLiveData<String> size = new MutableLiveData<>();

    public LiveData<String> getSize() {
        return size;
    }
    public void setSize(String s) {
        size.setValue(s);
    }



    public MutableLiveData<String> getDate_end() {
        return date_end;
    }

    public MutableLiveData<String> getDate_start() {
        return date_start;
    }
    public void setDate_start(String p){
        date_start.setValue(p);
    }
    public void setDate_end(String p){
        date_end.setValue(p);
    }


    public LiveData<String> getSex() {
        return sex;
    }
    public void setSex(String p){
        sex.setValue(p);
    }

    public LiveData<Integer> get_total() {
        return total_count;
    }
    public LiveData<Product> get_product() {
        return product;
    }
    public void setProduct(Product p){
        product.setValue(p);
    }
    public LiveData<Long> get_money_pay() {
        return total_pay;
    }
    public void setTotal_pay(long price,  int dayLong){
        total_pay.setValue(price*dayLong*get_total().getValue());
    }
    public void changeTotal(int count){
        int old = total_count.getValue();
        total_count.setValue(old+count);
    }
    public  int calculateDaysBetween(String dateStr1, String dateStr2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = dateFormat.parse(dateStr1);
            Date date2 = dateFormat.parse(dateStr2);

            // Tính sự khác biệt bằng mili giây
            long differenceInMillis = Math.abs(date2.getTime() - date1.getTime());

            // Chuyển đổi từ mili giây sang ngày
            long differenceInDays = differenceInMillis / (24 * 60 * 60 * 1000);
            return (int) differenceInDays;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Trả về -1 trong trường hợp có lỗi
        }
    }
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }
    void addOrder(String uid){
        try{
            String id = DbCloud._thue_first_tail+generateRandomId();
            Order order = new Order(product.getValue().name,total_pay.getValue(),total_count.getValue().longValue(),uid,id, size.getValue(), date_start.getValue(),date_end.getValue(),"thue");
            FirebaseFirestore f= FirebaseFirestore.getInstance();
            f.collection(DbCloud.user).document(uid).get().addOnCompleteListener(d->{
                long money = (long) d.getResult().get("money");
                if(money>= total_pay.getValue()){
                    f.collection(DbCloud._order).add(order);
                    Map<String,Object> data = new HashMap<>();
                    data.put("money",money- total_pay.getValue());
                    f.collection(DbCloud.user).document(uid).set(data, SetOptions.mergeFields("money"));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}