package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

/**
 * Created by hhdong on 2/28/2018.
 */

public class ImageAsynstask extends AsyncTask<Bitmap,Void,StringBuilder> {

    Context context;
    ProgressDialog progressDialog;
    ICallBack iCallBack;
    StringBuilder stringBuilder = new StringBuilder();
    public ImageAsynstask(Context context){
        this.context=context;
        progressDialog=new ProgressDialog(context);
        this.iCallBack= (ICallBack) context;
    }

    public StringBuilder getStringFromImage() {
        return stringBuilder;
    }
    @Override
    protected StringBuilder doInBackground(Bitmap... params) {


        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

        if(!textRecognizer.isOperational())
            Log.e("ERROR","Detector dependencies are not yet available");
        else{
            Frame frame = new Frame.Builder().setBitmap(params[0]).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            for(int i=0;i<items.size();++i)
            {
                TextBlock item = items.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Waiting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(StringBuilder costs) {
        super.onPostExecute(costs);
        iCallBack.callBackStringFromImage();
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}