package com.example.dong.kiemsoatxequansu.ui.searchinfor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;
import com.example.dong.kiemsoatxequansu.ui.searchdrivinglicense.ICallBackDataDrivingLicense;

public class DrivingLicensePlatesAsynctack extends AsyncTask<Void, Void, Void> {

    private Context context;
    private ICallBackDataDrivingLicensePlates iCallBackDataDrivingLicensePlates;
    private ProgressDialog progressDialog;

    public DrivingLicensePlatesAsynctack(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        this.iCallBackDataDrivingLicensePlates = (ICallBackDataDrivingLicensePlates) context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(context.getResources(), (Activity) context);
        objectBoxImporter.importDrivingLicensePlatesFromJson();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            progressDialog.setMessage(context.getResources().getString(R.string.download));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        try {
            iCallBackDataDrivingLicensePlates.callBackDataDrivingLicensePlates();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}