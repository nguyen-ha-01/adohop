package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

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
import com.apolom.aodoshop.fragments.shop.ShopFragment;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.HoaDonChuaNhanAdapter;
import com.apolom.aodoshop.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class HoaDonChuaNhanFragment extends Fragment {

    private HoaDonChuaNhanViewModel mViewModel;
    private RecyclerView recyclerView;
    List<Order> orders = new ArrayList<>();
    HoaDonChuaNhanAdapter adapter;

    public static HoaDonChuaNhanFragment newInstance() {
        return new HoaDonChuaNhanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new HoaDonChuaNhanViewModel(getContext());
        adapter = new HoaDonChuaNhanAdapter(requireContext(),orders, new Call<Order>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onPick(Order e) {

                mViewModel.scanToNhanDo(e,e1 -> {
                    orders.removeIf(new Predicate<Order>() {
                        @Override
                        public boolean test(Order order) {
                            if(order.id.equals(e.id))return true;
                            return false;
                        }
                    });
                    Log.e("recyyyy","reloaded");
                    adapter.notifyDataSetChanged();
                });
            }
        });
        mViewModel.loadTicket(new Call() {
            @Override
            public void onPick(Object e) {
                adapter.notifyDataSetChanged();
            }
        });

        View view = inflater.inflate(R.layout.fragment_hoa_don_chua_nhan, container, false);
        recyclerView = view.findViewById(R.id.frag_hdcn_recycle);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Order> _orders) {
                try {
                    orders.clear();
                    orders.addAll( _orders);
                    adapter.notifyDataSetChanged();



                }catch (Exception e){
                    e.printStackTrace();
                }

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
        requireActivity().registerReceiver(myBroadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(myBroadcastReceiver);
    }
}