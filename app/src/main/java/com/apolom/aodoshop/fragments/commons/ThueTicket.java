package com.apolom.aodoshop.fragments.commons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apolom.aodoshop.R;

public class ThueTicket extends FrameLayout {
    View root;
    private TextView _date,_maNhanDo,_nameProduct,_count,_size, _daThanhToan;
    private Button _doi,_tra;

    public ThueTicket(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ThueTicket(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThueTicket(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context ctx) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.item_thue_ticket, this, true);


        _date = root.findViewById(R.id.item_da_nhan_date);
        _maNhanDo = root.findViewById(R.id.item_da_nhan_ma_nhan_do);
        _nameProduct = root.findViewById(R.id.item_da_nhan_name_product);
        _count = root.findViewById(R.id.item_da_nhan_product_count);
        _size = root.findViewById(R.id.item_da_nhan_size);
        _daThanhToan = root.findViewById(R.id.item_da_nhan_da_thanh_toan);
        _doi = root.findViewById(R.id.item_da_nhan_ticket_doi_btn);
        _tra = root.findViewById(R.id.item_da_nhan_ticket_tra_btn);

    }

}