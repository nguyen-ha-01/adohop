package com.apolom.aodoshop.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Serializable {
    public  String name ;
    public List<Long> size;
    public String thumb;
    public long price;
    public String type;
    public String sex;
    public Product(String name, List<Long> size, long price, String type , String sex, String thumb) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.sex = sex;
        this.thumb = thumb;
        this.type = type;
    }
    public  Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("size", size);
        map.put("price", price);
        map.put("type",type);
        map.put("thumb",thumb);
        map.put("sex", sex);
        return map;
    }
    public  static Product generate(Map<String,Object> o){
        return new Product((String) o.get("name"), (List<Long>) o.get("size"), (long) o.get("price"), (String) o.get("type"), (String) o.get("sex"), (String) o.get("thumb"));
    }


}
