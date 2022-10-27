package dream.guys.hotdeskandroid.ui.book;

import static dream.guys.hotdeskandroid.utils.Utils.addingHoursToCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.compareTwoDate;
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
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ActiveTeamsAdapter;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapterBooking;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.NewDeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParkingSpotListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.adapter.RoomListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.MeetingAmenityStatus;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRecurrence;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.MeetingStatusModel;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CarParkListToEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkLocationsModel;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.RoomListResponse;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationsListActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.CalendarView;
import dream.guys.hotdeskandroid.utils.LogicHandler;
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
        NewDeskListRecyclerAdapter.OnChangeSelected,
        ActiveTeamsAdapter.OnActiveTeamsSelected,
        CarListToEditAdapterBooking.CarEditClickableBooking,
        ParkingSpotListRecyclerAdapter.OnSelectSelected,
        ParticipantNameShowAdapter.OnParticipantSelectable,
        RoomListRecyclerAdapter.OnSelectSelected,
        ShowCountryAdapter.OnSelectListener{

    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;


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

    //For Langguage
    @BindView(R.id.profile_back)
    TextView profile_back;
    @BindView(R.id.tvStartLocate)
    TextView tvStartLocate;
    @BindView(R.id.tvLocateEndTime)
    TextView tvLocateEndTime;
    @BindView(R.id.searchGlobal)
    TextView searchGlobal;



    List<DeskRoomCountResponse> events = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    Dialog dialog;

    BottomSheetDialog bookEditBottomSheet;
    BottomSheetDialog roomBottomSheet;
    public BottomSheetDialog deskListBottomSheet;
    BottomSheetDialog activeTeamsBottomSheet;
    int selectedicon = 0;
    String calSelectedDate="";
    String calSelectedMont="";

    String type="none";

    TextView startTime,endTime,repeat,date,deskRoomName,locationAddress;
    String repeatValue="None";

    int teamId=0,teamMembershipId=0,selectedDeskId=0;

    RecyclerView rvHomeBooking,rvDeskRecycler, rvActiveTeams;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    NewDeskListRecyclerAdapter newdeskListRecyclerAdapter;
    ActiveTeamsAdapter activeTeamsAdapter;

    ParkingSpotListRecyclerAdapter parkingSpotListRecyclerAdapter;
    RoomListRecyclerAdapter roomListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nestedScrollView;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk=new ArrayList<>();
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList = new ArrayList<>();
    List<ParkingSpotModel> parkingSpotModelList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseFilterList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdated=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdatedFilterList=new ArrayList<>();

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


    //Repeat
    int enableCurrentWeek=-1;
    boolean repeatActvieStatus=false;
    boolean isGlobalLocationSetUP = false;
    TextView tvRepeat, tvTeamName;
    int participants = 0;
    BottomSheetDialog repeatBottomSheetDialog;
    ChipGroup chipGroup;

    //Filter
    List<MeetingAmenityStatus> meetingAmenityStatusList=new ArrayList<>();
    List<AmenitiesResponse> amenitiesListToShowInMeetingRoomList=new ArrayList<>();
    List<Integer> filterAmenitiesList=new ArrayList<>();

    boolean amenitiesApplyStatus=false;
    boolean teamsCheckBoxStatus=false;

    boolean isVehicleReg = false;

    ArrayList<DataModel> mList = new ArrayList<>();;
    int filterClickedStatus=0;

    //FloorSearch
    EditText bsGeneralSearch;

    //FloorSearch
    boolean floorSearchStatus=false;

    //teams list and Desk List
    List<ActiveTeamsResponse> activeTeamsList = new ArrayList<>();
    public int selectedTeamId = 0;
    public int selectedTeamAutoApproveStatus = 0;
    String selectedTeamName="";
    EditBookingDetails editBookingDetailsGlobal;
    UserDetailsResponse profileData; //= new UserDetailsResponse();

    List<MeetingListToEditResponse.Attendees> attendeesListForEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Gson gson = new Gson();
        String json = SessionHandler.getInstance().get(getContext(), AppConstants.LOGIN_RESPONSE);
        if (json!=null){
            profileData = gson.fromJson(json, UserDetailsResponse.class);
        }

        selectedTeamId = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);
        setLanguage();

        checkVeichleReg();

        dialog= new Dialog(getActivity());
        if (endTimeSelectedStats == 0) {
            binding.locateEndTime.setText("23:59");
        }

        getActiveTeams();
        getAmenities();
        checkTeamsCheckBox();
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
        binding.rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterClickedStatus==0){
                    getLocateAmenitiesFilterData(true);
                }else {
                    //amenitiesResponseList
                    callLocateFilterBottomSheet(null);
                }
