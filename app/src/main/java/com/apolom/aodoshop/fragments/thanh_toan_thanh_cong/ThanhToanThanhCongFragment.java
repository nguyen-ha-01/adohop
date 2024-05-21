package com.apolom.aodoshop.fragments.thanh_toan_thanh_cong;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;

public class ThanhToanThanhCongFragment extends Fragment {

    private ThanhToanThanhCongViewModel mViewModel;

    public static ThanhToanThanhCongFragment newInstance() {
        return new ThanhToanThanhCongFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_toan_thanh_cong, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThanhToanThanhCongViewModel.class);
        // TODO: Use the ViewModel
    }

}