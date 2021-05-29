package com.yutongdxTop.LaborDispatching.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {

    public static String getNowTimeString(String format) {  //按照参数给定的格式生成当前时间的字符串
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat(format);//设置日期格式
        Date date = new Date(currentTime);
        return formatter.format(date);
    }

    public static Date dateTransform(String time) {  //将时间字符串转换为Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ParsePosition position = new ParsePosition(0);
        return dateFormat.parse(time, position);
    }

    public static String stringTransform(Date date) {  //将Date转换为字符串
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//设置日期格式
        return dateFormat.format(date);
    }
}
