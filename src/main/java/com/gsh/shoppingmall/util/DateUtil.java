package com.gsh.shoppingmall.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gsh
 * @Title: DateUtil
 * @Package com.gsh.shoppingmall.util
 * @Description:
 * @date 2018/7/12 17:30
 */
public class DateUtil {

    /**
     * 根据时间戳返回星期几
     * @param timeStamp
     * @return int 1代表星期一
     */
    public static int timeStampGetDayOfWeek(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = sdf.format(new Date(Long.valueOf(timeStamp + "000")));
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = sdf.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int weekOfDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekOfDay;
    }

    /**
     * 获得当前时间 格式2018/10/01
     * @return String
     */
    public static String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR)+"";
        String month = calendar.get(Calendar.MONTH)+1+"";
        int dayInt = calendar.get(Calendar.DAY_OF_MONTH);
        String day = "";
        if (dayInt < 10){
            day = "0"+dayInt;
        }
        return year+"/"+month+"/"+day;
    }


}
