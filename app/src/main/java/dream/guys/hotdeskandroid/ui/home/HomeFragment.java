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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;

import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
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

public class HomeFragment extends Fragment implements HomeBookingListAdapter.OnCheckInClickable, DeskListRecyclerAdapter.OnSelectSelected {

    FragmentHomeBinding binding;
    TextView text;
    ImageView userProfile;
    Toolbar toolbar;

    //Header
    @BindView(R.id.homeUserName)
    TextView homeUserName;

    //HomeBooking
    RecyclerView rvHomeBooking,rvDeskRecycler;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;

    //EditBooking
    TextView startTime,endTime,repeat,date,deskRoomName;
    String repeatValue="None";

    View view;
    Dialog dialog;
    int teamId=0,teamMembershipId=0,selectedDeskId=0;
    ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse;
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

    public void editBookingCall(BookingListResponse.DayGroup data) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            BookingsRequest bookingsRequest = new BookingsRequest();
            bookingsRequest.setTeamId(teamId);
            bookingsRequest.setTeamMembershipId(teamMembershipId);
            ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
            ArrayList<Integer> list1 =new ArrayList<>();
            list1.add(0);
            BookingsRequest.ChangeSets changeSets = new BookingsRequest.ChangeSets();
            changeSets.setId(data.getCalendarEntriesModel().getId());
            changeSets.setDate(""+Utils.getISO8601format(data.getDate()));

            BookingsRequest.ChangeSets.Changes changes= new BookingsRequest.ChangeSets.Changes();
            changes.setBookingStatus("OUT");
            changeSets.setChanges(changes);
            list.add(changeSets);

            bookingsRequest.setChangeSets(list);
            bookingsRequest.setDeletedIds(list1);
            // TODO: 06-07-2022
            System.out.println("booking req check"+bookingsRequest.getChangeSets());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.bookingBookings(bookingsRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Toast.makeText(getActivity(), "Success Bala", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail Bala"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    public void changeCheckOut(BookingListResponse.DayGroup data, int pos) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            BookingStatusRequest bookingsRequest = new BookingStatusRequest();
            bookingsRequest.setCalendarEntryId(data.getCalendarEntriesModel().getId());
            bookingsRequest.setBookingStatus("OUT");


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.bookingStatus(bookingsRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    BookingListResponse.DayGroup.CalendarEntry.Booking.Status calendarEntry = recyclerModelArrayList.get(pos).getCalendarEntriesModel().getBooking().getStatus();
                    calendarEntry.setId(1);
                    homeBookingListAdapter.notifyItemChanged(pos);
//                    openCheckoutDialog();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
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
                        teamId = bookingListResponse.getTeamId();
                        teamMembershipId = bookingListResponse.getTeamMembershipId();
                        createRecyclerList(bookingListResponse);
                        loadDeskList();

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

    private void loadDeskList() {
        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(teamId,teamMembershipId,Utils.getCurrentDate(),Utils.getCurrentDate());
            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    System.out.println(response.body().getTeamDeskAvailabilities());
                    bookingForEditResponse = response.body().getTeamDeskAvailabilities();
//                    createRecyclerDeskList(response.body().getTeamDeskAvailabilities());
                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }


    private void createRecyclerDeskList(List<BookingForEditResponse.TeamDeskAvailabilities> body) {
        List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse = body;


//        ProgressDialog.dismisProgressBar(getContext(),dialog);

    }
    private void createRecyclerList(BookingListResponse body) {
        BookingListResponse bookingListResponses = body;
        recyclerModelArrayList = new ArrayList<>();
//        ArrayList<BookingListResponse> recyclerModelArrayList = new ArrayList<>();
        for (int i=0; i<bookingListResponses.getDayGroups().size(); i++){
            boolean dateCheck =true;
            System.out.println("bala time format"+bookingListResponses.getDayGroups().get(i).getDate());
            Date date = bookingListResponses.getDayGroups().get(i).getDate();
            System.out.println("bala time format"+date);
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
                        momdel.setDate(date);
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
                        momdel.setDate(date);
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
                        momdel.setDate(date);
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
        homeBookingListAdapter=new HomeBookingListAdapter(getContext(),this,recyclerModelArrayList,getActivity(),this);
        rvHomeBooking.setAdapter(homeBookingListAdapter);

        ProgressDialog.dismisProgressBar(getContext(),dialog);

    }


    private void redirectToBioMetricAccess() {
        SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
        Utils.finishAllActivity(getContext());
    }

    @Override
    public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click, Date date) {

        if(click.equals(AppConstants.CHECKIN) || click.equals(AppConstants.REMOTE)){
            //Checkin
            System.out.println("BookingNameDest"+calendarEntriesModel.getUsageTypeName());
            NavController navController= Navigation.findNavController(view);
            Bundle bundle=new Bundle();
            if(click.equals(AppConstants.CHECKIN)){
                bundle.putString("ACTION",AppConstants.CHECKIN);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getBooking().getDeskCode());
                bundle.putString("BOOK_ADDRESS","address");
                bundle.putString("CHECK_IN_TIME",Utils.splitTime(calendarEntriesModel.getFrom()));
                bundle.putString("CHECK_OUT_TIME",Utils.splitTime(calendarEntriesModel.getMyto()));

                bundle.putInt("TEAM_ID",teamId);
                bundle.putInt("TEAM_MEMBERSHIP_ID",teamMembershipId);
                bundle.putString("DATE",""+Utils.getISO8601format(date));
                bundle.putInt("ID",calendarEntriesModel.getId());

            } else if(click.equals(AppConstants.REMOTE)){
                bundle.putString("ACTION",AppConstants.REMOTE);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getUsageTypeName());

            }
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);
        } else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("BookingEditClicked");
            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(calendarEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(calendarEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);
            editDeskBookingDetails.setDeskStatus(calendarEntriesModel.getBooking().getStatus().getId());
            editBookingUsingBottomSheet(editDeskBookingDetails,1);

        }
    }

