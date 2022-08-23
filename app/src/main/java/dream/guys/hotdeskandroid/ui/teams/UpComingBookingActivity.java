package dream.guys.hotdeskandroid.ui.teams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivityUpComingBookingBinding;

public class UpComingBookingActivity extends AppCompatActivity {

    int day, month, year;
    HorizontalCalendar horizontalCalendar;
    String currendate = "",selectedDate="";
    HorizontalCalendarView calendarView;
    ActivityUpComingBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_booking);

        binding = ActivityUpComingBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uiInit();

    }

    private void uiInit() {

        calendarView = binding.calendarView;
        /*Calendar startDate = Calendar.getInstance();

        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
        currendate = String.valueOf(year + "-" + (month + 1) + "-" + day);
        selectedDate = String.valueOf(day + "-" + (month + 1) + "-" + year);
        startDate.set(year, month);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);*/

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);

        //final HorizontalCalendar horizontalCalendar
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(4)
                .mode(HorizontalCalendar.Mode.MONTHS)
                .configure()
                .formatMiddleText("MMM")
                .formatBottomText("yyyy")
                .showTopText(false)
                .showBottomText(true)
                .selectedDateBackground(getResources().getDrawable(R.drawable.sel_date_bg))
                .selectorColor(R.color.white)
                .end()
                .defaultSelectedDate(startDate).build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                currendate = date.get(Calendar.YEAR) + "-" +
                        (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE);

                try {
                    currendate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
                    selectedDate = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });

    }
}