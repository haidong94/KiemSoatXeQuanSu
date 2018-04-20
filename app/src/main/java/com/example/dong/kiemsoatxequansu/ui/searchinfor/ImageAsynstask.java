package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.example.dong.kiemsoatxequansu.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

/**
 * Created by hhdong on 2/28/2018.
 */

public class ImageAsynstask extends AsyncTask<Bitmap, Void, StringBuilder> {
    private Context context;
    private ProgressDialog progressDialog;
    private ICallBack iCallBack;
    private StringBuilder stringBuilder = new StringBuilder();

    public ImageAsynstask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        this.iCallBack = (ICallBack) context;
    }

    public StringBuilder getStringFromImage() {
        return stringBuilder;
    }

    @Override
    protected StringBuilder doInBackground(Bitmap... params) {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

        try {
            if (!textRecognizer.isOperational())
                Log.e("ERROR", "Detector dependencies are not yet available");
            else {
                Frame frame = new Frame.Builder().setBitmap(params[0]).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);

                for (int i = 0; i < items.size(); ++i) {
                    TextBlock item = items.valueAt(i);
                    stringBuilder.append(item.getValue());//stringbuilder thì khi thêm không tạo thêm vùng nhớ. còn String thì có (immutable)
                    stringBuilder.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            progressDialog.setMessage(context.getResources().getString(R.string.progress));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(StringBuilder costs) {
        super.onPostExecute(costs);
        try {
            iCallBack.callBackStringFromImage();
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}