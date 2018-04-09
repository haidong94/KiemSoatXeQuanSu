package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
 *
 * Created by DONG on 02-Jan-18.
 */

public class TraThongTinActivity extends AppCompatActivity implements ICallBack , ProfileFragment.ImageProfile{
    Toolbar toolbar;

    ImageView imageView;

    Button btnProcess;

    EditText tvResult;

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

        addControl();

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
            @Override
            public void onClick(View view) {

                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                ImageAsynstask loginTask=new ImageAsynstask(TraThongTinActivity.this);
                loginTask.execute(bitmap);
                stringBuilder=loginTask.getStringFromImage();

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
            btnProcess = findViewById(R.id.btnProcess);
            tvResult=findViewById(R.id.tvResult);

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
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    private void checkPermission() {
        String[] listPermission = new String[] {android.Manifest.permission.CAMERA,
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
        if (isOn){
            ActivityCompat.requestPermissions(this, listPermission, MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    selectedImage = data.getData();
                    break;
                case 1:
                    selectedImage = data.getData();
                    break;
            }
        }

        Bundle bundle=new Bundle();
        bundle.putString("Image", String.valueOf(selectedImage));
        FragmentManager fm=getSupportFragmentManager();
        ProfileFragment profileFragment=new ProfileFragment();
        profileFragment.setArguments(bundle);
        profileFragment.show(fm,"profile");
    }
    public void selectImage(){
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {getResources().getString(R.string.take_photo), getResources().getString(R.string.gallery),getResources().getString(R.string.cancel)};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.choose_image_from));
                builder.setIcon(android.R.drawable.btn_star_big_on);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
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

    @Override
    public void callBackStringFromImage() {
        tvResult.setVisibility(View.VISIBLE);
        if(stringBuilder.toString().equals("")){
            tvResult.getText().clear();
            Toast.makeText(this,"Chất lượng ảnh chưa tốt. Bạn có thể nhập tay",Toast.LENGTH_SHORT).show();
        }else if (stringBuilder.toString().length()>20){
            tvResult.getText().clear();
            Toast.makeText(this,"Không phải định dạng biển số xe",Toast.LENGTH_SHORT).show();
        }else {
            tvResult.setText(stringBuilder.toString());
        }


    }

    @Override
    public void SendImage(Bitmap uri) {
            imageView.setImageBitmap(uri);
           // setPreference(this,"image1", BitMapToString(uri));

    }
}
