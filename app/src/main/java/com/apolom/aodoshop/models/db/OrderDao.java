package com.apolom.aodoshop.models.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.apolom.aodoshop.models.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order user);

    @Query("SELECT * FROM Me_order")
    List<Order> getAllOrders();

    @Query("SELECT * FROM Me_order WHERE uid = :id and orderType = :type")
    List<Order> getOrderById(String id,String type);



}
