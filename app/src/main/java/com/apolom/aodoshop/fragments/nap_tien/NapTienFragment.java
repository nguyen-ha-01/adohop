
package com.apolom.aodoshop.fragments.nap_tien;

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

public class NapTienFragment extends Fragment {

    private NapTienViewModel mViewModel;
    NavController navController;
    private  Button _next;

    public static NapTienFragment newInstance() {
        return new NapTienFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);

        }
        return inflater.inflate(R.layout.fragment_nap_tien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        _next = view.findViewById(R.id.fragment_nap_next_btn);
        _next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_napTienFragment_to_giaoDichThanhCongItem);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NapTienViewModel.class);
        // TODO: Use the ViewModel
    }

}