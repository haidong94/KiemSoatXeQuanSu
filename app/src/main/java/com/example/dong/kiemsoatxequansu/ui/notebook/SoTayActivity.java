package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild_;
import com.example.dong.kiemsoatxequansu.data.model.Matter_;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.Specification_;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle_;

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

    Box<Vehicle> vehicleBox;

    Box<Matter> matterBox;

    Box<MatterChild> matterChildBox;

    Box<Specification> specificationBox;

    Toolbar toolbar;

    Spinner spinLoaiXe, spinVehicle, spinNhomCongTac, spinCongtac;

    RecyclerView recyclerView;

    SpecificationAdapter specificationAdapter;

    RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sotaylaixe);

        checkPermission();
        try {
            boxStore = ((App) getApplication()).getBoxStore();
            vehicleBox = boxStore.boxFor(Vehicle.class);
            matterBox = boxStore.boxFor(Matter.class);
            matterChildBox = boxStore.boxFor(MatterChild.class);
            specificationBox = boxStore.boxFor(Specification.class);

            ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(getResources(), SoTayActivity.this);
            try {
                objectBoxImporter.importFromJson();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
           e.printStackTrace();
        }


        addControl();
        setUpSpiner();
        addEvent();

    }

    private void setUpSpiner() {
        try {
            // Vehicle listChiTiet=vehicleBox.query().equal(Vehicle_.idMatter,1).build().findFirst();
            //  List<Vehicle> listChiTiet = vehicleBox.query().build().find();

            //vehicle
            String[] listNameVehicle = vehicleBox.query().build().property(Vehicle_.nameVehicle).findStrings();
            ArrayAdapter<String> adapterVehicle = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listNameVehicle);
            spinVehicle.setAdapter(adapterVehicle);


            //matter
            String[] listNameMatter = matterBox.query().build().property(Matter_.nameMatter).findStrings();
            ArrayAdapter<String> adapterMatter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listNameMatter);
            spinNhomCongTac.setAdapter(adapterMatter);

            //matter child
            String[] listNameMatterChild = matterChildBox.query().build().property(MatterChild_.nameChildMatter).findStrings();
            ArrayAdapter<String> adapterMatterChild = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listNameMatterChild);
            spinCongtac.setAdapter(adapterMatterChild);

            //specification
            List<Specification> listNameSpecification = specificationBox.query().build().find();
//        ArrayAdapter<String> adapterSpecification = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listNameSpecification);
//        spinCongtac.setAdapter(adapterSpecification);

            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);
            specificationAdapter = new SpecificationAdapter(listNameSpecification, this);
            recyclerView.setAdapter(specificationAdapter);
        } catch (Exception e) {
           e.printStackTrace();
        }


    }

    private void addEvent() {

    }

    private void addControl() {
        try {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //quay về activity trước
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về
            //  spinLoaiXe = findViewById(R.id.spinLoaiXe);
            spinVehicle = findViewById(R.id.spinVehicle);
            spinNhomCongTac = findViewById(R.id.spinNhomCongTac);
            spinCongtac = findViewById(R.id.spinCongtac);
        } catch (Exception e) {
           e.printStackTrace();
        }



    }

    private void checkPermission() {
        try {
            String[] listPermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            boolean isOn = false;

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //   textStatus1.setText("On");
            } else {
                //   textStatus1.setText("Off");
                isOn = true;
            }
            if (isOn) {
                ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
}