//                getLocateAmenitiesFilterData(true);
            }
        });
        binding.searchGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocateCountryList();
            }
        });

        binding.tvParticipantsCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase(""))
                    participants = Integer.parseInt(s.toString());
                else
                    participants = 0;
            }
        });

        binding.calendarView.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date, int pos) {
                boolean countCheck = false;
                loo:
                for (int i=0; i<events.size();i++){
//                    System.out.println("avail assigned COunt"+Utils.getYearMonthDateFormat(date) +" : "+events.get(i).getDate());
                    if (events.get(i).getDate().equalsIgnoreCase(Utils.getYearMonthDateFormat(date)+"T00:00:00Z")){
                        System.out.println("avail count" + events.get(i).getAvailableCount() +events.get(i).getDate());
                        System.out.println("avail assigned COunt" + (events.get(i).getAssignedCount()-events.get(i).getUsedCount()));
                        System.out.println("avail assigned COunt assign" + events.get(i).getAssignedCount());
                        System.out.println("avail assigned COunt used" + events.get(i).getUsedCount());
                        if (events.get(i).getAvailableCount()>0
                                || (events.get(i).getAssignedCount()
                                        - events.get(i).getUsedCount())>0) {
                            countCheck=true;
                            break loo;
                        }
                    }
                }

                if (countCheck) {
                    if (!(Utils.compareTwoDate(date,Utils.getCurrentDate()) == 1)){
                        if (selectedicon==0){
                            /*if (isGlobalLocationSetUP)
                                getAvaliableDeskDetails("3",Utils.getISO8601format(date));
                            else*/
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
                    }else
                        Toast.makeText(getContext(), "Please Select current Date", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "No booking available", Toast.LENGTH_SHORT).show();

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

    private void getActiveTeams() {

        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ActiveTeamsResponse>> call = apiService.getActiveTeams();
            call.enqueue(new Callback<List<ActiveTeamsResponse>>() {
                @Override
                public void onResponse(Call<List<ActiveTeamsResponse>> call, Response<List<ActiveTeamsResponse>> response) {
//                    activeTeamsList = response.body();
                    for (int i=0;i<response.body().size();i++) {
                        if (response.body().get(i).isLeafTeam()){
                            activeTeamsList.add(response.body().get(i));
                        }
                    }

                    for (int i=0; i<activeTeamsList.size(); i++) {
                        if (selectedTeamId==activeTeamsList.get(i).getId()) {
                            selectedTeamName = activeTeamsList.get(i).getName();
                            selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ActiveTeamsResponse>> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }


    private void getAmenities() {
        if (Utils.isNetworkAvailable(getActivity())) {
//            dialog= ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<AmenitiesResponse>> call = apiService.getAmenities();
            call.enqueue(new Callback<List<AmenitiesResponse>>() {
                @Override
                public void onResponse(Call<List<AmenitiesResponse>> call, Response<List<AmenitiesResponse>> response) {
                    if(response.code()==200){
                        if(response.body().size() > 0)
                            amenitiesList = response.body();
                    }else if(response.code()==401){
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                    }
                }
                @Override
                public void onFailure(Call<List<AmenitiesResponse>> call, Throwable t) {

                }
            });
        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
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
                editBookingDetails.setEditEndTime(Utils.addHoursToDate(2));

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
                editBookingDetails.setEditEndTime(Utils.addHoursToDate(2));

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



        tabToggleViewClicked(selectedicon);

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
                binding.tvDesk.setText(appKeysPage.getWorkSpace());
                binding.rlParticipants.setVisibility(View.GONE);
                binding.rlFilter.setVisibility(View.GONE);
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
               binding.tvRoom.setText(appKeysPage.getRoom());
                binding.rlParticipants.setVisibility(View.VISIBLE);
                binding.rlFilter.setVisibility(View.VISIBLE);

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
                binding.tvParking.setText(appKeysPage.getParking());
                binding.rlParticipants.setVisibility(View.GONE);
                binding.rlFilter.setVisibility(View.GONE);


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
                binding.relativeMore.setVisibility(View.GONE);
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
                    call = apiService.getDailyDeskCount(month, ""+SessionHandler.getInstance().getInt(getActivity(), AppConstants.TEAM_ID));
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
                        SessionHandler.getInstance().saveBoolean(getActivity(),
                                AppConstants.LOGIN_CHECK,false);
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
                    isGlobalLocationSetUP = true;
                    if (response.code()==200 && response.body().size()>0){
//                        selectedTeamId = response.body().get(0).getTeamId();
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
        binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBackground));
        binding.roomLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBackground));
        binding.parkingLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBackground));
        binding.moreLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBackground));

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
                    List<MeetingListToEditResponse> meetingListToEditList  =new ArrayList<>();

                    for(int i=0; i<meetingListToEditResponseList.size(); i++){
                        System.out.println("meting userId"+meetingListToEditResponseList.get(i).getBookedByUserId()
                                +" : " +SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID));
                        if (meetingListToEditResponseList.get(i).getBookedByUserId()
                                == SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID)
                                && !meetingListToEditResponseList.get(i).getStatus().getBookingType().equalsIgnoreCase("req")){
                            meetingListToEditList.add(meetingListToEditResponseList.get(i));
                        }

                        System.out.println("recycler bala for"+meetingListToEditResponseList.size());
                    }
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
                        callMeetingRoomEditListAdapterBottomSheet(meetingListToEditList,"new");
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
            Call<List<CarParkListToEditResponse>> call = apiService.getCarParkListToEdit(startDate, endDate);
            call.enqueue(new Callback<List<CarParkListToEditResponse>>() {
                @Override
                public void onResponse(Call<List<CarParkListToEditResponse>> call, Response<List<CarParkListToEditResponse>> response) {

                    List<CarParkListToEditResponse> carParkingForEditResponse = response.body();
                    CallCarBookingEditList(carParkingForEditResponse, "5");

                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                }

                @Override
                public void onFailure(Call<List<CarParkListToEditResponse>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getCarParkLocationsList(EditBookingDetails editBookingDetails, String status) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkLocationsModel>> call = apiService.getCarParkLocation();
            call.enqueue(new Callback<List<CarParkLocationsModel>>() {
                @Override
                public void onResponse(Call<List<CarParkLocationsModel>> call, Response<List<CarParkLocationsModel>> response) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    if (response.code()==200 && response.body().size()>0){
                        getParkingSpotList(""+response.body().get(0).getId(),editBookingDetails,status);
                    } else {
                        Toast.makeText(getContext(), "No parking locations Aavilable", Toast.LENGTH_SHORT).show();
                    }

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
                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                    parkingSpotModelList.clear();
                    if(response.code()==200 && response.body()!=null && response.body().size()>0){
                        for(int i=0;i<response.body().size();i++){
                            if(response.body().get(i).getAssignees().size() == 0
                                    && response.body().get(i).isActive()){
                                parkingSpotModelList.add(response.body().get(i));
                            }
                        }
                        boolean checkIsRequest=false;
                        if (parkingSpotModelList!=null && parkingSpotModelList.size()>0){
                            loo :
                            for (int i=0;i<parkingSpotModelList.size();i++){
                                if (editBookingDetails.getParkingSlotId()==parkingSpotModelList.get(i).getId()){
                                    editBookingDetails.setLocationAddress(parkingSpotModelList.get(i).getLocation().getName());
                                    checkIsRequest=true;
                                    break loo;
                                }
                            }
                        }

//                    Toast.makeText(getContext(), "getParkingSpotList "+response.code(), Toast.LENGTH_LONG).show();
                        if (newEdit.equalsIgnoreCase("new"))
                            editBookingUsingBottomSheet(editBookingDetails,3,0,"new");
                        else if (newEdit.equalsIgnoreCase("new_deep_link")){
                            if (checkIsRequest)
                                editBookingUsingBottomSheet(editBookingDetails,3,0,"new_deep_link");
                            else
                                editBookingUsingBottomSheet(editBookingDetails,3,0,"request");
                        }else
                            editBookingUsingBottomSheet(editBookingDetails,3,0,"edit");


                    } else {
                        getCarParkLocationsList(editBookingDetails,"new");
                    }

                }

                @Override
                public void onFailure(Call<List<ParkingSpotModel>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    Toast.makeText(getActivity(), "Api Failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getRoomlist(EditBookingDetails editBookingDetails, String newEditStatus) {
        if (Utils.isNetworkAvailable(getActivity())) {
            System.out.println("ame list vala getroom list enter");

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.userAllowedMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                    userAllowedMeetingResponseListUpdated.clear();
                    userAllowedMeetingResponseList.clear();
//                    userAllowedMeetingResponseList=response.body();
                    userAllowedMeetingResponseFilterList.clear();
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    for(int i=0; i<response.body().size(); i++){
                        if(response.body().get(i).isActive()){
                            userAllowedMeetingResponseList.add(response.body().get(i));
                        }
                    }

                    boolean checkIsRequest=false;
                    if (userAllowedMeetingResponseList!=null && userAllowedMeetingResponseList.size()>0){

                        for (int i=0; i < userAllowedMeetingResponseList.size(); i++){
                            boolean check=false;
                            if (filterAmenitiesList.size()>0)
                                check = false;
                            else
                                check = true;

                            soo:
                            for (int j=0;j<filterAmenitiesList.size();j++){
                                boolean amenityCheck=false;
                                System.out.println("ame list check of"+i+" : "+filterAmenitiesList.get(j));
                                ooo:
                                for(int x=0; x<userAllowedMeetingResponseList.get(i).getAmenities().size(); x++){
                                    if (filterAmenitiesList.get(j) == userAllowedMeetingResponseList.get(i)
                                            .getAmenities().get(x).getId()){
                                        System.out.println("ame list check of true"+
                                                userAllowedMeetingResponseList.get(i).getName());
//                                        userAllowedMeetingResponseList.remove(i);
                                        System.out.println("ame list check of"+i+" : "+filterAmenitiesList.get(j));
                                        amenityCheck=true;
                                    }
                                }
                                if (!amenityCheck) {
                                    System.out.println("ame list check of remove"+
                                            userAllowedMeetingResponseList.get(i).getName());
//                                    userAllowedMeetingResponseFilterList.remove(i);
                                    check=false;
                                    break soo;
                                } else {
                                    check=true;
                                }
                            }
                            if (check){
                                userAllowedMeetingResponseFilterList.add(userAllowedMeetingResponseList.get(i));
                            }
                        }
                        loo:
                        for (int i=0; i< userAllowedMeetingResponseFilterList.size();i++){
                            if (participants <= userAllowedMeetingResponseFilterList.get(i).getNoOfPeople()){
//                                userAllowedMeetingResponseListUpdated.add(userAllowedMeetingResponseFilterList.get(i));
                                userAllowedMeetingResponseListUpdated.add(userAllowedMeetingResponseFilterList.get(i));
                            }

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
                        System.out.println("ame list vala else"+userAllowedMeetingResponseListUpdated.size());
                        if (userAllowedMeetingResponseListUpdated.size()>0)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    userAllowedMeetingResponseListUpdated.get(0).getId(),
                                        0,"new");
                        else if (userAllowedMeetingResponseFilterList.size()>0)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    userAllowedMeetingResponseFilterList.get(0).getId(),
                                        0,"new");
                        else
                            Toast.makeText(getContext(), "No Room avaialble clear Filter conditions", Toast.LENGTH_SHORT).show();
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

        addNew.setText(appKeysPage.getAddNew());
        editClose.setText(appKeysPage.getBack());

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
                if(SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID)>0)
                    getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editBookingDetails,"new");
                else
                    getCarParkLocationsList(editBookingDetails,"new");

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
        meetingListToEditAdapter.notifyDataSetChanged();
        System.out.println("recycler bala"+meetingListToEditResponseList.size());

        addNew.setText(appKeysPage.getAddNew());
        editClose.setText(appKeysPage.getBack());

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
                if (isGlobalLocationSetUP)
                    getAvaliableRoomDetails("4",calSelectedDate,editBookingDetails,"new");
                else
                    getRoomlist(editBookingDetails,"new");

            }
        });

        //getMeetingListToEdit


        bookEditBottomSheet.show();
    }

    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {

        //New...
        chipList.clear();
        if(attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            attendeesListForEdit.clear();
        }

        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponse.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingListToEditResponse.getTo()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(meetingListToEditResponse.getDate()));
        editDeskBookingDetails.setCalId(meetingListToEditResponse.getId());
        editDeskBookingDetails.setMeetingRoomtId(meetingListToEditResponse.getMeetingRoomId());

        //New...
        editDeskBookingDetails.setAttendeesList(meetingListToEditResponse.getAttendeesList());
        editDeskBookingDetails.setExternalAttendeesList(meetingListToEditResponse.getExternalAttendeesList());
        editDeskBookingDetails.setComments(meetingListToEditResponse.getComments());
        editDeskBookingDetails.setSubject(meetingListToEditResponse.getSubject());
        selectedDeskId = meetingListToEditResponse.getMeetingRoomId();

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
                            if (response.body().get(i).getId() == calId
                                    && response.body().get(i).getAmenities()!=null) {
                                for (int j=0;j<response.body().get(i).getAmenities().size();j++){
                                    amenitiesIntList.add(response.body().get(i).getAmenities().get(j).getId());
                                }
                                break goo;
                            }
                        }
                        System.out.println("ame list vala respos"+amenitiesIntList.size());
                        for (int i=0; i<amenitiesIntList.size();i++) {
                            for (int j=0;j<amenitiesList.size();j++) {
                                if (amenitiesIntList.get(i) == amenitiesList.get(j).getId()){
                                    System.out.println("ame list vala"+amenitiesList.get(j).getName());
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
                    bookingForEditResponse = response.body();
                    if(bookingDeskList==null)
                        bookingDeskList= new ArrayList<>();

                    if (!isGlobalLocationSetUP) {
                        bookingDeskList.clear();
                        if(response.body().getTeamDeskAvailabilities()!=null)
                            bookingDeskList = response.body().getTeamDeskAvailabilities();
                    }
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    if (code.equalsIgnoreCase("3"))
                        callBottomSheetToEdit(bookingForEditResponse, code);
                    else{
                        if(SessionHandler.getInstance().getBoolean(getContext(),AppConstants.LOGIN_CHECK))
                            deepLinking();
                        else
                            deeplinkLoginPopUP();
                    }

                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    if(SessionHandler.getInstance().getBoolean(getContext(),AppConstants.LOGIN_CHECK))
                        deepLinking();
                    else
                        deeplinkLoginPopUP();

                }
            });


        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }

    private void deeplinkLoginPopUP() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_validation);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText("Please Login and then scan the QR Code");
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);

