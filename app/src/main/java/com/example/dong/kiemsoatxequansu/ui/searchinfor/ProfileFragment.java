package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.dong.kiemsoatxequansu.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.support.annotation.Nullable;

/**
 *
 * Created by hhdong on 2/28/2018.
 */

public class ProfileFragment extends DialogFragment {
    ImageButton btnReturn,btnCheck;

    Uri uril;

    ImageProfile imageProfile;

    public interface ImageProfile{
        void SendImage(Bitmap uri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialogframent_profile,container,false);
        final CropImageView cropImageView=  view.findViewById(R.id.cropImageView);

        btnReturn=  view.findViewById(R.id.btnReturn);
        btnCheck=  view.findViewById(R.id.btnCheck);


        if (getArguments() != null){
            String uri = getArguments().getString("Image",null);
            if (uri != null){
                uril= Uri.parse(uri);
                cropImageView.setImageUriAsync(uril);

            }
        }

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProfile.SendImage(cropImageView.getCroppedImage(100,100));
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        imageProfile= (ImageProfile) context;
    }

}