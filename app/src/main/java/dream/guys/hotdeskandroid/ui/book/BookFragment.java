package dream.guys.hotdeskandroid.ui.book;

import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import butterknife.BindView;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ActiveTeamsAdapter;
import dream.guys.hotdeskandroid.adapter.AssertListAdapter;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CapacityAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapterBooking;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.NewDeskListForEditRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.NewDeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParkingSpotListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.adapter.RoomListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.controllers.BookDeskController;
import dream.guys.hotdeskandroid.controllers.EditCarParkController;
import dream.guys.hotdeskandroid.controllers.EditMeetingRoomController;
import dream.guys.hotdeskandroid.controllers.OtherBookingController;
import dream.guys.hotdeskandroid.controllers.EditDeskController;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.AssertModel;
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
import dream.guys.hotdeskandroid.model.response.CompanyDefaultResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
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
import dream.guys.hotdeskandroid.utils.CustomSpinner;
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
        NewDeskListForEditRecyclerAdapter.OnEDITChangeSelected,
        ActiveTeamsAdapter.OnActiveTeamsSelected,
        CarListToEditAdapterBooking.CarEditClickableBooking,
        ParkingSpotListRecyclerAdapter.OnSelectSelected,
        ParticipantNameShowAdapter.OnParticipantSelectable,
        RoomListRecyclerAdapter.OnSelectSelected,
        ShowCountryAdapter.OnSelectListener,
        CustomSpinner.OnSpinnerEventsListener,
        ItemAdapter.selectItemInterface {

    String TAG="BookFragment";
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
    public int selectedicon = 1;
    public boolean isSetup = false;
    String calSelectedDate="";
    String calSelectedMont="";

    String type="none";

    TextView startTime,endTime,repeat,date,deskRoomName,tv_description,locationAddress,continueEditBook;
    String repeatValue="None";

    int teamId=0,teamMembershipId=0,selectedDeskId=0;

    RecyclerView rvHomeBooking,rvDeskRecycler, rvActiveTeams;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    NewDeskListRecyclerAdapter newdeskListRecyclerAdapter;
    NewDeskListForEditRecyclerAdapter newDeskListForEditRecyclerAdapter;
    ActiveTeamsAdapter activeTeamsAdapter;

    ParkingSpotListRecyclerAdapter parkingSpotListRecyclerAdapter;
    RoomListRecyclerAdapter roomListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nestedScrollView;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk=new ArrayList<>();
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList = new ArrayList<>();
    List<DeskAvaliabilityResponse.LocationDesks> locationDeskList = new ArrayList<>();
    List<ParkingSpotModel> parkingSpotModelList=new ArrayList<>();
    List<UserAllowedMeetingResponse> allMeetingRoomList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseFilterList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdated=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdatedFilterList=new ArrayList<>();

    BookingForEditResponse bookingForEditResponse;
    BookingForEditResponse temp;
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
    TextView country, state, street, floor, back, bsApply,deskStatusText,deskStatusDot;
    RecyclerView rvCountry, rvState, rvStreet, rvFloor;
    ShowCountryAdapter showCountryAdapter;
    FloorAdapter floorAdapter;
    //    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock, statBlock, streetBlock, floorBlock;

    TextView bsLocationSearch, tvCapacityFilter;

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
    TextView tvRepeat, tvTeamName,tvcapacityCount;
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
    public int globalSelectedTeamId = 0;
    public int myTeamId = 0;
    public int selectedTeamAutoApproveStatus = 0;
    String selectedTeamName="";
    EditBookingDetails editBookingDetailsGlobal;
    UserDetailsResponse profileData; //= new UserDetailsResponse();

    List<MeetingListToEditResponse.Attendees> attendeesListForEdit;

    int defaultLocationcheck=0;
    int defaultEditDeskId=0;
    int changedTeamId=0;
    int changedDeskId=0;

    boolean startDisabled = false;
    boolean endDisabled = false;
    boolean selectDisabled = false;
    boolean tvTeamNameDisabled = false;
    boolean defaultLocationSet = false;
    //New...
    //For Displaying the count
    TextView filterTotalSize;

    String companyDefaultDeskStartTime="";
    String companyDefaultDeskEndTime="";
    CustomSpinner assertSpinner;
    ArrayList<AssertModel> assertList;
    AssertListAdapter assertListAdapter;

    BottomSheetDialog capacityDialog;
    private String capacitySelectedItem = "1";
    BottomSheetDialog bottomSheetDialog;

    int locationId =0;
    boolean isMeetingRequest =false;
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
        myTeamId = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);
        calSelectedDate=Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
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
                if(selectedicon < 4)
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
                for (int i=0; i<events.size();i++) {
//                    //System.out.println("avail assigned COunt"+Utils.getYearMonthDateFormat(date) +" : "+events.get(i).getDate());
                    if (events.get(i).getDate().equalsIgnoreCase(Utils.getYearMonthDateFormat(date)+"T00:00:00Z")){
                        if (events.get(i).getAvailableCount()>0
                                || (events.get(i).getAssignedCount()
                                - events.get(i).getUsedCount())>0) {
                            countCheck=true;
                            break loo;
                        }
                    }
                }

                if (countCheck) {
                    if (!(Utils.compareTwoDate(date,Utils.getCurrentDate()) == 1)) {
                        if (selectedicon == 1) {
                            if (isGlobalLocationSetUP)
                                getAvaliableDeskDetails("3",Utils.getISO8601format(date));
                            getAddEditDesk("3",Utils.getISO8601format(date));
                            calSelectedDate=Utils.getISO8601format(date);

//                            else
//                            ((MainActivity) getActivity()).getAddEditDesk("3",Utils.getISO8601format(date));
                        } else if (selectedicon==2) {
                            getMeetingBookingListToEdit("" + Utils.getYearMonthDateFormat(date)+"T00:00:00.000Z", "new");
                            calSelectedDate=Utils.getISO8601format(date);
                        } else if(selectedicon==3) {
                            getCarParListToEdit(""+Utils.getISO8601format(date),""+Utils.getISO8601format(date));
                            calSelectedDate=Utils.getISO8601format(date);
                        } else if (selectedicon == 4 || selectedicon == 5 || selectedicon == 6 ||
                                selectedicon == 7) {
                            calSelectedDate = Utils.getISO8601format(date);
                            new OtherBookingController(context, selectedicon, calSelectedDate);

                        } else {
                        }
                    }else
                        Toast.makeText(getContext(), "Please Select current Date", Toast.LENGTH_SHORT).show();
                } else{
                    if (selectedicon == 4 || selectedicon == 5 || selectedicon == 6 ||
                            selectedicon == 7) {
                        calSelectedDate = Utils.getISO8601format(date);
                        new OtherBookingController(context, selectedicon, calSelectedDate);

                    }
                }
                   // Toast.makeText(getContext(), "No booking available", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrevClicked(String month) {
                calSelectedMont=month;

                if (binding.searchGlobal.getText()!=null
                        && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                        && !binding.searchGlobal.getText().toString().isEmpty()){
                    getDeskCountLocation(month,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                } else {
                    getDeskCount(month);

                }
            }

        });

        binding.deskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=1;
                tabToggleViewClicked(1);

            }
        });
        binding.roomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=2;
                tabToggleViewClicked(2);
            }
        });
        binding.parkingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedicon=3;
                tabToggleViewClicked(3);
            }
        });
        binding.moreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectedicon = 4;
//                tabToggleViewClicked(4);
            }
        });

        return root;
    }

    /*
    private void loadDefaultLocation() {

        ProgressDialog.touchLock(getContext(), getActivity());


        //To load default location-saved in login Activity
        int parentIdCheck =SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID_CHECK);
        if(parentIdCheck>0 && defaultLocationcheck==0){
            int floorPositionCheck=SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION_CHECK);

            SessionHandler.getInstance().saveInt(getContext(),AppConstants.PARENT_ID,parentIdCheck);
            SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION,floorPositionCheck);


            //To set Default location
            String countryCheck=SessionHandler.getInstance().get(getContext(),AppConstants.COUNTRY_NAME_CHECK);
            String buildingCheck=SessionHandler.getInstance().get(getContext(),AppConstants.BUILDING_CHECK);
            String floorCheck=SessionHandler.getInstance().get(getContext(),AppConstants.FLOOR_CHECK);
            String fullPathCheck=SessionHandler.getInstance().get(getContext(),AppConstants.FULLPATHLOCATION_CHECK);


            SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME,countryCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING,buildingCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR,floorCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FULLPATHLOCATION,fullPathCheck);

        }

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        ProgressDialog.clearTouchLock(getContext(), getActivity());
        if (parentId > 0) {
            //Disable touch Screen
            ProgressDialog.touchLock(getContext(), getActivity());

            //Set Selected Floor In SearchView
            String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
            String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
            String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
            String fullPathLocation = SessionHandler.getInstance().get(getContext(), AppConstants.FULLPATHLOCATION);

            if (CountryName == null && buildingName == null && floorName == null && fullPathLocation == null) {
                binding.searchGlobal.setHint("choose location from the list");
            } else {
                if (fullPathLocation == null) {
                    binding.searchGlobal.setText(CountryName + "," + buildingName + "," + floorName);
                } else {
                    binding.searchGlobal.setText(fullPathLocation);
                }
            }


            initLoadFloorDetails(canvasss);
//            getAvaliableDeskDetails("3",calSelectedDate);
            *//*
            //Used For Desk Avaliability Checking
            getAvaliableDeskDetails(null, 0);

            //CarChecking
            doInitCarAvalibilityHere(parentId);

            //Meeting Checking
            doInitMeetingAvalibilityHere(parentId, canvasDrawStatus);*//*

        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }
    */

    private void loadDefaultLocation() {

        ProgressDialog.touchLock(getContext(), getActivity());

        locationId = SessionHandler.getInstance().getInt(context, AppConstants.LOCATION_ID);
        defaultLocationSet=true;
        //To load default location-saved in login Activity
        int parentIdCheck =SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID_CHECK);
        if(parentIdCheck>0 && defaultLocationcheck==0) {
            int floorPositionCheck=SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION_CHECK);

            SessionHandler.getInstance().saveInt(getContext(),AppConstants.PARENT_ID,parentIdCheck);
