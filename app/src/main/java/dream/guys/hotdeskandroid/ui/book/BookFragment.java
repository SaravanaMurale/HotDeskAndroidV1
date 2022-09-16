package dream.guys.hotdeskandroid.ui.book;

import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;
import static dream.guys.hotdeskandroid.utils.Utils.toastMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapterBooking;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.ParkingSpotListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.adapter.RoomListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CarParkListToEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkLocationsModel;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.CalendarView;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment implements
        MeetingListToEditAdapter.OnMeetingEditClickable,
        BookingListToEditAdapter.OnEditClickable,
        DeskListRecyclerAdapter.OnSelectSelected,
        CarListToEditAdapterBooking.CarEditClickableBooking,
        ParkingSpotListRecyclerAdapter.OnSelectSelected,
        ParticipantNameShowAdapter.OnParticipantSelectable,
        RoomListRecyclerAdapter.OnSelectSelected,
        ShowCountryAdapter.OnSelectListener{
    FragmentBookBinding binding;
    @BindView(R.id.desk_layout)
    LinearLayout deskLayout;
    @BindView(R.id.room_layout)
    LinearLayout roomLayout;
    @BindView(R.id.parking_layout)
    LinearLayout parkingLayout;
    @BindView(R.id.more_layout)
    LinearLayout moreLayout;

    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @BindView(R.id.iv_desk)
    ImageView ivDesk;
    @BindView(R.id.iv_room)
    ImageView ivRoom;
    @BindView(R.id.iv_parking)
    ImageView ivParking;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    @BindView(R.id.tv_desk)
    TextView tvDesk;
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_parking)
    TextView tvParking;
    @BindView(R.id.tv_more)
    TextView tvMore;

    List<DeskRoomCountResponse> events = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    Dialog dialog;

    BottomSheetDialog bookEditBottomSheet;
    BottomSheetDialog roomBottomSheet;
    int selectedicon = 0;
    String calSelectedDate="";
    String calSelectedMont="";

    String type="none";

    TextView startTime,endTime,repeat,date,deskRoomName,locationAddress;
    String repeatValue="None";

    int teamId=0,teamMembershipId=0,selectedDeskId=0;

    RecyclerView rvHomeBooking,rvDeskRecycler;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    ParkingSpotListRecyclerAdapter parkingSpotListRecyclerAdapter;
    RoomListRecyclerAdapter roomListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nestedScrollView;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk=new ArrayList<>();
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList=new ArrayList<>();
    List<ParkingSpotModel> parkingSpotModelList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList=new ArrayList<>();

    BookingForEditResponse bookingForEditResponse;
    View view;

    //room bookings
    ChipGroup participantChipGroup;
    String meetingRoomDescription = null;
    String carParkDescription = null;
    String deskDescriotion = null;

    List<ParticipantDetsilResponse> chipList = new ArrayList<>();
    List<AmenitiesResponse> amenitiesList = new ArrayList<>();
    ParticipantDetsilResponse participantDetsilResponse;
    Uri appLinkData;

    Context context;
    Activity activityContext;

    //BottomSheetData
    TextView country, state, street, floor, back, bsApply;
    RecyclerView rvCountry, rvState, rvStreet, rvFloor;
    ShowCountryAdapter showCountryAdapter;
    FloorAdapter floorAdapter;
//    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock, statBlock, streetBlock, floorBlock;

    TextView bsLocationSearch;

    RecyclerView rvMyTeam;
    LocateMyTeamAdapter locateMyTeamAdapter;

    List<Point> pointList = new ArrayList<>();

    int stateId = 0;
    int canvasss = 0;

    int endTimeSelectedStats = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog= new Dialog(getActivity());
        tabToggleViewClicked(selectedicon);
        if (endTimeSelectedStats == 0) {
            binding.locateEndTime.setText("23:59");
        }

        binding.locateStartTime.setText(getCurrentTime());
        binding.locateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateStartTime, "Start", binding.locateCalendearView.getText().toString(), 1);
            }
        });

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateEndTime, "End", binding.locateCalendearView.getText().toString(), 2);


            }
        });

        binding.searchGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocateCountryList();
            }
        });

        binding.calendarView.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date, int pos) {
                Toast.makeText(getActivity(), ""+Utils.getISO8601format(date), Toast.LENGTH_SHORT).show();
                if (!(Utils.compareTwoDate(date,Utils.getCurrentDate()) == 1)){
                    if (selectedicon==0){
                        getAddEditDesk("3",Utils.getISO8601format(date));
                        calSelectedDate=Utils.getISO8601format(date);
                    } else if (selectedicon==1) {
                        getMeetingBookingListToEdit("" + Utils.getYearMonthDateFormat(date)+"T00:00:00.000Z", "new");
                        calSelectedDate=Utils.getISO8601format(date);
                    } else if(selectedicon==2){
                        getCarParListToEdit(""+Utils.getISO8601format(date),""+Utils.getISO8601format(date));
                        calSelectedDate=Utils.getISO8601format(date);
                    }else {

                    }
                }

            }

            @Override
            public void onPrevClicked(String month) {
                Toast.makeText(getActivity(), ""+month, Toast.LENGTH_LONG).show();
                calSelectedMont=month;

                if (binding.searchGlobal.getText() != null){
                    getDeskCountLocation(month,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                } else {
                    getDeskCount(month);

                }
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
                selectedicon = 3;
                tabToggleViewClicked(3);
            }
        });

        return root;
    }

    private void deepLinking() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getActivity().getIntent();
        String appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();

        if(appLinkData != null && !AppConstants.FIRSTREFERAL) {
            //NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
//            navController.navigate(R.id.navigation_book);
            String data1= appLinkData.getQueryParameter("typekey"); // you will get the value "value1" from application 1

            AppConstants.FIRSTREFERAL = true;

            if (data1.equalsIgnoreCase("desk")){
                boolean checkIsRequest = false;
                selectedicon=0;

                String deskCode = appLinkData.getQueryParameter("deskCode");
                String deskId = appLinkData.getQueryParameter("deskId");
                String requestedTeamId = appLinkData.getQueryParameter("teamId");

                EditBookingDetails editBookingDetails= new EditBookingDetails();
                lop :
                for (int i=0; i<bookingDeskList.size();i++){
                    if (Integer.parseInt(deskId)
                            == bookingDeskList.get(i).getTeamDeskId()){
                        Toast.makeText(getActivity(), ""+bookingForEditResponse.getBookings().size(), Toast.LENGTH_SHORT).show();
                        if (bookingForEditResponse.getBookings().size() > 0){
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto()));
                            editBookingDetails.setEditEndTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto(),2));
                        }else {
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                        }
                        break lop;

                    } else {

                        System.out.println("date con"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+"Z");
                        editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                        editBookingDetails.setEditEndTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z",2));

                    }
                }
/*
                if (bookingForEditResponseDesk.size()==0){
                    editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
                    editBookingDetails.setEditEndTime(Utils.addingHoursToCurrentDate(2));

                }*/

                calSelectedDate=Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
//                editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                editBookingDetails.setDate(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
                editBookingDetails.setCalId(0);
                editBookingDetails.setDeskCode(deskCode);
                editBookingDetails.setDesktId(Integer.parseInt(deskId));
                editBookingDetails.setDeskTeamId(Integer.parseInt(requestedTeamId));
                editBookingDetails.setDeskStatus(0);

                if (bookingDeskList!=null && bookingDeskList.size()>0){
                    loo :
                    for (int i=0;i<bookingDeskList.size();i++){
                        if (deskId.equalsIgnoreCase(""+bookingDeskList.get(i).getTeamDeskId())){
                            checkIsRequest=true;
                            break loo;
                        }
                    }
                }
                System.out.println("cajec vava"+bookingDeskList.size() +"  "+checkIsRequest);
                if (checkIsRequest)
                    editBookingUsingBottomSheet(editBookingDetails,1,0,"new_deep_link");
                else
                    editBookingUsingBottomSheet(editBookingDetails,1,0,"request");

            } else if (data1.equalsIgnoreCase("room")){
                selectedicon=1;
                tabToggleViewClicked(selectedicon);

                String roomId = appLinkData.getQueryParameter("meetingRoomId");
                String roomName = appLinkData.getQueryParameter("meetingRoomName");
                EditBookingDetails editBookingDetails= new EditBookingDetails();

                editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
                editBookingDetails.setEditEndTime(Utils.addingHoursToCurrentDate(2));

                calSelectedDate=Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate()));

                editBookingDetails.setDate(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
                editBookingDetails.setCalId(0);
                editBookingDetails.setMeetingRoomtId(Integer.parseInt(roomId));
                editBookingDetails.setRoomName(roomName);
                getMeetingBookingListToEdit("" + Utils.getCurrentDate()+"T00:00:00.000Z", "new_deep_link");
//                getRoomlist(editBookingDetails, "new_deep_link");

            } else {
                selectedicon=2;
                String parkingId = appLinkData.getQueryParameter("parkingId");
                String parkingName = appLinkData.getQueryParameter("parkingName");
                EditBookingDetails editBookingDetails= new EditBookingDetails();

                editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
                editBookingDetails.setEditEndTime(Utils.addingHoursToCurrentDate(2));

                calSelectedDate=Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
//
//                editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                editBookingDetails.setDate(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
                editBookingDetails.setCalId(0);
                editBookingDetails.setParkingSlotId(Integer.parseInt(parkingId));
                editBookingDetails.setParkingSlotCode(parkingName);

                getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editBookingDetails,"new_deep_link");

            }
