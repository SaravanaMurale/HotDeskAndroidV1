package dream.guys.hotdeskandroid.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;

import java.util.Calendar;

import dream.guys.hotdeskandroid.R;

public class DummyCalendarActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_calendar);

        //calendarView=findViewById(R.id.calender_event);

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Title");
        //intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Descritpooin");
        intent.putExtra(CalendarContract.Events.ALL_DAY, true);
        startActivity(intent);



    }
}