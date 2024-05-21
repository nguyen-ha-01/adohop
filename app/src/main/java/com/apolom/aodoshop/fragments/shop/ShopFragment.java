package com.apolom.aodoshop.fragments.shop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.helper.ShopFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ShopFragment extends Fragment {

    private ShopViewModel mViewModel;

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);

        ShopFragmentPagerAdapter adapter= new ShopFragmentPagerAdapter(this.getActivity());
        viewPager.setAdapter(adapter);
        viewPager.canScrollHorizontally(2);

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        // TODO: Use the ViewModel
    }

}