package com.apolom.aodoshop.fragments.thue_detail;

import static java.lang.String.*;

import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.Calendar;

public class DetailThueFragment extends Fragment {

    private DetailThueViewModel mViewModel;
    private ImageView _start_date;
    private TextView _start_date_show,name;
    private ImageView _end_date;
    private TextView _end_date_show,total_price,price;
    private Button _thanh_toan,minus,add;
    private DetailAppBar bar;
    private TextView _total_count,nam,nu;
    private String money;
    private RecyclerView sizes;
    private NavController navController;

    public static DetailThueFragment newInstance() {
        return new DetailThueFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new DetailThueViewModel();
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mViewModel.setDate_start(day+"/"+month+"/"+year);
        mViewModel.setDate_end(day+"/"+month+"/"+year);

        View view =  inflater.inflate(R.layout.fragment_detail_thue, container, false);
        _start_date = view.findViewById(R.id.fragment_thue_detail_date_icon_start);
        name = view.findViewById(R.id.fragment_thue_detail_name);
        price = view.findViewById(R.id.fragment_thue_detail_price);
        _start_date_show = view.findViewById(R.id.fragment_thue_detail_date_start_show);
        total_price = view.findViewById(R.id.fragment_thue_detail_total_price);
        bar = view.findViewById(R.id.fragment_thue_detail_app_bar);
        _end_date = view.findViewById(R.id.fragment_thue_detail_date_icon_end);
        _end_date_show = view.findViewById(R.id.fragment_thue_detail_date_end_show);
        _thanh_toan = view.findViewById(R.id.fragment_thue_detail_btn_thanh_toan);
        minus = view.findViewById(R.id.fragment_thue_decrease);
        add = view.findViewById(R.id.fragment_thue_increase);
        sizes = view.findViewById(R.id.fragment_detail_thue_sizes);
        _total_count  = view.findViewById(R.id.fragment_thue_detail_so_luong);
        nam = view.findViewById(R.id.fragment_detail_thue_select_nam);
        nu = view. findViewById(R.id.fragment_detail_thue_select_nu);
        _start_date_show.setText(day+"/"+month+"/"+year);
        _end_date_show.setText(day+"/"+month+"/"+year);

        try {
            assert getArguments() != null;
            if (!getArguments().isEmpty()) {
                Product p = (Product) getArguments().getSerializable("product");
                if(p!=null){
                    name.setText(p.name );
                    price.setText(format("%d%s", p.price,p.type));
                    int numberOfColumns = 7; // Number of columns in the grid
                    sizes.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
                    SizeGridAdapter adapter = new SizeGridAdapter(this.getContext(), p.size, new Call<String>() {
                        @Override
                        public void onPick(String e) {
                            mViewModel.setSize(e);
                        }
                    });
                    sizes.setAdapter(adapter);
                    mViewModel.setProduct(p);
                }

                long _m = getArguments().getLong("money");

                bar.init(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if (mainActivity != null) {
                            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
                            mainView.setVisibility(View.VISIBLE);
                        }
                        navController.navigate(R.id.action_muaFragment_to_fragment_home);
                    }
                }, _m+"vnd--");


            }}catch (Exception e){
            Log.e("thue",e.toString());
        }
        return view;
    }
    void setBackground(TextView frame, boolean isSelected, Context ctx){
        if (isSelected)frame.setBackground(ctx.getDrawable(R.drawable.back_out_line_on));
        else{
            frame.setBackground(ctx.getDrawable(R.drawable.back_btn_outline));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);
        }
        mViewModel.getSex().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s == "nam"){
                    setBackground(nam, true, requireActivity() );
                    setBackground(nu, false, requireActivity() );
                }
                if(s == "nu"){
                    setBackground(nu, true, requireActivity() );
                    setBackground(nam, false, requireActivity() );
                }
            }
        });
        nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setSex("nam");
            }
        });
        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.setSex("nu");
            }
        });
        _start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickStartDate();
            }
        });
        _end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateEndPicker();
            }
        });
        mViewModel.getDate_start().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int day = mViewModel.calculateDaysBetween(s,mViewModel.getDate_end().getValue());
                mViewModel.setTotal_pay(mViewModel.get_product().getValue().price, day);
            }
        });

        mViewModel.getDate_end().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int day = mViewModel.calculateDaysBetween(mViewModel.getDate_start().getValue(),s);
                mViewModel.setTotal_pay(mViewModel.get_product().getValue().price, day);
            }
        });
        mViewModel.get_total().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int day = mViewModel.calculateDaysBetween(mViewModel.getDate_start().getValue(),mViewModel.getDate_end().getValue());
                mViewModel.setTotal_pay(mViewModel.get_product().getValue().price, day);
            }
        });
        mViewModel.get_money_pay().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                total_price.setText(aLong+" vnd");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(mViewModel.get_total().getValue()>=2)
                        mViewModel.changeTotal(-1);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.get_total().getValue()<=4)
                    mViewModel.changeTotal(+1);
            }
        });
        mViewModel.get_total().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                _total_count.setText(integer.toString());
                //
            }
        });


        _thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferencesManager   share = new SharedPreferencesManager(requireContext()) ;

                    long money = getArguments().getLong("money");
                    if(money>Long.getLong(total_price.getText().toString())){
                        String _id = mViewModel.addOrder(share.getUID());
                        Bundle b = new Bundle();
                        b.putString("id", _id);
                        navController.navigate(R.id.action_detailThueFragment_to_thanhToanThanhCongFragment,b);
                    }else {
                        Toast.makeText(requireActivity(),"vui long nap them tien vao tai khoan",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(requireContext(),"something work on order",Toast.LENGTH_SHORT);
                }

            }
        });
    }

    private void pickStartDate() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    _start_date_show.setText(selectedDate);
                    mViewModel.setDate_start(selectedDate);
                }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }
    private void dateEndPicker() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    // Handle the selected date
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    _end_date_show.setText(selectedDate);
                    mViewModel.setDate_end(selectedDate);
                }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

}