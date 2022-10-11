package com.zhks.safetyproduction.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zhks.safetyproduction.R;

public class LoadingView extends RelativeLayout {


    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.layout_view_loading, this);
        RelativeLayout loadingRoot = view.findViewById(R.id.loadingRoot);
        loadingRoot.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        ImageView imgLoading = view.findViewById(R.id.imgLoading);
        Glide.with(context).load(R.drawable.loading).into(imgLoading);
    }
}
