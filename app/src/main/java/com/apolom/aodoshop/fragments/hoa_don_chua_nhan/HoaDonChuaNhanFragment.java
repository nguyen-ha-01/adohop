package com.apolom.aodoshop.fragments.hoa_don_chua_nhan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;

public class HoaDonChuaNhanFragment extends Fragment {

    private HoaDonChuaNhanViewModel mViewModel;

    public static HoaDonChuaNhanFragment newInstance() {
        return new HoaDonChuaNhanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don_chua_nhan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HoaDonChuaNhanViewModel.class);
        // TODO: Use the ViewModel
    }

}