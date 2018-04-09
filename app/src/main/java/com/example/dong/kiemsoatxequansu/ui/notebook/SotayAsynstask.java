package com.example.dong.kiemsoatxequansu.ui.notebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.dong.kiemsoatxequansu.R;
import com.example.dong.kiemsoatxequansu.data.importer.ObjectBoxImporter;

public class SotayAsynstask extends AsyncTask<Void,Void,Void> {

    Context context;
    ICallBackData iCallBackData;
    ProgressDialog progressDialog;
    public SotayAsynstask( Context context){
        this.context=context;
        progressDialog=new ProgressDialog(context,R.style.MyAlertDialogStyle);
        this.iCallBackData= (ICallBackData) context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ObjectBoxImporter objectBoxImporter = new ObjectBoxImporter(context.getResources(), (Activity) context);
        objectBoxImporter.importFromJson();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage(context.getResources().getString(R.string.download));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        iCallBackData.callBackData();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}