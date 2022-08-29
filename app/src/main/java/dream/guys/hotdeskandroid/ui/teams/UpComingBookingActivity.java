package dream.guys.hotdeskandroid.ui.teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.UpComingBookingAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityUpComingBookingBinding;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingBookingActivity extends AppCompatActivity {

    int day, month, year;
    HorizontalCalendar horizontalCalendar;
    String currendate = "",selectedDate="";
    HorizontalCalendarView calendarView;
    ActivityUpComingBookingBinding binding;

    /*@BindView(R.id.desk_layout)
    LinearLayout deskLayout;
    @BindView(R.id.room_layout)
    LinearLayout roomLayout;
    @BindView(R.id.parking_layout)
    LinearLayout parkingLayout;
    @BindView(R.id.more_layout)
    LinearLayout moreLayout;*/

    int selectedicon = 0;
    Context context;

    List<TeamMembersResponse> teamMembersResponses = new ArrayList<>();
    List<String> arrayString= new ArrayList<>();
    DAOTeamMember daoTeamMember;
    String date = "",fName = "",lName ="";int userID;
    LinearLayoutManager linearLayoutManager;
    UpComingBookingAdapter upComingBookingAdapter;
    ArrayList<TeamMembersResponse.DayGroup> recyclerModelArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_up_coming_booking);

        binding = ActivityUpComingBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        context = UpComingBookingActivity.this;

        uiInit();

        try {

            Intent intent = getIntent();

            if (intent!=null){

                daoTeamMember = (DAOTeamMember) intent.getSerializableExtra(AppConstants.USER_CURRENT_STATUS);
                date = intent.getStringExtra("DATE");

                if (daoTeamMember!=null){

                    fName = daoTeamMember.getFirstName();
                    lName = daoTeamMember.getLastName();
                    userID = daoTeamMember.getUserId();

                    callTeamMemberStatus(date,daoTeamMember.getTeamId());

                }

            }

        }catch (Exception e){

        }

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.deskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=0;
                tabToggleViewClicked(0);

            }
        });
        binding.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=1;
                tabToggleViewClicked(1);
            }
        });
        binding.parkingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=2;
                tabToggleViewClicked(2);
            }
        });
        binding.moreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=3;
                tabToggleViewClicked(3);
            }
        });

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
        /*Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        *//* ends after 1 month from now *//*
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
        });*/

        Calendar startDate = Calendar.getInstance();
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH);
        year = Calendar.getInstance().get(Calendar.YEAR);
        currendate = String.valueOf(year + "-" + (month + 1) + "-" + day);
        selectedDate = String.valueOf(day + "-" + (month + 1) + "-" + year);
        startDate.set(year, month, day);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);

        try {
            currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
            selectedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        horizontalCalendar
                = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .mode(HorizontalCalendar.Mode.DAYS)
                .datesNumberOnScreen(4)
                .configure()
                .formatBottomText("EEE")
                .formatMiddleText("dd MMM")
                .showBottomText(true)
                .showTopText(false)
                .sizeMiddleText(15.0f)
                .sizeBottomText(12.0f)
                .selectedDateBackground(getResources().getDrawable(R.drawable.sel_date_bg))
                .selectorColor(R.color.white)
                .end()
                .defaultSelectedDate(startDate)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                currendate = date.get(Calendar.YEAR) + "-" +
                        (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE);
//2022-08-13T10:51:17.830Z
                try {
                    currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
                    selectedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));

                    callTeamMemberStatus(currendate,daoTeamMember.getTeamId());

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

    @SuppressLint("ResourceAsColor")
    public void tabToggleViewClicked(int i) {
        resetLayout();
        switch (i){
            case 0:
                binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.figmaBlue));
                binding.ivDesk.setImageTintList(ContextCompat.getColorStateList(context,R.color.white));
                binding.tvDesk.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams deskParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                deskParams.weight = 1.0f;
                binding.deskLayout.setLayoutParams(deskParams);
                binding.calendarView.setVisibility(View.VISIBLE);
                /*if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());*/

                break;
            case 1:
                binding.roomLayout.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.figmaBlue));
                binding.ivRoom.setImageTintList(ContextCompat.getColorStateList(context,R.color.white));
                binding.tvRoom.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams roomParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                roomParams.weight = 1.0f;
                binding.roomLayout.setLayoutParams(roomParams);
                binding.calendarView.setVisibility(View.VISIBLE);
                /*if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());*/
                break;
            case 2:
                binding.parkingLayout.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.figmaBlue));
                binding.ivParking.setImageTintList(ContextCompat.getColorStateList(context,R.color.white));
                binding.tvParking.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams parkingParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parkingParams.weight = 1.0f;
                binding.parkingLayout.setLayoutParams(parkingParams);
                binding.calendarView.setVisibility(View.VISIBLE);
                /*if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());*/
                break;
            case 3:
                binding.moreLayout.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.figmaBlue));
                binding.ivMore.setImageTintList(ContextCompat.getColorStateList(context,R.color.white));
                binding.tvMore.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                moreParams.weight = 1.0f;
                binding.moreLayout.setLayoutParams(moreParams);
                //binding.calendarView.setVisibility(View.GONE);