//
//            List<String> params = appLinkData.getPathSegments();
//
//            AppConstants.REFERALID = params.get(params.size() - 1);
//            AppConstants.REFERALCODEE = params.get(params.size() - 2);
//
//            System.out.println("Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE);
//            Toast.makeText(this, "Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        this.context = getContext();
        this.activityContext=getActivity();
        getAddEditDesk("-1",Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate())));

    }

    @SuppressLint("ResourceAsColor")
    public void tabToggleViewClicked(int i) {
        resetLayout();
        switch (i){
            case 0:
                binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivDesk.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvDesk.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams deskParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                deskParams.weight = 1.0f;
                binding.deskLayout.setLayoutParams(deskParams);
                binding.calendarView.setVisibility(View.VISIBLE);
                if (calSelectedMont != "" && !calSelectedMont.isEmpty()){
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(calSelectedMont,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(calSelectedMont);
                }
                else{
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }

                break;
            case 1:
                binding.roomLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivRoom.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvRoom.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams roomParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                roomParams.weight = 1.0f;
                binding.roomLayout.setLayoutParams(roomParams);
                binding.calendarView.setVisibility(View.VISIBLE);
               /* if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());*/
//                Toast.makeText(context, ""+binding.searchGlobal.getText().toString(), Toast.LENGTH_SHORT).show();
                if (calSelectedMont != "" && !calSelectedMont.isEmpty()){
//                    Toast.makeText(context, "cje"+binding.searchGlobal.getText(), Toast.LENGTH_SHORT).show();
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty()
                    )
                        getDeskCountLocation(calSelectedMont,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(calSelectedMont);
                }
                else{
//                    Toast.makeText(context, "cje"+binding.searchGlobal.getText(), Toast.LENGTH_SHORT).show();
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }
                break;
            case 2:
                binding.parkingLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivParking.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvParking.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams parkingParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parkingParams.weight = 1.0f;
                binding.parkingLayout.setLayoutParams(parkingParams);
                binding.calendarView.setVisibility(View.VISIBLE);
                /*
                if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());
                    */
                if (calSelectedMont != "" && !calSelectedMont.isEmpty()){
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(calSelectedMont,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(calSelectedMont);
                }
                else{
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }
                break;
            case 3:
                binding.moreLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivMore.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvMore.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                moreParams.weight = 1.0f;
                binding.moreLayout.setLayoutParams(moreParams);
                binding.calendarView.setVisibility(View.GONE);
//                getDeskCount(Utils.getCurrentDate());
                break;
            default:
                break;

        }
    }

    private void getDeskCount(String month) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<DeskRoomCountResponse>> call = null;
            switch (selectedicon){
                case 0:
                    call = apiService.getDailyDeskCount(month, ""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                    break;
                case 1:
                    call = apiService.getDailyRoomCount(month, ""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                    break;
                case 2:
                    call = apiService.getDailyParkingCount(month, ""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                    break;
            }
            call.enqueue(new Callback<List<DeskRoomCountResponse>>() {
                @Override
                public void onResponse(Call<List<DeskRoomCountResponse>> call, Response<List<DeskRoomCountResponse>> response) {
                    dialog.dismiss();
                    if (response.code()==200){
                        events.clear();
                        events.addAll(response.body());
                        if (events.size()>0){
                            binding.calendarView.updateCalendar(events, -1);
                        }
                    } else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                    }
                }

                @Override
                public void onFailure(Call<List<DeskRoomCountResponse>> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void getDeskCountLocation(String month, String locationId,int drawStatus) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());
            System.out.println("check sub parent Id  :  "+locationId);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<DeskRoomCountResponse>> call = null;
            switch (selectedicon){
                case 0:
                    if (drawStatus == 2) {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = calSelectedDate + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = calSelectedDate + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                    }
                    else
                        call = apiService.getDailyDeskCountLocation(month, locationId,"","");
                    break;
                case 1:
                    if (drawStatus == 2){
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = calSelectedDate + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = calSelectedDate + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyRoomCountLocation(month, locationId,fromTime,toTime);
                    }
                    else
                        call = apiService.getDailyRoomCountLocation(month, locationId,"","");
                    break;
                case 2:
                    if (drawStatus == 2){
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = calSelectedDate + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = calSelectedDate + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyParkingCountLocation(month, locationId,fromTime,toTime);
                    }
                    else
                        call = apiService.getDailyParkingCountLocation(month, locationId,"","");
                    break;
            }
            call.enqueue(new Callback<List<DeskRoomCountResponse>>() {
                @Override
                public void onResponse(Call<List<DeskRoomCountResponse>> call, Response<List<DeskRoomCountResponse>> response) {
                    dialog.dismiss();
                    if (response.code()==200 && response.body().size()>0){
                        events.clear();
                        events.addAll(response.body());
                        if (events.size()>0){
                            binding.calendarView.updateCalendar(events, -1);
                        }
                    } else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                    }
                }

                @Override
                public void onFailure(Call<List<DeskRoomCountResponse>> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void resetLayout() {
        binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBgGrey));
        binding.roomLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));
        binding.parkingLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));
        binding.moreLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));

        binding.ivDesk.setImageTintList(getContext().getResources().getColorStateList(R.color.figmaBlack));
        binding.ivRoom.setImageTintList(getContext().getResources().getColorStateList(R.color.figmaBlack));
        binding.ivParking.setImageTintList(getContext().getResources().getColorStateList(R.color.figmaBlack));
        binding.ivMore.setImageTintList(getContext().getResources().getColorStateList(R.color.figmaBlack));

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

    //Meeting room booking edit list
    private void getMeetingBookingListToEdit(String date, String newEditStatus) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(context);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<MeetingListToEditResponse>> call=apiService.getMeetingListToEdit(date,date);
            call.enqueue(new Callback<List<MeetingListToEditResponse>>() {
                @Override
                public void onResponse(Call<List<MeetingListToEditResponse>> call, Response<List<MeetingListToEditResponse>> response) {

                    List<MeetingListToEditResponse> meetingListToEditResponseList  =response.body();
                    ProgressDialog.dismisProgressBar(context,dialog);
//                    dialog.dismiss();
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    if (newEditStatus.equalsIgnoreCase("new_deep_link")){
                        String roomId = appLinkData.getQueryParameter("meetingRoomId");
                        String roomName = appLinkData.getQueryParameter("meetingRoomName");

                        EditBookingDetails editBookingDetails= new EditBookingDetails();
                        if (meetingListToEditResponseList.size() > 0){
                            editBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo(),
                                    12000)));
                        } else {
                            editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                            System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
                                    +Utils.getCurrentTime()+"Z");
                            editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                                    +Utils.getCurrentTime()+":00Z",12000)));

                        }
                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setMeetingRoomtId(Integer.parseInt(roomId));
                        editBookingDetails.setRoomName(roomName);

                        getRoomlist(editBookingDetails,"new_deep_link");


                    } else {
                        callMeetingRoomEditListAdapterBottomSheet(meetingListToEditResponseList,"new");
                    }

                }

                @Override
                public void onFailure(Call<List<MeetingListToEditResponse>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(context,dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });
        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    //car par edit list
    private void getCarParListToEdit(String startDate, String endDate) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//            System.out.println("check dat"+startDate+" adn"+endDate);
            Call<List<CarParkListToEditResponse>> call = apiService.getCarParkListToEdit(startDate, endDate);
            call.enqueue(new Callback<List<CarParkListToEditResponse>>() {
                @Override
                public void onResponse(Call<List<CarParkListToEditResponse>> call, Response<List<CarParkListToEditResponse>> response) {

                    List<CarParkListToEditResponse> carParkingForEditResponse = response.body();
//                    List<CarParkListToEditResponse> carParkingForEditResponse = response.body();

                    toastMessage(getActivity(),"hvhvhv");
                    CallCarBookingEditList(carParkingForEditResponse, "5");

                     ProgressDialog.dismisProgressBar(getContext(),dialog);

//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<CarParkListToEditResponse>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    CallCarBookingEditList(null, "5");

//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getCarParkLocationsList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkLocationsModel>> call = apiService.getCarParkLocation();
            call.enqueue(new Callback<List<CarParkLocationsModel>>() {
                @Override
                public void onResponse(Call<List<CarParkLocationsModel>> call, Response<List<CarParkLocationsModel>> response) {

//                    CarParkLocationsModel carParkLocationsModel = response.body().get(0);
//                    getParkingSpotList(""+carParkLocationsModel.getId(), editBookingDetails);

                     ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<CarParkLocationsModel>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getParkingSpotList(String id, EditBookingDetails editBookingDetails,String newEdit) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ParkingSpotModel>> call = apiService.getParkingSpotModels(id);
            call.enqueue(new Callback<List<ParkingSpotModel>>() {
                @Override
                public void onResponse(Call<List<ParkingSpotModel>> call, Response<List<ParkingSpotModel>> response) {
                    parkingSpotModelList.clear();
                    for(int i=0;i<response.body().size();i++){
                        if(response.body().get(i).getAssignees().size() == 0
                                && response.body().get(i).isActive()){
                            parkingSpotModelList.add(response.body().get(i));
                        }
                    }

                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    boolean checkIsRequest=false;
                    if (parkingSpotModelList!=null && parkingSpotModelList.size()>0){
                        loo :
                        for (int i=0;i<parkingSpotModelList.size();i++){
                            if (editBookingDetails.getParkingSlotId()==parkingSpotModelList.get(i).getId()){
                                checkIsRequest=true;
                                break loo;
                            }
                        }
                    }

                    if (newEdit.equalsIgnoreCase("new"))
                        editBookingUsingBottomSheet(editBookingDetails,3,0,"new");
                    else if (newEdit.equalsIgnoreCase("new_deep_link")){
                        if (checkIsRequest)
                            editBookingUsingBottomSheet(editBookingDetails,3,0,"new_deep_link");
                        else
                            editBookingUsingBottomSheet(editBookingDetails,3,0,"request");
                    }else
                        editBookingUsingBottomSheet(editBookingDetails,3,0,"edit");

//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<ParkingSpotModel>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    editBookingUsingBottomSheet(editBookingDetails,3,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getRoomlist(EditBookingDetails editBookingDetails, String newEditStatus) {
        if (Utils.isNetworkAvailable(getActivity())) {
//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.userAllowedMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                    userAllowedMeetingResponseList=response.body();
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    boolean checkIsRequest=false;
                    if (userAllowedMeetingResponseList!=null && userAllowedMeetingResponseList.size()>0){
                        loo :
                        for (int i=0;i<userAllowedMeetingResponseList.size();i++){
                            if (editBookingDetails.getMeetingRoomtId()==userAllowedMeetingResponseList.get(i).getId()){
                                checkIsRequest=true;
                                break loo;
                            }
                        }
                    }
                    if (newEditStatus.equalsIgnoreCase("new_deep_link")){
                        if (checkIsRequest)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    editBookingDetails.getMeetingRoomtId(),
                                    0,"new_deep_link");

                        else
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    editBookingDetails.getMeetingRoomtId(),
                                    0,"request");

                    }else {
                        callAmenitiesListForMeetingRoom(editBookingDetails,
                                editBookingDetails.getEditStartTTime(),
                                editBookingDetails.getEditEndTime(),
                                editBookingDetails.getDate(),
                                userAllowedMeetingResponseList.get(0).getId(),
                                0,"new");
//                        editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
                    }

//                    editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void CallCarBookingEditList(List<CarParkListToEditResponse> carParkingForEditResponse, String code) {

        RecyclerView rvCarEditList;
        TextView editClose, editDate, bookingName,addNew;
        LinearLayoutManager linearLayoutManager;

        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bookEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvCarEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = bookEditBottomSheet.findViewById(R.id.editClose);
        editDate = bookEditBottomSheet.findViewById(R.id.editDate);
        bookingName = bookEditBottomSheet.findViewById(R.id.bookingName);
        addNew = bookEditBottomSheet.findViewById(R.id.editBookingContinue);

        //New...
        editDate.setText(Utils.dateWithDayString(calSelectedDate));

        bookingName.setText("Car Booking");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCarEditList.setLayoutManager(linearLayoutManager);
        rvCarEditList.setHasFixedSize(true);

        //CarListToEditAdapter carListToEditAdapter = new CarListToEditAdapter(getContext(), carParkingForEditResponse, this, code);
        //rvCarEditList.setAdapter(carListToEditAdapter);

        CarListToEditAdapterBooking carListToEditAdapter=new CarListToEditAdapterBooking(getContext(),carParkingForEditResponse,this,code);
        rvCarEditList.setAdapter(carListToEditAdapter);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCarParkLocationsList();
//                if (SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID)!=null)
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                if (carParkingForEditResponse.size() > 0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                            .getMyto()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                            .getMyto(),12000)));

                }else {
                    editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+"Z");
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+":00Z",12000)));

                }
                editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editBookingDetails,"new");

            }
        });
        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEditBottomSheet.dismiss();
            }
        });

        bookEditBottomSheet.show();
    }


    private void callMeetingRoomEditListAdapterBottomSheet(List<MeetingListToEditResponse> meetingListToEditResponseList, String newEditStatus) {
        RecyclerView rvMeeingEditList;
        TextView editClose,editDate,addNew;
        LinearLayoutManager linearLayoutManager;

        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bookEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvMeeingEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=bookEditBottomSheet.findViewById(R.id.editClose);
        editDate=bookEditBottomSheet.findViewById(R.id.editDate);
        addNew=bookEditBottomSheet.findViewById(R.id.editBookingContinue);
        editDate.setText(Utils.dateWithDayString(calSelectedDate));
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMeeingEditList.setLayoutManager(linearLayoutManager);
        rvMeeingEditList.setHasFixedSize(true);

        MeetingListToEditAdapter meetingListToEditAdapter=new MeetingListToEditAdapter(getContext(),meetingListToEditResponseList,this);
        rvMeeingEditList.setAdapter(meetingListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEditBottomSheet.dismiss();
            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                if (meetingListToEditResponseList.size() > 0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo(),
                            12000)));
                } else {
                    editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+"Z");
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+":00Z",12000)));

                }
                editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                getRoomlist(editBookingDetails,"new");

            }
        });

        //getMeetingListToEdit


        bookEditBottomSheet.show();
    }

    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {

        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponse.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingListToEditResponse.getTo()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(meetingListToEditResponse.getDate()));
        editDeskBookingDetails.setCalId(meetingListToEditResponse.getId());
        editDeskBookingDetails.setMeetingRoomtId(meetingListToEditResponse.getMeetingRoomId());
        callAmenitiesListForMeetingRoom(editDeskBookingDetails,
                editDeskBookingDetails.getEditStartTTime(),
                editDeskBookingDetails.getEditEndTime(),
                editDeskBookingDetails.getDate(),
                editDeskBookingDetails.getMeetingRoomtId(),
                0,"edit");
    }

    @Override
    public void onMeetingDeleteClick(MeetingListToEditResponse meetingListToEditResponse) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();

