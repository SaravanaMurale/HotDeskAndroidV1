package dream.guys.hotdeskandroid.ui.home;

import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;

import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
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

public class HomeFragment extends Fragment implements HomeBookingListAdapter.OnCheckInClickable {

    FragmentHomeBinding binding;
    TextView text;
    ImageView userProfile;
    Toolbar toolbar;

    //Header
    @BindView(R.id.homeUserName)
    TextView homeUserName;

    //HomeBooking
    RecyclerView rvHomeBooking;
    HomeBookingListAdapter homeBookingListAdapter;
    LinearLayoutManager linearLayoutManager;
    List<BookingListResponse> bookingListResponseList;

    //EditBooking
    TextView startTime,endTime,repeat;
    String repeatValue="None";

    View view;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        dialog = new Dialog(getContext());

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

            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(),true);
            //Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-07-04",true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {

                    if(response.code()==200){


                        BookingListResponse bookingListResponse  =response.body();
                        createRecyclerList(bookingListResponse);


                    }else if(response.code()==401){
                        //Handle if token got expired
                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        redirectToBioMetricAccess();

                    }
                }
                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
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
        homeBookingListAdapter=new HomeBookingListAdapter(getContext(),this,recyclerModelArrayList,getActivity());
        rvHomeBooking.setAdapter(homeBookingListAdapter);

        ProgressDialog.dismisProgressBar(getContext(),dialog);

    }


    private void redirectToBioMetricAccess() {
        SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
        Utils.finishAllActivity(getContext());
    }

    @Override
    public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click) {

        if(click.equals(AppConstants.CHECKIN) || click.equals(AppConstants.REMOTE)){
            //Checkin
            System.out.println("BookingNameDest"+calendarEntriesModel.getUsageTypeName());
            NavController navController= Navigation.findNavController(view);
            Bundle bundle=new Bundle();
            if(click.equals(AppConstants.CHECKIN)){
                bundle.putString("ACTION",AppConstants.CHECKIN);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getBooking().getDeskCode());
                bundle.putString("BOOK_ADDRESS","address");
                bundle.putString("CHECK_IN_TIME",Utils.splitTime(calendarEntriesModel.getFromUTC()));
                bundle.putString("CHECK_OUT_TIME",Utils.splitTime(calendarEntriesModel.getToUTC()));
            } else if(click.equals(AppConstants.REMOTE)){
                bundle.putString("ACTION",AppConstants.REMOTE);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getUsageTypeName());

            }
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);
        } else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("BookingEditClicked");

            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(calendarEntriesModel.getFromUTC()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(calendarEntriesModel.getToUTC()));
            //editDeskBookingDetails.setDate(calendarEntriesModel.get);


            editBookingUsingBottomSheet(editDeskBookingDetails);

        }
    }

    @Override
    public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameMeeting" + meetingEntriesModel.getMeetingRoomName());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", meetingEntriesModel.getMeetingRoomName());
            bundle.putString("BOOK_ADDRESS", "Address");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(meetingEntriesModel.getFromUtc()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(meetingEntriesModel.getToUtc()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("MeetingEditClicked");

            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingEntriesModel.getFromUtc()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingEntriesModel.getToUtc()));

            editBookingUsingBottomSheet(editDeskBookingDetails);
        }
    }

    @Override
    public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameCar" + carParkingEntriesModel.getParkingSlotCode());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", carParkingEntriesModel.getParkingSlotCode());
            bundle.putString("BOOK_ADDRESS", "Addresss");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(carParkingEntriesModel.getFromUtc()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(carParkingEntriesModel.getToUtc()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("CarParkingEditClicked");
            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(carParkingEntriesModel.getFromUtc()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(carParkingEntriesModel.getToUtc()));
            editBookingUsingBottomSheet(editDeskBookingDetails);
        }
    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));


        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        repeat=bottomSheetDialog.findViewById(R.id.repeat);
        TextView back=bottomSheetDialog.findViewById(R.id.editBookingBack);
        TextView continueEditBook=bottomSheetDialog.findViewById(R.id.editBookingContinue);
        RelativeLayout repeatBlock=bottomSheetDialog.findViewById(R.id.bsRepeatBlock);

        startTime.setText(editDeskBookingDetails.getEditStartTTime());
        endTime.setText(editDeskBookingDetails.getEditEndTime());


        ChipGroup chipGroup = bottomSheetDialog.findViewById(R.id.list_item);
        for (int i=0; i<5; i++){
            Chip chip = new Chip(getContext());
            chip.setId(i);
            chip.setText("ABC "+i);
            chip.setChipBackgroundColorResource(R.color.figmaGrey);
            chip.setCloseIconVisible(false);
            chip.setTextColor(getContext().getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
            chipGroup.addView(chip);
        }

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.popUpTimePicker(getActivity(),startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.popUpTimePicker(getActivity(),endTime);
            }
        });

        repeatBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callRepeatBottomSheetDialog();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();

    }

    private void callRepeatBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_repeat_booking,
                new RelativeLayout(getContext()))));

        RelativeLayout repeatNoneBlock,repeatDailyBlock,repeatWeeklyBlock,repeatMonthlyBlock,repeatYearlyBlock;
        ImageView noneTickIv,dailyTickIv,weeklyTickIv,monthlyTickIv,yearlyTickIv;
        TextView bsRepeatBack;

        repeatNoneBlock=bottomSheetDialog.findViewById(R.id.repeatNoneBlock);
        repeatDailyBlock=bottomSheetDialog.findViewById(R.id.repeatDailyBlock);
        repeatWeeklyBlock=bottomSheetDialog.findViewById(R.id.repeatWeeklyBlock);
        repeatMonthlyBlock=bottomSheetDialog.findViewById(R.id.repeatMonthlyBlock);
        repeatYearlyBlock=bottomSheetDialog.findViewById(R.id.repeatYearlyBlock);

        noneTickIv=bottomSheetDialog.findViewById(R.id.noneTickIv);
        dailyTickIv=bottomSheetDialog.findViewById(R.id.dailyTickIv);
        weeklyTickIv=bottomSheetDialog.findViewById(R.id.weeklyTickIv);
        monthlyTickIv=bottomSheetDialog.findViewById(R.id.monthlyTickIv);
        yearlyTickIv=bottomSheetDialog.findViewById(R.id.yearlyTickIv);

        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsRepeatBack);

        repeatNoneBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat.setText("None");
                noneTickIv.setVisibility(View.VISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatDailyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                repeat.setText("Daily");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.VISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatWeeklyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Weekly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.VISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatMonthlyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Monthly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.VISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatYearlyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Yearly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.VISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


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