package com.apolom.aodoshop.fragments.home.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apolom.aodoshop.R;

public class ItemHome extends FrameLayout {
    private ImageView imageView;
    private TextView title,thumb,price;
    private View root;

    public ItemHome(Context context ) {
        super(context);
        init(context);
    }
    public ItemHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
    public ItemHome(Context context, AttributeSet attrs,  int defstyle) {
        super(context, attrs,defstyle);

        init(context);
    }

    private void init(Context context ) {
        // Inflate the layout

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.item_home, this, true);
        // Get references to the child views
        imageView = root.findViewById(R.id.item_home_image);
        title = root.findViewById(R.id.item_home_title);
        thumb = root.findViewById(R.id.item_home_thumb);
        price = root.findViewById(R.id.item_home_price);

    }

    // Add methods to set properties of the custom view
    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }
    public void addCallback(OnClickListener callback){
        root.setOnClickListener(callback);
    }
    public void setText(String _title,String _thumb,String _price) {
        this.title.setText(_title);
        this.thumb.setText(_thumb);
        this.price.setText(_price);
    }

}