//        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"delete");

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("meetingRoomId",meetingListToEditResponse.getMeetingRoomId());
        jsonOuterObject.addProperty("handleRecurring",false);
//        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();
//        list1.add(editDeskBookingDetails.getCalId());
        jsonDeletedIdsArray.add(meetingListToEditResponse.getId());
        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,2,"delete");

    }

    private void callAmenitiesListForMeetingRoom(EditBookingDetails editDeskBookingDetails, String editStartTTime,
                                                 String editEndTime,
                                                 Date date,
                                                 int calId, int position, String newEditStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.userAllowedMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                    if (response.code()==200){

                        List<Integer> amenitiesIntList =new ArrayList<>();
                        List<String> amenitiesStringList =new ArrayList<>();
                        goo:
                        for (int i=0; i < response.body().size(); i++) {
                            if (response.body().get(i).getId() == calId && response.body().get(i).getAmenities()!=null) {
                                for (int j=0;j<response.body().get(i).getAmenities().size();j++){
                                    amenitiesIntList.add(response.body().get(i).getAmenities().get(j).getId());
                                }
                                break goo;
                            }
                        }

                        for (int i=0; i<amenitiesIntList.size();i++) {
                            for (int j=0;j<amenitiesList.size();j++) {
                                if (amenitiesIntList.get(i) == amenitiesList.get(j).getId()){
                                    amenitiesStringList.add(amenitiesList.get(j).getName());
                                }
                            }
                        }
//                        Utils.toastMessage(getActivity(),"welcom bala "+amenitiesStringList.size());
                        editDeskBookingDetails.setAmenities(amenitiesStringList);
//                        Log.d(TAG, "onResponse: amenitySize"+editDeskBookingDetails.getAmenities().size());

                        editBookingUsingBottomSheet(editDeskBookingDetails,2,position,newEditStatus);

                    } else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                    }
                }

                @Override
                public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
