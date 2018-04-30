package com.example.dong.kiemsoatxequansu.ui.searchinfor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.dong.kiemsoatxequansu.R;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class InforVehicleActivity extends AppCompatActivity {

    private ImageView ivBack;
    private UltraViewPager ultraViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_vehicle);

        addControl();
        initView();
        addEvent();
    }

    /**
     * Khởi tạo dữ liệu view
     * Created by Dong on 30-Apr-18
     */

    private void initView() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        List<Bitmap> listImageVehicle = new ArrayList<>();
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);//icon mặc định
        Bitmap icon2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);//icon mặc định
        Bitmap icon3 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);//icon mặc định
        listImageVehicle.add(icon1);
        listImageVehicle.add(icon2);
        listImageVehicle.add(icon3);
        PagerAdapter adapter = new UltraPagerAdapter(listImageVehicle);
        ultraViewPager.setAdapter(adapter);

        //initialize built-in indicator
        ultraViewPager.initIndicator();
        //set style of indicators
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setMargin(0, 0, 0, 10)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_FRACTION_PARENT, 5, getResources().getDisplayMetrics()));
        //set the alignment
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //construct built-in indicator, and add it to  UltraViewPager
        ultraViewPager.getIndicator().build();

        //set an infinite loop
        ultraViewPager.setInfiniteLoop(true);
        //enable auto-scroll mode
        ultraViewPager.setAutoScroll(2000);
    }

    /**
     * Sự kiện các control
     * Created by Dong on 30-Apr-18
     */
    private void addEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Khởi tạo control
     * Created by Dong on 30-Apr-18
     */
    private void addControl() {
        ivBack = findViewById(R.id.ivBack);
        ultraViewPager = findViewById(R.id.ultra_viewpager);
    }


}
