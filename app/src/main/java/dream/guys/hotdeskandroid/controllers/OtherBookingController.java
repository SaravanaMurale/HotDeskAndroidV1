package dream.guys.hotdeskandroid.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.OtherBookingAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.OtherBookingRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherBookingController {

    private final Context context;
    private int selectedIcon;
    private String calSelectedDate;
    private String isFrom;
    private BookingForEditResponse bookingForEditResponse;
    private Dialog dialog;
    private BookingListResponse.DayGroup.CalendarEntry calendarEntry;
    private Date date;
    private int type = 0;
    private BottomSheetDialog addEditBottomSheet;
    private BottomSheetDialog editBookingSheet;
    private LanguagePOJO.AppKeys appKeysPage;
    private TextView tvRepeatTxt;
    private int enableCurrentWeek = -1;
    private boolean repeatActiveStatus = false;
    private BottomSheetDialog repeatBottomSheetDialog;
    private String repeatType = "none";

    public OtherBookingController(Context context, int selectedIcon, String calSelectedDate) {
        this.context = context;
        this.selectedIcon = selectedIcon;
        this.calSelectedDate = calSelectedDate;
        this.isFrom = "book";
        getAddEditDesk(selectedIcon, calSelectedDate);
    }

    public OtherBookingController(Context context,
                                  BookingListResponse.DayGroup.CalendarEntry calendarEntry,
                                  Date date, String isFrom) {
        this.context = context;
        this.calendarEntry = calendarEntry;
        this.date = date;
        this.isFrom = isFrom;
        try {
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date d1;
            d1 = sdf3.parse("" + this.date);
            String str = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(d1);
            this.calSelectedDate = str + "Z";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendarEntry.getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
            selectedIcon = 4;
            type = 9;
        } else if (calendarEntry.getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
            selectedIcon = 5;
            type = 18;
        } else if (calendarEntry.getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
            selectedIcon = 6;
            type = 6;
        } else if (calendarEntry.getUsageTypeAbbreviation().equalsIgnoreCase("TR")) {
            selectedIcon = 7;
            type = 8;
        }

        homeEditBooking(calendarEntry.getFrom(), calendarEntry.getMyto());
    }

    private void getAddEditDesk(int code, String date) {
        if (Utils.isNetworkAvailable(context)) {
            dialog = ProgressDialog.showProgressBar(context);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(
                    SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID),
                    SessionHandler.getInstance().getInt(context, AppConstants.TEAMMEMBERSHIP_ID),
                    date, date);

            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    try {
                        bookingForEditResponse = response.body();

                        checkBookings();

                    } catch (Exception e) {
                        e.printStackTrace();
                        ProgressDialog.dismissProgressBar(dialog);
                    }
                    ProgressDialog.dismissProgressBar(dialog);
                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    ProgressDialog.dismissProgressBar(dialog);
                }
            });

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void checkBookings() {

        List<BookingForEditResponse.Bookings> bookingsList = new ArrayList<>();

        for (BookingForEditResponse.Bookings bookings : bookingForEditResponse.getBookings()) {
            if (selectedIcon == 4) {
                if (bookings.getUsageTypeId() == 9) {
                    bookingsList.add(bookings);
                }
            } else if (selectedIcon == 5) {
                if (bookings.getUsageTypeId() == 18) {
                    bookingsList.add(bookings);
                }
            } else if (selectedIcon == 6) {
                if (bookings.getUsageTypeId() == 6) {
                    bookingsList.add(bookings);
                }
            } else if (selectedIcon == 7) {
                if (bookings.getUsageTypeId() == 8) {
                    bookingsList.add(bookings);
                }
            }
        }

        if (bookingsList.size() > 0) {
            openEditBookingSheet(bookingsList);
        } else {
            openNewBookingSheet("new", "", "", 0);
        }
    }

    public void openNewBookingSheet(String from, String startTimeStr, String endTimeStr, int bookingId) {

        addEditBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        addEditBottomSheet.setContentView(((Activity) context).getLayoutInflater().inflate
                (R.layout.bottom_sheet_other_bookings_add_edit, new RelativeLayout(context)));

        TextView bookingName = addEditBottomSheet.findViewById(R.id.bookingName);
        TextView tvDate = addEditBottomSheet.findViewById(R.id.tvDate);
        TextView editDelete = addEditBottomSheet.findViewById(R.id.editDelete);
        TextView startTime = addEditBottomSheet.findViewById(R.id.start_time);
        TextView endTime = addEditBottomSheet.findViewById(R.id.end_time);
        TextView tvClose = addEditBottomSheet.findViewById(R.id.tvClose);
        TextView tvBook = addEditBottomSheet.findViewById(R.id.tvBook);
        TextView tvRepeat = addEditBottomSheet.findViewById(R.id.tv_repeat);
        tvRepeatTxt = addEditBottomSheet.findViewById(R.id.repeat);
        RelativeLayout repeatBlock = addEditBottomSheet.findViewById(R.id.repeatBlock);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), startTime, "Start Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), false);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), endTime, "End Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), false);
            }
        });

        tvBook.setOnClickListener(view -> {
            addEditBottomSheet.dismiss();
            if (from.equalsIgnoreCase("new"))
                callBooking(from, startTime.getText().toString(), endTime.getText().toString());
            else
                callEditBooking(from, startTime.getText().toString(), endTime.getText().toString(),
                        bookingId);

        });

        editDelete.setOnClickListener(view -> {
            addEditBottomSheet.dismiss();
            callDeleteBooking(bookingId);
        });

        tvRepeatTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatBottomSheetDialog();
            }
        });

         /*4 == 9 - remote - WFH
       5 == 18 - sick leave
       6 ==  6 - holiday
       7 == 8 - training*/
        if (from.equalsIgnoreCase("new")) {
            if (selectedIcon == 4) {
                bookingName.setText("Working remotely");
                type = 9;
            } else if (selectedIcon == 5) {
                bookingName.setText("Log sickness");
                type = 18;
            } else if (selectedIcon == 6) {
                bookingName.setText("Book holiday");
                type = 6;
            } else if (selectedIcon == 7) {
                bookingName.setText("Book training");
                type = 8;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date objDate = dateFormat.parse(calSelectedDate);
                if (Utils.compareTwoDate(objDate, Utils.getCurrentDate()) == 2) {
                    repeatBlock.setVisibility(View.VISIBLE);
                } else {
                    repeatBlock.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            editDelete.setVisibility(View.GONE);

            if (Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                    Utils.getCurrentDate()) == 2) {
                startTime.setText(Utils.currentTimeWithExtraMins(2));
                endTime.setText(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
            } else {
                startTime.setText(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                endTime.setText(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
            }

            /*if (Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                    Utils.getCurrentDate()) == 2) {
                startTime.setText(Utils.setStartNearestThirtyMinToMeeting(Utils.getCurrentTime()));
                endTime.setText(Utils.setStartNearestThirtyMinToMeeting(startTime.getText().toString()));
            } else {
                startTime.setText(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                endTime.setText(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
            }
*/
            tvDate.setText(Utils.calendarDay10thMonthformat(
                    Utils.convertStringToDateFormet(calSelectedDate)));

        } else {

            if (editBookingSheet != null && editBookingSheet.isShowing())
                editBookingSheet.dismiss();

            if (selectedIcon == 4) {
                bookingName.setText("Edit working remotely");
                type = 9;
            } else if (selectedIcon == 5) {
                bookingName.setText("Edit sickness");
                type = 18;
            } else if (selectedIcon == 6) {
                bookingName.setText("Edit holiday");
                type = 6;
            } else if (selectedIcon == 7) {
                bookingName.setText("Edit training");
                type = 8;
            }

            tvBook.setText("Save changes");
            startTime.setText(startTimeStr);
            endTime.setText(endTimeStr);
            editDelete.setVisibility(View.VISIBLE);
            repeatBlock.setVisibility(View.GONE);

            tvDate.setText(Utils.calendarDay10thMonthYearformat(
                    Utils.convertStringToDateFormet(calSelectedDate)));

            /*if (bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size() - 1)
                    .getFrom() != null) {

                startTime.setText(Utils.splitTime(bookingForEditResponse.getBookings()
                        .get(bookingForEditResponse.getBookings().size() - 1)
                        .getMyto()));
            }*/
        }

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditBottomSheet.dismiss();
            }
        });

        addEditBottomSheet.show();
    }

    private void openEditBookingSheet(List<BookingForEditResponse.Bookings> bookingsList) {
        editBookingSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        /*editBookingSheet.setContentView(((Activity) context).getLayoutInflater().inflate
                (R.layout.bottom_sheet_other_bookings_list, new RelativeLayout(context)));*/

        View view = View.inflate(context, R.layout.bottom_sheet_other_bookings_list, null);
        editBookingSheet.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        TextView bookingName = editBookingSheet.findViewById(R.id.bookingName);
        TextView tvDate = editBookingSheet.findViewById(R.id.tvDate);
        RecyclerView bookedListRecycler = editBookingSheet.findViewById(R.id.bookedListRecycler);
        TextView tvClose = editBookingSheet.findViewById(R.id.tvClose);
        TextView tvAddNew = editBookingSheet.findViewById(R.id.tvAddNew);

        tvDate.setText(Utils.calendarDay10thMonthYearformat
                (Utils.convertStringToDateFormet(calSelectedDate)));
        setAdapter(bookedListRecycler, bookingsList);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBookingSheet.dismiss();
            }
        });

        tvAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBookingSheet.dismiss();
                openNewBookingSheet("new", "", "", 0);
            }
        });

        editBookingSheet.show();
    }

    public void homeEditBooking(String startTimeStr, String endTimeStr) {
        BottomSheetDialog addEditBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        addEditBottomSheet.setContentView(((Activity) context).getLayoutInflater().inflate
                (R.layout.bottom_sheet_other_bookings_add_edit, new RelativeLayout(context)));

        TextView bookingName = addEditBottomSheet.findViewById(R.id.bookingName);
        TextView tvDate = addEditBottomSheet.findViewById(R.id.tvDate);
        TextView editDelete = addEditBottomSheet.findViewById(R.id.editDelete);
        TextView startTime = addEditBottomSheet.findViewById(R.id.start_time);
        TextView endTime = addEditBottomSheet.findViewById(R.id.end_time);
        TextView tvClose = addEditBottomSheet.findViewById(R.id.tvClose);
        TextView tvBook = addEditBottomSheet.findViewById(R.id.tvBook);
        RelativeLayout repeatBlock = addEditBottomSheet.findViewById(R.id.repeatBlock);

        tvBook.setText("Save changes");
        tvDate.setText(Utils.calendarDay10thMonthYearformat(date));


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date objDate = dateFormat.parse(calSelectedDate);
            if (Utils.compareTwoDate(objDate, Utils.getCurrentDate()) == 2) {
                repeatBlock.setVisibility(View.VISIBLE);
            } else {
                repeatBlock.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), startTime, "Start Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), false);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), endTime, "End Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), false);
            }
        });

        tvBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditBottomSheet.dismiss();
                callEditBooking("edit", startTime.getText().toString(), endTime.getText()
                        .toString(), calendarEntry.getId());
            }
        });

        if (selectedIcon == 4) {
            bookingName.setText("Edit working remotely");
            type = 9;
        } else if (selectedIcon == 5) {
            bookingName.setText("Edit sickness");
            type = 18;
        } else if (selectedIcon == 6) {
            bookingName.setText("Edit holiday");
            type = 6;
        } else if (selectedIcon == 7) {
            bookingName.setText("Edit training");
            type = 8;
        }

        startTime.setText(Utils.splitTime(startTimeStr));
        endTime.setText(Utils.splitTime(endTimeStr));
        editDelete.setVisibility(View.VISIBLE);


        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addEditBottomSheet.isShowing())
                    addEditBottomSheet.dismiss();
            }
        });

        editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditBottomSheet.dismiss();
                callDeleteBooking(calendarEntry.getId());
            }
        });

        addEditBottomSheet.show();
    }

    private void setAdapter(RecyclerView bookedListRecycler, List<BookingForEditResponse.Bookings> bookingsList) {
        OtherBookingAdapter otherBookingAdapter = new OtherBookingAdapter(context, selectedIcon,
                bookingsList, this);
        bookedListRecycler.setAdapter(otherBookingAdapter);
    }

    private void repeatBottomSheetDialog() {
        appKeysPage = Utils.getAppKeysPageScreenData(context);
        repeatBottomSheetDialog = new BottomSheetDialog(context,
                R.style.AppBottomSheetDialogTheme);
        repeatBottomSheetDialog.setContentView((((Activity) context).getLayoutInflater().
                inflate(R.layout.dialog_bottom_sheet_other_booking_repeat, new RelativeLayout(context))));

        TextView titleRepeat = repeatBottomSheetDialog.findViewById(R.id.titleRepeat);
        TextView tv_none = repeatBottomSheetDialog.findViewById(R.id.tv_none);
        TextView tv_daily = repeatBottomSheetDialog.findViewById(R.id.tv_daily);
        TextView editBookingBack = repeatBottomSheetDialog.findViewById(R.id.editBookingBack);

        titleRepeat.setText(appKeysPage.getRepeat());
        tv_none.setText(appKeysPage.getNone());
        tv_daily.setText(appKeysPage.getDaily());
        editBookingBack.setText(appKeysPage.getBack());

        ConstraintLayout cl_daily_layout = repeatBottomSheetDialog.findViewById(R.id.cl_daily_layout);
        ConstraintLayout cl_weekly_layout = repeatBottomSheetDialog.findViewById(R.id.cl_weekly_layout);
        //None Block
        ConstraintLayout cl_none = repeatBottomSheetDialog.findViewById(R.id.cl_none);
        //Daily Block
        ConstraintLayout cl_daily = repeatBottomSheetDialog.findViewById(R.id.cl_daily);
        ConstraintLayout cl_weekly = repeatBottomSheetDialog.findViewById(R.id.cl_weekly);
        ConstraintLayout cl_monthly = repeatBottomSheetDialog.findViewById(R.id.cl_monthly);
        ConstraintLayout cl_yearly = repeatBottomSheetDialog.findViewById(R.id.cl_yearly);
        ImageView iv_none = repeatBottomSheetDialog.findViewById(R.id.iv_none);
        ImageView iv_daily = repeatBottomSheetDialog.findViewById(R.id.iv_daily);
        ImageView iv_weekly = repeatBottomSheetDialog.findViewById(R.id.iv_weekly);
        ImageView iv_monthly = repeatBottomSheetDialog.findViewById(R.id.iv_monthly);
        ImageView iv_yearly = repeatBottomSheetDialog.findViewById(R.id.iv_yearly);

        TextView editBookingContinue = repeatBottomSheetDialog.findViewById(R.id.editBookingContinue);
        TextView tv_repeat = repeatBottomSheetDialog.findViewById(R.id.tv_repeat);

        TextView tv_interval = repeatBottomSheetDialog.findViewById(R.id.tv_interval);
        TextView tv_until = repeatBottomSheetDialog.findViewById(R.id.tv_until);
        TextView tv_interval_weekly = repeatBottomSheetDialog.findViewById(R.id.tv_interval_weekly);
        TextView tv_day = repeatBottomSheetDialog.findViewById(R.id.tv_day);
        TextView tv_unit = repeatBottomSheetDialog.findViewById(R.id.tv_unit);

        TextView tv_until_txt = repeatBottomSheetDialog.findViewById(R.id.tv_until_txt);
        TextView tv_interval_txt = repeatBottomSheetDialog.findViewById(R.id.tv_interval_txt);

        //None Block Clicked
        cl_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repeatActiveStatus = false;

                tvRepeatTxt.setText(appKeysPage.getNone());

                repeatType = "none";
                iv_none.setVisibility(View.VISIBLE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.GONE);
                tv_repeat.setVisibility(View.GONE);
            }
        });

        //Daily Block Clicked
        cl_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRepeatTxt.setText(appKeysPage.getDaily());
                repeatType = "daily";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.VISIBLE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                //Get Current Week End Date
                Date date = Utils.getCurrentWeekEndDate();
                //Set Figma format
                tv_until.setText(Utils.getDateFormatToSetInRepeat(date) + " (end of Week)");

                cl_daily_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
                cl_weekly_layout.setVisibility(View.GONE);

                tv_interval_txt.setText(appKeysPage.getInterval());
                tv_until_txt.setText(appKeysPage.getUntil());
            }
        });

        //Until Block Clicked
        tv_until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUntil();
            }
        });


        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatActiveStatus = false;
                tvRepeatTxt.setText(appKeysPage.getNone());
                repeatBottomSheetDialog.dismiss();
            }
        });

        tv_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeeks();
            }
        });


        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        tv_interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_interval_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openIntervalsDialog(repeatType);
            }
        });


        cl_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatType = "weekly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.VISIBLE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
            }
        });
        cl_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatType = "monthly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.VISIBLE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
            }
        });

        cl_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatType = "yearly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.VISIBLE);
                cl_daily_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
                cl_weekly_layout.setVisibility(View.GONE);
            }
        });

        repeatBottomSheetDialog.show();
    }

    private void openUntil() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((((Activity) context).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_until,
                new RelativeLayout(context))));

        TextView titleUntil = bottomSheetDialog.findViewById(R.id.titleUntil);

        ConstraintLayout cl_forever = bottomSheetDialog.findViewById(R.id.cl_forever);
        ConstraintLayout cl_specific = bottomSheetDialog.findViewById(R.id.cl_specific);
        ImageView iv_forever = bottomSheetDialog.findViewById(R.id.iv_forever);
        ImageView iv_specific = bottomSheetDialog.findViewById(R.id.iv_specific);
        android.widget.CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);
        TextView tv_forever = bottomSheetDialog.findViewById(R.id.tv_forever);
        TextView tv_specific = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView editBookingContinue = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView repeatBookContinue = bottomSheetDialog.findViewById(R.id.editBookingContinue);

        titleUntil.setText(appKeysPage.getRepeatUntill());
        tv_specific.setText(appKeysPage.getSpecificDate());

        calendar_view.setVisibility(View.GONE);

        Date date = Utils.getCurrentWeekEndDate();
        tv_forever.setText(Utils.getDateFormatToSetInRepeat(date) + " (end of Week)");

        enableCurrentWeek = Utils.getDifferenceBetweenTwoDates(calSelectedDate);

        calendar_view.setMinDate(System.currentTimeMillis() - 1000);
        calendar_view.setMaxDate(System.currentTimeMillis() + enableCurrentWeek * 24 * 60 * 60 * 1000);

        cl_forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_forever.setVisibility(View.VISIBLE);
                iv_specific.setVisibility(View.GONE);
                calendar_view.setVisibility(View.GONE);
                repeatActiveStatus = true;
                bottomSheetDialog.dismiss();
                repeatBottomSheetDialog.dismiss();
            }
        });

        //specific date clicked
        cl_specific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_forever.setVisibility(View.GONE);
                iv_specific.setVisibility(View.VISIBLE);
                calendar_view.setVisibility(View.VISIBLE);

            }
        });


        //Calendar View
        calendar_view.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView view, int year, int month, int dayOfMonth) {

                repeatActiveStatus = true;

                //Coming WeekendDate
                LocalDate weekEndDate = LocalDate.of(year, month + 1, dayOfMonth);

                //Selected Date
                String[] words = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)).split("-");
                int selectedYear = Integer.parseInt(words[0]);
                int selectedMonth = Integer.parseInt(words[1]);
                int selectedDay = Integer.parseInt(words[2]);
                LocalDate currentSelectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);

                //Find Difference between 2 date
                Period difference = Period.between(currentSelectedDate, weekEndDate);
                enableCurrentWeek = difference.getDays();

                bottomSheetDialog.dismiss();
                repeatBottomSheetDialog.dismiss();

            }
        });


        bottomSheetDialog.show();
    }

    private void openWeeks() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((((Activity) context).getLayoutInflater().
                inflate(R.layout.dialog_bottom_sheet_week,
                        new RelativeLayout(context))));
        bottomSheetDialog.show();
    }

    private void openIntervalsDialog(String type) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((((Activity) context).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_interval,
                new RelativeLayout(context))));

        ConstraintLayout cl_1 = bottomSheetDialog.findViewById(R.id.cl_1);
        TextView tv_1 = bottomSheetDialog.findViewById(R.id.tv_1);
        ImageView iv_1 = bottomSheetDialog.findViewById(R.id.iv_1);
        ConstraintLayout cl_2 = bottomSheetDialog.findViewById(R.id.cl_2);
        TextView tv_2 = bottomSheetDialog.findViewById(R.id.tv_2);
        ImageView iv_2 = bottomSheetDialog.findViewById(R.id.iv_2);
        ConstraintLayout cl_3 = bottomSheetDialog.findViewById(R.id.cl_3);
        TextView tv_3 = bottomSheetDialog.findViewById(R.id.tv_3);
        ImageView iv_3 = bottomSheetDialog.findViewById(R.id.iv_3);
        ConstraintLayout cl_4 = bottomSheetDialog.findViewById(R.id.cl_4);
        TextView tv_4 = bottomSheetDialog.findViewById(R.id.tv_4);
        ImageView iv_4 = bottomSheetDialog.findViewById(R.id.iv_4);
        ConstraintLayout cl_5 = bottomSheetDialog.findViewById(R.id.cl_5);
        TextView tv_5 = bottomSheetDialog.findViewById(R.id.tv_5);
        ImageView iv_5 = bottomSheetDialog.findViewById(R.id.iv_5);
        ConstraintLayout cl_6 = bottomSheetDialog.findViewById(R.id.cl_6);
        TextView tv_6 = bottomSheetDialog.findViewById(R.id.tv_6);
        ImageView iv_6 = bottomSheetDialog.findViewById(R.id.iv_6);
        ConstraintLayout cl_7 = bottomSheetDialog.findViewById(R.id.cl_7);
        TextView tv_7 = bottomSheetDialog.findViewById(R.id.tv_7);
        ImageView iv_7 = bottomSheetDialog.findViewById(R.id.iv_7);
        ConstraintLayout cl_8 = bottomSheetDialog.findViewById(R.id.cl_8);
        TextView tv_8 = bottomSheetDialog.findViewById(R.id.tv_8);
        ImageView iv_8 = bottomSheetDialog.findViewById(R.id.iv_8);
        ConstraintLayout cl_9 = bottomSheetDialog.findViewById(R.id.cl_9);
        TextView tv_9 = bottomSheetDialog.findViewById(R.id.tv_9);
        ImageView iv_9 = bottomSheetDialog.findViewById(R.id.iv_9);
        ConstraintLayout cl_10 = bottomSheetDialog.findViewById(R.id.cl_10);
        TextView tv_10 = bottomSheetDialog.findViewById(R.id.tv_10);
        ImageView iv_10 = bottomSheetDialog.findViewById(R.id.iv_10);

        cl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.VISIBLE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.VISIBLE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.VISIBLE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.VISIBLE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.VISIBLE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.VISIBLE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.VISIBLE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.VISIBLE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.VISIBLE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.VISIBLE);

            }
        });

        if (type.equalsIgnoreCase("daily")) {

            tv_1.setText("1 day");
            tv_2.setText("2 day");
            tv_3.setText("3 day");
            tv_4.setText("4 day");
            tv_5.setText("5 day");
            tv_6.setText("6 day");
            tv_7.setText("7 day");
            tv_8.setText("8 day");
            tv_9.setText("9 day");
            tv_10.setText("10 day");


        } else if (type.equalsIgnoreCase("weekly")) {
            tv_1.setText("1 week");
            tv_2.setText("2 week");
            tv_3.setText("3 week");
            tv_4.setText("4 week");
            tv_5.setText("5 week");
            tv_6.setText("6 week");
            tv_7.setText("7 week");
            tv_8.setText("8 week");
            tv_9.setText("9 week");
            tv_10.setText("10 week");

        } else if (type.equalsIgnoreCase("monthly")) {
            tv_1.setText("1 month");
            tv_2.setText("2 month");
            tv_3.setText("3 month");
            tv_4.setText("4 month");
            tv_5.setText("5 month");
            tv_6.setText("6 month");
            tv_7.setText("7 month");
            tv_8.setText("8 month");
            tv_9.setText("9 month");
            tv_10.setText("10 month");

        } else {
            tv_1.setText("1 year");
            tv_2.setText("2 year");
            tv_3.setText("3 year");
            tv_4.setText("4 year");
            tv_5.setText("5 year");
            tv_6.setText("6 year");
            tv_7.setText("7 year");
            tv_8.setText("8 year");
            tv_9.setText("9 year");
            tv_10.setText("10 year");
        }
        bottomSheetDialog.show();
    }


    private void callBooking(String newEditType, String startTimeStr, String endTimeStr) {
        try {
            if (Utils.isNetworkAvailable(context)) {
                dialog = ProgressDialog.showProgressBar(context);
                OtherBookingRequest otherBookingRequest = new OtherBookingRequest();
                otherBookingRequest.setTeamId(SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID));
                otherBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(context,
                        AppConstants.TEAMMEMBERSHIP_ID));

                if (!repeatActiveStatus) {
                    OtherBookingRequest.Changeset changeSets = otherBookingRequest.new Changeset();
                    changeSets.setId(0);
                    String date = Utils.removeTandZInDate(calSelectedDate).split(" ")[0];
                    date = date + "T00:00:00Z";
                    changeSets.setDate(date);

                    OtherBookingRequest.Changeset.Changes changes = changeSets.new Changes();
                    changes.setUsageTypeId(type);

                    changes.setFrom("2000-01-01T" + startTimeStr + ":00.000Z");
                    changes.setTo("2000-01-01T" + endTimeStr + ":00.000Z");

                    String timeZone = "India Standard Time";
                    if (!SessionHandler.getInstance().get(context, AppConstants.DEFAULT_TIME_ZONE_ID).isEmpty())
                        changes.setTimeZoneId(SessionHandler.getInstance().get(context, AppConstants.DEFAULT_TIME_ZONE_ID));
                    else
                        changes.setTimeZoneId(timeZone);

                    changeSets.setChanges(changes);

                    List<OtherBookingRequest.Changeset> changeSetsList = new ArrayList<>();
                    changeSetsList.add(changeSets);
                    otherBookingRequest.setChangesets(changeSetsList);
                } else {
                    List<OtherBookingRequest.Changeset> changeSetsList = new ArrayList<>();
                    List<String> dateList = Utils.getCurrentWeekDateList(calSelectedDate, enableCurrentWeek);
                    for (int i = 0; i < dateList.size(); i++) {
                        OtherBookingRequest.Changeset changeset = otherBookingRequest.new Changeset();
                        changeset.setId(0);
                        changeset.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

                        OtherBookingRequest.Changeset.Changes changes = changeset.new Changes();
                        changes.setUsageTypeId(type);
                        changes.setFrom("2000-01-01T" + startTimeStr + ":00.000Z");
                        changes.setTo("2000-01-01T" + endTimeStr + ":00.000Z");
                        String timeZone = "India Standard Time";
                        if (!SessionHandler.getInstance().get(context, AppConstants.DEFAULT_TIME_ZONE_ID).isEmpty())
                            changes.setTimeZoneId(SessionHandler.getInstance().get(context, AppConstants.DEFAULT_TIME_ZONE_ID));
                        else
                            changes.setTimeZoneId(timeZone);

                        changeset.setChanges(changes);
                        changeSetsList.add(changeset);
                    }
                    otherBookingRequest.setChangesets(changeSetsList);
                }

                otherBookingRequest.setDeletedIds(new ArrayList<>());

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<BaseResponse> call = apiService.otherBookings(otherBookingRequest);
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        ProgressDialog.dismissProgressBar(dialog);
                        responseHandler(response, newEditType);
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        ProgressDialog.dismissProgressBar(dialog);
                    }
                });

            } else {
                Utils.toastMessage(context, context.getResources().getString(R.string.enable_internet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callEditBooking(String newEditType, String startTimeStr, String endTimeStr, int bookingId) {

        if (Utils.isNetworkAvailable(context)) {
            dialog = ProgressDialog.showProgressBar(context);
            OtherBookingRequest otherBookingRequest = new OtherBookingRequest();
            otherBookingRequest.setTeamId(SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID));
            otherBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(context,
                    AppConstants.TEAMMEMBERSHIP_ID));

            OtherBookingRequest.Changeset changeSets = otherBookingRequest.new Changeset();
            changeSets.setId(bookingId);
            String date = Utils.removeTandZInDate(calSelectedDate).split(" ")[0];
            date = date + "T00:00:00Z";
            changeSets.setDate(date);

            OtherBookingRequest.Changeset.Changes changes = changeSets.new Changes();
            changes.setFrom("2000-01-01T" + startTimeStr + ":00.000Z");
            changes.setTo("2000-01-01T" + endTimeStr + ":00.000Z");

            changeSets.setChanges(changes);

            List<OtherBookingRequest.Changeset> changeSetsList = new ArrayList<>();
            changeSetsList.add(changeSets);
            otherBookingRequest.setChangesets(changeSetsList);

            otherBookingRequest.setDeletedIds(new ArrayList<>());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.otherBookings(otherBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    ProgressDialog.dismissProgressBar(dialog);
                    responseHandler(response, newEditType);
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    ProgressDialog.dismissProgressBar(dialog);
                }
            });

        } else {
            Utils.toastMessage(context, context.getResources().getString(R.string.enable_internet));
        }
    }

    public void callDeleteBooking(int bookingId) {
        if (addEditBottomSheet != null && addEditBottomSheet.isShowing())
            addEditBottomSheet.dismiss();

        if (editBookingSheet != null && editBookingSheet.isShowing())
            editBookingSheet.dismiss();

        if (Utils.isNetworkAvailable(context)) {
            dialog = ProgressDialog.showProgressBar(context);
            OtherBookingRequest otherBookingRequest = new OtherBookingRequest();
            otherBookingRequest.setTeamId(SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID));
            otherBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(context,
                    AppConstants.TEAMMEMBERSHIP_ID));

            List<OtherBookingRequest.Changeset> changeSetsList = new ArrayList<>();
            otherBookingRequest.setChangesets(changeSetsList);
            List<Integer> deletedIds = new ArrayList<>();
            deletedIds.add(bookingId);
            otherBookingRequest.setDeletedIds(deletedIds);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.otherBookings(otherBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    ProgressDialog.dismissProgressBar(dialog);
                    responseHandler(response, "");
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    ProgressDialog.dismissProgressBar(dialog);
                }
            });

        } else {
            Utils.toastMessage(context, context.getResources().getString(R.string.enable_internet));
        }
    }

    private void responseHandler(Response<BaseResponse> response, String newEditType) {

        String resultString = "";
        try {
            if (response.code() == 200 && response.body().getResultCode() != null) {
                if (response.body().getResultCode().equalsIgnoreCase("ok")) {
                    if (newEditType.equalsIgnoreCase("new"))
                        openCheckoutDialog("Booking Created");
                    else if (newEditType.equalsIgnoreCase("edit"))
                        openCheckoutDialog("Booking Updated");
                    else
                        openCheckoutDeleteDialog("Booking Deleted");
                } else {
                    if (response.body().getResultCode().equals("INVALID_FROM")) {
                        resultString = "Invalid booking start time";
                    } else if (response.body().getResultCode().equals("INVALID_TO")) {
                        resultString = "Invalid booking end time";
                    } else if (response.body().getResultCode().equals("INVALID_TIMEZONE_ID")) {
                        resultString = "Invalid timezone";
                    } else if (response.body().getResultCode().equals("INVALID_TIMEPERIOD")) {
                        resultString = "Invalid timeperiod";
                    } else if (response.body().getResultCode().equals("USER_TIME_OVERLAP")) {
                        resultString = "Time overlaps with another booking";
                    } else if (response.body().getResultCode().equals("COVID_SYMPTOMS")) {
                        resultString = "COVID_SYMPTOMS";
                    } else if (response.body().getResultCode().equals("DESK_UNAVAILABLE")) {
                        resultString = "Desk is Unavailable";
                    } else {
                        resultString = response.body().getResultCode();
                    }
                    Utils.showCustomAlertDialog((Activity) context, resultString);
                }
            } else if (response.code() == 500) {
                Utils.showCustomAlertDialog((Activity) context, "" + response.message());
            } else if (response.code() == 401) {
                Utils.showCustomTokenExpiredDialog((Activity) context, "401 Error Response");
                SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK, false);
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            Log.e("TAG", exception.getMessage());
        }
    }

    private void openCheckoutDialog(String message) {
        Dialog popDialog = new Dialog(context);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(message);

        checkDialogClose.setOnClickListener(v -> {
            if (isFrom.equalsIgnoreCase("home"))
                ((MainActivity) context).callHomeFragment();
            popDialog.dismiss();
        });
        popDialog.show();
    }

    private void openCheckoutDeleteDialog(String message) {
        Dialog popDialog = new Dialog(context);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        ImageView ivChecout = popDialog.findViewById(R.id.ivCheckoutSuccess);

        dialogMsg.setText(message);

        checkDialogClose.setOnClickListener(v -> {
            if (isFrom.equalsIgnoreCase("home"))
                ((MainActivity) context).callHomeFragment();
            popDialog.dismiss();
        });
        popDialog.show();
    }
}
