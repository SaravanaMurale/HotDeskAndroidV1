package dream.guys.hotdeskandroid.ui.teams;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.SearchRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsContactsAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentTeamsBinding;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBooking;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.UsageTypeResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment implements TeamsAdapter.TeamMemberInterface, TeamsContactsAdapter.OnProfileClickable {

    FragmentTeamsBinding binding;
    int day, month, year;
    HorizontalCalendar horizontalCalendar;
    String currendate = "",selectedDate="";
    HorizontalCalendarView calendarView;
    ArrayList<DAOTeamMember> teamMembersList = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersInOfficeList = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersRemoteList = new ArrayList<>();
    ArrayList<DAOTeamMember> teamMembersHolidayList = new ArrayList<>();
    ArrayList<DAOTeamMember> copyTeamMembersList = new ArrayList<>();
    TeamsContactsAdapter teamsContactsAdapter,teamsContactsRemoteAdapter,teamsContactsHolidaysAdapter;
    TeamsAdapter teamsAdapter;

    List<GlobalSearchResponse.Results> list = new ArrayList<>();
    SearchRecyclerAdapter searchRecyclerAdapter;
    LinearLayoutManager linearLayoutManager,contactLinearLayout,contactHolidayLinearLayout,contactRemoteLinearLayout;

    int selID = 0;

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

        uiInit(root);

        binding.tvExapnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(),UpComingBookingActivity.class);
                //startActivity(intent);

                binding.expandRecyclerView.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.recyclerViewRemote.setVisibility(View.GONE);
                binding.recyclerViewHoliday.setVisibility(View.GONE);
                binding.tvTotalAvail.setVisibility(View.VISIBLE);

                setDataToExpandAdapter(teamMembersList);

            }
        });

        binding.txtAllteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TeamsUserActivity.class);
                startActivity(intent);
            }
        });

        binding.serachBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals("")){

                    filter(s.toString().toLowerCase());
                }else {

                    teamMembersList = new ArrayList<>();
                    teamMembersList.addAll(copyTeamMembersList);

                    setDataToExpandAdapter(teamMembersList);

                }

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
                setValueToAdapter(teamMembersList);
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
        searchRecyclerAdapter=new SearchRecyclerAdapter(getActivity(),list);
        binding.searchRecycler.setAdapter(searchRecyclerAdapter);

        return root;
    }

    private void getBookingUsageTypes() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
           Call<List<UsageTypeResponse>> call= apiService.getBookingUsageTypes();
           call.enqueue(new Callback<List<UsageTypeResponse>>() {
               @Override
               public void onResponse(Call<List<UsageTypeResponse>> call, Response<List<UsageTypeResponse>> response) {

               }

               @Override
               public void onFailure(Call<List<UsageTypeResponse>> call, Throwable t) {

               }
           });

        }else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void getCalendarEntryTeam() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            int userID=SessionHandler.getInstance().getInt(getContext(),AppConstants.USER_ID);
            int teamID=SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID);

            Call<ArrayList<DAOUpcomingBooking>> call = apiService.getMonthBookings(selectedDate,userID,teamID);
            call.enqueue(new Callback<ArrayList<DAOUpcomingBooking>>() {
                @Override
                public void onResponse(Call<ArrayList<DAOUpcomingBooking>> call, Response<ArrayList<DAOUpcomingBooking>> response) {

                    ArrayList<DAOUpcomingBooking> daoUpcomingBookingArrayList=response.body();

                    for (int i = 0; i <daoUpcomingBookingArrayList.size() ; i++) {

                        System.out.println("CalendarEntryReceivedDataTeamId "+daoUpcomingBookingArrayList.get(i).getTeamId());

                    }

                    binding.locateProgressBar.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<ArrayList<DAOUpcomingBooking>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }


    private void getTeamMembers() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<DAOTeamMember>> call = apiService.getTeamMembers(selectedDate);
            call.enqueue(new Callback<ArrayList<DAOTeamMember>>() {
                @Override
                public void onResponse(Call<ArrayList<DAOTeamMember>> call,
                                       Response<ArrayList<DAOTeamMember>> response) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    if (response.body()!=null){
                        if(response.code()==200){

                            teamMembersRemoteList.clear();
                            teamMembersHolidayList.clear();
                            teamMembersList = response.body();
                            copyTeamMembersList = response.body();

                            for (int i=0; i < teamMembersList.size(); i++){
                                if (teamMembersList.get(i).getDayGroups().size() > 0){
                                    switch (teamMembersList.get(i).getDayGroups().get(0)
                                            .getCalendarEntries().get(0)
                                            .getUsageTypeAbbreviation()){
                                        case "IO":
                                            teamMembersInOfficeList.add(teamMembersList.get(i));
                                            break;
                                        case "WOO":
                                            teamMembersRemoteList.add(teamMembersList.get(i));
                                            break;
                                        case "SL":
                                            teamMembersHolidayList.add(teamMembersList.get(i));
                                            break;
                                        default:
                                            teamMembersInOfficeList.add(teamMembersList.get(i));

                                    }

                                }
                            }

                            if (teamMembersList!=null && teamMembersList.size()>0){
                                setValueToAdapter(teamMembersList);
                            }
                        }else if(response.code()==401){
                            //Handle if token got expired
                            binding.locateProgressBar.setVisibility(View.INVISIBLE);
                            Utils.tokenExpiryAlert(getActivity(),"");

                        } else {
                            binding.locateProgressBar.setVisibility(View.INVISIBLE);
                            Log.d("Search", "onResponse: else");
                            Utils.showCustomAlertDialog(getActivity(),"Api Issue Code: "+response.code());
                        }
                    }else {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                        Utils.showCustomAlertDialog(getActivity(),"No Response");
                    }

                }
                @Override
                public void onFailure(Call<ArrayList<DAOTeamMember>> call, Throwable t) {
//                    Toast.makeText(getActivity(), "on fail", Toast.LENGTH_SHORT).show();
                    Utils.showCustomAlertDialog(getActivity(),"Response Failure: "+t.getMessage());
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }

    private void setValueToAdapter(ArrayList<DAOTeamMember> teamMembersList) {
        if (teamMembersRemoteList.size()>0){
            binding.tvWorkRemote.setVisibility(View.VISIBLE);
        }else
            binding.tvWorkRemote.setVisibility(View.GONE);
        if (teamMembersHolidayList.size()>0){
            binding.tvHoliday.setVisibility(View.VISIBLE);
        }else
            binding.tvHoliday.setVisibility(View.GONE);
        teamsContactsAdapter = new TeamsContactsAdapter(getActivity(),teamMembersList,this);
        binding.recyclerView.setAdapter(teamsContactsAdapter);

        teamsContactsRemoteAdapter = new TeamsContactsAdapter(getActivity(),teamMembersRemoteList, this);
        binding.recyclerViewRemote.setAdapter(teamsContactsRemoteAdapter);

        teamsContactsHolidaysAdapter = new TeamsContactsAdapter(getActivity(),teamMembersHolidayList, this);
        binding.recyclerViewHoliday.setAdapter(teamsContactsHolidaysAdapter);

    }


    @Override
    public void clickEvent(DAOTeamMember daoTeamMember) {
        selID = daoTeamMember.getUserId();
        Intent intent = new Intent(getActivity(),ShowProfileActivity.class);
        intent.putExtra(AppConstants.USER_CURRENT_STATUS,daoTeamMember);
        intent.putExtra("DATE",currendate);
        startActivity(intent);

        /*callSearchRecyclerData(fName + " " + lName);
        callTeamMemberStatus();*/
    }

    @Override
    public void onProfileClick(DAOTeamMember daoTeamMember) {
        if (daoTeamMember!=null){
            Intent intent = new Intent(getActivity(),ShowProfileActivity.class);
            daoTeamMember.setDayGroups(null);
            intent.putExtra(AppConstants.USER_CURRENT_STATUS,daoTeamMember);
            intent.putExtra("DATE",currendate);
            startActivity(intent);
        }else {
            binding.expandRecyclerView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.tvHoliday.setVisibility(View.GONE);
            binding.tvWorkRemote.setVisibility(View.GONE);
            binding.recyclerViewRemote.setVisibility(View.GONE);
            binding.recyclerViewHoliday.setVisibility(View.GONE);
            binding.tvTotalAvail.setVisibility(View.VISIBLE);
            binding.tvExapnd.setVisibility(View.GONE);
            setDataToExpandAdapter(teamMembersList);
        }

    }


    public class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final static int vertOverlap = -20;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);


            outRect.set(0, 0, vertOverlap, 0);


        }
    }

    private void setDataToExpandAdapter(ArrayList<DAOTeamMember> teamMembersList) {
        teamsAdapter = new TeamsAdapter(getActivity(),teamMembersList,this);
        binding.expandRecyclerView.setAdapter(teamsAdapter);
    }


    private void uiInit(View root) {

        contactLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false);
        contactRemoteLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false);
        contactHolidayLinearLayout = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(contactLinearLayout);
        binding.recyclerViewRemote.setLayoutManager(contactRemoteLinearLayout);
        binding.recyclerViewHoliday.setLayoutManager(contactHolidayLinearLayout);
        binding.recyclerView.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewRemote.addItemDecoration(new OverlapDecoration());
        binding.recyclerViewHoliday.addItemDecoration(new OverlapDecoration());
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerViewRemote.setHasFixedSize(true);
        binding.recyclerViewHoliday.setHasFixedSize(true);

        teamsContactsAdapter = new TeamsContactsAdapter(getActivity(),teamMembersList, this);
        binding.recyclerView.setAdapter(teamsContactsAdapter);

        teamsContactsRemoteAdapter = new TeamsContactsAdapter(getActivity(),teamMembersRemoteList, this);
        binding.recyclerViewRemote.setAdapter(teamsContactsRemoteAdapter);

        teamsContactsHolidaysAdapter = new TeamsContactsAdapter(getActivity(),teamMembersHolidayList, this);
        binding.recyclerViewHoliday.setAdapter(teamsContactsHolidaysAdapter);



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

        for(DAOTeamMember staff: copyTeamMembersList){
            if (staff.getFirstName().toLowerCase().contains(text) ||
                    staff.getLastName().toLowerCase().contains(text)){
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

}