//                mContext.startActivityForResult(intent, 123);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    //    Desk Edit List
    private void callBottomSheetToEdit(BookingForEditResponse bookingForEditResponse, String code) {
        String sDate="";
        RecyclerView rvEditList;
        TextView editClose, editDate, bookingName, addNew;
        LinearLayoutManager linearLayoutManager;
        bookingForEditResponseDesk.clear();
        if (isGlobalLocationSetUP && bookingDeskList.size()>0){
            System.out.println(" data in");
            for (int i=0; i < bookingDeskList.size(); i++){
                if(!bookingDeskList.get(i).isBookedByElse()){
                    bookingForEditResponseDesk.add(bookingDeskList.get(i));
                    //System.out.println("check data bala"+bookingForEditResponseDesk.get(i).getDeskCode());
                }
            }
        } else {
            for (int i=0; i<bookingForEditResponse.getTeamDeskAvailabilities().size(); i++){
                if(!bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){
                    bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
                }
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

        addNew.setText(appKeysPage.getAddNew());
        editClose.setText(appKeysPage.getBack());


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
                for (int i=0; i<bookingForEditResponseDesk.size();i++){
                    if (bookingForEditResponse.getUserPreferences().getTeamDeskId()
                            == bookingForEditResponseDesk.get(i).getTeamDeskId()){
                        if (bookingForEditResponse.getBookings().size() > 0){
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto()));

                            editBookingDetails.setEditEndTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto(),2));
                        } else {
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                        }

                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setDeskCode(bookingForEditResponseDesk.get(i).getDeskCode());
                        editBookingDetails.setDesktId(bookingForEditResponseDesk.get(i).getTeamDeskId());
                        editBookingDetails.setDeskStatus(0);
                    } else {
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
                /*
                if(isGlobalLocationSetUP)
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"request");
                else
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"new");
                */
                editBookingDetailsGlobal = editBookingDetails;
                getDeskList("3", calSelectedDate);
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

        //Language
        TextView tv_start=roomBottomSheet.findViewById(R.id.tv_start);
        TextView tv_end=roomBottomSheet.findViewById(R.id.tv_end);
        TextView tv_comment=roomBottomSheet.findViewById(R.id.tv_comment);
        TextView tv_repeat=roomBottomSheet.findViewById(R.id.tv_repeat);
        repeat = roomBottomSheet.findViewById(R.id.repeat);
        TextView continueEditBook=roomBottomSheet.findViewById(R.id.editBookingContinue);
        TextView back=roomBottomSheet.findViewById(R.id.editBookingBack);

        tv_start.setText(appKeysPage.getStart());
        tv_end.setText(appKeysPage.getEnd());
        tv_comment.setText(appKeysPage.getComments());
        tv_repeat.setText(appKeysPage.getRepeat());
        continueEditBook.setText(appKeysPage.getContinue());
        back.setText(appKeysPage.getBack());



        startTime = roomBottomSheet.findViewById(R.id.start_time);
        endTime = roomBottomSheet.findViewById(R.id.end_time);

        deskRoomName=roomBottomSheet.findViewById(R.id.tv_desk_room_name);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        //New...
        locationAddress.setVisibility(View.GONE);

        date=roomBottomSheet.findViewById(R.id.date);
        TextView title=roomBottomSheet.findViewById(R.id.title);
        TextView checkInDate=roomBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=roomBottomSheet.findViewById(R.id.showCheckInDate);

        TextView select=roomBottomSheet.findViewById(R.id.select_desk_room);

        TextView tvComments=roomBottomSheet.findViewById(R.id.tv_comments);
        EditText commentRegistration=roomBottomSheet.findViewById(R.id.ed_registration);
        RelativeLayout repeatBlock=roomBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=roomBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=roomBottomSheet.findViewById(R.id.rl_teams_layout);
        CheckBox teamsCheckBox = roomBottomSheet.findViewById(R.id.teams_check_box);
        RelativeLayout dateBlock = roomBottomSheet.findViewById(R.id.bookingDateBlock);
        LinearLayout statusCheckLayout=roomBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=roomBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=roomBottomSheet.findViewById(R.id.capacity_layout);

        chipGroup = roomBottomSheet.findViewById(R.id.list_item);
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
        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditStartTTime()
        )){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
        }
        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditEndTime()
        )){
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
        }


        if (newEditStatus.equalsIgnoreCase("new_deep_link")){
            select.setVisibility(View.GONE);
            dateBlock.setVisibility(View.VISIBLE);
        } else if (newEditStatus.equalsIgnoreCase("request")){
            select.setVisibility(View.GONE);
            dateBlock.setVisibility(View.GONE);
        } else {
            dateBlock.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }

        if (isGlobalLocationSetUP)
            select.setVisibility(View.VISIBLE);

        if (dskRoomParkStatus == 1) {
            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getRequestedTeamId()!=0)
                    select.setVisibility(View.GONE);
                repeatBlock.setVisibility(View.GONE);
            }else
                repeatBlock.setVisibility(View.VISIBLE);

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
            if (newEditStatus.equalsIgnoreCase("edit")){
                repeatBlock.setVisibility(View.GONE);
            }else
                repeatBlock.setVisibility(View.VISIBLE);

            llDeskLayout.setVisibility(View.VISIBLE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
//            repeatBlock.setVisibility(View.GONE);
            if(teamsCheckBoxStatus)
                teamsBlock.setVisibility(View.VISIBLE);
            else
                teamsBlock.setVisibility(View.GONE);

            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.VISIBLE);
            if (userAllowedMeetingResponseListUpdated.size() > 0){
//                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseListUpdated.get(0).getName());
                selectedDeskId = userAllowedMeetingResponseListUpdated.get(0).getId();
                locationAddress.setText(""+userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getName());
            }
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Meeting Room");
            }
        }else {
            if (newEditStatus.equalsIgnoreCase("edit")){
                repeatBlock.setVisibility(View.GONE);
                commentRegistration.setText(editDeskBookingDetails.getVehicleRegNumber());
            }else
                repeatBlock.setVisibility(View.VISIBLE);

            llDeskLayout.setVisibility(View.VISIBLE);
//            repeatBlock.setVisibility(View.VISIBLE);
            teamsBlock.setVisibility(View.GONE);
            commentBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Registration Number");
            tvComments.setText("Regitration Number");
            if (profileData != null)
                commentRegistration.setText(profileData.getVehicleRegNumber());
            chipGroup.setVisibility(View.GONE);
            capacitylayout.setVisibility(View.GONE);
            if(newEditStatus.equalsIgnoreCase("new") ||newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Parking Slot");
            }
//            System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
            if (parkingSpotModelList.size() > 0 && !newEditStatus.equalsIgnoreCase("edit")){
//                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+parkingSpotModelList.get(0).getCode());
                selectedDeskId = parkingSpotModelList.get(0).getId();
                locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());
            } else {
                select.setVisibility(View.GONE);
                title.setText("Edit Parking Details");
                deskRoomName.setText(""+editDeskBookingDetails.getParkingSlotCode());
                selectedDeskId = editDeskBookingDetails.getParkingSlotId();
                locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());
            }

        }
        toastMessage(getContext(),""+editDeskBookingDetails.getEditStartTTime());
        if (editDeskBookingDetails.getEditStartTTime()!=null){
            startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
            if(!newEditStatus.equalsIgnoreCase("edit") &&
                    Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2)
                startTime.setText(Utils.convert24HrsTO12Hrs(Utils.getCurrentTime()));
        }
        if (editDeskBookingDetails.getEditEndTime()!=null){
            endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
            if(!newEditStatus.equalsIgnoreCase("edit") &&
                    Utils.compareTimeIfCheckInEnable(Utils.convert12HrsTO24Hrs(""+startTime.getText()),
                    editDeskBookingDetails.getEditEndTime()))
                endTime.setText(Utils.convert24HrsTO12Hrs(Utils.addHoursToSelectedTime(Utils.convert24HrsTO12Hrs(""+startTime.getText()), 4)));
        }
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

//                Toast.makeText(context, "fsf"+editDeskBookingDetails.getRequestedTeamDeskId(), Toast.LENGTH_SHORT).show();
                if (editDeskBookingDetails.getDeskStatus() != 1
                        && editDeskBookingDetails.getDeskStatus() != 2
                        && editDeskBookingDetails.getRequestedTeamId() > 0)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                else if (editDeskBookingDetails.getDeskStatus() != 1
                        && editDeskBookingDetails.getDeskStatus() != 2)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
//                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1
                        && editDeskBookingDetails.getRequestedTeamId() > 0)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                else if(editDeskBookingDetails.getDeskStatus() != 1)
                    Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
//                    Utils.popUpTimePicker(getActivity(),endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dskRoomParkStatus==1)
                    repeatBottomSheetDialog("3");
                else if (dskRoomParkStatus==2)
                    repeatBottomSheetDialog("4");
                else
                    repeatBottomSheetDialog("5");
            }
        });
        teamsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editDeskBookingDetails.setTeamsChecked(true);
                }else
                    editDeskBookingDetails.setTeamsChecked(false);
            }
        });
        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                //New...
                if (isVehicleReg) {
                    if (commentRegistration.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(), "Enter Registration Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                */


                if (selectedicon==1 && newEditStatus.equalsIgnoreCase("new"))
                    callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                            startTime, endTime, selectedDeskId,
                            deskRoomName.getText().toString(), false,editDeskBookingDetails.getCalId());
                else if (selectedicon==1) {
                    if (newEditStatus.equalsIgnoreCase("request"))
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), true,editDeskBookingDetails.getCalId());
                    else
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), false,editDeskBookingDetails.getCalId());
                }
                else {
                    if (repeatActvieStatus) {
                        if (dskRoomParkStatus==1)
                            doRepeatDeskBookingForAWeek();
                        else if (dskRoomParkStatus==3)
                            doRepeatCarBookingForAWeek(commentRegistration.getText().toString());

                    } else {
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
                                if (selectedDeskId!=0 && dskRoomParkStatus==1
                                        && selectedDeskId != editDeskBookingDetails.getDesktId()){
//                                    Toast.makeText(context, "ds"+selectedDeskId, Toast.LENGTH_SHORT).show();
                                    jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                                }

                                if (newEditStatus.equalsIgnoreCase("request")){
                                    jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                    jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    jsonChangesObject.addProperty("usageTypeId", "7");
                                    jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
//                                jsonChangesObject.addProperty("typeOfCheckIn", "1");
                                }else if (isGlobalLocationSetUP){
                                    jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                    jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    jsonChangesObject.addProperty("usageTypeId", "7");
                                    jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
                                }else{
                                    if (!newEditStatus.equalsIgnoreCase("edit") && selectedDeskId!=0)
                                        jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                                    else if (selectedDeskId != editDeskBookingDetails.getDesktId())
                                        jsonChangesObject.addProperty("teamDeskId",editDeskBookingDetails.getDesktId());
                                }
                                break;
                            case 2:
                                jsonOuterObject.addProperty("meetingRoomId",editDeskBookingDetails.getMeetingRoomtId());
                                break;
                            case 3:
                                if (selectedDeskId!=0)
                                    jsonOuterObject.addProperty("parkingSlotId",selectedDeskId);
                                else
                                    jsonOuterObject.addProperty("parkingSlotId",editDeskBookingDetails.getParkingSlotId());

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
                            jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
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

                        if (dskRoomParkStatus==3 && isVehicleReg
                                && (commentRegistration.getText().toString().isEmpty()
                                || commentRegistration.getText().toString().equalsIgnoreCase(""))){
                            Toast.makeText(getActivity(), "Enter Registration Number", Toast.LENGTH_SHORT).show();
                        }else {
                            if (jsonChangesObject.size() > 0){
                                editBookingCall(jsonOuterObject,position,dskRoomParkStatus,newEditStatus);
                            }
                        }
                        selectedDeskId=0;
                        roomBottomSheet.dismiss();
                    }
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

    /*private void repeatBottomSheetDialog()
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
                openUntil("");
            }
        });

        tv_unit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                openUntil();
            }
        });


        tv_interval.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Toast.makeText(requireContext(),"onclick==="+type,Toast.LENGTH_LONG).show();
//                openIntervalsDialog(type);
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
    */
    private void openUntil(String code) {


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_until,
                new RelativeLayout(getContext()))));

        TextView titleUntil=bottomSheetDialog.findViewById(R.id.titleUntil);

        ConstraintLayout cl_forever = bottomSheetDialog.findViewById(R.id.cl_forever);
        ConstraintLayout cl_specific = bottomSheetDialog.findViewById(R.id.cl_specific);
        ImageView iv_forever = bottomSheetDialog.findViewById(R.id.iv_forever);
        ImageView iv_specific = bottomSheetDialog.findViewById(R.id.iv_specific);
        android.widget.CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);
        TextView tv_forever=bottomSheetDialog.findViewById(R.id.tv_forever);
        TextView tv_specific = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView editBookingContinue = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView repeatBookContinue=bottomSheetDialog.findViewById(R.id.editBookingContinue);

        titleUntil.setText(appKeysPage.getRepeatUntill());
        tv_specific.setText(appKeysPage.getSpecificDate());

        calendar_view.setVisibility(View.GONE);


        //Get Current Week End Date
        Date date=Utils.getCurrentWeekEndDate();
        //Set Figma format
        tv_forever.setText(Utils.getDateFormatToSetInRepeat(date)+"(end of Week)");


        //System.out.println("LocateDateHere "+binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+" "+ binding.locateEndTime.getText().toString());
        //2022-09-14 15:46 23:59

        //Get Date Difference Between current date and weekend date
        String selectedDate=binding.locateCalendearView.getText().toString();
        enableCurrentWeek=Utils.getDifferenceBetweenTwoDates(selectedDate);

        //System.out.println("enableCurrentWeek "+enableCurrentWeek);

        calendar_view.setMinDate(System.currentTimeMillis() - 1000);
        calendar_view.setMaxDate(System.currentTimeMillis() + enableCurrentWeek * 24 * 60 * 60 * 1000);

        //end of week book
        cl_forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_forever.setVisibility(View.VISIBLE);
                iv_specific.setVisibility(View.GONE);
                calendar_view.setVisibility(View.GONE);

                repeatActvieStatus=true;

                if(code.equals("3")){
                    //DeskBookForWholeWeekFromToday
//                    doRepeatDeskBookingForAWeek();
                }else if(code.equals("4")){
                    //Meeting Room Booking For Whole Week From Today
//                    doRepeatMeetingRoomBookingForWeek();
                }else if(code.equals("5")){
                    //CarBooking For Whole Week From Today
                    //doRepeatCarBookingForAWeek();
                }

                bottomSheetDialog.dismiss();
                repeatBottomSheetDialog.dismiss();
            }
        });

        //specific date clicked
        cl_specific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                iv_forever.setVisibility(View.GONE);
                iv_specific.setVisibility(View.VISIBLE);
                calendar_view.setVisibility(View.VISIBLE);

            }
        });


        //Calendar View
        calendar_view.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView view, int year, int month, int dayOfMonth) {

                repeatActvieStatus=true;

                //Coming WeekendDate
                LocalDate weekEndDate = LocalDate.of( year, month+1, dayOfMonth);

                //Selected Date
                String[] words = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)).split("-");
                int selectedYear=Integer.parseInt(words[0]);
                int selectedMonth=Integer.parseInt(words[1]);
                int selectedDay=Integer.parseInt(words[2]);
                LocalDate currentSelectedDate = LocalDate.of( selectedYear, selectedMonth, selectedDay);

                //Find Difference between 2 date
                Period difference = Period.between(currentSelectedDate,weekEndDate);
                enableCurrentWeek=difference.getDays();


                if(code.equals("3")){
                    //BookForSelectedDaysInAWeek
//                    doRepeatDeskBookingForAWeek();
                }else if(code.equals("4")){

                }else if(code.equals("5")){
                    //BookCarForSelectedDaysInAWeek
                    //doRepeatCarBookingForAWeek();
                }

                bottomSheetDialog.dismiss();
                repeatBottomSheetDialog.dismiss();

            }
        });


        bottomSheetDialog.show();
    }

