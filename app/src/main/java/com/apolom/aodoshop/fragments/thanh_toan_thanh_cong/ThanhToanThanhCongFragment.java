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
import android.widget.TextView;

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
    private TextView id;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_thanh_toan_thanh_cong, container, false);
        id = view.findViewById(R.id.item_ticket_ma_nhan_do);
        try {
            if(getArguments().isEmpty()){}
            else {
                String _id = getArguments().getString("id","");
                if(_id!= ""){id.setText(_id);}
            }
        }catch (Exception e){e.printStackTrace();}
        return view;
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