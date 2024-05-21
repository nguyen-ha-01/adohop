package com.apolom.aodoshop.fragments.dptc_tra_hang;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;

public class DptcTraHangFragment extends Fragment {

    private DptcTraHangViewModel mViewModel;

    public static DptcTraHangFragment newInstance() {
        return new DptcTraHangFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dptc_tra_hang, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DptcTraHangViewModel.class);
        // TODO: Use the ViewModel
    }

}