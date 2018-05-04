package com.example.dong.kiemsoatxequansu.ui.searchdrivinglicense;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicense;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicenseCatalog;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicenseCatalog_;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicense_;
import com.example.dong.kiemsoatxequansu.utils.Commons;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class DrivingLicenseActivity extends AppCompatActivity {
    public static final String PNG = ".PNG";
    public static final String JPG = ".JPG";
    public static final String FOLDER_IMAGE = "/avatar/";

    private BoxStore boxStore;
    private Box<DrivingLicenseCatalog> drivingLicenseCatalogBox;
    private Box<DrivingLicense> drivingLicenseBox;
    private TextInputLayout tilDrivingLicense;
    private EditText edDrivingLicense;
    private Button btnSearch;
    private ImageView ivBack;
    private TransactionTime transactionTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license);

        getData();
        addControl();
        addEvent();
    }

    /**
     * Get data từ database
     * Created by Dong on 25-Apr-18
     */
    private void getData() {
        try {
            boxStore = ((App) getApplication()).getBoxStore();
            drivingLicenseCatalogBox = boxStore.boxFor(DrivingLicenseCatalog.class);
            drivingLicenseBox = boxStore.boxFor(DrivingLicense.class);

//            ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(getResources(), DrivingLicenseActivity.this);
//            objectBoxImporter.importDrivingLicenseFromJson();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sự kiện các control
     * Created by Dong on 25-Apr-18
     */
    private void addEvent() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Kiểm tra nhập số giấy phép lái xe chưa
                    if (TextUtils.isEmpty(edDrivingLicense.getText().toString())) {
                        tilDrivingLicense.setError(getResources().getString(R.string.empty_driving_license));
                    } else {
                        transactionTime = new TransactionTime(System.currentTimeMillis());
                        tilDrivingLicense.setErrorEnabled(false);
                        String numberDrivingLicense = edDrivingLicense.getText().toString().trim();
                        String numberEncode = Commons.encodeString(numberDrivingLicense);

                        final DrivingLicense drivingLicense = drivingLicenseBox.query().equal(DrivingLicense_.numberDrivingLicense, numberEncode).build().findFirst();
                        if (drivingLicense != null) {
                            final DrivingLicenseCatalog drivingLicenseCatalog = drivingLicenseCatalogBox.query().equal(DrivingLicenseCatalog_.idCatalog, drivingLicense.getIdCatalog()).build().findFirst();
                            new Handler().postDelayed(new Runnable() {
                                @SuppressLint({"SetTextI18n", "NewApi"})
                                @Override
                                public void run() {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    assert inflater != null;
                                    @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_driving_license, null);
                                    // set the custom dialog components - text, image and button
                                    ImageView ivAvatar = dialogView.findViewById(R.id.ivAvatar);
                                    TextView tvNumberDrivingLicense = dialogView.findViewById(R.id.tvNumberDrivingLicense);
                                    TextView tvDateRegiter = dialogView.findViewById(R.id.tvDateRegiter);
                                    TextView tvName = dialogView.findViewById(R.id.tvName);
                                    TextView tvBirthDay = dialogView.findViewById(R.id.tvBirthDay);
                                    TextView tvAddress = dialogView.findViewById(R.id.tvAddress);
                                    TextView tvCodePerson = dialogView.findViewById(R.id.tvCodePerson);
                                    TextView tvCodeDateRanger = dialogView.findViewById(R.id.tvCodeDateRanger);
                                    TextView tvCodeIssuedBy = dialogView.findViewById(R.id.tvCodeIssuedBy);
                                    TextView tvDateOfElistment = dialogView.findViewById(R.id.tvDateOfElistment);
                                    TextView tvDateOfRecuitment = dialogView.findViewById(R.id.tvDateOfRecuitment);
                                    TextView tvKilometerDriven = dialogView.findViewById(R.id.tvKilometerDriven);
                                    TextView tvTimeHasDrove = dialogView.findViewById(R.id.tvTimeHasDrove);
                                    TextView tvNumberOfficers = dialogView.findViewById(R.id.tvNumberOfficers);
                                    TextView tvRankDriven = dialogView.findViewById(R.id.tvRankDriven);

                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DrivingLicenseActivity.this);
                                    dialogBuilder.setView(dialogView);
                                    dialogBuilder.setIcon(getDrawable(R.drawable.ic_infor));
                                    dialogBuilder.setCancelable(false);

                                    //get image
                                    Bitmap bitmap = null;
                                    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);//icon mặc định
                                    if (drivingLicense.getAvatar() != null) { //Không cần xét trường hợp empty vì lúc import dữ liệu nếu empty thì setAvatar là null rồi
                                        String imageJPGInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_IMAGE + Commons.decodeString(drivingLicense.getAvatar()) + JPG;
                                        if (imageJPGInSD != null) {
                                            bitmap = BitmapFactory.decodeFile(imageJPGInSD);
                                        } else {
                                            String imagePNGInSD = Environment.getExternalStorageDirectory().getAbsolutePath() + FOLDER_IMAGE + Commons.decodeString(drivingLicense.getAvatar()) + PNG;
                                            bitmap = BitmapFactory.decodeFile(imagePNGInSD);
                                        }
                                    }
                                    ivAvatar.setImageBitmap(bitmap != null ? bitmap : icon);

                                    //set value
                                    tvNumberDrivingLicense.setText(Commons.decodeString(drivingLicense.getNumberDrivingLicense()));
                                    tvName.setText(Commons.decodeString(drivingLicense.getNameDriver()));
                                    tvBirthDay.setText(Commons.decodeString(drivingLicense.getBirthDay()));
                                    tvAddress.setText(Commons.decodeString(drivingLicense.getVillage()) + ", " + Commons.decodeString(drivingLicense.getTown()) + ", " + Commons.decodeString(drivingLicense.getDistrict()) + ", " + Commons.decodeString(drivingLicense.getProvinces()));
                                    tvCodePerson.setText(Commons.decodeString(drivingLicense.getCodePerson()));
                                    tvCodeDateRanger.setText(Commons.decodeString(drivingLicense.getCodeDateRanger()));
                                    tvCodeIssuedBy.setText(Commons.decodeString(drivingLicense.getCodeIssuedBy()));
                                    tvDateOfElistment.setText(Commons.decodeString(drivingLicense.getDateOfEnlistment()));
                                    tvDateOfRecuitment.setText(Commons.decodeString(drivingLicense.getDateOfRecruitment()));
                                    tvKilometerDriven.setText(Commons.decodeString(drivingLicense.getKilometersDriven()));
                                    tvTimeHasDrove.setText(Commons.decodeString(drivingLicense.getTimeHasDrove()));
                                    tvNumberOfficers.setText(Commons.decodeString(drivingLicense.getNumberOfficers()));
                                    tvRankDriven.setText(Commons.decodeString(drivingLicenseCatalog.getClassCatalog()));
                                    tvDateRegiter.setText(Commons.decodeString(drivingLicense.getDateRegister()));

                                    final AlertDialog dialog = dialogBuilder.create();
                                    dialog.show();

                                    TextView tvClose = dialogView.findViewById(R.id.tvClose);
                                    tvClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }, 100);
                        } else {
                            Toast.makeText(DrivingLicenseActivity.this, "Không có số đăng ký này", Toast.LENGTH_SHORT).show();
                        }
                        transactionTime.setEnd(System.currentTimeMillis());
                        Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Khởi tạo control
     * Created by Dong on 04-May-18
     */
    private void addControl() {
        ivBack = findViewById(R.id.ivBack);
        tilDrivingLicense = findViewById(R.id.tilDrivingLicense);
        edDrivingLicense = findViewById(R.id.edDrivingLicense);
        btnSearch = findViewById(R.id.btnSearch);
    }
}
