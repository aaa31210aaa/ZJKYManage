package com.zhks.safetyproduction.manager;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.TimeMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.entity.TimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;
import com.zhks.safetyproduction.R;
import com.github.gzuliyujiang.wheelpicker.DatePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.impl.UnitDateFormatter;
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout;
import com.zhks.safetyproduction.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DatePickerManager {
    public static void onYearMonthDay(Activity activity, boolean notCurrent, DateEntity defalutDateEntity, OnDatePickedListener listener) {
        DatePicker picker = new DatePicker(activity);
        DateWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
        wheelLayout.setCyclicEnabled(true);
        //wheelLayout.setDateLabel("年", "月", "日");
        wheelLayout.setDateFormatter(new UnitDateFormatter());
        if (notCurrent) {
            wheelLayout.setRange(DateEntity.target(2022, 01, 01), DateEntity.target(2072, 12, 31), defalutDateEntity);
        } else {
            wheelLayout.setRange(DateEntity.target(2000, 01, 01), defalutDateEntity, defalutDateEntity);
        }

//        wheelLayout.setRange(dateEntity, DateEntity.yearOnFuture(30));
        wheelLayout.setCurtainEnabled(true);
        wheelLayout.setCurtainColor(0xFFCC0000);
        wheelLayout.setIndicatorEnabled(true);
        wheelLayout.setIndicatorColor(0xFFFF0000);
        wheelLayout.setIndicatorSize(RelativeLayout.LayoutParams.MATCH_PARENT);
        wheelLayout.setTextColor(0xCCCC0000);
        wheelLayout.setTextSize(14 * activity.getResources().getDisplayMetrics().scaledDensity);
        //注：建议通过`setStyle`定制样式设置文字加大，若通过`setSelectedTextSize`设置，该解决方案会导致选择器展示时跳动一下
        //wheelLayout.setSelectedTextSize(16 * getResources().getDisplayMetrics().scaledDensity);
        wheelLayout.setStyle(R.style.WheelDefault);
        wheelLayout.setSelectedTextColor(0xFFFF0000);
        //wheelLayout.getYearLabelView().setTextColor(0xFF999999);
        //wheelLayout.getMonthLabelView().setTextColor(0xFF999999);
        picker.setOnDatePickedListener(listener);
        picker.getWheelLayout().setResetWhenLinkage(false);
        picker.show();
    }

    public static void onYearMonthDayTime(Activity activity, boolean notCurrent, DatimeEntity defalutDateEntity, OnDatimePickedListener listener) {
        DatimePicker picker = new DatimePicker(activity);
        final DatimeWheelLayout wheelLayout = picker.getWheelLayout();
        picker.setOnDatimePickedListener(listener);
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
        wheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND);
        wheelLayout.setCyclicEnabled(true);
        if (notCurrent) {
            wheelLayout.setRange(DatimeEntity.now(), DatimeEntity.yearOnFuture(50));
        } else {
            wheelLayout.setRange(DatimeEntityTarget(Calendar.getInstance().get(Calendar.YEAR), 1, 1, 0, 0), DatimeEntityTarget(2072, 12, 31, 23, 59), defalutDateEntity);
        }
        wheelLayout.setDateLabel("年", "月", "日");
        wheelLayout.setTimeLabel("时", "分", "秒");
        picker.show();
    }

    public static DatimeEntity DatimeEntityTarget(int year, int month, int day, int hour, int minute) {
        try {
            DatimeEntity entity = new DatimeEntity();
            String time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
            Date date = DateUtils.formatter.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            DateEntity dateEntity = new DateEntity();
            dateEntity.setYear(year);
            dateEntity.setMonth(month);
            dateEntity.setDay(day);
            TimeEntity timeEntity = new TimeEntity();
            timeEntity.setHour(hour);
            timeEntity.setMinute(minute);
            entity.setDate(dateEntity);
            entity.setTime(timeEntity);
            return entity;
        } catch (ParseException e) {
            e.printStackTrace();
            return new DatimeEntity();
        }
    }
}
