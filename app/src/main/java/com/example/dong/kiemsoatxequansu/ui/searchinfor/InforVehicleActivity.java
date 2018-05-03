package com.example.dong.kiemsoatxequansu.ui.searchinfor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.data.model.DetailVehicle;
import com.example.dong.kiemsoatxequansu.utils.Commons;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class InforVehicleActivity extends AppCompatActivity {

    private ImageView ivBack;
    private UltraViewPager ultraViewPager;
    private DetailVehicle detailVehicle;
    private TextView tvLicensePlates, tvCategoryVehicle, tvUnit, tvNumberRegister,
            tvDateRegiter, tvFrameNumber, tvMachineNumber, tvSource, tvRankQuality, tvDateIncrease,
            tvDateUsing, tvNotes;
    public static final String FOLDER_IMAGE = "/image_vehicle/";
    public static final String PNG = ".PNG";
    public static final String JPG = ".JPG";
    private String unit;
    private String categoryVehicle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_vehicle);

        getData();
        addControl();
        initView();
        addEvent();
    }

    private void getData() {
        try {
            detailVehicle = (DetailVehicle) getIntent().getSerializableExtra(Commons.KEY_VEHICLE);
            unit = getIntent().getStringExtra(Commons.KEY_UNIT);
            categoryVehicle = getIntent().getStringExtra(Commons.KEY_CATEGORY_VEHICLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo dữ liệu view
     * Created by Dong on 30-Apr-18
     */

    private void initView() {

        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //initialize UltraPagerAdapter，and add child view to UltraViewPager
        List<Bitmap> listImageVehicle = new ArrayList<>();
        //get image
        Bitmap bitmap = null;
        // Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);//icon mặc định
        if (detailVehicle.getImageVehicle() != null) { //Không cần xét trường hợp empty vì lúc import dữ liệu nếu empty thì setAvatar là null rồi
            //Giải mã tên ảnh và cắt chuỗi sau dấu "," để lấy danh sách ảnh
            String[] listImage = Commons.decodeString(detailVehicle.getImageVehicle()).split(",");
            for (String image : listImage) {
                String imageJPGInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_IMAGE + image + JPG;
                if (imageJPGInSD != null) {
                    bitmap = BitmapFactory.decodeFile(imageJPGInSD);
                } else {
                    String imagePNGInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_IMAGE + image + PNG;
                    bitmap = BitmapFactory.decodeFile(imagePNGInSD);
                }
                if (bitmap != null) {
                    listImageVehicle.add(bitmap);
                }
            }

        }

        //Nếu có ảnh thì mới hiển thị
        if (listImageVehicle.size() != 0) {
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
        }else {
            ultraViewPager.setVisibility(View.GONE);
        }

        tvLicensePlates.setText(Commons.decodeString(detailVehicle.getLicensePlate()));
        tvCategoryVehicle.setText(Commons.decodeString(categoryVehicle));
        tvUnit.setText(Commons.decodeString(unit));
        tvNumberRegister.setText(Commons.decodeString(detailVehicle.getNumberRegister()));
        tvDateRegiter.setText(Commons.decodeString(detailVehicle.getDateRegister()));
        tvFrameNumber.setText(Commons.decodeString(detailVehicle.getFrameNumber()));
        tvMachineNumber.setText(Commons.decodeString(detailVehicle.getMachineNumber()));
        tvSource.setText(Commons.decodeString(detailVehicle.getSoure()));
        tvRankQuality.setText(Commons.decodeString(detailVehicle.getRankQuanlity()));
        tvDateIncrease.setText(Commons.decodeString(detailVehicle.getDateIncrease()));
        tvDateUsing.setText(Commons.decodeString(detailVehicle.getDateUsing()));
        if (detailVehicle.getNotes() != null) {
            tvNotes.setText(Commons.decodeString(detailVehicle.getNotes()));
        } else {
            tvNotes.setText(getResources().getString(R.string.not_notes));
        }
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
        tvLicensePlates = findViewById(R.id.tvLicensePlates);
        tvCategoryVehicle = findViewById(R.id.tvCategoryVehicle);
        tvUnit = findViewById(R.id.tvUnit);
        tvNumberRegister = findViewById(R.id.tvNumberRegister);
        tvDateRegiter = findViewById(R.id.tvDateRegiter);
        tvFrameNumber = findViewById(R.id.tvFrameNumber);
        tvMachineNumber = findViewById(R.id.tvMachineNumber);
        tvSource = findViewById(R.id.tvSource);
        tvRankQuality = findViewById(R.id.tvRankQuality);
        tvDateIncrease = findViewById(R.id.tvDateIncrease);
        tvDateUsing = findViewById(R.id.tvDateUsing);
        tvNotes = findViewById(R.id.tvNotes);

    }


}
