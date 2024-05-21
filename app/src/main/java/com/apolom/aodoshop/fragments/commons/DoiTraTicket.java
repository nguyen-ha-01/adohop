package com.apolom.aodoshop.fragments.commons;

import static com.apolom.aodoshop.R.layout.item_ticket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolom.aodoshop.R;

public class DoiTraTicket extends FrameLayout {
    View root;;
    private TextView _date,_maNhanDo,_nameProduct,_count,_size, _daThanhToan;

    public DoiTraTicket(Context context) {
        super(context);
        init(context);
    }

    public DoiTraTicket(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoiTraTicket(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.item_doi_ticket, this, true);


        _date = root.findViewById(R.id.item_doi_date);
        _maNhanDo = root.findViewById(R.id.item_doi_ma_nhan_do);
        _nameProduct = root.findViewById(R.id.item_doi_name_product);
        _count = root.findViewById(R.id.item_doi_product_count);
        _size = root.findViewById(R.id.item_doi_size);
        _daThanhToan = root.findViewById(R.id.item_doi_da_thanh_toan);

    }
    public void addCallback(View.OnClickListener call){
        root.setOnClickListener(call);
    }
    public void setText(String __date,String __maNhanDo,String __nameProduct,String __count,String __size, String __daThanhToan){
        _date.setText(__date);_maNhanDo.setText(__maNhanDo);_nameProduct.setText(__nameProduct);_count.setText(__count);_size.setText(__size); _daThanhToan.setText(__daThanhToan);
    }
}
