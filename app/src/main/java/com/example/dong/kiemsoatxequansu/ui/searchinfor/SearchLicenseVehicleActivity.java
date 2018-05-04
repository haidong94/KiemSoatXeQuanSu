package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.CategoryVehicle;
import com.example.dong.kiemsoatxequansu.data.model.CategoryVehicle_;
import com.example.dong.kiemsoatxequansu.data.model.DetailVehicle;
import com.example.dong.kiemsoatxequansu.data.model.DetailVehicle_;
import com.example.dong.kiemsoatxequansu.data.model.SignLicensePlates;
import com.example.dong.kiemsoatxequansu.data.model.SignLicensePlates_;
import com.example.dong.kiemsoatxequansu.data.model.UnitOrganization;
import com.example.dong.kiemsoatxequansu.data.model.UnitOrganization_;
import com.example.dong.kiemsoatxequansu.utils.Commons;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class SearchLicenseVehicleActivity extends AppCompatActivity {
    private TextInputLayout tilLicensePlates;
    private EditText edLicensePlates;
    private Button btnSearch;
    private ImageView ivBack;
    private TransactionTime transactionTime;

    private BoxStore boxStore;
    private Box<DetailVehicle> detailVehicleBox;
    private Box<UnitOrganization> unitOrganizationBox;
    private Box<SignLicensePlates> signLicensePlatesBox;
    private Box<CategoryVehicle> categoryVehicleBox;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_license_vehicle);

        //Get data từ database
        getData();
        //Khởi tạo control
        addControl();
        //Sự kiện các control
        addEvent();
    }
    /**
     * Get data từ database
     * Created by Dong on 25-Apr-18
     */
    private void getData() {
        try {
            boxStore = ((App) getApplication()).getBoxStore();
            detailVehicleBox = boxStore.boxFor(DetailVehicle.class);
            signLicensePlatesBox = boxStore.boxFor(SignLicensePlates.class);
            categoryVehicleBox = boxStore.boxFor(CategoryVehicle.class);
            unitOrganizationBox = boxStore.boxFor(UnitOrganization.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sự kiện các control
     * Created by Dong on 04-May-18
     */
    private void addEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(edLicensePlates.getText().toString())) {
                        Toast.makeText(SearchLicenseVehicleActivity.this, getResources().getString(R.string.quality_image_bad), Toast.LENGTH_SHORT).show();

                    } else {
                        //in hoa chuỗi nhập
                        String numberDrivingLicensePlates = edLicensePlates.getText().toString().trim().replace("-", "").toUpperCase();
                        //Lấy 2 kí tự đầu
                        String signUnit = Character.toString(numberDrivingLicensePlates.charAt(0)) + Character.toString(numberDrivingLicensePlates.charAt(1));
                        String signUnitEncode = Commons.encodeString(signUnit);

                        //Kiểm tra 2 kí tự đầu trong bảng kí hiệu biển số: nếu không có là biển giả luôn
                        SignLicensePlates signLicensePlates = signLicensePlatesBox.query().equal(SignLicensePlates_.sign, signUnitEncode).build().findFirst();
                        if (signLicensePlates != null) {
                            String numberEncode = Commons.encodeString(numberDrivingLicensePlates);
                            //Kiểm tra biển xe trong bảng chi tiết xe:Nếu không có là biển giả
                            DetailVehicle detailVehicle = detailVehicleBox.query().equal(DetailVehicle_.licensePlate, numberEncode).build().findFirst();
                            if (detailVehicle != null) {
                                UnitOrganization unitOrganization=unitOrganizationBox.query().equal(UnitOrganization_.idUnit, detailVehicle.getIdUnit()).build().findFirst();
                                CategoryVehicle categoryVehicle=categoryVehicleBox.query().equal(CategoryVehicle_.idCategoryVehicle, detailVehicle.getIdCategoryVehicle()).build().findFirst();
                                Intent intent = new Intent(SearchLicenseVehicleActivity.this, InforVehicleActivity.class);
                                intent.putExtra(Commons.KEY_VEHICLE, detailVehicle);
                                intent.putExtra(Commons.KEY_UNIT, unitOrganization.getName());
                                intent.putExtra(Commons.KEY_CATEGORY_VEHICLE, categoryVehicle.getName());
                                startActivity(intent);
                            } else {
                                Toast.makeText(SearchLicenseVehicleActivity.this, getResources().getString(R.string.not_plates), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(SearchLicenseVehicleActivity.this, getResources().getString(R.string.not_plates), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addControl() {
        ivBack = findViewById(R.id.ivBack);
        tilLicensePlates = findViewById(R.id.tilLicensePlates);
        edLicensePlates = findViewById(R.id.edLicensePlates);
        btnSearch = findViewById(R.id.btnSearch);
    }
}
