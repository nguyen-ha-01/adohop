package com.apolom.aodoshop.fragments.thue;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.ThueTicketAdapter;
import com.apolom.aodoshop.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ThueFragment extends Fragment {

    private ThueViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<Order> data; ThueTicketAdapter adapter;

    public static ThueFragment newInstance() {
        return new ThueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ThueViewModel(this.getContext());
        data = new ArrayList<>();

        View view =  inflater.inflate(R.layout.fragment_thue, container, false);
        recyclerView = view.findViewById(R.id.frag_thue_recycle);
        adapter = new ThueTicketAdapter(requireContext(), data, new Call<Order>() {
            @Override
            public void onPick(Order e) {
                mViewModel.remove(e, new Call<Order>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onPick(Order e) {
                        data.removeIf(d->{
                            if (d.id.equals(e.id))return true;
                            else return false;
                        });
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.loadTicket();

        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Order> orders) {
                try{
                data.clear();
                data.addAll(orders);
                adapter.notifyDataSetChanged();
                }catch (Exception e){}
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
                    mViewModel.loadTicket();
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        };

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