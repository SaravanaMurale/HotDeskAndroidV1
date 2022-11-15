package dream.guys.hotdeskandroid.ui.teams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;
import dream.guys.hotdeskandroid.adapter.UpComingBookingAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityShowProfileBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.ui.home.ViewTeamsActivity;
import dream.guys.hotdeskandroid.ui.settings.SettingsActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProfileActivity extends AppCompatActivity {



    ActivityShowProfileBinding binding;
    List<GlobalSearchResponse.Results> resultsList = new ArrayList<>();
    List<GlobalSearchResponse.Results> list = new ArrayList<>();
    List<TeamMembersResponse> teamMembersResponses = new ArrayList<>();
    List<String> arrayString= new ArrayList<>();
    DAOTeamMember daoTeamMember;
    Context context;

    String date = "",fName = "",lName ="";int userID,teamId;
    LinearLayoutManager linearLayoutManager;
    UpComingBookingAdapter upComingBookingAdapter;
    ArrayList<TeamMembersResponse.DayGroup> recyclerModelArrayList = new ArrayList();

    //ForLanguage
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_profile);
        binding = ActivityShowProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowProfileActivity.this;

        setLanguage();



        binding.ivEditEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvEditEmail !=null && !binding.tvEditEmail.getText().toString().equalsIgnoreCase("email")){
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + binding.tvEditEmail.getText().toString()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Team member");
                    intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                    startActivity(intent);

                    /*
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{""+binding.tvEditEmail.getText().toString()});
                    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                    i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                    try {
                        startActivity(i);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(ShowProfileActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    */

                }
            }
        });
        binding.tvEditPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvEditEmail !=null && !binding.tvEditPhone.getText().toString().equalsIgnoreCase("phone")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+binding.tvEditPhone.getText().toString()));
                    startActivity(intent);
                }
            }
        });
        binding.tvViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ShowProfileActivity.this, ViewTeamsActivity.class);
                startActivity(intent);
            }
        });

        try {

            Intent intent = getIntent();

            if (intent != null) {

                daoTeamMember = (DAOTeamMember) intent.getSerializableExtra(AppConstants.USER_CURRENT_STATUS);
                date = intent.getStringExtra("DATE");

                if (daoTeamMember != null) {

                    if (daoTeamMember.getLastName() != null) {
                        fName = daoTeamMember.getFirstName() + " " + daoTeamMember.getLastName();
                        lName = daoTeamMember.getLastName();
                    } else {
                        fName = daoTeamMember.getFirstName();// + " " +daoTeamMember.getLastName();
                        lName = "";
                    }

                    userID = daoTeamMember.getUserId();
                    //teamId = daoTeamMember.getTeamId();



                    //Show Person Profile Only
                    callSearchRecyclerData(fName, userID);
                    //Shows upcoming data

                    if (daoTeamMember.getTeamId()!=null){
                        binding.upcomingBookingRel.setVisibility(View.VISIBLE);
                        teamId = daoTeamMember.getTeamId();
                        callTeamMemberStatus(date, daoTeamMember.getTeamId());
                    }else {
                        binding.upcomingBookingRel.setVisibility(View.GONE);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpComingBookingActivity.class);
                intent.putExtra(AppConstants.USER_CURRENT_STATUS, daoTeamMember);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }


        });




    }


