package com.example.dong.kiemsoatxequansu.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.ui.notebook.ICallBackData;
import com.example.dong.kiemsoatxequansu.ui.notebook.SoTayActivity;
import com.example.dong.kiemsoatxequansu.ui.notebook.SotayAsynstask;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.TraThongTinActivity;

public class MainActivity extends AppCompatActivity implements ICallBackData {

    CardView cvSoTayLaiXe, cvTraThongTin, cv_BienBanViPham;
    private static final int MY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String testValue = "Hello, world!";

        byte[] encodeValue = Base64.encode(testValue.getBytes(), Base64.DEFAULT);
        byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);

        String a=new String(encodeValue);
        String b=new String(decodeValue);

        Log.d("TEST", "defaultValue = " + testValue);
        Log.d("TEST", "encodeValue = " + a);
        Log.d("TEST", "decodeValue = " +b);
        addControl();
        addEvent();

        checkPermission();
    }

    /**
     * Sự kiện control
     * Created by Dong on 10-Apr-18
     */
    private void addEvent() {
        cvSoTayLaiXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    SotayAsynstask soTayActivity=new SotayAsynstask(MainActivity.this);
                    soTayActivity.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cvTraThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, TraThongTinActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        cv_BienBanViPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this,"Tính năng đang phát triển",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });

    }

    /**
     * Khởi tạo control
     * Created by Dong on 10-Apr-18
     */
    private void addControl() {
        try {
            cvSoTayLaiXe = findViewById(R.id.cvSoTayLaiXe);
            cvTraThongTin = findViewById(R.id.cvTraThongTin);
            cv_BienBanViPham = findViewById(R.id.cv_BienBanViPham);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra quyền
     * Created by Dong on 10-Apr-18
     */
    private void checkPermission() {
        try {
            String[] listPermission = new String[]{android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,};
            boolean isOn = false;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //   textStatus1.setText("On");
            } else {
                //   textStatus1.setText("Off");
                isOn = true;
            }
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // textStatus2.setText("On");
            } else {
                // textStatus2.setText("Off");
                isOn = true;
            }
            if (isOn) {
                ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Callback dữ liệu
     * Created by Dong on 19-Apr-18
     */
    @Override
    public void callBackData() {
        Intent intent = new Intent(MainActivity.this, SoTayActivity.class);
        startActivity(intent);
    }
}
