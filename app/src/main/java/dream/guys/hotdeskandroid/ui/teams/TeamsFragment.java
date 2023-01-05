package dream.guys.hotdeskandroid.ui.teams;

import static dream.guys.hotdeskandroid.utils.Utils.getActionOverLaysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getBookingPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getMeScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getResetPasswordPageScreencreenData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.HorizontalCalTeamsAdapter;
import dream.guys.hotdeskandroid.adapter.SearchRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsContactsAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsFloorListAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentTeamsBinding;
import dream.guys.hotdeskandroid.model.FloorListModel;
import dream.guys.hotdeskandroid.model.HorizontalCalendarModel;
import dream.guys.hotdeskandroid.model.TeamsMemberListDataModel;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBooking;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.UsageTypeResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ExtendedDataHolder;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment implements TeamsAdapter.TeamMemberInterface,
        TeamsContactsAdapter.OnProfileClickable,
        TeamsFloorListAdapter.FloorListener, HorizontalCalTeamsAdapter.CalendarSelectedInterface {

    FragmentTeamsBinding binding;
    int day, month, year;
    HorizontalCalendar horizontalCalendar;
    String currendate = "", selectedDate = "";
    HorizontalCalendarView calendarView;
    HashMap<Integer, String> floorList = new HashMap<>();
    ArrayList<DAOTeamMember> teamMembersList = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersInOfficeListNew = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersInOfficeList = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersRemoteList = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersRemoteListNew = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersTrainingList = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersTrainingListNew = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersUnknownList = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersUnknownListNew = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersHolidayList = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersHolidayListNew = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersOutOfOffice = new ArrayList<>();
    ArrayList<TeamsMemberListDataModel> teamMembersOutOfOfficeNew = new ArrayList<>();
    ArrayList<DAOTeamMember> copyTeamMembersList = new ArrayList<>();
    ArrayList<TeamsContactsAdapter> dynamicAdapters = new ArrayList<>();
    TeamsContactsAdapter teamsContactsAdapter, teamsContactsRemoteAdapter,
            teamsContactsTrainingAdapter, teamsContactsHolidaysAdapter,
            teamsContactsOutOfOfficeAdapter, teamsContactsUnknownAdapter;
    TeamsAdapter teamsAdapter;
    TeamsFloorListAdapter teamsFloorListAdapter;

    List<GlobalSearchResponse.Results> list = new ArrayList<>();
    SearchRecyclerAdapter searchRecyclerAdapter;
    LinearLayoutManager linearLayoutManager, contactUnknownLinearLayout, contactLinearLayout,
            contactHolidayLinearLayout, contactOutOfOfficeLinearLayout, contactRemoteLinearLayout, contactTrainingLinearLayout;

    public HashMap<Integer, Boolean> firewardenList;
    public HashMap<Integer, Boolean> firstAidList;
    int selID = 0;

    //New...
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.Me mePage;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.ResetPassword resetPage;
    LanguagePOJO.Booking bookindata;

    public boolean expandStatus = false;
    RecyclerView horizontalRecycler;
    RecyclerView.LayoutManager horizontalLayoutManager;
    RecyclerView.Adapter horizontalCalTeamsAdapter;
    ArrayList<HorizontalCalendarModel> horizontalCalendarModels;

    Activity activityContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        activityContext = getActivity();

        setUpCalendarData();
        uiInit(root);

        setLanguage();
        getFirstAidPersonsDetails("firewarden");
        binding.tvExapnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandStatus = true;
                binding.tvExapnd.setVisibility(View.GONE);
                getTeamMembers();
//                setValueToAdapter(teamMembersList);

                //Intent intent = new Intent(getActivity(),UpComingBookingActivity.class);
                //startActivity(intent);
/*

                binding.searchBlock.setVisibility(View.VISIBLE);
                binding.searchBlock.setVisibility(View.VISIBLE);
                binding.expandRecyclerView.setVisibility(View.GONE);
                binding.tvHoliday.setVisibility(View.GONE);
                binding.tvOutOfOffice.setVisibility(View.GONE);
                binding.tvUnknown.setVisibility(View.GONE);
                binding.tvWorkRemote.setVisibility(View.GONE);
//                binding.tvFloorName.setVisibility(View.GONE);
                binding.tvAdddress.setVisibility(View.GONE);
                binding.listTitle.setVisibility(View.GONE);
//                binding.tvAvailableCount.setVisibility(View.GONE);
//                binding.ivLocation.setVisibility(View.GONE);
                binding.tvExapnd.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.recyclerViewRemote.setVisibility(View.GONE);
                binding.recyclerViewHoliday.setVisibility(View.GONE);
                binding.recyclerViewOutOfOffice.setVisibility(View.GONE);
                binding.recyclerViewUnkown.setVisibility(View.GONE);
                binding.tvTotalAvail.setVisibility(View.VISIBLE);

                setDataToExpandAdapter(teamMembersList);
*/

            }
        });
