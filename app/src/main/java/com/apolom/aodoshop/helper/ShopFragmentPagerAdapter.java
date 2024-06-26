package com.apolom.aodoshop.helper;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.dptc_da_nhan.DptcDaNhanFragment;
import com.apolom.aodoshop.fragments.dptc_doi_hang.DptcDoiHangFragment;
import com.apolom.aodoshop.fragments.dptc_tra_hang.DptcTraHangFragment;
import com.apolom.aodoshop.fragments.hoa_don_chua_nhan.HoaDonChuaNhanFragment;
import com.apolom.aodoshop.fragments.thue.ThueFragment;

public class ShopFragmentPagerAdapter extends FragmentStateAdapter {


    public ShopFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        switch (i) {
            case 1:
                return new DptcDaNhanFragment();
            case 2:
                return new DptcDoiHangFragment();
            case 3:
                return new DptcTraHangFragment();
            case 4:
                return new ThueFragment();
            case 0:
            default:
                return new HoaDonChuaNhanFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 5;
    }
}
