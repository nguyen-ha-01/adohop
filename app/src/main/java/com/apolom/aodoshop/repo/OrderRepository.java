package com.apolom.aodoshop.repo;
import android.app.Application;
import androidx.lifecycle.LiveData;

import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.models.db.AppDatabase;
import com.apolom.aodoshop.models.db.OrderDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {

    private OrderDao orderDao;
    private LiveData<List<Order>> allOrders;
    private ExecutorService executor;

    public OrderRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        orderDao = database.orderDao();


        executor = Executors.newFixedThreadPool(2);
    }

    public List<Order> getAllOrders(String msv,String type) {
        CompletableFuture<List<Order> > data =  CompletableFuture.supplyAsync(()->{return orderDao.getOrderById(msv,type);});
        try {
            return data.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Order order) {
        executor.execute(() -> orderDao.insert(order));
    }

    public void update(Order order) {
        executor.execute(() -> orderDao.update(order));
    }

    public void delete(Order order) {
        executor.execute(() -> orderDao.delete(order));
    }


}
