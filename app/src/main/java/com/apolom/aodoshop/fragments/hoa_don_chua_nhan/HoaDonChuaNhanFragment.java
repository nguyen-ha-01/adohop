package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.HoaDonChuaNhanAdapter;
import com.apolom.aodoshop.helper.SizeGridAdapter;
import com.apolom.aodoshop.models.Order;

import java.util.List;

public class HoaDonChuaNhanFragment extends Fragment {

    private HoaDonChuaNhanViewModel mViewModel;
    private RecyclerView recyclerView;

    public static HoaDonChuaNhanFragment newInstance() {
        return new HoaDonChuaNhanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new HoaDonChuaNhanViewModel(getContext());
        mViewModel.loadTicket();
        View view = inflater.inflate(R.layout.fragment_hoa_don_chua_nhan, container, false);
        recyclerView = view.findViewById(R.id.frag_hdcn_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                try {
                    HoaDonChuaNhanAdapter adapter = new HoaDonChuaNhanAdapter(requireContext(),orders, new Call() {
                        @Override
                        public void onPick(String e) {
                            //todo:set size here

                        }
                    });
                    recyclerView.setAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}