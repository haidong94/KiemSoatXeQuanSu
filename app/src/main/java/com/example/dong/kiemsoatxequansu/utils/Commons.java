package com.example.dong.kiemsoatxequansu.utils;

import android.util.Base64;

import java.text.DecimalFormat;

public class Commons {
    public static String convertMoneytoVND(double money){

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");

        return decimalFormat.format(money);

    }

    public static String encodeString(String valueString){
        byte[] result = Base64.encode(valueString.getBytes(), Base64.DEFAULT);
        return new String(result);
    }

    public static String decodeString(String valueString){
        byte[] result = Base64.decode(valueString.getBytes(), Base64.DEFAULT);
        return new String(result);
    }

}
