package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;


import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dong.kiemsoatxequansu.R;


/**
 * Created by DONG on 02-Jan-18.
 */

public class SearchLisenceVehileActivity extends AppCompatActivity implements ICallBack, ProfileFragment.ImageProfile {

    ImageView imageView, ivBack;

    Button btnProcess, btnSearch;

    TextView tvResult;

    StringBuilder stringBuilder;

    Uri selectedImage;

    private static int PICK_IMAGE_GALLERY = 1;

    private static int PICK_IMAGE_CAMERA = 0;

    private static final int MY_PERMISSIONS_REQUEST = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trathongtin);

        //Khởi tạo control
        addControl();
        //Sự kiện các control
        addEvent();
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                selectImage();
            }
        });

//        final Bitmap bitmap = BitmapFactory.decodeResource(
//                getApplicationContext().getResources(),
//                R.drawable.bienxemay2
//        );
//        imageView.setImageBitmap(bitmap);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                try {
                    Bitmap imageSelected = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    Bitmap noImage = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_camera)).getBitmap();
                    if (imageSelected == noImage) { //Nếu chưa chọn ảnh
                        Toast.makeText(SearchLisenceVehileActivity.this, getResources().getString(R.string.not_a_photo), Toast.LENGTH_SHORT).show();
                    } else {
                        ImageAsynstask loginTask = new ImageAsynstask(SearchLisenceVehileActivity.this);
                        loginTask.execute(imageSelected);
                        stringBuilder = loginTask.getStringFromImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(SearchLisenceVehileActivity.this, InforVehicleActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Setup control
     * Created by Dong on 10-Apr-18
     */
    private void addControl() {
        try {
            imageView = findViewById(R.id.imageView);
            ivBack = findViewById(R.id.ivBack);
            btnProcess = findViewById(R.id.btnProcess);
            btnSearch = findViewById(R.id.btnSearch);
            tvResult = findViewById(R.id.tvResult);

//            btnSearch.setAlpha(0.5f);
//            btnSearch.setEnabled(false);
//            tvResult.setAlpha(0.5f);
//            tvResult.setVisibility(View.VISIBLE);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Kiểm tra permission
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    selectedImage = data.getData();
                    break;
                case 1:
                    selectedImage = data.getData();
                    break;
            }
        }

        Bundle bundle = new Bundle();
        bundle.putString("Image", String.valueOf(selectedImage));
        FragmentManager fm = getSupportFragmentManager();
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        profileFragment.show(fm, "profile");
    }

    /**
     * Hiển thị dialog chọn ảnh từ bộ sưu tập hoặc chụp ảnh
     * Created by Dong on 10-Apr-18
     */
    public void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.gallery), getResources().getString(R.string.cancel)};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.choose_image_from));
                builder.setIcon(android.R.drawable.btn_star_big_on);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals(getResources().getString(R.string.take_photo))) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals(getResources().getString(R.string.gallery))) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals(getResources().getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            } else
                Toast.makeText(this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.camera_permission_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void callBackStringFromImage() {
        try {
            tvResult.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
           // btnSearch.setAlpha(1f);
            if (stringBuilder.toString().equals("")) {
                tvResult.setText("");
                Toast.makeText(this, getResources().getString(R.string.quality_image_bad), Toast.LENGTH_SHORT).show();
            } else if (stringBuilder.toString().length() > 20) {
                tvResult.setText("");
                Toast.makeText(this, getResources().getString(R.string.not_a_license_plate), Toast.LENGTH_SHORT).show();
            } else {
                tvResult.setText(stringBuilder.toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set ảnh được bắn
     *
     * @param uri bitmap ảnh
     *            Created by Dong on 10-Apr-18
     */
    @Override
    public void SendImage(Bitmap uri) {
        imageView.setImageBitmap(uri);

    }
}
