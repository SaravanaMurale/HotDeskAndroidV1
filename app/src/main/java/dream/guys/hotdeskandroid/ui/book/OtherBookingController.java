package dream.guys.hotdeskandroid.ui.book;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.OtherBookingAdapter;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
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

    private Context context;
    private int selectedIcon;
    private String calSelectedDate;
    private BookingForEditResponse bookingForEditResponse;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList = new ArrayList<>();
    Dialog dialog;

    public OtherBookingController(Context context, int selectedIcon, String calSelectedDate) {
        this.context = context;
        this.selectedIcon = selectedIcon;
        this.calSelectedDate = calSelectedDate;
        getAddEditDesk(selectedIcon, calSelectedDate);
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
        /*4 == 9 - remote - WFH
       5 == 18 - sick leave
       6 ==  6 - holiday
       7 == 8 - training*/
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

        // openEditBookingSheet(bookingsList);
        if (bookingsList.size() > 0) {
            openEditBookingSheet(bookingsList);

        } else {
            openNewBookingSheet("new", "", "");
        }
    }

    public void openNewBookingSheet(String from, String startTimeStr, String endTimeStr) {
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

        if (from.equalsIgnoreCase("new")) {
            if (selectedIcon == 4) {
                bookingName.setText("Working remotely");
            } else if (selectedIcon == 5) {
                bookingName.setText("Log sickness");
            } else if (selectedIcon == 6) {
                bookingName.setText("Book holiday");
            } else if (selectedIcon == 7) {
                bookingName.setText("Book training");
            }
        } else {
            if (selectedIcon == 4) {
                bookingName.setText("Edit working remotely");
            } else if (selectedIcon == 5) {
                bookingName.setText("Edit sickness");
            } else if (selectedIcon == 6) {
                bookingName.setText("Edit holiday");
            } else if (selectedIcon == 7) {
                bookingName.setText("Edit training");
            }

            startTime.setText(startTimeStr);
            endTime.setText(endTimeStr);

        }

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addEditBottomSheet.isShowing())
                addEditBottomSheet.dismiss();
            }
        });

        addEditBottomSheet.show();
    }

    private void openEditBookingSheet(List<BookingForEditResponse.Bookings> bookingsList) {
        BottomSheetDialog editBookingSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
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
                openNewBookingSheet("new", "", "");
            }
        });

        editBookingSheet.show();
    }

    private void setAdapter(RecyclerView bookedListRecycler, List<BookingForEditResponse.Bookings> bookingsList) {
        OtherBookingAdapter otherBookingAdapter = new OtherBookingAdapter(context, selectedIcon,
                bookingsList, this);
        bookedListRecycler.setAdapter(otherBookingAdapter);
    }
}
