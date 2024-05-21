package com.apolom.aodoshop.fragments.commons.app_bar;

import static com.apolom.aodoshop.R.layout.item_ticket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.apolom.aodoshop.R;

public class DetailAppBar extends FrameLayout {
    View root;
    private ImageView _back;
    private TextView _money;
    public DetailAppBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DetailAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DetailAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context ctx) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.detail_app_bar, this, true);
        _back = root.findViewById(R.id.detail_app_bar_back_btn);
        _money = root.findViewById(R.id.detail_app_bar_money);
    }

    public void init(OnClickListener callback, String money ) {
        _back.setOnClickListener(callback);
        _money.setText(money);
    }
}
