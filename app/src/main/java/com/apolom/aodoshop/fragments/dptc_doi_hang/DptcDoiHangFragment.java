package com.apolom.aodoshop.fragments.dptc_doi_hang;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
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
import android.widget.ImageView;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.hoa_don_chua_nhan.HoaDonChuaNhanFragment;
import com.apolom.aodoshop.fragments.hoa_don_chua_nhan.HoaDonChuaNhanViewModel;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.HoaDonChuaNhanAdapter;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.service.QrService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DptcDoiHangFragment extends Fragment {

    private DptcDoiHangViewModel mViewModel;

    public static DptcDoiHangFragment newInstance() {
        return new DptcDoiHangFragment();
    }
    private RecyclerView recyclerView;
    List<Order> orders = new ArrayList<>();


    HoaDonChuaNhanAdapter adapter;
    public void scanToNhanDo(Order e, Call<Order> o) {

        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.item_barcode, null,false);
        ImageView imageView = root.findViewById(R.id.imageView);
        QrService service = new QrService();
        try {
            Bitmap b = service.generateBarcode(e.id);
            imageView.setImageBitmap(b);
        }catch (Exception ex){ex.printStackTrace();}
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(root);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //remove and add on da nhan
                mViewModel.take(e);
                o.onPick(e);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.back_corner);
        dialog.show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new DptcDoiHangViewModel(getContext());
        adapter = new HoaDonChuaNhanAdapter(requireContext(),orders, new Call<Order>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onPick(Order e) {
                scanToNhanDo(e,e1 -> {

                    orders.removeIf(new Predicate<Order>() {
                        @Override
                        public boolean test(Order order) {
                            if(order.id.equals(e.id))return true;
                            return false;
                        }
                    });
                    adapter.notifyDataSetChanged();
                });
            }
        });
        mViewModel.loadTicket(new Call<Order>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onPick(Order e) {
                adapter.notifyDataSetChanged();
            }
        });
        View view = inflater.inflate(R.layout.fragment_dptc_doi_hang, container, false);
        recyclerView = view.findViewById(R.id.frag_doi_recycle);
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
                String data = intent.getStringExtra("data");
         try{

                    mViewModel.loadTicket(new Call<Order>() {
                        @Override
                        public void onPick(Order e) {

                        }
                    }); }catch (Exception e){
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