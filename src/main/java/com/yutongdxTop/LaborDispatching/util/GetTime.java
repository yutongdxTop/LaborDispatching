package com.yutongdxTop.LaborDispatching.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
    public static String getTime() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");//设置日期格式
        Date date = new Date(currentTime);
        return formatter.format(date);
    }

    public static String getDate() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(currentTime);
        return dateFormat.format(date);
    }
}
