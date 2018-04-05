package com.example.dong.kiemsoatxequansu.utils;

import java.text.DecimalFormat;

public class Commons {
    public static String convertMoneytoVND(double money){

        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");

        return decimalFormat.format(money);

    }

}
