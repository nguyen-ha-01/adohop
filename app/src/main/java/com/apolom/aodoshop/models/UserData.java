package com.apolom.aodoshop.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;
@Entity(tableName = "users")
public class UserData {
    @PrimaryKey()
    @NonNull
    public String msv;
    public String password, displayName;
    public long money;
    public UserData( String msv, String displayName, String password, Long money) {

        this.password = password;
        this.msv = msv;
        this.displayName = displayName;
        this.money = money;
    }
    public Map<String, Object> toMap() {
       Map<String, Object> user = new HashMap<>();
        user.put("password", password);
        user.put("msv", msv);
        user.put("display_name", displayName);
        user.put("money",money);
        return user;
    }
    static  public UserData fromMap(Map<String, Object> user) {
        try{UserData u = new UserData( (String) user.get("msv"), (String) user.get("display_name"), (String) user.get("password"), (long) user.get("money"));
            return u;}catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