//            SessionHandler.getInstance().saveInt(getContext(),AppConstants.LOCATION_ID,parentIdCheck);
            SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION,floorPositionCheck);


            //To set Default location
            String countryCheck=SessionHandler.getInstance().get(getContext(),AppConstants.COUNTRY_NAME_CHECK);
            String buildingCheck=SessionHandler.getInstance().get(getContext(),AppConstants.BUILDING_CHECK);
            String floorCheck=SessionHandler.getInstance().get(getContext(),AppConstants.FLOOR_CHECK);
            String finalFloorCheck = SessionHandler.getInstance().get(getContext(), AppConstants.FINAL_FLOOR_CHECK);
            String fullPathCheck=SessionHandler.getInstance().get(getContext(),AppConstants.FULLPATHLOCATION_CHECK);


            SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME,countryCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING,buildingCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR,floorCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FINAL_FLOOR,finalFloorCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FULLPATHLOCATION,fullPathCheck);
        }

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        ProgressDialog.clearTouchLock(getContext(), getActivity());
        if (parentId > 0) {
            //Disable touch Screen
            ProgressDialog.touchLock(getContext(), getActivity());

            //Set Selected Floor In SearchView
            String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
            String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
            String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
            String finalFloor = SessionHandler.getInstance().get(getContext(), AppConstants.FINAL_FLOOR);
            String fullPathLocation = SessionHandler.getInstance().get(getContext(), AppConstants.FULLPATHLOCATION);

            if (CountryName == null && buildingName == null && floorName == null && fullPathLocation == null) {
                binding.searchGlobal.setHint("choose location from the list");
            } else {
                if (fullPathLocation == null) {
                    binding.searchGlobal.setText(floorName + "  " + finalFloor);
                } else {
                    binding.searchGlobal.setText(floorName + "  " + finalFloor);
                }
            }


            initLoadFloorDetails(canvasss);
//            getAvaliableDeskDetails("3",calSelectedDate);
            /*
            //Used For Desk Avaliability Checking
            getAvaliableDeskDetails(null, 0);

            //CarChecking
            doInitCarAvalibilityHere(parentId);

            //Meeting Checking
            doInitMeetingAvalibilityHere(parentId, canvasDrawStatus);*/

        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void getActiveTeams() {

        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ActiveTeamsResponse>> call = apiService.getActiveTeams();
            call.enqueue(new Callback<List<ActiveTeamsResponse>>() {
                @Override
                public void onResponse(Call<List<ActiveTeamsResponse>> call, Response<List<ActiveTeamsResponse>> response) {
//                    activeTeamsList = response.body();
                    try {

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
                    } catch (Exception exception){
                        Toast.makeText(context, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
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
                    try {
                        if(response.code()==200){
                            if(response.body().size() > 0)
                                amenitiesList = response.body();
                        }else if(response.code()==401){
                            Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        }
                    } catch (Exception exception){

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
                String timeZoneId = appLinkData.getQueryParameter("timeZoneId");
                timeZoneId = timeZoneId.replace("_"," ");
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                /*lop :
                for (int i=0; i<bookingDeskList.size();i++){
                    if (Integer.parseInt(deskId)
                            == bookingDeskList.get(i).getTeamDeskId()) {
//                        Toast.makeText(getActivity(), ""+bookingForEditResponse.getBookings().size(), Toast.LENGTH_SHORT).show();
                        if (bookingForEditResponse.getBookings().size() > 0){
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                                    .getMyto(),2)));
                        }else {
                            editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                            editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                        }
                        break lop;

                    } else {

                        //System.out.println("date con"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+"Z");
                        editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                        editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z",2)));
                    }
                    }
                    */


/*
                if (bookingForEditResponseDesk.size()==0){
                    editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    //System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
                    editBookingDetails.setEditEndTime(Utils.addingHoursToCurrentDate(2));

                }*/

                calSelectedDate=Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
//                editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                editBookingDetails.setDate(Utils.convertStringToDateFormet(Utils.getCurrentDate()));
                editBookingDetails.setCalId(0);
                editBookingDetails.setDeskCode(deskCode);
                editBookingDetails.setDesktId(Integer.parseInt(deskId));
                editBookingDetails.setDeskTeamId(Integer.parseInt(requestedTeamId));
                editBookingDetails.setTimeZone(timeZoneId);
                editBookingDetails.setDeskStatus(0);
                editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z",2)));

                if (bookingDeskList!=null && bookingDeskList.size()>0){
                    loo :
                    for (int i=0;i<bookingDeskList.size();i++){
                        if (deskId.equalsIgnoreCase(""+bookingDeskList.get(i).getTeamDeskId())){
                            checkIsRequest=true;
                            break loo;
                        }
                    }
                }
                //System.out.println("cajec vava"+bookingDeskList.size() +"  "+checkIsRequest);
                if (checkIsRequest)
                    editBookingUsingBottomSheet(editBookingDetails,1,0,"new_deep_link");
                else if (Integer.parseInt(requestedTeamId) == SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID)) {
                    editBookingUsingBottomSheet(editBookingDetails,1,0,"new_deep_link");
                } else
                    editBookingUsingBottomSheet(editBookingDetails,1,0,"request");

            } else if (data1.equalsIgnoreCase("room")){
                selectedicon=1;
                tabToggleViewClicked(selectedicon);

                String roomId = appLinkData.getQueryParameter("meetingRoomId");
                String roomName = appLinkData.getQueryParameter("meetingRoomName");
                EditBookingDetails editBookingDetails= new EditBookingDetails();

                editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                //System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
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
                //System.out.println("eror check"+Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z");
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
//            //System.out.println("Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE);
//            Toast.makeText(this, "Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (assertSpinner != null) {
                assertSpinner.setSelection(selectedicon-1);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        this.context = getContext();
        this.activityContext=getActivity();

        assertSpinner = view.findViewById(R.id.assertSpinner);
        loadAssertSpinner();
        loadDefaultLocation();
//        Toast.makeText(context, "hit"+assertSpinner.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();

        tabToggleViewClicked(selectedicon);

        getAddEditDesk("-1",Utils.getISO8601format(Utils.convertStringToDateFormet(Utils.getCurrentDate())));

//        assertSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_outline));

    }

    @SuppressLint("ResourceAsColor")
    public void tabToggleViewClicked(int i) {
        resetLayout();
        switch (i) {
            case 1:
                if (selectedicon==4)
                    binding.profileBack.setText("Working Remotely");
                else if (selectedicon==5)
                    binding.profileBack.setText("Log sickness");
                else if (selectedicon==6)
                    binding.profileBack.setText("Book holiday");
                else if (selectedicon==7)
                    binding.profileBack.setText("Book training");
                else
                    binding.profileBack.setText("Book a workspace");

                if (selectedicon>3){
                    binding.searchGlobal.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.line_gray));
                    binding.searchGlobal.setTextColor(ContextCompat.getColorStateList(getActivity(),R.color.figmaGrey));
                } else {
                    binding.searchGlobal.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBackground));
                    binding.searchGlobal.setTextColor(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlack));
                }

                binding.rlTime.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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
                if (calSelectedMont != "" && !calSelectedMont.isEmpty()) {
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(calSelectedMont,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(calSelectedMont);
                } else {

                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }
                break;
            case 2:
                binding.profileBack.setText("Book a Room");
                binding.searchGlobal.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBackground));
                binding.rlTime.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

//                binding.rlTime.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                binding.roomLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivRoom.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvRoom.setVisibility(View.VISIBLE);
                binding.tvRoom.setText(appKeysPage.getRoom());
                binding.rlParticipants.setVisibility(View.GONE);
                binding.rlFilter.setVisibility(View.GONE);

                LinearLayout.LayoutParams roomParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                roomParams.weight = 1.0f;
                binding.roomLayout.setLayoutParams(roomParams);
                binding.calendarView.setVisibility(View.VISIBLE);
               /*
               if (calSelectedMont != "" && !calSelectedMont.isEmpty())
                    getDeskCount(calSelectedMont);
                else
                    getDeskCount(Utils.getCurrentDate());
                    */
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
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }
                break;
            case 3:
                binding.profileBack.setText("Book Parking");
                binding.searchGlobal.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBackground));
                binding.rlTime.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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
            default:
                if (selectedicon==4)
                    binding.profileBack.setText("Working Remotely");
                else if (selectedicon==5)
                    binding.profileBack.setText("Log sickness");
                else if (selectedicon==6)
                    binding.profileBack.setText("Book holiday");
                else if (selectedicon==7)
                    binding.profileBack.setText("Book training");
                else
                    binding.profileBack.setText("Book a workspace");

                binding.rlTime.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivDesk.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvDesk.setVisibility(View.VISIBLE);
                binding.tvDesk.setText(appKeysPage.getWorkSpace());
                binding.rlParticipants.setVisibility(View.GONE);
                binding.rlFilter.setVisibility(View.GONE);
                LinearLayout.LayoutParams deskParas = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                deskParas.weight = 1.0f;
                binding.deskLayout.setLayoutParams(deskParas);
                binding.calendarView.setVisibility(View.VISIBLE);
                if (calSelectedMont != "" && !calSelectedMont.isEmpty()){
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(calSelectedMont,""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(calSelectedMont);
                } else {
                    if (binding.searchGlobal.getText()!=null
                            && !binding.searchGlobal.getText().toString().equalsIgnoreCase("")
                            && !binding.searchGlobal.getText().toString().isEmpty())
                        getDeskCountLocation(Utils.getCurrentDate(),""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),1);
                    else
                        getDeskCount(Utils.getCurrentDate());
                }
                break;

        }
    }

    private void getDeskCount(String month) {
//        Toast.makeText(context, "just count", Toast.LENGTH_SHORT).show();
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<DeskRoomCountResponse>> call = null;
            Log.d(TAG, "getDeskCount: count");
            switch (selectedicon){
                case 1:
                    call = apiService.getDailyDeskCount(month, ""+SessionHandler.getInstance().getInt(getActivity(), AppConstants.TEAM_ID));
                    break;
                case 2:
                    call = apiService.getDailyRoomCount(month, ""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                    break;
                case 3:
                    call = apiService.getDailyParkingCount(month, ""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                    break;
                default:
                    call = apiService.getDailyDeskCount(month, ""+SessionHandler.getInstance().getInt(getActivity(), AppConstants.TEAM_ID));
                    break;
            }
            call.enqueue(new Callback<List<DeskRoomCountResponse>>() {
                @Override
                public void onResponse(Call<List<DeskRoomCountResponse>> call, Response<List<DeskRoomCountResponse>> response) {
                    dialog.dismiss();
                    try {

                        if (response.code()==200 && response.body().size()>0){
                            events.clear();
                            List<DeskRoomCountResponse> dsk = response.body();
                            int firstLocationId = dsk.get(0).getLocationId();
                            for (int i=0; i<dsk.size();i++){
                                if (firstLocationId == dsk.get(i).getLocationId()){
                                    events.add(dsk.get(i));
                                }
                            }
                            if (events.size()>0){
                                binding.calendarView.updateCalendar(events, selectedicon);
                            }
                        } else if(response.code()==401){
                            //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                            SessionHandler.getInstance().saveBoolean(getActivity(),
                                    AppConstants.LOGIN_CHECK,false);
                            Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        }
                    } catch (Exception exception){

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
//        Toast.makeText(context, "loca count"+calSelectedDate, Toast.LENGTH_SHORT).show();
        if (Utils.isNetworkAvailable(getActivity()) && Integer.parseInt(locationId)!=0) {
            if(dialog.isShowing())
                dialog.dismiss();
//            dialog= ProgressDialog.showProgressBar(context);
            //System.out.println("check sub parent Id  :  "+locationId);

            if(defaultLocationcheck>0){
                locationId = ""+SessionHandler.getInstance().getInt(context, AppConstants.LOCATION_ID_TEMP);
            } else {
                locationId = ""+SessionHandler.getInstance().getInt(context, AppConstants.LOCATION_ID);
            }

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<DeskRoomCountResponse>> call = null;
            int caseCount =0;
            if (selectedicon==1 || selectedicon>3)
                caseCount=1;
            else
                caseCount=selectedicon;
            switch (caseCount) {
                case 0:
                    if (drawStatus == 2) {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        Log.d(TAG, "getDeskCountLocation: if"+fromTime);
                        Log.d(TAG, "getDeskCountLocation: if to"+toTime);
                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);

                    }
                    else    {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        Log.d(TAG, "getDeskCountLocation: else from"+fromTime);
                        Log.d(TAG, "getDeskCountLocation: else to"+toTime);
//                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                        call = apiService.getDailyDeskCountLocation(month, locationId,"","");
                    }
                    break;
                case 1:
                    if (drawStatus == 2) {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                    }
                    else    {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
//                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                        call = apiService.getDailyDeskCountLocation(month, locationId,"","");
                    }
                    break;
                case 2:
                    if (drawStatus == 2){
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyRoomCountLocation(month, locationId,fromTime,toTime);
                    }
                    else
                        call = apiService.getDailyRoomCountLocation(month, locationId,"","");
                    break;
                case 3:
                    if (drawStatus == 2){
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyParkingCountLocation(month, locationId,fromTime,toTime);
                    }
                    else
                        call = apiService.getDailyParkingCountLocation(month, locationId,"","");
                    break;
                default:
                    Log.d(TAG, "getDeskCountLocation: else from"+calSelectedDate);
                    Log.d(TAG, "getDeskCountLocation: else to"+calSelectedDate);

                    if (drawStatus == 2) {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                    }
                    else    {
                        String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00Z";
                        String fromTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateStartTime.getText().toString() + ":00" + ".000Z";
                        String toTime = Utils.getYearMonthDateFormat(Utils.convertStringToDateFormet(calSelectedDate)) + " " + binding.locateEndTime.getText().toString() + ":00" + ".000Z";
//                        call = apiService.getDailyDeskCountLocation(month, locationId, fromTime, toTime);
                        call = apiService.getDailyDeskCountLocation(month, locationId,"","");
                    }
            }
            call.enqueue(new Callback<List<DeskRoomCountResponse>>() {
                @Override
                public void onResponse(Call<List<DeskRoomCountResponse>> call, Response<List<DeskRoomCountResponse>> response) {
//                    ProgressDialog.dismisProgressBar(context,dialog);
//                    dialog.dismiss();
                    isGlobalLocationSetUP = true;
                    try{

                        if (response.code()==200 && response.body()!=null){
                            events.clear();
                            events.addAll(response.body());
                            if (events.size()>0){
                                binding.calendarView.updateCalendar(events, selectedicon);
                            }else {
                                binding.calendarView.updateCalendar(null, -1);
                            }

                        } else if(response.code()==401){
                            //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                            Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        }
                    } catch(Exception exception){

                    }
                }

                @Override
                public void onFailure(Call<List<DeskRoomCountResponse>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(context,dialog);
                    Toast.makeText(getActivity(), "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
                }
            });

        } else {
            if (Integer.parseInt(locationId)==0)
                getDeskCount(month);
            else
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

                    for(int i=0; i<meetingListToEditResponseList.size(); i++) {
                        //System.out.println("meting userId"+meetingListToEditResponseList.get(i).getBookedByUserId()
//                                +" : " +SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID));
                        if (meetingListToEditResponseList.get(i).getBookedByUserId()
                                == SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID)){
                            meetingListToEditList.add(meetingListToEditResponseList.get(i));
                        }

                        //System.out.println("recycler bala for"+meetingListToEditResponseList.size());
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
                                    1800)));
                        } else {
                            editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                            //System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
//                                    +Utils.getCurrentTime()+"Z");
                            editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                                    +Utils.getCurrentTime()+":00Z",1800)));

                        }
                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setMeetingRoomtId(Integer.parseInt(roomId));
                        editBookingDetails.setRoomName(roomName);

                        getRoomlist(editBookingDetails,"new_deep_link");


                    } else {
                        if(meetingListToEditList.size()>0)
                            callMeetingRoomEditListAdapterBottomSheet(meetingListToEditList,"new");
                        else
                            newRoomBookingSheet(meetingListToEditResponseList);
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
                    try {
                        List<CarParkListToEditResponse> carParkingForEditResponse = response.body();

                        if(carParkingForEditResponse.size()>0)
                            CallCarBookingEditList(carParkingForEditResponse, "5");
                        else
                            newCarBottomSheet(carParkingForEditResponse);

                    } catch (Exception e){

                    }

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
//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkLocationsModel>> call = apiService.getCarParkLocation();
            call.enqueue(new Callback<List<CarParkLocationsModel>>() {
                @Override
                public void onResponse(Call<List<CarParkLocationsModel>> call, Response<List<CarParkLocationsModel>> response) {
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    try {

                        if (response.code()==200 && response.body().size()>0){
                            getParkingSpotList(""+response.body().get(0).getId(),editBookingDetails,status);
                        } else {
                            Toast.makeText(getContext(), "No parking locations Aavilable", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception){

                    }

                }

                @Override
                public void onFailure(Call<List<CarParkLocationsModel>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getParkingSpotList(String id, EditBookingDetails editBookingDetails,String newEdit) {
        if (Utils.isNetworkAvailable(getActivity())) {
//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ParkingSpotModel>> call = apiService.getParkingSpotModels(id);
            call.enqueue(new Callback<List<ParkingSpotModel>>() {
                @Override
                public void onResponse(Call<List<ParkingSpotModel>> call, Response<List<ParkingSpotModel>> response) {
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                    parkingSpotModelList.clear();
                    try {
                        if(response.code()==200 && response.body()!=null && response.body().size()>0){
                            for(int i=0;i<response.body().size();i++){
                                if(response.body().get(i).isActive() && response.body().get(i).getAssignees().size()==0) {
                                    parkingSpotModelList.add(response.body().get(i));
                                } else if(response.body().get(i).isActive() && response.body().get(i).getAssignees().size()>0){
                                    for (int x=0;x<response.body().get(i).getAssignees().size();x++){
                                        if (response.body().get(i).getAssignees().get(x).getId()
                                                == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)) {
                                            parkingSpotModelList.add(response.body().get(i));
                                        } else {
                                            if (response.body().get(i).getParkingSlotAvailability()==2)
                                                parkingSpotModelList.add(response.body().get(i));
                                        }
                                    }
                                }
                            }
                            boolean checkIsRequest=false;
                            if (parkingSpotModelList!=null && parkingSpotModelList.size()>0){
                                loo :
                                for (int i=0;i<parkingSpotModelList.size();i++){
                                    if (editBookingDetails.getParkingSlotId()==parkingSpotModelList.get(i).getId()){
                                        editBookingDetails.setLocationAddress(parkingSpotModelList.get(i).getLocation().getName());
                                        editBookingDetails.setDescription(parkingSpotModelList.get(i).getDescription());
                                        checkIsRequest=true;
                                        break loo;
                                    }
                                }
                            }

                            if (parkingSpotModelList.size() > 0) {
                                if (newEdit.equalsIgnoreCase("new"))
                                    if (parkingSpotModelList!=null && parkingSpotModelList.size()>0 && parkingSpotModelList.get(0).getParkingSlotAvailability()==2)
                                        editBookingUsingBottomSheet(editBookingDetails,3,0,"request");
                                    else
                                        editBookingUsingBottomSheet(editBookingDetails,3,0,"request");
                                else if (newEdit.equalsIgnoreCase("new_deep_link")){
                                    if (checkIsRequest)
                                        editBookingUsingBottomSheet(editBookingDetails,3,0,"new_deep_link");
                                    else
                                        editBookingUsingBottomSheet(editBookingDetails,3,0,"request");
                                }else
                                    editBookingUsingBottomSheet(editBookingDetails,3,0,"edit");

                            } else {
                                Toast.makeText(context, "No Parkings available,Please try later.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(!isGlobalLocationSetUP)
                                getCarParkLocationsList(editBookingDetails,"new");
                        }

                    } catch (Exception exception){

                    }

                }

                @Override
                public void onFailure(Call<List<ParkingSpotModel>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    Toast.makeText(getActivity(), "Api Failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void getRoomlist(EditBookingDetails editBookingDetails, String newEditStatus) {
        if (Utils.isNetworkAvailable(getActivity())) {
            //System.out.println("ame list vala getroom list enter");

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
                    try {
                        for(int i=0; i<response.body().size(); i++){
                            if(response.body().get(i).isActive()){
                                if (response.body().get(i).getAutomaticApprovalStatus()==3){
                                    boolean teamPresent=false;
                                    team:
                                    for (int x=0; x<response.body().get(i).getTeams().size(); x++){
                                        if (response.body().get(i).getTeams().get(x).getId() == teamId){
                                            teamPresent=true;
                                            break team;
                                        }
                                    }
                                    manager:
                                    for (int x=0; x<response.body().get(i).getManagers().size(); x++){
                                        if (response.body().get(i).getManagers().get(x).getId()
                                                == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)){
                                            teamPresent=true;
                                            break manager;
                                        }
                                    }
                                    if (teamPresent)
                                        userAllowedMeetingResponseList.add(response.body().get(i));
                                } else {
                                    userAllowedMeetingResponseList.add(response.body().get(i));
                                }
                            }
                        }
                    } catch (Exception e){

                    }

                    boolean checkIsRequest=false;
                    if (userAllowedMeetingResponseList!=null && userAllowedMeetingResponseList.size()>0){

                        for (int i=0; i < userAllowedMeetingResponseList.size(); i++){
                            boolean check=false;
                            if (filterAmenitiesList!=null && filterAmenitiesList.size()>0)
                                check = false;
                            else
                                check = true;

                            soo:
                            for (int j=0;j<filterAmenitiesList.size();j++){
                                boolean amenityCheck=false;
                                //System.out.println("ame list check of"+i+" : "+filterAmenitiesList.get(j));
                                ooo:
                                for(int x=0; x<userAllowedMeetingResponseList.get(i).getAmenities().size(); x++){
                                    if (filterAmenitiesList.get(j) == userAllowedMeetingResponseList.get(i)
                                            .getAmenities().get(x).getId()){
                                        //System.out.println("ame list check of true"+
//                                                userAllowedMeetingResponseList.get(i).getName());
//                                        userAllowedMeetingResponseList.remove(i);
                                        //System.out.println("ame list check of"+i+" : "+filterAmenitiesList.get(j));
                                        amenityCheck=true;
                                    }
                                }
                                if (!amenityCheck) {
                                    //System.out.println("ame list check of remove"+
//                                            userAllowedMeetingResponseList.get(i).getName());
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

                    } else {
                        if(userAllowedMeetingResponseListUpdated!=null &&
                                userAllowedMeetingResponseListUpdated.size()>0){
                            editBookingDetails.setCapacity(""+userAllowedMeetingResponseListUpdated.get(0).getNoOfPeople());
                        }
                        if (userAllowedMeetingResponseFilterList!=null
                                &&userAllowedMeetingResponseFilterList.size()>0)
                            editBookingDetails.setCapacity(""+userAllowedMeetingResponseFilterList.get(0).getNoOfPeople());
                        if (userAllowedMeetingResponseListUpdated!=null
                                && userAllowedMeetingResponseListUpdated.size()>0)
                            callAmenitiesListForMeetingRoom(editBookingDetails,
                                    editBookingDetails.getEditStartTTime(),
                                    editBookingDetails.getEditEndTime(),
                                    editBookingDetails.getDate(),
                                    userAllowedMeetingResponseListUpdated.get(0).getId(),
                                    0,"new");
                        else if (userAllowedMeetingResponseFilterList!=null && userAllowedMeetingResponseFilterList.size()>0)
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
        TextView editClose, editDate, bookingName,addNew,tvActive;
        LinearLayoutManager linearLayoutManager;

        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.dialog_calendar_edit_booking_bottomsheet, null);
        bookEditBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        rvCarEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = bookEditBottomSheet.findViewById(R.id.editClose);
        editDate = bookEditBottomSheet.findViewById(R.id.editDate);
        bookingName = bookEditBottomSheet.findViewById(R.id.bookingName);
        tvActive = bookEditBottomSheet.findViewById(R.id.tvactive);
        addNew = bookEditBottomSheet.findViewById(R.id.editBookingContinue);

        addNew.setText(appKeysPage.getAddNew());
        editClose.setText(appKeysPage.getBack());

        //New...
        editDate.setText(Utils.dateWithDayString(calSelectedDate));

        bookingName.setText("Book parking");
        if(carParkingForEditResponse !=null && carParkingForEditResponse.size()>0){
            tvActive.setText("Active bookings");
        } else {
            tvActive.setText("No active bookings");
        }


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCarEditList.setLayoutManager(linearLayoutManager);
        rvCarEditList.setHasFixedSize(true);

        //CarListToEditAdapter carListToEditAdapter = new CarListToEditAdapter(getContext(), carParkingForEditResponse, this, code);
        //rvCarEditList.setAdapter(carListToEditAdapter);

        CarListToEditAdapterBooking carListToEditAdapter = new CarListToEditAdapterBooking(getContext(),carParkingForEditResponse,this,code);
        rvCarEditList.setAdapter(carListToEditAdapter);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCarParkLocationsList();
//                if (SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID)!=null)
                newCarBottomSheet(carParkingForEditResponse);
                /*EditBookingDetails editBookingDetails= new EditBookingDetails();
                if (carParkingForEditResponse.size() > 0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                            .getMyto()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                            .getMyto(),12000)));

                }else {
                    if (profileData != null) {
                        editBookingDetails.setEditStartTTime(Utils.splitTime(profileData.getWorkHoursFrom()));
                        editBookingDetails.setEditEndTime(Utils.splitTime(profileData.getWorkHoursTo()));
//                        startRoomTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
//                        endTRoomime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
//                        showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " " + startRoomTime.getText().toString());
                    } else {
                        editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                        //System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
//                                +Utils.getCurrentTime()+"Z");
                        editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                                +Utils.getCurrentTime()+":00Z",1800)));

                    }
                    *//*editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    //System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+"Z");
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+":00Z",12000)));*//*

                }
                changedDeskId=0;
                changedTeamId=0;
                editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                if(SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID) > 0)
                    getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editBookingDetails,"new");
                else
                    getCarParkLocationsList(editBookingDetails,"new");*/
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

    private void newCarBottomSheet(List<CarParkListToEditResponse> carParkingForEditResponse) {
        EditBookingDetails editBookingDetails= new EditBookingDetails();
        if (carParkingForEditResponse.size() > 0){
            editBookingDetails.setEditStartTTime(Utils.splitTime(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                    .getMyto()));
            editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(carParkingForEditResponse.get(carParkingForEditResponse.size()-1)
                    .getMyto(),12000)));

        }else {
            if (profileData != null) {
                editBookingDetails.setEditStartTTime(Utils.splitTime(profileData.getWorkHoursFrom()));
                editBookingDetails.setEditEndTime(Utils.splitTime(profileData.getWorkHoursTo()));
//                        startRoomTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
//                        endTRoomime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
//                        showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " " + startRoomTime.getText().toString());
            } else {
                editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                //System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
//                                +Utils.getCurrentTime()+"Z");
                editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                        +Utils.getCurrentTime()+":00Z",1800)));

            }
                    /*editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                    //System.out.println("tim else" +" "+Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+"Z");
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                            +Utils.getCurrentTime()+":00Z",12000)));*/

        }
        changedDeskId=0;
        changedTeamId=0;
        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));

        if(isGlobalLocationSetUP) {
            if(defaultLocationcheck>0)
                getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.LOCATION_ID_TEMP),editBookingDetails,"new");
            else
                getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.LOCATION_ID),editBookingDetails,"new");
        } else {
            if(SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID) > 0)
                getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editBookingDetails,"new");
            else
                getCarParkLocationsList(editBookingDetails,"new");
        }
    }


    private void callMeetingRoomEditListAdapterBottomSheet(List<MeetingListToEditResponse> meetingListToEditResponseList, String newEditStatus) {
        RecyclerView rvMeeingEditList;
        TextView editClose,editDate,addNew,bookingName, tvActive;
        LinearLayoutManager linearLayoutManager;

        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        bookEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_calendar_edit_booking_bottomsheet,
//                new RelativeLayout(getContext())));

        View view = View.inflate(getContext(), R.layout.dialog_calendar_edit_booking_bottomsheet, null);
        bookEditBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        rvMeeingEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=bookEditBottomSheet.findViewById(R.id.editClose);
        editDate=bookEditBottomSheet.findViewById(R.id.editDate);
        bookingName=bookEditBottomSheet.findViewById(R.id.bookingName);
        tvActive=bookEditBottomSheet.findViewById(R.id.tvactive);
        addNew=bookEditBottomSheet.findViewById(R.id.editBookingContinue);
        editDate.setText(Utils.dateWithDayString(calSelectedDate));
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMeeingEditList.setLayoutManager(linearLayoutManager);
        rvMeeingEditList.setHasFixedSize(true);

        MeetingListToEditAdapter meetingListToEditAdapter=new MeetingListToEditAdapter(getContext(),meetingListToEditResponseList,this);
        rvMeeingEditList.setAdapter(meetingListToEditAdapter);
        meetingListToEditAdapter.notifyDataSetChanged();
        //System.out.println("recycler bala"+meetingListToEditResponseList.size());

        addNew.setText(appKeysPage.getAddNew());
//        editClose.setText(appKeysPage.getBack());
        if(meetingListToEditResponseList !=null && meetingListToEditResponseList.size()>0){
            tvActive.setText("Active bookings");
        } else {
            tvActive.setText("No active bookings");
        }

        bookingName.setText("Book a room");
        editClose.setText("Cancel");


        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEditBottomSheet.dismiss();
            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRoomBookingSheet(meetingListToEditResponseList);
//                chipList.clear();
                /*EditBookingDetails editBookingDetails= new EditBookingDetails();
                if (meetingListToEditResponseList.size() > 0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo(),
                            1800)));
                } else {
                    if (profileData != null) {
                        editBookingDetails.setEditStartTTime(Utils.splitTime(profileData.getWorkHoursFrom()));
                        editBookingDetails.setEditEndTime(Utils.splitTime(profileData.getWorkHoursTo()));
                    } else {
                        editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                        editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                                +Utils.getCurrentTime()+":00Z",1800)));

                    }
                }
                editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                if (isGlobalLocationSetUP)
                    getAvaliableRoomDetails("4",calSelectedDate,editBookingDetails,"new");
                else
                    getRoomlist(editBookingDetails,"new");
*/
            }
        });

        //getMeetingListToEdit


        bookEditBottomSheet.show();
    }

    private void newRoomBookingSheet(List<MeetingListToEditResponse> meetingListToEditResponseList) {
        chipList.clear();
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                if (meetingListToEditResponseList.size() > 0){
                    editBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo()));
                    editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(meetingListToEditResponseList.get(meetingListToEditResponseList.size()-1).getTo(),
                            1800)));
                } else {
                    if (profileData != null) {
                        editBookingDetails.setEditStartTTime(Utils.splitTime(profileData.getWorkHoursFrom()));
                        editBookingDetails.setEditEndTime(Utils.splitTime(profileData.getWorkHoursTo()));
                    } else {
                        editBookingDetails.setEditStartTTime(Utils.getCurrentTime());
                        editBookingDetails.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(Utils.getCurrentDate()+"T"
                                +Utils.getCurrentTime()+":00Z",1800)));

                    }
                }
                editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                if (isGlobalLocationSetUP)
                    getAvaliableRoomDetails("4",calSelectedDate,editBookingDetails,"new");
                else
                    getRoomlist(editBookingDetails,"new");
    }

    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {

        //New...
        chipList.clear();
        if(attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            attendeesListForEdit.clear();
        }

        EditBookingDetails editDeskBookingDetails = new EditBookingDetails();
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingListToEditResponse.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingListToEditResponse.getTo()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(meetingListToEditResponse.getDate()));
        editDeskBookingDetails.setCalId(meetingListToEditResponse.getId());
        editDeskBookingDetails.setMeetingRoomtId(meetingListToEditResponse.getMeetingRoomId());
        editDeskBookingDetails.setRoomName(meetingListToEditResponse.getMeetingRoomName());
        editDeskBookingDetails.setComments(meetingListToEditResponse.getComments());
        MeetingListToEditResponse.Status status = meetingListToEditResponse.getStatus();
        editDeskBookingDetails.setMeetingRoomBookingType(status.getBookingType());


        //New...
        editDeskBookingDetails.setAttendeesList(meetingListToEditResponse.getAttendeesList());
        editDeskBookingDetails.setExternalAttendeesList(meetingListToEditResponse.getExternalAttendeesList());
        editDeskBookingDetails.setComments(meetingListToEditResponse.getComments());
        editDeskBookingDetails.setSubject(meetingListToEditResponse.getSubject());
        selectedDeskId = meetingListToEditResponse.getMeetingRoomId();

        boolean isEditable = false;
        if (meetingListToEditResponse.getBookedByUserId() == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)){
            isEditable  = true;
        } else {
            isEditable  = false;
        }

        EditMeetingRoomController editMeetingRoomController = new EditMeetingRoomController(
                AppConstants.BOOKFRAGMENTINSTANCESTRING,
                activityContext,
                context,
                calSelectedDate,
                selectedDeskId,
                editDeskBookingDetails,
                isEditable
        );
