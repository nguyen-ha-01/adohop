package com.apolom.aodoshop.fragments.home;

import static java.lang.String.format;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.TextView;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.home.views.ItemHome;
import com.apolom.aodoshop.models.Product;
import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private SharedPreferencesManager share;
    private TextView name_tv,nap_tv,money_tv;
    ItemHome thue,mua;
    NavController navController;




    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mua = view.findViewById(R.id.fragment_home_mua);
        thue = view.findViewById(R.id.fragment_home_thue);
        name_tv = view.findViewById(R.id.fragment_home_username);
        money_tv = view.findViewById(R.id.fragment_home_money);
        nap_tv = view.findViewById(R.id.fragment_home_nap_tien);

        mViewModel.getQuocPhong().observe(this.getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product products) {
                updateQuocPhong(products);
            }
        });
        getMoneyInfo();
        mViewModel.initUser(this.requireContext());
        mViewModel.getTheChat().observe(this.getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product products) {
                updateTheChat(products);
            }
        });

        mViewModel.fetchProducts();

        return view;
    }

    void getMoneyInfo(){
        try{

            mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<UserData>() {
                @Override
                public void onChanged(UserData u) {
                    money_tv.setText(u.money+"");
                    name_tv.setText(u.displayName);
                }
            });

        }catch (Exception e){
            Log.e("user",e.toString());
        }
    }
    @SuppressLint("DefaultLocale")
    private void updateQuocPhong(Product p1) {
            thue.setText(p1.name, p1.thumb, format("%d%s", p1.price, p1.type));
            thue.addCallback(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle _thue_data = new Bundle();
                    _thue_data.putSerializable("product",p1);
                    _thue_data.putLong("money", mViewModel.getUser().getValue().money);
                    navController.navigate(R.id.action_fragment_home_to_detailThueFragment,_thue_data);
                }
            });

        }

    @SuppressLint("DefaultLocale")
    private void updateTheChat(Product p2) {
        mua.setText(p2.name, p2.thumb, String.format("%d%s", p2.price, p2.type));
        mua.setImageResource(R.drawable.img_1);

        mua.addCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle _mua_data = new Bundle();
                _mua_data.putSerializable("product",p2);
                _mua_data.putLong("money", mViewModel.getUser().getValue().money);
                navController.navigate(R.id.action_fragment_home_to_muaFragment,_mua_data);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


         navController = Navigation.findNavController(view);

         nap_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_fragment_home_to_napTienFragment);
            }
        });

    }



}