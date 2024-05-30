package com.apolom.aodoshop.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apolom.aodoshop.R;
import com.apolom.aodoshop.models.Order;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ThueTicketAdapter extends RecyclerView.Adapter<ThueTicketAdapter.ViewHolder> {

    private List<Order> mData;
    private Context ctx;
    private LayoutInflater mInflater;
    private Call<Order> callback;

    public ThueTicketAdapter(Context context, List<Order> data,Call<Order> call) {
        this.callback = call;
        this.mInflater = LayoutInflater.from(context);
        ctx = context;
        this.mData = data;

    }
    public Integer getSize() {return mData.size();}

    @NonNull
    @Override
    public ThueTicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_thue_ticket, parent, false);
        return new ThueTicketAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ThueTicketAdapter.ViewHolder holder, int position) {
        Order item = mData.get(position);

        if(item.orderType.equals("thue")) {
            holder.setText(item.startDate,item.endDate, item.id, item.productName, String.format("%d",item.quantity), item.size);
        }
        holder.addCallback(callback,item);


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


        private TextView _date_end, _date_start,_maNhanDo,_nameProduct,_count,_size;
        private MaterialButton traBtn;



        private void init(View _root) {

            _date_start = _root.findViewById(R.id.item_thue_ngay_thue);
            _date_end = _root.findViewById(R.id.item_thue_ngay_het_han);
            _maNhanDo = _root.findViewById(R.id.item_thue_ma_nhan_do);
            _nameProduct = _root.findViewById(R.id.item_thue_name_product);
            _count = _root.findViewById(R.id.item_thue_product_count);
            _size = _root.findViewById(R.id.item_thue_size);
            traBtn = _root.findViewById(R.id.item_thue_ticket_tra_btn);

        }
        public void addCallback(Call<Order> call,Order e){
            traBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call.onPick(e);
                }
            });
        }
        public void setText(String __date_start,String __date_end,String __maNhanDo,String __nameProduct,String __count,String __size){
            _date_start.setText(__date_start);_maNhanDo.setText(__maNhanDo);_nameProduct.setText(__nameProduct);_count.setText("x"+__count);_size.setText("size"+__size);
            _date_end.setText(__date_end);
        }




    }
}