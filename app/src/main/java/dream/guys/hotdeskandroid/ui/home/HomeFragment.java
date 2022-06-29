package dream.guys.hotdeskandroid.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;

import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeBookingListAdapter.OnCheckInClickable {

    FragmentHomeBinding binding;
    TextView text;
    ImageView userProfile;
    Toolbar toolbar;

    @BindView(R.id.homeUserName)
    TextView homeUserName;

    RecyclerView rvHomeBooking;
    HomeBookingListAdapter homeBookingListAdapter;
    LinearLayoutManager linearLayoutManager;

    List<BookingListResponse> bookingListResponseList;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        userProfile=root.findViewById(R.id.user_profile_pic);
        rvHomeBooking=root.findViewById(R.id.rvHomeBooking);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetEditYourBooking(getContext(),getActivity(),"message","dad");
            }
        });

        binding.homeUserName.setText(SessionHandler.getInstance().get(getContext(),AppConstants.USERNAME));
/*
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePicker(getContext(),getActivity(),"Start","30th date");
            }
        });
*/
        //doTokenExpiryHere();

        loadHomeList();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //loadHomeList();
        this.view=view;
    }
    private void doTokenExpiryHere() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
                Utils.finishAllActivity(getContext());
            }
        },5000);



    }

    private void loadHomeList(){
        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-06-25",true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {

                    if(response.code()==200){

                        BookingListResponse bookingListResponse  =response.body();
                        createRecyclerList(bookingListResponse);

                    }else if(response.code()==401){
                        //Handle if token got expired
                        redirectToBioMetricAccess();

                    }
                }
                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }


    private void createRecyclerList(BookingListResponse body) {
        BookingListResponse bookingListResponses = body;
        ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList = new ArrayList<>();
//        ArrayList<BookingListResponse> recyclerModelArrayList = new ArrayList<>();
        for (int i=0; i<bookingListResponses.getDayGroups().size(); i++){
            boolean dateCheck =true;
            Date date = bookingListResponses.getDayGroups().get(i).getDate();
            ArrayList<BookingListResponse.DayGroup.CalendarEntry> calendarEntries = null;
            ArrayList<BookingListResponse.DayGroup.MeetingBooking> meetingEntries = null;
            ArrayList<BookingListResponse.DayGroup.CarParkBooking> carParkEntries = null;

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
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(1);
                        momdel.setDate(date);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(1);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }
            if (meetingEntries!=null){
                for (int j=0; j < meetingEntries.size(); j++){
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(2);
                        momdel.setDate(date);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(2);
                        momdel.setMeetingBookingsModel(meetingEntries.get(j));
                    }

                    recyclerModelArrayList.add(momdel);
                }
            }
            if (carParkEntries!=null){
                for (int j=0; j < carParkEntries.size(); j++){
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(3);
                        momdel.setDate(date);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(3);
                        momdel.setCarParkBookingsModel(carParkEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }


        }

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvHomeBooking.setLayoutManager(linearLayoutManager);
        rvHomeBooking.setHasFixedSize(true);

       // homeBookingListAdapter=new HomeBookingListAdapter(getContext(), getActivity(), recyclerModelArrayList);
        homeBookingListAdapter=new HomeBookingListAdapter(getContext(),this,recyclerModelArrayList);
        rvHomeBooking.setAdapter(homeBookingListAdapter);

    }


    private void redirectToBioMetricAccess() {
        SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
        Utils.finishAllActivity(getContext());
    }

    @Override
    public void onCheckInClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, boolean s) {

        System.out.println("BookingNameDest"+calendarEntriesModel.getUsageTypeName());

        NavController navController= Navigation.findNavController(view);
        Bundle bundle=new Bundle();
        bundle.putString("BOOK_NAME",calendarEntriesModel.getUsageTypeName());
        bundle.putString("BOOK_ADDRESS","address");
        //String checkInTime=calendarEntriesModel.getFromUTC();
        //String checkOutTime=calendarEntriesModel.getFromUTC();
        bundle.putString("CHECK_IN_TIME",Utils.splitTime(calendarEntriesModel.getFromUTC()));
        bundle.putString("CHECK_OUT_TIME",Utils.splitTime(calendarEntriesModel.getToUTC()));
        navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);


    }

    @Override
    public void onCheckInClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, boolean s) {
        System.out.println("BookingNameMeeting"+meetingEntriesModel.getMeetingRoomName());
        NavController navController= Navigation.findNavController(view);
        Bundle bundle=new Bundle();
        bundle.putString("BOOK_NAME",meetingEntriesModel.getMeetingRoomName());
        bundle.putString("BOOK_ADDRESS","Address");
        bundle.putString("CHECK_IN_TIME",Utils.splitTime(meetingEntriesModel.getFromUtc()));
        bundle.putString("CHECK_OUT_TIME",Utils.splitTime(meetingEntriesModel.getToUtc()));
        navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);
    }

    @Override
    public void onCheckInClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, boolean s) {
        System.out.println("BookingNameCar"+carParkingEntriesModel.getParkingSlotCode());
        NavController navController= Navigation.findNavController(view);
        Bundle bundle=new Bundle();
        bundle.putString("BOOK_NAME",carParkingEntriesModel.getParkingSlotCode());
        bundle.putString("BOOK_ADDRESS","Addresss");
        bundle.putString("CHECK_IN_TIME",Utils.splitTime(carParkingEntriesModel.getFromUtc()));
        bundle.putString("CHECK_OUT_TIME",Utils.splitTime(carParkingEntriesModel.getToUtc()));
        navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);
    }


    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

    }
*/
}