//                    Log.d(TAG, "onFailure: amen"+t.getMessage());
                    editBookingUsingBottomSheet(editDeskBookingDetails,2,position,newEditStatus);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }


    private void getAddEditDesk(String code,String date) {
        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(
                    SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID),
                    SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID),
                    date,
                    date);

            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    bookingDeskList.clear();
                    bookingForEditResponse = response.body();
                    bookingDeskList = response.body().getTeamDeskAvailabilities();
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    if (code.equalsIgnoreCase("3"))
                        callBottomSheetToEdit(bookingForEditResponse, code);
                    else
                        deepLinking();

                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    deepLinking();

                }
            });


        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    //    Desk Edit List
    private void callBottomSheetToEdit(BookingForEditResponse bookingForEditResponse, String code) {
        String sDate="";
        RecyclerView rvEditList;
        TextView editClose, editDate, bookingName, addNew;
        LinearLayoutManager linearLayoutManager;
        bookingForEditResponseDesk.clear();
        for (int i=0; i<bookingForEditResponse.getTeamDeskAvailabilities().size(); i++){
            if(!bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){
                bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
            }
        }
//        bookingForEditResponseDesk.addAll(bookingForEditResponse.getTeamDeskAvailabilities());
        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bookEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = bookEditBottomSheet.findViewById(R.id.editClose);
        editDate = bookEditBottomSheet.findViewById(R.id.editDate);
        bookingName = bookEditBottomSheet.findViewById(R.id.bookingName);
        addNew = bookEditBottomSheet.findViewById(R.id.editBookingContinue);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvEditList.setLayoutManager(linearLayoutManager);
        rvEditList.setHasFixedSize(true);

        BookingListToEditAdapter bookingListToEditAdapter = new BookingListToEditAdapter(getContext(), bookingForEditResponse.getBookings(), this, code, bookingForEditResponse.getTeamDeskAvailabilities());
        rvEditList.setAdapter(bookingListToEditAdapter);
        editDate.setText(Utils.dateWithDayString(calSelectedDate));

        if (code.equals("3")) {
            bookingName.setText("Desk Booking");
        }else if (code.equals("5")) {
            bookingName.setText("Room Booking");
        }else if (code.equals("7")) {
            bookingName.setText("Car Parking Booking");
        }


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                System.out.println("checks size"+bookingForEditResponseDesk.size());
                for (int i=0; i<bookingForEditResponseDesk.size();i++){
                    if (bookingForEditResponse.getUserPreferences().getTeamDeskId()
                            == bookingForEditResponseDesk.get(i).getTeamDeskId()){
                        if (bookingForEditResponse.getBookings().size() > 0){
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto()));

                            editBookingDetails.setEditEndTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto(),2));
                        }else {
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                        }

                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setDeskCode(bookingForEditResponseDesk.get(i).getDeskCode());
                        editBookingDetails.setDesktId(bookingForEditResponseDesk.get(i).getTeamDeskId());
                        editBookingDetails.setDeskStatus(0);
                    }else {
                        editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                        editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                        editBookingDetails.setDeskCode(bookingForEditResponseDesk.get(0).getDeskCode());
                        editBookingDetails.setDesktId(bookingForEditResponseDesk.get(0).getTeamDeskId());

                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setDeskStatus(0);
                    }
                }
                if (bookingForEditResponseDesk.size()==0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));

                    editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                    editBookingDetails.setCalId(0);
                    editBookingDetails.setDeskStatus(0);
                }

                editBookingUsingBottomSheet(editBookingDetails,1,0,"new");
            }
        });
        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEditBottomSheet.dismiss();
            }
        });

        bookEditBottomSheet.show();

    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails, int dskRoomParkStatus, int position,String newEditStatus) {

        roomBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        roomBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        startTime = roomBottomSheet.findViewById(R.id.start_time);
        endTime = roomBottomSheet.findViewById(R.id.end_time);
        repeat = roomBottomSheet.findViewById(R.id.repeat);
        deskRoomName=roomBottomSheet.findViewById(R.id.tv_desk_room_name);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        date=roomBottomSheet.findViewById(R.id.date);
        TextView title=roomBottomSheet.findViewById(R.id.title);
        TextView checkInDate=roomBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=roomBottomSheet.findViewById(R.id.showCheckInDate);
        TextView back=roomBottomSheet.findViewById(R.id.editBookingBack);
        TextView select=roomBottomSheet.findViewById(R.id.select_desk_room);
        TextView continueEditBook=roomBottomSheet.findViewById(R.id.editBookingContinue);
        TextView tvComments=roomBottomSheet.findViewById(R.id.tv_comments);
        EditText commentRegistration=roomBottomSheet.findViewById(R.id.ed_registration);
        RelativeLayout repeatBlock=roomBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=roomBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=roomBottomSheet.findViewById(R.id.rl_teams_layout);
        RelativeLayout dateBlock=roomBottomSheet.findViewById(R.id.bookingDateBlock);
        LinearLayout statusCheckLayout=roomBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=roomBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=roomBottomSheet.findViewById(R.id.capacity_layout);

        ChipGroup chipGroup = roomBottomSheet.findViewById(R.id.list_item);
        showcheckInDate.setText(Utils.showBottomSheetDate(calSelectedDate));
        checkInDate.setText("");

        if (editDeskBookingDetails.getDeskStatus() == 1){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.GONE);
//            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
//            chipGroup.setVisibility(View.VISIBLE);
        } else {
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            statusCheckLayout.setVisibility(View.GONE);
//            chipGroup.setVisibility(View.GONE);
        }
        if (newEditStatus.equalsIgnoreCase("new_deep_link")
                || newEditStatus.equalsIgnoreCase("request")){
            select.setVisibility(View.GONE);
            dateBlock.setVisibility(View.VISIBLE);
        } else {
            dateBlock.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }

        if (dskRoomParkStatus == 1) {
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");
            capacitylayout.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);

            deskRoomName.setText(editDeskBookingDetails.getDeskCode());
            selectedDeskId=editDeskBookingDetails.getDesktId();
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Desk");
            }

        }else if (dskRoomParkStatus==2) {
            llDeskLayout.setVisibility(View.VISIBLE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.VISIBLE);
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Meeting Room");
            }
            if (userAllowedMeetingResponseList.size() > 0){
//                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseList.get(0).getName());
                selectedDeskId = userAllowedMeetingResponseList.get(0).getId();
                locationAddress.setText(""+userAllowedMeetingResponseList.get(0).getLocationMeeting().getName());
            }
        }else {
            llDeskLayout.setVisibility(View.VISIBLE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Registration Number");
            tvComments.setText("Regitration Number");
            commentRegistration.setText(editDeskBookingDetails.getVehicleRegNumber());
            chipGroup.setVisibility(View.GONE);
            capacitylayout.setVisibility(View.GONE);
            if(newEditStatus.equalsIgnoreCase("new") ||newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Parking Slot");
            }
//            System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
            if (parkingSpotModelList.size() > 0){
//                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+parkingSpotModelList.get(0).getCode());
                selectedDeskId = parkingSpotModelList.get(0).getId();
                locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());
            }

        }
        if (editDeskBookingDetails.getEditStartTTime()!=null)
            startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
        if (editDeskBookingDetails.getEditEndTime()!=null)
            endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
        if (editDeskBookingDetails.getDate()!=null)
        date.setText(""+Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));

        if (editDeskBookingDetails.getAmenities()!=null)
            System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
//        Log.d(TAG, "editBookingUsingBottomSheet: chip"+editDeskBookingDetails.getAmenities().size());
        if (editDeskBookingDetails.getAmenities()!=null){
            for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                Chip chip = new Chip(getContext());
                chip.setId(i);
                chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                chip.setChipBackgroundColorResource(R.color.figmaGrey);
                chip.setCloseIconVisible(false);
                chip.setTextColor(getContext().getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                chipGroup.addView(chip);
            }
        }

        showcheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", checkInDate,showcheckInDate);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1 && editDeskBookingDetails.getDeskStatus() != 2)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
