package dream.guys.hotdeskandroid.ui.teams;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.TeamsAdapter;
import dream.guys.hotdeskandroid.adapter.TeamsContactsAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentTeamsBinding;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.ui.home.ViewTeamsActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment {

    FragmentTeamsBinding binding;
    int day, month, year;
    HorizontalCalendar horizontalCalendar;
    String currendate = "",selectedDate="";
    HorizontalCalendarView calendarView;
    ArrayList<DAOTeamMember> teamMembersList = new ArrayList<>();
    TeamsContactsAdapter teamsContactsAdapter;
    TeamsAdapter teamsAdapter;

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

                setDataToExpandAdapter();

            }
        });

        getTeamMembers();

        return root;
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

                    if(response.code()==200){

                        teamMembersList = response.body();

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new OverlapDecoration());
        binding.recyclerView.setHasFixedSize(true);
        teamsContactsAdapter = new TeamsContactsAdapter(getActivity(),teamMembersList);
        binding.recyclerView.setAdapter(teamsContactsAdapter);

    }

    public class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final static int vertOverlap = -40;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);


            outRect.set(0, 0, vertOverlap, 0);


        }
    }

    private void setDataToExpandAdapter() {
        teamsAdapter = new TeamsAdapter(getActivity(),teamMembersList);
        binding.expandRecyclerView.setAdapter(teamsAdapter);
    }


    private void uiInit(View root) {
        calendarView = binding.calendarView;
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
                .configure().formatBottomText("EEE").formatMiddleText("dd").formatTopText("MMM yyyy")
                .showBottomText(true)
                .textSize(10.00f, 10.00f, 10.00f)
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