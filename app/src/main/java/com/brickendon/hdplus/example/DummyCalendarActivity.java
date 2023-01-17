package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CalendarView;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.utils.ProgressDialog;

public class DummyCalendarActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_calendar);

        //calendarView=findViewById(R.id.calender_event);

       /* Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Title");
        //intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Descritpooin");
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
        startActivity(intent);*/


        Dialog dialog=ProgressDialog.showProgressBar(DummyCalendarActivity.this);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog.dismisProgressBar(DummyCalendarActivity.this,dialog);
            }
        },5000);


    }
}