/*
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
*/

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

    private void callDeskListBottomSheetDialog() {
        for (int i=0; i<activeTeamsList.size(); i++) {
            if (selectedTeamId==activeTeamsList.get(i).getId()) {
                selectedTeamName = activeTeamsList.get(i).getName();
                selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
            }
        }

        System.out.println("chec stats " + selectedTeamAutoApproveStatus);
        deskListBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        deskListBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk_new,
                new RelativeLayout(getContext()))));

        TextView bsRepeatBack, selectDesk;
        rvDeskRecycler= deskListBottomSheet.findViewById(R.id.desk_list_select_recycler);
        selectDesk= deskListBottomSheet.findViewById(R.id.sheet_name);
        tvTeamName= deskListBottomSheet.findViewById(R.id.tv_team_name);
        bsRepeatBack=deskListBottomSheet.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
        if (selectedicon == 2){
            selectDesk.setText("Select Parking");
            parkingSpotListRecyclerAdapter =new ParkingSpotListRecyclerAdapter(getContext(),
                    this,getActivity(),parkingSpotModelList,getContext(),deskListBottomSheet);
            rvDeskRecycler.setAdapter(parkingSpotListRecyclerAdapter);
        }else if (selectedicon==1){
            selectDesk.setText("Select Meeting Room");
            roomListRecyclerAdapter =new RoomListRecyclerAdapter(getContext(),
                    this,getActivity(),userAllowedMeetingResponseListUpdated,getContext(),
                    deskListBottomSheet);
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }else {
            selectDesk.setText("Book a Workspace");
            tvTeamName.setText(selectedTeamName);

            /*
            deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,
                    getActivity(),bookingForEditResponseDesk,getContext(),deskListBottomSheet);
            */

            newdeskListRecyclerAdapter =new NewDeskListRecyclerAdapter(getContext(),this,
                    getActivity(),bookingDeskList,this,deskListBottomSheet);
            rvDeskRecycler.setAdapter(newdeskListRecyclerAdapter);

        }

        tvTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActiveTeamsBottomSheet();
            }
        });

        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deskListBottomSheet.dismiss();
            }
        });

        deskListBottomSheet.show();
    }

    private void callActiveTeamsBottomSheet() {
        activeTeamsBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        activeTeamsBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_active_teams,
                new RelativeLayout(getContext()))));

        TextView bsRepeatBack, selectDesk;
        rvActiveTeams= activeTeamsBottomSheet.findViewById(R.id.desk_list_select_recycler);
        selectDesk= activeTeamsBottomSheet.findViewById(R.id.sheet_name);
        bsRepeatBack=activeTeamsBottomSheet.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvActiveTeams.setLayoutManager(linearLayoutManager);
        rvActiveTeams.setHasFixedSize(true);
        selectDesk.setText("Book from another team");

        activeTeamsAdapter =new ActiveTeamsAdapter(getContext(),this,
                    getActivity(),activeTeamsList,this,activeTeamsBottomSheet);
        rvActiveTeams.setAdapter(activeTeamsAdapter);



        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTeamsBottomSheet.dismiss();
            }
        });

        activeTeamsBottomSheet.show();
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
            roomListRecyclerAdapter =new RoomListRecyclerAdapter(getContext(),this,getActivity(),userAllowedMeetingResponseListUpdated,getContext(),bottomSheetDialog);
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }else {
            selectDesk.setText("Select Desk");
            deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,
                    getActivity(),bookingForEditResponseDesk,getContext(),bottomSheetDialog);
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
                    String resultString="";
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
                            if (response.body().getResultCode().toString().equals("INVALID_FROM")) {
                                resultString = "Invalid booking start time";
                            } else if (response.body().getResultCode().toString().equals("INVALID_TO")) {
                                resultString = "Invalid booking end time";
                            } else if (response.body().getResultCode().toString().equals("INVALID_TIMEZONE_ID")) {
                                resultString = "Invalid timezone";
                            } else if (response.body().getResultCode().toString().equals("INVALID_TIMEPERIOD")) {
                                resultString = "Invalid timeperiod";
                            } else if (response.body().getResultCode().toString().equals("USER_TIME_OVERLAP")) {
                                resultString = "Time overlaps with another booking";
                            } else if(response.body().getResultCode().toString().equals("COVID_SYMPTOMS")){
                                resultString = "COVID_SYMPTOMS";
                            }
                            Utils.showCustomAlertDialog(getActivity(), resultString);
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
        if (bookings.getStatus().getTimeStatus().equalsIgnoreCase("ongoing"))
            editDeskBookingDetails.setDeskStatus(2);
        else
            editDeskBookingDetails.setDeskStatus(0);
        editDeskBookingDetails.setDesktId(bookings.getTeamDeskId());
        editDeskBookingDetails.setTimeStatus(bookings.getStatus().getTimeStatus());

        if (bookings.getRequestedTeamDeskId()!=null)
            editDeskBookingDetails.setRequestedTeamDeskId(bookings.getRequestedTeamDeskId());
        else
            editDeskBookingDetails.setRequestedTeamDeskId(0);
        if (bookings.getRequestedTeamId()!=null)
            editDeskBookingDetails.setRequestedTeamId(bookings.getRequestedTeamId());
        else
            editDeskBookingDetails.setRequestedTeamId(0);

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
    private void callMeetingRoomBookingBottomSheet(EditBookingDetails editDeskBookingDetails, TextView startTime, TextView endTime,
                                                   int meetingRoomId, String meetingRoomName, boolean isRequest,int id) {

        /*//New...
        chipList.clear();
        if(attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            attendeesListForEdit.clear();
        }*/

        //Show Amenities in Meeting Booking
        //Amenities Block
        List<String> amenitiesList = new ArrayList<>();
        for (int i = 0; i < userAllowedMeetingResponseList.size(); i++) {

            if (meetingRoomId == userAllowedMeetingResponseList.get(i).getId()) {

                for (int j = 0; j < userAllowedMeetingResponseList.get(i).getAmenities().size(); j++) {
                    System.out.println("MeetingAmenities " + userAllowedMeetingResponseList.get(i).getAmenities().get(j).getId());

                    for (int k = 0; k < amenitiesListToShowInMeetingRoomList.size(); k++) {

                        if (userAllowedMeetingResponseList.get(i).getAmenities().get(j).getId() == amenitiesListToShowInMeetingRoomList.get(k).getId()) {
                            amenitiesList.add(amenitiesListToShowInMeetingRoomList.get(k).getName());
                            System.out.println("TotalAmenitiesForThisRoom " + amenitiesListToShowInMeetingRoomList.get(k).getName());

                        }

                    }
                }


            }

        }

        TextView startRoomTime, endTRoomime, editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription, roomTitle;
        EditText etParticipants, externalAttendees, etSubject, etComments;
        ChipGroup chipGroup, externalAttendeesChipGroup;

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

        chipGroup = bottomSheetDialog.findViewById(R.id.meetingAmenitiesChipGroup);

        externalAttendees = bottomSheetDialog.findViewById(R.id.externalAttendees);
        externalAttendeesChipGroup = bottomSheetDialog.findViewById(R.id.externalAttendeesChipGroup);


        //Set All Amenities Here
        for (int i = 0; i < amenitiesList.size(); i++) {

            System.out.println("RoomAmenitiesList " + amenitiesList.get(i));
            Chip chip = new Chip(getContext());
            chip.setText(amenitiesList.get(i));
            chip.setCheckable(false);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }

        //Language
        editRoomBookingContinue.setText(appKeysPage.getContinue());
        editRoomBookingBack.setText(appKeysPage.getBack());
        etComments.setHint(appKeysPage.getComments());
        etSubject.setHint(meetingRoomsLanguage.getSubject());

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

        //External Participants
        List<String> externalAttendeesEmail = new ArrayList<>();

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
                    if (!repeatActvieStatus)
                        doMeetingRoomBooking(meetingRoomId,
                                startTime.getText().toString(),
                                endTime.getText().toString(), subject, comment, isRequest, editDeskBookingDetails.isTeamsChecked(),externalAttendeesEmail,id);
                    else
                        doRepeatMeetingRoomBookingForWeek(editDeskBookingDetails.isTeamsChecked());

                } else {
                    Toast.makeText(getContext(), "Please Enter All Details", Toast.LENGTH_LONG).show();
                }

            }
        });





        //Get AttendeeList To Edit
        if (editDeskBookingDetails.getAttendeesList()!=null){
            attendeesListForEdit=editDeskBookingDetails.getAttendeesList();
        }


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);




        try {

            if (editDeskBookingDetails.getExternalAttendeesList()!=null &&
                    editDeskBookingDetails.getExternalAttendeesList().size()>0){
                externalAttendeesChipGroup.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < editDeskBookingDetails.getExternalAttendeesList().size(); i++) {
                System.out.println("ExternalAttendeesListInLoop " + editDeskBookingDetails.getExternalAttendeesList().get(i));

                Chip chip = new Chip(getContext());
                chip.setText(editDeskBookingDetails.getExternalAttendeesList().get(i));
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setCloseIconVisible(true);
                externalAttendeesChipGroup.addView(chip);

                //Add In List
                externalAttendeesEmail.add(editDeskBookingDetails.getExternalAttendeesList().get(i));


                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (externalAttendeesEmail != null) {

                            for (int i = 0; i < externalAttendeesEmail.size(); i++) {

                                if (chip.getText().toString().contains(externalAttendeesEmail.get(i))) {
                                    externalAttendeesEmail.remove(i);
                                    externalAttendeesChipGroup.removeView(chip);

                                }

                            }

                        }

                    }
                });
            }


        }catch (Exception e){

        }


        try {
            for (int i = 0; i < attendeesListForEdit.size(); i++) {
                //System.out.println("ExternalAttendeesListInLoop " + editDeskBookingDetails.getExternalAttendeesList().get(i));

                ParticipantDetsilResponse participantDetsilResponse = new ParticipantDetsilResponse(editDeskBookingDetails.getAttendeesList().get(i).getId(), editDeskBookingDetails.getAttendeesList().get(i).getFirstName(), editDeskBookingDetails.getAttendeesList().get(i).getLastName(), editDeskBookingDetails.getAttendeesList().get(i).getFullName(), editDeskBookingDetails.getAttendeesList().get(i).getEmail(), editDeskBookingDetails.getAttendeesList().get(i).isActive());
                chipList.add(participantDetsilResponse);

                Chip chip = new Chip(getContext());
                chip.setText(attendeesListForEdit.get(i).getEmail());
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setCloseIconVisible(true);
                participantChipGroup.addView(chip);

                //Add In List
                //chipList.add(attendeesListForEdit.get(i).getEmail());


                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (chipList != null) {
                            for (int i = 0; i < chipList.size(); i++) {

                                if (chip.getText().toString().contains(chipList.get(i).getEmail())) {
                                    chipList.remove(chipList.get(i));
                                    participantChipGroup.removeView(chip);
                                    break;
                                }

                            }
                        }

                        //System.out.println("RemoveChipGroupName"+chip.getText().toString());



                    }
                });
            }
        }catch (Exception e){

        }


        etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etParticipants.setText("");
                rvParticipant.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            etSubject.setText(editDeskBookingDetails.getSubject());
            etComments.setText(editDeskBookingDetails.getComments());
        }catch (Exception e){

        }

        externalAttendees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                etParticipants.setText("");
                rvParticipant.setVisibility(View.GONE);

                if (s.toString().contains(" ")) {

                    Chip chip = new Chip(getContext());
                    chip.setText(s.toString());
                    chip.setCheckable(false);
                    chip.setClickable(false);
                    chip.setCloseIconVisible(true);
                    externalAttendeesChipGroup.setVisibility(View.VISIBLE);
                    externalAttendeesChipGroup.addView(chip);

                    //Add In List
                    externalAttendeesEmail.add(s.toString());

                    externalAttendees.clearFocus();
                    externalAttendees.setText("");


                    chip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (externalAttendeesEmail != null) {

                                for (int i = 0; i < externalAttendeesEmail.size(); i++) {

                                    if (chip.getText().toString().contains(externalAttendeesEmail.get(i))) {
                                        externalAttendeesEmail.remove(i);
                                        externalAttendeesChipGroup.removeView(chip);

                                    }

                                }

                            }

                        }
                    });

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        bottomSheetDialog.show();

    }

    private void sendEnteredPartipantLetterToServer(String participantLetter, RecyclerView rvParticipant) {

//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ParticipantDetsilResponse>> call = apiService.getParticipantDetails(participantLetter, 1);
        call.enqueue(new Callback<List<ParticipantDetsilResponse>>() {
            @Override
            public void onResponse(Call<List<ParticipantDetsilResponse>> call, Response<List<ParticipantDetsilResponse>> response) {


                List<ParticipantDetsilResponse> participantDetsilResponseList = response.body();

//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                //dialog.dismiss();
                if (participantDetsilResponseList != null) {

                    //System.out.println("ParticipantNameList" + participantDetsilResponseList.get(0).getFirstName());

                    showParticipantNameInRecyclerView(participantDetsilResponseList, rvParticipant);

                }

            }

            @Override
            public void onFailure(Call<List<ParticipantDetsilResponse>> call, Throwable t) {
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                //dialog.dismiss();
            }
        });
    }

    private void showParticipantNameInRecyclerView(List<ParticipantDetsilResponse> participantDetsilResponseList, RecyclerView rvParticipant) {


        rvParticipant.setVisibility(View.VISIBLE);
        ParticipantNameShowAdapter participantNameShowAdapter = new ParticipantNameShowAdapter(getContext(), participantDetsilResponseList, this,rvParticipant);
        rvParticipant.setAdapter(participantNameShowAdapter);
    }

    private void doMeetingRoomBooking(int meetingRoomId,
                                      String startRoomTime,
                                      String endRoomTime,
                                      String subject,
                                      String comment,
                                      boolean isRequest,
                                      boolean isTeamsChecked,List<String> externalAttendeesEmail,int id) {

//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        dialog= ProgressDialog.showProgressBar(getContext());
        MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
        if(teamsCheckBoxStatus && isTeamsChecked)
            meetingRoomRequest.setMsTeams(true);
        else
            meetingRoomRequest.setMsTeams(false);
        meetingRoomRequest.setHandleRecurring(false);
        meetingRoomRequest.setOnlineMeeting(false);

        MeetingRoomRequest.Changeset m = meetingRoomRequest.new Changeset();
        m.setId(id);
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
         //End


        //New...

            /*if (chipList != null) {
                //MeetingRoomEditRequest.Changesets.Changes.Attendees attendees = changes.new Attendees();
                for (int i = 0; i < chipList.size(); i++) {
                    System.out.println("EditedAndAddedParticipants "+chipList.get(i).getId());
                    attendeesList.add(chipList.get(i).getId());
                }
            }*/

        if (attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            //Edit flow....
            List<Integer> addedList= LogicHandler.getNewlyAdded(attendeesListForEdit,chipList);
            System.out.println("NewellyAddedParticipant "+addedList);

            if(addedList!=null && addedList.size()>0){

                for (int i = 0; i <addedList.size() ; i++) {
                    attendeesList.add(addedList.get(i));
                }

            }

            List<Integer> removedList=LogicHandler.getRemoved(attendeesListForEdit,chipList);
            System.out.println("RemovedParticipant "+removedList);
            if(removedList!=null && removedList.size()>0){
                for (int i = 0; i <removedList.size() ; i++) {
                    attendeesList.add(removedList.get(i));
                }
            }
        }else {
            //New Booking flow...
            if (chipList != null) {
                for (int i = 0; i < chipList.size(); i++) {
                    attendeesList.add(chipList.get(i).getId());
                }

            }
        }



        changes.setAttendees(attendeesList);


        //List<String> externalAttendeesList = new ArrayList<>();
        changes.setExternalAttendees(externalAttendeesEmail);

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
    public void onParticipantSelect(ParticipantDetsilResponse participantDetsilResponse, RecyclerView recyclerView) {

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
    public void onSelectRoom(int deskId, String deskName, String location, List<UserAllowedMeetingResponse.Amenity> amenityLIst) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
        locationAddress.setText(location);
        List<String> amenityStringList= new ArrayList<>();
        if(amenityLIst !=null){
            for (int i=0; i<amenityLIst.size(); i++){
                for (int j=0; j<amenitiesList.size();j++){
                    if (amenityLIst.get(i).getId() == amenitiesList.get(j).getId()){
                        amenityStringList.add(amenitiesList.get(j).getName());
                        break;
                    }
                }
            }
        }
        if (amenityStringList.size()>0){
            chipGroup.removeAllViews();
            for (int i=0; i<amenityStringList.size(); i++){
                Chip chip = new Chip(getContext());
                chip.setId(i);
                chip.setText(""+amenityStringList.get(i));
                chip.setChipBackgroundColorResource(R.color.figmaGrey);
                chip.setCloseIconVisible(false);
                chip.setTextColor(getContext().getResources().getColor(R.color.white));
                chipGroup.addView(chip);
            }
        }

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
        bsGeneralSearch = bottomSheetDialog.findViewById(R.id.bsGeneralSearch);

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
                if(floorAdapter.getSelectedPositionCheck()>=0){
                    floorSearchStatus=false;
                    int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
                    boolean floorSelectedStatus = SessionHandler.getInstance().getBoolean(getContext(), AppConstants.FLOOR_SELECTED_STATUS);
                    canvasss = 1;
                    //removes desk in layout
//                    binding.firstLayout.removeAllViews();

                    initLoadFloorDetails(canvasss);
                    getAvaliableDeskDetails("3",calSelectedDate);
                    bottomSheetDialog.dismiss();
                }else {
                    Utils.toastMessage(getContext(),"Please Select Floor");
                }

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

        bsGeneralSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!floorSearchStatus) {
                    showCountryAdapter.getFilter().filter(s.toString());
                } else {
                    floorAdapter.getFilter().filter(s.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

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

                System.out.println("InsideFloorDetails bala"+locateCountryResposeList.size());

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

        System.out.println("locate data check"+locateCountryResposeList.size());

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
                if(fullPathLocation == null) {
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

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(String code, String date) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        String toDate = date + "T00:00:00Z";
        String fromTime = date
                + "T00:00.000Z";
        String toTime = date+ "T23:59:00Z";

//        String toDate = date + "T00:00:00Z";
//        String fromTime = date + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
//        String toTime = date + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";

        //System.out.println("DateAndStatTimeAndEndTime"+toDate+" "+fromTime+" "+toTime);

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.getAvaliableDeskDetailsForDeskList(parentId, toDate, fromTime, toTime);

        call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
            @Override
            public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                bookingDeskList.clear();
                bookingDeskList = response.body();

            }
            @Override
            public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {
                System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
*/
    private void getDeskList(String code,String date) {
        if (Utils.isNetworkAvailable(getContext())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.
                    getTeamDeskAvailability(
                    selectedTeamId,
                    date,
                    date);

            call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
                @Override
                public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                    bookingDeskList.clear();
                    bookingDeskList = response.body();
//                    System.out.println("Selecrt id"+selectedTeamId + bookingDeskList.get(0).getDeskCode());
                    callDeskListBottomSheetDialog();

                }

                @Override
                public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {
//                    deepLinking();

                }
            });


        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }

    }

    private void getAvaliableDeskDetails(String code, String date) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            String toDate = Utils.getCurrentDate();
            String fromTime = "2022-09-23T00:00:00Z";
            String toTime = "2022-09-23T23:59:00Z";

            int parentId = SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);
            Call<BookingForEditResponse> call = apiService.getAvaliableDeskDetailsForDeskList(parentId,
                    toDate,
                    fromTime,
                    toTime);

            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    if(response.code()==200){
                        bookingDeskList.clear();
                        if(response.body().getTeamDeskAvailability() != null)
                            bookingDeskList = response.body().getTeamDeskAvailability();
                        if(bookingDeskList!=null && bookingDeskList.size() > 0)
                            selectedTeamId = bookingDeskList.get(0).getTeamId();
                    }

                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "fa"+bookingDeskList.size(), Toast.LENGTH_SHORT).show();
