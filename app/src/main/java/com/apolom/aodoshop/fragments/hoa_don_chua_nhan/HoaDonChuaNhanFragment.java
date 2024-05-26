package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.HoaDonChuaNhanAdapter;
import com.apolom.aodoshop.helper.SizeGridAdapter;
import com.apolom.aodoshop.models.Order;

import java.util.ArrayList;
import java.util.List;

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
            @Override
            public void onPick(Order e) {
                //todo:set size here
                Toast.makeText(getContext(),"click"+e,Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.loadTicket();

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
}