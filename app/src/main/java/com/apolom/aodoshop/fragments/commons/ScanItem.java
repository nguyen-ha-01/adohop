package com.apolom.aodoshop.fragments.commons;

import static com.apolom.aodoshop.R.layout.item_barcode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.apolom.aodoshop.R;
import com.bumptech.glide.Glide;

public class ScanItem extends LinearLayout {
    ImageView imageView;
    public ScanItem(Context context) {
        super(context);
    }

    public ScanItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScanItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(Context ctx){

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(item_barcode, this, true);
        imageView = root.findViewById(R.id.imageView);
    }
    public void loadBarCode(Context ctx, String url){
        Glide.with(ctx).load(url).into(imageView);
    }
}