    @Override
    public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click, Date date) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameMeeting" + meetingEntriesModel.getMeetingRoomName());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", meetingEntriesModel.getMeetingRoomName());
            bundle.putString("BOOK_ADDRESS", "Address");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(meetingEntriesModel.getFrom()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(meetingEntriesModel.getMyto()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("MeetingEditClicked");

            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);

            editBookingUsingBottomSheet(editDeskBookingDetails,2);
        }
    }

    @Override
    public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click, Date date) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameCar" + carParkingEntriesModel.getParkingSlotCode());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", carParkingEntriesModel.getParkingSlotCode());
            bundle.putString("BOOK_ADDRESS", "Addresss");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(carParkingEntriesModel.getFrom()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(carParkingEntriesModel.getMyto()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("CarParkingEditClicked");
            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(carParkingEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(carParkingEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);
            editBookingUsingBottomSheet(editDeskBookingDetails,3);
        }
    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails,int dskRoomParkStatus) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));


        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        repeat=bottomSheetDialog.findViewById(R.id.repeat);
        deskRoomName=bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        date=bottomSheetDialog.findViewById(R.id.date);
        TextView back=bottomSheetDialog.findViewById(R.id.editBookingBack);
        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);
        TextView continueEditBook=bottomSheetDialog.findViewById(R.id.editBookingContinue);
        EditText commentRegistration=bottomSheetDialog.findViewById(R.id.ed_registration);
        RelativeLayout repeatBlock=bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout teamsBlock=bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        LinearLayout statusCheckLayout=bottomSheetDialog.findViewById(R.id.status_check_layout);

        ChipGroup chipGroup = bottomSheetDialog.findViewById(R.id.list_item);

        if (editDeskBookingDetails.getDeskStatus() == 1){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            chipGroup.setVisibility(View.VISIBLE);
        } else {
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            chipGroup.setVisibility(View.GONE);
        }

        if (dskRoomParkStatus == 1){
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
        }

        startTime.setText(editDeskBookingDetails.getEditStartTTime());
        endTime.setText(editDeskBookingDetails.getEditEndTime());
        date.setText(""+Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));


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
                if (editDeskBookingDetails.getDeskStatus() != 1)
                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1)
                    Utils.popUpTimePicker(getActivity(),endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeskBottomSheetDialog();
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

    private void callDeskBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        TextView bsRepeatBack;
        rvDeskRecycler= bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),getActivity(),bookingForEditResponse,this);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);


        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
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