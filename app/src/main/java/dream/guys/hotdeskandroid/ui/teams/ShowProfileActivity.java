package dream.guys.hotdeskandroid.ui.teams;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.UpComingBookingAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityShowProfileBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.ui.home.ViewTeamsActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ExtendedDataHolder;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProfileActivity extends AppCompatActivity implements UpComingBookingAdapter.OnLocationIconClick {


    ActivityShowProfileBinding binding;
    List<GlobalSearchResponse.Results> resultsList = new ArrayList<>();
    List<GlobalSearchResponse.Results> list = new ArrayList<>();
    List<TeamMembersResponse> teamMembersResponses = new ArrayList<>();
    List<String> arrayString = new ArrayList<>();
    DAOTeamMember daoTeamMember;
    Context context;

    String date = "", fName = "", lName = "";
    int userID, teamId;
    LinearLayoutManager linearLayoutManager;
    UpComingBookingAdapter upComingBookingAdapter;
    ArrayList<TeamMembersResponse.DayGroup> recyclerModelArrayList = new ArrayList();

    //ForLanguage
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata;
    LanguagePOJO.Global global;


    HashMap<Integer, Boolean> firstAidList;
    HashMap<Integer, Boolean> firewardenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_profile);
        binding = ActivityShowProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = ShowProfileActivity.this;

        setLanguage();
        getFirstAidPersonsDetails("");


        binding.ivEditEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvEditEmail != null && !binding.tvEditEmail.getText().toString().equalsIgnoreCase("email")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + binding.tvEditEmail.getText().toString()));
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

        binding.tvViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowProfileActivity.this, ViewTeamsActivity.class);
                startActivity(intent);
            }
        });

        try {

            Intent intent = getIntent();

            if (intent != null) {
                String personJsonString = "";
                ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
                if (extras.hasExtra(AppConstants.USER_CURRENT_STATUS)) {
                    personJsonString = (String) extras.getExtra(AppConstants.USER_CURRENT_STATUS);
                }
                extras.clear();
                daoTeamMember = new Gson().fromJson(personJsonString, DAOTeamMember.class);

                //  daoTeamMember = (DAOTeamMember) intent.getSerializableExtra(AppConstants.USER_CURRENT_STATUS);
                date = intent.getStringExtra("DATE");

                if (daoTeamMember != null) {

                    if (daoTeamMember.getProfileImage() != null
                            && !daoTeamMember.getProfileImage().equalsIgnoreCase("")) {
                        String cleanImage = daoTeamMember.getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
                        byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Glide.with(context).load(decodedByte)
                                .placeholder(R.drawable.avatar)
                                .error(R.drawable.avatar)
                                .into(binding.ivViewPrifle);
                    } else {
                        Glide.with(context).load(R.drawable.avatar)
                                .into(binding.ivViewPrifle);
                    }

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

                    if (daoTeamMember.getTeamId() != null) {
                        binding.upcomingBookingRel.setVisibility(View.VISIBLE);
                        teamId = daoTeamMember.getTeamId();
                        callTeamMemberStatus(date, daoTeamMember.getTeamId());
                    } else {
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
//                intent.putExtra(AppConstants.USER_CURRENT_STATUS, daoTeamMember);
                String personJsonString = new Gson().toJson(daoTeamMember);
                ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
                extras.putExtra("profile", personJsonString);
                intent.putExtra("DATE", date);
                startActivity(intent);
            }


        });


    }


    private void callSearchRecyclerData(String searchText, int selID) {
        if (Utils.isNetworkAvailable(context)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GlobalSearchResponse> call = apiService.getGlobalSearchData(40, searchText);
            call.enqueue(new Callback<GlobalSearchResponse>() {
                @Override
                public void onResponse(Call<GlobalSearchResponse> call, Response<GlobalSearchResponse> response) {

                    if (response.code() == 200) {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        list.clear();
                        if (response.body().getResults() != null)
                            list.addAll(response.body().getResults());


                        if (list != null && list.size() > 0) {

                            for (int i = 0; i < list.size(); i++) {

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

                    } else if (response.code() == 401) {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Utils.showCustomTokenExpiredDialog(ShowProfileActivity.this, "Token Expired");
                        SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK, false);

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
                    Log.d("Search", "onResponse: fail" + t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void callTeamMemberStatus(String date, int teamID) {
        if (Utils.isNetworkAvailable(context)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(date, teamID);
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if (response.code() == 200) {

                        teamMembersResponses = response.body();
                        if (teamMembersResponses != null &&
                                teamMembersResponses.size() > 0) {
                            for (int i = 0; i < teamMembersResponses.size(); i++) {

                                if (userID == teamMembersResponses.get(i).getUserId()) {
                                    TeamMembersResponse body = teamMembersResponses.get(i);
                                    createRecyclerList(body);
                                    break;

                                }

                                /*arrayString.add(" "+teamMembersResponses.get(i).getFirstName()+" "+
                                        teamMembersResponses.get(i).getLastName());*/
                            }

                        }

                    } else if (response.code() == 401) {
                        //Handle if token got expired
                        Utils.tokenExpiryAlert(context, "");

                    } else {
                        Log.d("Search", "onResponse: else");
                        Utils.toastShortMessage(ShowProfileActivity.this, "Api Issue Code: " + response.code());
                    }

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(context, "on fail", Toast.LENGTH_SHORT).show();
                    Utils.toastShortMessage(ShowProfileActivity.this, "Response Failure: " + t.getMessage());
                    Log.d("Search", "onResponse: fail" + t.getMessage());

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void getFirstAidPersonsDetails(String description) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FirstAidResponse>> call = apiService.getFirstAidResponse();
        call.enqueue(new Callback<List<FirstAidResponse>>() {
            @Override
            public void onResponse(Call<List<FirstAidResponse>> call, Response<List<FirstAidResponse>> response) {
                List<FirstAidResponse> firstAidResponseList = response.body();
                List<FirstAidResponse.Persons> personsList = new ArrayList<>();
                List<FirstAidResponse.Persons> personsListfirstAid = new ArrayList<>();
                firstAidList = new HashMap<>();
                firewardenList = new HashMap<>();

                for (int i = 0; i < firstAidResponseList.size(); i++) {
                    if (firstAidResponseList.get(i).getPersonsList().size() > 0) {

                        if (firstAidResponseList.get(i).getType() == 4) {
                            for (int j = 0; j < firstAidResponseList.get(i).getPersonsList().size(); j++) {
                                personsList.add(firstAidResponseList.get(i).getPersonsList().get(j));
                            }
                        }
                        if (firstAidResponseList.get(i).getType() == 5) {
                            for (int j = 0; j < firstAidResponseList.get(i).getPersonsList().size(); j++) {
                                personsListfirstAid.add(firstAidResponseList.get(i).getPersonsList().get(j));
                            }
                        }

                    }
                }

                for (int i = 0; i < personsList.size(); i++) {
                    firewardenList.put(personsList.get(i).getId(), personsList.get(i).isActive());
                }
                for (int i = 0; i < personsListfirstAid.size(); i++) {
                    firstAidList.put(personsListfirstAid.get(i).getId(), personsListfirstAid.get(i).isActive());
                }
                updateFireStatus();
            }

            @Override
            public void onFailure(Call<List<FirstAidResponse>> call, Throwable t) {

            }
        });

    }

    private void updateFireStatus() {
        if (firewardenList != null) {
            if (firewardenList.containsKey(userID)) {
                binding.locateMyTeamName.setVisibility(View.VISIBLE);
                binding.locateMyTeamFireIv.setVisibility(View.VISIBLE);
            } else {
                binding.locateMyTeamName.setVisibility(View.GONE);
                binding.locateMyTeamFireIv.setVisibility(View.GONE);
            }
        } else {
            binding.locateMyTeamName.setVisibility(View.GONE);
            binding.locateMyTeamFireIv.setVisibility(View.GONE);
        }

        if (firstAidList != null) {
            if (firstAidList.containsKey(userID)) {
                binding.txtFire.setVisibility(View.VISIBLE);
                binding.locateMyTeamPlusIv.setVisibility(View.VISIBLE);
            } else {

                binding.txtFire.setVisibility(View.GONE);
                binding.locateMyTeamPlusIv.setVisibility(View.GONE);
            }

        } else {

            binding.txtFire.setVisibility(View.GONE);
            binding.locateMyTeamPlusIv.setVisibility(View.GONE);
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

        binding.weekStartEnd.setText(Utils.showBottomSheetDate("" + monday) +
                " - " +
                Utils.showBottomSheetDate("" + sunday));

        binding.tvViewProfileName.setText(results.getName());
        binding.txtTeam.setText(results.getTeam());
        binding.tvTeamName.setText(results.getTeam());
        if (results.getEmail() == null
                || results.getEmail().equalsIgnoreCase("")
                || results.getEmail().isEmpty())
            binding.tvEditEmail.setText("Email");
        else
            binding.tvEditEmail.setText(results.getEmail());

        if (results.getEmail() == null
                || results.getEmail().equalsIgnoreCase("")
                || results.getEmail().isEmpty())
            binding.tvEditEmail.setText("Email");
        else
            binding.tvEditEmail.setText(results.getEmail());

        if (results.getMobile() == null
                || results.getMobile().equalsIgnoreCase("")
                || results.getMobile().isEmpty())
            binding.tvEditPhone.setText("Phone");
        else
            binding.tvEditPhone.setText(results.getMobile());
       // binding.tvEditPhone.setText("Phone");
        binding.txtTname.setText(results.getTeam());
        //binding.tvEditPhone.setText(results.getMobile());


        binding.ivEditEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (results.getEmail() != null && !results.getEmail().isEmpty())
                    Utils.openMail(ShowProfileActivity.this, results.getEmail());*/
            }
        });

        binding.tvEditPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (results.getPhoneNumber() != null && !results.getPhoneNumber().isEmpty())
                    Utils.openDial(ShowProfileActivity.this, results.getPhoneNumber());*/
            }
        });
    }

    private void createRecyclerList(TeamMembersResponse bookingListResponses) {

        for (int i = 0; i < bookingListResponses.getDayGroups().size(); i++) {
            boolean dateCheck = true;
            System.out.println("bala time format" + bookingListResponses.getDayGroups().get(i).getDate());
            Date date = bookingListResponses.getDayGroups().get(i).getDate();
            System.out.println("bala time format" + date);
            ArrayList<TeamMembersResponse.DayGroup.CalendarEntry> calendarEntries = null;
            ArrayList<TeamMembersResponse.DayGroup.MeetingBooking> meetingEntries = null;
            ArrayList<TeamMembersResponse.DayGroup.CarParkBooking> carParkEntries = null;

            if (bookingListResponses.getDayGroups().get(i).getCalendarEntries() != null) {
                calendarEntries =
                        bookingListResponses.getDayGroups().get(i).getCalendarEntries();
            }
            if (bookingListResponses.getDayGroups().get(i).getMeetingBookings() != null) {
                meetingEntries =
                        bookingListResponses.getDayGroups().get(i).getMeetingBookings();
            }
            if (bookingListResponses.getDayGroups().get(i).getCarParkBookings() != null) {
                carParkEntries =
                        bookingListResponses.getDayGroups().get(i).getCarParkBookings();
            }

            if (calendarEntries != null) {
                for (int j = 0; j < calendarEntries.size(); j++) {
                    TeamMembersResponse.DayGroup model = new TeamMembersResponse.DayGroup();
                    if (dateCheck) {
                        model.setDateStatus(true);
                        model.setCalDeskStatus(1);
                        model.setDate(date);
                        model.setCalendarEntriesModel(calendarEntries.get(j));
                        model.setFrom(calendarEntries.get(j).getFrom());
                        model.setMyto(calendarEntries.get(j).getMyto());
                        dateCheck = false;
                    } else {
                        model.setDateStatus(false);
                        model.setCalDeskStatus(1);
                        model.setDate(date);
                        model.setCalendarEntriesModel(calendarEntries.get(j));
                        model.setFrom(calendarEntries.get(j).getFrom());
                        model.setMyto(calendarEntries.get(j).getMyto());
                    }
                    recyclerModelArrayList.add(model);
                }
            }
            if (meetingEntries != null) {
                for (int j = 0; j < meetingEntries.size(); j++) {
                    TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
                    if (dateCheck) {
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(2);
                        momdel.setDate(date);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                        dateCheck = false;
                    } else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(2);
                        momdel.setDate(date);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }
            if (carParkEntries != null) {
                for (int j = 0; j < carParkEntries.size(); j++) {
                    TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
                    if (dateCheck) {
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(3);
                        momdel.setDate(date);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                        dateCheck = false;
                    } else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(3);
                        momdel.setDate(date);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }

        }

        if (recyclerModelArrayList != null && recyclerModelArrayList.size() > 0) {
            binding.notAvailable.setVisibility(View.GONE);
            binding.samUpcomingRecycler.setVisibility(View.VISIBLE);
            binding.txtViewAll.setVisibility(View.VISIBLE);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.samUpcomingRecycler.setLayoutManager(linearLayoutManager);
            binding.samUpcomingRecycler.setHasFixedSize(true);

            upComingBookingAdapter = new UpComingBookingAdapter(context, recyclerModelArrayList, "Sample",this);
            binding.samUpcomingRecycler.setAdapter(upComingBookingAdapter);
        } else {
            binding.notAvailable.setVisibility(View.VISIBLE);
            binding.samUpcomingRecycler.setVisibility(View.GONE);
            binding.txtViewAll.setVisibility(View.GONE);
        }

    }

    public void setLanguage() {

        logoinPage = Utils.getLoginScreenData(ShowProfileActivity.this);
        appKeysPage = Utils.getAppKeysPageScreenData(ShowProfileActivity.this);
        resetPage = Utils.getResetPasswordPageScreencreenData(ShowProfileActivity.this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(ShowProfileActivity.this);
        bookindata = Utils.getBookingPageScreenData(ShowProfileActivity.this);
        global = Utils.getGlobalScreenData(ShowProfileActivity.this);

        //binding.tvTitle.setText(appKeysPage.getProfile());
        binding.titleTeam.setText(appKeysPage.getTeam());
        binding.scheduleProfile.setText(appKeysPage.getSchedule());
        binding.titleUpcoming.setText(appKeysPage.getUpcomingBookings());
        binding.tvViewTeam.setText(appKeysPage.getViewTeam());
        binding.txtViewAll.setText(appKeysPage.getViewAll());
        binding.tvEditContact.setText(appKeysPage.getContact());


    }

    @Override
    public void onLocationIconClicked(int parentLocationId, String selctedBuildingName, String selectedFloorName, int getID, String deskCode,String desk,int deskId) {
        onLocationIconClick(parentLocationId, selctedBuildingName, selectedFloorName,getID,deskCode,desk,deskId);
    }

    private void onLocationIconClick(int parentLocationId, String selctedBuildingName, String selectedFloorName, int getID, String deskCode,String desk,int deskId) {

        SessionHandler.getInstance().saveInt(ShowProfileActivity.this, AppConstants.PARENT_ID, parentLocationId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentLocationId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                for (int i = 0; i < locateCountryResposeList.size(); i++) {

                    if (desk.equals(AppConstants.DESK)) {

                        for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getDesks().size(); j++) {

                            if (deskId == locateCountryResposeList.get(i).getLocationItemLayout().getDesks().get(j).getDesksId()) {
                                SessionHandler.getInstance().saveInt(ShowProfileActivity.this, AppConstants.FLOOR_POSITION, i);

                                System.out.println("SelectedDeskFloorInLocate " + i + " " + desk + " ");

                                SessionHandler.getInstance().saveBoolean(context,AppConstants.SHOW_PROFILE_CLICKED_STATUS,true);
                                finish();
                                break;

                            }
                        }
                    } else if (desk.equals(AppConstants.MEETING)) {

                       /* for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getMeetingRoomsList().size(); j++) {

                            if (identifierId == locateCountryResposeList.get(i).getLocationItemLayout().getMeetingRoomsList().get(j).getMeetingRoomId()) {
                                SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);

                                System.out.println("SelectedMeetingFloorInLocate " + i + " " + desk + " ");

                                ((MainActivity) getActivity()).callLocateFragmentFromHomeFragment();
//                                navController.navigate(R.id.navigation_locate);
                            }

                        }*/


                    } else if (desk.equals(AppConstants.CAR_PARKING)) {

                        /*for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getParkingSlotsList().size(); j++) {

                            if (identifierId == locateCountryResposeList.get(i).getLocationItemLayout().getParkingSlotsList().get(j).getId()) {
                                SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);

                                System.out.println("SelectedCarFloorInLocate " + i + " " + desk + " ");
                                ((MainActivity) getActivity()).callLocateFragmentFromHomeFragment();
//                                navController.navigate(R.id.navigation_locate);
                            }
                        }*/
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean clickedStatus=SessionHandler.getInstance().getBoolean(context,AppConstants.SHOW_PROFILE_CLICKED_STATUS);
        System.out.println("CalledAfterShowProfileFinishOnResume");
        if(clickedStatus){
            finish();
        }
    }
}