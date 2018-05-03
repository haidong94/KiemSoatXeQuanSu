package com.example.dong.kiemsoatxequansu.utils;

import android.util.Base64;

import java.text.DecimalFormat;

public class Commons {
    /**
     * chuyển kiểu sang money
     * @param money kiểu double
     * @return
     * Created by Dong on 04-May-18
     */
    public static String convertMoneytoVND(double money) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(money);

    }

    /**
     * Mã hóa
     * @param valueString: trước khi mã hóa
     * @return chuỗi sau khi mã hóa
     * Created by Dong on 04-May-18
     */
    public static String encodeString(String valueString) {
        byte[] result = Base64.encode(valueString.getBytes(), Base64.DEFAULT);
        return new String(result);
    }

    /**
     * Giải mã
     * @param valueString chuỗi trước khi giải mã
     * @return chuỗi sau khi giải mã
     * Created by Dong on 04-May-18
     */
    public static String decodeString(String valueString) {
        byte[] result = Base64.decode(valueString.getBytes(), Base64.DEFAULT);
        return new String(result);
    }

    public static final String KEY_VEHICLE = "vehicle";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_CATEGORY_VEHICLE = "category_vehicle";
}