/*
        binding.ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamMembersInOfficeList.get(0).getDayGroups().size()>0
                        && teamMembersInOfficeList.get(0).getDayGroups().get(0).getCalendarEntries()!=null){
                    //Desk
                    if(teamMembersInOfficeList.get(0).getDayGroups()
                            .get(0).getCalendarEntries()
                            .get(0).getBooking().getLocationBuildingFloor() != null){

                        int parentLocationId=teamMembersInOfficeList.get(0).getDayGroups()
                                .get(0).getCalendarEntries()
                                .get(0).getBooking().getLocationBuildingFloor().getFloorID();
                        int deskId=teamMembersInOfficeList.get(0).getDayGroups()
                                .get(0).getCalendarEntries()
                                .get(0).getBooking().getDeskId();
                        onLocationIconClick(parentLocationId, deskId, AppConstants.DESK);
                    }

                }

            }
        });*/

        binding.txtAllteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TeamsUserActivity.class);
                startActivity(intent);
            }
        });

        binding.serachBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (teamsContactsTrainingAdapter != null)
                    teamsContactsTrainingAdapter.getFilter().filter(s.toString());
                if (teamsContactsRemoteAdapter != null)
                    teamsContactsRemoteAdapter.getFilter().filter(s.toString());
                if (teamsContactsHolidaysAdapter != null)
                    teamsContactsHolidaysAdapter.getFilter().filter(s.toString());
                if (teamsContactsOutOfOfficeAdapter != null)
                    teamsContactsOutOfOfficeAdapter.getFilter().filter(s.toString());
                if (teamsContactsUnknownAdapter != null)
                    teamsContactsUnknownAdapter.getFilter().filter(s.toString());

                if (dynamicAdapters != null)
                    for (int i = 0; i < dynamicAdapters.size(); i++) {
                        dynamicAdapters.get(i).getFilter().filter(s.toString());
                    }

                /*if (!s.toString().equals("")){

                    filter(s.toString().toLowerCase());
                }else {

                    teamMembersList = new ArrayList<>();
                    teamMembersList.addAll(copyTeamMembersList);

                    setDataToExpandAdapter(teamMembersList);

                }
*/
                /*if(s.toString().length()==0){
                    list.clear();
                    binding.searchRecycler.setVisibility(View.GONE);
                    searchRecyclerAdapter.notifyDataSetChanged();
                }*/

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
                //callSearchRecyclerData(s.toString());
            }
        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.serachBar.setText("");