/*
        callAmenitiesListForMeetingRoom(editDeskBookingDetails,
                editDeskBookingDetails.getEditStartTTime(),
                editDeskBookingDetails.getEditEndTime(),
                editDeskBookingDetails.getDate(),
                editDeskBookingDetails.getMeetingRoomtId(),
                0,"edit");*/
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

        //System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,2,"delete");

    }

    private void callAmenitiesListForMeetingRoom(EditBookingDetails editDeskBookingDetails, String editStartTTime,
                                                 String editEndTime,
                                                 Date date,
                                                 int calId, int position, String newEditStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.getAllMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                    try{
                        if (response.code()==200) {
                            if (allMeetingRoomList!=null)
                                allMeetingRoomList.clear();
                            else
                                allMeetingRoomList = new ArrayList<>();

                            allMeetingRoomList = response.body();
                            List<Integer> amenitiesIntList =new ArrayList<>();
                            List<String> amenitiesStringList =new ArrayList<>();
                            goo:
                            for (int i=0; i < allMeetingRoomList.size(); i++) {
                                if (allMeetingRoomList.get(i).getId() == calId) {
                                    editDeskBookingDetails.setCapacity(""+allMeetingRoomList.get(i).getNoOfPeople());
                                    editDeskBookingDetails.setLocationAddress(""+allMeetingRoomList.get(i).getLocationMeeting().getLocationDetails().getBuildingName()+", "
                                            +allMeetingRoomList.get(i).getLocationMeeting().getLocationDetails().getBuildingName());

                                    team:
                                    for (int x=0; x<allMeetingRoomList.get(i).getTeams().size(); x++){
                                        if(allMeetingRoomList.get(i).getTeams().get(x).getId() == myTeamId){
                                            editDeskBookingDetails.setMeetingRoomStatus(1);
                                            break team;
                                        } else {
                                            editDeskBookingDetails.setMeetingRoomStatus(2);
                                        }
                                    }
                                    manager:
                                    for (int x=0; x<allMeetingRoomList.get(i).getManagers().size(); x++){
                                        if(allMeetingRoomList.get(i).getManagers().get(x).getId()
                                                == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)){
                                            editDeskBookingDetails.setMeetingRoomStatus(1);
                                            break manager;
                                        }
                                    }
                                    if(allMeetingRoomList.get(i).getAutomaticApprovalStatus()==2)
                                        editDeskBookingDetails.setMeetingRoomStatus(1);


                                    if (allMeetingRoomList.get(i).getAmenities()!=null){
                                        for (int j=0;j<allMeetingRoomList.get(i).getAmenities().size();j++){
                                            amenitiesIntList.add(allMeetingRoomList.get(i).getAmenities().get(j).getId());
                                        }
                                        break goo;
                                    }
                                }
                            }

                            for (int i=0; i<amenitiesIntList.size();i++) {
                                for (int j=0;j<amenitiesList.size();j++) {
                                    if (amenitiesIntList.get(i) == amenitiesList.get(j).getId()){
                                        //System.out.println("ame list vala"+amenitiesList.get(j).getName());
                                        amenitiesStringList.add(amenitiesList.get(j).getName());
                                    }
                                }
                            }


                        Utils.toastMessage(getActivity(),"welcom bala "+editDeskBookingDetails.getMeetingRoomStatus());
                            editDeskBookingDetails.setAmenities(amenitiesStringList);
//                        Log.d(TAG, "onResponse: amenitySize"+editDeskBookingDetails.getAmenities().size());

                            editBookingUsingBottomSheet(editDeskBookingDetails,2,position,newEditStatus);

                        } else if(response.code()==401){
                            //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                            Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        } else {
//                            Toast.makeText(getActivity(), "elseeee", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception exception){

                    }
                }

                @Override
                public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
                    Log.d("Amen", "onFailure: amen"+t.getMessage());
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
                    try {
                        bookingForEditResponse = response.body();
                        List<BookingForEditResponse.Bookings> bookingsList = new ArrayList<>();
                        for (BookingForEditResponse.Bookings respo: bookingForEditResponse.getBookings()) {
                            if (respo.getUsageTypeId()==2 || respo.getUsageTypeId()==7){
                                bookingsList.add(respo);
                            }
                        }
                        bookingForEditResponse.setBookings(bookingsList);

                        if(bookingDeskList==null)
                            bookingDeskList= new ArrayList<>();

                        if (!isGlobalLocationSetUP) {
                            getDeskList("3",calSelectedDate,"new");
//                            bookingDeskList.clear();
//                            if(response.body().getTeamDeskAvailabilities()!=null)
//                                bookingDeskList = response.body().getTeamDeskAvailabilities();
                        }

                        if (code.equalsIgnoreCase("3")) {
                            if(bookingForEditResponse.getBookings()!=null
                                    && bookingForEditResponse.getBookings().size()>0)
                                callBottomSheetToEdit(bookingForEditResponse, code);
                            else
                                newDeskBookingSheet(bookingForEditResponse, code);
                        } else{
                            if(SessionHandler.getInstance().getBoolean(getContext(),AppConstants.LOGIN_CHECK))
                                deepLinking();
                            else
                                deeplinkLoginPopUP();
                        }

                    } catch (Exception exception){

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
        TextView editClose, editDate, bookingName, addNew, tvActive;
        LinearLayoutManager linearLayoutManager;
        bookingForEditResponseDesk.clear();
        try {
            if (isGlobalLocationSetUP && bookingDeskList!=null && bookingDeskList.size()>0) {
                for (int i=0; i < bookingDeskList.size(); i++){
//                logic to show booked by else
                    /*if(Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                            Utils.getCurrentDate())==2 && bookingDeskList.get(i).isBookedByElse()){

                        if(!(Utils.compareTwoDatesandTime(Utils.getYearMonthDateFormat(
                                Utils.convertStringToDateFormet(calSelectedDate))+"T"+Utils.getCurrentTime()+":00Z",
                                bookingDeskList.get(i).getAvailableTimeSlots()
                                        .get(bookingDeskList.get(i).getAvailableTimeSlots().size() - 1)
                                        .getFrom())==1)){

                            bookingForEditResponseDesk.add(bookingDeskList.get(i));
                        }
                    }*/
                    if(!bookingDeskList.get(i).isBookedByElse()){
                        bookingForEditResponseDesk.add(bookingDeskList.get(i));
                    }
                }
            } else {
                for (int i=0; i<bookingForEditResponse.getTeamDeskAvailabilities().size(); i++){

                    /*if(Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                            Utils.getCurrentDate())==2 && bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){

                        if(!(Utils.compareTwoDatesandTime(Utils.getYearMonthDateFormat(
                                Utils.convertStringToDateFormet(calSelectedDate))+"T"+Utils.getCurrentTime()+":00Z",
                                bookingForEditResponse.getTeamDeskAvailabilities().get(i).getAvailableTimeSlots()
                                        .get(bookingForEditResponse.getTeamDeskAvailabilities().get(i).getAvailableTimeSlots().size() - 1)
                                        .getFrom())==1)){

                            bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
                        }
                    }*/

                    if(!bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){
                        bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
                    }
                }
            }

        } catch (Exception exception){
            Log.d(TAG, "callBottomSheetToEdit: "+exception.getMessage());
        }
//        bookingForEditResponseDesk.addAll(bookingForEditResponse.getTeamDeskAvailabilities());
        bookEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        bookEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_calendar_edit_booking_bottomsheet,
//                new RelativeLayout(getContext())));

        View view = View.inflate(getContext(), R.layout.dialog_calendar_edit_booking_bottomsheet, null);
        bookEditBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        rvEditList = bookEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = bookEditBottomSheet.findViewById(R.id.editClose);
        editDate = bookEditBottomSheet.findViewById(R.id.editDate);
        bookingName = bookEditBottomSheet.findViewById(R.id.bookingName);
        addNew = bookEditBottomSheet.findViewById(R.id.editBookingContinue);
        tvActive = bookEditBottomSheet.findViewById(R.id.tvactive);

        addNew.setText(appKeysPage.getAddNew());
        editClose.setText("Close");
//        editClose.setText(appKeysPage.getBack());


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvEditList.setLayoutManager(linearLayoutManager);
        rvEditList.setHasFixedSize(true);

        BookingListToEditAdapter bookingListToEditAdapter = new BookingListToEditAdapter(getContext(), bookingForEditResponse.getBookings(), this, code, bookingForEditResponse.getTeamDeskAvailabilities());
        rvEditList.setAdapter(bookingListToEditAdapter);
//        editDate.setText(Utils.dateWithDayString(calSelectedDate));
        editDate.setText(Utils.calendarDay10thMonthYearformat
                (Utils.convertStringToDateFormet(calSelectedDate)));

        if(bookingForEditResponse !=null && bookingForEditResponse.getBookings().size()>0){
            tvActive.setText("Active bookings");
        } else {
            tvActive.setText("No active bookings");
        }

        if (code.equals("3")) {
            bookingName.setText("Book a workspace");
        }else if (code.equals("5")) {
            bookingName.setText("Book a room");
        }else if (code.equals("7")) {
            bookingName.setText("Book parking");
        }


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDeskBookingSheet(bookingForEditResponse, code);
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

    private void newDeskBookingSheet(BookingForEditResponse bookingForEditResponse, String code) {
        BookDeskController bookDeskController = new BookDeskController(activityContext, context,bookingForEditResponse,
                AppConstants.BOOKFRAGMENTINSTANCESTRING,isGlobalLocationSetUP,calSelectedDate);
//        bookDeskController.newDeskBookingSheet(bookingForEditResponse,code);
      /*  bookingForEditResponseDesk.clear();
        changedTeamId=0;
        changedDeskId=0;
        selectedDeskId=0;

        if (isGlobalLocationSetUP && bookingDeskList!=null && bookingDeskList.size()>0){
            for (int i=0; i < bookingDeskList.size(); i++){

//                logic to show booked by else
                if(Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                        Utils.getCurrentDate())==2 && bookingDeskList.get(i).isBookedByElse()){

                    if(!(Utils.compareTwoDatesandTime(Utils.getYearMonthDateFormat(
                            Utils.convertStringToDateFormet(calSelectedDate))+"T"+Utils.getCurrentTime()+":00Z",
                            bookingDeskList.get(i).getAvailableTimeSlots()
                                    .get(bookingDeskList.get(i).getAvailableTimeSlots().size() - 1)
                                    .getFrom())==1)){

                        bookingForEditResponseDesk.add(bookingDeskList.get(i));
                    }
                }

                if(!bookingDeskList.get(i).isBookedByElse()){
                    bookingForEditResponseDesk.add(bookingDeskList.get(i));
                }
            }
        } else {
            for (int i=0; i<bookingForEditResponse.getTeamDeskAvailabilities().size(); i++) {
//                logic to show booked by else
                if(Utils.compareTwoDate(Utils.convertStringToDateFormet(calSelectedDate),
                        Utils.getCurrentDate())==2 && bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){

                    if(!(Utils.compareTwoDatesandTime(Utils.getYearMonthDateFormat(
                            Utils.convertStringToDateFormet(calSelectedDate))+"T"+Utils.getCurrentTime()+":00Z",
                            bookingForEditResponse.getTeamDeskAvailabilities().get(i).getAvailableTimeSlots()
                                    .get(bookingForEditResponse.getTeamDeskAvailabilities().get(i).getAvailableTimeSlots().size() - 1)
                                    .getFrom())==1)){

                        bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
                    }
                }

                if(!bookingForEditResponse.getTeamDeskAvailabilities().get(i).isBookedByElse()){
                    bookingForEditResponseDesk.add(bookingForEditResponse.getTeamDeskAvailabilities().get(i));
                }
            }
        }


        editBookingDetailsGlobal = new EditBookingDetails();
        loop:
        for (int i=0; i<bookingForEditResponseDesk.size();i++) {
            if (bookingForEditResponse.getUserPreferences().getTeamDeskId()
                    == bookingForEditResponseDesk.get(i).getTeamDeskId()) {
//                        Toast.makeText(getContext(), " "+bookingForEditResponseDesk.get(i).getDeskCode(), Toast.LENGTH_SHORT).show();
                if (bookingForEditResponse.getBookings().size() > 0){
                    editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                            .getMyto()));

                    editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                            .getMyto(),2)));
                } else {
                    editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                    editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                }

                selectedTeamId = bookingForEditResponseDesk.get(i).getTeamId();
                globalSelectedTeamId = bookingForEditResponseDesk.get(i).getTeamId();
                for (int x=0; x<activeTeamsList.size(); x++) {
                    if (selectedTeamId == activeTeamsList.get(x).getId()) {
                        selectedTeamName = activeTeamsList.get(x).getName();
                        selectedTeamAutoApproveStatus = activeTeamsList.get(x).getAutomaticApprovalStatus();
                    }
                }
                editBookingDetailsGlobal.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                editBookingDetailsGlobal.setCalId(0);
                editBookingDetailsGlobal.setDescription(Utils.checkStringParms(bookingForEditResponseDesk.get(i).getDescription()));
                try {
                    editBookingDetailsGlobal.setLocationAddress(bookingForEditResponseDesk.get(i).getLocationDetails().getBuildingName()
                            +","+bookingForEditResponseDesk.get(i).getLocationDetails().getfLoorName());
                }catch (Exception e){

                }
                editBookingDetailsGlobal.setDeskCode(bookingForEditResponseDesk.get(i).getDeskCode());
                editBookingDetailsGlobal.setDesktId(bookingForEditResponseDesk.get(i).getTeamDeskId());
                editBookingDetailsGlobal.setTimeZone(bookingForEditResponseDesk.get(i).getTimeZones().get(0).getTimeZoneId());
                editBookingDetailsGlobal.setDeskTeamId(bookingForEditResponseDesk.get(i).getTeamId());
                selectedDeskId = bookingForEditResponseDesk.get(i).getTeamDeskId();
                editBookingDetailsGlobal.setDeskStatus(0);
                Log.d(TAG," desk id check if "+bookingForEditResponseDesk.get(i).getTeamDeskId());
                break loop;
            } else {
                int pos =0;
                out:
                for (int t=0; t < bookingForEditResponseDesk.size(); t++) {
                    if (bookingForEditResponseDesk.get(t).getTeamId()
                            == SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID)){
                        pos = t;
                    }
                }

                selectedTeamId = bookingForEditResponseDesk.get(pos).getTeamId();
                globalSelectedTeamId = bookingForEditResponseDesk.get(pos).getTeamId();
                for (int x=0; x<activeTeamsList.size(); x++) {
                    if (selectedTeamId == activeTeamsList.get(x).getId()) {
                        selectedTeamName = activeTeamsList.get(x).getName();
                        selectedTeamAutoApproveStatus = activeTeamsList.get(x).getAutomaticApprovalStatus();
                    }
                }
                editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
                editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
                editBookingDetailsGlobal.setDeskCode(bookingForEditResponseDesk.get(pos).getDeskCode());
                editBookingDetailsGlobal.setDesktId(bookingForEditResponseDesk.get(pos).getTeamDeskId());
                editBookingDetailsGlobal.setDeskTeamId(bookingForEditResponseDesk.get(pos).getTeamId());
                try {
                    editBookingDetailsGlobal.setLocationAddress(bookingForEditResponseDesk.get(pos).getLocationDetails().getBuildingName()
                            +","+bookingForEditResponseDesk.get(pos).getLocationDetails().getfLoorName());
                } catch (Exception e){

                }
                editBookingDetailsGlobal.setTimeZone(bookingForEditResponseDesk.get(i).getTimeZones().get(0).getTimeZoneId());
                selectedDeskId = bookingForEditResponseDesk.get(pos).getTeamDeskId();
                editBookingDetailsGlobal.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                editBookingDetailsGlobal.setCalId(0);
                editBookingDetailsGlobal.setDeskStatus(0);

            }
        }

        if (bookingForEditResponse.getBookings().size() > 0){

            editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                    .getMyto()));

            editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(
                    Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(
                            bookingForEditResponse.getBookings().size()-1)
                            .getMyto(),2)));
        }

        if (bookingForEditResponse.getBookings().size()==0) {
            editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursFrom()));
            editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));

            editBookingDetailsGlobal.setDate(Utils.convertStringToDateFormet(calSelectedDate));
            editBookingDetailsGlobal.setCalId(0);
            editBookingDetailsGlobal.setDeskStatus(0);
        }



        if(isGlobalLocationSetUP) {
            selectedTeamId = globalSelectedTeamId;
//            Toast.makeText(context, ""+selectedTeamId, Toast.LENGTH_SHORT).show();
            for (int i=0; i<activeTeamsList.size(); i++) {
                if (selectedTeamId==activeTeamsList.get(i).getId()) {
                    selectedTeamName = activeTeamsList.get(i).getName();
                    selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
                }
            }
            if (selectedTeamId !=
                    SessionHandler.getInstance().getInt(activityContext, AppConstants.TEAM_ID)){
                if(selectedTeamAutoApproveStatus == 3) {
                    editBookingUsingBottomSheet(editBookingDetailsGlobal,
                            1, 0, "request");
                } else if(selectedTeamAutoApproveStatus != 2) {
                    Log.d(TAG,"if");
                    editBookingUsingBottomSheet(editBookingDetailsGlobal,
                            1, 0, "request");
                } else {
                    Log.d(TAG, "else");
                    editBookingUsingBottomSheet(editBookingDetailsGlobal,
                            1, 0, "new");
                }
            } else{
                editBookingUsingBottomSheet(editBookingDetailsGlobal,
                        1, 0, "new");
            }

        } else {
            selectedTeamId = SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID);
            changedTeamId=0;
            changedDeskId=0;
//                    Toast.makeText(context, "else da" , Toast.LENGTH_SHORT).show();
            editBookingUsingBottomSheet(editBookingDetailsGlobal,
                    1, 0, "new");
        }*/
    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails, int dskRoomParkStatus, int position,String newEditStatus) {
        endDisabled=false;
        startDisabled=false;
        selectDisabled=false;

        roomBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        roomBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_calendar_bottom_sheet_edit_booking,
//                new RelativeLayout(getContext()))));
        View view = View.inflate(getContext(), R.layout.dialog_calendar_bottom_sheet_edit_booking, null);
        roomBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //Language
        TextView tv_start=roomBottomSheet.findViewById(R.id.tv_start);
        TextView tv_end=roomBottomSheet.findViewById(R.id.tv_end);
        TextView tv_comment=roomBottomSheet.findViewById(R.id.tv_comment);
        TextView tv_repeat=roomBottomSheet.findViewById(R.id.tv_repeat);
        tv_description=roomBottomSheet.findViewById(R.id.tv_description);
        repeat = roomBottomSheet.findViewById(R.id.repeat);
        deskStatusText = roomBottomSheet.findViewById(R.id.desk_status_text);
        deskStatusDot = roomBottomSheet.findViewById(R.id.user_status_dot);
        continueEditBook=roomBottomSheet.findViewById(R.id.editBookingContinue);
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
        TextView locationAddressTop=roomBottomSheet.findViewById(R.id.locationAddress);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        //New...
//        locationAddress.setVisibility(View.GONE);

        date=roomBottomSheet.findViewById(R.id.date);
        TextView title=roomBottomSheet.findViewById(R.id.title);
        TextView checkInDate=roomBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=roomBottomSheet.findViewById(R.id.showCheckInDate);

        TextView select=roomBottomSheet.findViewById(R.id.select_desk_room);

        TextView tvDelete=roomBottomSheet.findViewById(R.id.delete_text);
        tvDelete.setVisibility(View.GONE);
        TextView tvComments=roomBottomSheet.findViewById(R.id.tv_comments);
        EditText edComments=roomBottomSheet.findViewById(R.id.comments);
        EditText commentRegistration=roomBottomSheet.findViewById(R.id.ed_registration);
        EditText edRegistration=roomBottomSheet.findViewById(R.id.et_registration_num);
        RelativeLayout registrationLayout=roomBottomSheet.findViewById(R.id.registrationLayout);
        RelativeLayout repeatBlock=roomBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=roomBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=roomBottomSheet.findViewById(R.id.rl_teams_layout);
        CheckBox teamsCheckBox = roomBottomSheet.findViewById(R.id.teams_check_box);
        RelativeLayout dateBlock = roomBottomSheet.findViewById(R.id.bookingDateBlock);
        LinearLayout statusCheckLayout=roomBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=roomBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=roomBottomSheet.findViewById(R.id.capacity_layout);
        tvcapacityCount=roomBottomSheet.findViewById(R.id.tv_capacity_no);

        chipGroup = roomBottomSheet.findViewById(R.id.list_item);
        showcheckInDate.setText(Utils.showBottomSheetDate(calSelectedDate));
        checkInDate.setText("");

        if (editDeskBookingDetails.getDeskStatus() == 1){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=true;
            endDisabled=true;
            selectDisabled=true;
//            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlueText));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlueText));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=false;
            endDisabled=false;
            selectDisabled=true;
//            chipGroup.setVisibility(View.VISIBLE);
        } else {
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlueText));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlueText));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=false;
            endDisabled=false;
