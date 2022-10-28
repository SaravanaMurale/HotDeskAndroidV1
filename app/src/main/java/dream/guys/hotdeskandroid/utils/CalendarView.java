package dream.guys.hotdeskandroid.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.ui.book.BookFragment;
import dream.guys.hotdeskandroid.ui.home.HomeFragment;

public class CalendarView extends LinearLayout
{

    private static final String TAG = "CalendarView";
    private static final String LOGTAG = "Calendar View";
    private static final int DAYS_COUNT = 42;
    private static final String DATE_FORMAT = "MMM yyyy";
    private String dateFormat;
    private Calendar currentDate = Calendar.getInstance();
    private EventHandler eventHandler = null;
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    public OnPrevNxtInClickable onPrevNxtInClickable;
    String  currentDay, currentMonth;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);
        loadDateFormat(attrs);
        assignUiElements(context);
        assignClickHandlers();
        updateCalendar();
    }
    public interface  OnPrevNxtInClickable{
        public void onPreviousClicked();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements(Context context) {
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);

        if (Utils.isThemeChanged(context)) {
            header.setBackgroundResource(R.drawable.btn_bg_app_theme);
            GradientDrawable drawable = (GradientDrawable) header.getBackground();
            drawable.setColor(Utils.getAppTheme(context));
        }
    }

    private void assignClickHandlers() {
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (eventHandler != null)
                    eventHandler.onPrevClicked(""+sdf.format(currentDate.getTime()));
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                Date now =Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                if (eventHandler != null){
                    if(!sdf.format(currentDate.getTime()).equalsIgnoreCase(sdf.format(now))){
                        Log.d(TAG, "onClick: "+sdf.format(currentDate.getTime())+"===="+sdf.format(now));
                        currentDate.add(Calendar.MONTH, -1);
                        eventHandler.onPrevClicked(""+sdf.format(currentDate.getTime()));
                    } else {
                        Toast.makeText(getContext(),"Cannot select previous month",Toast.LENGTH_LONG).show();
                        currentDate.add(Calendar.MONTH, 0);
                        eventHandler.onPrevClicked(Calendar.MONTH+sdf.format(currentDate.getTime()));
                    }

                }

            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long l) {
                if (eventHandler != null)
                    eventHandler.onDayLongPress((Date) adapterView.getItemAtPosition(position), position);


            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position), position);
                return true;
            }
        });
    }

    public void updateCalendar() {
        updateCalendar(null, -1);
    }

    public void updateCalendar(List<DeskRoomCountResponse> events, int selectedPosition) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        int selectedMonth = currentDate.get(Calendar.MONTH);

        grid.setAdapter(new CalendarAdapter(getContext(), cells,
                events, currentDate, selectedPosition));

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

//        int season = monthSeason[month];
//        int color = rainbow[season];

        //   header.setBackgroundColor(getResources().getColor(color));
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        private List<DeskRoomCountResponse> eventDays;
        private LayoutInflater inflater;
        Calendar selectedMonth;
        int selectedPosition;
        private int firstDayOfMonth;
        private int previousMonthMaxDays;

        public CalendarAdapter(Context context,
                               ArrayList<Date> days,
                               List<DeskRoomCountResponse> eventDays,
                               Calendar selectedMonth,
                               int selectedPosition) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            this.selectedMonth = selectedMonth;
            this.selectedPosition = selectedPosition;
            inflater = LayoutInflater.from(context);
            setFirstDayOfMonth();
            setPreviousMonthMax();
        }
        private void setFirstDayOfMonth() {
            Date now =Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar tempCalendar = (Calendar) currentDate.clone();
//            System.out.println("current temp date "+tempCalendar.getTime());
            tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
            currentDay = (String) DateFormat.format("dd",   now);
            currentMonth = (String) DateFormat.format("MM",   now);
            firstDayOfMonth = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1;
            Log.d(TAG, "setFirstDayOfMonth: "+firstDayOfMonth);
        }

        private void setPreviousMonthMax() {
            Calendar tempCalendar = (Calendar) currentDate.clone();
            tempCalendar.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 1);
            this.previousMonthMaxDays = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        @Override
        public int getCount() {
            return 42;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            int maxNumberOfDays = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date today = new Date();
            TextView dateBox, count;

            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            dateBox = view.findViewById(R.id.cal_text_view);
            count = view.findViewById(R.id.tv_count);
            view.setBackgroundResource(0);
            if (eventDays != null) {
                for (DeskRoomCountResponse eventDate : eventDays) {
                    if (Utils.compareTwoDate(Utils.convertStringToDateFormet(eventDate.getDate()),
                            Utils.getCurrentDate())==1){
                        ((TextView) count).setVisibility(GONE);
                        ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaGrey));
                    } else if (Utils.compareTwoDate(Utils.convertStringToDateFormet(eventDate.getDate()),
                            Utils.getCurrentDate())==2){
                        ((TextView) count).setVisibility(VISIBLE);
                        ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaBlack));

                    }else if (Utils.compareTwoDate(Utils.convertStringToDateFormet(eventDate.getDate()),
                            Utils.getCurrentDate())==3){
                        ((TextView) count).setVisibility(VISIBLE);
                        ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaBlack));
                    } else {
                        ((TextView) count).setVisibility(GONE);
                        ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaGrey));
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date(); //Get system date
                    try {
                        d = sdf.parse(eventDate.getDate());
                        if (d.getDate() == day &&
                                d.getMonth() == month &&
                                d.getYear() == year) {
                            if (eventDate.getAvailableCount()>0)
                                ((TextView) count).setText("" + eventDate.getAvailableCount());
                            else if (eventDate.getAssignedCount()>0)
                                ((TextView) count).setText("" + (eventDate.getAssignedCount() - eventDate.getUsedCount()));

                            Log.d("CalendarView", "getView: "+eventDate.getAvailableCount());
                            break;
                        } else {
                            ((TextView) count).setVisibility(GONE);
                            ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaGrey));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }

            ((TextView) dateBox).setTypeface(null, Typeface.NORMAL);
