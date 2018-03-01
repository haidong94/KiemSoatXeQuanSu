package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;
import com.example.dong.kiemsoatxequansu.data.model.ChiTietPhuTung;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by DONG on 31-Oct-17.
 */

public class SoTayActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    BoxStore boxStore;
    Box<ChiTietPhuTung> box;

    Toolbar toolbar;
    Spinner spinLoaiXe,spinChungLoaiXe,spinNhomCongTac,spinChiTietPhuTung;
    ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sotaylaixe);

        checkPermission();
        boxStore = ((App) getApplication()).getBoxStore();
        box = boxStore.boxFor(ChiTietPhuTung.class);

        ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(getResources(),SoTayActivity.this);
        try {
            objectBoxImporter.importFromJson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        addControl();
        setUpSpiner();
        addEvent();
        
    }

    private void setUpSpiner() {
        List<ChiTietPhuTung> listChiTiet=box.query().build().find();
        for(ChiTietPhuTung chiTietPhuTung:listChiTiet)
        {
            list.add(chiTietPhuTung.getTen());
        }

        ArrayAdapter<String> adapterSoYTe=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        spinChiTietPhuTung.setAdapter(adapterSoYTe);

    }

    private void addEvent() {

    }

    private void addControl() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //quay về activity trước
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về

        spinLoaiXe=findViewById(R.id.spinLoaiXe);
        spinChungLoaiXe=findViewById(R.id.spinChungLoaiXe);
        spinNhomCongTac=findViewById(R.id.spinNhomCongTac);
        spinChiTietPhuTung=findViewById(R.id.spinChiTietPhuTung);


    }
    private void checkPermission() {
        String[] listPermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean isOn = false;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //   textStatus1.setText("On");
        } else {
            //   textStatus1.setText("Off");
            isOn = true;
        }
        if (isOn){
            ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
        }
    }
}
