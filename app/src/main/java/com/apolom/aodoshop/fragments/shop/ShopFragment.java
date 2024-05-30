package com.apolom.aodoshop.fragments.shop;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.hoa_don_chua_nhan.HoaDonChuaNhanFragment;
import com.apolom.aodoshop.helper.ShopFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ShopFragment extends Fragment {

    private ShopViewModel mViewModel;
    ViewPager2 viewPager;
    ShopFragmentPagerAdapter adapter;
    TabLayout tabLayout;

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop, container, false);
         tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        adapter= new ShopFragmentPagerAdapter(this.requireActivity());

        viewPager.setAdapter(adapter);
        viewPager.canScrollHorizontally(2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Intent intent = new Intent("com.apolom.aodoshop.UPDATE");
                intent.putExtra("data", tab.getPosition());
                requireActivity().sendBroadcast(intent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Chưa nhận");
                            break;
                        case 1:
                            tab.setText("Đã Nhận");
                            break;
                        case 2:
                            tab.setText("Đổi");
                            break;
                        case 3:
                            tab.setText("Trả");
                            break;
                        case 4:
                            tab.setText("Thuê");
                            break;
                    }
                }).attach();
        return view;
    }
     public void reloadCurrentTab(int pos) {

         Log.e("reloadddd","calll");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        // TODO: Use the ViewModel
    }

}