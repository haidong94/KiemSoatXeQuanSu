package com.example.dong.kiemsoatxequansu.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.ui.notebook.SoTayActivity;
import com.example.dong.kiemsoatxequansu.ui.searchinfor.TraThongTinActivity;

public class MainActivity extends AppCompatActivity {

    CardView cvSoTayLaiXe,cvTraThongTin,cv_BienBanViPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();
    }

    private void addEvent() {
        cvSoTayLaiXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SoTayActivity.class);
                startActivity(intent);
            }
        });

        cvTraThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TraThongTinActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addControl() {
        cvSoTayLaiXe=findViewById(R.id.cvSoTayLaiXe);
        cvTraThongTin=findViewById(R.id.cvTraThongTin);
        cv_BienBanViPham=findViewById(R.id.cv_BienBanViPham);

    }
}