//                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
//                    Utils.popUpTimePicker(getActivity(),endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatBottomSheetDialog();
            }
        });
        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedicon==1 && newEditStatus.equalsIgnoreCase("new"))
                    callMeetingRoomBookingBottomSheet(editDeskBookingDetails, startTime, endTime, selectedDeskId, deskRoomName.getText().toString(), false);
                else if (selectedicon==1) {
                    if (newEditStatus.equalsIgnoreCase("request"))
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), true);
                    else
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), false);
                }
                else {
                    JsonObject jsonOuterObject = new JsonObject();
                    JsonObject jsonInnerObject = new JsonObject();
                    JsonObject jsonChangesObject = new JsonObject();
                    JsonArray jsonChangesetArray = new JsonArray();
                    JsonArray jsonDeletedIdsArray = new JsonArray();
                    jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
                    if (newEditStatus.equalsIgnoreCase("new_deep_link")
                            || newEditStatus.equalsIgnoreCase("request")){
                        if (checkInDate.getText().toString().equalsIgnoreCase("")){
                            jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00Z");
                        }else {
                            jsonInnerObject.addProperty("date",""+checkInDate.getText().toString()+"T00:00:00Z");
                        }
                    }else {

                        jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00Z");
                    }
                    switch (dskRoomParkStatus){
                        case 1:
                            jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                            jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
                            if (!commentRegistration.getText().toString().isEmpty() &&
                                    !commentRegistration.getText().toString().equalsIgnoreCase(""))
                                jsonChangesObject.addProperty("comments",commentRegistration.getText().toString());
                            if (selectedDeskId!=0 && dskRoomParkStatus==1 && selectedDeskId != editDeskBookingDetails.getDesktId()){
                                jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                            }

                            if (newEditStatus.equalsIgnoreCase("request")){
                                jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                jsonChangesObject.addProperty("usageTypeId", "7");
                                jsonChangesObject.addProperty("timeZoneId", "India Standard Time");
//                                jsonChangesObject.addProperty("typeOfCheckIn", "1");
                            }else{
                                if (!newEditStatus.equalsIgnoreCase("edit"))
                                    jsonChangesObject.addProperty("teamDeskId",editDeskBookingDetails.getDesktId());
                            }
                            break;
                        case 2:
                            jsonOuterObject.addProperty("meetingRoomId",editDeskBookingDetails.getMeetingRoomtId());
                            break;
                        case 3:
                            if (selectedDeskId!=0)
                                jsonOuterObject.addProperty("parkingSlotId",selectedDeskId);

                            if (!commentRegistration.getText().toString().isEmpty() &&
                                    !commentRegistration.getText().toString().equalsIgnoreCase(""))
                                jsonChangesObject.addProperty("vehicleRegNumber",commentRegistration.getText().toString());
                            if (newEditStatus.equalsIgnoreCase("new")){
                                jsonChangesObject.addProperty("bookedForUser",SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID));
                            }

                            break;
                    }

                    BookingsRequest bookingsRequest = new BookingsRequest();
                    ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
                    ArrayList<Integer> list1 =new ArrayList<>();

                    BookingsRequest.ChangeSets changeSets = new BookingsRequest.ChangeSets();
                    changeSets.setId(editDeskBookingDetails.getCalId());
                    changeSets.setDate(""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");
                    JsonObject jsonObject = new JsonObject();
//                    if (selectedDeskId!=0){
//                        jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
//                    }
                    if (newEditStatus.equalsIgnoreCase("new")
                            || newEditStatus.equalsIgnoreCase("new_deep_link")){
                        jsonChangesObject.addProperty("usageTypeId", "2");
                        jsonChangesObject.addProperty("timeZoneId", "India Standard Time");
                    }
                    if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()).equalsIgnoreCase(startTime.getText().toString())
                            || newEditStatus.equalsIgnoreCase("new")
                            || newEditStatus.equalsIgnoreCase("new_deep_link")
                            || newEditStatus.equalsIgnoreCase("request")
                    ){
                        jsonChangesObject.addProperty("from", "2000-01-01T"+Utils.convert12HrsTO24Hrs(startTime.getText().toString())+":00.000Z");
                    }if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()).equalsIgnoreCase(endTime.getText().toString())
                            || newEditStatus.equalsIgnoreCase("new")
                            || newEditStatus.equalsIgnoreCase("new_deep_link")
                            || newEditStatus.equalsIgnoreCase("request")
                    ){
                        jsonChangesObject.addProperty("to","2000-01-01T"+Utils.convert12HrsTO24Hrs(endTime.getText().toString())+":00.000Z");
                    }

                    jsonInnerObject.add("changes",jsonChangesObject);
                    jsonChangesetArray.add(jsonInnerObject);

                    jsonOuterObject.add("changesets", jsonChangesetArray);
                    jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

                    System.out.println("json un" + jsonOuterObject.toString());

                    if (jsonChangesObject.size() > 0){
                        editBookingCall(jsonOuterObject,position,dskRoomParkStatus,newEditStatus);
                    }
                    selectedDeskId=0;
                    roomBottomSheet.dismiss();
                }

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (editDeskBookingDetails.getDeskStatus()!=1 && editDeskBookingDetails.getDeskStatus()!=2)
                    callDeskBottomSheetDialog();
            }
        });
        repeatBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callRepeatBottomSheetDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomBottomSheet.dismiss();
            }
        });

        roomBottomSheet.show();

    }

    private void repeatBottomSheetDialog()
    {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat_new,
                new RelativeLayout(getContext()))));

        ConstraintLayout cl_daily_layout = bottomSheetDialog.findViewById(R.id.cl_daily_layout);
        ConstraintLayout cl_weekly_layout = bottomSheetDialog.findViewById(R.id.cl_weekly_layout);
        ConstraintLayout cl_none = bottomSheetDialog.findViewById(R.id.cl_none);
        ConstraintLayout cl_daily = bottomSheetDialog.findViewById(R.id.cl_daily);
        ConstraintLayout cl_weekly = bottomSheetDialog.findViewById(R.id.cl_weekly);
        ConstraintLayout cl_monthly = bottomSheetDialog.findViewById(R.id.cl_monthly);
        ConstraintLayout cl_yearly = bottomSheetDialog.findViewById(R.id.cl_yearly);
        ImageView iv_none = bottomSheetDialog.findViewById(R.id.iv_none);
        ImageView iv_daily = bottomSheetDialog.findViewById(R.id.iv_daily);
        ImageView iv_weekly = bottomSheetDialog.findViewById(R.id.iv_weekly);
        ImageView iv_monthly = bottomSheetDialog.findViewById(R.id.iv_monthly);
        ImageView iv_yearly = bottomSheetDialog.findViewById(R.id.iv_yearly);
        TextView editBookingBack = bottomSheetDialog.findViewById(R.id.editBookingBack);
        TextView editBookingContinue = bottomSheetDialog.findViewById(R.id.editBookingContinue);
        TextView tv_repeat = bottomSheetDialog.findViewById(R.id.tv_repeat);

        TextView tv_interval = bottomSheetDialog.findViewById(R.id.tv_interval);
        TextView tv_until = bottomSheetDialog.findViewById(R.id.tv_until);
        TextView tv_interval_weekly = bottomSheetDialog.findViewById(R.id.tv_interval_weekly);
        TextView tv_day = bottomSheetDialog.findViewById(R.id.tv_day);
        TextView tv_unit = bottomSheetDialog.findViewById(R.id.tv_unit);

        editBookingBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bottomSheetDialog.dismiss();
            }
        });

        tv_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openWeeks();
            }
        });


        tv_until.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openUntil();
            }
        });

        tv_unit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openUntil();
            }
        });


        tv_interval.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(requireContext(),"onclick==="+type,Toast.LENGTH_LONG).show();
                openIntervalsDialog(type);
            }
        });

        tv_interval_weekly.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(requireContext(),"onclick==="+type,Toast.LENGTH_LONG).show();
                openIntervalsDialog(type);
            }
        });


        cl_none.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type ="none";
                iv_none.setVisibility(View.VISIBLE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.GONE);
                tv_repeat.setVisibility(View.GONE);
            }
        });

        cl_daily.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type ="daily";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.VISIBLE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
                cl_weekly_layout.setVisibility(View.GONE);
            }
        });
        cl_weekly.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type ="weekly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.VISIBLE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
            }
        });
        cl_monthly.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type ="monthly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.VISIBLE);
                iv_yearly.setVisibility(View.GONE);
                cl_daily_layout.setVisibility(View.GONE);
                cl_weekly_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
            }
        });

        cl_yearly.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type ="yearly";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.GONE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.VISIBLE);
                cl_daily_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
                cl_weekly_layout.setVisibility(View.GONE);
            }
        });



        bottomSheetDialog.show();


    }

    private void openUntil()
    {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_until,
                new RelativeLayout(getContext()))));

        ConstraintLayout cl_forever = bottomSheetDialog.findViewById(R.id.cl_forever);
        ConstraintLayout cl_specific = bottomSheetDialog.findViewById(R.id.cl_specific);
        ImageView iv_forever = bottomSheetDialog.findViewById(R.id.iv_forever);
        ImageView iv_specific = bottomSheetDialog.findViewById(R.id.iv_specific);
        android.widget.CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);
        TextView editBookingBack = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView editBookingContinue = bottomSheetDialog.findViewById(R.id.tv_specific);

        calendar_view.setVisibility(View.GONE);

        editBookingBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bottomSheetDialog.dismiss();
            }
        });

        cl_forever.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_forever.setVisibility(View.VISIBLE);
                iv_specific.setVisibility(View.GONE);
                calendar_view.setVisibility(View.GONE);

            }
        });

        cl_specific.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_forever.setVisibility(View.GONE);
                iv_specific.setVisibility(View.VISIBLE);
                calendar_view.setVisibility(View.VISIBLE);

            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void openWeeks()
    {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_week,
                new RelativeLayout(getContext()))));
        bottomSheetDialog.show();
    }

    private void openIntervalsDialog(String type)
    {

        Toast.makeText(requireContext(),"openIntervalsDialog==="+type,Toast.LENGTH_LONG).show();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_interval,
                new RelativeLayout(getContext()))));

        ConstraintLayout cl_1 = bottomSheetDialog.findViewById(R.id.cl_1);
        TextView tv_1 = bottomSheetDialog.findViewById(R.id.tv_1);
        ImageView iv_1 = bottomSheetDialog.findViewById(R.id.iv_1);
        ConstraintLayout cl_2 = bottomSheetDialog.findViewById(R.id.cl_2);
        TextView tv_2 = bottomSheetDialog.findViewById(R.id.tv_2);
        ImageView iv_2 = bottomSheetDialog.findViewById(R.id.iv_2);
        ConstraintLayout cl_3 = bottomSheetDialog.findViewById(R.id.cl_3);
        TextView tv_3 = bottomSheetDialog.findViewById(R.id.tv_3);
        ImageView iv_3 = bottomSheetDialog.findViewById(R.id.iv_3);
        ConstraintLayout cl_4 = bottomSheetDialog.findViewById(R.id.cl_4);
        TextView tv_4 = bottomSheetDialog.findViewById(R.id.tv_4);
        ImageView iv_4 = bottomSheetDialog.findViewById(R.id.iv_4);
        ConstraintLayout cl_5 = bottomSheetDialog.findViewById(R.id.cl_5);
        TextView tv_5 = bottomSheetDialog.findViewById(R.id.tv_5);
        ImageView iv_5 = bottomSheetDialog.findViewById(R.id.iv_5);
        ConstraintLayout cl_6 = bottomSheetDialog.findViewById(R.id.cl_6);
        TextView tv_6 = bottomSheetDialog.findViewById(R.id.tv_6);
        ImageView iv_6 = bottomSheetDialog.findViewById(R.id.iv_6);
        ConstraintLayout cl_7 = bottomSheetDialog.findViewById(R.id.cl_7);
        TextView tv_7 = bottomSheetDialog.findViewById(R.id.tv_7);
        ImageView iv_7 = bottomSheetDialog.findViewById(R.id.iv_7);
        ConstraintLayout cl_8 = bottomSheetDialog.findViewById(R.id.cl_8);
        TextView tv_8 = bottomSheetDialog.findViewById(R.id.tv_8);
        ImageView iv_8 = bottomSheetDialog.findViewById(R.id.iv_8);
        ConstraintLayout cl_9 = bottomSheetDialog.findViewById(R.id.cl_9);
        TextView tv_9 = bottomSheetDialog.findViewById(R.id.tv_9);
        ImageView iv_9 = bottomSheetDialog.findViewById(R.id.iv_9);
        ConstraintLayout cl_10 = bottomSheetDialog.findViewById(R.id.cl_10);
        TextView tv_10 = bottomSheetDialog.findViewById(R.id.tv_10);
        ImageView iv_10 = bottomSheetDialog.findViewById(R.id.iv_10);

        cl_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.VISIBLE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.VISIBLE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.VISIBLE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.VISIBLE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.VISIBLE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.VISIBLE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.VISIBLE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.VISIBLE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.VISIBLE);
                iv_10.setVisibility(View.GONE);

            }
        });

        cl_10.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_10.setVisibility(View.VISIBLE);

            }
        });

        if(type.equalsIgnoreCase("daily")){

            tv_1.setText("1 day");
            tv_2.setText("2 day");
            tv_3.setText("3 day");
            tv_4.setText("4 day");
            tv_5.setText("5 day");
            tv_6.setText("6 day");
            tv_7.setText("7 day");
            tv_8.setText("8 day");
            tv_9.setText("9 day");
            tv_10.setText("10 day");


        } else if(type.equalsIgnoreCase("weekly")){
            tv_1.setText("1 week");
            tv_2.setText("2 week");
            tv_3.setText("3 week");
            tv_4.setText("4 week");
            tv_5.setText("5 week");
            tv_6.setText("6 week");
            tv_7.setText("7 week");
            tv_8.setText("8 week");
            tv_9.setText("9 week");
            tv_10.setText("10 week");

        } else if (type.equalsIgnoreCase("monthly")){
            tv_1.setText("1 month");
            tv_2.setText("2 month");
            tv_3.setText("3 month");
            tv_4.setText("4 month");
            tv_5.setText("5 month");
            tv_6.setText("6 month");
            tv_7.setText("7 month");
            tv_8.setText("8 month");
            tv_9.setText("9 month");
            tv_10.setText("10 month");

        } else {
            tv_1.setText("1 year");
            tv_2.setText("2 year");
            tv_3.setText("3 year");
            tv_4.setText("4 year");
            tv_5.setText("5 year");
            tv_6.setText("6 year");
            tv_7.setText("7 year");
            tv_8.setText("8 year");
            tv_9.setText("9 year");
            tv_10.setText("10 year");
        }
        bottomSheetDialog.show();
    }

    private void callDeskBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        TextView bsRepeatBack, selectDesk;
        rvDeskRecycler= bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        selectDesk= bottomSheetDialog.findViewById(R.id.select_desk);
        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
        if (selectedicon == 2){
            selectDesk.setText("Select Parking");
            parkingSpotListRecyclerAdapter =new ParkingSpotListRecyclerAdapter(getContext(),this,getActivity(),parkingSpotModelList,getContext(),bottomSheetDialog);
            rvDeskRecycler.setAdapter(parkingSpotListRecyclerAdapter);
        }else if (selectedicon==1){
            selectDesk.setText("Select Meeting Room");
            roomListRecyclerAdapter =new RoomListRecyclerAdapter(getContext(),this,getActivity(),userAllowedMeetingResponseList,getContext(),bottomSheetDialog);
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }else {
            selectDesk.setText("Select Desk");
            deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,getActivity(),bookingForEditResponseDesk,getContext(),bottomSheetDialog);
            rvDeskRecycler.setAdapter(deskListRecyclerAdapter);

        }


        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }
    public void editBookingCall(JsonObject data,int position,int dskRoomStatus,String newEditDelete) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());
            // TODO: 06-07-2022
            String json ="{'teamId':6,'teamMembershipId':21,'changesets':[{'id':1178,'date':'2022-07-11T00:00:00.000Z','changes':{'teamDeskId':64,'from':'2000-01-01T14:24:00.000Z','to':'2000-01-01T17:50:00.000Z'}}],'deletedIds':[]}";
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call=null;
            switch (dskRoomStatus){
                case 1:
                    call = apiService.bookingBookings(data);
                    break;
                case 2:
                    call = apiService.meetingRoomBookingBookings(data);
                    break;
                case 3:
                    call = apiService.carParkBookingBookings(data);
                    break;
            }
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    dialog.dismiss();
                    if (response.code()==200){
//                        Utils.showCustomAlertDialog(getActivity(),"Update Success");
//                        Toast.makeText(getActivity(), "Success Bala", Toast.LENGTH_SHORT).show();
                        if (response.body().getResultCode()!=null && response.body().getResultCode().equalsIgnoreCase("ok")){
                            if (newEditDelete.equalsIgnoreCase("new")
                                    || newEditDelete.equalsIgnoreCase("new_deep_link")
                                    )
                                openCheckoutDialog("Booking Successful",dskRoomStatus);
                            else if (newEditDelete.equalsIgnoreCase("edit"))
                                openCheckoutDialog("Booking Updated",dskRoomStatus);
                            else if (newEditDelete.equalsIgnoreCase("request"))
                                openCheckoutDialog("Booking Requested Successfully",dskRoomStatus);
                            else
                                openCheckoutDeleteDialog("Booking Deleted",dskRoomStatus);

                            switch (dskRoomStatus){
                                case 1:
                                    tabToggleViewClicked(0);
                                    break;
                                case 2:
                                    tabToggleViewClicked(1);
                                    break;
                                case 3:
                                    tabToggleViewClicked(2);
                                    break;
                                default:
                                    break;
                            }

//                            openCheckoutDialog("Booking Updated");
                        }else {
                            Utils.showCustomAlertDialog(getActivity(),"Booking Not Updated "+response.body().getResultCode().toString());
                        }
                    }else if (response.code() == 500){
                        Utils.showCustomAlertDialog(getActivity(),"500 Response");
                    }else if (response.code() == 401){
                        Utils.showCustomTokenExpiredDialog(getActivity(),"401 Error Response");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                    }
                    else {
                        Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail Bala"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("resps"+t.getMessage());
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }
    private void openCheckoutDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(getActivity());
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(""+mesg);
        if (dskRoomStatus==1 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else if(dskRoomStatus==3 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else{
            if (bookEditBottomSheet!=null)
                bookEditBottomSheet.dismiss();
            roomBottomSheet.dismiss();
        }

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }
    private void openCheckoutDeleteDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(getActivity());
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        ImageView ivChecout = popDialog.findViewById(R.id.ivCheckoutSuccess);
//        ivChecout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_red));
        ivChecout.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_red));
        dialogMsg.setText(""+mesg);
        if (dskRoomStatus==1 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else if(dskRoomStatus==3 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else{
            if (bookEditBottomSheet!=null)
                bookEditBottomSheet.dismiss();
            if (roomBottomSheet!=null)
                roomBottomSheet.dismiss();
        }

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

    @Override
    public void onEditClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(bookings.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(bookings.getMyto()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(bookings.getDate()));
        editDeskBookingDetails.setCalId(bookings.getId());
        editDeskBookingDetails.setDeskCode(bookings.getDeskCode());
        editDeskBookingDetails.setDeskStatus(0);
        editDeskBookingDetails.setDesktId(bookings.getTeamDeskId());
        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");

    }

    @Override
    public void ondeskDeleteClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setCalId(bookings.getId());
//        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"delete");

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();
//        list1.add(editDeskBookingDetails.getCalId());
        jsonDeletedIdsArray.add(editDeskBookingDetails.getCalId());
        
        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        System.out.println("json un"+jsonOuterObject.toString());

            editBookingCall(jsonOuterObject,0,1,"delete");

    }

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
        selectedDeskId = deskId;
    }

    @Override
    public void onCarEditClicks(CarParkListToEditResponse carParkBooking) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setCalId(carParkBooking.getId());
        editDeskBookingDetails.setParkingSlotId(carParkBooking.getParkingSlotId());
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(carParkBooking.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(carParkBooking.getMyto()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(carParkBooking.getDate()));
        editDeskBookingDetails.setVehicleRegNumber(carParkBooking.getVehicleRegNumber());
        editDeskBookingDetails.setParkingSlotCode(carParkBooking.getParkingSlotName());
        getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editDeskBookingDetails,"edit");


    }

    @Override
    public void onCarDeleteClicks(CarParkListToEditResponse carParkBooking) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
