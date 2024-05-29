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

import kotlin.Unit;

public class DptcDaNhanAdapter extends RecyclerView.Adapter<DptcDaNhanAdapter.ViewHolder>{
    Call<Order> doi;Call<Order> tra;Context ctx;private List<Order> mData;
    public DptcDaNhanAdapter(Context context, Call<Order> doi,Call<Order> tra,  List<Order> data){

        this.mInflater = LayoutInflater.from(context);
        ctx = context;
        this.doi =doi ;
        this.tra = tra;
        this.mData = data;
    }
    private LayoutInflater mInflater;
    @NonNull
    @Override
    public DptcDaNhanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_da_nhan_ticket, viewGroup, false);
        return new DptcDaNhanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DptcDaNhanAdapter.ViewHolder viewHolder, int i) {
        Order item  = mData.get(i);
        viewHolder.setText("havent no yet", item.id, item.productName, String.format("%d",item.quantity), item.size, String.format("%d",item.price));
        viewHolder.addCallback(doi,tra,item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class  ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(View itemView) {
            super(itemView);
            init(itemView);
        }
        View root;

        private TextView _date,_maNhanDo,_nameProduct,_count,_size, _daThanhToan;


        private MaterialButton _doi, _tra;
        private void init(View _root) {
            _date = _root.findViewById(R.id.item_da_nhan_date);
            _maNhanDo = _root.findViewById(R.id.item_da_nhan_ma_nhan_do);
            _nameProduct = _root.findViewById(R.id.item_da_nhan_name_product);
            _count = _root.findViewById(R.id.item_da_nhan_product_count);
            _size = _root.findViewById(R.id.item_da_nhan_size);
            _daThanhToan = _root.findViewById(R.id.item_da_nhan_da_thanh_toan);
            _doi = _root.findViewById(R.id.item_da_nhan_ticket_doi_btn);
            _tra = _root.findViewById(R.id.item_da_nhan_ticket_tra_btn);

        }
        public void addCallback(Call<Order> doi, Call<Order> tra, Order e){
            _doi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doi.onPick(e);
                }
            });
            _tra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tra.onPick(e);
                }
            });

        }
        public void setText(String __date,String __maNhanDo,String __nameProduct,String __count,String __size, String __daThanhToan){
            _date.setText(__date);_maNhanDo.setText(__maNhanDo);_nameProduct.setText(__nameProduct);_count.setText("x"+__count);_size.setText("size"+__size); _daThanhToan.setText(__daThanhToan+" vnd");
        }
    }
}
