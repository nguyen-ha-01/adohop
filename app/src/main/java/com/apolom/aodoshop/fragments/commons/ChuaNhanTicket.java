package com.apolom.aodoshop.fragments.commons;

import static com.apolom.aodoshop.R.layout.item_ticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolom.aodoshop.R;


public class ChuaNhanTicket extends FrameLayout  {
    View root;
    private ImageView _barcode;
    private TextView _date,_maNhanDo,_nameProduct,_count,_size, _daThanhToan;

    public ChuaNhanTicket(Context context) {
        super(context);
        init(context);
    }

    public ChuaNhanTicket(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChuaNhanTicket(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(item_ticket, this, true);

        _barcode = root.findViewById(R.id.item_ticket_barcode);
        _date = root.findViewById(R.id.item_ticket_date);
        _maNhanDo = root.findViewById(R.id.item_ticket_ma_nhan_do);
        _nameProduct = root.findViewById(R.id.item_ticket_name_product);
        _count = root.findViewById(R.id.item_ticket_product_count);
        _size = root.findViewById(R.id.item_ticket_size);
        _daThanhToan = root.findViewById(R.id.item_ticket_da_thanh_toan);

    }
    public void addCallback(OnClickListener call){
        root.setOnClickListener(call);
    }
    public void setText(String __date,String __maNhanDo,String __nameProduct,String __count,String __size, String __daThanhToan){
        _date.setText(__date);_maNhanDo.setText(__maNhanDo);_nameProduct.setText(__nameProduct);_count.setText(__count);_size.setText(__size); _daThanhToan.setText(__daThanhToan);
    }
    public void setImage(Bitmap bm){
        _barcode.setImageBitmap(bm);
    }


}
