package com.apolom.aodoshop.models;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    public String productName;
    public long price;
    public long quantity;
    public String uid,id;
    public String startDate;
    public String endDate;
    public String orderType;
    public String size;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Order(String productName, long price, long quantity, String uid, String id,String size, String startDate, String endDate, String orderType) {
        this.productName = productName;
        this.size = size;
        this.price = price;
        this.id= id;
        this.quantity = quantity;
        this.uid = uid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderType = orderType;
    }


    // Chuyển đổi đối tượng Order thành chuỗi
    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", uid='" + uid + '\'' +
                ", startDate=" + dateFormat.format(startDate) +
                ", endDate=" + dateFormat.format(endDate) +
                ", orderType='" + orderType + '\'' +
                '}';
    }

    // Chuyển đổi đối tượng Order thành HashMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productName);
        map.put("price", price);
        map.put("quantity", quantity);
        map.put("uid", uid);
        map.put("id", id);
        map.put("size", size);

        map.put("startDate", dateFormat.format(startDate));
        map.put("endDate", dateFormat.format(endDate));
        map.put("orderType", orderType);
        return map;
    }

    // Tạo đối tượng Order từ HashMap
    public static Order generate(Map<String, Object> map) {
        try {
            String productName = (String) map.get("productName");
            long price = (long) map.get("price");
            long quantity = (long) map.get("quantity");
            String uid = (String) map.get("uid");
            String id = (String) map.get("id");
            String size = (String) map.get("size");
            String startDate = (String) map.get("startDate");
            String endDate = (String) map.get("endDate");
            String orderType = (String) map.get("orderType");

            return new Order(productName, price, quantity, uid, id,size,startDate, endDate, orderType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

