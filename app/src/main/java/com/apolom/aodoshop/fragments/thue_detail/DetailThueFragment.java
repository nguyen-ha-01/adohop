package com.apolom.aodoshop.fragments.thue_detail;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolom.aodoshop.MainActivity;
import com.apolom.aodoshop.R;
import com.apolom.aodoshop.fragments.commons.app_bar.DetailAppBar;
import com.apolom.aodoshop.helper.SizeGridAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailThueFragment extends Fragment {

    private DetailThueViewModel mViewModel;
    private ImageView _start_date;
    private TextView _start_date_show;
    private ImageView _end_date;
    private TextView _end_date_show;
    private Button _thanh_toan;
    private DetailAppBar bar;
    private String money;
    private NavController navController;

    public static DetailThueFragment newInstance() {
        return new DetailThueFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_thue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        money = "1000000 vnd";
        navController = Navigation.findNavController(view);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
            mainView.setVisibility(View.INVISIBLE);
        }
        bar = view.findViewById(R.id.fragment_thue_detail_app_bar);
        bar.init(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_detailThueFragment_to_fragment_home);
                if (mainActivity != null) {
                    BottomNavigationView mainView = mainActivity.findViewById(R.id.nav_view);
                    mainView.setVisibility(View.VISIBLE);
                }
            }
        }, money);
        _start_date = view.findViewById(R.id.fragment_thue_detail_date_icon_start);
        _start_date_show = view.findViewById(R.id.fragment_thue_detail_date_start_show);
        _start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(_start_date_show);
            }
        });
        _end_date = view.findViewById(R.id.fragment_thue_detail_date_icon_end);
        _end_date_show = view.findViewById(R.id.fragment_thue_detail_date_end_show);
        _end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(_end_date_show);
            }
        });

        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("" + i);
        }
        RecyclerView sizes = view.findViewById(R.id.fragment_detail_thue_sizes);
        sizes.setLayoutManager(new GridLayoutManager(getContext(), 7));
        SizeGridAdapter adapter = new SizeGridAdapter(this.getContext(), data);
        sizes.setAdapter(adapter);
        _thanh_toan = view.findViewById(R.id.fragment_thue_detail_btn_thanh_toan);

        _thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_detailThueFragment_to_thanhToanThanhCongFragment);
            }
        });
    }

    private void showDatePickerDialog(TextView show) {
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
                    show.setText(selectedDate);
                }, year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailThueViewModel.class);
        // TODO: Use the ViewModel
    }

}