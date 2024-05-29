package com.apolom.aodoshop.models;


import java.util.HashMap;
import java.util.Map;

public class UserData {
    private String uid;
    public String email,password, displayName;
    public long money;
    public UserData(String uid,String email, String displayName, String password,  Long money) {
        this.uid = uid;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
        this.money = money;
    }
    public Map<String, Object> toMap() {
       Map<String, Object> user = new HashMap<>();
        user.put("password", password);
        user.put("email", email);
        user.put("display_name", displayName);
        user.put("money",money);
        return user;
    }
    static  public UserData fromMap(String uid, Map<String, Object> user) {
        try{UserData u = new UserData(uid, (String) user.get("email"), (String) user.get("display_name"), (String) user.get("password"), (long) user.get("money"));
            return u;}catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
