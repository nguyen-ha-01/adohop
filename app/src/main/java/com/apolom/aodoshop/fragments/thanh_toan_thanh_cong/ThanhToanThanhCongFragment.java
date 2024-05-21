package com.apolom.aodoshop.fragments.thanh_toan_thanh_cong;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ThanhToanThanhCongFragment extends Fragment {

    private ThanhToanThanhCongViewModel mViewModel;

    public static ThanhToanThanhCongFragment newInstance() {
        return new ThanhToanThanhCongFragment();
    }
    private MaterialButton _next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_toan_thanh_cong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _next = view.findViewById(R.id.fragment_tttc_btn);

        NavController navController = Navigation.findNavController(view);


        _next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_thanhToanThanhCongFragment_to_fragment_home);
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
                    mainView.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThanhToanThanhCongViewModel.class);
        // TODO: Use the ViewModel
    }

}