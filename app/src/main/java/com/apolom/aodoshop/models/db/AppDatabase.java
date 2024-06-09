package com.apolom.aodoshop.models.db;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.models.UserData;

@Database(entities = {UserData.class , Order.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();

    public  abstract  OrderDao orderDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database_v2")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