//            chipGroup.setVisibility(View.GONE);
        }

        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditStartTTime()
        ) && newEditStatus.equalsIgnoreCase("edit")) {
            /*startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            startDisabled=true;
            selectDisabled=true;*/
        }
        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditEndTime()
        ) && newEditStatus.equalsIgnoreCase("edit")) {
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            startDisabled=true;

            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
//            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endDisabled=true;
//            selectDisabled=true;
        }


        if (newEditStatus.equalsIgnoreCase("new_deep_link")) {
            select.setVisibility(View.GONE);
            dateBlock.setVisibility(View.VISIBLE);
            deskStatusText.setVisibility(View.INVISIBLE);
            deskStatusDot.setVisibility(View.INVISIBLE);

        } else if (newEditStatus.equalsIgnoreCase("request") ||
                newEditStatus.equalsIgnoreCase("new") ) {
            deskStatusText.setVisibility(View.VISIBLE);
            deskStatusDot.setVisibility(View.VISIBLE);
            if (selectedTeamId !=
                    SessionHandler.getInstance().getInt(activityContext, AppConstants.TEAM_ID)){
                if(selectedTeamAutoApproveStatus == 3) {
                    deskStatusText.setText("Not Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_red));
                    continueEditBook.setVisibility(View.GONE);
                } else if(selectedTeamAutoApproveStatus != 2) {
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                    continueEditBook.setVisibility(View.VISIBLE);
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                    continueEditBook.setVisibility(View.VISIBLE);
                }
            } else {
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                continueEditBook.setVisibility(View.VISIBLE);
            }

            select.setVisibility(View.VISIBLE);
            dateBlock.setVisibility(View.GONE);
        } else {
            dateBlock.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }

        if (isGlobalLocationSetUP)
            select.setVisibility(View.VISIBLE);

        if (editDeskBookingDetails.getLocationAddress()!=null &&
                !editDeskBookingDetails.getLocationAddress().isEmpty()
                && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase(""))
            locationAddressTop.setText(""+editDeskBookingDetails.getLocationAddress());

        if (dskRoomParkStatus == 1) {
//            Toast.makeText(context, ""+editDeskBookingDetails.getLocationAddress(), Toast.LENGTH_SHORT).show();
            if (editDeskBookingDetails.getLocationAddress()!=null &&
                    !editDeskBookingDetails.getLocationAddress().isEmpty()
                    && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase(""))
                locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());
            tv_description.setText(editDeskBookingDetails.getDescription());

            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));
                repeatBlock.setVisibility(View.GONE);
                commentBlock.setVisibility(View.GONE);
                select.setText("Select");
                if (editDeskBookingDetails.getUsageTypeId() == 7) {
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                }

            }else {
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthformat(editDeskBookingDetails.getDate()));

                commentBlock.setVisibility(View.GONE);
                if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
                    repeatBlock.setVisibility(View.VISIBLE);
                } else {
                    repeatBlock.setVisibility(View.GONE);
                }
            }


            if (newEditStatus.equalsIgnoreCase("edit")
                    && !(editDeskBookingDetails.getUsageTypeId()== 2 ||
                    editDeskBookingDetails.getUsageTypeId()== 7)){
                select.setVisibility(View.GONE);
            } else {
                select.setVisibility(View.VISIBLE);
            }

            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getUsageTypeId()==2
                        && editDeskBookingDetails.getRequestedTeamId()>0){
                    select.setTextColor((getActivity().getResources().getColor(R.color.figmaGrey)));
                    selectDisabled=true;
                } else if(editDeskBookingDetails.getUsageTypeId()==7){
                    select.setTextColor((getActivity().getResources().getColor(R.color.figmaBlueText)));
                    selectDisabled=false;
                } else {
                    select.setTextColor((getActivity().getResources().getColor(R.color.figmaBlueText)));
                    selectDisabled=false;
                }
            }


            teamsBlock.setVisibility(View.GONE);
//            commentBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");
            capacitylayout.setVisibility(View.GONE);
            chipGroup.setVisibility(View.INVISIBLE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);

            if (editDeskBookingDetails.getDeskCode()!=null && !editDeskBookingDetails.getDeskCode().isEmpty()
                    && !editDeskBookingDetails.getDeskCode().equalsIgnoreCase(""))
                deskRoomName.setText(editDeskBookingDetails.getDeskCode());
            else
                deskRoomName.setText("No Available desk/Room");

            selectedDeskId=editDeskBookingDetails.getDesktId();
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")) {
                title.setText("Book a workspace");
                continueEditBook.setText("Book");
                back.setText("Close");
            } else {
                if (editDeskBookingDetails.getComments() != null &&
                        !editDeskBookingDetails.getComments().equalsIgnoreCase("")&&
                        !editDeskBookingDetails.getComments().isEmpty())
                    edComments.setText(editDeskBookingDetails.getComments());
                continueEditBook.setText("Continue");
                back.setText("Back");
            }

        }else if (dskRoomParkStatus==2) {
            if(editDeskBookingDetails.getMeetingRoomStatus() == 2){
                deskStatusText.setText("Available For Request");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                continueEditBook.setVisibility(View.VISIBLE);
            } else {
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                continueEditBook.setVisibility(View.VISIBLE);
            }

            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));

                repeatBlock.setVisibility(View.GONE);
                select.setVisibility(View.GONE);
                commentBlock.setVisibility(View.GONE);
            }else{
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthformat(editDeskBookingDetails.getDate()));

                commentBlock.setVisibility(View.GONE);
                if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
                    repeatBlock.setVisibility(View.VISIBLE);
                } else {
                    repeatBlock.setVisibility(View.GONE);
                }
            }

            llDeskLayout.setVisibility(View.VISIBLE);
            commentRegistration.setVisibility(View.VISIBLE);
            tvComments.setVisibility(View.GONE);
            if(teamsCheckBoxStatus)
                teamsBlock.setVisibility(View.VISIBLE);
            else
                teamsBlock.setVisibility(View.GONE);

            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.GONE);
            tvcapacityCount.setText(editDeskBookingDetails.getCapacity());
            if (userAllowedMeetingResponseListUpdated.size() > 0) {
//                //System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseListUpdated.get(0).getName());
                selectedDeskId = userAllowedMeetingResponseListUpdated.get(0).getId();
                try {
                    locationAddress.setText(""+userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getLocationDetails().getBuildingName()
                            +", " +userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getLocationDetails().getFloorName());
                    tv_description.setText(Utils.checkStringParms(userAllowedMeetingResponseListUpdated.get(0).getDescription()));
                } catch (Exception c){

                }
            }else {
                deskRoomName.setText("No Available Desk/Room");
            }


            if (newEditStatus.equalsIgnoreCase("edit")) {
                deskRoomName.setText(""+editDeskBookingDetails.getRoomName());
            }
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book a room");
                continueEditBook.setText("Continue");
                back.setText("Cancel");
            } else {
                if (editDeskBookingDetails.getComments() != null &&
                        !editDeskBookingDetails.getComments().equalsIgnoreCase("")&&
                        !editDeskBookingDetails.getComments().isEmpty())
                    edComments.setText(editDeskBookingDetails.getComments());
                continueEditBook.setText("Continue");
                back.setText("Cancel");
            }
        } else {

            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));

                tvDelete.setVisibility(View.VISIBLE);
                repeatBlock.setVisibility(View.GONE);
                select.setVisibility(View.GONE);
                commentBlock.setVisibility(View.GONE);
                edRegistration.setText(editDeskBookingDetails.getVehicleRegNumber());
            }else{
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthformat(editDeskBookingDetails.getDate()));

                commentBlock.setVisibility(View.GONE);
                if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
                    repeatBlock.setVisibility(View.VISIBLE);
                } else {
                    repeatBlock.setVisibility(View.GONE);
                }
            }
            edRegistration.setVisibility(View.VISIBLE);
            registrationLayout.setVisibility(View.VISIBLE);
            llDeskLayout.setVisibility(View.VISIBLE);
//            repeatBlock.setVisibility(View.VISIBLE);
            teamsBlock.setVisibility(View.GONE);
            edRegistration.setHint("Registration");
//            commentBlock.setVisibility(View.GONE);
//            commentRegistration.setHint("Registration Number");
//            tvComments.setText("Regitration Number");
//            if (profileData != null)
//                commentRegistration.setText(profileData.getVehicleRegNumber());
            chipGroup.setVisibility(View.INVISIBLE);
            capacitylayout.setVisibility(View.GONE);
            if(newEditStatus.equalsIgnoreCase("new") ||newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")){
                title.setText("Book Parking");
                continueEditBook.setText("Book");
                back.setText("Close");
            } else {

                if (editDeskBookingDetails.getComments() != null &&
                        !editDeskBookingDetails.getComments().equalsIgnoreCase("")&&
                        !editDeskBookingDetails.getComments().isEmpty())
                    edComments.setText(editDeskBookingDetails.getComments());
                title.setText("Edit your booking");
                continueEditBook.setText("Continue");
                back.setText("Back");
            }
//            //System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
            if (parkingSpotModelList.size() > 0 && !newEditStatus.equalsIgnoreCase("edit")) {
                if (parkingSpotModelList.get(0).getParkingSlotAvailability()==1) {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                } else {
                    if(parkingSpotModelList.get(0).getAssignees().size()>0){
                        loop:
                        for (int i=0; i < parkingSpotModelList.get(0).getAssignees().size(); i++) {
                            if (parkingSpotModelList.get(0).getAssignees().get(i).getId() == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)){
                                deskStatusText.setText("Available");
                                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                                break loop;
                            } else {
                                deskStatusText.setText("Available For Request");
                                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                            }
                        }
                    } else {
                        deskStatusText.setText("Available For Request");
                        deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                    }
//                    deskStatusText.setText("Available For Request");
//                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                }

//                //System.out.println("tim else"+
                select.setVisibility(View.VISIBLE);
                deskRoomName.setText(""+parkingSpotModelList.get(0).getCode());
                selectedDeskId = parkingSpotModelList.get(0).getId();
                try {
                    locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());
                    locationAddressTop.setText(""+parkingSpotModelList.get(0).getLocation().getName());
                    tv_description.setText(""+parkingSpotModelList.get(0).getDescription());
                } catch (Exception e){

                }
            } else {
                select.setVisibility(View.GONE);
//                title.setText("Edit Parking Details");
                deskRoomName.setText(""+editDeskBookingDetails.getParkingSlotCode());
                selectedDeskId = editDeskBookingDetails.getParkingSlotId();
                try {
                    locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());
                    tv_description.setText(""+editDeskBookingDetails.getDescription());
                }catch (Exception e){

                }
            }
        }

        //Logic for start time and end time of DESK and MEETING ROOM
        if(dskRoomParkStatus==2) {
            if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
//                startTime.setText(Utils.convert24HrsTO12Hrs(Utils.currentTimeWithExtraMins(2)));
                startTime.setText(Utils.setNearestThirtyMinToMeeting(Utils.getCurrentTime()));
//                endTime.setText(Utils.setStartNearestFiveMinToMeeting(Utils.currentTimeWithExtraMins(32)));
                endTime.setText(Utils.selectedTimeWithExtraMins(startTime.getText().toString(),30));
//                endTime.setText(Utils.convert24HrsTO12Hrs(Utils.setStartNearestFiveMinToMeeting(Utils.currentTimeWithExtraMins(32))));
            } else {

                if (editDeskBookingDetails.getEditStartTTime()!=null) {
//                    startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
                    startTime.setText(editDeskBookingDetails.getEditStartTTime());
//                    endTime.setText(Utils.convert24HrsTO12Hrs(Utils.setStartNearestThirtyMinToMeeting(Utils.convert12HrsTO24Hrs(startTime.getText().toString()))));
                    if(!newEditStatus.equalsIgnoreCase("edit") &&
                            Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                                    editDeskBookingDetails.getEditStartTTime())
                    ){
//                        startTime.setText(Utils.convert24HrsTO12Hrs(Utils.currentTimeWithExtraMins(30)));
                    }
                }

                if (editDeskBookingDetails.getEditEndTime()!=null) {
//                    endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
                    endTime.setText(editDeskBookingDetails.getEditEndTime());
                    if(!newEditStatus.equalsIgnoreCase("edit") &&
                            Utils.compareTimeIfCheckInEnable(""+startTime.getText(),
                                    editDeskBookingDetails.getEditEndTime())) {
//                        if (Utils.compareTimeIfCheckInEnable(Utils.convert12HrsTO24Hrs(""+startTime.getText()),
//                                Utils.addHoursToSelectedTimeWithMinutes(Utils.convert12HrsTO24Hrs(""+startTime.getText()), 30))) {
//                            endTime.setText(Utils.convert24HrsTO12Hrs("23:59"));
//                        } else {
//                            endTime.setText(Utils.convert24HrsTO12Hrs(Utils.addHoursToSelectedTimeWithMinutes(Utils.convert12HrsTO24Hrs(""+startTime.getText()), 30)));
//                        }
                    }
                }
            }

        } else {
            if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
//                startTime.setText(Utils.convert24HrsTO12Hrs(Utils.currentTimeWithExtraMins(2)));
//                startTime.setText(Utils.currentTimeWithExtraMins(2));
                startTime.setText(Utils.setStartNearestThirtyMinToMeeting(Utils.getCurrentTime()));
//                endTime.setText(Utils.convert24HrsTO12Hrs(Utils.setStartNearestFiveMinToMeeting(Utils.currentTimeWithExtraMins(32))));
                endTime.setText(Utils.setStartNearestThirtyMinToMeeting(startTime.getText().toString()));

            }
