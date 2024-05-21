package com.apolom.aodoshop.fragments.dptc_da_nhan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;

public class DptcDaNhanFragment extends Fragment {

    private DptcDaNhanViewModel mViewModel;

    public static DptcDaNhanFragment newInstance() {
        return new DptcDaNhanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dptc_da_nhan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DptcDaNhanViewModel.class);
        // TODO: Use the ViewModel
    }

}