//        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"delete");

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("parkingSlotId",carParkBooking.getId());

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();
//        list1.add(editDeskBookingDetails.getCalId());
        jsonDeletedIdsArray.add(carParkBooking.getId());
        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,3,"delete");


    }

    @Override
    public void onSelectParking(int deskId, String deskName,String location) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
        locationAddress.setText(location);
    }


    //Room Booking Methods
    private void callMeetingRoomBookingBottomSheet(EditBookingDetails editDeskBookingDetails, TextView startTime, TextView endTime, int meetingRoomId, String meetingRoomName, boolean isRequest) {

        TextView startRoomTime, endTRoomime, editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription, roomTitle;
        EditText etParticipants, etSubject, etComments;
        RecyclerView rvParticipant;
        LinearLayoutManager linearLayoutManager;
        RelativeLayout startTimeLayout, endTimeLayout;


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_participant_booking,
                new RelativeLayout(getContext()))));

        etParticipants = bottomSheetDialog.findViewById(R.id.etParticipants);
        etSubject = bottomSheetDialog.findViewById(R.id.etSubject);

        etComments = bottomSheetDialog.findViewById(R.id.etComments);
        editRoomBookingContinue = bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack = bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        roomTitle = bottomSheetDialog.findViewById(R.id.roomTitle);
        rvParticipant = bottomSheetDialog.findViewById(R.id.rvParticipant);
        participantChipGroup = bottomSheetDialog.findViewById(R.id.participantChipGroup);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);


        roomTitle.setText(meetingRoomName);


        etParticipants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 2) {
                    sendEnteredPartipantLetterToServer(s.toString(), rvParticipant);
                } else {
                    rvParticipant.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(binding.serachBar.getWindowToken(), 0);
            }
        });

        editRoomBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        editRoomBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = true;

                String subject = etSubject.getText().toString();
                String comment = etComments.getText().toString();
                if (subject.isEmpty() || subject.equals("") || subject == null) {
                    Toast.makeText(getContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (comment.isEmpty() || comment.equals("") || comment == null) {
                    Toast.makeText(getContext(), "Please Enter Comment", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (status) {
                    bottomSheetDialog.dismiss();
                    bottomSheetDialog.dismiss();
                    doMeetingRoomBooking(meetingRoomId, startTime.getText().toString(), endTime.getText().toString(), subject, comment, isRequest);

                } else {
                    Toast.makeText(getContext(), "Please Enter All Details", Toast.LENGTH_LONG).show();
                }

            }
        });


        bottomSheetDialog.show();

    }

    private void sendEnteredPartipantLetterToServer(String participantLetter, RecyclerView rvParticipant) {

//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        dialog = ProgressDialog.showProgressBar(getContext());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ParticipantDetsilResponse>> call = apiService.getParticipantDetails(participantLetter, 1);
        call.enqueue(new Callback<List<ParticipantDetsilResponse>>() {
            @Override
            public void onResponse(Call<List<ParticipantDetsilResponse>> call, Response<List<ParticipantDetsilResponse>> response) {


                List<ParticipantDetsilResponse> participantDetsilResponseList = response.body();

//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                if (participantDetsilResponseList != null) {

                    //System.out.println("ParticipantNameList" + participantDetsilResponseList.get(0).getFirstName());

                    showParticipantNameInRecyclerView(participantDetsilResponseList, rvParticipant);

                }

            }

            @Override
            public void onFailure(Call<List<ParticipantDetsilResponse>> call, Throwable t) {
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
    }

    private void showParticipantNameInRecyclerView(List<ParticipantDetsilResponse> participantDetsilResponseList, RecyclerView rvParticipant) {


        rvParticipant.setVisibility(View.VISIBLE);
        ParticipantNameShowAdapter participantNameShowAdapter = new ParticipantNameShowAdapter(getContext(), participantDetsilResponseList, this);
        rvParticipant.setAdapter(participantNameShowAdapter);
    }

    private void doMeetingRoomBooking(int meetingRoomId,
                                      String startRoomTime,
                                      String endRoomTime,
                                      String subject,
                                      String comment,
                                      boolean isRequest) {

//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        dialog= ProgressDialog.showProgressBar(getContext());
        MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
        meetingRoomRequest.setMsTeams(false);
        meetingRoomRequest.setHandleRecurring(false);
        meetingRoomRequest.setOnlineMeeting(false);

        MeetingRoomRequest.Changeset m = meetingRoomRequest.new Changeset();
        m.setId(0);
        m.setDate(Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + "T" + "00:00:00.000" + "Z");

        MeetingRoomRequest.Changeset.Changes changes = m.new Changes();
        changes.setFrom(getCurrentDate() + "" + "T" + Utils.convert12HrsTO24Hrs(startRoomTime) + ":" + "00" + "." + "000" + "Z");
        changes.setMyto(getCurrentDate() + "" + "T" + Utils.convert12HrsTO24Hrs(endRoomTime) + ":" + "00" + "." + "000" + "Z");
        changes.setComments(comment);
        changes.setSubject(subject);
        changes.setRequest(isRequest);

        m.setChanges(changes);

        List<MeetingRoomRequest.Changeset> changesetList = new ArrayList<>();
        changesetList.add(m);

        meetingRoomRequest.setChangesets(changesetList);

        List<Integer> attendeesList = new ArrayList<>();


        //Newly Participant Added
        if (chipList != null) {
            for (int i = 0; i < chipList.size(); i++) {
                attendeesList.add(chipList.get(i).getId());
            }

        } //End

        changes.setAttendees(attendeesList);

        List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList = new ArrayList<>();
        changes.setExternalAttendees(externalAttendeesList);

        List<MeetingRoomRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        meetingRoomRequest.setDeletedIds(deleteIdsList);

        System.out.println("BookingMeetingRoom" + meetingRoomRequest);


        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doMeetingRoomBook(meetingRoomRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                chipList.clear();

                if (response.code()==200){
                    openCheckoutDialog("Booking Succcessfull",2);
                }else {
                    roomBottomSheet.dismiss();
                    Utils.showCustomAlertDialog(getActivity(), "Booking Not Successfull");
                }

                ProgressDialog.dismisProgressBar(getContext(),dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(),dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });


    }


    @Override
    public void onParticipantSelect(ParticipantDetsilResponse participantDetsilResponse) {

        this.participantDetsilResponse= participantDetsilResponse;

        Chip chip=new Chip(getContext());
        chip.setText(participantDetsilResponse.getFullName());
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);

        chipList.add(participantDetsilResponse);

        participantChipGroup.addView(chip);
        participantChipGroup.setVisibility(View.VISIBLE);


        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chipList!=null){
                    for (int i = 0; i <chipList.size() ; i++) {

                        if(chip.getText().toString().contains(chipList.get(i).getFullName())){
                            chipList.remove(chipList.get(i));
                        }

                    }
                }

                System.out.println("RemoveChipGroupName"+chip.getText().toString());

                participantChipGroup.removeView(chip);

            }
        });

    }

    @Override
    public void onSelectRoom(int deskId, String deskName, String location) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
        locationAddress.setText(location);
    }

    //Global Search MOdel
    private void getLocateCountryList() {


        if (Utils.isNetworkAvailable(getContext())) {

            dialog = ProgressDialog.showProgressBar(getContext());
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes = response.body();

                    System.out.println("LocateCountryList" + locateCountryResposes.size());

                    CallFloorBottomSheet(locateCountryResposes);


                    ProgressDialog.dismisProgressBar(getContext(), dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    ProgressDialog.dismisProgressBar(getContext(), dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }


    }
    //Select Floor Plan BottomSheeet
    private void CallFloorBottomSheet(List<LocateCountryRespose> locateCountryResposes) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().
                inflate(R.layout.bottom_sheet_locate_floor_filter,
                        new RelativeLayout(getContext())));


        bsLocationSearch = bottomSheetDialog.findViewById(R.id.bsLocationSearch);

        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);

        if (buildingName == null && floorName == null) {
            bsLocationSearch.setText("40th Bank Street,30th Floor");
        } else {
            bsLocationSearch.setText(buildingName + "," + floorName);
        }

        countryBlock = bottomSheetDialog.findViewById(R.id.bsCountryBlock);
        statBlock = bottomSheetDialog.findViewById(R.id.bsStateBlock);
        streetBlock = bottomSheetDialog.findViewById(R.id.bsStreetBlock);
        floorBlock = bottomSheetDialog.findViewById(R.id.bsFloorBlock);

        country = bottomSheetDialog.findViewById(R.id.bsCountry);
        state = bottomSheetDialog.findViewById(R.id.bsState);
        street = bottomSheetDialog.findViewById(R.id.bsStreet);
        floor = bottomSheetDialog.findViewById(R.id.bsfloor);


        //Get initial data
        //getCountryStateStreetAndFloorDetails();

        rvCountry = bottomSheetDialog.findViewById(R.id.rvCountry);
        rvState = bottomSheetDialog.findViewById(R.id.rvState);
        rvStreet = bottomSheetDialog.findViewById(R.id.rvStreet);
        rvFloor = bottomSheetDialog.findViewById(R.id.rvFloorList);


        country.setText("Global Location");
        //country.setText(locateCountryResposes.get(0).getName());
        rvCountry.setVisibility(View.INVISIBLE);
        statBlock.setVisibility(View.INVISIBLE);
        rvState.setVisibility(View.INVISIBLE);
        streetBlock.setVisibility(View.INVISIBLE);
        rvStreet.setVisibility(View.INVISIBLE);
        floorBlock.setVisibility(View.INVISIBLE);

        back = bottomSheetDialog.findViewById(R.id.bsBack);
        bsApply = bottomSheetDialog.findViewById(R.id.bsApply);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();


            }
        });

        bsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canvasss = 1;

                //removes desk in layout