//            Toast.makeText(context, " ssd "+editDeskBookingDetails.getEditStartTTime(), Toast.LENGTH_SHORT).show();
            if (editDeskBookingDetails.getEditStartTTime()!=null) {
//                startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
                startTime.setText(editDeskBookingDetails.getEditStartTTime());
                if(!newEditStatus.equalsIgnoreCase("edit") &&
                        Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                                editDeskBookingDetails.getEditStartTTime())
                        && Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2)
                    startTime.setText(Utils.currentTimeWithExtraMins(2));
            }
            if (editDeskBookingDetails.getEditEndTime()!=null) {
//                endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
                endTime.setText(editDeskBookingDetails.getEditEndTime());
                if(!newEditStatus.equalsIgnoreCase("edit") &&
                        Utils.compareTimeIfCheckInEnable(""+startTime.getText(),
                                editDeskBookingDetails.getEditEndTime())) {
                    if (Utils.compareTimeIfCheckInEnable(""+startTime.getText(),
                            Utils.addHoursToSelectedTime(""+startTime.getText(), 4))){
                        endTime.setText("23:59");
                    } else {
                        endTime.setText(Utils.addHoursToSelectedTime(""+startTime.getText(), 4));
                    }
                }
            }
        }


        if (editDeskBookingDetails.getAmenities()!=null)
            //System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
            if (editDeskBookingDetails.getAmenities()!=null){
                for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                    ShapeAppearanceModel shapeAppearanceModel= new ShapeAppearanceModel()
                            .toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED,15)
                            .build();
                    Chip chip = new Chip(getContext());
                    chip.setId(i);
                    chip.setTextAppearance(R.style.chipText);
                    chip.setShapeAppearanceModel(shapeAppearanceModel);
                    chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                    chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                    chip.setCloseIconVisible(false);
                    chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                    chipGroup.addView(chip);
                }
            }

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar(editDeskBookingDetails.getCalId());
            }
        });
        showcheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", checkInDate,showcheckInDate);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startDisabled){
                    if(dskRoomParkStatus==2){
                        Utils.bottomSheetTimePickerMeetingRoom(getContext(),
                                getActivity(),startTime,endTime,"Start Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                    } else {
                        if (dskRoomParkStatus == 1
                                && newEditStatus.equalsIgnoreCase("edit")) {
                            if (editDeskBookingDetails.getUsageTypeId()==2
                                    && editDeskBookingDetails.getRequestedTeamId()>0) {
                                Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),startTime,"Start Time",
                                        Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                            } else {

                                Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),startTime,"Start Time",
                                        Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
                            }
                        } else
                            Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);

/*
                        if (editDeskBookingDetails.getDeskBookingType()!=null
                                && editDeskBookingDetails.getDeskStatus() != 1
                                && editDeskBookingDetails.getDeskStatus() != 2
                                && editDeskBookingDetails.getDeskBookingType().equalsIgnoreCase("req"))
                            Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
*/


                    }
                }

//                Toast.makeText(context, "fsf"+editDeskBookingDetails.getRequestedTeamDeskId(), Toast.LENGTH_SHORT).show();