private void callSearchRecyclerData(String searchText,int selID) {
        if (Utils.isNetworkAvailable(context)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GlobalSearchResponse> call = apiService.getGlobalSearchData(40,searchText);
            call.enqueue(new Callback<GlobalSearchResponse>() {
                @Override
                public void onResponse(Call<GlobalSearchResponse> call, Response<GlobalSearchResponse> response) {

                    if(response.code()==200){
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        list.clear();
                        if (response.body().getResults()!=null)
                            list.addAll(response.body().getResults());


                        if (list!=null && list.size()>0){

                            for (int i=0;i<list.size();i++){

                                if (selID == list.get(i).getId()) {

                                    List<GlobalSearchResponse.Results> resultsList = new ArrayList<>();
                                    resultsList.add(list.get(i));
                                    setResults(resultsList.get(0));
                                    break;
                                }
                            }
                        }

                        //binding.searchRecycler.setVisibility(View.VISIBLE);
                        //searchRecyclerAdapter.notifyDataSetChanged();

                    }else if(response.code()==401){
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Utils.showCustomTokenExpiredDialog(ShowProfileActivity.this,"Token Expired");
                        SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);

                    } else {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Log.d("Search", "onResponse: else");
                    }

                }
                @Override
                public void onFailure(Call<GlobalSearchResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "on fail", Toast.LENGTH_SHORT).show();
//                    ProgressDialog.dismisProgressBar(context,dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void callTeamMemberStatus(String date,int teamID) {
        if (Utils.isNetworkAvailable(context)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(date,teamID);
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){

                        teamMembersResponses = response.body();
                        if (teamMembersResponses!=null &&
                                teamMembersResponses.size()>0){
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
                        Utils.showCustomAlertDialog(ShowProfileActivity.this,"Api Issue Code: "+response.code());
                    }

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(context, "on fail", Toast.LENGTH_SHORT).show();
                    Utils.showCustomAlertDialog(ShowProfileActivity.this,"Response Failure: "+t.getMessage());
                    Log.d("Search", "onResponse: fail"+t.getMessage());

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void setResults(GlobalSearchResponse.Results results) {
        LocalDate today = LocalDate.now();
        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        // Go forward to get Sunday
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        System.out.println("Today: " + today);
        System.out.println("Monday of the Week: " + monday);
        System.out.println("Sunday of the Week: " + sunday);

        binding.weekStartEnd.setText(Utils.showBottomSheetDate(""+monday)+
                " - "+
                Utils.showBottomSheetDate(""+sunday));

        binding.tvViewProfileName.setText(results.getName());
        binding.txtTeam.setText(results.getTeam());
        binding.tvTeamName.setText(results.getTeam());
        if (results.getEmail() == null
                || results.getEmail().equalsIgnoreCase("")
                || results.getEmail().isEmpty())
            binding.tvEditEmail.setText("Email");
        else
            binding.tvEditEmail.setText(results.getEmail());
        if (results.getDeskPhoneNumber() == null
                || results.getDeskPhoneNumber().equalsIgnoreCase("")
                ||results.getDeskPhoneNumber().isEmpty())
            binding.tvEditPhone.setText("Phone");
        else
            binding.tvEditPhone.setText(results.getDeskPhoneNumber());

        binding.txtTname.setText(results.getTeam());
        //binding.tvEditPhone.setText(results.getMobile());

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
            binding.notAvailable.setVisibility(View.GONE);
            binding.samUpcomingRecycler.setVisibility(View.VISIBLE);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.samUpcomingRecycler.setLayoutManager(linearLayoutManager);
            binding.samUpcomingRecycler.setHasFixedSize(true);

            upComingBookingAdapter=new UpComingBookingAdapter(context,recyclerModelArrayList,"Sample");
            binding.samUpcomingRecycler.setAdapter(upComingBookingAdapter);
        }else {
            binding.notAvailable.setVisibility(View.VISIBLE);
            binding.samUpcomingRecycler.setVisibility(View.GONE);
        }
        
    }

    public void setLanguage(){

        logoinPage = Utils.getLoginScreenData(ShowProfileActivity.this);
        appKeysPage = Utils.getAppKeysPageScreenData(ShowProfileActivity.this);
        resetPage = Utils.getResetPasswordPageScreencreenData(ShowProfileActivity.this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(ShowProfileActivity.this);
        bookindata = Utils.getBookingPageScreenData(ShowProfileActivity.this);
        global=Utils.getGlobalScreenData(ShowProfileActivity.this);

        binding.tvTitle.setText(appKeysPage.getProfile());
        binding.titleTeam.setText(appKeysPage.getTeam());
        binding.scheduleProfile.setText(appKeysPage.getSchedule());
        binding.titleUpcoming.setText(appKeysPage.getUpcomingBookings());
        binding.tvViewTeam.setText(appKeysPage.getViewTeam());
        binding.txtViewAll.setText(appKeysPage.getViewAll());
        binding.tvEditContact.setText(appKeysPage.getContact());



    }

}