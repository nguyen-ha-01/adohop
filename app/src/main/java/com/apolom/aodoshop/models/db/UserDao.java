package com.apolom.aodoshop.models.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.apolom.aodoshop.models.UserData;

import java.util.List;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserData user);

    @Update
    void update(UserData user);

    @Delete
    void delete(UserData user);

    @Query("SELECT * FROM users")
    List<UserData> getAllUsers();

    @Query("SELECT * FROM users WHERE msv = :id")
    UserData getUserById(String id);


}