//                setValueToAdapter(teamMembersList);
            }
        });


        getTeamMembers();


        //NewNew
        getBookingUsageTypes();
        getCalendarEntryTeam();


        //New...
        linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        binding.searchRecycler.setLayoutManager(linearLayoutManager);
        binding.searchRecycler.setHasFixedSize(true);
        searchRecyclerAdapter = new SearchRecyclerAdapter(getActivity(), list);
        binding.searchRecycler.setAdapter(searchRecyclerAdapter);

        return root;
    }

    private void setUpCalendarData() {
        // Inflate the layout for this fragment
        horizontalCalendarModels = new ArrayList<>();
        DateFormat fullDateformat = new SimpleDateFormat("yyyy-M-d");
        DateFormat dayFormat = new SimpleDateFormat("EEE");
        DateFormat monthFormat = new SimpleDateFormat("MMMM");
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();

        String day="";
        String date="";
        for (int i = 0; i < 30; i++)
        {
            HorizontalCalendarModel horizontalCalendar = new HorizontalCalendarModel();
            horizontalCalendar.setDate(fullDateformat.format(calendar.getTime()));
            horizontalCalendar.setDay(dayFormat.format(calendar.getTime()));
            horizontalCalendar.setMonth(monthFormat.format(calendar.getTime()));
            horizontalCalendar.setDayDate(dateFormat.format(calendar.getTime()));
            if (i==0){
                horizontalCalendar.setSelected(true);
                horizontalCalendar.setToday(true);
            } else {
                horizontalCalendar.setSelected(false);
                horizontalCalendar.setToday(false);
            }

            horizontalCalendarModels.add(horizontalCalendar);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.horizontalRecycler.setLayoutManager(horizontalLayoutManager);
        binding.horizontalRecycler.setHasFixedSize(true);
        horizontalCalTeamsAdapter = new HorizontalCalTeamsAdapter(horizontalCalendarModels,
                activityContext,this,this);
        binding.horizontalRecycler.setAdapter(horizontalCalTeamsAdapter);

        if (horizontalCalendarModels.size()>0)
            updateDate(horizontalCalendarModels.get(0).getDate());

    }

    private void getBookingUsageTypes() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UsageTypeResponse>> call = apiService.getBookingUsageTypes();
            call.enqueue(new Callback<List<UsageTypeResponse>>() {
                @Override
                public void onResponse(Call<List<UsageTypeResponse>> call, Response<List<UsageTypeResponse>> response) {

                }

                @Override
                public void onFailure(Call<List<UsageTypeResponse>> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
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

            }

            @Override
            public void onFailure(Call<List<FirstAidResponse>> call, Throwable t) {

            }
        });

    }


    private void getCalendarEntryTeam() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            int userID = SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID);
            int teamID = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);

            Call<ArrayList<DAOUpcomingBooking>> call = apiService.getMonthBookings(selectedDate, userID, teamID);
            call.enqueue(new Callback<ArrayList<DAOUpcomingBooking>>() {
                @Override
                public void onResponse(Call<ArrayList<DAOUpcomingBooking>> call, Response<ArrayList<DAOUpcomingBooking>> response) {

                    if (response.body() != null) {
                        ArrayList<DAOUpcomingBooking> daoUpcomingBookingArrayList = response.body();

                        for (int i = 0; i < daoUpcomingBookingArrayList.size(); i++) {

                            System.out.println("CalendarEntryReceivedDataTeamId " + daoUpcomingBookingArrayList.get(i).getTeamId());

                        }
                    }

                    binding.locateProgressBar.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<ArrayList<DAOUpcomingBooking>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }


    private void getTeamMembers() {

        if (Utils.isNetworkAvailable(getActivity())) {
            View textView = (LinearLayout) View.inflate(getContext(), R.layout.floor_layout_recycler, null);
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<DAOTeamMember>> call = apiService.getTeamMembersWithImage(selectedDate,
                    "" + SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID),
                    true, true);
            call.enqueue(new Callback<ArrayList<DAOTeamMember>>() {
                @Override
                public void onResponse(Call<ArrayList<DAOTeamMember>> call,
                                       Response<ArrayList<DAOTeamMember>> response) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    if (response.body() != null) {
                        if (response.code() == 200) {
//                            binding.tvExapnd.setVisibility(View.VISIBLE);
                            binding.expandRecyclerView.setVisibility(View.GONE);
                            teamMembersUnknownList.clear();
                            teamMembersRemoteList.clear();
                            teamMembersTrainingList.clear();
                            teamMembersHolidayList.clear();
                            teamMembersOutOfOffice.clear();
                            teamMembersInOfficeList.clear();
                            teamMembersUnknownListNew.clear();
                            teamMembersRemoteListNew.clear();
                            teamMembersTrainingListNew.clear();
                            teamMembersHolidayListNew.clear();
                            teamMembersOutOfOfficeNew.clear();
                            teamMembersInOfficeListNew.clear();
                            teamMembersList = response.body();
                            copyTeamMembersList = response.body();
                            floorList.clear();

                            for (int i = 0; i < teamMembersList.size(); i++) {

                                if (teamMembersList.get(i).getDayGroups().size() > 0
                                        && teamMembersList.get(i).getDayGroups().get(0)
                                        .getCalendarEntries().size() > 0) {
                                    for (int x = 0; x < teamMembersList.get(i).getDayGroups().get(0)
                                            .getCalendarEntries().size(); x++) {

                                        switch (teamMembersList.get(i).getDayGroups().get(0)
                                                .getCalendarEntries().get(x)
                                                .getUsageTypeAbbreviation()) {
                                            case "IO":
                                                try {
                                                    TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                                    TeamsMemberListDataModel.CalendarEntry calendarEntry = new TeamsMemberListDataModel.CalendarEntry();
                                                    TeamsMemberListDataModel.CalendarEntry.Booking calendarEntryBooking = new TeamsMemberListDataModel.CalendarEntry.Booking();
                                                    TeamsMemberListDataModel.CalendarEntry.Booking.Status calendarEntryBookingStatus = new TeamsMemberListDataModel.CalendarEntry.Booking.Status();
                                                    TeamsMemberListDataModel.LocationBuildingFloor locationBuildingFloor = new TeamsMemberListDataModel.LocationBuildingFloor();

                                                    daoTeamMember.setDaoTeamMember(teamMembersList.get(i));
                                                    daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                                    daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                                    daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                                    daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                                    daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                                    daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                                    daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                                    daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                                    daoTeamMember.setUserId(teamMembersList.get(i).getUserId());

                                                    calendarEntry.setFrom(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    calendarEntry.setMyto(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());

                                                    calendarEntryBooking.setDeskCode(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getDeskCode());

                                                    locationBuildingFloor.setBuildingID(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getLocationBuildingFloor().getBuildingID());
                                                    locationBuildingFloor.setFloorID(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getLocationBuildingFloor().getFloorID());
                                                    locationBuildingFloor.setBuildingName(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getLocationBuildingFloor().getBuildingName());
                                                    locationBuildingFloor.setfLoorName(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getLocationBuildingFloor().getfLoorName());

                                                    calendarEntryBookingStatus.setId(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getStatus().getId());
                                                    calendarEntryBookingStatus.setName(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getBooking().getStatus().getName());

                                                    calendarEntryBooking.setLocationBuildingFloor(locationBuildingFloor);
                                                    calendarEntryBooking.setStatus(calendarEntryBookingStatus);
                                                    calendarEntry.setBooking(calendarEntryBooking);
                                                    daoTeamMember.setCalendarEntriesModel(calendarEntry);

                                                    teamMembersList.get(i);
                                                    teamMembersInOfficeList.add(teamMembersList.get(i));
                                                    teamMembersInOfficeListNew.add(daoTeamMember);
                                                    /*daoTeamMember.setTeamFromTIme(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    daoTeamMember.setTeamToTime(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());
*/

//                                                for (int j=0; j<teamMembersList.get(i).getDayGroups().get(0).getCalendarEntries().size();j++){
                                                    if (teamMembersList.get(i).getDayGroups().get(0).getCalendarEntries()
                                                            .get(x).getBooking() != null
                                                            && !floorList.containsKey(teamMembersList.get(i).getDayGroups().get(0).getCalendarEntries()
                                                            .get(x).getBooking().getLocationBuildingFloor().getFloorID())) {
                                                        floorList.put(teamMembersList.get(i).getDayGroups().get(0).getCalendarEntries()
                                                                        .get(x).getBooking().getLocationBuildingFloor().getFloorID(),
                                                                teamMembersList.get(i).getDayGroups().get(0).getCalendarEntries()
                                                                        .get(x).getBooking().getLocationBuildingFloor().getfLoorName());
                                                    }
                                                } catch (Exception e){

                                                }
                                                break;
                                            case "WFH":
                                                try {
                                                    TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                                    TeamsMemberListDataModel.CalendarEntry calendarEntry = new TeamsMemberListDataModel.CalendarEntry();
                                                    daoTeamMember.setDaoTeamMember(teamMembersList.get(i));

                                                    daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                                    daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                                    daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                                    daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                                    daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                                    daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                                    daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                                    daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                                    daoTeamMember.setUserId(teamMembersList.get(i).getUserId());

                                                    calendarEntry.setFrom(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    calendarEntry.setMyto(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());

                                                    daoTeamMember.setCalendarEntriesModel(calendarEntry);

                                                    teamMembersRemoteList.add(teamMembersList.get(i));
                                                    teamMembersRemoteListNew.add(daoTeamMember);
                                                }catch (Exception e){

                                                }
                                                break;
                                            case "OO":
                                                try {
                                                    TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                                    TeamsMemberListDataModel.CalendarEntry calendarEntry = new TeamsMemberListDataModel.CalendarEntry();
                                                    daoTeamMember.setDaoTeamMember(teamMembersList.get(i));

                                                    daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                                    daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                                    daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                                    daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                                    daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                                    daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                                    daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                                    daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                                    daoTeamMember.setUserId(teamMembersList.get(i).getUserId());

                                                    calendarEntry.setFrom(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    calendarEntry.setMyto(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());

                                                    daoTeamMember.setCalendarEntriesModel(calendarEntry);
                                                    teamMembersOutOfOffice.add(teamMembersList.get(i));
                                                    teamMembersOutOfOfficeNew.add(daoTeamMember);
                                                } catch (Exception e){

                                                }
                                                break;
                                            case "TR":
                                                try {
                                                    TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                                    TeamsMemberListDataModel.CalendarEntry calendarEntry = new TeamsMemberListDataModel.CalendarEntry();
                                                    daoTeamMember.setDaoTeamMember(teamMembersList.get(i));

                                                    daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                                    daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                                    daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                                    daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                                    daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                                    daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                                    daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                                    daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                                    daoTeamMember.setUserId(teamMembersList.get(i).getUserId());

                                                    calendarEntry.setFrom(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    calendarEntry.setMyto(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());

                                                    daoTeamMember.setCalendarEntriesModel(calendarEntry);

                                                    teamMembersTrainingList.add(teamMembersList.get(i));
                                                    teamMembersTrainingListNew.add(daoTeamMember);
                                                }catch (Exception e){

                                                }
                                                break;
                                            case "SL":
                                                try {
                                                    TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                                    TeamsMemberListDataModel.CalendarEntry calendarEntry = new TeamsMemberListDataModel.CalendarEntry();
                                                    daoTeamMember.setDaoTeamMember(teamMembersList.get(i));

                                                    daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                                    daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                                    daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                                    daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                                    daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                                    daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                                    daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                                    daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                                    daoTeamMember.setUserId(teamMembersList.get(i).getUserId());

                                                    calendarEntry.setFrom(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getFrom());
                                                    calendarEntry.setMyto(teamMembersList.get(i).getDayGroups().get(0)
                                                            .getCalendarEntries().get(x).getMyto());

                                                    daoTeamMember.setCalendarEntriesModel(calendarEntry);

                                                    teamMembersHolidayList.add(teamMembersList.get(i));
                                                    teamMembersHolidayListNew.add(daoTeamMember);
                                                }catch (Exception e){

                                                }
                                                break;
                                            default:

                                        }
                                    }
                                } else {
                                    try {
                                        TeamsMemberListDataModel daoTeamMember = new TeamsMemberListDataModel();
                                        daoTeamMember.setDaoTeamMember(teamMembersList.get(i));

                                        daoTeamMember.setTeamId(teamMembersList.get(i).getTeamId());
                                        daoTeamMember.setTeamMembershipId(teamMembersList.get(i).getTeamMembershipId());
                                        daoTeamMember.setIfFirstAidStatus(teamMembersList.get(i).isIfFirstAidStatus());
                                        daoTeamMember.setFireStatus(teamMembersList.get(i).isFireStatus());
                                        daoTeamMember.setFirstName(teamMembersList.get(i).getFirstName());
                                        daoTeamMember.setLastName(teamMembersList.get(i).getLastName());
                                        daoTeamMember.setProfileImage(teamMembersList.get(i).getProfileImage());
                                        daoTeamMember.setProfileImageUrl(teamMembersList.get(i).getProfileImageUrl());
                                        daoTeamMember.setUserId(teamMembersList.get(i).getUserId());


                                        teamMembersUnknownList.add(teamMembersList.get(i));
                                        teamMembersUnknownListNew.add(daoTeamMember);
                                    }catch (Exception e){

                                    }
                                }
                            }

                            if (teamMembersList != null && teamMembersList.size() > 0) {
                                if (teamMembersInOfficeList.size() > 0) {
                                    System.out.println("check cala" + floorList.size());
                                    if (floorList.size() > 1) {
                                        createMultipleFloorRecyler();
                                    } else {
                                        createMultipleFloorRecyler();
//                                        getAvaliableDeskDetails(-1,null);
                                    }

                                }
                                setValueToAdapter(teamMembersList);

                            }
                        } else if (response.code() == 401) {
                            //Handle if token got expired
                            binding.locateProgressBar.setVisibility(View.INVISIBLE);
                            Utils.tokenExpiryAlert(getActivity(), "");

                        } else {
                            binding.locateProgressBar.setVisibility(View.INVISIBLE);
                            Log.d("Search", "onResponse: else");
                            Utils.showCustomAlertDialog(getActivity(), "Api Issue Code: " + response.code());
                        }
                    } else {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Utils.showCustomAlertDialog(getActivity(), "No Response");
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<DAOTeamMember>> call, Throwable t) {
//                    Toast.makeText(getActivity(), "on fail", Toast.LENGTH_SHORT).show();
                    Utils.showCustomAlertDialog(getActivity(), "Response Failure: " + t.getMessage());
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Log.d("Search", "onResponse: fail" + t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }

    private void createMultipleFloorRecyler() {
        ArrayList<FloorListModel> floorListModels = new ArrayList<>();
        Iterator entries = floorList.entrySet().iterator();
        while (entries.hasNext()) {
            FloorListModel floorListModel = new FloorListModel();
            Map.Entry<Integer, String> thisEntry = (Map.Entry) entries.next();
            Integer key = thisEntry.getKey();
            String value = thisEntry.getValue();

            // TODO: 16-09-2022
            System.out.println("check hash" + value);

            ArrayList<DAOTeamMember> teamMembersFloorList = new ArrayList<>();
            for (int i = 0; i < teamMembersInOfficeList.size(); i++) {
                loopu:
                for (int j = 0; j < teamMembersInOfficeList.get(i).getDayGroups().get(0)
                        .getCalendarEntries().size(); j++) {
                    if (teamMembersInOfficeList.get(i).getDayGroups().get(0).getCalendarEntries()
                            .get(j).getBooking() != null
                            &&
                            key.equals(teamMembersInOfficeList.get(i).getDayGroups().get(0).getCalendarEntries()
                                    .get(j).getBooking().getLocationBuildingFloor()
                                    .getFloorID())) {

                        teamMembersFloorList.add(teamMembersInOfficeList.get(i));
                        break loopu;
                    }
                }
            }
            ArrayList<TeamsMemberListDataModel> teamMembersFloorListnew = new ArrayList<>();
            for (int i = 0; i < teamMembersInOfficeListNew.size(); i++) {
                if (teamMembersInOfficeListNew.get(i).getCalendarEntriesModel().getBooking() != null
                        &&
                        key.equals(teamMembersInOfficeListNew.get(i).getCalendarEntriesModel().getBooking().getLocationBuildingFloor()
                                .getFloorID())) {
                    teamMembersFloorListnew.add(teamMembersInOfficeListNew.get(i));

                }
            }

            floorListModel.setFloorId(key);
            floorListModel.setFloorName(value);
            floorListModel.setDaoTeamMembers(teamMembersFloorList);
            floorListModel.setDaoTeamMembersNew(teamMembersFloorListnew);
            floorListModels.add(floorListModel);

//            getAvaliableDeskDetails(key, tvAvailableCount);


           /* floorLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loopu:
                    for (int i=0; i<teamMembersInOfficeList.size(); i++){
                        for (int j=0; j<teamMembersInOfficeList.get(i).getDayGroups().get(0)
                                .getCalendarEntries().size(); j++){
                            if (floorName.getText().toString().equalsIgnoreCase(teamMembersInOfficeList.get(i).getDayGroups().get(0).getCalendarEntries()
                                    .get(j).getBooking().getLocationBuildingFloor()
                                    .getfLoorName())) {
                                int parentLocationId= teamMembersInOfficeList.get(i).getDayGroups().get(0).getCalendarEntries()
                                        .get(j).getBooking().getLocationBuildingFloor()
                                        .getFloorID();
                                int deskId = teamMembersInOfficeList.get(i).getDayGroups().get(0).getCalendarEntries()
                                        .get(j).getBooking().getDeskId();
                                onLocationIconClick(parentLocationId, deskId, AppConstants.DESK);
                                break loopu;
                            }
                        }
                    }
                    Toast.makeText(getActivity(), "I : "+floorName, Toast.LENGTH_SHORT).show();
                }
            });
*/


//            binding.floorLinearLayout.addView();
//            RelativeLayout relativeLayout = new RelativeLayout();
            // ...
        }
        getAvaliableDeskDetails(floorListModels);


    }

    private void setValueToAdapter(ArrayList<DAOTeamMember> teamMembersList) {
        if (teamMembersTrainingListNew.size() > 0) {
            binding.tvWorkTraining.setVisibility(View.VISIBLE);
            binding.recyclerViewTraining.setVisibility(View.VISIBLE);
        } else {
            binding.tvWorkTraining.setVisibility(View.GONE);
            binding.recyclerViewTraining.setVisibility(View.GONE);
        }
        if (teamMembersRemoteListNew.size() > 0) {
            binding.tvWorkRemote.setVisibility(View.VISIBLE);
            binding.recyclerViewRemote.setVisibility(View.VISIBLE);
        } else {
            binding.tvWorkRemote.setVisibility(View.GONE);
            binding.recyclerViewRemote.setVisibility(View.GONE);
        }

        if (teamMembersUnknownListNew.size() > 0) {
            binding.tvUnknown.setVisibility(View.VISIBLE);
            binding.recyclerViewUnkown.setVisibility(View.VISIBLE);
        } else {
            binding.tvUnknown.setVisibility(View.GONE);
            binding.recyclerViewUnkown.setVisibility(View.GONE);
        }
        if (teamMembersOutOfOfficeNew.size() > 0) {
            binding.tvOutOfOffice.setVisibility(View.VISIBLE);
            binding.recyclerViewOutOfOffice.setVisibility(View.VISIBLE);
        } else {
            binding.tvOutOfOffice.setVisibility(View.GONE);
            binding.recyclerViewOutOfOffice.setVisibility(View.GONE);
        }

        if (teamMembersHolidayListNew.size() > 0) {
            binding.tvHoliday.setVisibility(View.VISIBLE);
            binding.recyclerViewHoliday.setVisibility(View.VISIBLE);
        } else {
            binding.tvHoliday.setVisibility(View.GONE);
            binding.recyclerViewHoliday.setVisibility(View.GONE);
        }
        if (teamMembersInOfficeListNew.size() > 0) {
//            binding.tvFloorName.setVisibility(View.VISIBLE);
            binding.tvAdddress.setVisibility(View.GONE);
            binding.listTitle.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
//            binding.tvAvailableCount.setVisibility(View.VISIBLE);
//            binding.ivLocation.setVisibility(View.VISIBLE);
//            binding.tvFloorName.setText(""+teamMembersInOfficeList.get(0).getDayGroups()
//                    .get(0).getCalendarEntries()
//                    .get(0).getBooking().getLocationBuildingFloor().getfLoorName());
            if (teamMembersInOfficeListNew.get(0).getCalendarEntriesModel().getBooking()!= null)
                binding.tvAdddress.setText("" + teamMembersInOfficeListNew.get(0)
                        .getCalendarEntriesModel()
                        .getBooking().getLocationBuildingFloor().getBuildingName());
        } else {
//            binding.tvFloorName.setVisibility(View.GONE);
            binding.tvAdddress.setVisibility(View.GONE);
            binding.listTitle.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
//            binding.tvAvailableCount.setVisibility(View.GONE);
//            binding.ivLocation.setVisibility(View.GONE);
        }

//        teamsContactsAdapter = new TeamsContactsAdapter(getActivity(),teamMembersInOfficeList,this);
//        binding.recyclerView.setAdapter(teamsContactsAdapter);

        if (expandStatus)
            binding.recyclerViewTraining.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        else
            binding.recyclerViewTraining.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        teamsContactsTrainingAdapter = new TeamsContactsAdapter(getActivity(), teamMembersTrainingList,teamMembersTrainingListNew, this, this);
        binding.recyclerViewTraining.setAdapter(teamsContactsTrainingAdapter);

        if (expandStatus)
            binding.recyclerViewRemote.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        else
            binding.recyclerViewRemote.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        teamsContactsRemoteAdapter = new TeamsContactsAdapter(getActivity(), teamMembersRemoteList,teamMembersRemoteListNew, this, this);
        binding.recyclerViewRemote.setAdapter(teamsContactsRemoteAdapter);


        if (expandStatus)
            binding.recyclerViewOutOfOffice.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        else
            binding.recyclerViewOutOfOffice.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        teamsContactsOutOfOfficeAdapter = new TeamsContactsAdapter(getActivity(), teamMembersOutOfOffice,teamMembersOutOfOfficeNew,
                this, this);
        binding.recyclerViewOutOfOffice.setAdapter(teamsContactsOutOfOfficeAdapter);

        if (expandStatus)
            binding.recyclerViewHoliday.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        else
            binding.recyclerViewHoliday.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        teamsContactsHolidaysAdapter = new TeamsContactsAdapter(getActivity(), teamMembersHolidayList,teamMembersHolidayListNew, this, this);
        binding.recyclerViewHoliday.setAdapter(teamsContactsHolidaysAdapter);

        if (expandStatus)
            binding.recyclerViewUnkown.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
        else
            binding.recyclerViewUnkown.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        teamsContactsUnknownAdapter = new TeamsContactsAdapter(getActivity(), teamMembersUnknownList,teamMembersUnknownListNew, this, this);
        binding.recyclerViewUnkown.setAdapter(teamsContactsUnknownAdapter);
    }

    @Override
    public void clickEvent(DAOTeamMember daoTeamMember) {
        selID = daoTeamMember.getUserId();
        Intent intent = new Intent(getActivity(), ShowProfileActivity.class);
        String personJsonString = new Gson().toJson(daoTeamMember);

        ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
        extras.putExtra(AppConstants.USER_CURRENT_STATUS, personJsonString);
        //  intent.putExtra(AppConstants.USER_CURRENT_STATUS, daoTeamMember);
        intent.putExtra("DATE", currendate);
        startActivity(intent);

        /*callSearchRecyclerData(fName + " " + lName);
        callTeamMemberStatus();*/
    }

    @Override
    public void onProfileClick(DAOTeamMember daoTeamMember) {
        if (daoTeamMember != null) {

            Intent intent = new Intent(getActivity(), ShowProfileActivity.class);
            String personJsonString = new Gson().toJson(daoTeamMember);

            ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
            extras.putExtra(AppConstants.USER_CURRENT_STATUS, personJsonString);

            //intent.putExtra(AppConstants.USER_CURRENT_STATUS,daoTeamMember);
            intent.putExtra("DATE", currendate);
            startActivity(intent);
        } else {
            expandStatus = true;
            binding.tvExapnd.setVisibility(View.GONE);
            getTeamMembers();
            /*
            binding.expandRecyclerView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvUnknown.setVisibility(View.GONE);
            binding.tvHoliday.setVisibility(View.GONE);
            binding.tvOutOfOffice.setVisibility(View.GONE);
//            binding.tvFloorName.setVisibility(View.GONE);
            binding.tvAdddress.setVisibility(View.GONE);
            binding.listTitle.setVisibility(View.GONE);
//            binding.tvAvailableCount.setVisibility(View.GONE);
//            binding.ivLocation.setVisibility(View.GONE);
            binding.tvWorkRemote.setVisibility(View.GONE);
            binding.recyclerViewRemote.setVisibility(View.GONE);
            binding.recyclerViewOutOfOffice.setVisibility(View.GONE);
            binding.recyclerViewHoliday.setVisibility(View.GONE);
            binding.recyclerViewUnkown.setVisibility(View.GONE);
            binding.tvTotalAvail.setVisibility(View.VISIBLE);
            binding.tvExapnd.setVisibility(View.GONE);
            setDataToExpandAdapter(teamMembersList);*/
        }

    }

    @Override
    public void floorListenerClick(int parentLocationId, int identifierId, String desk) {
        onLocationIconClick(parentLocationId, identifierId, desk);
    }

    @Override
    public void updateAdapterList(TeamsContactsAdapter teamsContactsAdapter) {
        if (dynamicAdapters == null)
            dynamicAdapters = new ArrayList<>();
        else
            dynamicAdapters.add(teamsContactsAdapter);
    }

    @Override
    public void calendarSelectedDate(String date, int oldSelectedPos, int newSelectedPos) {
        horizontalCalendarModels.get(oldSelectedPos).setSelected(false);
        horizontalCalendarModels.get(newSelectedPos).setSelected(true);
        horizontalCalTeamsAdapter.notifyItemChanged(oldSelectedPos);
        horizontalCalTeamsAdapter.notifyItemChanged(newSelectedPos);


        currendate = date;

        updateDate(currendate);

    }

    private void updateDate(String currendate) {
        try {
            currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
            selectedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
            binding.tvExapnd.setVisibility(View.VISIBLE);
            expandStatus = false;
            if (dynamicAdapters != null)
                dynamicAdapters.clear();
            getTeamMembers();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final static int vertOverlap = -20;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);


            outRect.set(0, 0, vertOverlap, 0);


        }
    }

    private void setDataToExpandAdapter(ArrayList<DAOTeamMember> teamMembersList) {
        teamsAdapter = new TeamsAdapter(getActivity(), teamMembersList, this, firstAidList, firewardenList);
        binding.expandRecyclerView.setAdapter(teamsAdapter);
    }


    private void uiInit(View root) {

        contactLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        contactUnknownLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        contactTrainingLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        contactRemoteLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        contactOutOfOfficeLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        contactHolidayLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(contactLinearLayout);
        binding.recyclerViewTraining.setLayoutManager(contactTrainingLinearLayout);
        binding.recyclerViewRemote.setLayoutManager(contactRemoteLinearLayout);
        binding.recyclerViewOutOfOffice.setLayoutManager(contactOutOfOfficeLinearLayout);
        binding.recyclerViewHoliday.setLayoutManager(contactHolidayLinearLayout);
        binding.recyclerViewUnkown.setLayoutManager(contactUnknownLinearLayout);
//        binding.recyclerView.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewTraining.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewRemote.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewOutOfOffice.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewHoliday.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewUnkown.addItemDecoration(new OverlapDecoration());
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerViewTraining.setHasFixedSize(true);
        binding.recyclerViewRemote.setHasFixedSize(true);
        binding.recyclerViewOutOfOffice.setHasFixedSize(true);
        binding.recyclerViewHoliday.setHasFixedSize(true);
        binding.recyclerViewUnkown.setHasFixedSize(true);

//        teamsContactsAdapter = new TeamsContactsAdapter(getActivity(),teamMembersInOfficeList, this);
//        binding.recyclerView.setAdapter(teamsContactsAdapter);

        teamsContactsTrainingAdapter = new TeamsContactsAdapter(getActivity(), teamMembersTrainingList,teamMembersTrainingListNew, this, this);
        binding.recyclerViewTraining.setAdapter(teamsContactsTrainingAdapter);

        teamsContactsRemoteAdapter = new TeamsContactsAdapter(getActivity(), teamMembersRemoteList,teamMembersRemoteListNew, this, this);
        binding.recyclerViewRemote.setAdapter(teamsContactsRemoteAdapter);

        teamsContactsOutOfOfficeAdapter = new TeamsContactsAdapter(getActivity(), teamMembersOutOfOffice,teamMembersOutOfOfficeNew, this, this);
        binding.recyclerViewOutOfOffice.setAdapter(teamsContactsOutOfOfficeAdapter);

        teamsContactsHolidaysAdapter = new TeamsContactsAdapter(getActivity(), teamMembersHolidayList,teamMembersHolidayListNew, this, this);
        binding.recyclerViewHoliday.setAdapter(teamsContactsHolidaysAdapter);

        teamsContactsUnknownAdapter = new TeamsContactsAdapter(getActivity(), teamMembersUnknownList,teamMembersUnknownListNew, this, this);
        binding.recyclerViewHoliday.setAdapter(teamsContactsUnknownAdapter);

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

        //final HorizontalCalendar horizontalCalendar
        horizontalCalendar
                = new HorizontalCalendar.Builder(root, R.id.calendarView)
                .range(startDate, endDate)
                .mode(HorizontalCalendar.Mode.DAYS)
                .datesNumberOnScreen(5)
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
                int a = horizontalCalendar.getSelectedDatePosition();

                try {
                    currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
                    selectedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
                    binding.tvExapnd.setVisibility(View.VISIBLE);
                    expandStatus = false;
                    if (dynamicAdapters != null)
                        dynamicAdapters.clear();
                    getTeamMembers();
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

    //New...

    private void filter(String text) {

        teamMembersList = new ArrayList<>();

        for (DAOTeamMember staff : copyTeamMembersList) {
            if (staff.getFirstName().toLowerCase().contains(text) ||
                    staff.getLastName().toLowerCase().contains(text)) {
                teamMembersList.add(staff);
            }
        }

        setDataToExpandAdapter(teamMembersList);

    }

    /*private void callSearchRecyclerData(String searchText) {
        if (Utils.isNetworkAvailable(getActivity())) {

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
                        Toast.makeText(getActivity(), "ls "+list.size(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(getActivity(), "200"+searchText, Toast.LENGTH_SHORT).show();

                        if (list!=null && list.size()>0){

                            for (int i=0;i<list.size();i++){

                                if (selID == list.get(i).getId()) {

                                    List<GlobalSearchResponse.Results> resultsList = new ArrayList<>();
                                    resultsList.add(list.get(i));

                                    Intent intent = new Intent(getActivity(),ShowProfileActivity.class);
                                    intent.putExtra(AppConstants.USER_ID,selID);
                                    intent.putExtra("USER_DETAILS", (Serializable) resultsList);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }

                        //binding.searchRecycler.setVisibility(View.VISIBLE);
                        //searchRecyclerAdapter.notifyDataSetChanged();

                    }else if(response.code()==401){
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);

                    } else {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Log.d("Search", "onResponse: else");
                    }

                }
                @Override
                public void onFailure(Call<GlobalSearchResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "on fail", Toast.LENGTH_SHORT).show();
//                    ProgressDialog.dismisProgressBar(getActivity(),dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void callTeamMemberStatus() {
        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(Utils.getCurrentDate(),
                    SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){

                        teamMembersResponses = response.body();
                        if (teamMembersResponses!=null &&
                                teamMembersResponses.size()>0){
                            for (int i=0;i<teamMembersResponses.size();i++){
                                arrayString.add(" "+teamMembersResponses.get(i).getFirstName()+" "+
                                        teamMembersResponses.get(i).getLastName());
                            }
                            //adapter.notifyDataSetChanged();
                        }

                    }else if(response.code()==401){
                        //Handle if token got expired
                        Utils.tokenExpiryAlert(getActivity(),"");

                    } else {
                        Log.d("Search", "onResponse: else");
                        Utils.showCustomAlertDialog(getActivity(),"Api Issue Code: "+response.code());
                    }

                }
                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(getActivity(), "on fail", Toast.LENGTH_SHORT).show();
                    Utils.showCustomAlertDialog(getActivity(),"Response Failure: "+t.getMessage());
                    ProgressDialog.dismisProgressBar(getActivity(),dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }*/
    public void onLocationIconClick(int parentLocationId, int identifierId, String desk) {

//        NavController navController= Navigation.findNavController(view);

        SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, parentLocationId);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentLocationId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                for (int i = 0; i < locateCountryResposeList.size(); i++) {

                    if (desk.equals(AppConstants.DESK)) {

                        for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getDesks().size(); j++) {

                            if (identifierId == locateCountryResposeList.get(i).getLocationItemLayout().getDesks().get(j).getDesksId()) {
                                SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);


                                System.out.println("SelectedDeskFloorInLocate " + i + " " + desk + " ");

                                ((MainActivity) getActivity()).callLocateFragmentFromHomeFragment();
//                                navController.navigate(R.id.action_navigation_home_to_navigation_locate);
                            }
                        }
                    } else if (desk.equals(AppConstants.MEETING)) {

                        for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getMeetingRoomsList().size(); j++) {

                            if (identifierId == locateCountryResposeList.get(i).getLocationItemLayout().getMeetingRoomsList().get(j).getMeetingRoomId()) {
                                SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);

                                System.out.println("SelectedMeetingFloorInLocate " + i + " " + desk + " ");

                                ((MainActivity) getActivity()).callLocateFragmentFromHomeFragment();
//                                navController.navigate(R.id.navigation_locate);
                            }

                        }


                    } else if (desk.equals(AppConstants.CAR_PARKING)) {

                        for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getParkingSlotsList().size(); j++) {

                            if (identifierId == locateCountryResposeList.get(i).getLocationItemLayout().getParkingSlotsList().get(j).getId()) {
                                SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);

                                System.out.println("SelectedCarFloorInLocate " + i + " " + desk + " ");
                                ((MainActivity) getActivity()).callLocateFragmentFromHomeFragment();
//                                navController.navigate(R.id.navigation_locate);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

            }
        });

    }

    private void getAvaliableDeskDetails(ArrayList<FloorListModel> floorListModelArrayList) {
        ArrayList<FloorListModel> list = floorListModelArrayList;
        for (int i = 0; i < floorListModelArrayList.size(); i++) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        System.out.println("check cala"+horizontalCalendar.getSelectedDate().getTime());

            String toDate = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(selectedDate))
                    + "T00:00:00Z";
            String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(selectedDate))
                    + "T" + "08:00" + ":00Z";
            String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(selectedDate))
                    + "T23:59:00Z";

            int parentId = floorListModelArrayList.get(i).getFloorId();

//        System.out.println("parent Booking cje"+parentId);
            //Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
            Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId,
                    toDate,
                    fromTime,
                    toTime);

            int finalI = i;
            call.enqueue(new Callback<DeskAvaliabilityResponse>() {
                @Override
                public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                    DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();
                    int teamAvailableCount = 0;
                    if (deskAvaliabilityResponseList != null) {
                        for (int i = 0; i < deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size(); i++) {
                            if (!deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).isBookedByElse()
                                    || !deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).isBookedByUser()) {
                                teamAvailableCount++;
                            }
                        }
                        System.out.println("tets vals" + teamAvailableCount + "/"
                                + deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size()
                                + " desks available");
                        list.get(finalI).setDeskAvailability(teamAvailableCount + "/"
                                + deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size()
                                + " desks available");
                        System.out.println("tets vals : ij" + list.get(finalI).getDeskAvailability() + finalI);

                        if (teamsFloorListAdapter != null) {
                            System.out.println("tets vals : ad" + list.get(0).getDeskAvailability());
                            teamsFloorListAdapter.notifyDataSetChanged();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                    floorListModelArrayList.get(finalI).setDeskAvailability(0 + "/"
                            + "0 desks available");
                    System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

//        System.out.println("tets vals : bj"+list.get(0).getDeskAvailability());
//        System.out.println("tets vals : bj"+list.get(1).getDeskAvailability());
        teamsFloorListAdapter = new TeamsFloorListAdapter(getActivity(),
                list, this, this, this);
        binding.recyclerView.setAdapter(teamsFloorListAdapter);

    }


    public void setLanguage() {
        mePage = getMeScreenData(getActivity());
        appKeysPage = getAppKeysPageScreenData(getActivity());
        resetPage = getResetPasswordPageScreencreenData(getActivity());
        actionOverLays = getActionOverLaysPageScreenData(getActivity());

        bookindata = getBookingPageScreenData(getActivity());

        System.out.println("null pointer" + mePage);
        System.out.println("null pointer" + mePage.getMyTeam());
        binding.title.setText(mePage.getMyTeam());
        binding.txtAllteam.setText(appKeysPage.getAllTeams());
        binding.serachBar.setHint(appKeysPage.getSearch());
        binding.tvExapnd.setText(appKeysPage.getExpandAll());
        binding.listTitle.setText(appKeysPage.getWorkFromOffice());
        binding.tvUnknown.setText(appKeysPage.getNoBookingsAvailable());
        binding.tvTotalAvail.setText(appKeysPage.getDesksAvailable());
        binding.teamName.setText(SessionHandler.getInstance().get(getActivity(), AppConstants.CURRENT_TEAM)
                == null ? "" : SessionHandler.getInstance().get(getActivity(), AppConstants.CURRENT_TEAM));
    }

}