//                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!endDisabled) {
                    if (dskRoomParkStatus == 1
                            && newEditStatus.equalsIgnoreCase("edit")) {
                        if (editDeskBookingDetails.getUsageTypeId()==2
                                && editDeskBookingDetails.getRequestedTeamId()>0) {

                            Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                        } else {

                            Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
                        }
                    } else
                        Utils.bottomSheetTimePicker24Hrs(getContext(),getActivity(),endTime,"End Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);

                }
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
                editDeskBookingDetails.setDisplayTime(startTime.getText()+" to "+endTime.getText());
                if(locationAddress!=null)
                    editDeskBookingDetails.setLocationAddress(locationAddress.getText().toString());

                if (selectedicon==2 && newEditStatus.equalsIgnoreCase("new")) {
                    if (editDeskBookingDetails.getMeetingRoomStatus() == 2) {
                        isMeetingRequest = true;
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime, endTime, selectedDeskId,
                                deskRoomName.getText().toString(), isMeetingRequest,
                                editDeskBookingDetails.getCalId(), newEditStatus);
                    } else {
                        isMeetingRequest = false;
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime, endTime, selectedDeskId,
                                deskRoomName.getText().toString(), isMeetingRequest,
                                editDeskBookingDetails.getCalId(), newEditStatus);
                    }
                } else if (selectedicon==2) {
                    if (newEditStatus.equalsIgnoreCase("request")) {
                        isMeetingRequest=true;
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), isMeetingRequest, editDeskBookingDetails.getCalId(), newEditStatus);
                    } else if(newEditStatus.equalsIgnoreCase("edit")){
                        isMeetingRequest = true;
                        if(editDeskBookingDetails.getMeetingRoomBookingType().equalsIgnoreCase("req")) {
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedDeskId, deskRoomName.getText().toString(), isMeetingRequest, editDeskBookingDetails.getCalId(), newEditStatus);
                        }
                        else {
                            isMeetingRequest = false;
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedDeskId, deskRoomName.getText().toString(), isMeetingRequest,editDeskBookingDetails.getCalId(),newEditStatus);
                        }
                    }
                }else if(selectedicon==1) {
                    if (repeatActvieStatus) {
                        if (dskRoomParkStatus==1){
                            doRepeatDeskBookingForAWeek(editDeskBookingDetails,newEditStatus);

                        } else if (dskRoomParkStatus==3)
                            doRepeatCarBookingForAWeek(edRegistration.getText().toString());

                    } else {
                        if (newEditStatus.equalsIgnoreCase("edit"))
                            editBookingCallForDesk(editDeskBookingDetails,edComments);
                        else if (newEditStatus.equalsIgnoreCase("new"))
                            newBookingCallForDesk(editDeskBookingDetails,edComments);
                        else
                            requestBookingCallForDesk(editDeskBookingDetails,edComments);
                    }
                } else {
                    if (repeatActvieStatus) {
                        if (dskRoomParkStatus==1)
                            doRepeatDeskBookingForAWeek(editDeskBookingDetails,newEditStatus);
                        else if (dskRoomParkStatus==3)
                            doRepeatCarBookingForAWeek(edRegistration.getText().toString());

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
                                jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");
                            }else {
                                jsonInnerObject.addProperty("date",""+checkInDate.getText().toString()+"T00:00:00.000Z");
                            }
                        }else {

                            jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");
                        }
                        switch (dskRoomParkStatus){
                            case 1:
                                jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                                jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
                                if (!edComments.getText().toString().trim().equalsIgnoreCase("") || !edComments.getText().toString().trim().isEmpty())
                                    jsonChangesObject.addProperty("comments",edComments.getText().toString());
                                if (!commentRegistration.getText().toString().isEmpty() &&
                                        !commentRegistration.getText().toString().equalsIgnoreCase(""))
                                    jsonChangesObject.addProperty("comments",commentRegistration.getText().toString());
                                if (selectedDeskId!=0 && dskRoomParkStatus==1
                                        && selectedDeskId != editDeskBookingDetails.getDesktId()) {
                                    if (editDeskBookingDetails.getRequestedTeamId()>0) {
                                        Log.d("bookei chock 1st if",""+selectedDeskId);
                                        jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
                                        jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    } else
                                        jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                                }
                                if (((newEditStatus.equalsIgnoreCase("request")
                                        && editDeskBookingDetails.getRequestedTeamId()>0))){
                                    Log.d("bookei chock 2st if",""+selectedDeskId);
                                    jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
                                    jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    if(!newEditStatus.equalsIgnoreCase("edit"))
                                        jsonChangesObject.addProperty("usageTypeId", "7");
                                    jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
//                                jsonChangesObject.addProperty("typeOfCheckIn", "1");
                                } else if ((newEditStatus.equalsIgnoreCase("edit")
                                        && editDeskBookingDetails.getRequestedTeamId()>0
                                )){
                                    if(changedDeskId > 0 && defaultEditDeskId != changedDeskId){
                                        if(changedTeamId != SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                                            Log.d("bookei chock 3st if",""+selectedDeskId);
                                            jsonChangesObject.addProperty("requestedTeamDeskId",changedDeskId);
                                        } else {
                                            jsonChangesObject.addProperty("teamDeskId",changedDeskId);
                                        }
                                    } else {
                                        String no=null;
                                        jsonChangesObject.addProperty("requestedTeamDeskId",no);
                                        jsonChangesObject.addProperty("requestedTeamDeskId",no);
                                    }
                                    /*if(selectedDeskId != editDeskBookingDetails.getDesktId()){
                                        jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
//                                        jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    }else {
                                        String team=null;
                                    }*/
                                    jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
//                                jsonChangesObject.addProperty("typeOfCheckIn", "1");
                                }else if (isGlobalLocationSetUP){
                                    /*
                                        jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                        jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                        if(!newEditStatus.equalsIgnoreCase("edit"))
                                        jsonChangesObject.addProperty("usageTypeId", "7");
                                        jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
                                    */
                                }else{
                                    if (!newEditStatus.equalsIgnoreCase("edit") && selectedDeskId!=0){
                                        if (changedDeskId>0)
                                            if (editDeskBookingDetails.getRequestedTeamId()>0){
                                                Log.d("bookei chock 6st if",""+selectedDeskId);

                                                jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
                                                jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                            } else {
                                                jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                                            }
                                    }
                                    else if (selectedDeskId != editDeskBookingDetails.getDesktId()){
                                        if (editDeskBookingDetails.getRequestedTeamId()>0){
                                            Log.d("bookei chock 7st if",""+selectedDeskId);

                                            jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
                                            jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                        } else {
                                            jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                                        }
                                    }
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

                                if (!edRegistration.getText().toString().isEmpty() &&
                                        !edRegistration.getText().toString().equalsIgnoreCase(""))
                                    jsonChangesObject.addProperty("vehicleRegNumber",edRegistration.getText().toString());
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
                            if(editDeskBookingDetails.getRequestedTeamId()>0)
                                jsonChangesObject.addProperty("usageTypeId", "7");
                            else
                                jsonChangesObject.addProperty("usageTypeId", "2");
                            jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
                        }
                        if (!editDeskBookingDetails.getEditStartTTime().equalsIgnoreCase(startTime.getText().toString())
                                || newEditStatus.equalsIgnoreCase("new")
                                || newEditStatus.equalsIgnoreCase("new_deep_link")
                                || newEditStatus.equalsIgnoreCase("request")
                        ){
                            jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
                        }if (!editDeskBookingDetails.getEditEndTime().equalsIgnoreCase(endTime.getText().toString())
                                || newEditStatus.equalsIgnoreCase("new")
                                || newEditStatus.equalsIgnoreCase("new_deep_link")
                                || newEditStatus.equalsIgnoreCase("request")
                        ){
                            jsonChangesObject.addProperty("to","2000-01-01T"+endTime.getText().toString()+":00.000Z");
                        }

                        jsonInnerObject.add("changes",jsonChangesObject);
                        jsonChangesetArray.add(jsonInnerObject);

                        jsonOuterObject.add("changesets", jsonChangesetArray);
                        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

                        //System.out.println("json un" + jsonOuterObject.toString());

                        if (dskRoomParkStatus==3 && isVehicleReg
                                && (edRegistration.getText().toString().isEmpty()
                                || edRegistration.getText().toString().equalsIgnoreCase(""))){
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
                if(!selectDisabled){
                    editDeskBookingDetails.setDisplayTime(startTime.getText().toString()+" to "+endTime.getText().toString());
                    if (dskRoomParkStatus==1){
                        if (isGlobalLocationSetUP){
                            selectedDeskList(selectedTeamId,
                                    Utils.getISO8601format(editDeskBookingDetails.getDate()),
                                    editDeskBookingDetails,newEditStatus);
                        } else {
                            if (editDeskBookingDetails.getRequestedTeamId()>0)
                                selectedDeskList(editDeskBookingDetails.getRequestedTeamId(),
                                        Utils.getISO8601format(editDeskBookingDetails.getDate()),
                                        editDeskBookingDetails,newEditStatus);
                            else
                                selectedDeskList(selectedTeamId,
                                        Utils.getISO8601format(editDeskBookingDetails.getDate())
                                        ,editDeskBookingDetails,newEditStatus);
                        }
                    } else {
                        if (editDeskBookingDetails.getDeskStatus()!=1 && editDeskBookingDetails.getDeskStatus()!=2)
                            callDeskBottomSheetDialog();
                    }
                }

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

    private void editBookingCallForDesk(EditBookingDetails editDeskBookingDetails,EditText edComments) {
        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
        jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
        if (!edComments.getText().toString().trim().equalsIgnoreCase("") || !edComments.getText().toString().trim().isEmpty())
            jsonChangesObject.addProperty("comments",edComments.getText().toString());
        jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
        if (changedDeskId>0){
            if (changedTeamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                jsonChangesObject.addProperty("requestedTeamDeskId",changedDeskId);
                jsonChangesObject.addProperty("requestedTeamId",changedTeamId);
            } else {
                jsonChangesObject.addProperty("teamDeskId",changedDeskId);
            }
        } else {
            String nullda=null;
            jsonChangesObject.addProperty("requestedTeamDeskId",nullda);
            jsonChangesObject.addProperty("requestedTeamId",nullda);
            jsonChangesObject.addProperty("teamDeskId",nullda);
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
        if (!editDeskBookingDetails.getEditStartTTime().equalsIgnoreCase(startTime.getText().toString()))
        {
            jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
        }

        if (!editDeskBookingDetails.getEditEndTime().equalsIgnoreCase(endTime.getText().toString())
        ){
            jsonChangesObject.addProperty("to","2000-01-01T"+endTime.getText().toString()+":00.000Z");
        }

        jsonInnerObject.add("changes",jsonChangesObject);
        jsonChangesetArray.add(jsonInnerObject);

        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        //System.out.println("json un" + jsonOuterObject.toString());
        if (jsonChangesObject.size() > 0){
            editBookingCall(jsonOuterObject,0,1,"edit");
        }
        selectedDeskId=0;
        roomBottomSheet.dismiss();

    }
    private void newBookingCallForDesk(EditBookingDetails editDeskBookingDetails,EditText edComments) {
        Log.d(TAG,"new booking enter");

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
        jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
        if (!edComments.getText().toString().trim().equalsIgnoreCase("") || !edComments.getText().toString().trim().isEmpty())
            jsonChangesObject.addProperty("comments",edComments.getText().toString());
        jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());

        jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
        jsonChangesObject.addProperty("to", "2000-01-01T"+endTime.getText().toString()+":00.000Z");
        if (changedDeskId>0){
            if (changedTeamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                jsonChangesObject.addProperty("requestedTeamDeskId",changedDeskId);
                jsonChangesObject.addProperty("requestedTeamId",changedTeamId);
                jsonChangesObject.addProperty("usageTypeId", "7");
            } else {
                jsonChangesObject.addProperty("teamDeskId",changedDeskId);
                jsonChangesObject.addProperty("usageTypeId", "2");
            }
        } else {
            if(editDeskBookingDetails.getDesktId()>0) {
                jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
                jsonChangesObject.addProperty("usageTypeId", "2");
            }
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
        if (!editDeskBookingDetails.getEditStartTTime().equalsIgnoreCase(startTime.getText().toString()))
        {
            jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
        }

        if (!editDeskBookingDetails.getEditEndTime().equalsIgnoreCase(endTime.getText().toString())
        ){
            jsonChangesObject.addProperty("to","2000-01-01T"+endTime.getText().toString()+":00.000Z");
        }

        jsonInnerObject.add("changes",jsonChangesObject);
        jsonChangesetArray.add(jsonInnerObject);

        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        //System.out.println("json un" + jsonOuterObject.toString());
        if (jsonChangesObject.size() > 0){
            editBookingCall(jsonOuterObject,0,1,"new");
        }
        selectedDeskId=0;
        roomBottomSheet.dismiss();

    }
    private void requestBookingCallForDesk(EditBookingDetails editDeskBookingDetails,EditText edComments) {
        Log.d(TAG,"request booking enter");
        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
        jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
        if (!edComments.getText().toString().trim().equalsIgnoreCase("") || !edComments.getText().toString().trim().isEmpty())
            jsonChangesObject.addProperty("comments",edComments.getText().toString());

        jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
        jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
        jsonChangesObject.addProperty("to", "2000-01-01T"+endTime.getText().toString()+":00.000Z");
        if (changedDeskId>0){
            if (changedTeamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                jsonChangesObject.addProperty("requestedTeamDeskId",changedDeskId);
                jsonChangesObject.addProperty("requestedTeamId",changedTeamId);
                jsonChangesObject.addProperty("usageTypeId", "7");
            } else {
                jsonChangesObject.addProperty("teamDeskId",changedDeskId);
                jsonChangesObject.addProperty("usageTypeId", "2");
            }
        } else {
            if(editDeskBookingDetails.getDesktId()>0) {
                jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
                jsonChangesObject.addProperty("requestedTeamId",selectedTeamId);
                jsonChangesObject.addProperty("usageTypeId", "7");
            }
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
        if (!editDeskBookingDetails.getEditStartTTime().equalsIgnoreCase(startTime.getText().toString()))
        {
            jsonChangesObject.addProperty("from", "2000-01-01T"+startTime.getText().toString()+":00.000Z");
        }

        if (!editDeskBookingDetails.getEditEndTime().equalsIgnoreCase(endTime.getText().toString())
        ){
            jsonChangesObject.addProperty("to","2000-01-01T"+endTime.getText().toString()+":00.000Z");
        }

        jsonInnerObject.add("changes",jsonChangesObject);
        jsonChangesetArray.add(jsonInnerObject);

        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        //System.out.println("json un" + jsonOuterObject.toString());
        if (jsonChangesObject.size() > 0){
            editBookingCall(jsonOuterObject,0,1,"request");
        }
        selectedDeskId=0;
        roomBottomSheet.dismiss();

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
        tv_forever.setText(Utils.getDateFormatToSetInRepeat(date)+" (end of Week)");


        ////System.out.println("LocateDateHere "+binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+" "+ binding.locateEndTime.getText().toString());
        //2022-09-14 15:46 23:59

        //Get Date Difference Between current date and weekend date
        String selectedDate=binding.locateCalendearView.getText().toString();
        enableCurrentWeek = Utils.getDifferenceBetweenTwoDates(selectedDate);

        ////System.out.println("enableCurrentWeek "+enableCurrentWeek);

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

    private void openIntervalsDialog(String type) {

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

    private void callDeskListBottomSheetDialog(int id,EditBookingDetails editBookingDetails,String newEditStatus) {
        for (int i=0; i<activeTeamsList.size(); i++) {
            if (selectedTeamId==activeTeamsList.get(i).getId()) {
                selectedTeamName = activeTeamsList.get(i).getName();
                selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
            }
        }

        deskListBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        deskListBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk_new,
//                new RelativeLayout(getContext()))));

        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_edit_select_desk_new, null);
        deskListBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        TextView bsRepeatBack, selectDesk, sheetDate, sheetTime;
        bsGeneralSearch = deskListBottomSheet.findViewById(R.id.bsGeneralSearch);
        rvDeskRecycler= deskListBottomSheet.findViewById(R.id.desk_list_select_recycler);
        selectDesk= deskListBottomSheet.findViewById(R.id.sheet_name);
        sheetDate= deskListBottomSheet.findViewById(R.id.sheet_date);
        sheetTime= deskListBottomSheet.findViewById(R.id.sheet_time);
        tvTeamName= deskListBottomSheet.findViewById(R.id.tv_team_name);
        bsRepeatBack=deskListBottomSheet.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
        sheetDate.setText(Utils.calendarDay10thMonthformat(Utils.convertStringToDateFormet(calSelectedDate)));
        if(editBookingDetails.getDisplayTime()!=null)
            sheetTime.setText(""+editBookingDetails.getDisplayTime());

        if (selectedicon == 3){
            selectDesk.setText("Select Parking");
            parkingSpotListRecyclerAdapter =new ParkingSpotListRecyclerAdapter(getContext(),
                    this,getActivity(),parkingSpotModelList,getContext(),
                    deskListBottomSheet, deskRoomName.getText().toString());
            rvDeskRecycler.setAdapter(parkingSpotListRecyclerAdapter);
        }else if (selectedicon==2){
            selectDesk.setText("Select a room");
            roomListRecyclerAdapter =new RoomListRecyclerAdapter(getContext(),
                    this,getActivity(),userAllowedMeetingResponseListUpdated,getContext(),
                    deskListBottomSheet,allMeetingRoomList,Utils.checkStringParms(deskRoomName.getText().toString()));
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }else {
            selectDesk.setText("Book a workspace");
            tvTeamName.setText(selectedTeamName);
            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editBookingDetails.getUsageTypeId()==2){
                    tvTeamName.setTextColor(context.getResources().getColor(R.color.figmaGrey));
                    tvTeamNameDisabled=true;
                } else if(editBookingDetails.getUsageTypeId() == 7){
                    tvTeamName.setTextColor(context.getResources().getColor(R.color.figmaBlueText));
                    tvTeamNameDisabled=false;
                }
            }else {
                tvTeamName.setTextColor(context.getResources().getColor(R.color.figmaBlueText));
                tvTeamNameDisabled=false;
            }
            /*
            if (editBookingDetails!=null && editBookingDetails.getRequestedTeamId()>0)
                tvTeamName.setTextColor(context.getResources().getColor(R.color.figmaGrey));

            deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,
                    getActivity(),bookingForEditResponseDesk,getContext(),deskListBottomSheet);
            */
            /*if(newEditStatus.equalsIgnoreCase("edit")){
                newDeskListForEditRecyclerAdapter = new NewDeskListForEditRecyclerAdapter(getContext(),this,
                        getActivity(),bookingDeskList,this,deskListBottomSheet,
                        id,editBookingDetails,newEditStatus);
                rvDeskRecycler.setAdapter(newDeskListForEditRecyclerAdapter);
            } else {

            }
            */
            try {
                newdeskListRecyclerAdapter = new NewDeskListRecyclerAdapter(getContext(),this,
                        getActivity(),bookingDeskList,this,deskListBottomSheet,
                        id,editBookingDetails,newEditStatus,Utils.checkStringParms(deskRoomName.getText().toString()));
                rvDeskRecycler.setAdapter(newdeskListRecyclerAdapter);
            } catch (Exception e){

            }


        }

        tvTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvTeamNameDisabled){
                    if (newEditStatus.equalsIgnoreCase("edit")){
                        callActiveTeamsBottomSheet(id,editBookingDetails,newEditStatus);
                    }else {
//                        if(newEditStatus.equalsIgnoreCase("new")
//                                || newEditStatus.equalsIgnoreCase("request"))
                        callActiveTeamsBottomSheet(id,editBookingDetails,newEditStatus);
                    }
                }
            }
        });
        bsGeneralSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newdeskListRecyclerAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void callActiveTeamsBottomSheet(int id,EditBookingDetails editBookingDetails,String newEditStatus) {
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

        activeTeamsAdapter =new ActiveTeamsAdapter(getContext(), this,
                getActivity(),activeTeamsList,this,
                activeTeamsBottomSheet,id,editBookingDetails,newEditStatus);
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

        bottomSheetDialog  = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
//                new RelativeLayout(getContext()))));

        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_edit_select_desk, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        TextView bsRepeatBack, selectDesk,sheetDate,sheetTime,tvFilter;
        LinearLayout linera,filter_layout;
        linera= bottomSheetDialog.findViewById(R.id.linera);
        filter_layout= bottomSheetDialog.findViewById(R.id.filter_layout);
        rvDeskRecycler= bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        ImageView ivLocation = bottomSheetDialog.findViewById(R.id.location_icon_location);
        tvFilter = bottomSheetDialog.findViewById(R.id.filter);
        tvCapacityFilter = bottomSheetDialog.findViewById(R.id.capa_tv);
        selectDesk = bottomSheetDialog.findViewById(R.id.select_desk);
        sheetDate = bottomSheetDialog.findViewById(R.id.sheet_date);
        sheetTime = bottomSheetDialog.findViewById(R.id.sheet_time);
        bsGeneralSearch = bottomSheetDialog.findViewById(R.id.bsGeneralSearch);
        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
        sheetDate.setText(Utils.calendarDay10thMonthformat(Utils.convertStringToDateFormet(calSelectedDate)));
        sheetTime.setText(""+startTime.getText()+" to "+endTime.getText());

        if (selectedicon == 3) {
            selectDesk.setText("Book parking");
            filter_layout.setVisibility(View.GONE);
            linera.setVisibility(View.VISIBLE);
            parkingSpotListRecyclerAdapter =new ParkingSpotListRecyclerAdapter(getContext(),this,getActivity(),parkingSpotModelList,getContext(),bottomSheetDialog,
                    Utils.checkStringParms(deskRoomName.getText().toString()));
            rvDeskRecycler.setAdapter(parkingSpotListRecyclerAdapter);
        }else if (selectedicon==2) {
            selectDesk.setText("Book a room");
            tvCapacityFilter.setVisibility(View.VISIBLE);
            linera.setVisibility(View.VISIBLE);

            tvFilter.setVisibility(View.GONE);
            roomListRecyclerAdapter =new RoomListRecyclerAdapter(getContext(),this,getActivity(),userAllowedMeetingResponseListUpdated,
                    getContext(),bottomSheetDialog,allMeetingRoomList,Utils.checkStringParms(deskRoomName.getText().toString()));
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }else {
            selectDesk.setText("Book a workspace");
            deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,
                    getActivity(),bookingForEditResponseDesk,getContext(),bottomSheetDialog);
            rvDeskRecycler.setAdapter(deskListRecyclerAdapter);

        }

        bsGeneralSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (selectedicon == 3){
                    parkingSpotListRecyclerAdapter.getFilter().filter(s.toString());
                }else if (selectedicon==2){
                    roomListRecyclerAdapter.getFilter().filter(s.toString());
                }else {
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvCapacityFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCapacityBottomDialog(capacitySelectedItem);
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

    private void callCapacityBottomDialog(String selectedItem) {

        capacityDialog  = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        capacityDialog.setContentView((getLayoutInflater().inflate(R.layout.bottom_sheet_capacity,
                new RelativeLayout(getContext()))));

        TextView title = capacityDialog.findViewById(R.id.title);
        RecyclerView capacityRecycler = capacityDialog.findViewById(R.id.capacityRecycler);

        ArrayList<String> capacityList = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            capacityList.add(i+"");
        }

        CapacityAdapter adapter = new CapacityAdapter(BookFragment.this, capacityList, selectedItem);
        capacityRecycler.setAdapter(adapter);

        capacityDialog.show();
    }

    public void dismissCapacityDialog(String selectedItem){
        capacityDialog.dismiss();
        tvCapacityFilter.setText("Capacity " + selectedItem.replace(" +","+"));
        capacitySelectedItem = selectedItem.replace(" +","");

        if(userAllowedMeetingResponseListUpdated != null && userAllowedMeetingResponseListUpdated.size()>0) {
            List<UserAllowedMeetingResponse> tempList = new ArrayList<>();

            for (UserAllowedMeetingResponse res : userAllowedMeetingResponseListUpdated) {
                int i = Integer.parseInt(selectedItem.replace(" +", ""));
                if (i <= res.getNoOfPeople()) {
                    tempList.add(res);
                }
            }

            roomListRecyclerAdapter = new RoomListRecyclerAdapter(getContext(), this, getActivity(),
                    tempList,
                    getContext(), bottomSheetDialog, allMeetingRoomList,Utils.checkStringParms(deskRoomName.getText().toString()));
            rvDeskRecycler.setAdapter(roomListRecyclerAdapter);
        }
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
                    try {
                        if (response.code()==200 && response.body().getResultCode()!=null){
//                        Utils.showCustomAlertDialog(getActivity(),"Update Success");
//                        Toast.makeText(getActivity(), "Success Bala", Toast.LENGTH_SHORT).show();
                            if (response.body().getResultCode().equalsIgnoreCase("ok")){
                                if (newEditDelete.equalsIgnoreCase("new")
                                        || newEditDelete.equalsIgnoreCase("new_deep_link"))
                                    openCheckoutDialog("Booking Created",dskRoomStatus);
                                else if (newEditDelete.equalsIgnoreCase("edit"))
                                    openCheckoutDialog("Booking Updated",dskRoomStatus);
                                else if (newEditDelete.equalsIgnoreCase("request"))
                                    openCheckoutDialog("Booking Created",dskRoomStatus);
                                else
                                    openCheckoutDeleteDialog("Booking Deleted",dskRoomStatus);

                                switch (dskRoomStatus){
                                    case 1:
                                        tabToggleViewClicked(1);
                                        break;
                                    case 2:
                                        tabToggleViewClicked(2);
                                        break;
                                    case 3:
                                        tabToggleViewClicked(3);
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
                                }else if(response.body().getResultCode().toString().equals("DESK_UNAVAILABLE")){
                                    resultString = "Desk is Unavailable";
                                }else {
                                    resultString = response.body().getResultCode().toString();
                                }
                                Utils.showCustomAlertDialog(getActivity(), resultString);
                            }
                        }else if (response.code() == 500){
                            Utils.showCustomAlertDialog(getActivity(),""+response.message());
                        }else if (response.code() == 401){
                            Utils.showCustomTokenExpiredDialog(getActivity(),"401 Error Response");
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                        }
                        else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception){
                        Log.e(TAG,exception.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    Toast.makeText(getActivity(), "fail Bala"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    //System.out.println("resps"+t.getMessage());
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
        if (roomBottomSheet!=null)
            roomBottomSheet.dismiss();
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
        // ivChecout.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_red));
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
    public void onEditClick(BookingForEditResponse.Bookings bookings, String code,
                            List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {
        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(bookings.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(bookings.getMyto()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(bookings.getDate()));
        editDeskBookingDetails.setCalId(bookings.getId());
        editDeskBookingDetails.setDeskCode(bookings.getDeskCode());
        editDeskBookingDetails.setDescription(Utils.checkStringParms(bookings.getDescription()));

        if (bookings.getDeskLocation()!=null && bookings.getDeskLocation().getBuildingName()!=null)
            editDeskBookingDetails.setLocationAddress(Utils.checkStringParms(bookings.getDeskLocation().getBuildingName())
                +", " + Utils.checkStringParms(bookings.getDeskLocation().getfLoorName()));
        else if (bookings.getRequestedDeskLocation()!=null)
            editDeskBookingDetails.setLocationAddress(bookings.getRequestedDeskLocation().getBuildingName()
                    +", " + bookings.getRequestedDeskLocation().getfLoorName());
        editDeskBookingDetails.setComments(bookings.getComments());

        if (bookings.getDeskLocation()!=null && bookings.getDeskLocation().getBuildingName()!=null)
            editDeskBookingDetails.setLocationAddress(bookings.getDeskLocation().getfLoorName()+", "+bookings.getDeskLocation().getBuildingName());
        else if(bookings.getRequestedDeskLocation()!=null)
            editDeskBookingDetails.setLocationAddress(bookings.getRequestedDeskLocation().getfLoorName()+", "+bookings.getRequestedDeskLocation().getBuildingName());

        if (bookings.getStatus().getTimeStatus().equalsIgnoreCase("ongoing"))
            editDeskBookingDetails.setDeskStatus(2);
        else if (bookings.getStatus().getTimeStatus().equalsIgnoreCase("past"))
            editDeskBookingDetails.setDeskStatus(1);
        else
            editDeskBookingDetails.setDeskStatus(0);
        if (bookings.getStatus()!=null)
            editDeskBookingDetails.setDeskBookingType(bookings.getStatus().getBookingType());
        else
            editDeskBookingDetails.setDeskBookingType("");

        defaultEditDeskId=bookings.getTeamDeskId();
        editDeskBookingDetails.setDesktId(bookings.getTeamDeskId());
        editDeskBookingDetails.setTimeStatus(bookings.getStatus().getTimeStatus());

        if (bookings.getRequestedTeamDeskId()!=null)
            editDeskBookingDetails.setRequestedTeamDeskId(bookings.getRequestedTeamDeskId());
        else
            editDeskBookingDetails.setRequestedTeamDeskId(0);
        if (bookings.getRequestedTeamId()!=null &&bookings.getRequestedTeamId()>0){
            selectedTeamId=bookings.getRequestedTeamId();
            editDeskBookingDetails.setRequestedTeamId(bookings.getRequestedTeamId());
        } else {
            selectedTeamId=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID);
            editDeskBookingDetails.setRequestedTeamId(0);
        }
        if (bookings.getUsageTypeId()>0)
            editDeskBookingDetails.setUsageTypeId(bookings.getUsageTypeId());
        switch (bookings.getUsageTypeId()){
            case 9:
                editDeskBookingDetails.setDeskCode("Working from home");
                break;
            case 1:
                editDeskBookingDetails.setDeskCode("Working in alternative office");
                break;
            case 5:
                editDeskBookingDetails.setDeskCode("Not assigned to team");
                break;
            case 8:
                editDeskBookingDetails.setDeskCode("Training");
                break;
            case 6:
                editDeskBookingDetails.setDeskCode("Out of office");
                break;
            case 18:
                editDeskBookingDetails.setDeskCode("Sick Leave");
                break;
            default:
                break;

        }


//        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");
        changedDeskId=0;
        changedTeamId=0;

        EditDeskController editDeskController = new EditDeskController(activityContext, context,
                editDeskBookingDetails,AppConstants.BOOKFRAGMENTINSTANCESTRING);
        if(bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();

        /*if (editDeskBookingDetails.getUsageTypeId() == 7){
            getRequestDeskDeskList(editDeskBookingDetails,"edit");
        } else{
            editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");
        }
*/
    }

    private void getRequestDeskDeskList(EditBookingDetails editDeskBookingDetails, String edit) {
        if (Utils.isNetworkAvailable(getContext())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.
                    getTeamDeskAvailability(
                            editDeskBookingDetails.getRequestedTeamId(),
                            Utils.getISO8601format(editDeskBookingDetails.getDate()),
                            Utils.getISO8601format(editDeskBookingDetails.getDate()));

            call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
                @Override
                public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                    List<BookingForEditResponse.TeamDeskAvailabilities> deskList = new ArrayList<>();
                    deskList=response.body();
                    if (deskList!=null && deskList.size()>0){
                        loop:
                        for (int i=0; i<deskList.size(); i++){
                            if (editDeskBookingDetails.getRequestedTeamDeskId()==deskList.get(i).getTeamDeskId()){
                                selectedTeamId = deskList.get(i).getTeamId();
                                editDeskBookingDetails.setDeskCode(deskList.get(i).getDeskCode());
                                editDeskBookingDetails.setTimeZone(deskList.get(i).getTimeZones().get(0).getTimeZoneId());
                                editDeskBookingDetails.setDesktId(deskList.get(i).getTeamDeskId());
                                editDeskBookingDetails.setDeskTeamId(deskList.get(i).getTeamId());
                                break loop;
                            }
                        }
                    }

//                    editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");
                    editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");
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

        //System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,1,"delete");

    }

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
        selectedDeskId = deskId;
    }

    @Override
    public void onCarEditClicks(CarParkListToEditResponse carParkBooking) {
        if(bookEditBottomSheet!=null) {
            bookEditBottomSheet.dismiss();
        }

        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
        editDeskBookingDetails.setCalId(carParkBooking.getId());
        editDeskBookingDetails.setParkingSlotId(carParkBooking.getParkingSlotId());
        editDeskBookingDetails.setEditStartTTime(Utils.splitTime(carParkBooking.getFrom()));
        editDeskBookingDetails.setEditEndTime(Utils.splitTime(carParkBooking.getMyto()));
        editDeskBookingDetails.setDate(Utils.convertStringToDateFormet(carParkBooking.getDate()));
        editDeskBookingDetails.setVehicleRegNumber(carParkBooking.getVehicleRegNumber());
        editDeskBookingDetails.setParkingSlotCode(carParkBooking.getParkingSlotName());


        EditCarParkController editCarParkController = new EditCarParkController(activityContext, context,
                editDeskBookingDetails,AppConstants.BOOKFRAGMENTINSTANCESTRING,calSelectedDate);
//        getParkingSpotList(""+SessionHandler.getInstance().getInt(getActivity(),AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editDeskBookingDetails,"edit");


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

        //System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,3,"delete");


    }
    public void deleteCar(int id){

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("parkingSlotId",id);

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();
//        list1.add(editDeskBookingDetails.getCalId());
        jsonDeletedIdsArray.add(id);
        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        //System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,3,"delete");


    }
    @Override
    public void onSelectParking(int deskId, String deskName,String location,String description,String available) {
        try {
            deskRoomName.setText(""+deskName);
            selectedDeskId= deskId;
            if (available.equalsIgnoreCase("Available")){
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
            } else {
                deskStatusText.setText("Available For Request");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
            }

            locationAddress.setText(location);
            tv_description.setText(description);

        } catch (Exception e){

        }

    }


    //Room Booking Methods
    private void callMeetingRoomBookingBottomSheet(EditBookingDetails editDeskBookingDetails, TextView startTime, TextView endTime,
                                                   int meetingRoomId, String meetingRoomName, boolean isRequest,int id,String newEditStatus) {

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
                    //System.out.println("MeetingAmenities " + userAllowedMeetingResponseList.get(i).getAmenities().get(j).getId());

                    for (int k = 0; k < amenitiesListToShowInMeetingRoomList.size(); k++) {

                        if (userAllowedMeetingResponseList.get(i).getAmenities().get(j).getId() == amenitiesListToShowInMeetingRoomList.get(k).getId()) {
                            amenitiesList.add(amenitiesListToShowInMeetingRoomList.get(k).getName());
                            //System.out.println("TotalAmenitiesForThisRoom " + amenitiesListToShowInMeetingRoomList.get(k).getName());

                        }

                    }
                }


            }

        }

        TextView startRoomTime, endTRoomime, editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription,
                roomTitle,sheetDate,sheetTime,capacityNo,locationAddress;
        EditText etParticipants, externalAttendees, etSubject, etComments;
        ChipGroup chipGroup, externalAttendeesChipGroup;

        RecyclerView rvParticipant;
        LinearLayoutManager linearLayoutManager;
        RelativeLayout startTimeLayout, endTimeLayout;


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
//        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_participant_booking,
//                new RelativeLayout(getContext()))));
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_room_participant_booking, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        etParticipants = bottomSheetDialog.findViewById(R.id.etParticipants);
        etSubject = bottomSheetDialog.findViewById(R.id.etSubject);

        capacityNo = bottomSheetDialog.findViewById(R.id.tv_capacity_no);
        etComments = bottomSheetDialog.findViewById(R.id.etComments);
        editRoomBookingContinue = bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack = bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        roomTitle = bottomSheetDialog.findViewById(R.id.roomTitle);
        sheetDate = bottomSheetDialog.findViewById(R.id.sheet_date);
        locationAddress = bottomSheetDialog.findViewById(R.id.locationAddress);
        sheetTime = bottomSheetDialog.findViewById(R.id.sheet_time);
        rvParticipant = bottomSheetDialog.findViewById(R.id.rvParticipant);
        participantChipGroup = bottomSheetDialog.findViewById(R.id.participantChipGroup);

        chipGroup = bottomSheetDialog.findViewById(R.id.room_top_chip_group);

        externalAttendees = bottomSheetDialog.findViewById(R.id.externalAttendees);
        externalAttendeesChipGroup = bottomSheetDialog.findViewById(R.id.externalAttendeesChipGroup);

        //set spannable text
        String s= "Internal participants optional";
        int start=21;
        int end=29;
        Utils.setSPannableStringForParticipants(etParticipants,s,start,end);

        String ex= "External participants optional";
        Utils.setSPannableStringForParticipants(externalAttendees,ex,start,end);

        String com="Comments optional";
        int start1=9;
        int end1=12;
        Utils.setSPannableStringForParticipants(etComments,com,start1,end1);

        if (editDeskBookingDetails.getAmenities()!=null){
            for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                ShapeAppearanceModel  shapeAppearanceModel = new ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED,15)
                        .build();
                Chip chip = new Chip(getContext());
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setShapeAppearanceModel(shapeAppearanceModel);
                chip.setId(i);
                chip.setTextAppearance(R.style.chipText);
                chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                chipGroup.addView(chip);
            }
        }

        //Set All Amenities Here
/*
        for (int i = 0; i < amenitiesList.size(); i++) {

            //System.out.println("RoomAmenitiesList " + amenitiesList.get(i));
            Chip chip = new Chip(getContext());
            chip.setText(amenitiesList.get(i));
            chip.setCheckable(false);
            chip.setTextAppearance(R.style.chipText);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }
*/

        //Language
//        editRoomBookingContinue.setText(appKeysPage.getContinue());
        editRoomBookingContinue.setText("Book");
//        editRoomBookingBack.setText(appKeysPage.getBack());
        editRoomBookingBack.setText("Back");
//        etComments.setHint(appKeysPage.getComments());
        etComments.setHint("Comments optional");
        etSubject.setHint(meetingRoomsLanguage.getSubject());

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);


        roomTitle.setText(meetingRoomName);
        capacityNo.setText(editDeskBookingDetails.getCapacity());
        sheetTime.setText(editDeskBookingDetails.getDisplayTime());
        locationAddress.setText(editDeskBookingDetails.getLocationAddress());
        sheetDate.setText(Utils.calendarDay10thMonthformat(Utils.convertStringToDateFormet(calSelectedDate)));

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

                /*if (comment.isEmpty()
                        || comment.equals("")
                        || comment == null) {
                    Toast.makeText(getContext(), "Please Enter Comment", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }*/

                if (status) {
                    bottomSheetDialog.dismiss();
                    bottomSheetDialog.dismiss();
                    if (!repeatActvieStatus)
                        doMeetingRoomBooking(meetingRoomId,
                                startTime.getText().toString(),
                                endTime.getText().toString(), subject, comment,
                                isMeetingRequest, editDeskBookingDetails.isTeamsChecked(),
                                externalAttendeesEmail, id, newEditStatus);
                    else
                        doRepeatMeetingRoomBookingForWeek(editDeskBookingDetails.isTeamsChecked(), isMeetingRequest,
                                externalAttendeesEmail);

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
                //System.out.println("ExternalAttendeesListInLoop " + editDeskBookingDetails.getExternalAttendeesList().get(i));

                Chip chip = new Chip(getContext());
                chip.setText(editDeskBookingDetails.getExternalAttendeesList().get(i));
                chip.setCheckable(false);
                chip.setTextAppearance(R.style.chipText);
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
                ////System.out.println("ExternalAttendeesListInLoop " + editDeskBookingDetails.getExternalAttendeesList().get(i));

                ParticipantDetsilResponse participantDetsilResponse = new ParticipantDetsilResponse(editDeskBookingDetails.getAttendeesList().get(i).getId(), editDeskBookingDetails.getAttendeesList().get(i).getFirstName(), editDeskBookingDetails.getAttendeesList().get(i).getLastName(), editDeskBookingDetails.getAttendeesList().get(i).getFullName(), editDeskBookingDetails.getAttendeesList().get(i).getEmail(), editDeskBookingDetails.getAttendeesList().get(i).isActive());
                chipList.add(participantDetsilResponse);

                Chip chip = new Chip(getContext());
                chip.setText(attendeesListForEdit.get(i).getEmail());
                chip.setCheckable(false);
                chip.setTextAppearance(R.style.chipText);
                chip.setClickable(false);
                chip.setTextAppearance(R.style.chipText);
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

                        ////System.out.println("RemoveChipGroupName"+chip.getText().toString());



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
                    if( Utils.isValidEmail(s.toString().trim())){

                        chip.setText(s.toString());
                        chip.setCheckable(false);
                        chip.setClickable(false);
                        chip.setTextAppearance(R.style.chipText);
                        chip.setCloseIconVisible(true);
                        externalAttendeesChipGroup.setVisibility(View.VISIBLE);
                        externalAttendeesChipGroup.addView(chip);

                        //Add In List
                        externalAttendeesEmail.add(s.toString());

                        externalAttendees.clearFocus();
                        externalAttendees.setText("");
                    }else {
                        Utils.toastMessage(getContext(), "Please enter a valid email address.");
                    }

/*

                    chip.setText(s.toString());
                    chip.setCheckable(false);
                    chip.setTextAppearance(R.style.chipText);
                    chip.setClickable(false);
                    chip.setCloseIconVisible(true);
                    externalAttendeesChipGroup.setVisibility(View.VISIBLE);
                    externalAttendeesChipGroup.addView(chip);

                    //Add In List
                    externalAttendeesEmail.add(s.toString());

                    externalAttendees.clearFocus();
                    externalAttendees.setText("");

*/

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

                    ////System.out.println("ParticipantNameList" + participantDetsilResponseList.get(0).getFirstName());

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

        //Get Saved UserId
        int userId=SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID);

        List<ParticipantDetsilResponse> result = new ArrayList<>();
        result = (List<ParticipantDetsilResponse>) participantDetsilResponseList.stream().filter(val -> val.getId() != userId).collect(Collectors.toList());

        rvParticipant.setVisibility(View.VISIBLE);
        ParticipantNameShowAdapter participantNameShowAdapter = new ParticipantNameShowAdapter(getContext(), result, this,rvParticipant);
        rvParticipant.setAdapter(participantNameShowAdapter);
    }

    private void doMeetingRoomBooking(int meetingRoomId,
                                      String startRoomTime,
                                      String endRoomTime,
                                      String subject,
                                      String comment,
                                      boolean isRequest,
                                      boolean isTeamsChecked,List<String> externalAttendeesEmail,int id,String newEditStatus) {

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
        changes.setFrom(getCurrentDate() + "" + "T" + startRoomTime + ":" + "00" + "." + "000" + "Z");
        changes.setMyto(getCurrentDate() + "" + "T" + endRoomTime + ":" + "00" + "." + "000" + "Z");
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
                    //System.out.println("EditedAndAddedParticipants "+chipList.get(i).getId());
                    attendeesList.add(chipList.get(i).getId());
                }
            }*/

        if (attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            //Edit flow....
            List<Integer> addedList= LogicHandler.getNewlyAdded(attendeesListForEdit,chipList);
            //System.out.println("NewellyAddedParticipant "+addedList);

            if(addedList!=null && addedList.size()>0){

                for (int i = 0; i <addedList.size() ; i++) {
                    attendeesList.add(addedList.get(i));
                }

            }

            List<Integer> removedList=LogicHandler.getRemoved(attendeesListForEdit,chipList);
            //System.out.println("RemovedParticipant "+removedList);
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

        //System.out.println("BookingMeetingRoom" + meetingRoomRequest);

        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doMeetingRoomBook(meetingRoomRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                chipList.clear();
                String resultString = "";
                try {
                    if (response.code()==200 &&response.body().getResultCode()!=null && response.body().getResultCode().equalsIgnoreCase("ok")){
                        if(newEditStatus.equalsIgnoreCase("new") ||
                                newEditStatus.equalsIgnoreCase("request"))
                            openCheckoutDialog("Booking Created",2);
                        else
                            openCheckoutDialog("Booking Updated",2);
                    }else if(response.code()==500){
                        resultString = response.body().toString();
                        Utils.showCustomAlertDialog(getActivity(), resultString);
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
                        }else if(response.body().getResultCode().toString().equals("DESK_UNAVAILABLE")){
                            resultString = "Desk is Unavailable";
                        } else {
                            resultString = response.body().getResultCode().toString();
                        }
                        roomBottomSheet.dismiss();
                        Utils.showCustomAlertDialog(getActivity(), resultString);
                    }

                } catch (Exception exception){
                    Log.e(TAG,exception.getMessage());
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


        Chip chip = new Chip(getContext());

        //Should not add already added user
        if(chipList!=null && chipList.size()>0) {

            boolean alreadyHasId = chipList.stream().anyMatch(m -> m.getId() == participantDetsilResponse.getId());

            if (alreadyHasId) {
                recyclerView.setVisibility(View.GONE);
            }else {
                chipList.add(participantDetsilResponse);

                chip.setText(participantDetsilResponse.getFullName());
                chip.setCloseIconVisible(true);
                chip.setCheckable(false);
                chip.setTextAppearance(R.style.chipText);
                chip.setClickable(false);

                participantChipGroup.addView(chip);
                participantChipGroup.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }


        }else {
            chipList.add(participantDetsilResponse);

            chip.setText(participantDetsilResponse.getFullName());
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);

            participantChipGroup.addView(chip);
            participantChipGroup.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }



       /* Chip chip=new Chip(getContext());
        chip.setText(participantDetsilResponse.getFullName());
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);

        chipList.add(participantDetsilResponse);

        participantChipGroup.addView(chip);
        participantChipGroup.setVisibility(View.VISIBLE);
*/

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
//                //System.out.println("RemoveChipGroupName"+chip.getText().toString());
                participantChipGroup.removeView(chip);
            }
        });
    }

    @Override
    public void onSelectRoom(int deskId, String deskName, String location, List<UserAllowedMeetingResponse.Amenity> amenityLIst,int capacityCount,String availability) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
        locationAddress.setText(location);

        if (availability.equalsIgnoreCase("Available For Request")){
            isMeetingRequest = true;
            deskStatusText.setText("Available For Request");
            deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
        } else {
            isMeetingRequest = false;
            deskStatusText.setText("Available");
            deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
        }
        
        if (tvcapacityCount!=null)
            tvcapacityCount.setText(""+capacityCount);

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
        if (amenityStringList!=null && amenityStringList.size()>0){
            chipGroup.removeAllViews();
            for (int i=0; i<amenityStringList.size(); i++){
                ShapeAppearanceModel shapeAppearanceModel= new ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED,15)
                        .build();
                Chip chip = new Chip(getContext());
                chip.setId(i);
                chip.setTextAppearance(R.style.chipText);
                chip.setShapeAppearanceModel(shapeAppearanceModel);
                chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                chip.setCloseIconVisible(false);
                chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));
                chip.setText(""+amenityStringList.get(i));
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

