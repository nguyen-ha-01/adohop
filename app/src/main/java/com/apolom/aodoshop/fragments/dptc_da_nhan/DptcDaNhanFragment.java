package com.apolom.aodoshop.fragments.dptc_da_nhan;

import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apolom.aodoshop.R;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.DptcDaNhanAdapter;

import com.apolom.aodoshop.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DptcDaNhanFragment extends Fragment {

    private DptcDaNhanViewModel mViewModel;
    private RecyclerView recyclerView;
    private  List<Order> orders;
    DptcDaNhanAdapter adapter;

    public static DptcDaNhanFragment newInstance() {
        return new DptcDaNhanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new DptcDaNhanViewModel(requireContext());
        orders = new ArrayList<>();
        View view =  inflater.inflate(R.layout.fragment_dptc_da_nhan, container, false);
        recyclerView = view.findViewById(R.id.frag_danhan_recycle);
        adapter = new DptcDaNhanAdapter(requireContext(), new Call<Order>() {
            @Override
            public void onPick(Order e) {
                //show size picker then remove it from da nhan then add to doi
                mViewModel.doi_size(e,getViewLifecycleOwner());
            }
        }, new Call<Order>() {
            @Override
            public void onPick(Order e) {
                mViewModel.tra_hang(e, new Call<Order>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onPick(Order e) {
                        orders.removeIf(new Predicate<Order>() {
                            @Override
                            public boolean test(Order order) {
                                if(order.id == e.id)return true;
                                return false;
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, orders);
        mViewModel.loadTicket(new Call<Order>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onPick(Order e) {
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Order> data_new) {
                orders.clear();
                orders.addAll(data_new);
                Log.e("dptc","reset");
                adapter.notifyDataSetChanged();
            }
        });

    }

    private BroadcastReceiver myBroadcastReceiver;

    @Override
    public void onResume() {
        super.onResume();
        // Initialize the BroadcastReceiver
        myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Update the UI with data from the broadcast
                String data = intent.getStringExtra("data");


                   try{
                       mViewModel.loadTicket(new Call<Order>() {
                           @Override
                           public void onPick(Order e) {

                           }
                       });
                   }catch (Exception e){
                       e.printStackTrace();
                   }

            }
        };

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter("com.apolom.aodoshop.UPDATE");
        requireActivity().registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(myBroadcastReceiver);
    }
}