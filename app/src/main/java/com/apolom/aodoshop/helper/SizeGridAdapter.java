package com.apolom.aodoshop.helper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apolom.aodoshop.R;

import java.util.List;

public class SizeGridAdapter extends RecyclerView.Adapter<SizeGridAdapter.ViewHolder> {
    public int selected = 0;
    private List<String> mData;
    private  Context ctx;
    private LayoutInflater mInflater;

    public SizeGridAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        ctx = context;
        this.mData = data;

    }
    public String getSize() {return mData.get(selected);}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_size_pick, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.myTextView.setText(item);
        holder.setBackground(selected==position,ctx);
        holder.frame.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selected);
                TextView value = v.findViewById(R.id.size);
                selected = holder.getAdapterPosition();
                notifyItemChanged(selected);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        FrameLayout frame;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.size);
            frame = itemView.findViewById(R.id.item_size_back);

        }
        @SuppressLint("UseCompatLoadingForDrawables")
        void setBackground(boolean isSelected, Context ctx){
            if (isSelected)frame.setBackground(ctx.getDrawable(R.drawable.back_out_line_on));
            else{
                frame.setBackground(ctx.getDrawable(R.drawable.back_btn_outline));
            }
        }
    }
}