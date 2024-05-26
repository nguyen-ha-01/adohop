package com.apolom.aodoshop.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.models.Order;
import com.apolom.aodoshop.service.QrService;

import java.util.List;

public class HoaDonChuaNhanAdapter extends RecyclerView.Adapter<HoaDonChuaNhanAdapter.ViewHolder> {

    private List<Order> mData;
    private Context ctx;
    private LayoutInflater mInflater;
    private Call callback;
    QrService service = new QrService();

    public HoaDonChuaNhanAdapter(Context context, List<Order> data,Call call) {
        this.callback = call;
        this.mInflater = LayoutInflater.from(context);
        ctx = context;
        this.mData = data;

    }
    public Integer getSize() {return mData.size();}

    @NonNull
    @Override
    public HoaDonChuaNhanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ticket, parent, false);
        return new HoaDonChuaNhanAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HoaDonChuaNhanAdapter.ViewHolder holder, int position) {
        Order item = mData.get(position);
        //todo:bind data and callback for item view
        holder.setText("havent no yet", item.id, item.productName, String.format("%d",item.quantity), item.size, String.format("%d",item.price));
        holder.addCallback(callback,item);

        try {
            Bitmap b = service.generateBarcode(item.id);
            holder.setImage(b);
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            init(itemView);
        }
        View root;

        private ImageView _barcode;
        private TextView _date,_maNhanDo,_nameProduct,_count,_size, _daThanhToan;



        private void init(View _root) {
            _barcode = _root.findViewById(R.id.item_ticket_barcode);
            _date = _root.findViewById(R.id.item_ticket_date);
            _maNhanDo = _root.findViewById(R.id.item_ticket_ma_nhan_do);
            _nameProduct = _root.findViewById(R.id.item_ticket_name_product);
            _count = _root.findViewById(R.id.item_ticket_product_count);
            _size = _root.findViewById(R.id.item_ticket_size);
            _daThanhToan = _root.findViewById(R.id.item_ticket_da_thanh_toan);

        }
        public void addCallback(Call call,Order e){
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call.onPick(e);
                }
            });

        }
        public void setText(String __date,String __maNhanDo,String __nameProduct,String __count,String __size, String __daThanhToan){
            _date.setText(__date);_maNhanDo.setText(__maNhanDo);_nameProduct.setText(__nameProduct);_count.setText("x"+__count);_size.setText("size"+__size); _daThanhToan.setText(__daThanhToan+" vnd");
        }
        public void setImage(Bitmap bm){
            _barcode.setImageBitmap(bm);
        }



    }
}