//            ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaBlack));

//            if (month !=  currentDate.get(Calendar.MONTH) || year !=  currentDate.get(Calendar.YEAR)) {
//                ((TextView) dateBox).setTextColor(getResources().getColor(R.color.figmaGrey));
//            }
//            System.out.println("mont check"+month+" "+today.getMonth());
            if (day == today.getDate() && month==today.getMonth()) {
                ((TextView) dateBox).setTypeface(null, Typeface.BOLD);
                ((TextView) dateBox).setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.figmaBlack));
            } else {
                ((TextView) dateBox).setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.figmaBackground));
            }

            ((TextView) dateBox).setText(String.valueOf(date.getDate()));

            /*
            if (selectedPosition != -1 && position == selectedPosition) {
                dateBox.setBackgroundResource(R.drawable.btn_bg_app_theme);
                ((TextView) dateBox).setTextColor(getResources().getColor(R.color.white));
            }
            */

            //below if condition greys out previous dates of past month
            if(month!=Integer.parseInt(currentMonth)){
                if (position < Integer.parseInt(currentDay)){
                    Log.d(TAG, "getView: date"+currentDay+"==="+month);
                    Log.d(TAG, "getView: "+firstDayOfMonth+"==="+currentDay);
                    Log.d("CardView : ", "getView: "+position);
                    int value = this.previousMonthMaxDays - Integer.parseInt(currentDay) + position + 1;
//                date.setText(String.valueOf(value));
                    dateBox.setTextColor(Color.rgb(166, 166, 166));
                    ((TextView) dateBox).setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.figmaBackground));
                    count.setVisibility(GONE);
                }
            } else {
                if (position < firstDayOfMonth){
                    Log.d(TAG, "getView: date else"+currentDay+"==="+month);

                    Log.d(TAG, "getView: "+firstDayOfMonth+"==="+currentDay);
                    Log.d("CardView : ", "getView: "+position);
                    int value = this.previousMonthMaxDays - firstDayOfMonth + position + 1;
//                date.setText(String.valueOf(value));
                    dateBox.setTextColor(Color.rgb(166, 166, 166));
                    ((TextView) dateBox).setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.figmaBackground));
                    count.setVisibility(GONE);
                }
            }

            /*
            if (position >= firstDayOfMonth && position < maxNumberOfDays + firstDayOfMonth) {
                int value = position - firstDayOfMonth + 1;
                System.out.println("bala today"+ currentDate.getTime());
//                Toast.makeText(getContext(), " betweeen"+currentDate, Toast.LENGTH_SHORT).show();
                if (currentDate.before(currentDate.getTime())) {
                    Log.d(TAG, "getView: "+currentDate.before(currentDate.getTime()));
//                    Toast.makeText(getContext(), " betweeen", Toast.LENGTH_SHORT).show();
//                    date.setText(String.valueOf(value));
                    dateBox.setTextColor(Color.rgb(166, 166, 166));
                    dateBox.setVisibility(VISIBLE);
                    count.setVisibility(VISIBLE);
                }
            }
            */

            if (position >= maxNumberOfDays + firstDayOfMonth) {
                int value = position - (maxNumberOfDays + firstDayOfMonth - 1);
                dateBox.setTextColor(Color.rgb(166, 166, 166));
                ((TextView) dateBox).setBackgroundTintList(ContextCompat.getColorStateList(getContext(),R.color.figmaBackground));
                dateBox.setVisibility(GONE);
                count.setVisibility(GONE);
            }
            return view;
        }
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayLongPress(Date date, int pos);
        void onPrevClicked(String month);
    }
}

