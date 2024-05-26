package com.apolom.aodoshop.fragments.thue;

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
        adapter = new ThueTicketAdapter(requireContext(), data, new Call<Order>() {
            @Override
            public void onPick(Order e) {
                Toast.makeText(getContext(),"click"+e.id,Toast.LENGTH_SHORT).show();

            }
        });
        View view =  inflater.inflate(R.layout.fragment_thue, container, false);
        recyclerView = view.findViewById(R.id.frag_thue_recycle);

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
}