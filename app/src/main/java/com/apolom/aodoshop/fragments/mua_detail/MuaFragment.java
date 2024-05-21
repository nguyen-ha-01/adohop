package com.apolom.aodoshop.fragments.mua_detail;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.commons.app_bar.DetailAppBar;
import com.apolom.aodoshop.helper.SizeGridAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MuaFragment extends Fragment {

    private MuaViewModel mViewModel;
//    private Button _minus, _add;


    public static MuaFragment newInstance() {
        return new MuaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);

        }
        return inflater.inflate(R.layout.fragment_mua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Context c = getContext();
        super.onViewCreated(view, savedInstanceState);
        NavController  controller = Navigation.findNavController(view);

        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add(""+i);
        }
        DetailAppBar  appBar = view.findViewById(R.id.fragment_mua_app_bar);
        appBar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
                    mainView.setVisibility(View.VISIBLE);

                }
                controller.navigate(R.id.action_muaFragment_to_fragment_home);
            }
        },"");
        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.fragment_mua_sizes);
        int numberOfColumns = 7; // Number of columns in the grid
        recyclerView.setLayoutManager(new GridLayoutManager(c, numberOfColumns));
        SizeGridAdapter adapter = new SizeGridAdapter(this.getContext(), data);
        recyclerView.setAdapter(adapter);
        Button thanh_toan = view.findViewById(R.id.fragment_mua_btn_mua) ;
        thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_muaFragment_to_thanhToanThanhCongFragment);
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MuaViewModel.class);
        // TODO: Use the ViewModel
    }


}