package dream.guys.hotdeskandroid.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import dream.guys.hotdeskandroid.R;

public class CalendarView extends LinearLayout
{

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

    // seasons' rainbow
   /* int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};*/
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
                updateCalendar();
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
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

    public void updateCalendar(HashSet<Date> events, int selectedPosition) {
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


    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        private HashSet<Date> eventDays;
        private LayoutInflater inflater;
        Calendar selectedMonth;
        int selectedPosition;

        public CalendarAdapter(Context context, ArrayList<Date> days,
                               HashSet<Date> eventDays, Calendar selectedMonth,
                               int selectedPosition) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            this.selectedMonth = selectedMonth;
            this.selectedPosition = selectedPosition;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            Date today = new Date();
            TextView dateBox, count;
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            dateBox = view.findViewById(R.id.cal_text_view);
            count = view.findViewById(R.id.tv_count);
            view.setBackgroundResource(0);
            if (eventDays != null) {
                for (Date eventDate : eventDays) {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year) {
                        dateBox.setBackgroundResource(R.drawable.btn_bg_app_theme);
                        ((TextView) view).setTextColor(Color.WHITE);
                        break;
                    }
                }
            }

            ((TextView) view).setTypeface(null, Typeface.NORMAL);
            ((TextView) view).setTextColor(Color.BLACK);

            // if (month != today.getMonth() || year != today.getYear()) {
           /* if (month !=  currentDate.get(Calendar.MONTH) || year !=  currentDate.get(Calendar.YEAR)) {
                ((TextView) view).setTextColor(getResources().getColor(R.color.greyed_out));
            } else */

            /*if (day == today.getDate()) {
                ((TextView) view).setTypeface(null, Typeface.BOLD);
                ((TextView) view).setTextColor(getResources().getColor(R.color.colorAppTheme));
            }*/

            ((TextView) view).setText(String.valueOf(date.getDate()));

            if (selectedPosition != -1 && position == selectedPosition) {
                dateBox.setBackgroundResource(R.drawable.btn_bg_app_theme);
                ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            }

            return view;
        }
    }


    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayLongPress(Date date, int pos);
    }
}

