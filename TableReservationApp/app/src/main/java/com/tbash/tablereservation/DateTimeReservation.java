package com.tbash.tablereservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DateTimeReservation extends AppCompatActivity {

    Button pickDate_btn,pickTime_btn,cekDT_btn;

    String dateReserv,timeReserv;

    Integer year,month,day,hour,minute;

    TextView tv1_date, tv2_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_reservation);

        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month=calendar.get(Calendar.MONTH);
        this.day=calendar.get(Calendar.DAY_OF_MONTH);
        this.hour=calendar.get(Calendar.HOUR_OF_DAY);
        this.minute=calendar.get(Calendar.MINUTE);

        pickDate_btn=findViewById(R.id.button_PickDate);
        pickTime_btn=findViewById(R.id.button_PickTime);
        cekDT_btn=findViewById(R.id.button_CekDT);
        tv1_date=findViewById(R.id.textView_date);
        tv2_time=findViewById(R.id.textView_time);

        pickDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDate();
            }
        });

        pickTime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogTime();
            }
        });

        cekDT_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateReserv=tv1_date.getText().toString();
                timeReserv=tv2_time.getText().toString();

                if (TextUtils.isEmpty(dateReserv) || TextUtils.isEmpty(timeReserv)) {
                    Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                } else {
                    openTableReservAct();
                }

                //openTableReservAct();
            }
        });

    }

    public void openDialogDate(){
        DatePickerDialog dialogDate= new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //pickDate_btn.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
                tv1_date.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
            }
        },year,month,day);
        dialogDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialogDate.show();
        dialogDate.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.parseColor("#046C42"));
        dialogDate.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#046C42"));

    }

    public void openDialogTime(){
        TimePickerDialog dialogTime= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                tv2_time.setText(String.valueOf(hours)+":"+String.valueOf(minutes)+":00");
            }
        },hour,minute,true);
        dialogTime.show();
        dialogTime.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.parseColor("#046C42"));
        dialogTime.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#046C42"));
    }

    public void openTableReservAct(){
        Intent intentForTableReserv=new Intent(this, TableResevationStatus.class);
        intentForTableReserv.putExtra("date",dateReserv);
        intentForTableReserv.putExtra("time",timeReserv);
        startActivity(intentForTableReserv);
        finish();
    }

}