//                binding.firstLayout.removeAllViews();
                //removeZoomInLayout();

                initLoadFloorDetails(canvasss);
                bottomSheetDialog.dismiss();
            }
        });


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                country.setText("Global Location");
                rvCountry.setVisibility(View.VISIBLE);
                showCountryListInAdapter(locateCountryResposes);

                statBlock.setVisibility(View.GONE);
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.GONE);
                rvStreet.setVisibility(View.GONE);
                floorBlock.setVisibility(View.GONE);
                rvFloor.setVisibility(View.INVISIBLE);

                //getLocateCountryList();
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.setText("City");
                streetBlock.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.INVISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);
                callCountrysChildData(stateId);


            }
        });

        //Building
        street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                street.setText("Building");
                floorBlock.setVisibility(View.INVISIBLE);
                floor.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.VISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);
            }
        });

        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvFloor.setVisibility(View.VISIBLE);
            }
        });



         /*linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         rvCountry.setLayoutManager(linearLayoutManager);
         rvCountry.setHasFixedSize(true);*/

        bottomSheetDialog.show();

    }
    private void showCountryListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "COUNTRY");
        rvCountry.setAdapter(showCountryAdapter);

    }


    private void callCountrysChildData(int parentId) {
        dialog = ProgressDialog.showProgressBar(getContext());
//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);

        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposes = response.body();

                for (int i = 0; i < locateCountryResposes.size(); i++) {
                    System.out.println("CountrychildName" + locateCountryResposes.get(i).getName());
                }

                rvCountry.setVisibility(View.GONE);
                statBlock.setVisibility(View.VISIBLE);
                rvState.setVisibility(View.VISIBLE);

                showCountryChildListInAdapter(locateCountryResposes);

                ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showCountryChildListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvState.setLayoutManager(linearLayoutManager);
        rvState.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "STATE");
        rvState.setAdapter(showCountryAdapter);
    }

    private void getFloorDetails(int floorId, boolean findCoordinateStatus) {
//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(floorId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposes = response.body();

                //ProgressDialog.dismisProgressBar(getContext(), dialog);

                if (findCoordinateStatus) {
                    int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    for (int i = 0; i < locateCountryResposes.size(); i++) {
                        if (parentId == locateCountryResposes.get(i).getLocateCountryId()) {
                            if (locateCountryResposes.get(i).getSupportZoneLayoutItemsList() != null) {
                                // ProgressDialog.dismisProgressBar(getContext(), dialog);
                                getOtherSubZoneLayoutItems(locateCountryResposes.get(i).getSupportZoneLayoutItemsList());
                            }
                        }
                    }
                } else {
                    rvStreet.setVisibility(View.VISIBLE);
                    showFloorListInAdapter(locateCountryResposes);

                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                }
                ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                 ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void showFloorListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvStreet.setLayoutManager(linearLayoutManager);
        rvStreet.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "FLOOR");
        rvStreet.setAdapter(showCountryAdapter);

    }

    private void getOtherSubZoneLayoutItems(List<LocateCountryRespose.SupportZoneLayoutItems> supportZoneLayoutItemsList) {

        //List<Point> pointList=new ArrayList<>();

        pointList.clear();

        for (int i = 0; i < supportZoneLayoutItemsList.size(); i++) {

            System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.size());
            System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.get(i).getTitle());
            System.out.println("CorrrdnateSize" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size());

            for (int j = 0; j < supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size(); j++) {

                System.out.println("DATAATATA" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0) + " " + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));

                Point point = new Point(supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0),
                        supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));
                pointList.add(point);

            }

        }

       /* for (int i = 0; i <pointList.size() ; i++) {
            System.out.println("PointListDate "+pointList.get(i).getX()+" "+pointList.get(i).getY());
        }*/

        //ProgressDialog.dismisProgressBar(getContext(), dialog);
        /*  Point point=new Point(coordinateList.get(i).get(0),coordinateList.get(i).get(1));

         */

    }

    private void getDeskRoomCarParkingDetails(int parentId) {
        dialog = ProgressDialog.showProgressBar(getContext());
//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                System.out.println("InsideFloorDetails");

                for (int j = 0; j < locateCountryResposeList.size(); j++) {
                    System.out.println("InsideFloorName" + locateCountryResposeList.get(j).getName());
                }

                showFloorImageAndNameInAdapter(locateCountryResposeList);

                ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    //Final Adapter
    private void showFloorImageAndNameInAdapter(List<LocateCountryRespose> locateCountryResposeList) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFloor.setLayoutManager(linearLayoutManager);
        rvFloor.setHasFixedSize(true);

        floorAdapter = new FloorAdapter(getContext(), locateCountryResposeList, this, "FLOOR_NAME");
        rvFloor.setAdapter(floorAdapter);
    }

    public void initLoadFloorDetails(int canvasDrawStatus) {

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

        if (parentId > 0) {
            //Disable touch Screen
            ProgressDialog.touchLock(getContext(),getActivity());
            ProgressDialog.clearTouchLock(getContext(),getActivity());

            //Set Selected Floor In SearchView
            String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
            String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
            String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
            String fullPathLocation = SessionHandler.getInstance().get(getContext(), AppConstants.FULLPATHLOCATION);

            if (CountryName == null && buildingName == null && floorName == null && fullPathLocation==null) {
                binding.searchGlobal.setHint("Choose Location");
            } else {
                if(fullPathLocation==null) {
                    binding.searchGlobal.setText(CountryName + "," + buildingName + "," + floorName);
                }else {
                    binding.searchGlobal.setText(fullPathLocation);
                }
            }

            //ForCoordinate
            int subParentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.SUB_PARENT_ID);
            boolean findCoordinateStatus = true;

            //Used For Desk Avaliability Checking
            if (!calSelectedDate.isEmpty() || !calSelectedDate.equalsIgnoreCase(""))
                getDeskCountLocation(calSelectedMont, ""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),canvasDrawStatus);
            else if (!calSelectedMont.isEmpty() || !calSelectedMont.equalsIgnoreCase(""))
                getDeskCountLocation(calSelectedDate, ""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),canvasDrawStatus);
            else
                getDeskCountLocation(Utils.getCurrentDate(), ""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),canvasDrawStatus);