//                    //System.out.println("LocateCountryList" + locateCountryResposes.size());

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

        country.setText("Global Location");
        rvCountry.setVisibility(View.VISIBLE);

        showCountryListInAdapter(locateCountryResposes);
        statBlock.setVisibility(View.GONE);
        rvState.setVisibility(View.GONE);
        streetBlock.setVisibility(View.GONE);
        rvStreet.setVisibility(View.GONE);
        floorBlock.setVisibility(View.GONE);
        rvFloor.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();


            }
        });

        bsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(floorAdapter!=null){
                    if(floorAdapter.getSelectedPositionCheck()>=0){
                        floorSearchStatus=false;
                        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
                        boolean floorSelectedStatus = SessionHandler.getInstance().getBoolean(getContext(), AppConstants.FLOOR_SELECTED_STATUS);
                        canvasss = 1;
                        //removes desk in layout
//                    binding.firstLayout.removeAllViews();

                        defaultLocationcheck = 1;
                        locationId = SessionHandler.getInstance().getInt(context, AppConstants.LOCATION_ID_TEMP);
                        initLoadFloorDetails(canvasss);
                        getAvaliableDeskDetails("5",calSelectedDate);
                        bottomSheetDialog.dismiss();
                    }else {
                        Utils.toastMessage(getContext(),"Please Select Floor");
                    }
                } else {
                    Utils.toastMessage(getContext(), "Please Select Floor");
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
                    //System.out.println("CountrychildName" + locateCountryResposes.get(i).getName());
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

            //System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.size());
            //System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.get(i).getTitle());
            //System.out.println("CorrrdnateSize" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size());

            for (int j = 0; j < supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size(); j++) {

                //System.out.println("DATAATATA" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0) + " " + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));

                Point point = new Point(supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0),
                        supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));
                pointList.add(point);

            }

        }

       /* for (int i = 0; i <pointList.size() ; i++) {
            //System.out.println("PointListDate "+pointList.get(i).getX()+" "+pointList.get(i).getY());
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

                //System.out.println("InsideFloorDetails bala"+locateCountryResposeList.size());

                for (int j = 0; j < locateCountryResposeList.size(); j++) {
                    //System.out.println("InsideFloorName" + locateCountryResposeList.get(j).getName());
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

        //System.out.println("locate data check"+locateCountryResposeList.size());

        floorAdapter = new FloorAdapter(getContext(), locateCountryResposeList, this, "FLOOR_NAME");
        rvFloor.setAdapter(floorAdapter);
    }

    public void initLoadFloorDetails(int canvasDrawStatus) {

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

        if (parentId > 0 ) {
            //Disable touch Screen
            ProgressDialog.touchLock(getContext(),getActivity());
            ProgressDialog.clearTouchLock(getContext(),getActivity());

            //Set Selected Floor In SearchView
            String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
            String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
            String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
            String finalFloor = SessionHandler.getInstance().get(getContext(), AppConstants.FINAL_FLOOR);
            String fullPathLocation = SessionHandler.getInstance().get(getContext(), AppConstants.FULLPATHLOCATION);
            if(defaultLocationcheck>0){
                if (CountryName == null && buildingName == null && floorName == null && fullPathLocation==null) {
                    binding.searchGlobal.setHint("choose location from the list");
                } else {
                    if(fullPathLocation == null) {
                        binding.searchGlobal.setText(floorName + "  " + finalFloor);
                    }else {
                        binding.searchGlobal.setText(floorName + "  " + finalFloor);
                    }
                }
            }

            //ForCoordinate
            int subParentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.SUB_PARENT_ID);
            boolean findCoordinateStatus = true;

            //Used For Desk Avaliability Checking
            if (!calSelectedDate.isEmpty() || !calSelectedDate.equalsIgnoreCase(""))
                getDeskCountLocation(calSelectedDate, ""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),canvasDrawStatus);
            else if (!calSelectedMont.isEmpty() || !calSelectedMont.equalsIgnoreCase(""))
                getDeskCountLocation(calSelectedMont, ""+SessionHandler.getInstance().getInt(getContext(),AppConstants.LOCATION_ID),canvasDrawStatus);
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

        ////System.out.println("DateAndStatTimeAndEndTime"+toDate+" "+fromTime+" "+toTime);

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
                //System.out.println("Failure" + t.getMessage().toString());
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
*/
    private void getDeskList(String code,String date,String newEditStatus) {
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
                    locationDeskList.clear();
                    if(response.body() != null)
                        bookingDeskList = response.body();


                    if(bookingDeskList!=null && bookingDeskList.size() > 0){
                        selectedTeamId = bookingDeskList.get(0).getTeamId();
                        globalSelectedTeamId = bookingDeskList.get(0).getTeamId();
                    }
                    for (int x=0; x<activeTeamsList.size(); x++) {
                        if (selectedTeamId == activeTeamsList.get(x).getId()) {
                            selectedTeamName = activeTeamsList.get(x).getName();
                            selectedTeamAutoApproveStatus = activeTeamsList.get(x).getAutomaticApprovalStatus();
                        }
                    }

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
    private void getRequestDeskDeskList(String code,String date) {
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
//                    //System.out.println("Selecrt id"+selectedTeamId + bookingDeskList.get(0).getDeskCode());
//                    callDeskListBottomSheetDialog(0,null);

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
    private void selectedDeskList(int selectedTeamIdChange,String date, EditBookingDetails editBookingDetails,String newEditStatus) {
        if (Utils.isNetworkAvailable(getContext())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.
                    getTeamDeskAvailability(
                            selectedTeamIdChange,
                            date,
                            date);

            call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
                @Override
                public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                    bookingDeskList.clear();
                    bookingDeskList = response.body();
//                    //System.out.println("Selecrt id"+selectedTeamId + bookingDeskList.get(0).getDeskCode());
                    callDeskListBottomSheetDialog(1,editBookingDetails,newEditStatus);

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
                try{
                    if(response.code()==200){
                        bookingDeskList.clear();
                        locationDeskList.clear();
                        if(response.body().getTeamDeskAvailability() != null)
                            bookingDeskList = response.body().getTeamDeskAvailability();
                        if (response.body().getLocationDesks() !=null)
                            locationDeskList = response.body().getLocationDesks();

                        for (int i=0; i<locationDeskList.size();i++){
                            loop:
                            for (BookingForEditResponse.TeamDeskAvailabilities list: bookingDeskList) {
                                if (locationDeskList.get(i).getId() == list.getDeskId()){
                                    list.setLocationDetails(locationDeskList.get(i).getLocationDetails());
                                    list.setDescription(locationDeskList.get(i).getDescription());
                                    break loop;
                                }
                            }

                        }

                        if(bookingDeskList!=null && bookingDeskList.size() > 0){
                            selectedTeamId = bookingDeskList.get(0).getTeamId();
                            globalSelectedTeamId = bookingDeskList.get(0).getTeamId();
                        }
                        for (int x=0; x<activeTeamsList.size(); x++) {
                            if (selectedTeamId == activeTeamsList.get(x).getId()) {
                                selectedTeamName = activeTeamsList.get(x).getName();
                                selectedTeamAutoApproveStatus = activeTeamsList.get(x).getAutomaticApprovalStatus();
                            }
                        }
                    }

                }catch (Exception exception){
                    Log.e(TAG,exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });


    }
    private void getAvaliableRoomDetails(String code, String date, EditBookingDetails editBookingDetails,String newEditStatus) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        //System.out.println("check cala"+horizontalCalendar.getSelectedDate().getTime());

        String toDate = date;
        String fromTime = "2022-09-23T00:00:00Z";
        String toTime = "2022-09-23T23:59:00Z";

        int parentId = SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);

        Call<List<UserAllowedMeetingResponse>> call = apiService.getAvaliableRoomDetailsForRoomList(parentId,
                toDate,
                fromTime,
                toTime);

        call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
            @Override
            public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                userAllowedMeetingResponseListUpdated.clear();

                //New
                userAllowedMeetingResponseList.clear();
//                List<UserAllowedMeetingResponse> userAllowedMeeting = response.body().getMeetingResponses();
                List<UserAllowedMeetingResponse> userAllowedMeeting = response.body();


                for (int i=0; i < userAllowedMeeting.size(); i++){
                    if (userAllowedMeeting.get(i).isActive()
                            && userAllowedMeeting.get(i).getLocationMeeting().getLocationId()==locationId){

                        if (userAllowedMeeting.get(i).getAutomaticApprovalStatus()==3){
                            boolean teamPresent=false;
                            team:
                            for (int x=0; x<userAllowedMeeting.get(i).getTeams().size(); x++){
                                if (userAllowedMeeting.get(i).getTeams().get(x).getId() == teamId){
                                    teamPresent=true;
                                    break team;
                                }
                            }
                            manager:
                            for (int x=0; x<userAllowedMeeting.get(i).getManagers().size(); x++){
                                if (userAllowedMeeting.get(i).getManagers().get(x).getId()
                                        == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)){
                                    teamPresent=true;
                                    break manager;
                                }
                            }
                            if (teamPresent)
                                userAllowedMeetingResponseList.add(userAllowedMeeting.get(i));
                        } else {
                            userAllowedMeetingResponseList.add(userAllowedMeeting.get(i));
                        }

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

//                            //System.out.println("chek dat no of people"+userAllowedMeetingResponseList.get(i).getNoOfPeople());

                    }
                    loo:
                    for (int i=0; i<userAllowedMeetingResponseList.size();i++){
                        if (participants <= userAllowedMeetingResponseList.get(i).getNoOfPeople()){
                            //System.out.println("chek dat in loop"+userAllowedMeetingResponseList.get(i).getName());

                            userAllowedMeetingResponseListUpdated.add(userAllowedMeetingResponseList.get(i));
                        }

                        if (editBookingDetails.getMeetingRoomtId()==userAllowedMeetingResponseList.get(i).getId()){
                            checkIsRequest=true;
                            break loo;
                        }
                    }
                }
                if (userAllowedMeetingResponseListUpdated!=null
                        && userAllowedMeetingResponseListUpdated.size()>0)
                    editBookingDetails.setCapacity(""+userAllowedMeetingResponseListUpdated.get(0).getNoOfPeople());
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
                    //System.out.println("ame list vala else size"+userAllowedMeetingResponseListUpdated.size());
                    try {
                        Log.d(TAG, "onResponse: "+userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getLocationDetails().getBuildingName());
                        editBookingDetails.setLocationAddress(userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getLocationDetails().getBuildingName()
                                +", "+ userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getLocationDetails().getFloorName());
                    } catch (Exception e){
//                        Toast.makeText(context, "dfsds", Toast.LENGTH_SHORT).show();
                    }
                    if (userAllowedMeetingResponseListUpdated!=null
                            && userAllowedMeetingResponseListUpdated.size()>0 && checkIsRequest)
                        callAmenitiesListForMeetingRoom(editBookingDetails,
                                editBookingDetails.getEditStartTTime(),
                                editBookingDetails.getEditEndTime(),
                                editBookingDetails.getDate(),
                                userAllowedMeetingResponseListUpdated.get(0).getId(),
                                0,"request");
                    else if (userAllowedMeetingResponseListUpdated!=null
                            &&userAllowedMeetingResponseListUpdated.size()>0 && !checkIsRequest)
                        callAmenitiesListForMeetingRoom(editBookingDetails,
                                editBookingDetails.getEditStartTTime(),
                                editBookingDetails.getEditEndTime(),
                                editBookingDetails.getDate(),
                                userAllowedMeetingResponseListUpdated.get(0).getId(),
                                0,"request");
                    else
                        editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
//                        Toast.makeText(getContext(), "Please Clear Fiter", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
//                    Toast.makeText(getContext(), "fa"+bookingDeskList.size(), Toast.LENGTH_SHORT).show();
//                    getAddEditDesk("3",Utils.getISO8601format(date));

                //System.out.println("Failure" + t.getMessage().toString());
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


                //System.out.println("SubParentIdAndItsPosition" + locateCountryRespose.getLocateCountryId() + " ");

                break;

            case "FLOOR":

                floorSearchStatus=true;

                bsApply.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);

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
        simpleTimePicker24Hours.setIs24HourView(true);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);

        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(calSelectedDate);
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

                ////System.out.println("GETDATATATATA" + hour + " " + minute);

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
                tv_until.setText(Utils.getDateFormatToSetInRepeat(date)+" (end of Week)");

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


        //System.out.println("RepeatMeetingRoom "+meetingRoomRequest);

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
                }else if(response.body().getResultCode().toString().equals("DESK_UNAVAILABLE")){
                    resultString = "Desk is Unavailable";
                } else {
                    resultString = response.body().getResultCode().toString();
                }
                //Utils.showCustomAlertDialog(getActivity(), "Booking Not Updated " + resultString);
                Utils.showCustomAlertDialog(getActivity(), resultString);
            }
        } else if (response.code() == 500) {
            Utils.showCustomAlertDialog(getActivity(), ""+response.message());
        } else if (response.code() == 401) {
            Utils.showCustomTokenExpiredDialog(getActivity(), "401 Error Response");
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }


    }


    private void doRepeatDeskBookingForAWeek(EditBookingDetails editBookingDetails, String newEditStatus) {

        String selectedDate = calSelectedDate.toString();
        List<String> dateList=Utils.getCurrentWeekDateList(calSelectedDate,enableCurrentWeek);

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        List<LocateBookingRequest.ChangeSets> changeSetsList= new ArrayList<>();

//        Toast.makeText(context, "adadas"+editBookingDetails.getRequestedTeamId(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i <dateList.size() ; i++) {
            ////System.out.println("AddedDateList "+dateList.get(i));

            LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();

            changeSets.setChangeSetId(0);
            //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
            changeSets.setChangeSetDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();

            changes.setFrom(getCurrentDate() + "" + "T" + startTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + endTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTimeZoneId(""+editBookingDetails.getTimeZone());


            if (changedDeskId>0){
                if (changedTeamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                    changes.setRequestedTeamDeskId(changedDeskId);
                    changes.setRequestedTeamId(changedTeamId);
                    changes.setUsageTypeId(7);
                } else {
                    changes.setRequestedTeamDeskId(null);
                    changes.setRequestedTeamId(null);
                    changes.setTeamDeskId(changedDeskId);
                    changes.setUsageTypeId(2);

                }
            } else {
                if (newEditStatus.equalsIgnoreCase("new")){
                    changes.setRequestedTeamDeskId(null);
                    changes.setRequestedTeamId(null);
                    changes.setTeamDeskId(selectedDeskId);
                    changes.setUsageTypeId(2);

                } else {
                    changes.setRequestedTeamDeskId(selectedDeskId);
                    changes.setRequestedTeamId(selectedTeamId);
                    changes.setUsageTypeId(7);
                }

            }

//            changes.setTeamDeskId(teamDeskIdForBooking);

            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            changeSetsList.add(changeSets);


        }
        locateBookingRequest.setChangeSetsList(changeSetsList);
        List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        //System.out.println("RepeatModuleRequestData "+locateBookingRequest);

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
        String selectedDate=calSelectedDate.toString();
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

        //System.out.println("RepeatModuleCarRequestData "+locateCarParkBookingRequest);

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


        TextView locateFilterCancel, locateFilterApply, tvFilterAmenities;

       /* BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));*/


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_locate_filter, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RelativeLayout layout = bottomSheetDialog.findViewById(R.id.amenitiesViewBlock);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);
        tvFilterAmenities = bottomSheetDialog.findViewById(R.id.tvFilter);
        filterSearch = bottomSheetDialog.findViewById(R.id.filterSearch);
        filterTotalSize = bottomSheetDialog.findViewById(R.id.filterTotalSize);

        //Language
        filterSearch.setHint(" "+appKeysPage.getSearch());
        tvFilterAmenities.setText(appKeysPage.getFilters());


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        //locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int i = 0; i < userAllowedMeetingResponseList.size(); i++) {
                    for (int j = 0; j < meetingStatusModelList.size(); j++) {
                        if (userAllowedMeetingResponseList.get(i).getId() == meetingStatusModelList.get(j).getId()) {
                            if (meetingStatusModelList.get(j).getStatus() == 1) {
                                List<UserAllowedMeetingResponse.Amenity> amenityList = userAllowedMeetingResponseList.get(i).getAmenities();

                                doCheckAppliedAminitiesWithMeetingRoom(amenityList, meetingStatusModelList.get(j));


                            }

                        }


                    }

                }