//                    getAddEditDesk("3",Utils.getISO8601format(date));

                    System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });


    }
    private void getAvaliableRoomDetails(String code, String date, EditBookingDetails editBookingDetails,String newEditStatus) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        System.out.println("check cala"+horizontalCalendar.getSelectedDate().getTime());

            String toDate = date;
            String fromTime = "2022-09-23T00:00:00Z";
            String toTime = "2022-09-23T23:59:00Z";

            int parentId = SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);

            Call<RoomListResponse> call = apiService.getAvaliableRoomDetailsForRoomList(parentId,
                    toDate,
                    fromTime,
                    toTime);

            call.enqueue(new Callback<RoomListResponse>() {
                @Override
                public void onResponse(Call<RoomListResponse> call, Response<RoomListResponse> response) {
                    userAllowedMeetingResponseListUpdated.clear();
                    userAllowedMeetingResponseList = response.body().getMeetingResponses();

                    for (int i=0; i < response.body().getMeetingResponses().size(); i++){
                        if (response.body().getMeetingResponses().get(i).isActive()){
                            userAllowedMeetingResponseList.add(response.body().getMeetingResponses().get(i));
                        }
                    }

//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    boolean checkIsRequest=false;
                    if (userAllowedMeetingResponseList!=null && userAllowedMeetingResponseList.size()>0){

                        for (int i=0; i < userAllowedMeetingResponseList.size(); i++){
                            boolean amenityCheck=false;
                            soo:
                            for (int j=0;j<filterAmenitiesList.size();j++){
                                for(int x=0;x<userAllowedMeetingResponseList.get(i).getAmenities().size();x++){
                                    if (filterAmenitiesList.get(j) == userAllowedMeetingResponseList.get(i)
                                            .getAmenities().get(x).getId()){
                                        amenityCheck=true;
                                    }
                                }
                                if (!amenityCheck) {
                                    userAllowedMeetingResponseList.remove(i);
                                    break soo;
                                }
                            }

//                            System.out.println("chek dat no of people"+userAllowedMeetingResponseList.get(i).getNoOfPeople());

                        }
                        loo:
                        for (int i=0; i<userAllowedMeetingResponseList.size();i++){
                            if (participants <= userAllowedMeetingResponseList.get(i).getNoOfPeople()){
                                System.out.println("chek dat in loop"+userAllowedMeetingResponseList.get(i).getName());

                                userAllowedMeetingResponseListUpdated.add(userAllowedMeetingResponseList.get(i));
                            }

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
                        System.out.println("ame list vala else size"+userAllowedMeetingResponseListUpdated.size());
                        if (userAllowedMeetingResponseListUpdated.size()>0 && checkIsRequest)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    userAllowedMeetingResponseListUpdated.get(0).getId(),
                                    0,"request");
                        else if (userAllowedMeetingResponseListUpdated.size()>0 && !checkIsRequest)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    userAllowedMeetingResponseListUpdated.get(0).getId(),
                                    0,"request");
                        else
                            Toast.makeText(getContext(), "Please Clear Fiter", Toast.LENGTH_SHORT).show();
//                        editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
                    }

                }

                @Override
                public void onFailure(Call<RoomListResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "fa"+bookingDeskList.size(), Toast.LENGTH_SHORT).show();
//                    getAddEditDesk("3",Utils.getISO8601format(date));

                    System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });


    }

    @Override
    public void onSelect(LocateCountryRespose locateCountryRespose, String identifier) {

        switch (identifier) {
            case "COUNTRY":
                floorSearchStatus=false;

                state.setText("City");
                stateId = locateCountryRespose.getLocateCountryId();
                SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME, locateCountryRespose.getName());
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());
                break;

            case "STATE":
                floorSearchStatus=false;

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
                floorSearchStatus=true;

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
// Repeat Module
    private void repeatBottomSheetDialog(String code) {

        repeatBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        repeatBottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat_new,
                new RelativeLayout(getContext()))));

        //Language
        TextView titleRepeat=repeatBottomSheetDialog.findViewById(R.id.titleRepeat);
        TextView tv_none=repeatBottomSheetDialog.findViewById(R.id.tv_none);
        TextView tv_daily=repeatBottomSheetDialog.findViewById(R.id.tv_daily);
        TextView editBookingBack = repeatBottomSheetDialog.findViewById(R.id.editBookingBack);

        titleRepeat.setText(appKeysPage.getRepeat());
        tv_none.setText(appKeysPage.getNone());
        tv_daily.setText(appKeysPage.getDaily());
        editBookingBack.setText(appKeysPage.getBack());

        ConstraintLayout cl_daily_layout = repeatBottomSheetDialog.findViewById(R.id.cl_daily_layout);
        ConstraintLayout cl_weekly_layout = repeatBottomSheetDialog.findViewById(R.id.cl_weekly_layout);
        //None Block
        ConstraintLayout cl_none = repeatBottomSheetDialog.findViewById(R.id.cl_none);
        //Daily Block
        ConstraintLayout cl_daily = repeatBottomSheetDialog.findViewById(R.id.cl_daily);
        ConstraintLayout cl_weekly = repeatBottomSheetDialog.findViewById(R.id.cl_weekly);
        ConstraintLayout cl_monthly = repeatBottomSheetDialog.findViewById(R.id.cl_monthly);
        ConstraintLayout cl_yearly = repeatBottomSheetDialog.findViewById(R.id.cl_yearly);
        ImageView iv_none = repeatBottomSheetDialog.findViewById(R.id.iv_none);
        ImageView iv_daily = repeatBottomSheetDialog.findViewById(R.id.iv_daily);
        ImageView iv_weekly = repeatBottomSheetDialog.findViewById(R.id.iv_weekly);
        ImageView iv_monthly = repeatBottomSheetDialog.findViewById(R.id.iv_monthly);
        ImageView iv_yearly = repeatBottomSheetDialog.findViewById(R.id.iv_yearly);

        TextView editBookingContinue = repeatBottomSheetDialog.findViewById(R.id.editBookingContinue);
        TextView tv_repeat = repeatBottomSheetDialog.findViewById(R.id.tv_repeat);

        TextView tv_interval = repeatBottomSheetDialog.findViewById(R.id.tv_interval);
        TextView tv_until = repeatBottomSheetDialog.findViewById(R.id.tv_until);
        TextView tv_interval_weekly = repeatBottomSheetDialog.findViewById(R.id.tv_interval_weekly);
        TextView tv_day = repeatBottomSheetDialog.findViewById(R.id.tv_day);
        TextView tv_unit = repeatBottomSheetDialog.findViewById(R.id.tv_unit);

        TextView tv_until_txt=repeatBottomSheetDialog.findViewById(R.id.tv_until_txt);
        TextView tv_interval_txt=repeatBottomSheetDialog.findViewById(R.id.tv_interval_txt);



        //None Block Clicked
        cl_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repeatActvieStatus = false;
                //repeat.setText("None");

                repeat.setText(appKeysPage.getNone());

                type = "none";
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

        //Daily Block Clicked
        cl_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //repeat.setText("Daily");

                repeat.setText(appKeysPage.getDaily());

                type = "daily";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.VISIBLE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                //Get Current Week End Date
                Date date=Utils.getCurrentWeekEndDate();
                //Set Figma format
                tv_until.setText(Utils.getDateFormatToSetInRepeat(date)+"(end of Week)");

                cl_daily_layout.setVisibility(View.VISIBLE);
                tv_repeat.setVisibility(View.VISIBLE);
                cl_weekly_layout.setVisibility(View.GONE);

                tv_interval_txt.setText(appKeysPage.getInterval());
                tv_until_txt.setText(appKeysPage.getUntil());
            }
        });

        //Until Block Clicked
        tv_until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUntil(code);
            }
        });


        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatBottomSheetDialog.dismiss();
            }
        });

        tv_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeeks();
            }
        });



        tv_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openUntil();
            }
        });


        tv_interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(requireContext(),"onclick==="+type,Toast.LENGTH_LONG).show();
                //openIntervalsDialog(type);
            }
        });

        tv_interval_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(requireContext(),"onclick==="+type,Toast.LENGTH_LONG).show();
                openIntervalsDialog(type);
            }
        });



        cl_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "weekly";
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
        cl_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "monthly";
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

        cl_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "yearly";
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

        repeatBottomSheetDialog.show();

    }

    /*private void doRepeatMeetingRoomBookingForWeek() {

        String selectedDate=binding.locateCalendearView.getText().toString();
        List<String> dateList=Utils.getCurrentWeekDateList(selectedDate,enableCurrentWeek);

        MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
        meetingRoomRequest.setMsTeams(false);
        meetingRoomRequest.setHandleRecurring(false);
        meetingRoomRequest.setOnlineMeeting(false);

        List<MeetingRoomRequest.Changeset> changesetList = new ArrayList<>();

        for (int i = 0; i <dateList.size() ; i++) {

            MeetingRoomRequest.Changeset m = meetingRoomRequest.new Changeset();
            m.setId(0);
            m.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            MeetingRoomRequest.Changeset.Changes changes = m.new Changes();
            changes.setFrom(getCurrentDate() + "" + "T" + startRoomTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setMyto(getCurrentDate() + "" + "T" + endTRoomime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setComments("Comment");
            changes.setSubject("subject");
            boolean isRequest=false;
            changes.setRequest(isRequest);

            m.setChanges(changes);

            changesetList.add(m);

            List<Integer> attendeesList = new ArrayList<>();


            //Newly Participant Added
            if (chipList != null) {
                for (int j = 0; j < chipList.size(); i++) {
                    attendeesList.add(chipList.get(j).getId());
                }

            } //End

            changes.setAttendees(attendeesList);

            List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList = new ArrayList<>();
            changes.setExternalAttendees(externalAttendeesList);

        }

        meetingRoomRequest.setChangesets(changesetList);

        List<MeetingRoomRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        meetingRoomRequest.setDeletedIds(deleteIdsList);


        System.out.println("RepeatMeetingRoom "+meetingRoomRequest);

        if (Utils.isNetworkAvailable(getActivity())) {
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call=apiService.doRepeatMeetingRoomBooking(meetingRoomRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    locateResponseHandler(response,getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }
    */
    private void locateResponseHandler(Response<BaseResponse> response, String string) {

        String resultString = "";

        if (response.code() == 200) {
            if (response.body().getResultCode() != null && response.body().getResultCode().equalsIgnoreCase("ok")) {
                openCheckoutDialog(string,3);
//                callInitView();
            } else {

                if (response.body().getResultCode().toString().equals("INVALID_FROM")) {
                    resultString = "Invalid booking start time";
                } else if (response.body().getResultCode().toString().equals("INVALID_TO")) {
                    resultString = "Invalid booking end time";
                } else if (response.body().getResultCode().toString().equals("INVALID_TIMEZONE_ID")) {
                    resultString = "Invalid timezone";
                } else if (response.body().getResultCode().toString().equals("INVALID_TIMEPERIOD")) {
                    resultString = "Invalid timeperiod";
                }else if(response.body().getResultCode().toString().equals("USER_TIME_OVERLAP")){
                    resultString = "Time overlaps with another booking";
                }
                //Utils.showCustomAlertDialog(getActivity(), "Booking Not Updated " + resultString);
                Utils.showCustomAlertDialog(getActivity(), resultString);
            }
        } else if (response.code() == 500) {
            Utils.showCustomAlertDialog(getActivity(), "500 Response");
        } else if (response.code() == 401) {
            Utils.showCustomTokenExpiredDialog(getActivity(), "401 Error Response");
        } else {
            Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
        }


    }
    private void doRepeatDeskBookingForAWeek() {

        String selectedDate=binding.locateCalendearView.getText().toString();
        System.out.println("Seelcteddate "+selectedDate+" "+enableCurrentWeek);
        List<String> dateList=Utils.getCurrentWeekDateList(selectedDate,enableCurrentWeek);

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        List<LocateBookingRequest.ChangeSets> changeSetsList= new ArrayList<>();


        for (int i = 0; i <dateList.size() ; i++) {
            //System.out.println("AddedDateList "+dateList.get(i));

            LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();

            changeSets.setChangeSetId(0);
            //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
            changeSets.setChangeSetDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(2);

            changes.setFrom(getCurrentDate() + "" + "T" + startTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + endTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTimeZoneId("India Standard Time");
//            changes.setTeamDeskId(teamDeskIdForBooking);
            changes.setTeamDeskId(selectedDeskId);
            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            changeSetsList.add(changeSets);


        }
        locateBookingRequest.setChangeSetsList(changeSetsList);
        List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("RepeatModuleRequestData "+locateBookingRequest);

        if (Utils.isNetworkAvailable(getActivity())) {
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            dialog = ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call=apiService.doRepeatBookingForWeek(locateBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    locateResponseHandler(response,getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                }
            });

        }else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }
    private void doRepeatCarBookingForAWeek(String registrationNumber) {
        String selectedDate=binding.locateCalendearView.getText().toString();
        List<String> dateList=Utils.getCurrentWeekDateList(selectedDate,enableCurrentWeek);

        LocateCarParkBookingRequest locateCarParkBookingRequest = new LocateCarParkBookingRequest();
        locateCarParkBookingRequest.setParkingSlotId(selectedDeskId);

        List<LocateCarParkBookingRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();

        for (int i = 0; i <dateList.size() ; i++) {

            LocateCarParkBookingRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkBookingRequest.new CarParkingChangeSets();
            carParkingChangeSets.setId(0);
            carParkingChangeSets.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateCarParkBookingRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
            carParkingChanges.setFrom(getCurrentDate() + "" + "T" + startTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            carParkingChanges.setTo(getCurrentDate() + "" + "T" + endTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
//            carParkingChanges.setComments(etComment.getText().toString());
            carParkingChanges.setBookedForUser(SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID));
            carParkingChanges.setVehicleRegNumber(registrationNumber);

            carParkingChangeSets.setCarParkingChanges(carParkingChanges);

            carParkingChangeSetsList.add(carParkingChangeSets);


        }

        locateCarParkBookingRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);
        List<LocateCarParkBookingRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
        locateCarParkBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("RepeatModuleCarRequestData "+locateCarParkBookingRequest);

        if (Utils.isNetworkAvailable(getActivity())) {
            dialog = ProgressDialog.showProgressBar(getContext());
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call=apiService.doRepeatCarParkBooking(locateCarParkBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    locateResponseHandler(response,getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                }
            });

        }else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }
    //check filter
    private void getLocateAmenitiesFilterData(boolean calledFromFilter) {
        if (Utils.isNetworkAvailable(getContext())) {
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            dialog = ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<AmenitiesResponse>> call = apiService.getAmenities();
            call.enqueue(new Callback<List<AmenitiesResponse>>() {
                @Override
                public void onResponse(Call<List<AmenitiesResponse>> call, Response<List<AmenitiesResponse>> response) {


                    //List<AmenitiesResponse> amenitiesResponseList=response.body();
                    List<AmenitiesResponse> amenitiesResponseList=response.body();
                    amenitiesListToShowInMeetingRoomList=amenitiesResponseList;
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    //If true which is called from filter so call bottomsheet
                    if(calledFromFilter){
                        meetingAmenityStatusList.clear();
                        callLocateFilterBottomSheet(amenitiesResponseList);
                    }

                }

                @Override
                public void onFailure(Call<List<AmenitiesResponse>> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                }
            });

        }else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }
    /*private void callLocateFilterBottomSheet(List<AmenitiesResponse> amenitiesResponseList) {

        RecyclerView locateFilterMainRV;
        ValuesPOJO valuesPOJO;
        ArrayList<DataModel> mList;
        ItemAdapter adapter;


        TextView locateFilterCancel, locateFilterApply,tvFilterAmenities;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);
        tvFilterAmenities=bottomSheetDialog.findViewById(R.id.tvFilter);
//        tvFilterAmenities.setText(appKeysPage.getFilters());


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAmenitiesList.clear();
                ItemAdapter itemAadapter=new ItemAdapter();
                ArrayList<DataModel> userSelectedAmenities =itemAadapter.getUpdatedList();
                int amenitiesMatchCount=0;

                //Checking Here Only With Rooms
                for (int i = 0; i <userSelectedAmenities.get(1).getNestedList().size() ; i++) {
                    if(userSelectedAmenities.get(1).getNestedList().get(i).isChecked()) {
                        filterAmenitiesList.add(userSelectedAmenities.get(1).getNestedList().get(i).getId());
                    }
                }


                bottomSheetDialog.dismiss();
            }
        });

        mList = new ArrayList<>();

        //list1
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Monitor", false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Adjustable height", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Laptop stand", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("USB_C Dock", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Charge point", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Standing desk", false);
        nestedList1.add(valuesPOJO);

        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();


        for (int i = 0; i <amenitiesResponseList.size() ; i++) {

            //if(amenitiesResponseList.get(i).isAvailable()){
            valuesPOJO = new ValuesPOJO(amenitiesResponseList.get(i).getId(),amenitiesResponseList.get(i).getName(), false);
            nestedList2.add(valuesPOJO);
            //}


        }


        *//*valuesPOJO = new ValuesPOJO("Single", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Double", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Ac", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Non-AC", false);
        nestedList2.add(valuesPOJO);*//*


        mList.add(new DataModel(nestedList1, "Workspaces"));
        mList.add(new DataModel(nestedList2, "Rooms"));
//        mList.add(new DataModel(nestedList2, appKeysPage.getRooms()));

        adapter = new ItemAdapter(mList);
        locateFilterMainRV.setAdapter(adapter);

        bottomSheetDialog.show();

    }
*/
    private void callLocateFilterBottomSheet(List<AmenitiesResponse> amenitiesResponseList) {

        RecyclerView locateFilterMainRV;
        ValuesPOJO valuesPOJO;
        //ArrayList<DataModel> mList;
        ItemAdapter adapter;
        EditText filterSearch;


        TextView locateFilterCancel, locateFilterApply,tvFilterAmenities,filterTotalSize;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);
        tvFilterAmenities=bottomSheetDialog.findViewById(R.id.tvFilter);
        filterSearch=bottomSheetDialog.findViewById(R.id.filterSearch);
        filterTotalSize=bottomSheetDialog.findViewById(R.id.filterTotalSize);


        //Language
        filterSearch.setHint(appKeysPage.getSearch());
        tvFilterAmenities.setText(appKeysPage.getFilters());


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });



        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAmenitiesList.clear();
                ItemAdapter itemAadapter=new ItemAdapter();
                ArrayList<DataModel> userSelectedAmenities =itemAadapter.getUpdatedList();
                int amenitiesMatchCount=0;

                //Checking Here Only With Rooms
                for (int i = 0; i <userSelectedAmenities.get(1).getNestedList().size() ; i++) {
                    if(userSelectedAmenities.get(1).getNestedList().get(i).isChecked()) {
                        filterAmenitiesList.add(userSelectedAmenities.get(1).getNestedList().get(i).getId());
                    }
                }

                bottomSheetDialog.dismiss();
            }
        });

        //mList = new ArrayList<>();

        //list1
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Monitor", false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Adjustable height", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Laptop stand", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("USB_C Dock", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Charge point", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Standing desk", false);
        nestedList1.add(valuesPOJO);

        if(filterClickedStatus==0) {
            filterClickedStatus=1;
            ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

            for (int i = 0; i < amenitiesResponseList.size(); i++) {

                //if(amenitiesResponseList.get(i).isAvailable()){
                valuesPOJO = new ValuesPOJO(amenitiesResponseList.get(i).getId(), amenitiesResponseList.get(i).getName(), false);
                nestedList2.add(valuesPOJO);
                //}
            }

            mList.add(new DataModel(nestedList1, "Workspaces"));
            mList.add(new DataModel(nestedList2, appKeysPage.getRooms()));

        }

        for (int i = 0; i <mList.size() ; i++) {

            mList.get(i).setExpandable(false);

        }
        filterTotalSize.setText("("+mList.get(1).getNestedList().size()+")");
        adapter = new ItemAdapter(mList);
        locateFilterMainRV.setAdapter(adapter);


     /*   filterSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(s.toString().length()>=2){


                    for (int i = 0; i <nestedList2.size() ; i++) {
                       if( nestedList2.get(i).getValues().contains(s.toString())){

                       }

                    }



                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        bottomSheetDialog.show();

    }


/*    private void getLocateAmenitiesFilterData() {
        if (Utils.isNetworkAvailable(getContext())) {
            dialog = ProgressDialog.showProgressBar(getContext());
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<AmenitiesResponse>> call = apiService.getAmenities();
            call.enqueue(new Callback<List<AmenitiesResponse>>() {
                @Override
                public void onResponse(Call<List<AmenitiesResponse>> call, Response<List<AmenitiesResponse>> response) {
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                    List<AmenitiesResponse> amenitiesResponseList=response.body();

                    meetingAmenityStatusList.clear();
                    callLocateFilterBottomSheet(amenitiesResponseList);

                }

                @Override
                public void onFailure(Call<List<AmenitiesResponse>> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                }
            });

        }else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }*/
    /*
    private void callLocateFilterBottomSheet(List<AmenitiesResponse> amenitiesResponseList) {

        RecyclerView locateFilterMainRV;
        ValuesPOJO valuesPOJO;
        ArrayList<DataModel> mList;
        ItemAdapter adapter;

        TextView locateFilterCancel, locateFilterApply;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomSheetDialog.dismiss();
            }
        });

        mList = new ArrayList<>();

        //list1
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Monitor", false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Adjustable height", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Laptop stand", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("USB_C Dock", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Charge point", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Standing desk", false);
        nestedList1.add(valuesPOJO);

        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();


        for (int i = 0; i <amenitiesResponseList.size() ; i++) {

//            if(amenitiesResponseList.get(i).isAvailable()){
            valuesPOJO = new ValuesPOJO(amenitiesResponseList.get(i).getId(),amenitiesResponseList.get(i).getName(), false);
            nestedList2.add(valuesPOJO);
//            }


        }


        *//*valuesPOJO = new ValuesPOJO("Single", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Double", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Ac", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Non-AC", false);
        nestedList2.add(valuesPOJO);*//*


        mList.add(new DataModel(nestedList1, "Workspaces"));
        mList.add(new DataModel(nestedList2, "Rooms"));

        adapter = new ItemAdapter(mList);
        locateFilterMainRV.setAdapter(adapter);

        bottomSheetDialog.show();

    }*/

    public void setLanguage(){

        logoinPage = Utils.getLoginScreenData(getContext());
        appKeysPage = Utils.getAppKeysPageScreenData(getContext());
        resetPage = Utils.getResetPasswordPageScreencreenData(getContext());
        actionOverLays = Utils.getActionOverLaysPageScreenData(getContext());
        bookindata = Utils.getBookingPageScreenData(getContext());
        global=Utils.getGlobalScreenData(getContext());
        meetingRoomsLanguage=Utils.getMeetingRoomsPageScreenData(getContext());



        //binding.tvPMOOffice.setText(appKeysPage);
        //binding.searchGlobal.setText(appKeysPage.getChooseLocation());
        System.out.println("lang check global" + global);
        System.out.println("lang check bookindata" + bookindata);
        System.out.println("lang check" + appKeysPage);
        System.out.println("lang check" + appKeysPage.getStart());
        System.out.println("lang check" + binding.tvStartLocate);
        binding.tvStartLocate.setText(appKeysPage.getStart());
        binding.tvLocateEndTime.setText(appKeysPage.getEnd());


    }

    private void doRepeatMeetingRoomBookingForWeek(boolean isTeamsChecked) {

        String selectedDate = binding.locateCalendearView.getText().toString();
        List<String> dateList = Utils.getCurrentWeekDateList(selectedDate, enableCurrentWeek);


        MeetingRoomRecurrence meetingRoomRecurrence =new MeetingRoomRecurrence();
        meetingRoomRecurrence.setMeetingRoomId(selectedDeskId);
        meetingRoomRecurrence.setOnlineMeeting(false);
        if(teamsCheckBoxStatus && isTeamsChecked)
            meetingRoomRecurrence.setMsTeams(true);
        else
            meetingRoomRecurrence.setMsTeams(false);
        meetingRoomRecurrence.setHandleRecurring(false);

        List<MeetingRoomRecurrence.Changeset> changesetList=new ArrayList<>();

        for (int i = 0; i <dateList.size() ; i++) {

            MeetingRoomRecurrence.Changeset changeset=meetingRoomRecurrence.new Changeset();
            changeset.setId(0);
            changeset.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            MeetingRoomRecurrence.Changeset.Changes changes=changeset.new Changes();
            changes.setComments("");
            changes.setFrom(getCurrentDate() + "" + "T" + startTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setMyto(getCurrentDate() + "" + "T" + endTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setSubject("");
            changes.setRecurrence("True");
            changes.setRequest(false);

            changeset.setChanges(changes);

            List<Integer> attendeesList = new ArrayList<>();

            //Newly Participant Added
            if (chipList != null) {
                for (int j = 0; j < chipList.size(); j++) {
                    attendeesList.add(chipList.get(j).getId());
                }

            } //End

            changes.setAttendees(attendeesList);

            List<MeetingRoomRecurrence.Changeset.Changes.ExternalAttendees> externalAttendees=new ArrayList<>();
            changes.setExternalAttendees(externalAttendees);

            //List<MeetingRoomRecurrence.Changeset.Changes.RecurrenceDetails> recurrenceDetailsList=new ArrayList<>();

            MeetingRoomRecurrence.Changeset.Changes.RecurrenceDetails recurrenceDetails=changes.new RecurrenceDetails();
            recurrenceDetails.setInterval(1);
            recurrenceDetails.setStartDate(dateList.get(0) + "T" + "00:00:00.000" + "Z");
            recurrenceDetails.setEndDate(dateList.get(dateList.size()-1) + "T" + "00:00:00.000" + "Z");
            recurrenceDetails.setOnDay(24);
            recurrenceDetails.setSelectedMonth(8);
            recurrenceDetails.setPeriod(0);

            changes.setRecurrenceDetailsList(recurrenceDetails);

            changesetList.add(changeset);

        }

        meetingRoomRecurrence.setChangesets(changesetList);

        List<MeetingRoomRecurrence.DeleteIds> deleteIdsList=new ArrayList<>();
        meetingRoomRecurrence.setDeletedIds(deleteIdsList);

        System.out.println("MeetingRoomRecurrence "+meetingRoomRecurrence);

        if (Utils.isNetworkAvailable(getActivity())) {
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            dialog = ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doMeetingRoomRecurrence(meetingRoomRecurrence);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }




        /*MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
        meetingRoomRequest.setMsTeams(false);
        meetingRoomRequest.setHandleRecurring(false);
        meetingRoomRequest.setOnlineMeeting(false);

        List<MeetingRoomRequest.Changeset> changesetList = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {

            MeetingRoomRequest.Changeset m = meetingRoomRequest.new Changeset();
            m.setId(0);
            m.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            MeetingRoomRequest.Changeset.Changes changes = m.new Changes();
            changes.setFrom(getCurrentDate() + "" + "T" + startRoomTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setMyto(getCurrentDate() + "" + "T" + endTRoomime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setComments("Comment");
            changes.setSubject("subject");
            boolean isRequest = false;
            changes.setRequest(isRequest);

            m.setChanges(changes);

            changesetList.add(m);

            List<Integer> attendeesList = new ArrayList<>();


            //Newly Participant Added
            if (chipList != null) {
                for (int j = 0; j < chipList.size(); i++) {
                    attendeesList.add(chipList.get(j).getId());
                }

            } //End

            changes.setAttendees(attendeesList);

            List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList = new ArrayList<>();
            changes.setExternalAttendees(externalAttendeesList);

        }

        meetingRoomRequest.setChangesets(changesetList);

        List<MeetingRoomRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        meetingRoomRequest.setDeletedIds(deleteIdsList);


        System.out.println("RepeatMeetingRoom " + meetingRoomRequest);*/




    }
    private void doCheckAppliedAminitiesWithMeetingRoom(List<UserAllowedMeetingResponse.Amenity> amenityList, MeetingStatusModel meetingStatusModel) {
        //amenityList-->This has default meeting room aminities
        //list-->User selected amenities

        ItemAdapter itemAadapter=new ItemAdapter();
        ArrayList<DataModel> userSelectedAmenities =itemAadapter.getUpdatedList();
        int amenitiesMatchCount=0;

        //Checking Here Only With Rooms
        for (int i = 0; i <userSelectedAmenities.get(1).getNestedList().size() ; i++) {
            for (int j = 0; j <amenityList.size() ; j++) {

                if(userSelectedAmenities.get(1).getNestedList().get(i).isChecked()) {
                    if (userSelectedAmenities.get(1).getNestedList().get(i).getId() == amenityList.get(j).getId()) {
                        amenitiesMatchCount = amenitiesMatchCount + 1;
                    }
                }
            }
        }

        int userSelectedTrueCount=0;
        for (int i = 0; i <userSelectedAmenities.get(1).getNestedList().size() ; i++) {
            if(userSelectedAmenities.get(1).getNestedList().get(i).isChecked()){
                userSelectedTrueCount=userSelectedTrueCount+1;
            }
        }

        if(amenitiesMatchCount==userSelectedTrueCount){
            System.out.println("AllUserSelectedAmenitiesAvaliableHInThisRoom "+meetingStatusModel.getId());

        }else {
            System.out.println("UserSelectedAmenityIsNotAvaliableHInThisRoom "+meetingStatusModel.getId());
            MeetingAmenityStatus meetingAmenityStatus=new MeetingAmenityStatus(meetingStatusModel.getId());
            meetingAmenityStatusList.add(meetingAmenityStatus);
        }
        amenitiesApplyStatus=true;
    }
    public void checkTeamsCheckBox(){
        if (Utils.isNetworkAvailable(getContext())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<String> call = apiService.getSettingData("GraphAPIEnabled");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code()==200){
                        if (response.body().equalsIgnoreCase("true")){
                            teamsCheckBoxStatus = true;
                        } else {
                            teamsCheckBoxStatus = false;
                        }
                    }else if (response.code() == 403){
                        teamsCheckBoxStatus = false;
                    }else {
                        teamsCheckBoxStatus = false;
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }
    }

    //New...
    public void checkVeichleReg() {

        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Boolean> call = apiService.getIsVehicleReg();
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    if(response.body()!=null && response.code() == 200) {
                        isVehicleReg = response.body();
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }

    @Override
    public void onChangeDesk(int deskId, String deskName, String request, String timeZone) {
        editBookingDetailsGlobal.setDeskCode(deskName);
        editBookingDetailsGlobal.setDesktId(deskId);
        editBookingDetailsGlobal.setDeskTeamId(selectedTeamId);
        editBookingDetailsGlobal.setTimeZone(timeZone);

        selectedDeskId=deskId;
        if(selectedTeamId == SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID))
            editBookingUsingBottomSheet(editBookingDetailsGlobal,
                            1,0,"new");
        else
            editBookingUsingBottomSheet(editBookingDetailsGlobal,
                            1,0,"request");

    }

    @Override
    public void onActiveTeamsSelected(int teamId, String teamName) {
        selectedTeamId = teamId;
        tvTeamName.setText(teamName);
        getDeskList("-1", calSelectedDate);
    }
}
