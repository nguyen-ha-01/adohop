package com.apolom.aodoshop.fragments.home;

import static com.apolom.aodoshop.R.id.fragment_home;
import static com.apolom.aodoshop.R.id.fragment_profile;
import static com.apolom.aodoshop.R.id.fragment_shop;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.home.views.ItemHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private TextView name_tv,nap_tv,money_tv;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Context ctx  = this.requireContext();
        MainActivity act  = (MainActivity) this.getActivity();

        NavController navController = Navigation.findNavController(view);
        ItemHome thue = view.findViewById(R.id.fragment_home_thue);
        thue.setText("Aos quan su", "thue ngay", "tu 1000d");
        thue.addCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragment_home_to_detailThueFragment);

            }
        });
        ItemHome mua = view.findViewById(R.id.fragment_home_mua);
        mua.setText("Aos quan ", "mua ngay", "100000vnd");
        mua.addCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragment_home_to_muaFragment);
            }
        });
        name_tv = view.findViewById(R.id.fragment_home_username);
        money_tv = view.findViewById(R.id.fragment_home_money);
        nap_tv = view.findViewById(R.id.fragment_home_nap_tien);
        nap_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragment_home_to_napTienFragment);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}