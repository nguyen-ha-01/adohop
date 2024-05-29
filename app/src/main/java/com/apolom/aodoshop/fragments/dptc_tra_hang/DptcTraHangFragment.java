package com.apolom.aodoshop.fragments.dptc_tra_hang;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.dptc_doi_hang.DptcDoiHangViewModel;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.HoaDonChuaNhanAdapter;
import com.apolom.aodoshop.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DptcTraHangFragment extends Fragment {

    private DptcTraHangViewModel mViewModel;

    public static DptcTraHangFragment newInstance() {
        return new DptcTraHangFragment();
    }
    private RecyclerView recyclerView;
    List<Order> orders = new ArrayList<>();


    HoaDonChuaNhanAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new DptcTraHangViewModel(getContext());
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
}