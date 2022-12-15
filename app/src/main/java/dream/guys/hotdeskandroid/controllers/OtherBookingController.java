package dream.guys.hotdeskandroid.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.OtherBookingAdapter;
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

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), startTime, "Start Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), true);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), endTime, "End Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), true);
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
        editBookingSheet.setContentView(((Activity) context).getLayoutInflater().inflate
                (R.layout.bottom_sheet_other_bookings_list, new RelativeLayout(context)));

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
        tvBook.setText("Save changes");
        tvDate.setText(Utils.calendarDay10thMonthYearformat(date));

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), startTime, "Start Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), true);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePicker24Hrs(context, ((Activity) context), endTime, "End Time",
                        Utils.dayDateMonthFormat(Utils.convertStringToDateFormet(calSelectedDate)), true);
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


    private void callBooking(String newEditType, String startTimeStr, String endTimeStr) {

        if (Utils.isNetworkAvailable(context)) {
            dialog = ProgressDialog.showProgressBar(context);
            OtherBookingRequest otherBookingRequest = new OtherBookingRequest();
            otherBookingRequest.setTeamId(SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID));
            otherBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(context,
                    AppConstants.TEAMMEMBERSHIP_ID));

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