*/

//                callInitView();

                filterAmenitiesList.clear();
                ItemAdapter itemAadapter=new ItemAdapter();
                ArrayList<DataModel> userSelectedAmenities =itemAadapter.getUpdatedList();
                int amenitiesMatchCount=0;

                //Checking Here Only With Rooms
                for (int i = 0; i <userSelectedAmenities.get(0).getNestedList().size() ; i++) {
                    if(userSelectedAmenities.get(0).getNestedList().get(i).isChecked()) {
                        filterAmenitiesList.add(userSelectedAmenities.get(0).getNestedList().get(i).getId());
                    }
                }


                bottomSheetDialog.dismiss();
            }
        });

        //mList = new ArrayList<>();

        //list1L
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        //valuesPOJO = new ValuesPOJO("", false);
        //nestedList1.add(valuesPOJO);

        //if (filterClickedStatus == 0) {
        //filterClickedStatus = 1;
        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

        for (int i = 0; i < amenitiesResponseList.size(); i++) {

            //if(amenitiesResponseList.get(i).isAvailable()){
            valuesPOJO = new ValuesPOJO(amenitiesResponseList.get(i).getId(), amenitiesResponseList.get(i).getName(), false);
            nestedList2.add(valuesPOJO);
            //}
        }

        //mList.add(new DataModel(nestedList1, "Workspaces"));
        mList.add(new DataModel(nestedList2, appKeysPage.getRooms()));

        //}

        for (int i = 0; i < mList.size(); i++) {

            mList.get(i).setExpandable(true);

        }
        filterTotalSize.setText("(" + mList.get(0).getNestedList().size() + ")");
        adapter = new ItemAdapter(mList,this);
        locateFilterMainRV.setAdapter(adapter);


        filterSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        locateFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //When we search mList is updated, so clear when close
                adapter.clearAll();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

    }

/*
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



        bottomSheetDialog.show();

    }
*/



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
        //System.out.println("lang check global" + global);
        //System.out.println("lang check bookindata" + bookindata);
        //System.out.println("lang check" + appKeysPage);
        //System.out.println("lang check" + appKeysPage.getStart());
        //System.out.println("lang check" + binding.tvStartLocate);
        binding.tvStartLocate.setText(appKeysPage.getStart());
        binding.tvLocateEndTime.setText(appKeysPage.getEnd());


    }

    private void doRepeatMeetingRoomBookingForWeek(boolean isTeamsChecked, boolean isReq,
                                                   List<String> externalAttendeesEmail) {

        String selectedDate = calSelectedDate.toString();
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
            changes.setRequest(isReq);

            changeset.setChanges(changes);

            List<Integer> attendeesList = new ArrayList<>();

            //Newly Participant Added
            if (chipList != null) {
                for (int j = 0; j < chipList.size(); j++) {
                    attendeesList.add(chipList.get(j).getId());
                }

            } //End

            changes.setAttendees(attendeesList);

//            List<MeetingRoomRecurrence.Changeset.Changes.ExternalAttendees> externalAttendees=new ArrayList<>();
            changes.setExternalAttendees(externalAttendeesEmail);

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

        //System.out.println("MeetingRoomRecurrence "+meetingRoomRecurrence);

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


        //System.out.println("RepeatMeetingRoom " + meetingRoomRequest);*/




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
            //System.out.println("AllUserSelectedAmenitiesAvaliableHInThisRoom "+meetingStatusModel.getId());

        }else {
            //System.out.println("UserSelectedAmenityIsNotAvaliableHInThisRoom "+meetingStatusModel.getId());
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
                    try {
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
                    } catch (Exception exception){

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
    public void companyDefaults(){
        if (Utils.isNetworkAvailable(activityContext)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CompanyDefaultResponse> call = apiService.getCompanyDefaultSettings();
            call.enqueue(new Callback<CompanyDefaultResponse>() {
                @Override
                public void onResponse(Call<CompanyDefaultResponse> call, Response<CompanyDefaultResponse> response) {
                    try {
                        if (response.code()==200){
                            CompanyDefaultResponse companyDefaultResponse = response.body();
                            companyDefaultDeskStartTime = companyDefaultResponse.getDeskAvailabilityHours().getFrom();
                            companyDefaultDeskStartTime = companyDefaultResponse.getDeskAvailabilityHours().getMyto();
                        }

                    } catch (Exception exception){

                    }

                }
                @Override
                public void onFailure(Call<CompanyDefaultResponse> call, Throwable t) {
                }
            });

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }
    }

    //New...
    public void loadAssertSpinner() {
//        assertSpinner = activityContext.findViewById(R.id.assertSpinner);
//        assertList.clear();

        assertList = new ArrayList<>();
        assertSpinner.setSpinnerEventsListener(this);
        for (int i=1;i<8;i++){
            AssertModel assertModel= new AssertModel();
            assertModel.setId(i);
            switch (i){
                case 1:
                    assertModel.setAssertName("Workspace");
                    assertModel.setImage(R.drawable.chair);
                    break;
                case 2:
                    assertModel.setAssertName("Room");
                    assertModel.setImage(R.drawable.room);
                    break;
                case 3:
                    assertModel.setAssertName("Parking");
                    assertModel.setImage(R.drawable.car);
                    break;
                case 4:
                    assertModel.setAssertName("Remote");
                    assertModel.setImage(R.drawable.home);
                    break;
                case 5:
                    assertModel.setAssertName("Sick");
                    assertModel.setImage(R.drawable.sick_plus);
                    break;
                case 6:
                    assertModel.setAssertName("Holiday");
                    assertModel.setImage(R.drawable.plane);
                    break;
                case 7:
                    assertModel.setAssertName("Training");
                    assertModel.setImage(R.drawable.training_book);
                    break;
                default:
            }

            assertList.add(assertModel);
        }
        assertListAdapter = new AssertListAdapter(context, assertList,this);
        assertSpinner.setAdapter(assertListAdapter);
        assertSpinner.setSelection(0);
        assertListAdapter.notifyDataSetChanged();

        /*
        OnSpinnerEventsListener onSpinnerEventsListener = this;
        if (assertSpinner!=null)
            assertSpinner.performClosedEvent(assertSpinner);
        */

    }
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
    public void onChangeDesk(BookingForEditResponse.TeamDeskAvailabilities deskList,int deskId, String deskName, String request,
                             String timeZone,int typeId,
                             EditBookingDetails editBookingDetails,String newEditStatus,int teamId) {
        changedTeamId = teamId;
        changedDeskId = deskId;
        selectedDeskId = deskId;
        continueEditBook.setVisibility(View.VISIBLE);
        if (locationAddress!=null)
            locationAddress.setText(Utils.checkStringParms(deskList.getLocationDetails().getBuildingName())+
                ", "+
                Utils.checkStringParms(deskList.getLocationDetails().getfLoorName()));
        if (tv_description!=null)
            tv_description.setText(Utils.checkStringParms(deskList.getDescription()));

        Toast.makeText(context, "dsj"+request, Toast.LENGTH_SHORT).show();
        if (typeId == 0) {
            editBookingDetailsGlobal.setDeskCode(deskName);
            editBookingDetailsGlobal.setTimeZone(timeZone);

            if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                editBookingDetailsGlobal.setDesktId(deskId);
                editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
            } else {
                editBookingDetailsGlobal.setDesktId(deskId);
                editBookingDetailsGlobal.setDeskTeamId(selectedTeamId);
                editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                editBookingDetailsGlobal.setRequestedTeamId(0);
            }
            selectedDeskId=deskId;
            if(request.equalsIgnoreCase("new"))
                editBookingUsingBottomSheet(editBookingDetails,
                        1,0,"new");
            else
                editBookingUsingBottomSheet(editBookingDetails,
                        1,0,"request");
            /*
            if(selectedTeamId == SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID)
                && selectedTeamAutoApproveStatus!=2 && selectedTeamAutoApproveStatus!=3)
                editBookingUsingBottomSheet(editBookingDetailsGlobal,
                        1,0,"new");
            else
                editBookingUsingBottomSheet(editBookingDetailsGlobal,
                        1,0,"request");*/
        } else {
            if (newEditStatus.equalsIgnoreCase("edit")){
                selectedDeskId = deskId;
                deskRoomName.setText(deskName);
                if (editBookingDetails!=null){
                    editBookingDetails.setDeskCode(deskName);
                    editBookingDetails.setDesktId(deskId);
                    editBookingDetails.setDeskTeamId(selectedTeamId);
                    editBookingDetails.setTimeZone(timeZone);
                }
                if(request.equalsIgnoreCase("request")){
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                }

            }else if (newEditStatus.equalsIgnoreCase(request)){
                selectedDeskId = deskId;
                deskRoomName.setText(deskName);
                if (editBookingDetails!=null){
                    editBookingDetails.setDeskCode(deskName);
                    editBookingDetails.setDesktId(deskId);
                    editBookingDetails.setDeskTeamId(selectedTeamId);
                    editBookingDetails.setTimeZone(timeZone);
                }
                if(request.equalsIgnoreCase("request")){
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                }

            } else {
                selectedDeskId = deskId;
                deskRoomName.setText(deskName);
                if (editBookingDetails!=null){
                    editBookingDetails.setDeskCode(deskName);
                    editBookingDetails.setDesktId(deskId);
                    editBookingDetails.setDeskTeamId(selectedTeamId);
                    editBookingDetails.setTimeZone(timeZone);
                }
                if(request.equalsIgnoreCase("request")){
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));

                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));

                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                }
                if(roomBottomSheet!=null)
                    roomBottomSheet.dismiss();
                if(request.equalsIgnoreCase("new"))
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"new");
                else
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"request");
            }
            /*selectedDeskId = deskId;
            deskRoomName.setText(deskName);
            if (editBookingDetails!=null){
                editBookingDetails.setDeskCode(deskName);
                editBookingDetails.setDesktId(deskId);
                editBookingDetails.setDeskTeamId(selectedTeamId);
                editBookingDetails.setTimeZone(timeZone);
            }*/
        }
    }

    @Override
    public void onActiveTeamsSelected(int teamId, String teamName,
                                      int typeId, EditBookingDetails editDeskBookingDetails,String newEditStatus) {
        if (newEditStatus.equalsIgnoreCase("edit")) {
            selectedTeamId = teamId;
            tvTeamName.setText(teamName);

            selectedDeskList(selectedTeamId,
                    Utils.getISO8601format(editDeskBookingDetails.getDate()),editDeskBookingDetails,newEditStatus);
//            getDeskList("-1", calSelectedDate,newEditStatus);
        } else {
            selectedTeamId = teamId;
            tvTeamName.setText(teamName);
            /*if (editDeskBookingDetails.getRequestedTeamId()>0)
                selectedDeskList(editDeskBookingDetails.getRequestedTeamId(),
                        Utils.getISO8601format(editDeskBookingDetails.getDate()),editDeskBookingDetails,newEditStatus);
            else*/
            selectedDeskList(selectedTeamId,
                    Utils.getISO8601format(editDeskBookingDetails.getDate()),editDeskBookingDetails,newEditStatus);
        }
    }

    @Override
    public void onEDITChangeDesk(int deskId, String deskName, String request, String timeZone, int typeId, EditBookingDetails editBookingDetails, String newEditStatus, int teamId) {

    }


    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        Log.d(TAG, "onPopupWindowOpened: "+spinner.getSelectedItemPosition());
        assertSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_outline_opened));
        View view = assertSpinner.getSelectedView();

        TextView whiteLine = view.findViewById(R.id.divider);
        ImageView tick = view.findViewById(R.id.tick);
        RelativeLayout bg = view.findViewById(R.id.background);
        whiteLine.setVisibility(View.GONE);
        tick.setVisibility(View.GONE);
        bg.setBackgroundColor(Color.TRANSPARENT);
        int pos = assertSpinner.getSelectedItemPosition();

        assertListAdapter = new AssertListAdapter(context, assertList,this);
        assertSpinner.setAdapter(assertListAdapter);
        assertSpinner.setSelection(pos);
        assertListAdapter.notifyDataSetChanged();


    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        View view = assertSpinner.getSelectedView();

        TextView whiteLine = view.findViewById(R.id.divider);
        ImageView tick = view.findViewById(R.id.tick);
        RelativeLayout bg = view.findViewById(R.id.background);
        whiteLine.setVisibility(View.GONE);
        tick.setVisibility(View.GONE);
        bg.setBackgroundColor(Color.TRANSPARENT);

        selectedicon = assertSpinner.getSelectedItemPosition()+1;
        assertSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_outline));
        switch (selectedicon) {
            case 1:
                tabToggleViewClicked(1);
                break;
            case 2:
                tabToggleViewClicked(2);
                break;
            case 3:
                tabToggleViewClicked(3);
                break;
            case 4:
                tabToggleViewClicked(1);
                break;
            case 5:
                tabToggleViewClicked(1);
                break;
            case 6:
                tabToggleViewClicked(1);
                break;
            case 7:
                tabToggleViewClicked(1);
                break;
            default:
                tabToggleViewClicked(1);
        }
//        tabToggleViewClicked(selectedicon);

    }

    @Override
    public void clickCount(ArrayList<DataModel> mList, int pos) {
        if (mList!=null && mList.size()>0) {

            ArrayList<ValuesPOJO> nestedList = mList.get(pos).getNestedList();

            if (nestedList!=null && nestedList.size()>0){

                ArrayList<ValuesPOJO> inComingList = new ArrayList<>();
                inComingList = (ArrayList<ValuesPOJO>) nestedList.stream().filter(val -> val.isChecked()).collect(Collectors.toList());

                if(inComingList!=null && inComingList.size()>0) {

                    if (filterTotalSize!=null){
                        filterTotalSize.setText("("+String.valueOf(inComingList.size())+")");
                    }

                }else {
                    if (filterTotalSize!=null){
                        filterTotalSize.setText("(0)");
                    }
                }

            }

        }

    }
}
