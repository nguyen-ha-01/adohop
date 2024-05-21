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

public class DaNhanTicket extends FrameLayout {
    View root;
    private TextView _ngayThue,_maNhanDo, _ngayHetHan,_count,_size, _nameProduct;
    private Button _tra;

    public DaNhanTicket(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DaNhanTicket(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DaNhanTicket(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context ctx) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.item_da_nhan_ticket, this, true);


        _ngayThue = root.findViewById(R.id.item_thue_ngay_thue);
        _maNhanDo = root.findViewById(R.id.item_thue_ma_nhan_do);
        _ngayHetHan = root.findViewById(R.id.item_thue_ngay_het_han);
        _count = root.findViewById(R.id.item_thue_product_count);
        _size = root.findViewById(R.id.item_thue_size);
        _nameProduct = root.findViewById(R.id.item_thue_name_product);
        _tra = root.findViewById(R.id.item_thue_ticket_tra_btn);


    }

}
