package com.apolom.aodoshop.fragments.commons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;


public class GiaoDichThanhCongItem extends Fragment {

    MaterialButton _hoan_thanh;
    public static com.apolom.aodoshop.fragments.thanh_toan_thanh_cong.ThanhToanThanhCongFragment newInstance() {
        return new com.apolom.aodoshop.fragments.thanh_toan_thanh_cong.ThanhToanThanhCongFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.giao_dich_thanh_cong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        _hoan_thanh = view.findViewById(R.id.item_thue_ticket_tra_btn);
        _hoan_thanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_giaoDichThanhCongItem_to_fragment_home);
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
                    mainView.setVisibility(View.VISIBLE);

                }
            }
        });
    }
}