//                getDeskCount(Utils.getCurrentDate());
                break;
            default:
                break;

        }
    }

    private void resetLayout() {
        binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.figmaBgGrey));
        binding.roomLayout.setBackgroundTintList(getResources().getColorStateList(R.color.figmaBgGrey));
        binding.parkingLayout.setBackgroundTintList(getResources().getColorStateList(R.color.figmaBgGrey));
        binding.moreLayout.setBackgroundTintList(getResources().getColorStateList(R.color.figmaBgGrey));

        binding.ivDesk.setImageTintList(getResources().getColorStateList(R.color.figmaBlack));
        binding.ivRoom.setImageTintList(getResources().getColorStateList(R.color.figmaBlack));
        binding.ivParking.setImageTintList(getResources().getColorStateList(R.color.figmaBlack));
        binding.ivMore.setImageTintList(getResources().getColorStateList(R.color.figmaBlack));

        binding.tvDesk.setVisibility(View.GONE);
        binding.tvRoom.setVisibility(View.GONE);
        binding.tvParking.setVisibility(View.GONE);
        binding.tvMore.setVisibility(View.GONE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,(float) 0.0);

        binding.deskLayout.setLayoutParams(params);
        binding.roomLayout.setLayoutParams(params);
        binding.parkingLayout.setLayoutParams(params);
        binding.moreLayout.setLayoutParams(params);

    }

    private void callTeamMemberStatus(String date,int teamID) {
        if (Utils.isNetworkAvailable(context)) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(date,teamID);
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){

                        teamMembersResponses = new ArrayList<>();
                        teamMembersResponses = response.body();
                        if (teamMembersResponses!=null &&
                                teamMembersResponses.size()>0){

                            recyclerModelArrayList = new ArrayList<>();

                            for (int i=0;i<teamMembersResponses.size();i++){

                                if (userID == teamMembersResponses.get(i).getUserId()) {
                                    TeamMembersResponse body = teamMembersResponses.get(i);
                                    createRecyclerList(body);
                                    break;

                                }

                                /*arrayString.add(" "+teamMembersResponses.get(i).getFirstName()+" "+
                                        teamMembersResponses.get(i).getLastName());*/
                            }

                        }

                    }else if(response.code()==401){
                        //Handle if token got expired
                        Utils.tokenExpiryAlert(context,"");

                    } else {
                        Log.d("Search", "onResponse: else");
                        Utils.showCustomAlertDialog(UpComingBookingActivity.this,"Api Issue Code: "+response.code());
                    }

                }
                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(context, "on fail", Toast.LENGTH_SHORT).show();
                    Utils.showCustomAlertDialog(UpComingBookingActivity.this,"Response Failure: "+t.getMessage());
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void createRecyclerList(TeamMembersResponse bookingListResponses) {

        for (int i=0; i<bookingListResponses.getDayGroups().size(); i++){
            boolean dateCheck =true;
            System.out.println("bala time format"+bookingListResponses.getDayGroups().get(i).getDate());
            Date date = bookingListResponses.getDayGroups().get(i).getDate();
            System.out.println("bala time format"+date);
            ArrayList<TeamMembersResponse.DayGroup.CalendarEntry> calendarEntries = null;
            ArrayList<TeamMembersResponse.DayGroup.MeetingBooking> meetingEntries = null;
            ArrayList<TeamMembersResponse.DayGroup.CarParkBooking> carParkEntries = null;

            if (bookingListResponses.getDayGroups().get(i).getCalendarEntries()!=null){
                calendarEntries =
                        bookingListResponses.getDayGroups().get(i).getCalendarEntries();
            }
            if (bookingListResponses.getDayGroups().get(i).getMeetingBookings()!=null){
                meetingEntries =
                        bookingListResponses.getDayGroups().get(i).getMeetingBookings();
            }
            if (bookingListResponses.getDayGroups().get(i).getCarParkBookings()!=null){
                carParkEntries =
                        bookingListResponses.getDayGroups().get(i).getCarParkBookings();
            }

            if (calendarEntries!=null){
                for (int j=0; j < calendarEntries.size(); j++){
                    TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(1);
                        momdel.setDate(date);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(1);
                        momdel.setDate(date);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }
            if (meetingEntries!=null){
                for (int j=0; j < meetingEntries.size(); j++){
                    TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(2);
                        momdel.setDate(date);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(2);
                        momdel.setDate(date);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }
            if (carParkEntries!=null){
                for (int j=0; j < carParkEntries.size(); j++){
                    TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(3);
                        momdel.setDate(date);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(3);
                        momdel.setDate(date);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }

        }

        if (recyclerModelArrayList!=null && recyclerModelArrayList.size()>0){
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.upbookingRecyclerview.setLayoutManager(linearLayoutManager);
            binding.upbookingRecyclerview.setHasFixedSize(true);

            upComingBookingAdapter=new UpComingBookingAdapter(context,recyclerModelArrayList,"");
            binding.upbookingRecyclerview.setAdapter(upComingBookingAdapter);
        }

    }
    
}