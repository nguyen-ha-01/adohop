package com.apolom.aodoshop.repo;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_UID = "uid";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUID(String uid) {
        editor.putString(KEY_UID, uid);
        editor.apply();
        editor.commit();
        sharedPreferences.notify();

    }

    public String getUID() {
        // TODO: 5/30/2024 check login
        // "TgKvV9StXvXLYawM4RChCyDlXdU2"
        return sharedPreferences.getString(KEY_UID,null);
    }

    public void clearUID() {
        editor.remove(KEY_UID);
        editor.apply();
    }
}
