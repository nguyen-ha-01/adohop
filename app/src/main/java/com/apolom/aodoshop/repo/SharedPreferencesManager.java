package com.apolom.aodoshop.repo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.apolom.aodoshop.models.UserData;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_UID = "uid";
    private static final String NAME = "NAME";
    private static final String MSV = "MSV";
    private static final String PASSWORD = "PASSWORD";
    private static final String MONEY = "MONEY";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUID(String uid) {
        editor.putString(KEY_UID, uid);
        editor.apply();
    }

    public void saveUserData(UserData userData) {
        editor.putString(NAME, userData.displayName);
        editor.putString(MSV, userData.msv);
        editor.putString(PASSWORD, userData.msv);
        editor.putLong(MONEY, userData.money);
        editor.apply();
    }

    public void setMoney(long m) {
        editor.putLong(MONEY, m);
        editor.apply();
    }

    public long getMoney() {
        return sharedPreferences.getLong(MONEY, 0L);
    }

    @Nullable
    public UserData getUserData() {
        try {
            String name = sharedPreferences.getString(NAME, "");
            String msv = sharedPreferences.getString(MSV, "");
            String pass = sharedPreferences.getString(PASSWORD, "");
            Long m = sharedPreferences.getLong(MONEY, 0L);
            return new UserData(msv, name, pass, m);
        } catch (Exception e) {
            return null;
        }

    }

    public String getUID() {
        return sharedPreferences.getString(MSV, "");
    }

    public void clearUID() {
        editor.remove(KEY_UID);
        editor.apply();
    }
}
