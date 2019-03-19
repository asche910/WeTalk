package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.helper.calendar.ZWCalendarView;
import com.asche.wetalk.util.StringUtils;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ClockInActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;


    private TextView textMoneyVirtual, textDayConti, textDayTotal;
    private Button btnClockIn;

    private ZWCalendarView calendarView;
    private TextView show;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clockin);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        textMoneyVirtual = findViewById(R.id.text_clockin_money_virtual);
        textDayConti = findViewById(R.id.text_clockin_day_continuous);
        textDayTotal = findViewById(R.id.text_clockin_day_total);
        btnClockIn = findViewById(R.id.btn_clockin_clockin);
        calendarView = findViewById(R.id.calendarView);
        show = findViewById(R.id.tv_calendar_show);

        textTitle.setText("签到日历");
        imgBack.setOnClickListener(this);
        btnClockIn.setOnClickListener(this);


        calendarView.setSelectListener(new ZWCalendarView.SelectListener() {
            @Override
            public void change(int year, int month) {
                show.setText(String.format("%s 年 %s 月", year, month));
            }

            @Override
            public void select(int year, int month, int day, int week) {
            }
        });

        //代码选中一个日期
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.selectDate(2017, 9, 3);
            }
        });

        findViewById(R.id.calendar_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showPreviousMonth();
            }
        });

        findViewById(R.id.calendar_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showNextMonth();
            }
        });

        findViewById(R.id.tv_calendar_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.backToday();
            }
        });

        HashMap<String, Boolean> sign = new HashMap<>();
        sign.put("2018-08-13", true);
        sign.put("2018-08-14", true);
        sign.put("2018-08-15", false);
        sign.put("2018-08-18", false);
        sign.put("2018-08-31", true);
        sign.put("2018-09-05", true);
        sign.put("2018-09-07", false);
        sign.put("2019-03-03", true);
        sign.put("2019-03-05", true);
        sign.put("2019-03-06", true);
        sign.put("2019-03-07", true);
        sign.put("2019-03-12", true);
        sign.put("2019-03-13", true);
        sign.put("2019-03-15", false);
        sign.put("2019-03-16", true);
        sign.put("2019-03-17", true);
        sign.put("2019-03-18", true);
        sign.put("2019-03-19", true);
        calendarView.setSignRecords(sign);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_clockin_clockin:
                String virtualMoney = textMoneyVirtual.getText().toString();
                textMoneyVirtual.setText(StringUtils.addInteger(virtualMoney, 7));
                textDayConti.setText(StringUtils.addOne(textDayConti.getText().toString()));
                textDayTotal.setText(StringUtils.addOne(textDayTotal.getText().toString()));
                btnClockIn.setText("签到成功！");
                Toast.makeText(this, "签到成功！积分+7", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
