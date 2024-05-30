package com.apolom.aodoshop.fragments.mua_detail;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.commons.app_bar.DetailAppBar;
import com.apolom.aodoshop.helper.Call;
import com.apolom.aodoshop.helper.SizeGridAdapter;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MuaFragment extends Fragment {

    private MuaViewModel mViewModel;
    private Button _minus, _add;
    Button thanh_toan;
    RecyclerView recyclerView;
    TextView name, price,total,count;
    NavController controller;
    DetailAppBar appBar;
    long _price = 0;

    public static MuaFragment newInstance() {
        return new MuaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new MuaViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);
        }
        View view = inflater.inflate(R.layout.fragment_mua, container, false);
        thanh_toan = view.findViewById(R.id.fragment_mua_btn_mua);
        name = view.findViewById(R.id.fragment_mua_name);
        price = view.findViewById(R.id.fragment_mua_price);
        total = view.findViewById(R.id.fragment_mua_total);
        recyclerView = view.findViewById(R.id.fragment_mua_sizes);
        _minus = view.findViewById(R.id.fragment_mua_decrease);
        _add =view.findViewById(R.id.fragment_mua_increase);
        count = view.findViewById(R.id.fragment_mua_so_luong);
        appBar = view.findViewById(R.id.fragment_mua_app_bar);
        try {
            assert getArguments() != null;
            if (!getArguments().isEmpty()) {
                Product p = (Product) getArguments().getSerializable("product");
                mViewModel.setProduct(p);
                name.setText(p.name);
                price.setText(String.format("%d%s", p.price,p.type));
                Log.e("list", p.size.size() + " size");
                int numberOfColumns = 7; // Number of columns in the grid
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
                SizeGridAdapter adapter = new SizeGridAdapter(this.getContext(), p.size, new Call<String>() {
                    @Override
                    public void onPick(String e) {
                        //todo:set size here
                        mViewModel.setSize(e);
                    }
                });
                recyclerView.setAdapter(adapter);
                long _m = getArguments().getLong("money");

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
                }, _m+"vnd");

            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "error load arg", Toast.LENGTH_SHORT);
        }
        _minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if(mViewModel.get_total().getValue()>=2)
                mViewModel.changeTotal(-1);
            }
        });

        _add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.get_total().getValue()<=4)
                    mViewModel.changeTotal(1);

            }
        });

        mViewModel.get_total_pay().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                total.setText(integer+"vnd");
            }
        });
        mViewModel.get_total().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aLong) {
                mViewModel.updateTotalPay();
                count.setText(String.format("%d", aLong));
                total.setText(String.format("%d%s",aLong*mViewModel.get_product().getValue().price,"vnd"));
            }
        });


        return view;
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = Navigation.findNavController(view);

        thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try {
                SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(requireContext());
                String idOrder= mViewModel.addOrder(sharedPreferencesManager.getUID());

                long money = getArguments().getLong("money");
                if(money>Long.getLong(total.getText().toString())){
                    Bundle b = new Bundle();
                    b.putString("id", idOrder);
                    controller.navigate(R.id.action_muaFragment_to_thanhToanThanhCongFragment, b);
                }else {
                    Toast.makeText(requireContext(),"vui long nap them tien vao tai khoan",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        });

    }



}