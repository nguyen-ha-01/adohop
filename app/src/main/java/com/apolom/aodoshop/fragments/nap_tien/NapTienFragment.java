
package com.apolom.aodoshop.fragments.nap_tien;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NapTienFragment extends Fragment {
    private SharedPreferencesManager _share;

    private NapTienViewModel mViewModel;
    NavController navController;
    private  Button _next;
    private EditText _money;
    private DbCloud _db = new DbCloud("");
    public static NapTienFragment newInstance() {
        return new NapTienFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();
        mViewModel = new NapTienViewModel(mainActivity.getApplicationContext());
        _share = new SharedPreferencesManager(getActivity());
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);
        }

        View view = inflater.inflate(R.layout.fragment_nap_tien, container, false);
        _money =view.findViewById(R.id.fragment_nap_tien_money);
        _next = view.findViewById(R.id.fragment_nap_next_btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        _next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long m;
                    m = Long.valueOf(_money.getText().toString());
                    boolean e = mViewModel .nap(m);
                    if(e) {
                        navController.navigate(R.id.action_napTienFragment_to_giaoDichThanhCongItem);
                    }
                    else {
                        throw new Exception("drop");
                    }
                }catch (Exception e){ Log.e("uid",""+e.toString());
                    Toast.makeText(requireContext(),"drop money",Toast.LENGTH_SHORT);
                }


            }
        });
    }




}