//            getAvaliableDeskDetails(null, 0);

            //CarChecking
//            doInitCarAvalibilityHere(parentId);

            //Meeting Checking
//            doInitMeetingAvalibilityHere(parentId,canvasDrawStatus);

        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks, int id) {


//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //"2022-07-05T00:00:00.000Z"
        //"2000-01-01T15:50:38.000Z"
        //"2000-01-01T18:00:00.000Z"


        //String toDate=Utils.getCurrentDate()+"T00:00:00Z";
        //System.out.println("ToDateCheckoing"+toDate);

        //Add min and hour
       /* String startTime=Utils.addMinuteWithCurrentTime(1,2);
        String fromTime=startTime+".000Z";
        String endTime=Utils.addMinuteWithCurrentTime(2,9);
        String toTime=endTime+".000Z";*/
        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
        String fromTime = binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
        String toTime = binding.locateCalendearView.getText().toString() + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";

        //System.out.println("DateAndStatTimeAndEndTime"+toDate+" "+fromTime+" "+toTime);

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
//        System.out.println("parent Booking cje"+parentId);
        //Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, toDate, fromTime, toTime);

        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();

                //Call GetTeams API
//                getTeams();


                if (deskAvaliabilityResponseList != null) {
//                    teamDeskAvaliabilityList = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList();
                }

                //GetTeamDeskIdForBooking
                if (id > 0) {
                    for (int i = 0; i < deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size(); i++) {

                        if (id == deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getDeskId()) {
//                            teamDeskIdForBooking = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getTeamDeskId();
//                            System.out.println("TeamDeskIdForBooking " + teamDeskIdForBooking);
                        }


                    }
                }


            }

            @Override
            public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onSelect(LocateCountryRespose locateCountryRespose, String identifier) {

        switch (identifier) {
            case "COUNTRY":
                state.setText("City");
                stateId = locateCountryRespose.getLocateCountryId();
                SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME, locateCountryRespose.getName());
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());
                break;

            case "STATE":
                state.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING, locateCountryRespose.getName());
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                street.setText("Building");

                SessionHandler.getInstance().saveInt(getContext(), AppConstants.SUB_PARENT_ID, locateCountryRespose.getLocateCountryId());
                getFloorDetails(locateCountryRespose.getLocateCountryId(), false);


                System.out.println("SubParentIdAndItsPosition" + locateCountryRespose.getLocateCountryId() + " ");

                break;

            case "FLOOR":
                floorBlock.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                floor.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR, locateCountryRespose.getName());
                SessionHandler.getInstance().remove(getContext(),AppConstants.FULLPATHLOCATION);
                rvStreet.setVisibility(View.GONE);
                rvFloor.setVisibility(View.VISIBLE);
                SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, locateCountryRespose.getLocateCountryId());

                if (locateCountryRespose.getSupportZoneLayoutItemsList() != null) {
                    getOtherSubZoneLayoutItems(locateCountryRespose.getSupportZoneLayoutItemsList());
                }

                //Final
                getDeskRoomCarParkingDetails(locateCountryRespose.getLocateCountryId());


                break;


        }

        if (Utils.isNetworkAvailable(getContext())) {

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }

    }

    //Locate Booking TimerPicker BottomSheet
    private void bottomSheetLocateTimePickerInBooking(Context mContext, Activity activity, TextView tv, String title, String date, int i) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));

        TimePicker simpleTimePicker24Hours = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        //simpleTimePicker24Hours.setIs24HourView(false);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);

        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }

        titleTv.setText(title);

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hour = null, minute = null;
                String getHHour = String.valueOf(simpleTimePicker24Hours.getHour());
                String getMMinute = String.valueOf(simpleTimePicker24Hours.getMinute());

                if (getHHour.length() == 1) {
                    hour = "0" + getHHour;
                } else {
                    hour = getHHour;
                }


                if (getMMinute.length() == 1) {
                    minute = "0" + getMMinute;
                } else {
                    minute = getMMinute;
                }

                //System.out.println("GETDATATATATA" + hour + " " + minute);

                tv.setText(hour + ":" + minute);

                if (i == 1) {
                    String eTime = binding.locateEndTime.getText().toString();
                    checkStartEndtime(hour + ":" + minute, eTime);
                } else {
                    String sTime = binding.locateStartTime.getText().toString();
                    checkStartEndtime(sTime, hour + ":" + minute);
                }

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }
    private void checkStartEndtime(String startTime, String endTime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            if (date1.before(date2)) {
//                binding.firstLayout.removeAllViews();
                endTimeSelectedStats = 1;
                initLoadFloorDetails(2);
            } else {
                Toast.makeText(getContext(), "Invalid Time Range", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



}