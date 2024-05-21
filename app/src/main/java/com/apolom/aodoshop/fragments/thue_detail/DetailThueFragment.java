package com.apolom.aodoshop.fragments.thue_detail;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolom.aodoshop.R;

import java.util.Calendar;

public class DetailThueFragment extends Fragment {

    private DetailThueViewModel mViewModel;
    private ImageView _start_date;
    private TextView _start_date_show;
    private ImageView _end_date;
    private TextView _end_date_show;


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