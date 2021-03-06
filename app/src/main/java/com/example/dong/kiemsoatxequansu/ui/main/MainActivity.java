package com.example.dong.kiemsoatxequansu.ui.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.ui.notebook.ICallBackData;
import com.example.dong.kiemsoatxequansu.ui.notebook.NoteBookActivity;
import com.example.dong.kiemsoatxequansu.ui.notebook.NoteBookAsynstask;
import com.example.dong.kiemsoatxequansu.ui.searchdrivinglicense.DrivingLicenseActivity;
import com.example.dong.kiemsoatxequansu.ui.searchdrivinglicense.DrivingLicenseAsynctack;
import com.example.dong.kiemsoatxequansu.ui.searchdrivinglicense.ICallBackDataDrivingLicense;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.DrivingLicensePlatesAsynctack;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.ICallBackDataDrivingLicensePlates;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.RecognizerLisenceVehileActivity;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.SearchLicenseVehicleActivity;

public class MainActivity extends AppCompatActivity implements ICallBackData,ICallBackDataDrivingLicense,ICallBackDataDrivingLicensePlates {

    private CardView cvSoTayLaiXe, cvRecognizerLicensePlates, cvSearchDrivingLicense,cvSearchLicensePlates;
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private int SEARCH_TYPE=0;//tìm kiếm bằng nhận dạng biển là 0, tìm kiếm bằng nhập tay là 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    NoteBookAsynstask noteBookAsynstask =new NoteBookAsynstask(MainActivity.this);
                    noteBookAsynstask.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cvRecognizerLicensePlates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SEARCH_TYPE=0;
                    DrivingLicensePlatesAsynctack drivingLicensePlatesAsynctack=new DrivingLicensePlatesAsynctack(MainActivity.this);
                    drivingLicensePlatesAsynctack.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        cvSearchDrivingLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DrivingLicenseAsynctack drivingLicenseAsynctack=new DrivingLicenseAsynctack(MainActivity.this);
                    drivingLicenseAsynctack.execute();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
        });

        cvSearchLicensePlates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SEARCH_TYPE=1;
                    DrivingLicensePlatesAsynctack drivingLicensePlatesAsynctack=new DrivingLicensePlatesAsynctack(MainActivity.this);
                    drivingLicensePlatesAsynctack.execute();
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
            cvRecognizerLicensePlates = findViewById(R.id.cvRecognizerLicensePlates);
            cvSearchDrivingLicense = findViewById(R.id.cvSearchDrivingLicense);
            cvSearchLicensePlates = findViewById(R.id.cvSearchLicensePlates);
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
     * Callback dữ liệu tra cứu phụ tùng xe
     * Created by Dong on 19-Apr-18
     */
    @Override
    public void callBackData() {
        Intent intent = new Intent(MainActivity.this, NoteBookActivity.class);
        startActivity(intent);
    }

    /**
     * Callback dữ liệu tra cứu giấy phép lái xe
     * Created by Dong on 19-Apr-18
     */
    @Override
    public void callBackDataDrivingLicense() {
        Intent intent = new Intent(MainActivity.this, DrivingLicenseActivity.class);
        startActivity(intent);
    }

    @Override
    public void callBackDataDrivingLicensePlates() {
        if(SEARCH_TYPE==0) {
            Intent intent = new Intent(MainActivity.this, RecognizerLisenceVehileActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, SearchLicenseVehicleActivity.class);
            startActivity(intent);
        }
    }
}
