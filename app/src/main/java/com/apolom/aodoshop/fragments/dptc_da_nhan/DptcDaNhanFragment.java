package com.apolom.aodoshop.fragments.dptc_da_nhan;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.R;

import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.DptcDaNhanAdapter;

import com.apolom.aodoshop.models.Order;
import com.google.android.material.button.MaterialButton;

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
                doi_size(e,getViewLifecycleOwner());
            }
        }, new Call<Order>() {
            @Override
            public void onPick(Order e) {
                tra_hang(e, new Call<Order>() {
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
    public void doi_size(Order e, LifecycleOwner owner){
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_doi_size, null,false);
        TextView _new_size = root.findViewById(R.id.dialog_size);
        Button minus= root.findViewById(R.id.dialog_size_decrease);
        Button  add = root.findViewById(R.id.dialog_size_increase);
        MaterialButton huy = root.findViewById(R.id.dialog_size_huy_btn);
        MaterialButton doi = root.findViewById(R.id.dialog_size_btn_doi);
        try{
            mViewModel.newSize.setValue(Integer.valueOf(e.size));
        }catch (Exception exception){
            exception.printStackTrace();
        }
        mViewModel.newSize.observe(owner, new Observer<Integer>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(Integer integer) {
                _new_size.setText(String.format("%d",integer));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.newSize.getValue()<44){
                    mViewModel.newSize.setValue(1 + mViewModel.newSize.getValue());}
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.newSize.getValue()>30) {
                    mViewModel.newSize.setValue(mViewModel.newSize.getValue()-1);
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(root);
        AlertDialog dialog = builder.create();

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.size = mViewModel.newSize.getValue().toString();
                mViewModel.doi_size_call(e, new Call<Boolean>() {
                    @Override
                    public void onPick(Boolean e) {
                        dialog.dismiss();
                        mViewModel.loadTicket(new Call<Order>() {
                            @Override
                            public void onPick(Order e) {

                            }
                        });

                    }
                });

            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
    }
    public void tra_hang(  Order e, Call<Order> onSuccess){
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_xac_nhan_tra_hang, null,false);
        MaterialButton huy,tra;
        huy = root.findViewById(R.id.dialog_xac_nhan_tra_hang_huy_btn);
        tra = root.findViewById(R.id.dialog_xac_nhan_tra_hang_tra_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(root);

        AlertDialog dialog = builder.create();
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.tra_hang_call(e);
                onSuccess.onPick(e);
                dialog.dismiss();

            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
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