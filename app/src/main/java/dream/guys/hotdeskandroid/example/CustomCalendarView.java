package dream.guys.hotdeskandroid.example;

import android.content.Context;
import android.media.metrics.Event;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dream.guys.hotdeskandroid.R;

public class CustomCalendarView extends LinearLayout {

    //https://www.youtube.com/watch?v=ubvACPf5_tQ

    ImageButton nextButton,previousButton;
    TextView currentDate;
    GridView gridView;
    public static final int MAX_CALENDAR_DAYS=42;
    Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
    Context context;

    SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat=new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy",Locale.ENGLISH);

    List<Date> dates=new ArrayList<>();
    List<Event> eventList=new ArrayList<>();




    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, ImageButton nextButton) {
        super(context, attrs);
        this.context = context;
    }

    private void initializeLayout(){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view=inflater.inflate(R.layout.ca)
    }


}
