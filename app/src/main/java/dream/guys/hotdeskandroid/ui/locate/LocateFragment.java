package dream.guys.hotdeskandroid.ui.locate;

import static dream.guys.hotdeskandroid.utils.Utils.currentTimeWithExtraMins;
import static dream.guys.hotdeskandroid.utils.Utils.getActionOverLaysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getBookingPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDateWithDay;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;
import static dream.guys.hotdeskandroid.utils.Utils.getGlobalScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getLoginScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getMeetingRoomsPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getResetPasswordPageScreencreenData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.DeskSelectListAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.adapter.RepeateDataAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.example.CanvasView;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.*;
import dream.guys.hotdeskandroid.model.request.CarParkingDeleteRequest;
import dream.guys.hotdeskandroid.model.request.CarParkingStatusModel;
import dream.guys.hotdeskandroid.model.request.DeleteMeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.DeskStatusModel;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkEditRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookEditFromRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskDeleteRequest;
import dream.guys.hotdeskandroid.model.request.LocationMR_Request;
import dream.guys.hotdeskandroid.model.request.MeetingAmenityStatus;
import dream.guys.hotdeskandroid.model.request.MeetingRoomEditRequest;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRecurrence;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.MeetingStatusModel;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.request.SelectCode;
import dream.guys.hotdeskandroid.model.response.*;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkAvalibilityResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingslotsResponse;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.DeskDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.LocationWithMR_Response;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.MeetingRoomDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.LogicHandler;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateFragment extends Fragment implements ShowCountryAdapter.OnSelectListener, BookingListToEditAdapter.OnEditClickable, DeskListRecyclerAdapter.OnSelectSelected, CarListToEditAdapter.CarEditClickable, MeetingListToEditAdapter.OnMeetingEditClickable, DeskSelectListAdapter.OnDeskSelectClickable, ParticipantNameShowAdapter.OnParticipantSelectable,
        RepeateDataAdapter.repeatInterface, LocateMyTeamAdapter.ShowMyTeamLocationClickable, ItemAdapter.selectItemInterface {

    @BindView(R.id.locateProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.statusUnAvaliable)
    TextView statusUnAvaliable;
    @BindView(R.id.statusBookedByMe)
    TextView statusBookedByMe;
    @BindView(R.id.statusBooked)
    TextView statusBooked;
    @BindView(R.id.statusByRequest)
    TextView statusByRequest;
    @BindView(R.id.statusAvaliable)
    TextView statusAvaliable;


    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;

    //For Language
    TextView tvFilterAmenities;

    //Teams
    boolean teamsCheckBoxStatus = false;
    boolean isMeetingCheckBoxCheckedStatus = false;


    //BottomSheetData
    TextView country, state, street, floor, back, bsApply;
    RecyclerView rvCountry, rvState, rvStreet, rvFloor;
    ShowCountryAdapter showCountryAdapter;
    FloorAdapter floorAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock, statBlock, streetBlock, floorBlock;

    TextView bsLocationSearch;

    //FloorSearch
    EditText bsGeneralSearch;


    TextView locateText, title;


    @BindView(R.id.searchLocate)
    TextView searchLocate;
    @BindView(R.id.locateCalendearView)
    TextView locateCalendearView;
    @BindView(R.id.locateStartTime)
    TextView locateStartTime;
    @BindView(R.id.locateEndTime)
    TextView locateEndTime;


    @BindView(R.id.ivLocateMyTeam)
    ImageView ivLocateMyTeam;
    @BindView(R.id.ivLocateFilter)
    ImageView ivLocateFilter;
    @BindView(R.id.ivLocateKey)
    ImageView ivLocateKey;
    @BindView(R.id.locateKeyStatusBlock)
    RelativeLayout locateKeyStatusBlock;


    @BindView(R.id.first)
    ImageView first;

    @BindView(R.id.firstLayout)
    LinearLayout firstLayout;


    @BindView(R.id.locateMyTeamList)
    RelativeLayout locateMyTeamList;
    //@BindView(R.id.rvLocateMyTeam)
    //  RecyclerView rvMyTeam;
    LocateMyTeamAdapter locateMyTeamAdapter;


    @BindView(R.id.secondLayout)
    LinearLayout secondLayout;

    View deskView;
    int canvasss = 0;
    int defaultLocationcheck = 0;


    List<LocateCountryRespose> locateCountryResposeList;


    CanvasView canvasView;
    @NonNull
    FragmentLocateBinding binding;

    //Dialog dialog;
    int stateId = 0;

    int endTimeSelectedStats = 0;

    boolean keyClickedStats = true;
    //List<Point> pointList = new ArrayList<>();

    //CheckInDetails
    TextView locateDeskName, editBookingBack, editBookingContinue;
    RelativeLayout bookingDateBlock, bookingStartBlock, bookingEndBlock, bookingCommentBlock, bookingVechicleRegtBlock;
    EditText etComment, etVehicleReg;
    TextView locateCheckInDate, locateCheckInTime, locateCheckoutTime, showlocateCheckInDate;
    int teamDeskIdForBooking = 0;
    int selectedCarParkingSlotId = 0;

    List<LocateCountryRespose.LocationItemLayout.Desks> desksCode;
    List<LocateCountryRespose.LocationItemLayout.ParkingSlots> carCode;
    List<LocateCountryRespose.LocationItemLayout.MeetingRooms> meetingCode;
    //SetSelectedDeskHere
    TextView tv_desk_room_name;

    //DesAvaliablityChecking
    List<DeskAvaliabilityResponse.TeamDeskAvaliabilityList> teamDeskAvaliabilityList;
    List<TeamsResponse> teamsResponseList;


    //Desk Status
    //DeskStatusModel deskStatusModel = null;
    List<DeskStatusModel> deskStatusModelList = new ArrayList<>();

    //MeetingStatusModel
    List<MeetingStatusModel> meetingStatusModelList = new ArrayList<>();

    //Edit Booking
    int selectedDeskId = 0;
    TextView deskRoomName;

    //Edit CarParking
    int selectedCarId = 0;
    TextView carSelectedName;

    //CarParkingAvalibilityChecking
    List<CarParkingslotsResponse> carParkingslots;
    List<CarParkAvalibilityResponse> carParkAvalibilityResponseList;
    List<CarParkingStatusModel> carParkingStatusModelList;
    boolean carParkingCheckingStatus = false;

    //MeetingAvalibilityChecking
    List<LocationWithMR_Response> locationWithMR_response = new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList;
    //MatchList
    List<LocationWithMR_Response.Matches> matchesList;

    int meetingRoomId = 0;
    TextView startRoomTime, endTRoomime;

    ParticipantDetsilResponse participantDetsilResponse;
    ChipGroup participantChipGroup;

    //Description
    String meetingRoomDescription = null;
    String carParkDescription = null;
    String deskDescriotion = null;

    //New...
    TextView txtInterval;
    BottomSheetDialog repeatDataBottomSheetDialog, locateEditBottomSheet, locateCarEditBottomSheet, locateMeetEditBottomSheet;
    List<ParticipantDetsilResponse> chipList = new ArrayList<>();

    //In Participant Edit add and delete attendees
    //List<Integer> attendeesAddDelList=new ArrayList<>();
    List<MeetingListToEditResponse.Attendees> attendeesListForEdit;

    int page = 1;


    //Repeat
    BottomSheetDialog repeatBottomSheetDialog;
    String type = "None";
    int enableCurrentWeek = -1;
    //Used to call repeat and recurrence
    boolean repeatActvieStatus = false;
    TextView tvRepeat, repeat_room;
    ;
    String repeatSelectedDate = "";

    //MyTeam
    //BottomSheetDialog myTeamBottomSheet;
    //BottomSheetBehavior myteamBottomSheetBehavior;
    RelativeLayout myTeamHeader, myTeamContactBlock;
    //Contact
    TextView locateMyTeamUserName, tvLocateMyTeamLocationView, locateMyTeamDeskName, myTeam_tv_start_time, myTeam_tv_end_time, tvMyTeamEmail, tvMyTeamTeams, tvmyTeamPhone, myTeamBookNearBy, bookNearByBack;

    //Filter
    List<MeetingAmenityStatus> meetingAmenityStatusList = new ArrayList<>();
    boolean amenitiesApplyStatus = false;
    ArrayList<DataModel> mList = new ArrayList<>();
    ;
    int filterClickedStatus = 0;

    List<AmenitiesResponse> amenitiesListToShowInMeetingRoomList = new ArrayList<>();

    //New...
    String orgSTime = "";
    String orgETime = "";
    UserDetailsResponse profileData;
    boolean isOnTextChanged = false;

    boolean isVehicleReg = false;
    String editLastEndTime = "";


    //FloorSearch
    boolean floorSearchStatus = false;

    //Get TimeZoneId
    List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilitiesList;

    //New...
    //For Displaying the count
    TextView filterTotalSize;

    List<DAOTeamMember> locateMyTeamMemberStatusList;


    //SupportZonelayout canvas
    List<LocateCountryRespose.SupportZoneLayoutItems> getSupportZoneLayoutForCanvas;


    //New...
    ImageView img_bsCountry,img_bsState,img_bsStreet;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProgressDialog.touchLock(getContext(), getActivity());
        //TimeZoneId
        int defaultTeamId = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);

        //Before initLoadFloorDetails need to get timezone
        getTimeZoneForBooking(defaultTeamId);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.locateProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initLoadFloorDetails(0);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1000);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        setLanguage();



        //New...
        checkVeichleReg();


        //TeamsCheck
        checkTeamsCheckBox();

        //New...
        profileData = Utils.getLoginData(getActivity());

        binding.locateStartTime.setText(getCurrentTime());
        binding.locateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if (parentId > 0) {

                    bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateStartTime, "Start", binding.locateCalendearView.getText().toString(), 1);

                } else {
                    Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (endTimeSelectedStats == 0) {
            binding.locateEndTime.setText("23:59");
        }

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if (parentId > 0) {

                    bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateEndTime, "End", binding.locateCalendearView.getText().toString(), 2);

                } else {
                    Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
                }

                // Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), binding.locateEndTime, "", "");


            }
        });


        binding.locateCalendearView.setText(getCurrentDate());
        binding.showCalendar.setText(getCurrentDateWithDay());
        checkIsCurrentDate(binding.locateCalendearView.getText().toString());
        binding.locateCalendearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if (parentId > 0) {

                    bottomSheetLocateDatePickerInBooking(getContext(), getActivity(), "", "", binding.locateCalendearView);

                } else {
                    Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        binding.searchLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //List<DAOTeamMember> locateMyTeamMemberStatusList=new ArrayList<>();
                //locateMyTeamMemberStatusList.set(0,null);
                //If my team layout opens when floor selecting bottomsheet open below method will close
                closeAndClearMyTeamList(locateMyTeamMemberStatusList);

                getLocateCountryList();

            }
        });

        binding.ivLocateMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //locateMyTeamList.setVisibility(View.VISIBLE);

                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if (parentId > 0) {

                    ProgressDialog.touchLock(getContext(), getActivity());
                    getFirAidAndFirwarndsReport();

                    //getMyTeamMemberData();
                } else {
                    Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        binding.ivLocateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if (parentId > 0) {

                    /*if (filterClickedStatus == 0) {
                        getLocateAmenitiesFilterData(true);
                    } else {
                        //amenitiesResponseList
                        meetingAmenityStatusList.clear();
                        callLocateFilterBottomSheet(null);
                    }*/
                    meetingAmenityStatusList.clear();
                    getLocateAmenitiesFilterData(true);


                } else {
                    Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
                }


            }
        });

        binding.ivLocateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (keyClickedStats) {
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.key_icon_orange));
                    binding.locateKeyStatusBlock.setVisibility(View.VISIBLE);
                    keyClickedStats = false;

                } else {
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.locate_key));
                    binding.locateKeyStatusBlock.setVisibility(View.INVISIBLE);
                    keyClickedStats = true;
                }

            }
        });


        //Initally Load Floor Details
        //initLoadFloorDetails(0);


        return root;
    }

    private void getFirAidAndFirwarndsReport() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FirstAidResponse>> call = apiService.getFirstAidResponse();
        call.enqueue(new Callback<List<FirstAidResponse>>() {
            @Override
            public void onResponse(Call<List<FirstAidResponse>> call, Response<List<FirstAidResponse>> response) {
                List<FirstAidResponse> firstAidResponseList = response.body();

                //Get Whole List
                List<FirstAidResponse.Persons> personFirewardenssList = new ArrayList<>();
                List<FirstAidResponse.Persons> personFirstAidList = new ArrayList<>();
                //firstAidList= new HashMap<>();
                //firewardenList = new HashMap<>();


                //Get userId Alone
                List<Integer> firstAidList = new ArrayList<>();
                List<Integer> firewardenList = new ArrayList<>();

                if (firstAidResponseList != null && firstAidResponseList.size() > 0) {

                    for (int i = 0; i < firstAidResponseList.size(); i++) {
                        if (firstAidResponseList.get(i).getPersonsList().size() > 0) {

                            //Firewardenss
                            if (firstAidResponseList.get(i).getType() == 4) {

                                if (firstAidResponseList.get(i).getPersonsList() != null && firstAidResponseList.get(i).getPersonsList().size() > 0) {

                                    for (int j = 0; j < firstAidResponseList.get(i).getPersonsList().size(); j++) {
                                        personFirewardenssList.add(firstAidResponseList.get(i).getPersonsList().get(j));
                                    }
                                }
                            }

                            //FirstAid
                            if (firstAidResponseList.get(i).getType() == 5) {

                                if (firstAidResponseList.get(i).getPersonsList() != null && firstAidResponseList.get(i).getPersonsList().size() > 0) {

                                    for (int j = 0; j < firstAidResponseList.get(i).getPersonsList().size(); j++) {
                                        personFirstAidList.add(firstAidResponseList.get(i).getPersonsList().get(j));
                                    }

                                }
                            }

                        }
                    }

                }

                //Get UserId alone
                for (int i = 0; i < personFirewardenssList.size(); i++) {
                    firewardenList.add(personFirewardenssList.get(i).getId());
                }
                for (int i = 0; i < personFirstAidList.size(); i++) {
                    firstAidList.add(personFirstAidList.get(i).getId());
                }

                binding.locateProgressBar.setVisibility(View.GONE);

                getMyTeamMemberData(firstAidList, firewardenList);

            }

            @Override
            public void onFailure(Call<List<FirstAidResponse>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.GONE);
            }
        });


    }


    private void getMyTeamMemberData(List<Integer> firstAidList, List<Integer> firewardenList) {


        binding.locateProgressBar.setVisibility(View.VISIBLE);

        binding.tvPMOOffice.setText(SessionHandler.getInstance().get(getContext(), AppConstants.CURRENT_TEAM));

        String startTime = binding.locateStartTime.getText().toString() + ":00.000Z";

        String dateWithTime = binding.locateCalendearView.getText().toString() + "T" + startTime;
        System.out.println("StartTimeForamt " + dateWithTime);

        //2022-09-20T10:51:17.830Z
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        int tenandiId = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);
        Call<ArrayList<DAOTeamMember>> call = apiService.getTeamMembersWithImage(dateWithTime, "" + tenandiId, true, true);
        call.enqueue(new Callback<ArrayList<DAOTeamMember>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOTeamMember>> call, Response<ArrayList<DAOTeamMember>> response) {

                if (response.body() != null && response.body().size() > 0) {
                    List<DAOTeamMember> daoTeamMemberList = response.body();

                    //callMyTeamBottomSheet(daoTeamMemberList);

                    binding.locateMyTeamList.setVisibility(View.VISIBLE);
                    binding.bookNearByBlock.setVisibility(View.GONE);

                    if (daoTeamMemberList != null) {

                        callMyTeamLayout(daoTeamMemberList, firstAidList, firewardenList);

                    }
                }

                //Hide BottomNavigation Bar
                ((MainActivity) getActivity()).getNav().setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<DAOTeamMember>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    private void callMyTeamLayout(List<DAOTeamMember> daoTeamMemberList, List<Integer> firstAidList, List<Integer> firewardenList) {

        //rvMyTeam = myTeamBottomSheet.findViewById(R.id.rvLocateMyTeam);

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvLocateMyTeam.setLayoutManager(linearLayoutManager);
        binding.rvLocateMyTeam.setHasFixedSize(true);*/


        locateMyTeamMemberStatusList = new ArrayList<>();
        for (int i = 0; i < daoTeamMemberList.size(); i++) {

            if (daoTeamMemberList.get(i).getDayGroups().isEmpty()) {
                locateMyTeamMemberStatusList.add(daoTeamMemberList.get(i));
            } else {

                for (int j = 0; j < daoTeamMemberList.get(i).getDayGroups().size(); j++) {

                    ArrayList<DAOTeamMember.DayGroup.CalendarEntry> calendarEntries = null;
                    ArrayList<DAOTeamMember.DayGroup.MeetingBooking> meetingEntries = null;
                    ArrayList<DAOTeamMember.DayGroup.CarParkBooking> carParkEntries = null;

                    if (daoTeamMemberList.get(i).getDayGroups().get(0).getCalendarEntries() != null) {
                        calendarEntries = daoTeamMemberList.get(i).getDayGroups().get(0).getCalendarEntries();
                    }
                    /*if (bookingListResponses.getDayGroups().get(i).getMeetingBookings()!=null){
                        meetingEntries =
                                bookingListResponses.getDayGroups().get(i).getMeetingBookings();
                    }
                    if (bookingListResponses.getDayGroups().get(i).getCarParkBookings()!=null){
                        carParkEntries =
                                bookingListResponses.getDayGroups().get(i).getCarParkBookings();
                    }*/

                    if (calendarEntries != null) {
                        for (int k = 0; k < calendarEntries.size(); k++) {
                            DAOTeamMember.DayGroup momdel = new DAOTeamMember.DayGroup();
                            DAOTeamMember daoTeamMember = new DAOTeamMember();
                            //daoTeamMember=daoTeamMemberList.get(i);
                            daoTeamMember.setFirstName(daoTeamMemberList.get(i).getFirstName());
                            daoTeamMember.setLastName(daoTeamMemberList.get(i).getLastName());
                            ArrayList<DAOTeamMember.DayGroup> dayGroupList = new ArrayList<>();

                            momdel.setCalendarEntriesModel(calendarEntries.get(k));

                            dayGroupList.add(momdel);
                            daoTeamMember.setDayGroups(dayGroupList);
                            locateMyTeamMemberStatusList.add(daoTeamMember);

                        }

                    }
                }
                  /*  if (meetingEntries!=null){
                        for (int j=0; j < meetingEntries.size(); j++){
                            TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
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
                            TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
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
                    }*/

            }

        }

        binding.rvLocateMyTeam.setHasFixedSize(true);


        //For FirstAid
        for (int i = 0; i < firstAidList.size(); i++) {

            for (int j = 0; j < locateMyTeamMemberStatusList.size(); j++) {

                if (firstAidList.get(i) == locateMyTeamMemberStatusList.get(j).getUserId()) {
                    locateMyTeamMemberStatusList.get(j).setIfFirstAidStatus(true);
                    break;
                }

            }

        }

        //For Firewarnds

        for (int i = 0; i < firewardenList.size(); i++) {

            for (int j = 0; j < locateMyTeamMemberStatusList.size(); j++) {

                if (firewardenList.get(i) == locateMyTeamMemberStatusList.get(j).getUserId()) {
                    locateMyTeamMemberStatusList.get(j).setFireStatus(true);
                    break;
                }

            }

        }


        locateMyTeamAdapter = new LocateMyTeamAdapter(getContext(), locateMyTeamMemberStatusList, this);
        binding.rvLocateMyTeam.setAdapter(locateMyTeamAdapter);

        ProgressDialog.clearTouchLock(getContext(), getActivity());

        binding.locateProgressBar.setVisibility(View.INVISIBLE);

        binding.myTeamClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.locateMyTeamSearch.setText("");

                closeAndClearMyTeamList(locateMyTeamMemberStatusList);


            }
        });

        binding.locateMyTeamSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                locateMyTeamAdapter.getFilter().filter(s.toString());
               /* if (s.toString().length() >= 2) {
                    locateMyTeamAdapter.getFilter().filter(s.toString());
                }*/

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  binding.bookNearByBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.myTeamClose.setVisibility(View.VISIBLE);
                //binding.locateMyTeamList.setVisibility(View.VISIBLE);
                binding.bookNearByBlock.setVisibility(View.GONE);
                binding.bookNearByBack.setVisibility(View.GONE);
                binding.myTeamBookNearBy.setVisibility(View.GONE);

            }
        });*/


    }

    private void closeAndClearMyTeamList(List<DAOTeamMember> locateMyTeamMemberStatusList) {

        if (locateMyTeamMemberStatusList != null && locateMyTeamMemberStatusList.size() > 0 && binding.locateMyTeamList.getVisibility() == View.VISIBLE) {
            locateMyTeamMemberStatusList.clear();
            //visible BottomNavigation Bar
            ((MainActivity) getActivity()).getNav().setVisibility(View.VISIBLE);
            binding.locateMyTeamList.setVisibility(View.GONE);
        }


    }

    //MyTeamBottomSheet
   /* private void callMyTeamBottomSheet(List<DAOTeamMember> daoTeamMemberList) {

        TextView myTeamClose,tvMyTeamLocate,tvPMOOffice,tvAllTeams;;
        TextView locateMyTeamSearch;


        *//*BottomSheetDialog myTeamBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        myTeamBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_myteam_bottomsheet,
                new RelativeLayout(getContext())));*//*

        myTeamBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_myteam_bottomsheet, null);
        myTeamBottomSheet.setContentView(view);
        myteamBottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        myteamBottomSheetBehavior.setPeekHeight(600);

        tvMyTeamLocate=myTeamBottomSheet.findViewById(R.id.tvMyTeam);
        tvPMOOffice=myTeamBottomSheet.findViewById(R.id.tvPMOOffice);
        tvAllTeams=myTeamBottomSheet.findViewById(R.id.tvAllTeams);
        locateMyTeamSearch=myTeamBottomSheet.findViewById(R.id.locateMyTeamSearch);

        tvAllTeams.setText(appKeysPage.getAllTeams());
        tvMyTeamLocate.setText(appKeysPage.getMYTeam());
        locateMyTeamSearch.setHint(appKeysPage.getSearch());



        rvMyTeam = myTeamBottomSheet.findViewById(R.id.rvLocateMyTeam);
        myTeamClose = myTeamBottomSheet.findViewById(R.id.myTeamClose);
        bookNearByBack=myTeamBottomSheet.findViewById(R.id.bookNearByBack);

        myTeamHeader = myTeamBottomSheet.findViewById(R.id.myTeamHeader);

        myTeamContactBlock = myTeamBottomSheet.findViewById(R.id.myTeamContactBlock);
        locateMyTeamUserName = myTeamBottomSheet.findViewById(R.id.locateMyTeamUserName);
        tvLocateMyTeamLocationView = myTeamBottomSheet.findViewById(R.id.tvLocateMyTeamLocationView);
        locateMyTeamDeskName = myTeamBottomSheet.findViewById(R.id.locateMyTeamDeskName);
        myTeam_tv_start_time = myTeamBottomSheet.findViewById(R.id.myTeam_tv_start_time);
        myTeam_tv_end_time = myTeamBottomSheet.findViewById(R.id.myTeam_tv_end_time);
        tvMyTeamEmail = myTeamBottomSheet.findViewById(R.id.tvMyTeamEmail);
        tvMyTeamTeams = myTeamBottomSheet.findViewById(R.id.tvMyTeamTeams);
        tvmyTeamPhone = myTeamBottomSheet.findViewById(R.id.tvmyTeamPhone);
        myTeamBookNearBy = myTeamBottomSheet.findViewById(R.id.myTeamBookNearBy);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyTeam.setLayoutManager(linearLayoutManager);
        rvMyTeam.setHasFixedSize(true);

        bookNearByBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myTeamBottomSheet.dismiss();

            }
        });

        myTeamClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myTeamBottomSheet.dismiss();
            }
        });


        List<DAOTeamMember> locateMyTeamMemberStatusList = new ArrayList<>();
        for (int i = 0; i < daoTeamMemberList.size(); i++) {

            if (daoTeamMemberList.get(i).getDayGroups().isEmpty()) {
                locateMyTeamMemberStatusList.add(daoTeamMemberList.get(i));
            } else {

                for (int j = 0; j < daoTeamMemberList.get(i).getDayGroups().size(); i++) {

                    ArrayList<DAOTeamMember.DayGroup.CalendarEntry> calendarEntries = null;
                    ArrayList<DAOTeamMember.DayGroup.MeetingBooking> meetingEntries = null;
                    ArrayList<DAOTeamMember.DayGroup.CarParkBooking> carParkEntries = null;

                    if (daoTeamMemberList.get(i).getDayGroups().get(0).getCalendarEntries() != null) {
                        calendarEntries = daoTeamMemberList.get(i).getDayGroups().get(0).getCalendarEntries();
                    }
                    *//*if (bookingListResponses.getDayGroups().get(i).getMeetingBookings()!=null){
                        meetingEntries =
                                bookingListResponses.getDayGroups().get(i).getMeetingBookings();
                    }
                    if (bookingListResponses.getDayGroups().get(i).getCarParkBookings()!=null){
                        carParkEntries =
                                bookingListResponses.getDayGroups().get(i).getCarParkBookings();
                    }*//*

                    if (calendarEntries != null) {
                        for (int k = 0; k < calendarEntries.size(); k++) {
                            DAOTeamMember.DayGroup momdel = new DAOTeamMember.DayGroup();
                            DAOTeamMember daoTeamMember = new DAOTeamMember();
                            //daoTeamMember=daoTeamMemberList.get(i);
                            daoTeamMember.setFirstName(daoTeamMemberList.get(i).getFirstName());
                            daoTeamMember.setLastName(daoTeamMemberList.get(i).getLastName());
                            ArrayList<DAOTeamMember.DayGroup> dayGroupList = new ArrayList<>();

                            momdel.setCalendarEntriesModel(calendarEntries.get(k));

                            dayGroupList.add(momdel);
                            daoTeamMember.setDayGroups(dayGroupList);
                            locateMyTeamMemberStatusList.add(daoTeamMember);

                        }

                    }
                }
                  *//*  if (meetingEntries!=null){
                        for (int j=0; j < meetingEntries.size(); j++){
                            TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
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
                            TeamMembersResponse.DayGroup momdel = new TeamMembersResponse.DayGroup();
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
                    }*//*

            }

        }

        locateMyTeamAdapter = new LocateMyTeamAdapter(getContext(), locateMyTeamMemberStatusList, this);
        rvMyTeam.setAdapter(locateMyTeamAdapter);

        myTeamBottomSheet.show();


    }*/


    public void initLoadFloorDetails(int canvasDrawStatus) {

        ProgressDialog.touchLock(getContext(), getActivity());

        afterBookingDisableRepeat();

        deskStatusModelList.clear();
        meetingStatusModelList.clear();




      /*  if(!amenitiesApplyStatus){
            meetingAmenityStatusList.clear();
        }

        for (int i = 0; i <meetingAmenityStatusList.size() ; i++) {
            System.out.println("InitialInitAmenitiStatus "+meetingAmenityStatusList.get(i).getId()+" "+amenitiesApplyStatus);
        }*/


        //To load default location-saved in login Activity
        //defaultLocationcheck-0-loaded from other fragment
        //defaultLocationcheck-1-loaded within fragment

        int parentIdCheck = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID_CHECK);
        int selecctedFloorFromHome = SessionHandler.getInstance().getInt(getContext(), AppConstants.SELECTED_LOCATION_FROM_HOME);

        binding.firstLayout.removeAllViews();

        if (parentIdCheck > 0 && defaultLocationcheck == 0 && selecctedFloorFromHome == 0) {
            int floorPositionCheck = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION_CHECK);

            SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, parentIdCheck);
            SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, floorPositionCheck);


            //To set Default location
            String countryCheck = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME_CHECK);
            String buildingCheck = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING_CHECK);
            String floorCheck = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR_CHECK);
            String fullPathCheck = SessionHandler.getInstance().get(getContext(), AppConstants.FULLPATHLOCATION_CHECK);


            SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME, countryCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING, buildingCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR, floorCheck);
            SessionHandler.getInstance().save(getContext(), AppConstants.FULLPATHLOCATION, fullPathCheck);

        }

        //Disable selected location id
        SessionHandler.getInstance().saveInt(getContext(), AppConstants.SELECTED_LOCATION_FROM_HOME, 0);

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
                binding.searchLocate.setHint("Choose Location");
            } else {
                if (fullPathLocation == null) {
                    binding.searchLocate.setText(buildingName + "  " + floorName);
                } else {
                    binding.searchLocate.setText(fullPathLocation);
                }
            }

            //ForCoordinate
            int subParentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.SUB_PARENT_ID);
            boolean findCoordinateStatus = true;
            //getFloorDetails(subParentId,findCoordinateStatus);

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            //Used For Desk Avaliability Checking
            getAvaliableDeskDetails(null, 0);

            //CarChecking
            doInitCarAvalibilityHere(parentId);

            //Meeting Checking
            doInitMeetingAvalibilityHere(parentId, canvasDrawStatus);

        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTimeZoneForBooking(int defaultTeamId) {

        if (Utils.isNetworkAvailable(getContext())) {

            //binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.
                    getTeamDeskAvailability(defaultTeamId,
                            Utils.getCurrentDate(),
                            Utils.getCurrentDate());

            call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
                @Override
                public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                    System.out.println("SuccessPrintHere ");
                    teamDeskAvailabilitiesList = response.body();

                    //binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    //String timeZoneId=teamDeskAvailabilitiesList.get(0).getTimeZones().get(0).getTimeZoneId();
                    //System.out.println("MyTimeZoneId "+timeZoneId);

                    /*for (int i = 0; i <teamDeskAvailabilitiesList.size() ; i++) {
                        System.out.println("VAlue "+teamDeskAvailabilitiesList.get(i).getTimeZones().get(i).getTimeZoneId());
                    }*/

                    /*if(teamDeskAvailabilitiesList!=null && teamDeskAvailabilitiesList.size()>0){

                    }*/


                    // initLoadFloorDetails(0);


                }

                @Override
                public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {
//                    deepLinking();
                    System.out.println("FailurePrint " + t.getMessage().toString());
                }
            });


        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }

    }

    private void afterBookingDisableRepeat() {
        //After booking disable repeat book here
        repeatActvieStatus = false;
        repeatSelectedDate = "";
    }

    private void doInitMeetingAvalibilityHere(int parentId, int canvasDrawStatus) {

        LocationMR_Request locationMR_request = new LocationMR_Request();

        List<LocationMR_Request.Amenities> amenitiesList = new ArrayList<>();
        locationMR_request.setAmenitiesList(amenitiesList);

        locationMR_request.setFrom(binding.locateCalendearView.getText().toString() + "T" + binding.locateStartTime.getText().toString() + ":00Z");
        locationMR_request.setTo(binding.locateCalendearView.getText().toString() + "T" + binding.locateEndTime.getText().toString() + ":00Z");
        locationMR_request.setDate(binding.locateCalendearView.getText().toString());
        locationMR_request.setLocationId(parentId);
        LocationMR_Request.Timezone timezone = locationMR_request.new Timezone();
        //timezone.setId("India Standard Time");

        //New...
        if (teamDeskAvailabilitiesList != null && teamDeskAvailabilitiesList.size() > 0
                && teamDeskAvailabilitiesList.get(0).getTimeZones() != null &&
                teamDeskAvailabilitiesList.get(0).getTimeZones().size() > 0) {
            timezone.setId(teamDeskAvailabilitiesList.get(0).getTimeZones().get(0).getTimeZoneId());
        }


        locationMR_request.setTimezone(timezone);

        getLocationMR(locationMR_request, parentId, canvasDrawStatus);


    }


    private void getLocationMR(LocationMR_Request locationMR_request, int parentId, int canvasDrawStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocationWithMR_Response>> call = apiService.getLocationMR(locationMR_request);
            call.enqueue(new Callback<List<LocationWithMR_Response>>() {
                @Override
                public void onResponse(Call<List<LocationWithMR_Response>> call, Response<List<LocationWithMR_Response>> response) {

                    //if (response.body()!=null && response.body().size()>0){
                    locationWithMR_response = response.body();

                    //}
                    getUserAllowedMeeting(parentId, canvasDrawStatus);

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocationWithMR_Response>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void getUserAllowedMeeting(int parentId, int canvasDrawStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.userAllowedMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {

                    userAllowedMeetingResponseList = response.body();

                    //ToGetAmenities DataHere
                    amenitiesListToShowInMeetingRoomList.clear();
                    getLocateAmenitiesFilterData(false);

                    //Final Call To Set Desk,Car and Meeting Room
                    getLocateDeskRoomCarDesign(parentId, canvasDrawStatus);

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void doInitCarAvalibilityHere(int parentId) {

        getCarParkingSlots(parentId);

    }

    private void carParkAvalibilityChecking() {

        CarParkingslotsResponse carParkingslotsResponse = null;
        CarParkingStatusModel carParkingStatusModel = null;
        CarParkAvalibilityResponse carParkAvalibilityResponse = null;
        carParkingStatusModelList = new ArrayList<>();
        carParkingStatusModelList.clear();

        if (carParkingslots != null) {

            for (int i = 0; i < carParkingslots.size(); i++) {

                carParkingslotsResponse = carParkingslots.get(i);

                if (carParkAvalibilityResponseList != null) {

                    //M-Added-04-10
                    carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 0);
                    //M-Added-04-10

                    for (int j = 0; j < carParkAvalibilityResponseList.size(); j++) {

                        if (carParkingslotsResponse.getCarParkingSlotId() == carParkAvalibilityResponseList.get(j).getParkingSlotAvalibilityId()) {
                            carParkAvalibilityResponse = carParkAvalibilityResponseList.get(j);
                            //carParkingStatusModel=new CarParkingStatusModel()


                            if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && (carParkingslots.get(i).getParkingSlotAvailability() == 1 && (carParkingslots.get(i).getAssignessList().size() == 0 || checkCurrentUserStatus(carParkingslots.get(i).getAssignessList())))) {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 1);
                                System.out.println("CarParkAvaliable");
                                // ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && carParkingslots.get(i).getParkingSlotAvailability() == 2 && (carParkingslots.get(i).getAssignessList().size() == 0 || checkCurrentUserStatus(carParkingslots.get(i).getAssignessList()))) {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 4);
                                System.out.println("CarParkingRequest");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && (carParkAvalibilityResponse.isAvailable() == false || carParkingslots.get(i).getParkingSlotAvailability() == 1)) {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 0);
                                System.out.println("CarParkUnAvaliable");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == true) {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 3);
                                System.out.println("CarParkingBookedOther");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_booked));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByUser() == true) {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 2);
                                System.out.println("BookedForMe");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                break;
                            } else {
                                carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 0);
                                System.out.println("CarParkUnAvaliable");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                break;
                            }

                        }

                    }

                } else {
                    System.out.println("CarParkUnAvaliable");
                    carParkingStatusModel = new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(), carParkingslotsResponse.getCode(), 0);
                    // ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                }

                carParkingStatusModelList.add(carParkingStatusModel);

            }

        } else {
            System.out.println("CarParkUnAvaliable");
            //deskStatusModel=new DeskStatusModel(key,id,code,0);
            //deskStatusModelList.add(deskStatusModel);
            //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
        }

    }

    private boolean checkCurrentUserStatus(List<CarParkingslotsResponse.Assigness> assignessList) {

        boolean status = false;

        if (assignessList != null && assignessList.size() > 0) {

            int UserId = SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID);

            ArrayList<CarParkingslotsResponse.Assigness> result = new ArrayList<>();
            result = (ArrayList<CarParkingslotsResponse.Assigness>) assignessList.stream().filter(val -> val.getId() == UserId).collect(Collectors.toList());

            if (result != null && result.size() > 0) {
                status = true;
            }

        }


        return status;

    }

    private void getCarParkingAvalibilitySlots() {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            //String toDate=Utils.getCurrentDate()+"T00:00:00Z";
            String toDate = binding.locateCalendearView.getText().toString() + "T00:00:00.000Z";
            //System.out.println("ToDateCheckoing"+toDate);

            //Add min and hour
        /*String startTime = Utils.addMinuteWithCurrentTime(1, 2);
        String fromTime = startTime + ".000Z";
        String endTime = Utils.addMinuteWithCurrentTime(2, 5);
        String toTime = endTime + ".000Z";*/
            String fromTime = binding.locateCalendearView.getText().toString() + "T" + binding.locateStartTime.getText().toString() + ":00Z";
            String toTime = binding.locateCalendearView.getText().toString() + "T" + binding.locateEndTime.getText().toString() + ":00Z";

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkAvalibilityResponse>> call = apiService.getCarParkingSlotAvalibility(toDate, fromTime, toTime);
            call.enqueue(new Callback<List<CarParkAvalibilityResponse>>() {
                @Override
                public void onResponse(Call<List<CarParkAvalibilityResponse>> call, Response<List<CarParkAvalibilityResponse>> response) {

                    if (response.body() != null && response.code() == 200 && response.body().size() > 0) {
                        carParkAvalibilityResponseList = response.body();

                        checkCarParkAvalibilityAndUnAvalibility();
                    }

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<List<CarParkAvalibilityResponse>> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }

    private void checkCarParkAvalibilityAndUnAvalibility() {

        int dateStatus = Utils.doDateCompareHere(binding.locateCalendearView.getText().toString());
        if (dateStatus == 0) {
            //unavaliable

            carParkingStatusModelList.clear();
            if (carParkingslots != null) {
                for (int i = 0; i < carParkingslots.size(); i++) {
                    CarParkingStatusModel carParkingStatusModel = new CarParkingStatusModel(carParkingslots.get(i).getCarParkingSlotId(), carParkingslots.get(i).getCode(), 0);
                    System.out.println("CarParkUnAvaliable");
                    carParkingStatusModelList.add(carParkingStatusModel);
                }
            }
            System.out.println("CarParkUnAvaliable");
        } else if (dateStatus == 1) {
            //todayDate
            System.out.println("CarParktodayDate");
        } else if (dateStatus == 2) {
            //nextdayDate
            System.out.println("CarParknextdayDate");
        }

        if (dateStatus > 0) {
            carParkAvalibilityChecking();
        }

    }

    private void getCarParkingSlots(int parentId) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            //System.out.println("CarPArkingSlotCAlled");

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkingslotsResponse>> call = apiService.getCarParkingSlots(parentId);
            call.enqueue(new Callback<List<CarParkingslotsResponse>>() {
                @Override
                public void onResponse(Call<List<CarParkingslotsResponse>> call, Response<List<CarParkingslotsResponse>> response) {
                    //if (response.body()!=null && response.body().size()>0){
                    carParkingslots = response.body();

                    //CallCarParkAvalibilitySlotsAPI
                    getCarParkingAvalibilitySlots();
                    //}
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<CarParkingslotsResponse>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void getTeams() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamsResponse>> call = apiService.getTeams();
            call.enqueue(new Callback<List<TeamsResponse>>() {
                @Override
                public void onResponse(Call<List<TeamsResponse>> call, Response<List<TeamsResponse>> response) {

                    teamsResponseList = response.body();

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<TeamsResponse>> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }


    private void getLocateDeskRoomCarDesign(int parentId, int canvasDrawId) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
            System.out.println("SelectedFloorPositionInLocate " + floorPosition);
            Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    ProgressDialog.clearTouchLock(getContext(), getActivity());

                    if (response.body() != null && response.body().size() > 0) {

                        locateCountryResposeList = response.body();
                        if (desksCode != null) {
                            desksCode.clear();
                        }
                        if (carCode != null) {
                            carCode.clear();
                        }

                        if (meetingCode != null) {
                            meetingCode.clear();
                        }

                        if (locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().size() > 0) {
                            desksCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks();
                            //System.out.println("NowDeskCodeAvaliable");
                        } else if (locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size() > 0) {
                            carCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList();
                            //System.out.println("NoeCarCodeAvaliable");
                        } else if (locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().size() > 0) {
                            meetingCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList();
                            //System.out.println("NoeMeetingCodeAvaliable");
                        }


                        int totalDeskSize = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().size();
                        //System.out.println("TotalSize" + totalDeskSize);


                        //ProgressDialog.dismisProgressBar(getContext(), dialog);
                        //getAvaliableDeskDetails(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks(),0);

                        //if (canvasDrawId == 1) {

                        //List<Point> pointList = new ArrayList<>();

                        List<Point> pointList;

                        //support zone canvas
                        if (getSupportZoneLayoutForCanvas != null && getSupportZoneLayoutForCanvas.size() > 0) {


                            for (int i = 0; i < getSupportZoneLayoutForCanvas.size(); i++) {

                                pointList = new ArrayList<>();

                                for (int j = 0; j < getSupportZoneLayoutForCanvas.get(i).getSupportZoneCoordinates().size(); j++) {

                                    int x = getSupportZoneLayoutForCanvas.get(i).getSupportZoneCoordinates().get(j).get(0);
                                    int y = getSupportZoneLayoutForCanvas.get(i).getSupportZoneCoordinates().get(j).get(1);

                                    Point point = new Point(x + 40, y + 20);
                                    pointList.add(point);
                                }

                                addDottedLine(pointList);
                            }
                        }

                        //Final API canvas
                        List<List<Integer>> coordinateList = locateCountryResposeList.get(floorPosition).getCoordinates();

                        if (coordinateList != null && coordinateList.size() > 0) {

                            pointList = new ArrayList<>();

                            for (int i = 0; i < coordinateList.size(); i++) {
                                System.out.println("CoordinateData" + i + "position" + "size " + coordinateList.get(i).size());

                                Point point = new Point(coordinateList.get(i).get(0) + 40, coordinateList.get(i).get(1) + 20);
                                pointList.add(point);

                            }

                            addDottedLine(pointList);

                        }

                        //pointList.clear();

                        /*if (pointList.size() > 0) {
                            MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

                            binding.secondLayout.addView(myCanvasDraw);

                        }*/


                   /* } else {
                        getFloorCoordinates(locateCountryResposeList.get(floorPosition).getCoordinates());
                    }*/


                        List<String> valueList = new ArrayList<>();
                        if (locateCountryResposeList.get(floorPosition).getItems() != null) {

                            int itemTotalSize = locateCountryResposeList.get(floorPosition).getItems().size();

                            for (String key : locateCountryResposeList.get(floorPosition).getItems().keySet()) {

                                valueList = locateCountryResposeList.get(floorPosition).getItems().get(key);

                                addView(valueList, key, floorPosition, itemTotalSize);

                            }

                        } else {
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_LONG).show();
                        }

                    }

                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void getLayoutCode(List<LocateCountryRespose.LocationItemLayout.Desks> desks) {
    }


    private void getFloorCoordinates(List<List<Integer>> coordinateList) {


        //((MainActivity) getActivity()).getFloorCoordinatesInMain(coordinateList, binding.secondLayout);

        /*System.out.println("CoordinateSize" + coordinateList.size());
        //List<Point> pointList=new ArrayList<>();
        for (int i = 0; i < coordinateList.size(); i++) {

            System.out.println("CoordinateData" + i + "position" + "size " + coordinateList.get(i).size());

            Point point = new Point(coordinateList.get(i).get(0) + 40, coordinateList.get(i).get(1) + 20);
            pointList.add(point);


        }
        System.out.println("PointListSize" + pointList.size());

        //binding.secondLayout.postInvalidate();
        //binding.secondLayout.invalidate();

        if (pointList.size() > 0) {
            MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

            *//*if(canvasss==1){
                myCanvasDraw.setDrawMethod();
            }*//*


            //myCanvasDraw.postInvalidate();
            //myCanvasDraw.invalidate();
            //binding.secondLayout.postInvalidate();
            //binding.secondLayout.invalidate();
            //myCanvasDraw.setInvalidate();
            binding.secondLayout.addView(myCanvasDraw);

            //binding.secondLayout.onFinishTemporaryDetach();
            System.out.println("AlreadyHaveCanvashObject");

        }*/

    }

    @Override
    public void onResume() {
        super.onResume();

        //System.out.println("OnResumeCalled");
    }

    private void addDottedLine(List<Point> pointList) {


        /* View dottView = getLayoutInflater().inflate(R.layout.layout_dotted_line, null, false);
         ImageView ivDesk = dottView.findViewById(R.id.dottedImage);
         RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
 
         relativeLayout.leftMargin =300;
         relativeLayout.topMargin = 500;
         ivDesk.setLayoutParams(relativeLayout);*/

        //binding.firstLayout.addView(dottView);

        MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

        //binding.firstLayout.removeAllViews();

        binding.firstLayout.addView(myCanvasDraw);

        //pointList.clear();


    }


    @SuppressLint("ResourceType")
    private void addView(List<String> valueList, String key, int floorPosition, int itemTotalSize) {

        //System.out.println("ItemTotalSize" + itemTotalSize);
        //System.out.println("ReceivedKeyInAddView" + key);
        //String startDate="2022-07-26 18:30:00";
        //String endDate="2022-07-26 23:30:00";

        String startDate = binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + ":00";
        String endDate = binding.locateCalendearView.getText().toString() + " " + binding.locateEndTime.getText().toString() + ":00";


        //System.out.println("AddViewDataPrintedHere" + binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + " " + binding.locateEndTime.getText().toString());
        //System.out.println("AddViewStart" + startDate);
        //System.out.println("AddViewEnd" + endDate);

        //Desk Avaliablity Checking Split key to get id and code
        String[] result = key.split("_");
        int id = Integer.parseInt(result[0]);
        String code = result[1];

        deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView ivDesk = deskView.findViewById(R.id.ivDesk);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        //Desk
        DeskStatusModel deskStatusModel = null;
        //List<DeskStatusModel> deskStatusModelList = new ArrayList<>();
        //Inside Loop Added Status
        boolean deskAddedStatus = false;

        //Room
        MeetingStatusModel meetingStatusModel = null;
        //List<MeetingStatusModel> meetingStatusModelList = new ArrayList<>();
        //Inside Loop Added Status
        boolean meetingAddedStatus = false;

        //Desk Avaliablity Checking

        if (code.equals(AppConstants.DESK)) {

            if (teamDeskAvaliabilityList != null) {

                for (int i = 0; i < teamDeskAvaliabilityList.size(); i++) {

                    boolean wasAssigned = false;

                    if (id == teamDeskAvaliabilityList.get(i).getDeskId()) {

                        DeskAvaliabilityResponse.TeamDeskAvaliabilityList teamDeskAvaliability = teamDeskAvaliabilityList.get(i);

                        //GET TEAM ID
                        boolean getTeamId = false;
                        TeamsResponse teamsResponse = new TeamsResponse();
                        if (!getTeamId) {
                            if (teamsResponseList != null) {
                                for (int j = 0; j < teamsResponseList.size(); j++) {
                                    if (teamDeskAvaliability.getTeamId() == teamsResponseList.get(j).getId()) {

                                        teamsResponse = teamsResponseList.get(j);
                                        getTeamId = true;
                                    }
                                }
                            }
                        }//

                        //System.out.println("TotalAvaliableDeskId" + teamDeskAvaliability.getDeskId());
                        //System.out.println("CurrentDateAndTimeIn24HoursFormat" + Utils.getCurrentTimeIn24HourFormat());

                        //GetCurrentDate and Offset
                        String offSetAddedDate = Utils.addingHoursToCurrentDate(teamDeskAvaliability.getCurrentTimeZoneOffset());

                        //System.out.println("NewlyAddedDateWithTime" + offSetAddedDate);
                        //System.out.println("NewlyAddedDateAlone" + Utils.splitGetDate(offSetAddedDate));


                        for (int j = 0; j < teamDeskAvaliability.getAvailableTimeSlotsList().size(); j++) {

                            if (!wasAssigned) {

                                DeskAvaliabilityResponse.TeamDeskAvaliabilityList.AvailableTimeSlots availableTimeSlots = teamDeskAvaliability.getAvailableTimeSlotsList().get(j);

                                int dateComparsionResult = Utils.compareCurrentDateWithSelectedDate(startDate);
                                //int dateComparsionResult = Utils.compareTwoDates(startDate, offSetAddedDate);
                                int second = Utils.compareTwoDates(startDate, Utils.removeTandZInDate(availableTimeSlots.getFrom()));
                                int third = Utils.compareTwoDates(endDate, Utils.removeTandZInDate(availableTimeSlots.getTo()));

                                if (dateComparsionResult == 1) {
                                    System.out.println("BookingUnavaliable");
                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                    deskAddedStatus = true;
                                    deskStatusModel = new DeskStatusModel(key, id, code, 0);

                                } else if (teamDeskAvaliability.isPartiallyAvailable() == true && second == 2 && third == 1) {
                                    deskAddedStatus = true;
                                    if (teamDeskAvaliability.isBookedByUser() == true) {
                                        System.out.println("BookingbookedForMe");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 2);

                                    } else if (teamDeskAvaliability.isBookedByElse() == true) {
                                        System.out.println("BookingBookedOther");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_booked));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 3);

                                    } else if (teamsResponse.getDeskCount() != 0 && teamsResponse.getAutomaticApprovalStatus() == 2) {
                                        System.out.println("BookingAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 1);

                                    } else if (teamDeskAvaliability.getTeamId() == SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID)) {
                                        System.out.println("BookingAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 1);
                                    } else {

                                        if (teamsResponse != null && teamsResponse.getAutomaticApprovalStatus() == 3) {
                                            System.out.println("BookingUnavaliable");
                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                            deskStatusModel = new DeskStatusModel(key, id, code, 0);
                                        } else {
                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                                            System.out.println("BookingRequest");
                                            deskStatusModel = new DeskStatusModel(key, id, code, 4);
                                        }
                                    }
                                    wasAssigned = true;

                                } else {
                                    //System.out.println("BookingUnavaliable");
                                    //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                    //deskStatusModel = new DeskStatusModel(key, id, code, 0);
                                    if (teamDeskAvaliability.isBookedByUser() == true) {
                                        System.out.println("BookingbookedForMe");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 2);
                                        deskAddedStatus = true;
                                    } else if (teamDeskAvaliability.isBookedByElse() == true) {
                                        System.out.println("BookingBookedOther");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_booked));
                                        deskStatusModel = new DeskStatusModel(key, id, code, 3);
                                        deskAddedStatus = true;

                                    }
                                }


                            }

                            deskStatusModelList.add(deskStatusModel);
                            break;


                        }


                    }


                }


            } else {
                System.out.println("BookingUnavaliable");
                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                deskStatusModel = new DeskStatusModel(key, id, code, 0);
                deskStatusModelList.add(deskStatusModel);
            }

            //If desk is not go inside loop by default it will be unavaliable
            if (!deskAddedStatus) {
                deskStatusModel = new DeskStatusModel(key, id, code, 0);
                deskStatusModelList.add(deskStatusModel);
            }


        } else if (code.equals(AppConstants.CAR_PARKING)) {

            //CarPark Avalibility Checking

            //if(!carParkingCheckingStatus) {

            //M-Added
            if (carParkingStatusModelList != null && carParkingStatusModelList.size() > 0) {

                for (int i = 0; i < carParkingStatusModelList.size(); i++) {

                    if (id == carParkingStatusModelList.get(i).getId()) {

                        if (carParkingStatusModelList.get(i).getStatus() == 0) {
                            System.out.println("Unavaliable");
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.car_unavaliable));
                        } else if (carParkingStatusModelList.get(i).getStatus() == 1) {
                            System.out.println("Avaliable");
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.car_available));
                        } else if (carParkingStatusModelList.get(i).getStatus() == 2) {
                            System.out.println("BookedByMe");
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.car_bookedbyme));

                        } else if (carParkingStatusModelList.get(i).getStatus() == 3) {
                            System.out.println("BookingBookedOther");
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.locate_car_booked));
                        } else if (carParkingStatusModelList.get(i).getStatus() == 4) {
                            System.out.println("Request");
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.car_request));
                        }

                    }
                    // System.out.println("CarParkTesting" + carParkingStatusModelList.get(i).getKey() + " " + carParkingStatusModelList.get(i).getStatus());

                }

            }

            // carParkingCheckingStatus=true;
            //}
        }
        //MeetingChecking
        if (code.equals(AppConstants.MEETING)) {

            //ByDefault Set Meeting Image
            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_unavalible));


            int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);


            if (locationWithMR_response != null) {

                outerloop:
                for (int i = 0; i < locationWithMR_response.size(); i++) {

                    if (parentId == locationWithMR_response.get(i).getParentLocationId()) {

                        LocationWithMR_Response locationWithMR = locationWithMR_response.get(i);

                        if (locationWithMR != null) {

                            if (locationWithMR.getMatchesList() != null) {

                                for (int j = 0; j < locationWithMR.getMatchesList().size(); j++) {

                                    if (userAllowedMeetingResponseList != null) {

                                        for (int k = 0; k < userAllowedMeetingResponseList.size(); k++) {

                                            for (int l = 0; l < locationWithMR.getMatchesList().size(); l++) {

                                                for (int m = 0; m < userAllowedMeetingResponseList.size(); m++) {

                                                    if (locationWithMR.getMatchesList().get(l).getMatchesId() == userAllowedMeetingResponseList.get(m).getId()) {
                                                        locationWithMR.getMatchesList().get(l).setAllowedForBooking(true);
                                                    }

                                                }

                                            }

                                           /* if (locationWithMR.getMatchesList().get(j).getMatchesId() == userAllowedMeetingResponseList.get(k).getId()) {
                                                locationWithMR.getMatchesList().get(j).setAllowedForBooking(true);
                                            }
*/
                                            locationWithMR.getMatchesList().get(j).setCurrentTimeZoneOffset(locationWithMR_response.get(i).getTimeZoneOffsetMinutes());
                                            LocationWithMR_Response.Matches lMatches = locationWithMR.getMatchesList().get(j);


                                            if (id == lMatches.getMatchesId()) {
                                                //M-Added-19-10
                                                meetingAddedStatus = true;


                                                //GetCurrentDate and Add OffsetTime
                                                String offSetAddedDate = Utils.addingHoursToCurrentDate(lMatches.getCurrentTimeZoneOffset());
                                                //int dateComparsionResult = Utils.compareTwoDates(startDate, offSetAddedDate);

                                                int dateComparsionResult = Utils.doCompareDateAlone(startDate, offSetAddedDate);

                                                if (dateComparsionResult == 2) {
                                                    //28,27,26
                                                    System.out.println("MeetingBookingUnavaliable");
                                                    meetingStatusModel = new MeetingStatusModel(key, id, code, 0);
                                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_unavalible));

                                                } else {
                                                    //29,30
                                                    System.out.println("MeetingAvaliable");
                                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_avaliable));
                                                    meetingStatusModel = new MeetingStatusModel(key, id, code, 1);

                                                    if (lMatches.getBookingsList().size() > 0) {

                                                        for (int l = 0; l < lMatches.getBookingsList().size(); l++) {

                                                            LocationWithMR_Response.Matches.Bookings bookings = lMatches.getBookingsList().get(l);

                                                            String[] fromUTCDate = bookings.getFromUtc().split("T");
                                                            String fromUtcDateAlone = fromUTCDate[0];

                                                            String[] toUTCDate = bookings.getToUtc().split("T");
                                                            String toUtcDateAlone = toUTCDate[0];

                                                            String[] fromDate = bookings.getFrom().split("T");
                                                            String fromTimeAloneWithT = fromDate[1];
                                                            String[] fromTimeAlonez = fromTimeAloneWithT.split("Z");
                                                            String fromTimeAlone = fromTimeAlonez[0];

                                                            String[] toDate = bookings.getTo().split("T");
                                                            String toTimeAloneWithZ = toDate[1];
                                                            String[] toTimeAlonez = toTimeAloneWithZ.split("Z");
                                                            String toTimeAlone = toTimeAlonez[0];


                                                            String fromDateTime = fromUtcDateAlone + " " + fromTimeAlone;
                                                            String toDateTime = toUtcDateAlone + " " + toTimeAlone;


                                                            //fromDateTime less or equal
                                                            int dateCompar1 = Utils.compareTwoDates(fromDateTime, endDate);

                                                            //startDate less or equal
                                                            int dateCompare2 = Utils.compareTwoDates(startDate, toDateTime);

                                                            if ((dateCompar1 == 0 || dateCompar1 == 1) && (dateCompare2 == 0 || dateCompare2 == 1)) {

                                                                if (bookings.getBookedByUserId() == SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID)) {

                                                                    if (!lMatches.isAllowedForBooking()) {

                                                                        if (lMatches.getMatchType() == 2 && lMatches.getAutomaticApprovalStatus() == 0) {
                                                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_bookedbyme));
                                                                            meetingStatusModel = new MeetingStatusModel(key, id, code, 2);
                                                                            System.out.println("MeetingBookedForMe");
                                                                            meetingStatusModelList.add(meetingStatusModel);
                                                                            break outerloop;
                                                                        } else {
                                                                            meetingStatusModel = new MeetingStatusModel(key, id, code, 4);
                                                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_request));
                                                                            System.out.println("MeetingRequest");
                                                                            break outerloop;
                                                                        }

                                                                    } else {
                                                                        System.out.println("MeetingBookedForMe");
                                                                        meetingStatusModel = new MeetingStatusModel(key, id, code, 2);
                                                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_bookedbyme));
                                                                        meetingStatusModelList.add(meetingStatusModel);
                                                                        break outerloop;
                                                                    }

                                                                } else {
                                                                    meetingStatusModel = new MeetingStatusModel(key, id, code, 3);
                                                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_booked));
                                                                    System.out.println("MeetingBookedOther");
                                                                    meetingStatusModelList.add(meetingStatusModel);
                                                                    break outerloop;
                                                                }


                                                            } else if (lMatches.getAutomaticApprovalStatus() == 3 && !lMatches.isAllowedForBooking()) {
                                                                meetingStatusModel = new MeetingStatusModel(key, id, code, 0);
                                                                System.out.println("MeetingUnavaliable");
                                                                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_unavalible));
                                                                meetingStatusModelList.add(meetingStatusModel);
                                                                break outerloop;

                                                            } else if (lMatches.getAutomaticApprovalStatus() == 2 || lMatches.isAllowedForBooking()) {
                                                                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_avaliable));
                                                                System.out.println("Meetingavaliable");
                                                                meetingStatusModel = new MeetingStatusModel(key, id, code, 1);
                                                                meetingStatusModelList.add(meetingStatusModel);
                                                                break outerloop;

                                                            } else {
                                                                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_request));
                                                                System.out.println("MeetingRequest");
                                                                meetingStatusModel = new MeetingStatusModel(key, id, code, 4);
                                                                meetingStatusModelList.add(meetingStatusModel);
                                                                break outerloop;

                                                            }


                                                        }

                                                    } else if (lMatches.getAutomaticApprovalStatus() == 3 && !locationWithMR.getMatchesList().get(j).isAllowedForBooking()) {
                                                        System.out.println("MeetingBookingUnavaliable");
                                                        meetingStatusModel = new MeetingStatusModel(key, id, code, 0);
                                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_unavalible));
                                                        meetingStatusModelList.add(meetingStatusModel);
                                                        break outerloop;
                                                    } else if (lMatches.getAutomaticApprovalStatus() == 2 || locationWithMR.getMatchesList().get(j).isAllowedForBooking()) {
                                                        System.out.println("MeetingAvaliable");
                                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_avaliable));
                                                        meetingStatusModel = new MeetingStatusModel(key, id, code, 1);
                                                        meetingStatusModelList.add(meetingStatusModel);
                                                        break outerloop;
                                                    } else {
                                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_request));
                                                        meetingStatusModel = new MeetingStatusModel(key, id, code, 4);
                                                        meetingStatusModelList.add(meetingStatusModel);
                                                        System.out.println("MeetingRequest");
                                                        break outerloop;
                                                    }


                                                }

                                                meetingStatusModelList.add(meetingStatusModel);

                                            } else {

                                                //I Added Newly on 11-10-2022
                                                //MeetingRoomRequest
                                                    /*ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_request));
                                                    meetingStatusModel = new MeetingStatusModel(key, id, code, 4);
                                                    meetingStatusModelList.add(meetingStatusModel);
                                                    System.out.println("MeetingRequest");*/
                                            }

                                            //}
                                        }

                                    }

                                }

                            } else {
                                //MeetingRoomRequest
                                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_request));
                                meetingStatusModel = new MeetingStatusModel(key, id, code, 4);
                                meetingStatusModelList.add(meetingStatusModel);
                                System.out.println("MeetingRequest");
                                break outerloop;
                            }

                        }

                    }

                }

            }

            //M-Added-19-10
            //If meeting room is not go inside (id == lMatches.getMatchesId()) it will be activated
            // If meeting is not go inside loop by default it will be unavaliable
            if (!meetingAddedStatus) {
                meetingStatusModel = new MeetingStatusModel(key, id, code, 0);
                meetingStatusModelList.add(meetingStatusModel);
            }


            //Amenities Filter
            for (int i = 0; i < meetingAmenityStatusList.size(); i++) {

                System.out.println("AllAmenitiesDataHere " + meetingAmenityStatusList.get(i).getId());

                int idValue = meetingAmenityStatusList.get(i).getId();
                if (id == idValue) {
                    ivDesk.setVisibility(View.GONE);
                    System.out.println("VisibleGoneAmenitiesIdAndID " + meetingAmenityStatusList.get(i).getId() + " " + id + " " + amenitiesApplyStatus);
                } else {
                    ivDesk.setVisibility(View.VISIBLE);
                }
            }


        }

        //Head Facing
        for (int i = 0; i < valueList.size(); i++) {

            if (i == 2) {
                int rotateValue = Integer.parseInt(valueList.get(2));
                if (rotateValue == 1) {
                    ivDesk.setRotation(90);
                } else if (rotateValue == 2) {
                    ivDesk.setRotation(135);
                } else if (rotateValue == 3) {
                    ivDesk.setRotation(180);
                } else if (rotateValue == 4) {
                    ivDesk.setRotation(225);
                } else if (rotateValue == 5) {
                    ivDesk.setRotation(270);
                } else if (rotateValue == 6) {
                    ivDesk.setRotation(315);
                } else if (rotateValue == 7) {
                    ivDesk.setRotation(360);
                } else if (rotateValue == 8) {
                    ivDesk.setRotation(405);
                }
            }
            //System.out.println("PositionValue" + valueList.get(i));

        }


        //Set Image Based on Position
        int x = Integer.parseInt(valueList.get(0));
        int y = Integer.parseInt(valueList.get(1));

        relativeLayout.leftMargin = x;
        relativeLayout.topMargin = y;
        relativeLayout.width = 80;
        relativeLayout.height = 80;
        ivDesk.setLayoutParams(relativeLayout);


        //OnClickListener
        ivDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //removeZoomInLayout();

                //List<String> onClickValue = locateCountryResposeList.get(floorPosition).getItems().get(key);
                      /*for (int j = 0; j <onClickValue.size() ; j++) {
                         System.out.println("OnClickedKey"+key+"OnClickedValue"+onClickValue.get(j));
                     }*/

                //Split key to get id and code
                String[] result = key.split("_");
                int id = Integer.parseInt(result[0]);
                String code = result[1];
                String selctedCode = "";
                //int meetingRoomId = 0;
                String meetingRoomName = "";

                //Get code based on id
                if (code.equals(AppConstants.DESK)) {
                    //Get Code For Desk
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().size(); i++) {

                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDesksId()) {

                            selctedCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDeskCode();

                            System.out.println("ClickedCodeIs " + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDeskCode());

                        }

                    }

                } else if (code.equals(AppConstants.MEETING)) {
                    //Get Code For MEETING
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().size(); i++) {

                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomId()) {

                            meetingRoomName = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomCode();
                            meetingRoomId = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomId();
                        }

                    }


                } else if (code.equals(AppConstants.CAR_PARKING)) {
                    //Get Code For CAR_PARKING
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size(); i++) {
                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId()) {

                            selctedCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode();
                        }

                    }

                }

               /* if(deskStatusModelList.size()>0){
                    for (int i = 0; i <deskStatusModelList.size() ; i++) {

                        System.out.println("DESKNAMETOREQUESTTISERVER"+deskStatusModelList.get(i).getKey()+" "+deskStatusModelList.get(i).getStatus());

                    }
                }*/

                int requestTeamId = 0, requestTeamDeskId = 0;
                if (code.equals("3")) {
                    if (deskStatusModelList != null) {

                        if (deskStatusModelList.size() == 0) {
                            //Unavaliable
                            callDeskUnavaliable(selctedCode, key, id, code, requestTeamId, requestTeamDeskId);
                        } else {
                            for (int i = 0; i < deskStatusModelList.size(); i++) {
                                if (key.equals(deskStatusModelList.get(i).getKey())) {

                                    if (deskStatusModelList.get(i).getStatus() == 1) {
                                        //DeskDescription
                                        getDescriptionUsingDeskId(id);


                                        System.out.println("DeskDataPrintHere " + deskStatusModelList.get(i).getId() + " " + deskStatusModelList.get(i).getCode() + " " + deskStatusModelList.get(i).getKey() + " " + deskStatusModelList.get(i).getStatus());

                                        //Check autoaproved status

                                        int getAutoAproveStatus = 0;

                                        for (int j = 0; j < teamDeskAvaliabilityList.size(); j++) {

                                            if (deskStatusModelList.get(i).getId() == teamDeskAvaliabilityList.get(j).getDeskId()) {
                                                //Get TeamId
                                                int teamId = teamDeskAvaliabilityList.get(j).getTeamId();


                                                for (int k = 0; k < teamsResponseList.size(); k++) {

                                                    if (teamId == teamsResponseList.get(k).getId()) {

                                                        if (teamsResponseList.get(k).getAutomaticApprovalStatus() == 2) {

                                                            getAutoAproveStatus = teamsResponseList.get(k).getAutomaticApprovalStatus();
                                                            requestTeamId = teamDeskAvaliabilityList.get(j).getTeamId();
                                                            requestTeamDeskId = teamDeskAvaliabilityList.get(j).getTeamDeskId();
                                                        }


                                                    }

                                                }


                                            }

                                        }

                                        if (getAutoAproveStatus == 2) {
                                            editLastEndTime = "";
                                            System.out.println("AutoApprivedStausFlowGoesHere " + getAutoAproveStatus);

                                            //Team request id to get timezone
                                            DeskStatusModel deskStatusModel1 = deskStatusModelList.get(i);
                                            for (int j = 0; j < teamDeskAvaliabilityList.size(); j++) {

                                                if (deskStatusModel1.getId() == teamDeskAvaliabilityList.get(j).getDeskId()) {

                                                    requestTeamId = teamDeskAvaliabilityList.get(j).getTeamId();
                                                    break;
                                                }
                                            }

                                            callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 1, getAutoAproveStatus);
                                        } else if (getAutoAproveStatus == 0) {
                                            //Avaliable Booking
                                            //Desk Booking Bottom Sheet
                                            editLastEndTime = "";

                                            //Team request id to get timezone
                                            DeskStatusModel deskStatusModel1 = deskStatusModelList.get(i);
                                            for (int j = 0; j < teamDeskAvaliabilityList.size(); j++) {

                                                if (deskStatusModel1.getId() == teamDeskAvaliabilityList.get(j).getDeskId()) {
                                                    requestTeamId = teamDeskAvaliabilityList.get(j).getTeamId();
                                                    break;
                                                }
                                            }

                                            callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 1, 0);
                                        }


                                    } else if (deskStatusModelList.get(i).getStatus() == 4) {
                                        //Booking Request
                                        DeskStatusModel deskStatusModel1 = deskStatusModelList.get(i);
                                        for (int j = 0; j < teamDeskAvaliabilityList.size(); j++) {

                                            if (deskStatusModel1.getId() == teamDeskAvaliabilityList.get(j).getDeskId()) {

                                                requestTeamId = teamDeskAvaliabilityList.get(j).getTeamId();
                                                requestTeamDeskId = teamDeskAvaliabilityList.get(j).getTeamDeskId();
                                                break;
                                                //System.out.println("RequstedTeamId" + teamDeskAvaliabilityList.get(j).getTeamId());
                                                //System.out.println("RequestedTeamDeskId" + teamDeskAvaliabilityList.get(j).getTeamDeskId());

                                            }

                                        }

                                        //DeskDescription
                                        getDescriptionUsingDeskId(id);

                                        //Booking Request Bottom Sheet
                                        editLastEndTime = "";
                                        callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 4, 0);


                                    } else if (deskStatusModelList.get(i).getStatus() == 2) {
                                        //Booking Edit BottomSheet

                                        //Team request id to get timezone
                                        DeskStatusModel deskStatusModel1 = deskStatusModelList.get(i);
                                        for (int j = 0; j < teamDeskAvaliabilityList.size(); j++) {

                                            if (deskStatusModel1.getId() == teamDeskAvaliabilityList.get(j).getDeskId()) {
                                                requestTeamId = teamDeskAvaliabilityList.get(j).getTeamId();
                                                requestTeamDeskId = teamDeskAvaliabilityList.get(j).getTeamDeskId();
                                                break;
                                            }
                                        }

                                        getBookingListToEdit(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 4);

                                    } else if (deskStatusModelList.get(i).getStatus() == 0) {
                                        //Unavaliable
                                        callDeskUnavaliable(selctedCode, key, id, code, requestTeamId, requestTeamDeskId);
                                    } else if (deskStatusModelList.get(i).getStatus() == 3) {
                                        //Booked
                                        //Toast.makeText(getContext(), "Desk Is Already Booked", Toast.LENGTH_LONG).show();
                                        getDeskBookedData(selctedCode, key, id, code);


                                    }


                                }
                            }
                        }


                    }


                } else if (code.equals("5")) {

                    if (carParkingStatusModelList != null) {

                        for (int i = 0; i < carParkingStatusModelList.size(); i++) {

                            if (id == carParkingStatusModelList.get(i).getId()) {

                                if (carParkingStatusModelList.get(i).getStatus() == 0) {
                                    callDeskUnavaliable(selctedCode, key, id, code, requestTeamId, requestTeamDeskId);
                                } else if (carParkingStatusModelList.get(i).getStatus() == 1) {

                                    //CarDescription
                                    getCarDescriptionUsingCardId(id);

                                    //CarBooking
                                    editLastEndTime = "";
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 1, 0);
                                } else if (carParkingStatusModelList.get(i).getStatus() == 2) {
                                    //EditCarParking
                                    //getCarBookingEditList(id, code,selctedCode);
                                    getCarBookingEditList(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 1);

                                } else if (carParkingStatusModelList.get(i).getStatus() == 3) {
                                    //Booked
                                    //Toast.makeText(getContext(), "Park Is Already Booked", Toast.LENGTH_LONG).show();1
                                    
                                    getCarParkBookedData(selctedCode, key, id, code);
                                    
                                } else if (carParkingStatusModelList.get(i).getStatus() == 4) {

                                    getCarDescriptionUsingCardId(id);
                                    //CarRequestBooking
                                    editLastEndTime = "";
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, 4, 0);
                                } else if (carParkingStatusModelList.get(i).getStatus() == 0) {
                                    callDeskUnavaliable(selctedCode, key, id, code, requestTeamId, requestTeamDeskId);
                                }

                            }

                        }

                    }


                } else if (code.equals("4")) {

                    //System.out.println("SelectedMeetingRoomId" + meetingRoomId);


                    if (meetingStatusModelList != null) {

                        for (int i = 0; i < meetingStatusModelList.size(); i++) {

                            if (id == meetingStatusModelList.get(i).getId()) {

                                if (meetingStatusModelList.get(i).getStatus() == 0) {
                                    callDeskUnavaliable(meetingRoomName, key, id, code, requestTeamId, requestTeamDeskId);
                                } else if (meetingStatusModelList.get(i).getStatus() == 1) {
                                    editLastEndTime = "";
                                    boolean isReqduest = false;
                                    //New...
                                    getMeetingRoomDescription(meetingRoomId, meetingRoomName, isReqduest, "BOOK");

                                    //callMeetingRoomBookingBottomSheet(meetingRoomId, meetingRoomName, isReqduest, "BOOK");
                                } else if (meetingStatusModelList.get(i).getStatus() == 2) {
                                    boolean isReqduest = false;
                                    getMeetingBookingListToEdit(meetingRoomId, meetingRoomName, isReqduest);
                                } else if (meetingStatusModelList.get(i).getStatus() == 3) {
                                    //Toast.makeText(getContext(), "Meeting Room Is Already Booked", Toast.LENGTH_LONG).show();
                                    
                                    getMeetingBookedData(meetingRoomId, meetingRoomName);
                                    
                                } else if (meetingStatusModelList.get(i).getStatus() == 4) {
                                    editLastEndTime = "";
                                    boolean isReqduest = true;
                                    //New...
                                    getMeetingRoomDescription(meetingRoomId, meetingRoomName, isReqduest, "BOOK");

                                    //callMeetingRoomBookingBottomSheet(meetingRoomId, meetingRoomName, isReqduest, "BOOK");
                                }
                                //System.out.println("ClickedRoomIdStatus" + meetingStatusModelList.get(i).getStatus() + " " + meetingStatusModelList.get(i).getKey() + " " + meetingStatusModelList.get(i).getId());
                            }

                        }

                    }


                    //getMeetingRoomDescription(meetingRoomId);

                    //callMeetingRoomBookingBottomSheet(meetingRoomId,meetingRoomName);

                    //callMeetingRoomEditListAdapterBottomSheet();

                    /*if(id==3){
                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                        getMeetingBookingListToEdit(meetingRoomId);
                        //callMeetingRoomEditListAdapterBottomSheet();

                    }else if(id==4){
                        //MeetingRoomBooking
                        callMeetingRoomBookingBottomSheet(meetingRoomId,meetingRoomName);
                    }*/


                    //MeetingRoomEditListAdapter


                }

            }
        });

        //binding.firstLayout.setPadding(0,0,50,0);

        //TO Set Desk,Meeting and Car Icon Blink
        int floorIconBlick = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_ICON_BLINK);
        System.out.println("FloorIconBlinkStatus " + floorIconBlick);
        if (floorIconBlick > 0) {
            if (id == floorIconBlick) {
                ivDesk.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                //ivDesk.setPadding(9,7,7,9);
                ivDesk.setBackground(getResources().getDrawable(R.drawable.image_border));
                System.out.println("PaddingAddSuccesfully ");

                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.room_bookedbyme));
            } else {
                //Utils.toastShortMessage(getContext(),"The item you searched for is not available in the floorplan / layout view.");
            }
        }


        binding.firstLayout.addView(deskView);


    }

    private void getMeetingBookedData(int meetingRoomId, String meetingRoomName) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<BookedMeetingResponse>> call=apiService.getMeetingBookedTime(getCurrentDate(),getCurrentDate(),meetingRoomId);
        call.enqueue(new Callback<List<BookedMeetingResponse>>() {
            @Override
            public void onResponse(Call<List<BookedMeetingResponse>> call, Response<List<BookedMeetingResponse>> response) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                List<BookedMeetingResponse> bookedMeetingResponse=response.body();
                if(bookedMeetingResponse!=null){
                    callMeetingBookedBottomSheet(meetingRoomId,meetingRoomName,bookedMeetingResponse);
                }


            }

            @Override
            public void onFailure(Call<List<BookedMeetingResponse>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callMeetingBookedBottomSheet(int meetingRoomId, String meetingRoomName, List<BookedMeetingResponse> bookedMeetingResponse) {

        int selectedMeetingRoomId=0;
        BookedMeetingResponse getBookedResponse=null;

        //get meeting room id
        for (int i = 0; i <bookedMeetingResponse.size() ; i++) {
            if(meetingRoomId==bookedMeetingResponse.get(i).getMeetingRoomId()){
                //selectedMeetingRoomId=bookedMeetingResponse.get(i).getMeetingRoomId();
                getBookedResponse=bookedMeetingResponse.get(i);
            }
        }

        TextView bookedDeskTitle, bookedDeskDate, bookedLocation;
        RelativeLayout capacityRoomBlock, BookedAmenitiesBlock;
        ChipGroup bookedAmenitiesChipGroup;

        TextView tv_cap, tv_cap_size, meetingRoomDescription, tvBookedRoomStartTime, tvBookedRoomEndTime, bookedCancel;

        BottomSheetDialog locateBookedBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_booked_bottomsheet, null);
        locateBookedBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(800);

        bookedDeskTitle = locateBookedBottomSheet.findViewById(R.id.bookedDeskTitle);
        bookedDeskDate = locateBookedBottomSheet.findViewById(R.id.bookedDeskDate);
        bookedLocation = locateBookedBottomSheet.findViewById(R.id.bookedLocation);

        capacityRoomBlock = locateBookedBottomSheet.findViewById(R.id.capacityRoomBlock);
        BookedAmenitiesBlock = locateBookedBottomSheet.findViewById(R.id.BookedAmenitiesBlock);
        tv_cap_size = locateBookedBottomSheet.findViewById(R.id.tv_cap_size);
        meetingRoomDescription = locateBookedBottomSheet.findViewById(R.id.meetingRoomDescription);


        bookedAmenitiesChipGroup = locateBookedBottomSheet.findViewById(R.id.bookedAmenitiesChipGroup);

        tvBookedRoomStartTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomStartTime);
        tvBookedRoomEndTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomEndTime);
        bookedCancel = locateBookedBottomSheet.findViewById(R.id.bookedCancel);



        //System.out.println("FromAndToTimeOfBooking "+Utils.splitTime(getBookedResponse.getFrom())+" "+Utils.splitTime(bookedDeskResponseList.get(0).getTo()));

        bookedDeskTitle.setText(meetingRoomName);
        tvBookedRoomStartTime.setText(Utils.splitTime(getBookedResponse.getFrom()));
        tvBookedRoomEndTime.setText(Utils.splitTime(getBookedResponse.getTo()));

        String sDate = binding.locateCalendearView.getText().toString();
        String dateTime = Utils.dateWithDayString(sDate);
        bookedDeskDate.setText(dateTime);

        bookedLocation.setText(binding.searchLocate.getText());


        bookedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateBookedBottomSheet.dismiss();
            }
        });


        locateBookedBottomSheet.show();




    }

    private void getCarParkBookedData(String selctedCode, String key, int id, String code) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        Call<BookedCarResponse> call=apiService.getCarBookedTime(getCurrentDate(),getCurrentDate(),id,parentId);
        call.enqueue(new Callback<BookedCarResponse>() {
            @Override
            public void onResponse(Call<BookedCarResponse> call, Response<BookedCarResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                BookedCarResponse bookedCarResponse =response.body();
                
                if(bookedCarResponse!=null){
                    callCarBookedBottomSheet(selctedCode, key, id, code, bookedCarResponse);
                }
                
                
            }

            @Override
            public void onFailure(Call<BookedCarResponse> call, Throwable t) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                
            }
        });

    }

    private void callCarBookedBottomSheet(String selctedCode, String key, int id, String code, BookedCarResponse bookedCarResponse) {


        TextView bookedDeskTitle, bookedDeskDate, bookedLocation;
        RelativeLayout capacityRoomBlock, BookedAmenitiesBlock;
        ChipGroup bookedAmenitiesChipGroup;

        TextView tv_cap, tv_cap_size, meetingRoomDescription, tvBookedRoomStartTime, tvBookedRoomEndTime, bookedCancel;

        BottomSheetDialog locateBookedBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_booked_bottomsheet, null);
        locateBookedBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(800);

        bookedDeskTitle = locateBookedBottomSheet.findViewById(R.id.bookedDeskTitle);
        bookedDeskDate = locateBookedBottomSheet.findViewById(R.id.bookedDeskDate);
        bookedLocation = locateBookedBottomSheet.findViewById(R.id.bookedLocation);

        capacityRoomBlock = locateBookedBottomSheet.findViewById(R.id.capacityRoomBlock);
        BookedAmenitiesBlock = locateBookedBottomSheet.findViewById(R.id.BookedAmenitiesBlock);
        tv_cap_size = locateBookedBottomSheet.findViewById(R.id.tv_cap_size);
        meetingRoomDescription = locateBookedBottomSheet.findViewById(R.id.meetingRoomDescription);


        bookedAmenitiesChipGroup = locateBookedBottomSheet.findViewById(R.id.bookedAmenitiesChipGroup);

        tvBookedRoomStartTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomStartTime);
        tvBookedRoomEndTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomEndTime);
        bookedCancel = locateBookedBottomSheet.findViewById(R.id.bookedCancel);



        //System.out.println("FromAndToTimeOfBooking "+Utils.splitTime(bookedDeskResponseList.get(0).getFrom())+" "+Utils.splitTime(bookedDeskResponseList.get(0).getTo()));

        bookedDeskTitle.setText(selctedCode);
        tvBookedRoomStartTime.setText(Utils.splitTime(bookedCarResponse.getCarParkBookingsList().get(0).getFrom()));
        tvBookedRoomEndTime.setText(Utils.splitTime(bookedCarResponse.getCarParkBookingsList().get(0).getTo()));

        String sDate = binding.locateCalendearView.getText().toString();
        String dateTime = Utils.dateWithDayString(sDate);
        bookedDeskDate.setText(dateTime);

        bookedLocation.setText(binding.searchLocate.getText());


        bookedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateBookedBottomSheet.dismiss();
            }
        });


        locateBookedBottomSheet.show();


    }

    private void getDeskBookedData(String selctedCode, String key, int id, String code) {


        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //String bookingDate=binding.locateCalendearView.getText().toString() + "T" + binding.locateStartTime.getText().toString() + ":00Z";

        System.out.println("BookedDate " + getCurrentDate() + " " + id);

        Call<List<BookedDeskResponse>> call = apiService.getDeskBookedTime(getCurrentDate(), id);
        call.enqueue(new Callback<List<BookedDeskResponse>>() {
            @Override
            public void onResponse(Call<List<BookedDeskResponse>> call, Response<List<BookedDeskResponse>> response) {

                List<BookedDeskResponse> bookedDeskResponse = response.body();
                if (bookedDeskResponse != null && bookedDeskResponse.size()>0 ) {
                    callDeskBookedBottomSheet(selctedCode, key, id, code, bookedDeskResponse);
                }
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<BookedDeskResponse>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void callDeskBookedBottomSheet(String selctedCode, String key, int id, String code, List<BookedDeskResponse> bookedDeskResponseList) {

        TextView bookedDeskTitle, bookedDeskDate, bookedLocation;
        RelativeLayout capacityRoomBlock, BookedAmenitiesBlock;
        ChipGroup bookedAmenitiesChipGroup;

        TextView tv_cap, tv_cap_size, meetingRoomDescription, tvBookedRoomStartTime, tvBookedRoomEndTime, bookedCancel;

        BottomSheetDialog locateBookedBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_booked_bottomsheet, null);
        locateBookedBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(800);

        bookedDeskTitle = locateBookedBottomSheet.findViewById(R.id.bookedDeskTitle);
        bookedDeskDate = locateBookedBottomSheet.findViewById(R.id.bookedDeskDate);
        bookedLocation = locateBookedBottomSheet.findViewById(R.id.bookedLocation);

        capacityRoomBlock = locateBookedBottomSheet.findViewById(R.id.capacityRoomBlock);
        BookedAmenitiesBlock = locateBookedBottomSheet.findViewById(R.id.BookedAmenitiesBlock);
        tv_cap_size = locateBookedBottomSheet.findViewById(R.id.tv_cap_size);
        meetingRoomDescription = locateBookedBottomSheet.findViewById(R.id.meetingRoomDescription);


        bookedAmenitiesChipGroup = locateBookedBottomSheet.findViewById(R.id.bookedAmenitiesChipGroup);

        tvBookedRoomStartTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomStartTime);
        tvBookedRoomEndTime = locateBookedBottomSheet.findViewById(R.id.tvBookedRoomEndTime);
        bookedCancel = locateBookedBottomSheet.findViewById(R.id.bookedCancel);



        System.out.println("FromAndToTimeOfBooking "+Utils.splitTime(bookedDeskResponseList.get(0).getFrom())+" "+Utils.splitTime(bookedDeskResponseList.get(0).getTo()));

        bookedDeskTitle.setText(selctedCode);
        tvBookedRoomStartTime.setText(Utils.splitTime(bookedDeskResponseList.get(0).getFrom()));
        tvBookedRoomEndTime.setText(Utils.splitTime(bookedDeskResponseList.get(0).getTo()));

        String sDate = binding.locateCalendearView.getText().toString();
        String dateTime = Utils.dateWithDayString(sDate);
        bookedDeskDate.setText(dateTime);

        bookedLocation.setText(binding.searchLocate.getText());


        bookedCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateBookedBottomSheet.dismiss();
            }
        });


        locateBookedBottomSheet.show();

    }


    private void getMeetingBookingListToEdit(int meetingRoomId, String meetingRoomName, boolean isReqduest) {


        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);

        //String startDate=binding.locateCalendearView.getText().toString()+"T00:00:00.000Z";
        //String endDate=binding.locateCalendearView.getText().toString()+"T00:00:00.000Z";
        //String startDate = binding.locateCalendearView.getText().toString();
        //String endDate = binding.locateCalendearView.getText().toString();

        if (Utils.isNetworkAvailable(getActivity())) {

            String startDate = binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + ":00Z";
            String endDate = binding.locateCalendearView.getText().toString() + " " + binding.locateEndTime.getText().toString() + ":00Z";

            int[] roomid = new int[1];
            roomid[0] = meetingRoomId;
            String roomId = Arrays.toString(roomid);

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<MeetingListToEditResponse>> call = apiService.getMeetingListToEditInLocate(startDate, endDate, roomId);
            call.enqueue(new Callback<List<MeetingListToEditResponse>>() {
                @Override
                public void onResponse(Call<List<MeetingListToEditResponse>> call, Response<List<MeetingListToEditResponse>> response) {

                    //if (response.body()!=null && response.body().size()>0){

                    List<MeetingListToEditResponse> meetingListToEditResponseList = response.body();

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    callMeetingRoomEditListAdapterBottomSheet(meetingListToEditResponseList, meetingRoomName, isReqduest);

                    //}

                }

                @Override
                public void onFailure(Call<List<MeetingListToEditResponse>> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void callMeetingRoomEditListAdapterBottomSheet(List<MeetingListToEditResponse> meetingListToEditResponseList, String meetingRoomName, boolean isReqduest) {
        RecyclerView rvMeeingEditList;
        TextView editClose, editDate, addNew, tvActiveBookings;
        LinearLayoutManager linearLayoutManager;

        locateMeetEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateMeetEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvMeeingEditList = locateMeetEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = locateMeetEditBottomSheet.findViewById(R.id.editClose);
        addNew = locateMeetEditBottomSheet.findViewById(R.id.editBookingContinue);
        editDate = locateMeetEditBottomSheet.findViewById(R.id.editDate);
        tvActiveBookings = locateMeetEditBottomSheet.findViewById(R.id.tvActiveBookings);

        tvActiveBookings.setText("Active bookings");

        addNew.setText(global.getAddNew());
        editClose.setText(global.getCancel());

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //New...
                if (meetingListToEditResponseList.size() > 0) {
                    int c = meetingListToEditResponseList.size();
                    editLastEndTime = Utils.splitTime(meetingListToEditResponseList.get(c - 1).getTo());
                }
                //New...
                getMeetingRoomDescription(meetingRoomId, meetingRoomName, isReqduest, "EDIT");

                //callMeetingRoomBookingBottomSheet(meetingRoomId, meetingRoomName, isReqduest, "EDIT");
            }
        });

        //New...
        String sDate = binding.locateCalendearView.getText().toString();
        if (!(sDate.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(sDate);
            if (dateTime.equalsIgnoreCase("")) {
                editDate.setText(binding.locateCalendearView.getText().toString());
            } else {
                editDate.setText(dateTime);
            }
        } else {
            editDate.setText(binding.locateCalendearView.getText().toString());
        }

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMeeingEditList.setLayoutManager(linearLayoutManager);
        rvMeeingEditList.setHasFixedSize(true);
        for (int i = 0; i < meetingListToEditResponseList.size(); i++) {



            /*if(meetingListToEditResponseList.get(i).getStatus().getTimeStatus().equals("PAST")||
                    meetingListToEditResponseList.get(i).getStatus().getTimeStatus().equals("ONGOING")){
                meetingListToEditResponseList.remove(meetingListToEditResponseList.get(i));
            }*/

            if (meetingListToEditResponseList.get(i).getStatus().getTimeStatus().equals("PAST")) {
                meetingListToEditResponseList.remove(meetingListToEditResponseList.get(i));
            }

        }

        MeetingListToEditAdapter meetingListToEditAdapter = new MeetingListToEditAdapter(getContext(), meetingListToEditResponseList, this);
        rvMeeingEditList.setAdapter(meetingListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMeetEditBottomSheet.dismiss();
            }
        });

        //getMeetingListToEdit

        //New...
        if (meetingListToEditResponseList.size() > 0) {

            int c = meetingListToEditResponseList.size();
            editLastEndTime = Utils.splitTime(meetingListToEditResponseList.get(c - 1).getTo());

        } else {
            editLastEndTime = "";
        }

        locateMeetEditBottomSheet.show();
    }

    private void callDeskUnavaliable(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId) {

        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);

        TextView unAvalibaleDeskName, tvUnavaliableBack, unAvaliableLocate, tvDescriptionUnAvaliable, unAvailableDesc;

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_unavalible_bottomsheet, null);
        locateCheckInBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);


        unAvalibaleDeskName = locateCheckInBottomSheet.findViewById(R.id.unAvalibaleDeskName);
        tvUnavaliableBack = locateCheckInBottomSheet.findViewById(R.id.tvUnavaliableBack);
        unAvaliableLocate = locateCheckInBottomSheet.findViewById(R.id.unAvaliableLocate);
        tvDescriptionUnAvaliable = locateCheckInBottomSheet.findViewById(R.id.tvDescriptionUnAvaliable);
        unAvailableDesc = locateCheckInBottomSheet.findViewById(R.id.un_available_desc);

        unAvalibaleDeskName.setText(selctedCode);
        if (binding.searchLocate.getText().toString() != null) {
            //System.out.println("UnavaliableSearchHere"+binding.searchLocate.getText().toString());
            unAvaliableLocate.setText(binding.searchLocate.getText().toString());
        }
        if (code.equalsIgnoreCase("5")) {
            unAvailableDesc.setText("This car par is unavailable");
        }
        if (deskDescriotion != null) {
            tvDescriptionUnAvaliable.setText(deskDescriotion);
        }

        tvUnavaliableBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCheckInBottomSheet.dismiss();
            }
        });

        locateCheckInBottomSheet.show();


    }

    private void getMeetingRoomDescription(int meetingRoomId, String meetingRoomName, boolean isReqduest, String action) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MeetingRoomDescriptionResponse> call = apiService.getMeetingRoomDescription(meetingRoomId);
            call.enqueue(new Callback<MeetingRoomDescriptionResponse>() {
                @Override
                public void onResponse(Call<MeetingRoomDescriptionResponse> call, Response<MeetingRoomDescriptionResponse> response) {
                    MeetingRoomDescriptionResponse meetingRoomDescriptionResponse = response.body();
                    if (meetingRoomDescriptionResponse.getDescription() != null) {
                        meetingRoomDescription = meetingRoomDescriptionResponse.getDescription();
                    }

                    //New...
                    callMeetingRoomBookingBottomSheet(meetingRoomId, meetingRoomName, isReqduest, action);

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<MeetingRoomDescriptionResponse> call, Throwable t) {
                    //New...
                    callMeetingRoomBookingBottomSheet(meetingRoomId, meetingRoomName, isReqduest, action);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }

    private void getCarDescriptionUsingCardId(int id) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CarParkingDescriptionResponse> call = apiService.getCarParkingDescription(id);
            call.enqueue(new Callback<CarParkingDescriptionResponse>() {
                @Override
                public void onResponse(Call<CarParkingDescriptionResponse> call, Response<CarParkingDescriptionResponse> response) {

                    CarParkingDescriptionResponse carParkingDescriptionResponse = response.body();
                    if (carParkingDescriptionResponse != null) {
                        carParkDescription = carParkingDescriptionResponse.getDescription();
                    }

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<CarParkingDescriptionResponse> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }


    private void getDescriptionUsingDeskId(int id) {
        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<DeskDescriptionResponse> call = apiService.getDiskDescription(id);
            call.enqueue(new Callback<DeskDescriptionResponse>() {
                @Override
                public void onResponse(Call<DeskDescriptionResponse> call, Response<DeskDescriptionResponse> response) {
                    DeskDescriptionResponse deskDescriptionResponse = response.body();
                    if (deskDescriptionResponse != null) {
                        deskDescriotion = deskDescriptionResponse.getDeskDescription();
                    }


                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<DeskDescriptionResponse> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void callMeetingRoomBookingBottomSheet(int meetingRoomId, String meetingRoomName,
                                                   boolean isRequest, String action) {


        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);

        System.out.println("MeetingIsRequetStatus " + isRequest);

        int capacity = 0;

        //Show Amenities in Meeting Booking
        //Amenities Block

        List<String> amenitiesList = new ArrayList<>();
        for (int i = 0; i < userAllowedMeetingResponseList.size(); i++) {

            if (meetingRoomId == userAllowedMeetingResponseList.get(i).getId()) {

                capacity = userAllowedMeetingResponseList.get(i).getNoOfPeople();

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


        TextView editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription, roomTitle, showtvRoomStartTime, tv_cap, tv_cap_size;
        EditText etParticipants, externalAttendees, etSubject, etComments;
        RecyclerView rvParticipant;
        LinearLayoutManager linearLayoutManager;
        RelativeLayout startTimeLayout, endTimeLayout, rl_repeat_block_room,
                amenitiesBlock, rl_teams_layout_room, selectMeetingRoomLayout;
        //New...
        LinearLayout subCmtLay, child_layout;
        TextView roomDate;
        ChipGroup chipGroup, externalAttendeesChipGroup;

        //Language
        TextView tvRoomStart, tvRoomEnd, tv_teams_room, tv_repeat_room, showTvRoomEndTime;

        CheckBox teams_check_box_room;
        ImageView tv_user_status_room;


       /* BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_booking,
                new RelativeLayout(getContext()))));*/





        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_room_booking, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RelativeLayout layout = bottomSheetDialog.findViewById(R.id.design_bottom_sheet_room);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);






        startRoomTime = bottomSheetDialog.findViewById(R.id.tvRoomStartTime);
        endTRoomime = bottomSheetDialog.findViewById(R.id.tvRoomEndTime);
        showtvRoomStartTime = bottomSheetDialog.findViewById(R.id.showtvRoomStartTime);
        showTvRoomEndTime = bottomSheetDialog.findViewById(R.id.showTvRoomEndTime);
        amenitiesBlock = bottomSheetDialog.findViewById(R.id.amenitiesBlock);

        //Teams
        rl_teams_layout_room = bottomSheetDialog.findViewById(R.id.rl_teams_layout_room);
        teams_check_box_room = bottomSheetDialog.findViewById(R.id.teams_check_box_room);

        selectMeetingRoomLayout = bottomSheetDialog.findViewById(R.id.selectMeetingRoomLayout);
        selectMeetingRoomLayout.setVisibility(View.GONE);

        chipGroup = bottomSheetDialog.findViewById(R.id.meetingAmenitiesChipGroup);

        externalAttendees = bottomSheetDialog.findViewById(R.id.externalAttendees);
        externalAttendeesChipGroup = bottomSheetDialog.findViewById(R.id.externalAttendeesChipGroup);

        tv_cap = bottomSheetDialog.findViewById(R.id.tv_cap);
        tv_cap_size = bottomSheetDialog.findViewById(R.id.tv_cap_size);

        tv_cap_size.setText("" + capacity);


        //Set All Amenities Here
        for (int i = 0; i < amenitiesList.size(); i++) {

            System.out.println("RoomAmenitiesList " + amenitiesList.get(i));
            Chip chip = new Chip(getContext());
            chip.setText(amenitiesList.get(i));

            chip.setTextAppearance(R.style.chipText);
            chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
            chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));
            //chip.setTextSize(10);
            //chip.setPadding(1,1,1,1);
            chip.setCheckable(false);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }

        List<String> externalAttendeesEmail = new ArrayList<>();


        etParticipants = bottomSheetDialog.findViewById(R.id.etParticipants);
        etSubject = bottomSheetDialog.findViewById(R.id.etSubject);

        rl_repeat_block_room = bottomSheetDialog.findViewById(R.id.rl_repeat_block_room);
        repeat_room = bottomSheetDialog.findViewById(R.id.repeat_room);

        etComments = bottomSheetDialog.findViewById(R.id.etCommentsMeeting);
        editRoomBookingContinue = bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack = bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        tvMeetingRoomDescription = bottomSheetDialog.findViewById(R.id.meetingRoomDescription);
        roomTitle = bottomSheetDialog.findViewById(R.id.roomTitle);
        rvParticipant = bottomSheetDialog.findViewById(R.id.rvParticipant);
        participantChipGroup = bottomSheetDialog.findViewById(R.id.participantChipGroup);
        tv_user_status_room = bottomSheetDialog.findViewById(R.id.tv_user_status_room);

        startTimeLayout = bottomSheetDialog.findViewById(R.id.startTimeLayout);
        endTimeLayout = bottomSheetDialog.findViewById(R.id.endTimeLayout);
        subCmtLay = bottomSheetDialog.findViewById(R.id.subCmtLay);
        child_layout = bottomSheetDialog.findViewById(R.id.child_layout);
        roomDate = bottomSheetDialog.findViewById(R.id.roomDate);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);

        //Language
        tvRoomStart = bottomSheetDialog.findViewById(R.id.tvRoomStart);
        tvRoomEnd = bottomSheetDialog.findViewById(R.id.tvRoomEnd);
        tv_teams_room = bottomSheetDialog.findViewById(R.id.tv_teams_room);
        tv_repeat_room = bottomSheetDialog.findViewById(R.id.tv_repeat_room);
        TextView meetingAvaliable = bottomSheetDialog.findViewById(R.id.meetingAvaliable);

        tvRoomStart.setText(appKeysPage.getStart());
        tvRoomEnd.setText(appKeysPage.getEnd());
        tv_teams_room.setText(appKeysPage.getTeams());
        tv_repeat_room.setText(appKeysPage.getRepeat());
        editRoomBookingContinue.setText(appKeysPage.getContinue());
        editRoomBookingBack.setText(appKeysPage.getCancel());
        //tvMeetingRoomDescription.setText(appKeysPage.getDescription());
        //etComments.setHint(appKeysPage.getComments());
        etSubject.setHint(meetingRoomsLanguage.getSubject());

        if (isRequest) {
            meetingAvaliable.setText("Request");
            tv_user_status_room.setImageDrawable(getResources().getDrawable(R.drawable.byrequest));
        } else {
            meetingAvaliable.setText("Avaliable");
        }


        //Repeat
        //Enable for current date
        //Disable for future date
        boolean currentDateStatus = Utils.checkIsCurrentDate(binding.locateCalendearView.getText().toString());
        if (currentDateStatus) {
            rl_repeat_block_room.setVisibility(View.VISIBLE);
        } else {
            rl_repeat_block_room.setVisibility(View.GONE);
        }


        roomTitle.setText(meetingRoomName);

        externalAttendees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                etParticipants.setText("");
                Chip chip = new Chip(getContext());
                if (s.toString().contains(" ")) {
                    //validate email address
                    if (Utils.isValidEmail(s.toString().trim())) {

                        chip.setText(s.toString());
                        chip.setCheckable(false);
                        chip.setClickable(false);
                        chip.setCloseIconVisible(true);

                        chip.setTextAppearance(R.style.chipText);
                        chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                        chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

                        externalAttendeesChipGroup.setVisibility(View.VISIBLE);
                        externalAttendeesChipGroup.addView(chip);

                        //Add In List
                        externalAttendeesEmail.add(s.toString());

                        externalAttendees.clearFocus();
                        externalAttendees.setText("");
                    } else {
                        Utils.toastMessage(getContext(), "Please enter a valid email address.");
                    }


                    //After Email is added in chip
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


        teams_check_box_room.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isMeetingCheckBoxCheckedStatus = true;
                } else {
                    isMeetingCheckBoxCheckedStatus = false;
                }

            }
        });

        rl_repeat_block_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatBottomSheetDialog("4");
            }
        });


        if (teamsCheckBoxStatus) {
            rl_teams_layout_room.setVisibility(View.VISIBLE);
        } else {
            rl_teams_layout_room.setVisibility(View.GONE);
        }


        //New...
        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
        String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
        if (CountryName == null && buildingName == null && floorName == null) {

            roomDate.setText(buildingName + "," + floorName);

        } else {
            roomDate.setText(buildingName + "," + floorName);

        }

        if (meetingRoomDescription != null) {
            tvMeetingRoomDescription.setText(tvMeetingRoomDescription.getText().toString() + meetingRoomDescription);
        } else {
            tvMeetingRoomDescription.setText(appKeysPage.getDescription());
            //tvMeetingRoomDescription.setText("Description:");
        }


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

        etSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etParticipants.setText("");
                rvParticipant.setVisibility(View.GONE);
            }
        });


        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startRoomTime, "Start", binding.locateCalendearView.getText().toString());
            }
        });

        //New...
        //showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " " + binding.locateStartTime.getText().toString());
        //startRoomTime.setText(binding.locateStartTime.getText().toString());
        //endTRoomime.setText(binding.locateEndTime.getText().toString());
        startRoomTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + Utils.showBottomSheetDateTimeAMPM(startRoomTime.getText().toString()));

                boolean b = Utils.checkIsCurrentDate(binding.locateCalendearView.getText().toString());

                String defaultToWorkHours = startRoomTime.getText().toString();
                String[] strDefaultWork = defaultToWorkHours.split(":");
                int workHour = Integer.parseInt(strDefaultWork[0]);
                int workMinute = Integer.parseInt(strDefaultWork[1]);

                if (b) {

                    //endTRoomime.setText(Utils.setNearestThirtyMinToMeeting(startRoomTime.getText().toString()));

                    if (workHour == 23) {

                        if (workMinute >= 30) {

                            endTRoomime.setText("23:59");
                            showTvRoomEndTime.setText("23:59");

                        } else {

                            //New Logic For set Near 30 min and end time +30 from start Time...
                            endTRoomime.setText(Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30));
                            showTvRoomEndTime.setText(Utils.showBottomSheetDateTimeAMPM(
                                    Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30)));

                            //endTRoomime.setText(Utils.setStartNearestFiveMinToMeeting(startRoomTime.getText().toString()));
                        }

                    } else {

                        //New Logic For set Near 30 min and end time +30 from start Time...
                        endTRoomime.setText(Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30));
                        showTvRoomEndTime.setText(Utils.showBottomSheetDateTimeAMPM(
                                Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30)));

                        //endTRoomime.setText(Utils.setStartNearestFiveMinToMeeting(startRoomTime.getText().toString()));

                    }


                } else {

                    if (workHour == 23) {

                        if (workMinute >= 30) {

                            endTRoomime.setText("23:59");

                        } else {

                            //For Not Current Day...
                            endTRoomime.setText(Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30));

                        }

                    } else {
                        //For Not Current Day...
                        endTRoomime.setText(Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30));
                    }

                }

            }
        });


        endTRoomime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                showTvRoomEndTime.setText(Utils.showBottomSheetDateTime(Utils.showBottomSheetDateTimeAMPM(endTRoomime.getText().toString())));


            }
        });

        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTRoomime, "End", binding.locateCalendearView.getText().toString());
            }
        });

        editRoomBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page == 1) {
                    bottomSheetDialog.dismiss();
                } else {
                    editRoomBookingContinue.setText(appKeysPage.getContinue());
                    editRoomBookingBack.setText(appKeysPage.getCancel());
                    subCmtLay.setVisibility(View.GONE);
                    child_layout.setVisibility(View.VISIBLE);
                    roomDate.setText(buildingName + "," + floorName);
                    page = 1;
                }

            }
        });

        editRoomBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page == 1) {
                    editRoomBookingContinue.setText(appKeysPage.getContinue());
                    boolean status = true;
                    if (startRoomTime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }
                    if (endTRoomime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    if (Utils.compareStartEndTime(startRoomTime.getText().toString(), endTRoomime.getText().toString())) {

                    } else {
                        Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    subCmtLay.setVisibility(View.VISIBLE);
                    child_layout.setVisibility(View.GONE);

                    String startTime = showtvRoomStartTime.getText().toString();
                    System.out.println("StartRoomFormat " + startTime);
                    String[] startTimeToSplit = startTime.split("•");
                    String startWithAMOrPM = startTimeToSplit[1];


                    roomDate.setText(Utils.showCalendarDate(binding.locateCalendearView.getText().toString()) + " • " +
                            startWithAMOrPM + " to " + showTvRoomEndTime.getText().toString());

                    page = 2;
                    editRoomBookingContinue.setText(appKeysPage.getBook());
                    editRoomBookingBack.setText(appKeysPage.getBack());

                } else {
                    boolean status = true;
                    editRoomBookingContinue.setText(appKeysPage.getBook());
                    if (startRoomTime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }
                    if (endTRoomime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    if (Utils.compareStartEndTime(startRoomTime.getText().toString(), endTRoomime.getText().toString())) {


                    } else {
                        Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    String subject = etSubject.getText().toString();
                    String comment = etComments.getText().toString();
                    if (subject.isEmpty() || subject.equals("") || subject == null) {
                        Toast.makeText(getContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                   /* if (comment.isEmpty() || comment.equals("") || comment == null) {
                        Toast.makeText(getContext(), "Please Enter Comment", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }*/

                    if (status) {
                        page = 1;
                        bottomSheetDialog.dismiss();

                        if (!repeatActvieStatus) {

                            /*for (int i = 0; i <externalAttendeesEmail.size() ; i++) {
                                System.out.println("FinalExternalAttendessList "+externalAttendeesEmail.get(i));
                            }*/

                            doMeetingRoomBooking(meetingRoomId,
                                    startRoomTime.getText().toString(),
                                    endTRoomime.getText().toString(),
                                    subject, comment, isRequest, externalAttendeesEmail);
                        } else {
                            doRepeatMeetingRoomBookingForWeek();
                        }

                    } else {
                        Toast.makeText(getContext(), "Please Enter All Details", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        //New...

        if (editLastEndTime.equals("") && action.equalsIgnoreCase("BOOK")) {

            boolean b = Utils.checkIsCurrentDate(binding.locateCalendearView.getText().toString());

            if (b) {

                startRoomTime.setText(Utils.setNearestThirtyMinToMeeting(getCurrentTime()));
                showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + Utils.showBottomSheetDateTimeAMPM(startRoomTime.getText().toString()));

            } else {
                if (profileData != null) {

                    startRoomTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));

                    //Old logic...
                    //endTRoomime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
                    endTRoomime.setText(Utils.selectedTimeWithExtraMins(startRoomTime.getText().toString(), 30));

                    showTvRoomEndTime.setText(Utils.showBottomSheetDateTimeAMPM(endTRoomime.getText().toString()));
                    showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + Utils.showBottomSheetDateTimeAMPM(startRoomTime.getText().toString()));
                }
            }


        } else {

            startRoomTime.setText(editLastEndTime);
            if (!(startRoomTime.getText().toString().isEmpty())) {
                showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + Utils.showBottomSheetDateTimeAMPM(startRoomTime.getText().toString()));

                //Add 4Hour...
                String[] time = startRoomTime.getText().toString().split(":");
                int t1 = Integer.parseInt(time[0]);

                if (t1 >= 20) {
                    endTRoomime.setText("23:59");
                } else {
                    endTRoomime.setText(Utils.addHoursToSelectedTime(
                            startRoomTime.getText().toString(), 4));
                }
            }

        }

        //New...
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                page = 1;
            }
        });

        bottomSheetDialog.show();

    }

    private void sendEnteredPartipantLetterToServer(String participantLetter, RecyclerView rvParticipant) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ParticipantDetsilResponse>> call = apiService.getParticipantDetails(participantLetter, 1);
            call.enqueue(new Callback<List<ParticipantDetsilResponse>>() {
                @Override
                public void onResponse(Call<List<ParticipantDetsilResponse>> call, Response<List<ParticipantDetsilResponse>> response) {

                    List<ParticipantDetsilResponse> participantDetsilResponseList = response.body();

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    if (participantDetsilResponseList != null) {

                        showParticipantNameInRecyclerView(participantDetsilResponseList, rvParticipant);

                    }

                }

                @Override
                public void onFailure(Call<List<ParticipantDetsilResponse>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void showParticipantNameInRecyclerView(List<ParticipantDetsilResponse> participantDetsilResponseList, RecyclerView rvParticipant) {


        //Get Saved UserId
        int userId = SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID);

        List<ParticipantDetsilResponse> result = new ArrayList<>();
        result = (List<ParticipantDetsilResponse>) participantDetsilResponseList.stream().filter(val -> val.getId() != userId).collect(Collectors.toList());


        rvParticipant.setVisibility(View.VISIBLE);
        ParticipantNameShowAdapter participantNameShowAdapter = new ParticipantNameShowAdapter(getContext(), result, this, rvParticipant);
        rvParticipant.setAdapter(participantNameShowAdapter);
    }

    @Override
    public void onParticipantSelect(ParticipantDetsilResponse participantDetsilResponse, RecyclerView recyclerView) {

        this.participantDetsilResponse = participantDetsilResponse;

        Chip chip = new Chip(getContext());


        //Should not add already added user
        if (chipList.size() > 0) {

            boolean alreadyHasId = chipList.stream().anyMatch(m -> m.getId() == participantDetsilResponse.getId());

            if (alreadyHasId) {
                recyclerView.setVisibility(View.GONE);
            } else {
                chipList.add(participantDetsilResponse);

                chip.setText(participantDetsilResponse.getFullName());
                chip.setCloseIconVisible(true);
                chip.setCheckable(false);
                chip.setClickable(false);

                chip.setTextAppearance(R.style.chipText);
                chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

                participantChipGroup.addView(chip);
                participantChipGroup.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }


        } else {
            chipList.add(participantDetsilResponse);

            chip.setText(participantDetsilResponse.getFullName());
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);

            chip.setTextAppearance(R.style.chipText);
            chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
            chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

            participantChipGroup.addView(chip);
            participantChipGroup.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chipList != null) {
                    for (int i = 0; i < chipList.size(); i++) {

                        if (chip.getText().toString().contains(chipList.get(i).getFullName())) {
                            chipList.remove(chipList.get(i));
                        }

                    }
                }

                //System.out.println("RemoveChipGroupName"+chip.getText().toString());

                participantChipGroup.removeView(chip);

            }
        });

    }

    private void doMeetingRoomBooking(int meetingRoomId, String startMeetRoomTime, String endMeetRoomTime, String subject, String comment, boolean isRequest, List<String> externalAttendeesEmail) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
            meetingRoomRequest.setMeetingRoomId(meetingRoomId);

            if (teamsCheckBoxStatus && isMeetingCheckBoxCheckedStatus) {
                meetingRoomRequest.setMsTeams(true);
            } else {
                meetingRoomRequest.setMsTeams(false);
            }

            meetingRoomRequest.setHandleRecurring(false);
            meetingRoomRequest.setOnlineMeeting(false);

            MeetingRoomRequest.Changeset m = meetingRoomRequest.new Changeset();
            m.setId(0);
            m.setDate(binding.locateCalendearView.getText().toString() + "T" + "00:00:00.000" + "Z");

            MeetingRoomRequest.Changeset.Changes changes = m.new Changes();
            changes.setFrom(getCurrentDate() + "" + "T" + startMeetRoomTime + ":" + "00" + "." + "000" + "Z");
            changes.setMyto(getCurrentDate() + "" + "T" + endMeetRoomTime + ":" + "00" + "." + "000" + "Z");
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

            //ExternalAttendess
            List<String> externalAttendeesListStr = new ArrayList<>();

            if (externalAttendeesEmail != null) {

                for (int i = 0; i < externalAttendeesEmail.size(); i++) {
                    externalAttendeesListStr.add(externalAttendeesEmail.get(i));
                }

            }


            changes.setAttendees(attendeesList);

            List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList = new ArrayList<>();
            changes.setExternalAttendees(externalAttendeesListStr);

            List<MeetingRoomRequest.DeleteIds> deleteIdsList = new ArrayList<>();
            meetingRoomRequest.setDeletedIds(deleteIdsList);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doMeetingRoomBook(meetingRoomRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    chipList.clear();

                    if (response.code() == 200) {
                        page = 1;
                    }

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }


    private void getCarBookingEditList(String selectedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId, int i) {


        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);

        if (Utils.isNetworkAvailable(getActivity())) {

            //String startDate="2022-07-25T00:00:00.000Z";
            //String endDate="2022-07-25T00:00:00.000Z";

            String startDate = binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + ":00.000Z";
            String endDate = binding.locateCalendearView.getText().toString() + " " + binding.locateEndTime.getText().toString() + ":00.000Z";

            //dialog = ProgressDialog.showProgressBar(getContext());
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<CarParkingForEditResponse> call = apiService.getCarParkingEditList(startDate, endDate, id);
            call.enqueue(new Callback<CarParkingForEditResponse>() {
                @Override
                public void onResponse(Call<CarParkingForEditResponse> call, Response<CarParkingForEditResponse> response) {

                    CarParkingForEditResponse carParkingForEditResponse = response.body();

                    if (carParkingForEditResponse != null) {
                        CallCarBookingEditList(carParkingForEditResponse, code, selectedCode, key, id, requestTeamId, requestTeamDeskId, i);
                    }
               /* for (int i = 0; i <carParkingForEditResponse.getCarParkBookings().size() ; i++) {
                    System.out.println("CarParkingEditListVeHicleNumber"+carParkingForEditResponse.getCarParkBookings().get(i).getVehicleRegNumber());

                }*/

                    // ProgressDialog.dismisProgressBar(getContext(),dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<CarParkingForEditResponse> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(getContext(),dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }


    private void CallCarBookingEditList(CarParkingForEditResponse carParkingForEditResponse, String code, String selectedCode, String key, int id, int requestTeamId, int requestTeamDeskId, int i) {


        RecyclerView rvCarEditList;
        TextView editClose, editDate, bookingName, carAddNew;
        LinearLayoutManager linearLayoutManager;

        locateCarEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCarEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvCarEditList = locateCarEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = locateCarEditBottomSheet.findViewById(R.id.editClose);
        editDate = locateCarEditBottomSheet.findViewById(R.id.editDate);
        carAddNew = locateCarEditBottomSheet.findViewById(R.id.editBookingContinue);
        bookingName = locateCarEditBottomSheet.findViewById(R.id.bookingName);

        //editDate.setVisibility(View.GONE);
        //editBookingContinue.setVisibility(View.GONE);

        editClose.setText("Close");

        //New...
        String sDate = binding.locateCalendearView.getText().toString();
        if (!(sDate.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(sDate);
            if (dateTime.equalsIgnoreCase("")) {
                editDate.setText(binding.locateCalendearView.getText().toString());
            } else {
                editDate.setText(dateTime);
            }
        } else {
            editDate.setText(binding.locateCalendearView.getText().toString());
        }

        carAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDeskBookingnBottomSheet(selectedCode, key, id, code, requestTeamId, requestTeamDeskId, i, 0);
            }
        });

        bookingName.setText("Booking Parking");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCarEditList.setLayoutManager(linearLayoutManager);
        rvCarEditList.setHasFixedSize(true);

        CarListToEditAdapter carListToEditAdapter = new CarListToEditAdapter(getContext(), carParkingForEditResponse.getCarParkBookings(), this, code, selectedCode);
        rvCarEditList.setAdapter(carListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCarEditBottomSheet.dismiss();
            }
        });

        //New...
        if (carParkingForEditResponse.getCarParkBookings() != null && carParkingForEditResponse.getCarParkBookings().size() > 0) {

            int c = carParkingForEditResponse.getCarParkBookings().size();
            editLastEndTime = Utils.splitTime(carParkingForEditResponse.getCarParkBookings().get(c - 1).getMyto());

        } else {
            editLastEndTime = "";
        }

        locateCarEditBottomSheet.show();
    }

    //DeskBookingEditList
    private void callBottomSheetToEdit(BookingForEditResponse bookingForEditResponse, String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId, int i) {


        RecyclerView rvEditList;
        TextView editClose, editDate, bookingName, addNew, tvActiveBookings;
        LinearLayoutManager linearLayoutManager;

        locateEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvEditList = locateEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = locateEditBottomSheet.findViewById(R.id.editClose);
        addNew = locateEditBottomSheet.findViewById(R.id.editBookingContinue);
        editDate = locateEditBottomSheet.findViewById(R.id.editDate);
        bookingName = locateEditBottomSheet.findViewById(R.id.bookingName);
        tvActiveBookings = locateEditBottomSheet.findViewById(R.id.tvActiveBookings);

        addNew.setText(global.getAddNew());
        editClose.setText(global.getBack());
        tvActiveBookings.setText("Active bookings");
        //bookingName.setText();


        //editClose.setVisibility(View.INVISIBLE);
        //addNew.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvEditList.setLayoutManager(linearLayoutManager);
        rvEditList.setHasFixedSize(true);

        BookingListToEditAdapter bookingListToEditAdapter = new BookingListToEditAdapter(getContext(), bookingForEditResponse.getBookings(), this, code, bookingForEditResponse.getTeamDeskAvailabilities());
        rvEditList.setAdapter(bookingListToEditAdapter);

        //Show Here Current Date
        String sDate = binding.locateCalendearView.getText().toString();
        if (!(sDate.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(sDate);
            if (dateTime.equalsIgnoreCase("")) {
                editDate.setText(binding.locateCalendearView.getText().toString());
            } else {
                editDate.setText(dateTime);
            }
        } else {
            editDate.setText(binding.locateCalendearView.getText().toString());
        }
        //editDate.setText(binding.locateCalendearView.getText().toString());

        if (code.equals("3")) {
            bookingName.setText("Book a workspace");
        }

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeskBookingnBottomSheet(selctedCode, key, id, code, requestTeamId, requestTeamDeskId, i, 0);
            }
        });

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateEditBottomSheet.dismiss();
            }
        });


        //New...
        if (bookingForEditResponse.getBookings() != null && bookingForEditResponse.getBookings().size() > 0) {

            int c = bookingForEditResponse.getBookings().size();
            editLastEndTime = Utils.splitTime(bookingForEditResponse.getBookings().get(c - 1).getMyto());

        } else {
            editLastEndTime = "";
        }

        locateEditBottomSheet.show();

    }

    private void getBookingListToEdit(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId, int i) {


        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);


        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID),
                    SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID),
                    binding.locateCalendearView.getText().toString(),
                    binding.locateCalendearView.getText().toString());

            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {

                    BookingForEditResponse bookingForEditResponse = response.body();

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    if (bookingForEditResponse != null) {
                        callBottomSheetToEdit(bookingForEditResponse, selctedCode, key, id, code, requestTeamId, requestTeamDeskId, i);
                    }

                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(getContext(),dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks, int id) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
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
            System.out.println("parent Booking cje" + parentId);
            //Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
            Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, toDate, fromTime, toTime);

            call.enqueue(new Callback<DeskAvaliabilityResponse>() {
                @Override
                public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                    DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();

                    //Call GetTeams API
                    getTeams();


                    if (deskAvaliabilityResponseList != null) {
                        teamDeskAvaliabilityList = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList();
                    }

                    //GetTeamDeskIdForBooking
                    if (id > 0) {
                        for (int i = 0; i < deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size(); i++) {

                            if (id == deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getDeskId()) {
                                teamDeskIdForBooking = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getTeamDeskId();
                                //System.out.println("TeamDeskIdForBooking " + teamDeskIdForBooking);
                            }


                        }
                    }


                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                /*List<String> deskCodeList = new ArrayList<>();
                for (int i = 0; i < desks.size(); i++) {
                    deskCodeList.add(desks.get(i).getDeskCode());
                }

                if (deskAvaliabilityResponseList.getLocationDesksList() != null) {
                    for (int i = 0; i < deskAvaliabilityResponseList.getLocationDesksList().size(); i++) {

                        if (deskCodeList.contains(deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode())) {
                            System.out.println("AvaliableDesks" + deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode());
                        }

                    }
                }*/


                    //ProgressDialog.dismisProgressBar(getContext(), dialog);


                }

                @Override
                public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }


    private void getLocateCountryList() {


        if (Utils.isNetworkAvailable(getContext())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    if (response.body() != null) {
                        List<LocateCountryRespose> locateCountryResposes = response.body();

                        CallFloorBottomSheet(locateCountryResposes);
                    } else {
                        Utils.toastMessage(getActivity(), "No Data");
                    }


                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    //Select Floor Plan BottomSheeet
    private void CallFloorBottomSheet(List<LocateCountryRespose> locateCountryResposes) {

        /* BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().
                inflate(R.layout.bottom_sheet_locate_floor_filter,
                        new RelativeLayout(getContext())));*/




       /* BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_unavalible_bottomsheet, null);
        locateCheckInBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);*/


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_locate_floor_filter, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.parentIdLocate);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);


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


        country.setText(appKeysPage.getGlobalLocation());
        //country.setText(locateCountryResposes.get(0).getName());
        rvCountry.setVisibility(View.INVISIBLE);
        statBlock.setVisibility(View.INVISIBLE);
        rvState.setVisibility(View.INVISIBLE);
        streetBlock.setVisibility(View.INVISIBLE);
        rvStreet.setVisibility(View.INVISIBLE);
        floorBlock.setVisibility(View.INVISIBLE);

        back = bottomSheetDialog.findViewById(R.id.bsBack);
        bsApply = bottomSheetDialog.findViewById(R.id.bsApply);

        //New...
        img_bsCountry = bottomSheetDialog.findViewById(R.id.img_bsCountry);
        img_bsStreet = bottomSheetDialog.findViewById(R.id.img_bsStreet);
        img_bsState = bottomSheetDialog.findViewById(R.id.img_bsState);

        back.setText(appKeysPage.getBack());
        bsApply.setText(appKeysPage.getApply());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (floorAdapter != null) {

                    if (floorAdapter.getSelectedPositionCheck() >= 0) {
                        floorSearchStatus = false;
                        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
                        boolean floorSelectedStatus = SessionHandler.getInstance().getBoolean(getContext(), AppConstants.FLOOR_SELECTED_STATUS);

                        //System.out.println("SelectedFloorPosition "+floorPosition+" "+floorSelectedStatus);

                        //if(floorSelectedStatus){
                        canvasss = 1;
                        //removes desk in layout
                        binding.firstLayout.removeAllViews();

                        //used to check default location
                        defaultLocationcheck = 1;

                        initLoadFloorDetails(canvasss);
                        bottomSheetDialog.dismiss();
                    } else {
                        Utils.toastMessage(getContext(), "Please Select Floor");
                    }

                } else {
                    Utils.toastMessage(getContext(), "Please Select Floor");
                }


            }
        });


        country.setText(appKeysPage.getGlobalLocation());
        rvCountry.setVisibility(View.VISIBLE);
        showCountryListInAdapter(locateCountryResposes);


        //Without click Global location have toload
        //Global Location
        statBlock.setVisibility(View.GONE);
        rvState.setVisibility(View.GONE);
        streetBlock.setVisibility(View.GONE);
        rvStreet.setVisibility(View.GONE);
        floorBlock.setVisibility(View.GONE);
        rvFloor.setVisibility(View.INVISIBLE);


        //Global Location
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //country.setText("Global Location");
                country.setText(appKeysPage.getGlobalLocation());
                rvCountry.setVisibility(View.VISIBLE);
                showCountryListInAdapter(locateCountryResposes);

                statBlock.setVisibility(View.GONE);
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.GONE);
                rvStreet.setVisibility(View.GONE);
                floorBlock.setVisibility(View.GONE);
                rvFloor.setVisibility(View.INVISIBLE);

                //getLocateCountryList();
                //New...
                countryColor();
            }
        });

        //City
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //state.setText("City");
                state.setText(appKeysPage.getCity());
                streetBlock.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.INVISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);
                callCountrysChildData(stateId);

                //New...
                cityColor();
                floorBlock.setVisibility(View.GONE);

            }
        });

        //Building
        street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //street.setText("Building");
                street.setText(appKeysPage.getBuilding());
                floorBlock.setVisibility(View.INVISIBLE);
                floor.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.VISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);

                //New...
                buildingcolor();
            }
        });

        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvFloor.setVisibility(View.VISIBLE);
                floorColor();
            }
        });


        bsGeneralSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!floorSearchStatus) {

                    if (showCountryAdapter != null) {

                        showCountryAdapter.getFilter().filter(s.toString());

                    } else {
                        Utils.toastMessage(getContext(), "Choose Any Location");
                    }
                } else {
                    if (floorAdapter != null) {

                        floorAdapter.getFilter().filter(s.toString());

                    } else {
                        Utils.toastMessage(getContext(), "Choose Any Floor");
                    }
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

        locateCountryResposes.sort(Comparator.comparing(LocateCountryRespose::getName, String::compareToIgnoreCase));

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "COUNTRY");
        rvCountry.setAdapter(showCountryAdapter);


    }

    //Interface To select
    @Override
    public void onSelect(LocateCountryRespose locateCountryRespose, String identifier) {


        switch (identifier) {
            case "COUNTRY":

                floorSearchStatus = false;

                state.setText("City");
                stateId = locateCountryRespose.getLocateCountryId();
                SessionHandler.getInstance().save(getContext(), AppConstants.COUNTRY_NAME, locateCountryRespose.getName());
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());


                //New...
                //Set Clicked Place To Show Textview
                bsLocationSearch.setText(locateCountryRespose.getName());

                cityColor();
                break;

            case "STATE":

                floorSearchStatus = false;

                state.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING, locateCountryRespose.getName());
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                street.setText("Building");

                SessionHandler.getInstance().saveInt(getContext(), AppConstants.SUB_PARENT_ID, locateCountryRespose.getLocateCountryId());
                getFloorDetails(locateCountryRespose.getLocateCountryId(), false);


                //System.out.println("SubParentIdAndItsPosition" + locateCountryRespose.getLocateCountryId() + " ");

                //New...
                //Set Clicked Place To Show Textview
                bsLocationSearch.setText(locateCountryRespose.getName());

                buildingcolor();
                break;

            case "FLOOR":

                floorSearchStatus = true;

                bsApply.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);

                floorBlock.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                floor.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR, locateCountryRespose.getName());
                SessionHandler.getInstance().remove(getContext(), AppConstants.FULLPATHLOCATION);
                rvStreet.setVisibility(View.GONE);
                rvFloor.setVisibility(View.VISIBLE);
                SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, locateCountryRespose.getLocateCountryId());

               /* if (locateCountryRespose.getSupportZoneLayoutItemsList() == null) {

                    if(getSupportZoneLayoutForCanvas!=null && getSupportZoneLayoutForCanvas.size()>0) {
                        getSupportZoneLayoutForCanvas.clear();
                    }
                }*/

                //clear it before get
                if (getSupportZoneLayoutForCanvas != null && getSupportZoneLayoutForCanvas.size() > 0) {
                    getSupportZoneLayoutForCanvas.clear();
                }
                if (locateCountryRespose.getSupportZoneLayoutItemsList() != null && locateCountryRespose.getSupportZoneLayoutItemsList().size() > 0) {
                    //get support zone to draw canvas
                    getSupportZoneLayoutForCanvas = locateCountryRespose.getSupportZoneLayoutItemsList();

                    //getOtherSubZoneLayoutItems(locateCountryRespose.getSupportZoneLayoutItemsList());
                }

                //Final
                getDeskRoomCarParkingDetails(locateCountryRespose.getLocateCountryId());

                //New...
                //Set Clicked Place To Show Textview
                bsLocationSearch.setText(locateCountryRespose.getName());

                floorColor();
                break;


        }

    }

    private void getOtherSubZoneLayoutItems(List<LocateCountryRespose.SupportZoneLayoutItems> supportZoneLayoutItemsList) {


        //pointList.clear();

        List<SubZonePoint> subZoneLayoutPointList = null;

        for (int i = 0; i < supportZoneLayoutItemsList.size(); i++) {

            subZoneLayoutPointList = new ArrayList<SubZonePoint>();
            List<SubZonePoint.SubPoint> subPointList = null;

            for (int j = 0; j < supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size(); j++) {


                SubZonePoint subZone = new SubZonePoint();

                int x = supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0);
                int y = supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1);

                System.out.println("Posotion " + supportZoneLayoutItemsList.get(i).getTitle() + " " + x + " " + y);

                SubZonePoint.SubPoint subPoint = subZone.new SubPoint(x, y);


                subPointList = new ArrayList<>();
                subPointList.add(subPoint);

                SubZonePoint subZonePoint = new SubZonePoint(
                        supportZoneLayoutItemsList.get(i).getSupportZoneId(),
                        supportZoneLayoutItemsList.get(i).getTitle(),
                        subPointList);

                subZoneLayoutPointList.add(subZonePoint);

            }

        }


        for (int i = 0; i < subZoneLayoutPointList.size(); i++) {

            System.out.println("FinalAllCoordinates " + subZoneLayoutPointList.get(i).getTitle());

            for (int j = 0; j < subZoneLayoutPointList.get(i).getSubPoint().size(); j++) {

            }


        }



       /* for (int i = 0; i <pointList.size() ; i++) {
            System.out.println("PointListDate "+pointList.get(i).getX()+" "+pointList.get(i).getY());
        }*/

        //ProgressDialog.dismisProgressBar(getContext(), dialog);
        /*  Point point=new Point(coordinateList.get(i).get(0),coordinateList.get(i).get(1));

         */

    }

    private void callCountrysChildData(int parentId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);

            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes = response.body();

                /*for (int i = 0; i < locateCountryResposes.size(); i++) {
                    System.out.println("CountrychildName" + locateCountryResposes.get(i).getName());
                }*/

                    rvCountry.setVisibility(View.GONE);
                    statBlock.setVisibility(View.VISIBLE);
                    rvState.setVisibility(View.VISIBLE);

                    showCountryChildListInAdapter(locateCountryResposes);

                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }
    }

    private void showCountryChildListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvState.setLayoutManager(linearLayoutManager);
        rvState.setHasFixedSize(true);

        locateCountryResposes.sort(Comparator.comparing(LocateCountryRespose::getName, String::compareToIgnoreCase));

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "STATE");
        rvState.setAdapter(showCountryAdapter);
    }

    private void getFloorDetails(int floorId, boolean findCoordinateStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(floorId);
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes = response.body();


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


                    }


                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                    // ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }

    private void showFloorListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvStreet.setLayoutManager(linearLayoutManager);
        rvStreet.setHasFixedSize(true);

        locateCountryResposes.sort(Comparator.comparing(LocateCountryRespose::getName, String::compareToIgnoreCase));


        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "FLOOR");
        rvStreet.setAdapter(showCountryAdapter);

    }

    //Final
    private void getDeskRoomCarParkingDetails(int parentId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposeList = response.body();

                    //System.out.println("InsideFloorDetails");

                /*for (int j = 0; j < locateCountryResposeList.size(); j++) {
                    System.out.println("InsideFloorName" + locateCountryResposeList.get(j).getName());
                }*/

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    showFloorImageAndNameInAdapter(locateCountryResposeList);


                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    //Final Adapter
    private void showFloorImageAndNameInAdapter(List<LocateCountryRespose> locateCountryResposeList) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFloor.setLayoutManager(linearLayoutManager);
        rvFloor.setHasFixedSize(true);

        floorAdapter = new FloorAdapter(getContext(), locateCountryResposeList, this, "FLOOR_NAME");
        rvFloor.setAdapter(floorAdapter);
    }


    //BookBottomSheet
    private void callDeskBookingnBottomSheet(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId, int statusCode, int getAutoApproveStatus) {

        //close when myteam bottom sheet open
        closeAndClearMyTeamList(locateMyTeamMemberStatusList);

        if (code.equals("3")) {
            //Get Time Zone Id
            teamDeskAvailabilitiesList.clear();
            getTimeZoneForBooking(requestTeamId);

        }

        RelativeLayout selectDeskBlock, bsRepeatBlock;
        TextView selectedLocation, tv_select_desk_room, statusText;
        ImageView ivOnline;

        TextView tvDescription, tvLocateDeskBookLocation;

        TextView tvLocateCheckInDateLang, tvLocateCheckInStartLang, tvLocateCheckoutLang, tv_repeatLang;


        System.out.println("BookingRequestDetail" + selctedCode + " " + key + " " + id + " " + code + " " + statusCode);

        /*BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_checkin_bottomsheet,
                new RelativeLayout(getContext())));*/


        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.dialog_locate_checkin_bottomsheet, null);
        locateCheckInBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout layout = locateCheckInBottomSheet.findViewById(R.id.bottomBookCoordinatorLayout);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);


        bookingDateBlock = locateCheckInBottomSheet.findViewById(R.id.bookingDateBlock);
        bookingStartBlock = locateCheckInBottomSheet.findViewById(R.id.bookingStartBlock);
        bookingEndBlock = locateCheckInBottomSheet.findViewById(R.id.bookingEndBlock);
        selectDeskBlock = locateCheckInBottomSheet.findViewById(R.id.selectDeskBlock);
        bsRepeatBlock = locateCheckInBottomSheet.findViewById(R.id.bsRepeatBlock);

        ivOnline = locateCheckInBottomSheet.findViewById(R.id.ivOnline);
        statusText = locateCheckInBottomSheet.findViewById(R.id.statusText);


        if (statusCode == 4) {
            ivOnline.setImageDrawable(getResources().getDrawable(R.drawable.byrequest));
            statusText.setText("Request");
            statusText.setText(appKeysPage.getRequest());
        }


        locateCheckInDate = locateCheckInBottomSheet.findViewById(R.id.locateCheckInDate);
        locateCheckInTime = locateCheckInBottomSheet.findViewById(R.id.locateCheckInTime);
        locateCheckoutTime = locateCheckInBottomSheet.findViewById(R.id.locateCheckoutTime);
        showlocateCheckInDate = locateCheckInBottomSheet.findViewById(R.id.showlocateCheckInDate);

        locateDeskName = locateCheckInBottomSheet.findViewById(R.id.locateDeskName);
        editBookingContinue = locateCheckInBottomSheet.findViewById(R.id.editBookingContinue);
        editBookingBack = locateCheckInBottomSheet.findViewById(R.id.editBookingBack);

        tv_desk_room_name = locateCheckInBottomSheet.findViewById(R.id.tv_desk_room_name);
        selectedLocation = locateCheckInBottomSheet.findViewById(R.id.selectedLocation);
        tv_select_desk_room = locateCheckInBottomSheet.findViewById(R.id.tv_select_desk_room);

        tvLocateDeskBookLocation = locateCheckInBottomSheet.findViewById(R.id.tvLocateDeskBookLocation);
        tvDescription = locateCheckInBottomSheet.findViewById(R.id.tvDescription);
        tvRepeat = locateCheckInBottomSheet.findViewById(R.id.tvRepeat);

        //new...
        locateCheckInDate.setText(binding.locateCalendearView.getText());
        showlocateCheckInDate.setText(Utils.showBottomSheetDate(binding.locateCalendearView.getText().toString()));
        //locateCheckInTime.setText(binding.locateStartTime.getText().toString());

        //Repeat
        //Enable repeat for current date
        //Disable repeat for future date

        boolean currentDateStatue = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());
        if (currentDateStatue) {
            bsRepeatBlock.setVisibility(View.VISIBLE);
        } else {
            bsRepeatBlock.setVisibility(View.GONE);
        }

        locateCheckInDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                boolean currentDateStatue = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());
                if (currentDateStatue) {
                    bsRepeatBlock.setVisibility(View.VISIBLE);
                } else {
                    bsRepeatBlock.setVisibility(View.GONE);
                }

            }
        });

        //New...

        //Normal desk booking
        if (editLastEndTime.equals("")) {

            boolean b = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());

            if (b) {
                //If current time add 2min and set
                locateCheckInTime.setText(currentTimeWithExtraMins(2));
                bsRepeatBlock.setVisibility(View.VISIBLE);
            } else {
                //If future date set users default works
                if (profileData != null) {
                    locateCheckInTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
                    bsRepeatBlock.setVisibility(View.GONE);
                }
            }

        } else {
            //If add new clicks need to add 4 hour in end time
            locateCheckInTime.setText(editLastEndTime);
            //Add 4Hour...
            String[] time = locateCheckInTime.getText().toString().split(":");
            int t1 = Integer.parseInt(time[0]);

            if (t1 >= 20) {
                locateCheckoutTime.setText("23:59");
            } else {
                locateCheckoutTime.setText(Utils.addHoursToSelectedTime(
                        locateCheckInTime.getText().toString(), 4));
            }
        }


        //12:42 current
        //11 default end works
        if (profileData != null) {
            String defaultToWorkHours = Utils.splitTime(profileData.getWorkHoursTo());
            String[] strDefaultWork = defaultToWorkHours.split(":");
            int workHour = Integer.parseInt(strDefaultWork[0]);
            int workMinute = Integer.parseInt(strDefaultWork[1]);


            String sCurrentTime = Utils.getCurrentTime();
            String[] current = sCurrentTime.split(":");
            int currentHour = Integer.parseInt(current[0]);
            int currentMinute = Integer.parseInt(current[1]);


            if (workHour <= currentHour) {
                //locateCheckoutTime.setText("23:59");
                if (workMinute <= currentMinute) {
                    locateCheckoutTime.setText("23:59");
                } else {
                    locateCheckoutTime.setText(defaultToWorkHours);
                }

            } else {
                locateCheckoutTime.setText(defaultToWorkHours);
            }


        }
        //Set Checkout time
        //locateCheckoutTime.setText(binding.locateEndTime.getText().toString());


        locateCheckInTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isOnTextChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (isOnTextChanged) {
                    isOnTextChanged = false;

                    if (editLastEndTime.equals("")) {

                        //boolean b = Utils.checkSelTimeWithCurrentTime(locateCheckInDate.getText().toString(),locateCheckInTime.getText().toString());
                        boolean b = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());

                        if (b) {

                            boolean isTimeCheck = Utils.checkSelTimeWithCurrentTime(b, locateCheckInTime.getText().toString());

                            if (isTimeCheck) {
                                //Leave it the selected time...
                            } else {
                                Toast.makeText(getActivity(), "Start time not less than current time", Toast.LENGTH_SHORT).show();
                                locateCheckInTime.setText(Utils.currentTimeWithExtraMins(2));
                            }

                        }/*else {
                        if (profileData!=null) {
                            locateCheckInTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
                        }
                    }*/

                    } else {

                        //Add 4Hour...
                        /*String[] time = locateCheckInTime.getText().toString().split(":");
                        int t1 = Integer.parseInt(time[0]);

                        if (t1>=20){
                            locateCheckoutTime.setText("23:59");
                        }else {
                            locateCheckoutTime.setText(Utils.addHoursToSelectedTime(
                                    locateCheckoutTime.getText().toString(),4));
                        }*/

                    }

                }

            }
        });


        //Only for Car Part to add 15min in end time
        //If  start time is higher than the user's default end time below logic goes
        if (code.equals("5")) {
            boolean b = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());

            if (profileData != null) {
                String defaultToWorkHours = Utils.splitTime(profileData.getWorkHoursTo());
                String[] strDefaultWork = defaultToWorkHours.split(":");
                int workHour = Integer.parseInt(strDefaultWork[0]);
                int workMinute = Integer.parseInt(strDefaultWork[1]);

                if (workHour == 23) {
                    if (workMinute >= 44) {
                        locateCheckoutTime.setText("23:59");
                    } else {

                        int addedFifteen = workMinute + 15;
                        locateCheckoutTime.setText("" + workHour + ":" + addedFifteen);
                    }

                } else {
                    String[] statTime = locateCheckInTime.getText().toString().split(":");
                    int startHour = Integer.parseInt(statTime[0]);
                    int startMin = Integer.parseInt(statTime[1]);

                    String StrEndTime = Utils.selectedTimeWithExtraMins(Utils.splitTime(profileData.getWorkHoursTo()), 15);

                    String[] endTimetime = StrEndTime.split(":");
                    int endHour = Integer.parseInt(endTimetime[0]);
                    int endMin = Integer.parseInt(endTimetime[1]);

                    if (startHour >= endHour) {

                        if (startMin >= endMin) {
                            locateCheckoutTime.setText("23:59");
                        } else {
                            locateCheckoutTime.setText(Utils.selectedTimeWithExtraMins(Utils.splitTime(profileData.getWorkHoursTo()), 15));
                        }


                    } else {
                        locateCheckoutTime.setText(Utils.selectedTimeWithExtraMins(Utils.splitTime(profileData.getWorkHoursTo()), 15));
                    }
                    //locateCheckoutTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
                }


            }


        }


        //Language
        tvLocateCheckInDateLang = locateCheckInBottomSheet.findViewById(R.id.tvLocateCheckInDate);
        tvLocateCheckInStartLang = locateCheckInBottomSheet.findViewById(R.id.tvLocateCheckInStart);
        tvLocateCheckoutLang = locateCheckInBottomSheet.findViewById(R.id.tvLocateCheckout);
        tv_repeatLang = locateCheckInBottomSheet.findViewById(R.id.tv_repeat);

        tv_repeatLang.setText(appKeysPage.getRepeat());
        tvLocateCheckInDateLang.setText(appKeysPage.getDate());
        tvLocateCheckInStartLang.setText(appKeysPage.getStart());
        tvLocateCheckoutLang.setText(appKeysPage.getEnd());
        tv_repeatLang.setText(appKeysPage.getRepeat());
        editBookingBack.setText(appKeysPage.getCancel());
        editBookingContinue.setText(appKeysPage.getBook());
        tvDescription.setText(appKeysPage.getDescription());


        bsRepeatBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //repeateBottomSheet();

                repeatBottomSheetDialog(code);

            }
        });

        if (deskDescriotion != null) {
            tvDescription.setText(tvDescription.getText().toString() + ":" + deskDescriotion);
        } else {
            //tvDescription.setText("Description:");
        }

        tv_select_desk_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<SelectCode> code = new ArrayList<>();


                if (desksCode != null && desksCode.size() != 0) {

                    for (int i = 0; i < desksCode.size(); i++) {
                        SelectCode allDeskCode = new SelectCode(desksCode.get(i).getDesksId(), desksCode.get(i).getDeskCode());
                        code.add(allDeskCode);
                    }
                } else if (carCode != null && carCode.size() != 0) {
                    for (int i = 0; i < carCode.size(); i++) {
                        SelectCode allCarCode = new SelectCode(carCode.get(i).getId(), carCode.get(i).getCode());
                        code.add(allCarCode);
                    }
                } else if (meetingCode != null && meetingCode.size() != 0) {

                    for (int i = 0; i < meetingCode.size(); i++) {
                        SelectCode allMeeingCode = new SelectCode(meetingCode.get(i).getMeetingRoomId(), meetingCode.get(i).getMeetingRoomCode());
                        code.add(allMeeingCode);
                    }

                }

                callBottomSheetToSelectDesk(code, AppConstants.DESK);

            }
        });

        //Car Parking Booking Widget
        bookingCommentBlock = locateCheckInBottomSheet.findViewById(R.id.bookingCommentBlock);
        bookingVechicleRegtBlock = locateCheckInBottomSheet.findViewById(R.id.bookingVechicleRegtBlock);
        etComment = locateCheckInBottomSheet.findViewById(R.id.etComment);
        etComment.setText("Comment");
        etVehicleReg = locateCheckInBottomSheet.findViewById(R.id.etVehicleReg);
        //etVehicleReg.setText("TN20CX2443");

        //Desk Avaliability Checking
        if (code.equals("3")) {
            bookingCommentBlock.setVisibility(View.GONE);
            bookingVechicleRegtBlock.setVisibility(View.GONE);
            getAvaliableDeskDetails(null, id);
        } else if (code.equals("5")) {
            bookingDateBlock.setVisibility(View.GONE);
            bookingCommentBlock.setVisibility(View.GONE);
            bookingVechicleRegtBlock.setVisibility(View.VISIBLE);
            String vehicleNumber = SessionHandler.getInstance().get(getContext(), AppConstants.VEHICLE_NUMBER);
            if (vehicleNumber != null) {
                etVehicleReg.setText(vehicleNumber);
            }
            getParkingSlotId(selctedCode);
        }

        bookingDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", locateCheckInDate, showlocateCheckInDate);
            }
        });

        bookingStartBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateCheckInTime, "Start", locateCheckInDate.getText().toString());
                //callBookingTimePickerBottomSheet();
            }
        });

        bookingEndBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateCheckoutTime, "End", locateCheckInDate.getText().toString());
            }
        });

        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
        String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);

        /*if (CountryName == null && buildingName == null && floorName == null) {
            tvLocateDeskBookLocation.setText(buildingName + "," + floorName);
            selectedLocation.setText(buildingName + "," + floorName);
        } else {
            tvLocateDeskBookLocation.setText(CountryName + "," + buildingName + "," + floorName);
            selectedLocation.setText(CountryName + "," + buildingName + "," + floorName);
        }*/
        tvLocateDeskBookLocation.setText(binding.searchLocate.getText());
        selectedLocation.setText(binding.searchLocate.getText());


        locateDeskName.setText(selctedCode);
        tv_desk_room_name.setText(selctedCode);

        editBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = true;

                if (locateCheckInTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }
                if (locateCheckoutTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (locateCheckInDate.getText().toString().equals("10 Aug")) {
                    Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                    status = false;
                }

                if (Utils.compareStartEndTime(locateCheckInTime.getText().toString(), locateCheckoutTime.getText().toString())) {


                } else {
                    Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (status) {

                    if (code.equals("3")) {

                        locateCheckInBottomSheet.dismiss();

                        if (requestTeamId > 0 && requestTeamDeskId > 0) {

                            if (!repeatActvieStatus) {
                                //Request Desk Booking
                                System.out.println("NormalRequestBooking");

                                requestDeskBooking(requestTeamId, requestTeamDeskId, getAutoApproveStatus);
                            } else if (repeatActvieStatus) {
                                System.out.println("RepeatRequestBooking");
                                doRepeatRequestDeskBooking(requestTeamId, requestTeamDeskId);
                            }

                        } else {
                            if (!repeatActvieStatus) {
                                //Desk Booking
                                deskBooking();
                                System.out.println("NormalDeskBookingActiviatedHere");
                            } else if (repeatActvieStatus) {
                                System.out.println("RepeatDeskBookingActiviatedHere");
                                doRepeatBookingForAWeek();
                            }
                        }

                    } else if (code.equals("5")) {

                        if (isVehicleReg) {

                            if (etVehicleReg.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "Enter Registration Number", Toast.LENGTH_SHORT).show();
                            } else {
                                locateCheckInBottomSheet.dismiss();
                                if (!repeatActvieStatus) {
                                    //CarBooking and CarRequestBooking
                                    System.out.println("NormalCarBookingActiviatedHere");
                                    carParkingRequest();
                                } else if (repeatActvieStatus) {
                                    System.out.println("RepeatCarBookingActiviatedHere");
                                    doRepeatCarBookingForAWeek();
                                }
                            }

                        } else {
                            locateCheckInBottomSheet.dismiss();
                            if (!repeatActvieStatus) {
                                //CarBooking and CarRequestBooking
                                System.out.println("NormalCarBookingActiviatedHere");
                                carParkingRequest();
                            } else if (repeatActvieStatus) {
                                System.out.println("RepeatCarBookingActiviatedHere");
                                doRepeatCarBookingForAWeek();
                            }
                        }


                    }

                }


            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCheckInBottomSheet.dismiss();
            }
        });


        locateCheckInDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                boolean b = Utils.checkIsCurrentDate(locateCheckInDate.getText().toString());

                if (b) {
                    locateCheckInTime.setText(currentTimeWithExtraMins(2));
                } else {
                    if (profileData != null) {
                        locateCheckInTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
                    }
                }

            }
        });

        locateCheckInBottomSheet.show();
    }


    private void repeatBottomSheetDialog(String code) {


        /*repeatBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        repeatBottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat_new,
                new RelativeLayout(getContext()))));*/



        repeatBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet_repeat_new, null);
        repeatBottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RelativeLayout layout = repeatBottomSheetDialog.findViewById(R.id.design_bottom_sheet_repeat);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);






        //Language
        TextView titleRepeat = repeatBottomSheetDialog.findViewById(R.id.titleRepeat);
        TextView tv_none = repeatBottomSheetDialog.findViewById(R.id.tv_none);
        TextView tv_daily = repeatBottomSheetDialog.findViewById(R.id.tv_daily);
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
        TextView tv_until_txt = repeatBottomSheetDialog.findViewById(R.id.tv_until_txt);
        TextView tv_interval_txt = repeatBottomSheetDialog.findViewById(R.id.tv_interval_txt);

        Date date = Utils.getCurrentWeekEndDate();

        if (repeatActvieStatus) {

            iv_none.setVisibility(View.GONE);
            if (code.equals("4")) {
                //repeat_room.setText("Daily");
                repeat_room.setText(appKeysPage.getDaily());
            } else {
                //tvRepeat.setText("Daily");
                tvRepeat.setText(appKeysPage.getDaily());
            }

            //Tick
            iv_daily.setVisibility(View.VISIBLE);

            if (repeatSelectedDate.isEmpty()) {
                //Show end of week
                tv_until.setText(Utils.getDateFormatToSetInRepeat(date) + "(end of Week)");
            } else {
                //Show selected date
                tv_until.setText(repeatSelectedDate);
            }

            cl_daily_layout.setVisibility(View.VISIBLE);
            tv_repeat.setVisibility(View.VISIBLE);

        }


        //None Block Clicked
        cl_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repeatActvieStatus = false;

                if (code.equals("4")) {
                    //repeat_room.setText("None");
                    repeat_room.setText(appKeysPage.getNone());
                } else {
                    //tvRepeat.setText("None");
                    tvRepeat.setText(appKeysPage.getNone());
                }


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

                if (code.equals("4")) {
                    //repeat_room.setText("Daily");
                    repeat_room.setText(appKeysPage.getDaily());
                } else {
                    //tvRepeat.setText("Daily");
                    tvRepeat.setText(appKeysPage.getDaily());
                }

                type = "daily";
                iv_none.setVisibility(View.GONE);
                iv_daily.setVisibility(View.VISIBLE);
                iv_weekly.setVisibility(View.GONE);
                iv_monthly.setVisibility(View.GONE);
                iv_yearly.setVisibility(View.GONE);
                //Get Current Week End Date
                //Date date = Utils.getCurrentWeekEndDate();
                //Set Figma format

                if (repeatSelectedDate.isEmpty()) {
                    tv_until.setText(Utils.getDateFormatToSetInRepeat(date) + "(end of Week)");
                } else {
                    tv_until.setText(repeatSelectedDate);
                }

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

    private void openIntervalsDialog(String type) {

        Toast.makeText(requireContext(), "openIntervalsDialog===" + type, Toast.LENGTH_LONG).show();
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

        cl_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        cl_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        if (type.equalsIgnoreCase("daily")) {

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


        } else if (type.equalsIgnoreCase("weekly")) {
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

        } else if (type.equalsIgnoreCase("monthly")) {
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

    private void openWeeks() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_week,
                new RelativeLayout(getContext()))));
        bottomSheetDialog.show();
    }

    private void openUntil(String code) {


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_until,
                new RelativeLayout(getContext()))));

        TextView titleUntil = bottomSheetDialog.findViewById(R.id.titleUntil);

        ConstraintLayout cl_forever = bottomSheetDialog.findViewById(R.id.cl_forever);
        ConstraintLayout cl_specific = bottomSheetDialog.findViewById(R.id.cl_specific);
        ImageView iv_forever = bottomSheetDialog.findViewById(R.id.iv_forever);
        ImageView iv_specific = bottomSheetDialog.findViewById(R.id.iv_specific);
        android.widget.CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);
        TextView tv_forever = bottomSheetDialog.findViewById(R.id.tv_forever);
        TextView tv_specific = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView editBookingContinue = bottomSheetDialog.findViewById(R.id.tv_specific);
        TextView repeatBookContinue = bottomSheetDialog.findViewById(R.id.editBookingContinue);

        titleUntil.setText(appKeysPage.getRepeatUntill());
        tv_specific.setText(appKeysPage.getSpecificDate());

        calendar_view.setVisibility(View.GONE);

        //calendar_view.setSelectedDateVerticalBar(R.drawable.circle_date_calender);


        //Get Current Week End Date
        Date date = Utils.getCurrentWeekEndDate();
        //Set Figma format
        tv_forever.setText(Utils.getDateFormatToSetInRepeat(date) + "(end of Week)");


        //System.out.println("LocateDateHere " + binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + " " + binding.locateEndTime.getText().toString());
        //2022-09-14 15:46 23:59

        //Get Date Difference Between current date and weekend date
        String selectedDate = binding.locateCalendearView.getText().toString();
        enableCurrentWeek = Utils.getDifferenceBetweenTwoDates(selectedDate);

        //System.out.println("enableCurrentWeek " + enableCurrentWeek);

        calendar_view.setMinDate(System.currentTimeMillis() - 1000);
        calendar_view.setMaxDate(System.currentTimeMillis() + enableCurrentWeek * 24 * 60 * 60 * 1000);

        //end of week book
        cl_forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_forever.setVisibility(View.VISIBLE);
                iv_specific.setVisibility(View.GONE);
                calendar_view.setVisibility(View.GONE);

                repeatActvieStatus = true;
                repeatSelectedDate = "";

                if (code.equals("3")) {

                    //DeskBookForWholeWeekFromToday
                    //doRepeatBookingForAWeek();
                } else if (code.equals("4")) {
                    //Meeting Room Booking For Whole Week From Today
                    //doRepeatMeetingRoomBookingForWeek();
                } else if (code.equals("5")) {
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
        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {


                repeatActvieStatus = true;

                int currentMonth = month + 1;

                repeatSelectedDate = dayOfMonth + "-" + currentMonth + "-" + year;

                //Coming WeekendDate
                LocalDate weekEndDate = LocalDate.of(year, currentMonth, dayOfMonth);

                //Selected Date
                String[] words = selectedDate.split("-");
                int selectedYear = Integer.parseInt(words[0]);
                int selectedMonth = Integer.parseInt(words[1]);
                int selectedDay = Integer.parseInt(words[2]);
                LocalDate currentSelectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);

                //Find Difference between 2 date
                Period difference = Period.between(currentSelectedDate, weekEndDate);
                enableCurrentWeek = difference.getDays();


                if (code.equals("3")) {
                    //BookForSelectedDaysInAWeek
                    //doRepeatBookingForAWeek();
                } else if (code.equals("4")) {

                } else if (code.equals("5")) {
                    //BookCarForSelectedDaysInAWeek
                    //doRepeatCarBookingForAWeek();
                }

                bottomSheetDialog.dismiss();
                repeatBottomSheetDialog.dismiss();


            }
        });


        bottomSheetDialog.show();
    }

    private void doRepeatMeetingRoomBookingForWeek() {

        String selectedDate = binding.locateCalendearView.getText().toString();
        List<String> dateList = Utils.getCurrentWeekDateList(selectedDate, enableCurrentWeek);


        MeetingRoomRecurrence meetingRoomRecurrence = new MeetingRoomRecurrence();
        meetingRoomRecurrence.setMeetingRoomId(meetingRoomId);
        meetingRoomRecurrence.setOnlineMeeting(false);

        if (teamsCheckBoxStatus && isMeetingCheckBoxCheckedStatus) {
            meetingRoomRecurrence.setMsTeams(true);
        } else {
            meetingRoomRecurrence.setMsTeams(false);
        }
        meetingRoomRecurrence.setHandleRecurring(false);

        List<MeetingRoomRecurrence.Changeset> changesetList = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {

            MeetingRoomRecurrence.Changeset changeset = meetingRoomRecurrence.new Changeset();
            changeset.setId(0);
            changeset.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            MeetingRoomRecurrence.Changeset.Changes changes = changeset.new Changes();
            changes.setComments("");
            changes.setFrom(getCurrentDate() + "" + "T" + startRoomTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setMyto(getCurrentDate() + "" + "T" + endTRoomime.getText().toString() + ":" + "00" + "." + "000" + "Z");
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

            List<MeetingRoomRecurrence.Changeset.Changes.ExternalAttendees> externalAttendees = new ArrayList<>();
            changes.setExternalAttendees(externalAttendees);

            //List<MeetingRoomRecurrence.Changeset.Changes.RecurrenceDetails> recurrenceDetailsList=new ArrayList<>();

            MeetingRoomRecurrence.Changeset.Changes.RecurrenceDetails recurrenceDetails = changes.new RecurrenceDetails();
            recurrenceDetails.setInterval(1);
            recurrenceDetails.setStartDate(dateList.get(0) + "T" + "00:00:00.000" + "Z");
            recurrenceDetails.setEndDate(dateList.get(dateList.size() - 1) + "T" + "00:00:00.000" + "Z");
            recurrenceDetails.setOnDay(24);
            recurrenceDetails.setSelectedMonth(8);
            recurrenceDetails.setPeriod(0);

            changes.setRecurrenceDetailsList(recurrenceDetails);

            changesetList.add(changeset);

        }

        meetingRoomRecurrence.setChangesets(changesetList);

        List<MeetingRoomRecurrence.DeleteIds> deleteIdsList = new ArrayList<>();
        meetingRoomRecurrence.setDeletedIds(deleteIdsList);

        System.out.println("MeetingRoomRecurrence " + meetingRoomRecurrence);

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doMeetingRoomRecurrence(meetingRoomRecurrence);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                    afterBookingDisableRepeat();
                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
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


    private void doRepeatRequestDeskBooking(int requestTeamId, int requestTeamDeskId) {

        String selectedDate = binding.locateCalendearView.getText().toString();
        List<String> dateList = Utils.getCurrentWeekDateList(selectedDate, enableCurrentWeek);

        LocateDeskBookingRequest locateDeskBookingRequest = new LocateDeskBookingRequest();
        locateDeskBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateDeskBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        List<LocateDeskBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();

        //Get Time Zone
        String timeZone = "India Standard Time";
        for (int j = 0; j < teamDeskAvailabilitiesList.size(); j++) {

            if (requestTeamDeskId == teamDeskAvailabilitiesList.get(j).getTeamDeskId()) {
                timeZone = teamDeskAvailabilitiesList.get(j).getTimeZones().get(0).getTimeZoneId();
                break;
            }

        }

        for (int i = 0; i < dateList.size(); i++) {

            LocateDeskBookingRequest.ChangeSets changeSets = locateDeskBookingRequest.new ChangeSets();

            changeSets.setChangeSetId(0);
            changeSets.setChangeSetDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateDeskBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(7);

            changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            //changes.setTimeZoneId("India Standard Time");


            changes.setTimeZoneId(timeZone);
            changes.setTeamDeskId(null);
            changes.setRequestedTeamId(requestTeamId);
            changes.setRequestedTeamDeskId(requestTeamDeskId);
            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            changeSetsList.add(changeSets);

        }

        locateDeskBookingRequest.setChangeSets(changeSetsList);

        List<LocateDeskBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        locateDeskBookingRequest.setDeletedIds(deleteIdsList);

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doRepeatRequestDeskBooking(locateDeskBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                    afterBookingDisableRepeat();

                    if (locateEditBottomSheet != null) {
                        locateEditBottomSheet.dismiss();
                    }

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }


    private void doRepeatCarBookingForAWeek() {
        String selectedDate = binding.locateCalendearView.getText().toString();
        List<String> dateList = Utils.getCurrentWeekDateList(selectedDate, enableCurrentWeek);

        LocateCarParkBookingRequest locateCarParkBookingRequest = new LocateCarParkBookingRequest();
        locateCarParkBookingRequest.setParkingSlotId(selectedCarParkingSlotId);

        List<LocateCarParkBookingRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {

            LocateCarParkBookingRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkBookingRequest.new CarParkingChangeSets();
            carParkingChangeSets.setId(0);
            carParkingChangeSets.setDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateCarParkBookingRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
            carParkingChanges.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            carParkingChanges.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            carParkingChanges.setComments(etComment.getText().toString());
            carParkingChanges.setBookedForUser(SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID));
            carParkingChanges.setVehicleRegNumber(etVehicleReg.getText().toString());

            carParkingChangeSets.setCarParkingChanges(carParkingChanges);

            carParkingChangeSetsList.add(carParkingChangeSets);


        }

        locateCarParkBookingRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);
        List<LocateCarParkBookingRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
        locateCarParkBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("RepeatModuleCarRequestData " + locateCarParkBookingRequest);

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doRepeatCarParkBooking(locateCarParkBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                    afterBookingDisableRepeat();
                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void doRepeatBookingForAWeek() {

        String selectedDate = binding.locateCalendearView.getText().toString();
        System.out.println("Seelcteddate " + selectedDate + " " + enableCurrentWeek);
        List<String> dateList = Utils.getCurrentWeekDateList(selectedDate, enableCurrentWeek);

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        List<LocateBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();

        //Get Time Zone
        String timeZone = "India Standard Time";
        for (int j = 0; j < teamDeskAvailabilitiesList.size(); j++) {

            if (teamDeskIdForBooking == teamDeskAvailabilitiesList.get(j).getTeamDeskId()) {
                timeZone = teamDeskAvailabilitiesList.get(j).getTimeZones().get(0).getTimeZoneId();
                break;

            }

        }


        for (int i = 0; i < dateList.size(); i++) {
            //System.out.println("AddedDateList "+dateList.get(i));

            LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();

            changeSets.setChangeSetId(0);
            //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
            changeSets.setChangeSetDate(dateList.get(i) + "T" + "00:00:00.000" + "Z");

            LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(2);

            changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            //changes.setTimeZoneId("India Standard Time");


            changes.setTimeZoneId(timeZone);
            changes.setTeamDeskId(teamDeskIdForBooking);
            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            changeSetsList.add(changeSets);


        }
        locateBookingRequest.setChangeSetsList(changeSetsList);
        List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("RepeatModuleRequestData " + locateBookingRequest);

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doRepeatBookingForWeek(locateBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                    afterBookingDisableRepeat();

                    if (locateEditBottomSheet != null) {
                        locateEditBottomSheet.dismiss();
                    }

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }

    private void callBottomSheetToSelectDesk(List<SelectCode> code, String desk) {

        RecyclerView rvDeskRecycler;
        DeskSelectListAdapter deskSelectListAdapter;
        TextView bsRepeatBackS;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        rvDeskRecycler = bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBackS = bottomSheetDialog.findViewById(R.id.bsDeskBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        deskSelectListAdapter = new DeskSelectListAdapter(getContext(), code, this, bottomSheetDialog, desk);
        rvDeskRecycler.setAdapter(deskSelectListAdapter);

        bsRepeatBackS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }

    private void requestDeskBooking(int requestTeamId, int requestTeamDeskId, int autoApprovedStatus) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            LocateDeskBookingRequest locateDeskBookingRequest = new LocateDeskBookingRequest();
            locateDeskBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
            locateDeskBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

            LocateDeskBookingRequest.ChangeSets changeSets = locateDeskBookingRequest.new ChangeSets();
            changeSets.setChangeSetId(0);

            changeSets.setChangeSetDate(locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

            LocateDeskBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(7);

            changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            //changes.setTimeZoneId("India Standard Time");

            //Get Time Zone
            String timeZone = "India Standard Time";
            for (int i = 0; i < teamDeskAvailabilitiesList.size(); i++) {

                if (requestTeamDeskId == teamDeskAvailabilitiesList.get(i).getTeamDeskId()) {
                    timeZone = teamDeskAvailabilitiesList.get(i).getTimeZones().get(0).getTimeZoneId();
                    break;
                }

            }

            changes.setTimeZoneId(timeZone);
            changes.setTeamDeskId(null);
            changes.setRequestedTeamId(requestTeamId);
            changes.setRequestedTeamDeskId(requestTeamDeskId);
            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            List<LocateDeskBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
            changeSetsList.add(changeSets);

            locateDeskBookingRequest.setChangeSets(changeSetsList);

            List<LocateDeskBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
            //deleteIdsList.add(deleteIds);

            locateDeskBookingRequest.setDeletedIds(deleteIdsList);

            //System.out.println("BookingRequestObject" + locateDeskBookingRequest);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doRequestDeskBooking(locateDeskBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    //requestTeamId=0;
                    //requestTeamDeskId=0;
                    //autoApprovedStatus=0;

                    if (locateEditBottomSheet != null) {
                        locateEditBottomSheet.dismiss();
                    }

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }

    }

    private void getParkingSlotId(String key) {

        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
        for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size(); i++) {

            //System.out.println("SelectedCodeEquatl" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode());
            if (key.equals(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode())) {
                //System.out.println("SelectedCarParkingSlotId" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId());
                selectedCarParkingSlotId = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId();
            }

        }


    }

    private void callBookingDatePickerBottomSheet() {



        /*BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((getActivity()).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(getActivity())));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView=bottomSheetDatePicker.findViewById(R.id.datePicker);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString=String.valueOf(year);
                String monthInString=String.valueOf(month);
                String dayInString=String.valueOf(dayOfMonth);
                String dateInString= yearInString+"-"+monthInString+"-"+dayInString;
                locateCheckInDate.setText(dateInString);
            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();*/

    }

    private void openCheckoutDialog(String mesg) {
        Dialog popDialog = new Dialog(getActivity());
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText("" + mesg);

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

    private void deskBooking() {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            //System.out.println("DeskBookingRequested");

            LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
            locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
            locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

            //locateBookingRequest.setTeamId(9);
            //locateBookingRequest.setTeamMembershipId(12);

            LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();
            changeSets.setChangeSetId(0);
            //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
            changeSets.setChangeSetDate
                    (locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

            LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(2);
            //changes.setFrom("2022-07-21T20:15:00.000Z");
            //changes.setTo("2022-07-21T21:30:00.000Z");

            changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");


            //Get Time Zone
            String timeZone = "India Standard Time";
            for (int i = 0; i < teamDeskAvailabilitiesList.size(); i++) {

                if (teamDeskIdForBooking == teamDeskAvailabilitiesList.get(i).getTeamDeskId()) {
                    timeZone = teamDeskAvailabilitiesList.get(i).getTimeZones().get(0).getTimeZoneId();
                    break;
                }

            }

            changes.setTimeZoneId(timeZone);
            changes.setTeamDeskId(teamDeskIdForBooking);
            changes.setTypeOfCheckIn(1);

            changeSets.setChanges(changes);

            List<LocateBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
            changeSetsList.add(changeSets);

            locateBookingRequest.setChangeSetsList(changeSetsList);

            LocateBookingRequest.DeleteIds deleteIds = locateBookingRequest.new DeleteIds();
            List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
            //deleteIdsList.add(deleteIds);

            locateBookingRequest.setDeleteIdsList(deleteIdsList);

            //System.out.println("BookingRequestObject" + locateBookingRequest);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doDeskBooking(locateBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    if (locateEditBottomSheet != null) {
                        locateEditBottomSheet.dismiss();
                    }

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void locateResponseHandler(Response<BaseResponse> response, String string) {

        String resultString = "";

        if (response.code() == 200) {
            if (response.body() != null && response.body().getResultCode() != null && response.body().getResultCode().equalsIgnoreCase("ok")) {
                openCheckoutDialog(string);
                callInitView();
            } else {

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
                } else if (response.body().getResultCode().toString().equals("COVID_SYMPTOMS")) {
                    resultString = "COVID_SYMPTOMS";
                } else if (response.body().getResultCode().toString().equals("DESK_UNAVAILABLE")) {
                    resultString = "Desk is Unavailable";
                } else {
                    resultString = response.body().getResultCode().toString();
                }
                //Utils.showCustomAlertDialog(getActivity(), "Booking Not Updated " + resultString);
                Utils.showCustomAlertDialog(getActivity(), resultString);
            }
        } else if (response.code() == 500) {
            Utils.showCustomAlertDialog(getActivity(), "" + response.message());
        } else if (response.code() == 401) {
            Utils.showCustomTokenExpiredDialog(getActivity(), "401 Error Response");
        } else {
            Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
        }


    }

    private void carParkingRequest() {

        if (Utils.isNetworkAvailable(getActivity())) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            LocateCarParkBookingRequest locateCarParkBookingRequest = new LocateCarParkBookingRequest();
            locateCarParkBookingRequest.setParkingSlotId(selectedCarParkingSlotId);

            LocateCarParkBookingRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkBookingRequest.new CarParkingChangeSets();
            carParkingChangeSets.setId(0);
            carParkingChangeSets.setDate(locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

            LocateCarParkBookingRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
            carParkingChanges.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            carParkingChanges.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
            carParkingChanges.setComments(etComment.getText().toString());
            carParkingChanges.setBookedForUser(SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID));
            carParkingChanges.setVehicleRegNumber(etVehicleReg.getText().toString());

            carParkingChangeSets.setCarParkingChanges(carParkingChanges);

            List<LocateCarParkBookingRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();
            carParkingChangeSetsList.add(carParkingChangeSets);

            locateCarParkBookingRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);

            LocateCarParkBookingRequest.CarParkingDeleteIds carParkingDeleteIds = locateCarParkBookingRequest.new CarParkingDeleteIds();
            List<LocateCarParkBookingRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
            //deleteIdsList.add(carParkingDeleteIds);

            locateCarParkBookingRequest.setDeleteIdsList(deleteIdsList);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doCarParkingBooking(locateCarParkBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    locateResponseHandler(response, getResources().getString(R.string.booking_success));

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }


    @Override
    public void onEditClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        TextView startTime, endTime, date, editBookingBack, tv_comment;
        LinearLayout status_check_layout;
        RelativeLayout selectDeskEditBlock;
        EditText et_comment_desk;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        date = bottomSheetDialog.findViewById(R.id.date);
        deskRoomName = bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        TextView continueEditBook = bottomSheetDialog.findViewById(R.id.editBookingContinue);
        LinearLayout llDeskLayout = bottomSheetDialog.findViewById(R.id.ll_desk_layout);
        RelativeLayout repeatBlock = bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout rl_comment_block = bottomSheetDialog.findViewById(R.id.rl_comment_block);
        selectDeskEditBlock = bottomSheetDialog.findViewById(R.id.selectDeskEditBlock);
        status_check_layout = bottomSheetDialog.findViewById(R.id.status_check_layout);
        tv_comment = bottomSheetDialog.findViewById(R.id.tv_comment);
        RelativeLayout teamsBlock = bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        TextView tvComments = bottomSheetDialog.findViewById(R.id.tv_comments);
        EditText commentRegistration = bottomSheetDialog.findViewById(R.id.ed_registration);
        editBookingBack = bottomSheetDialog.findViewById(R.id.editBookingBack);

        TextView select = bottomSheetDialog.findViewById(R.id.select_desk_room);
        et_comment_desk = bottomSheetDialog.findViewById(R.id.et_comment_desk);

        et_comment_desk.setVisibility(View.VISIBLE);
        et_comment_desk.setText(bookings.getComments());
        llDeskLayout.setVisibility(View.GONE);
        status_check_layout.setVisibility(View.GONE);
        selectDeskEditBlock.setVisibility(View.GONE);
        //Need To Change Date Here
        String sDate = binding.locateCalendearView.getText().toString();
        if (!(sDate.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(sDate);
            if (dateTime.equalsIgnoreCase("")) {
                date.setText(binding.locateCalendearView.getText().toString());
            } else {
                date.setText(dateTime);
            }
        } else {
            date.setText(binding.locateCalendearView.getText().toString());
        }
        //date.setText(binding.locateCalendearView.getText().toString());

        orgSTime = "";
        orgETime = "";

        if (bookings.getStatus() != null) {
            if (bookings.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                //startTime.setEnabled(false);
                //endTime.setEnabled(false);

                orgSTime = Utils.splitTime(bookings.getFrom());
                orgETime = Utils.splitTime(bookings.getMyto());

            } else {
                startTime.setEnabled(true);
                endTime.setEnabled(true);
            }
        } else {
            startTime.setEnabled(true);
            endTime.setEnabled(true);
        }


        if (code.equals("3")) {
            //repeatBlock.setVisibility(View.VISIBLE);
            teamsBlock.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);

            startTime.setText(Utils.splitTime(bookings.getFrom()));
            endTime.setText(Utils.splitTime(bookings.getMyto()));
            // date.setText(""+Utils.dayDateMonthFormat(bookings.getDate()));
            deskRoomName.setText(bookings.getDeskCode());
        } else {

        }

        //New...
        startTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bookings.getStatus() != null) {
                    if (bookings.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgSTime.equalsIgnoreCase(startTime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, startTime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    startTime.setText(orgSTime);
                                }

                            }

                        }
                    }
                }
            }
        });

        endTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bookings.getStatus() != null) {
                    if (bookings.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgETime.equalsIgnoreCase(endTime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, endTime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    endTime.setText(orgETime);
                                }

                            }

                        }
                    }
                }
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startTime, "Start", binding.locateCalendearView.getText().toString());
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTime, "End", binding.locateCalendearView.getText().toString());
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time","");

            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeskBottomSheetDialogToSelectDeskCode(teamDeskAvailabilities);
            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = true;

                if (startTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }
                if (endTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (Utils.compareStartEndTime(startTime.getText().toString(), endTime.getText().toString())) {


                } else {
                    Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (status) {
                    bottomSheetDialog.dismiss();
                    //Edit DeskBooking
                    doEditDeskBooking(bookings, startTime.getText().toString(), endTime.getText().toString(), et_comment_desk.getText().toString());
                }


            }
        });

        bottomSheetDialog.show();


    }

    @Override
    public void ondeskDeleteClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            //System.out.println("DeleteIdPrint"+bookings.getId()+" "+bookings.getDeskId()+" "+bookings.getTeamDeskId()+" "+bookings.getDeskCode()+" "+ bookings.getTeamMembershipId()+" "+bookings.getRequestedTeamId());

            LocateDeskDeleteRequest locateDeskDeleteRequest = new LocateDeskDeleteRequest();
            locateDeskDeleteRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
            locateDeskDeleteRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));
            List<LocateDeskDeleteRequest.ChangeSets> changeSetsList = new ArrayList<>();
            locateDeskDeleteRequest.setChangesetsList(changeSetsList);

            List<Integer> integerList = new ArrayList<>();
            integerList.add(bookings.getId());

            locateDeskDeleteRequest.setDeleteIdsList(integerList);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doDeleteDeskBooking(locateDeskDeleteRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    locateResponseHandler(response, getResources().getString(R.string.desk_book_deleted));

                    locateEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    locateEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void doEditDeskBooking(BookingForEditResponse.Bookings bookings, String startTime, String endTime, String comment) {


        //System.out.println("OriginalStartAndEndTime "+bookings.getFrom()+" "+bookings.getMyto());
        //System.out.println("SplitTime "+Utils.splitTime(bookings.getFrom())+" "+Utils.splitTime(bookings.getMyto()));
        //System.out.println("ChangedStartAndEndTime "+startTime+" "+endTime);


        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            LocateDeskBookEditFromRequest locateBookingRequest = new LocateDeskBookEditFromRequest();
            locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
            locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

            LocateDeskBookEditFromRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();
            changeSets.setChangeSetId(bookings.getId());

            //changeSets.setChangeSetDate(startTim+ "T" + "00:00:00.000" + "Z");
            changeSets.setChangeSetDate(Utils.splitDate(bookings.getDate()) + "T" + "00:00:00.000" + "Z");

            LocateDeskBookEditFromRequest.ChangeSets.Changes changes = changeSets.new Changes();
            changes.setUsageTypeId(null);

            if (comment != null && !comment.isEmpty()) {
                changes.setComments(comment);
            }

            boolean nochange = false;

            if (Utils.splitTime(bookings.getFrom()).equalsIgnoreCase(startTime) && !Utils.splitTime(bookings.getMyto()).equalsIgnoreCase(endTime)) {
                changes.setFrom(null);
                changes.setTo(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");
            } else if (Utils.splitTime(bookings.getMyto()).equalsIgnoreCase(endTime) && !Utils.splitTime(bookings.getFrom()).equalsIgnoreCase(startTime)) {
                changes.setTo(null);
                changes.setFrom(getCurrentDate() + "" + "T" + startTime + ":" + "00" + "." + "000" + "Z");
            } else if (!Utils.splitTime(bookings.getFrom()).equalsIgnoreCase(startTime) && !Utils.splitTime(bookings.getFrom()).equalsIgnoreCase(startTime)) {
                changes.setFrom(getCurrentDate() + "" + "T" + startTime + ":" + "00" + "." + "000" + "Z");
                changes.setTo(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");
            } else {
                // nochange=true;
                changes.setFrom(null);
                changes.setTo(null);
            }
            //changes.setTo(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");

            // changes.setTimeZoneId("India Standard Time");
            changes.setTimeZoneId(null);


            if (selectedDeskId > 0) {
                changes.setTeamDeskId(null);
            } else {
                changes.setTeamDeskId(null);
            }

            changes.setTypeOfCheckIn(null);

            changeSets.setChanges(changes);

            List<LocateDeskBookEditFromRequest.ChangeSets> changeSetsList = new ArrayList<>();

            if (Utils.splitTime(bookings.getFrom()).equalsIgnoreCase(startTime) && Utils.splitTime(bookings.getMyto()).equalsIgnoreCase(endTime)) {

            } else {
                changeSetsList.add(changeSets);
            }


            locateBookingRequest.setChangeSetsList(changeSetsList);

            LocateDeskBookEditFromRequest.DeleteIds deleteIds = locateBookingRequest.new DeleteIds();
            List<LocateDeskBookEditFromRequest.DeleteIds> deleteIdsList = new ArrayList<>();
            //deleteIdsList.add(deleteIds);

            locateBookingRequest.setDeleteIdsList(deleteIdsList);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doEditDeskBooking(locateBookingRequest);

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    locateResponseHandler(response, getResources().getString(R.string.booking_updated));

                    locateEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                    locateEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void callDeskBottomSheetDialogToSelectDeskCode(List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        RecyclerView rvDeskRecycler;
        DeskListRecyclerAdapter deskListRecyclerAdapter;
        TextView bsRepeatBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        rvDeskRecycler = bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack = bottomSheetDialog.findViewById(R.id.bsDeskBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        deskListRecyclerAdapter = new DeskListRecyclerAdapter(getContext(), this, getActivity(), teamDeskAvailabilities, getContext(), bottomSheetDialog);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);

        bottomSheetDialog.show();

    }

    @Override
    public void onSelectDesk(int deskId, String deskName) {

        deskRoomName.setText("" + deskName);
        selectedDeskId = deskId;


    }

    @Override
    public void onCarEditClick(CarParkingForEditResponse.CarParkBooking carParkBooking, String selectedCode) {

        TextView startTime, endTime, date, editBookingBack, tv_location_details;
        LinearLayout status_check_layout, capacity_layout;
        RelativeLayout selectDeskEditBlock;
        ChipGroup list_item;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        list_item = bottomSheetDialog.findViewById(R.id.list_item);
        tv_location_details = bottomSheetDialog.findViewById(R.id.tv_location_details);
        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        date = bottomSheetDialog.findViewById(R.id.date);
        deskRoomName = bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        capacity_layout = bottomSheetDialog.findViewById(R.id.capacity_layout);
        TextView continueEditBook = bottomSheetDialog.findViewById(R.id.editBookingContinue);
        LinearLayout llDeskLayout = bottomSheetDialog.findViewById(R.id.ll_desk_layout);
        status_check_layout = bottomSheetDialog.findViewById(R.id.status_check_layout);
        RelativeLayout repeatBlock = bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout teamsBlock = bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        TextView tvComments = bottomSheetDialog.findViewById(R.id.tv_comments);
        EditText commentRegistration = bottomSheetDialog.findViewById(R.id.ed_registration);
        EditText etBookedBy = bottomSheetDialog.findViewById(R.id.etBookedBy);
        editBookingBack = bottomSheetDialog.findViewById(R.id.editBookingBack);
        selectDeskEditBlock = bottomSheetDialog.findViewById(R.id.selectDeskEditBlock);
        TextView select = bottomSheetDialog.findViewById(R.id.select_desk_room);
        carSelectedName = bottomSheetDialog.findViewById(R.id.tv_desk_room_name);

        carSelectedName.setText(selectedCode);


        //New...
        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
        String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
        if (CountryName == null && buildingName == null && floorName == null) {

            tv_location_details.setText(buildingName + "," + floorName);
            //meetingRoomLocation.setText(buildingName + "," + floorName);

        } else {
            tv_location_details.setText(CountryName + "," + buildingName + "," + floorName);
            //meetingRoomLocation.setText(CountryName + "," + buildingName + "," + floorName);

        }


        list_item.setVisibility(View.GONE);
        capacity_layout.setVisibility(View.GONE);
        select.setVisibility(View.GONE);
        repeatBlock.setVisibility(View.GONE);
        teamsBlock.setVisibility(View.GONE);
        llDeskLayout.setVisibility(View.VISIBLE);
        status_check_layout.setVisibility(View.VISIBLE);
        selectDeskEditBlock.setVisibility(View.VISIBLE);

        etBookedBy.setVisibility(View.GONE);
        //etBookedBy.setText(carParkBooking.getBookedForUserName());
        tvComments.setVisibility(View.GONE);
        //tvComments.setText("Registration number");
        System.out.println("REceivedCarRegNumber " + carParkBooking.getVehicleRegNumber());
        commentRegistration.setText(carParkBooking.getVehicleRegNumber());


        startTime.setText(Utils.splitTime(carParkBooking.getFrom()));
        endTime.setText(Utils.splitTime(carParkBooking.getMyto()));
        select.setVisibility(View.GONE);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<SelectCode> code = new ArrayList<>();

                if (carCode != null && carCode.size() != 0) {
                    for (int i = 0; i < carCode.size(); i++) {

                        System.out.println("SelectedCode " + carCode.get(i).getId() + " " + carCode.get(i).getCode());
                        SelectCode allCarCode = new SelectCode(carCode.get(i).getId(), carCode.get(i).getCode());
                        code.add(allCarCode);
                    }
                }

                callBottomSheetToSelectDesk(code, AppConstants.CAR_PARKING);

            }
        });

        //New...
        String sDate = binding.locateCalendearView.getText().toString();
        if (!(sDate.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(sDate);
            if (dateTime.equalsIgnoreCase("")) {
                date.setText(binding.locateCalendearView.getText().toString());
            } else {
                date.setText(dateTime);
            }
        } else {
            date.setText(binding.locateCalendearView.getText().toString());
        }

        orgSTime = "";
        orgETime = "";
        orgSTime = Utils.splitTime(carParkBooking.getFrom());
        orgETime = Utils.splitTime(carParkBooking.getMyto());

        //New...
        startTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (carParkBooking.getStatus() != null) {
                    if (carParkBooking.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgSTime.equalsIgnoreCase(startTime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, startTime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    startTime.setText(orgSTime);
                                }

                            }

                        }
                    }
                }
            }
        });

        endTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (carParkBooking.getStatus() != null) {
                    if (carParkBooking.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgETime.equalsIgnoreCase(endTime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, endTime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    endTime.setText(orgETime);
                                }

                            }

                        }
                    }
                }
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startTime, "Start", binding.locateCalendearView.getText().toString());
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTime, "End", binding.locateCalendearView.getText().toString());
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time","");

            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = true;

                if (startTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }
                if (endTime.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (Utils.compareStartEndTime(startTime.getText().toString(), endTime.getText().toString())) {


                } else {
                    Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                if (isVehicleReg) {

                    if (commentRegistration.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Enter Registration Number", Toast.LENGTH_SHORT).show();
                        status = false;
                        return;
                    }

                }

                if (status) {
                    //Edit CarParkBooking
                    bottomSheetDialog.dismiss();
                    doEditCarParkBooking(carParkBooking, startTime.getText().toString(), endTime.getText().toString());
                }


            }
        });

        bottomSheetDialog.show();

    }

    @Override
    public void onCarDeleteClick(CarParkingForEditResponse.CarParkBooking carParkBooking) {

        // System.out.println("CarDeleteRequest"+carParkBooking.getId()+" "+carParkBooking.getParkingSlotId()+" "+carParkBooking.getDate()+" "+carParkBooking.getBookingCreatedUserId()+" "+carParkBooking.getBookedForUser());

        System.out.println("CarDeleteRequest" + carParkBooking.getFrom() + " " + carParkBooking.getMyto() + " " + carParkBooking.getFromUtc() + " " + carParkBooking.getToUtc());
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        CarParkingDeleteRequest carParkingDeleteRequest = new CarParkingDeleteRequest();
        carParkingDeleteRequest.setParkingSlotId(carParkBooking.getParkingSlotId());

        CarParkingDeleteRequest.Changesets changesets = carParkingDeleteRequest.new Changesets();
        //changesets.setId(0);
        // changesets.setDate(binding.locateCalendearView.getText().toString()+"T00:00:00.000Z");
        //changesets.setDate(""+carParkBooking.getDate());

        //CarParkingDeleteRequest.Changesets.Changes changes=changesets.new Changes();

        /*System.out.println("2000-01-01T18:00:00Z");
        //String fromReplace=carParkBooking.getFrom().replace("00Z","00.000Z");
        String fromReplace=Utils.MonthAndDateAndTwithZString(carParkBooking.getFrom());
        changes.setFrom(fromReplace);
        //String toReplace=carParkBooking.getMyto().replace("00Z","00.000Z");
        String toReplace=Utils.MonthAndDateAndTwithZString(carParkBooking.getMyto());
        changes.setTo(toReplace);

        changes.setComments(carParkBooking.getComments());
        changes.setBookedForUser(carParkBooking.getBookingCreatedUserId());
        changes.setVehicleRegNumber(carParkBooking.getVehicleRegNumber());

        changesets.setChanges(changes);*/

        List<CarParkingDeleteRequest.Changesets> changesetsList = new ArrayList<>();
        //changesetsList.add(changesets);
        carParkingDeleteRequest.setChangesetsList(changesetsList);

        List<Integer> deIntegerList = new ArrayList<>();
        deIntegerList.add(carParkBooking.getId());
        carParkingDeleteRequest.setDeIntegerList(deIntegerList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doDeleteCarParking(carParkingDeleteRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                locateCarEditBottomSheet.dismiss();
                locateResponseHandler(response, getResources().getString(R.string.desk_book_deleted));


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                locateCarEditBottomSheet.dismiss();
            }
        });


    }

    private void doEditCarParkBooking(CarParkingForEditResponse.CarParkBooking carParkBooking, String startTime, String endTime) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            //From Calender
            //String startDate="2022-07-25T11:00:00.000Z";
            String startDate = binding.locateCalendearView.getText().toString() + " " + binding.locateStartTime.getText().toString() + ":00.000Z";
            //String endDate=binding.locateCalendearView.getText().toString()+" "+binding.locateEndTime.getText().toString()+":00";


            LocateCarParkEditRequest locateCarParkEditRequest = new LocateCarParkEditRequest();

            if (selectedCarId > 0) {
                locateCarParkEditRequest.setParkingSlotId(selectedCarId);
            } else {
                locateCarParkEditRequest.setParkingSlotId(carParkBooking.getParkingSlotId());
            }

            List<LocateCarParkEditRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();

            LocateCarParkEditRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkEditRequest.new CarParkingChangeSets();
            carParkingChangeSets.setId(carParkBooking.getId());
            carParkingChangeSets.setDate(startDate);

            LocateCarParkEditRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
            carParkingChanges.setVehicleRegNumber(carParkBooking.getVehicleRegNumber());
            carParkingChanges.setFrom("2000-01-01T" + startTime + ":00.000Z");
            carParkingChanges.setTo("2000-01-01T" + endTime + ":00.000Z");
            carParkingChangeSets.setCarParkingChanges(carParkingChanges);

            carParkingChangeSetsList.add(carParkingChangeSets);

            locateCarParkEditRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);

            LocateCarParkEditRequest.CarParkingDeleteIds carParkingDeleteIds = locateCarParkEditRequest.new CarParkingDeleteIds();
            List<LocateCarParkEditRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
            //deleteIdsList.add(carParkingDeleteIds);

            locateCarParkEditRequest.setDeleteIdsList(deleteIdsList);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doCarParkingEdit(locateCarParkEditRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    locateCarEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    selectedCarId = 0;
                    locateResponseHandler(response, getResources().getString(R.string.booking_updated));


                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    locateCarEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }


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
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        RelativeLayout layout = bottomSheetDialog.findViewById(R.id.amenitiesViewBlock);
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);
        tvFilterAmenities = bottomSheetDialog.findViewById(R.id.tvFilter);
        filterSearch = bottomSheetDialog.findViewById(R.id.filterSearch);
        filterTotalSize = bottomSheetDialog.findViewById(R.id.filterTotalSize);

        //Language
        filterSearch.setHint(" " + appKeysPage.getSearch());
        tvFilterAmenities.setText(appKeysPage.getFilters());


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        //locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < userAllowedMeetingResponseList.size(); i++) {

                    System.out.println("AllDetailsInUserAllowedMeetings " + userAllowedMeetingResponseList.get(i).getName());

                    for (int j = 0; j < meetingStatusModelList.size(); j++) {


                        if (userAllowedMeetingResponseList.get(i).getId() == meetingStatusModelList.get(j).getId()) {
                            if (meetingStatusModelList.get(j).getStatus() == 1) {

                                List<UserAllowedMeetingResponse.Amenity> amenityList = userAllowedMeetingResponseList.get(i).getAmenities();

                                doCheckAppliedAminitiesWithMeetingRoom(amenityList, meetingStatusModelList.get(j));


                            }

                        }


                    }

                }

                callInitView();


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
        adapter = new ItemAdapter(mList, this);
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

    private void doCheckAppliedAminitiesWithMeetingRoom(List<UserAllowedMeetingResponse.Amenity> amenityList, MeetingStatusModel meetingStatusModel) {

        //amenityList-->This has default meeting room aminities
        //list-->User selected amenities


        ItemAdapter itemAadapter = new ItemAdapter();
        //Get user selected amenities
        ArrayList<DataModel> userSelectedAmenities = itemAadapter.getUpdatedList();
        int amenitiesMatchCount = 0;

        //Checking Here Only With Rooms
        for (int i = 0; i < userSelectedAmenities.get(0).getNestedList().size(); i++) {


            for (int j = 0; j < amenityList.size(); j++) {

                if (userSelectedAmenities.get(0).getNestedList().get(i).isChecked()) {
                    if (userSelectedAmenities.get(0).getNestedList().get(i).getId() == amenityList.get(j).getId()) {
                        amenitiesMatchCount = amenitiesMatchCount + 1;
                    }
                }

            }
        }


        int userSelectedTrueCount = 0;
        for (int i = 0; i < userSelectedAmenities.get(0).getNestedList().size(); i++) {

            if (userSelectedAmenities.get(0).getNestedList().get(i).isChecked()) {
                userSelectedTrueCount = userSelectedTrueCount + 1;
            }

        }

        if (amenitiesMatchCount == userSelectedTrueCount) {
            System.out.println("AllUserSelectedAmenitiesAvaliableHInThisRoom " + meetingStatusModel.getId());

        } else {
            System.out.println("UserSelectedAmenityIsNotAvaliableHInThisRoom " + meetingStatusModel.getId());
            MeetingAmenityStatus meetingAmenityStatus = new MeetingAmenityStatus(meetingStatusModel.getId());
            meetingAmenityStatusList.add(meetingAmenityStatus);
        }

        amenitiesApplyStatus = true;


    }


    public void bottomSheetLocateDatePickerInBooking(Context mContext, Activity activity, String title, String date, TextView locateCheckInDateCal) {

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView tvSelectDate = bottomSheetDatePicker.findViewById(R.id.tvSelectDate);
        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView = bottomSheetDatePicker.findViewById(R.id.datePicker);

        //Language
        calContinue.setText(appKeysPage.getContinue());
        calBack.setText(appKeysPage.getBack());
        tvSelectDate.setText(appKeysPage.getSelectDate());

        Calendar c = Calendar.getInstance();
        calendarView.setMinDate(c.getTimeInMillis() - 1000);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString = String.valueOf(year);

                //MonthConversion
                int actualMonth = month + 1;
                String monthInStringFormat;
                if (actualMonth >= 10) {
                    monthInStringFormat = String.valueOf(actualMonth);
                } else {
                    String monthInString = String.valueOf(actualMonth);
                    monthInStringFormat = "0" + monthInString;
                }

                //DayConversion
                String dayInStringFormat;
                if (dayOfMonth < 10) {
                    String dayInString = String.valueOf(dayOfMonth);
                    dayInStringFormat = "0" + dayInString;
                } else {
                    dayInStringFormat = String.valueOf(dayOfMonth);
                }


                String dateInString = "";

                //System.out.println("ContinuPrintHere" + locateCheckInDateCal.getText());
                dateInString = yearInString + "-" + monthInStringFormat + "-" + dayInStringFormat;
                //System.out.println("PickedDate" + dateInString);


                locateCheckInDateCal.setText("" + dateInString);
                binding.showCalendar.setText(Utils.showCalendarDate(dateInString));
                checkIsCurrentDate(dateInString);


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callInitView();

                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date d1=null,d2=null;
                try {
                    d1=formatter.parse(formatter.format(date));
                    d2 = formatter.parse(dateInString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println("CurentDateSelected"+d1+" "+d2);*/

                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();

    }


    private void checkIsCurrentDate(String dateInString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date currrentDate = null, d2 = null;
        try {
            currrentDate = formatter.parse(formatter.format(date));
            d2 = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currrentDate.getDate() == d2.getDate()) {
            //System.out.println("BothDateEqual");

            SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");
            Date currentTime = Calendar.getInstance().getTime();

            try {
                String time = tFormat.format(currentTime);

                Date d = tFormat.parse(time);
                Calendar cal = Calendar.getInstance();
                if (d != null) {
                    cal.setTime(d);
                    cal.add(Calendar.MINUTE, 2);
                    String newTime = tFormat.format(cal.getTime());

                    binding.locateStartTime.setText(newTime);
                    binding.locateEndTime.setText("23:59");
                } else {
                    binding.locateStartTime.setText(time);
                    binding.locateEndTime.setText("23:59");
                }

            } catch (Exception e) {
                binding.locateStartTime.setText("08:30");
                binding.locateEndTime.setText("23:59");
            }

        } else if (currrentDate.getDate() < d2.getDate()) {
            //System.out.println("SelecctedDateIsHigh");
            /*binding.locateStartTime.setText("08:30");
            binding.locateEndTime.setText("23:59");*/

            if (profileData != null) {
                if (profileData.getWorkHoursFrom() != null && profileData.getWorkHoursTo() != null) {
                    binding.locateStartTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
                    binding.locateEndTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
                } else {
                    binding.locateStartTime.setText("08:30");
                    binding.locateEndTime.setText("23:59");
                }
            } else {
                binding.locateStartTime.setText("08:30");
                binding.locateEndTime.setText("23:59");
            }

        } else if (currrentDate.getDate() > d2.getDate()) {
            //System.out.println("SelecctedDateIsLow");
            /*binding.locateStartTime.setText("08:00");
            binding.locateEndTime.setText("23:59");*/

            if (profileData != null) {
                if (profileData.getWorkHoursFrom() != null && profileData.getWorkHoursTo() != null) {
                    binding.locateStartTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
                    binding.locateEndTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
                } else {
                    binding.locateStartTime.setText("08:30");
                    binding.locateEndTime.setText("23:59");
                }
            } else {
                binding.locateStartTime.setText("08:30");
                binding.locateEndTime.setText("23:59");
            }

        }

        //System.out.println("CurentDateSelected" + currrentDate + " " + d2);

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

        //Language
        backTv.setText(appKeysPage.getBack());
        continueTv.setText(appKeysPage.getContinue());

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
        if (title.equals("Start")) {
            titleTv.setText(appKeysPage.getStart());
        } else if (title.equals("End")) {
            titleTv.setText(appKeysPage.getEnd());
        }


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
                binding.firstLayout.removeAllViews();
                endTimeSelectedStats = 1;
                initLoadFloorDetails(2);
            } else {
                Toast.makeText(getContext(), "Invalid Time Range", Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void callInitView() {
        binding.firstLayout.removeAllViews();
        initLoadFloorDetails(0);
    }


    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {


        int capacity = 0;

        //Show Amenities in Meeting Booking
        //Amenities Block

        List<String> amenitiesList = new ArrayList<>();
        for (int i = 0; i < userAllowedMeetingResponseList.size(); i++) {


            if (meetingListToEditResponse.getMeetingRoomId() == userAllowedMeetingResponseList.get(i).getId()) {

                capacity = userAllowedMeetingResponseList.get(i).getNoOfPeople();

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


        //Clear Internal Participants here
        chipList.clear();

        if (attendeesListForEdit != null && attendeesListForEdit.size() > 0) {
            attendeesListForEdit.clear();
        }

        //Get AttendeeList To Edit
        attendeesListForEdit = meetingListToEditResponse.getAttendeesList();

        TextView startRoomTime, endTRoomime, editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription, roomTitle, showtvRoomStartTime, tvRoomCapacityCount;
        EditText etParticipants, externalAttendees, etSubject, etComments;
        RelativeLayout startTimeLayout, endTimeLayout, rl_repeat_block_room, selectMeetingRoomLayout, rl_teams_layout_room, capacityRoomBlock;
        RecyclerView rvParticipant;
        LinearLayout capacityBlock;
        //New...
        LinearLayout subCmtLay, child_layout;
        TextView roomDate, select_desk_room_room, tv_desk_room_name_room, meetingRoomLocation, user_status_room;

        ChipGroup chipGroupListItem, externalAttendeesChipGroup, list_item;
        //participantChipGroupInEdit

        //Language
        TextView tvRoomStart, tvRoomEnd, showTvRoomEndTime;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_booking,
                new RelativeLayout(getContext()))));

        roomTitle = bottomSheetDialog.findViewById(R.id.roomTitle);
        tvRoomStart = bottomSheetDialog.findViewById(R.id.tvRoomStart);
        tvRoomEnd = bottomSheetDialog.findViewById(R.id.tvRoomEnd);
        rl_teams_layout_room = bottomSheetDialog.findViewById(R.id.rl_teams_layout_room);
        rl_teams_layout_room.setVisibility(View.GONE);

        capacityRoomBlock = bottomSheetDialog.findViewById(R.id.capacityRoomBlock);
        capacityRoomBlock.setVisibility(View.GONE);


        roomTitle.setText("Edit your booking");


        startRoomTime = bottomSheetDialog.findViewById(R.id.tvRoomStartTime);
        endTRoomime = bottomSheetDialog.findViewById(R.id.tvRoomEndTime);
        showTvRoomEndTime = bottomSheetDialog.findViewById(R.id.showTvRoomEndTime);

        selectMeetingRoomLayout = bottomSheetDialog.findViewById(R.id.selectMeetingRoomLayout);
        selectMeetingRoomLayout.setVisibility(View.GONE);
        select_desk_room_room = bottomSheetDialog.findViewById(R.id.select_desk_room_room);
        select_desk_room_room.setVisibility(View.GONE);

        tv_desk_room_name_room = bottomSheetDialog.findViewById(R.id.tv_desk_room_name_room);
        tv_desk_room_name_room.setText(meetingListToEditResponse.getMeetingRoomName());

        meetingRoomLocation = bottomSheetDialog.findViewById(R.id.meetingRoomLocation);

        capacityBlock = bottomSheetDialog.findViewById(R.id.capacityBlock);
        capacityBlock.setVisibility(View.VISIBLE);
        tvRoomCapacityCount = bottomSheetDialog.findViewById(R.id.tvRoomCapacityCount);
        tvRoomCapacityCount.setText("" + capacity);

        chipGroupListItem = bottomSheetDialog.findViewById(R.id.list_item);
        chipGroupListItem.setVisibility(View.GONE);
        list_item = bottomSheetDialog.findViewById(R.id.list_item);


        externalAttendeesChipGroup = bottomSheetDialog.findViewById(R.id.externalAttendeesChipGroup);


        user_status_room = bottomSheetDialog.findViewById(R.id.user_status_room);
        user_status_room.setText("Avaliable");


        etParticipants = bottomSheetDialog.findViewById(R.id.etParticipants);
        externalAttendees = bottomSheetDialog.findViewById(R.id.externalAttendees);
        etSubject = bottomSheetDialog.findViewById(R.id.etSubject);
        rvParticipant = bottomSheetDialog.findViewById(R.id.rvParticipant);
        participantChipGroup = bottomSheetDialog.findViewById(R.id.participantChipGroup);
        rl_repeat_block_room = bottomSheetDialog.findViewById(R.id.rl_repeat_block_room);
        rl_repeat_block_room.setVisibility(View.GONE);

        etComments = bottomSheetDialog.findViewById(R.id.etCommentsMeeting);
        editRoomBookingContinue = bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack = bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        tvMeetingRoomDescription = bottomSheetDialog.findViewById(R.id.meetingRoomDescription);
        roomTitle = bottomSheetDialog.findViewById(R.id.roomTitle);
        TextView meetingAvaliable = bottomSheetDialog.findViewById(R.id.meetingAvaliable);

        TextView select = bottomSheetDialog.findViewById(R.id.select_desk_room);

        startTimeLayout = bottomSheetDialog.findViewById(R.id.startTimeLayout);
        endTimeLayout = bottomSheetDialog.findViewById(R.id.endTimeLayout);

        showtvRoomStartTime = bottomSheetDialog.findViewById(R.id.showtvRoomStartTime);

        subCmtLay = bottomSheetDialog.findViewById(R.id.subCmtLay);
        child_layout = bottomSheetDialog.findViewById(R.id.child_layout);
        roomDate = bottomSheetDialog.findViewById(R.id.roomDate);

        startRoomTime.setText(Utils.splitTime(meetingListToEditResponse.getFrom()));
        endTRoomime.setText(Utils.splitTime(meetingListToEditResponse.getTo()));
        showTvRoomEndTime.setText(Utils.splitTime(meetingListToEditResponse.getTo()));

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);

        //Language
        tvRoomStart.setText(appKeysPage.getStart());
        tvRoomEnd.setText(appKeysPage.getEnd());
        tvMeetingRoomDescription.setText(appKeysPage.getDescription());
        editRoomBookingContinue.setText(appKeysPage.getContinue());
        editRoomBookingBack.setText(appKeysPage.getBack());
        etSubject.setHint(meetingRoomsLanguage.getSubject());
        etComments.setHint(appKeysPage.getComments());
        meetingAvaliable.setText(global.getAvailable());


        showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + startRoomTime.getText().toString());


        //Language
        tvMeetingRoomDescription.setText(appKeysPage.getDescription());


        //New...
        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);
        String CountryName = SessionHandler.getInstance().get(getContext(), AppConstants.COUNTRY_NAME);
        if (CountryName == null && buildingName == null && floorName == null) {

            roomDate.setText(buildingName + "," + floorName);
            meetingRoomLocation.setText(buildingName + "," + floorName);

        } else {
            roomDate.setText(CountryName + "," + buildingName + "," + floorName);
            meetingRoomLocation.setText(CountryName + "," + buildingName + "," + floorName);

        }

        if (amenitiesList.size() > 0) {
            list_item.setVisibility(View.VISIBLE);
            for (int i = 0; i < amenitiesList.size(); i++) {

                Chip AminitiesChip = new Chip(getContext());
                AminitiesChip.setText(amenitiesList.get(i));
                AminitiesChip.setCheckable(false);
                AminitiesChip.setClickable(false);

                AminitiesChip.setTextAppearance(R.style.chipText);
                AminitiesChip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                AminitiesChip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

                list_item.addView(AminitiesChip);


            }
        }


        for (int i = 0; i < meetingListToEditResponse.getAttendeesList().size(); i++) {


            System.out.println("AttendeesListInLoop " + meetingListToEditResponse.getAttendeesList().get(i).getEmail());
            //System.out.println("AttendeesListInChipList " + chipList.get(i).getEmail());

            ParticipantDetsilResponse participantDetsilResponse = new ParticipantDetsilResponse(meetingListToEditResponse.getAttendeesList().get(i).getId(), meetingListToEditResponse.getAttendeesList().get(i).getFirstName(), meetingListToEditResponse.getAttendeesList().get(i).getLastName(), meetingListToEditResponse.getAttendeesList().get(i).getFullName(), meetingListToEditResponse.getAttendeesList().get(i).getEmail(), meetingListToEditResponse.getAttendeesList().get(i).isActive());
            chipList.add(participantDetsilResponse);

            Chip chip = new Chip(getContext());
            chip.setText(meetingListToEditResponse.getAttendeesList().get(i).getEmail());
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setCloseIconVisible(true);

            chip.setTextAppearance(R.style.chipText);
            chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
            chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

            participantChipGroup.addView(chip);


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


        //External Participants
        List<String> externalAttendeesEmail = new ArrayList<>();

        for (int i = 0; i < meetingListToEditResponse.getExternalAttendeesList().size(); i++) {
            System.out.println("ExternalAttendeesListInLoop " + meetingListToEditResponse.getExternalAttendeesList().get(i));

            Chip chip = new Chip(getContext());
            chip.setText(meetingListToEditResponse.getExternalAttendeesList().get(i));
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setCloseIconVisible(true);

            chip.setTextAppearance(R.style.chipText);
            chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
            chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

            externalAttendeesChipGroup.addView(chip);

            //Add In List
            externalAttendeesEmail.add(meetingListToEditResponse.getExternalAttendeesList().get(i));


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


        //Internal Participants
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
                    //validate email address
                    if (Utils.isValidEmail(s.toString().trim())) {


                        chip.setText(s.toString());
                        chip.setCheckable(false);
                        chip.setClickable(false);
                        chip.setCloseIconVisible(true);

                        chip.setTextAppearance(R.style.chipText);
                        chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                        chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));

                        externalAttendeesChipGroup.setVisibility(View.VISIBLE);
                        externalAttendeesChipGroup.addView(chip);

                        //Add In List
                        externalAttendeesEmail.add(s.toString());

                        externalAttendees.clearFocus();
                        externalAttendees.setText("");
                    } else {
                        Utils.toastMessage(getContext(), "Please enter a valid email address.");
                    }

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


        //New...

        orgSTime = "";
        orgETime = "";
        orgSTime = Utils.splitTime(meetingListToEditResponse.getFrom());
        orgETime = Utils.splitTime(meetingListToEditResponse.getTo());

        startRoomTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (meetingListToEditResponse.getStatus() != null) {


                    //Requested time can change only between start and end time
                    if (meetingListToEditResponse.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgSTime.equalsIgnoreCase(startRoomTime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, startRoomTime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    startRoomTime.setText(orgSTime);
                                }

                            }

                        }
                    } else {

                        showtvRoomStartTime.setText(Utils.showBottomSheetDateTime(binding.locateCalendearView.getText().toString()) + " • " + startRoomTime.getText().toString());

                    }
                }
            }
        });

        endTRoomime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (meetingListToEditResponse.getStatus() != null) {
                    if (meetingListToEditResponse.getStatus().getBookingType().equalsIgnoreCase("REQGRN")) {
                        if (orgETime.equalsIgnoreCase(endTRoomime.getText().toString())) {

                        } else {

                            if (!(orgSTime.equals("")) && !(orgETime.equals(""))) {

                                boolean b = Utils.checkEditTime(orgSTime, orgETime, endTRoomime.getText().toString());

                                if (!b) {
                                    Toast.makeText(getActivity(), "You can't exceed approved booking time period", Toast.LENGTH_SHORT).show();
                                    endTRoomime.setText(orgETime);


                                }

                            }

                        }
                    } else {
                        showTvRoomEndTime.setText(endTRoomime.getText().toString());
                    }
                }
            }
        });

        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startRoomTime, "Start", binding.locateCalendearView.getText().toString());

            }
        });

        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTRoomime, "End", binding.locateCalendearView.getText().toString());

            }
        });

        editRoomBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 1) {
                    bottomSheetDialog.dismiss();

                } else {

                    subCmtLay.setVisibility(View.GONE);
                    child_layout.setVisibility(View.VISIBLE);
                    roomDate.setText(buildingName + "," + floorName);
                    page = 1;
                }
            }
        });

        editRoomBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 1) {

                    boolean status = true;

                    if (startRoomTime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }
                    if (endTRoomime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    if (Utils.compareStartEndTime(startRoomTime.getText().toString(), endTRoomime.getText().toString())) {

                    } else {
                        Toast.makeText(getContext(), "End time must be after the start time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    chipGroupListItem.setVisibility(View.VISIBLE);
                    etSubject.setText(meetingListToEditResponse.getSubject());
                    etComments.setText(meetingListToEditResponse.getComments());


                    //make visible
                    participantChipGroup.setVisibility(View.VISIBLE);
                    externalAttendeesChipGroup.setVisibility(View.VISIBLE);

                    subCmtLay.setVisibility(View.VISIBLE);
                    child_layout.setVisibility(View.GONE);


                    String startTime = showtvRoomStartTime.getText().toString();
                    System.out.println("StartRoomFormat " + startTime);
                    String[] startTimeToSplit = startTime.split("•");
                    String startWithAMOrPM = startTimeToSplit[1];

                    roomDate.setText(Utils.showCalendarDate(binding.locateCalendearView.getText().toString()) +
                            startWithAMOrPM + "to" + showTvRoomEndTime.getText().toString());

                    page = 2;

                } else {
                    boolean status = true;

                    if (startRoomTime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select Start Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }
                    if (endTRoomime.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please Select End Time", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                    String subject = etSubject.getText().toString();
                    String comment = etComments.getText().toString();


                    if (subject.isEmpty() || subject.equals("") || subject == null) {
                        Toast.makeText(getContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }

                   /* if (comment.isEmpty() || comment.equals("") || comment == null) {
                        Toast.makeText(getContext(), "Please Enter Comment", Toast.LENGTH_LONG).show();
                        status = false;
                        return;
                    }*/

                    if (status) {
                        //Edit MeeingkBooking
                        doEditMeetingRoomBooking(meetingListToEditResponse, startRoomTime.getText().toString(), endTRoomime.getText().toString(), subject, comment, bottomSheetDialog, externalAttendeesEmail);
                        //System.out.println("MeeingEditGoHere");
                    }

                }


            }
        });

        //New...
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                page = 1;
            }
        });

        bottomSheetDialog.show();

    }

    @Override
    public void onMeetingDeleteClick(MeetingListToEditResponse meetingListToEditResponse) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            DeleteMeetingRoomRequest deleteMeetingRoomRequest = new DeleteMeetingRoomRequest();
            List<DeleteMeetingRoomRequest.DelChangesets> delChangesetsList = new ArrayList<>();


            List<Integer> integerList = new ArrayList<>();
            integerList.add(meetingListToEditResponse.getId());
            deleteMeetingRoomRequest.setDeletedIdsList(integerList);
            deleteMeetingRoomRequest.setChangesetsList(delChangesetsList);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doDeleteMeetingRoom(deleteMeetingRoomRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    locateMeetEditBottomSheet.dismiss();

                    locateResponseHandler(response, getResources().getString(R.string.desk_book_deleted));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    locateMeetEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    private void doEditMeetingRoomBooking(MeetingListToEditResponse meetingListToEditResponse, String startTime, String endTime, String subject, String comment, BottomSheetDialog bottomSheetDialog, List<String> externalAttendeesEmail) {

        if (Utils.isNetworkAvailable(getActivity())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            MeetingRoomEditRequest meetingRoomEditRequest = new MeetingRoomEditRequest();
            meetingRoomEditRequest.setMeetingRoomId(meetingListToEditResponse.getMeetingRoomId());
            meetingRoomEditRequest.setMsTeams(false);
            meetingRoomEditRequest.setHandleRecurring(false);
            meetingRoomEditRequest.setOnlineMeeting(false);

            MeetingRoomEditRequest.Changesets changesets = meetingRoomEditRequest.new Changesets();

            changesets.setDate(binding.locateCalendearView.getText().toString() + "T" + "00:00:00.000" + "Z");
            changesets.setId(meetingListToEditResponse.getId());


            MeetingRoomEditRequest.Changesets.Changes changes = changesets.new Changes();

            List<Integer> attendeesList = new ArrayList<>();



            /*if (chipList != null) {
                //MeetingRoomEditRequest.Changesets.Changes.Attendees attendees = changes.new Attendees();
                for (int i = 0; i < chipList.size(); i++) {
                    System.out.println("EditedAndAddedParticipants "+chipList.get(i).getId());
                    attendeesList.add(chipList.get(i).getId());
                }
            }*/


            List<Integer> addedList = LogicHandler.getNewlyAdded(attendeesListForEdit, chipList);
            System.out.println("NewellyAddedParticipant " + addedList);

            if (addedList != null && addedList.size() > 0) {

                for (int i = 0; i < addedList.size(); i++) {
                    attendeesList.add(addedList.get(i));
                }

            }

            List<Integer> removedList = LogicHandler.getRemoved(attendeesListForEdit, chipList);
            System.out.println("RemovedParticipant " + removedList);
            if (removedList != null && removedList.size() > 0) {
                for (int i = 0; i < removedList.size(); i++) {
                    attendeesList.add(removedList.get(i));
                }
            }

            changes.setAttendeesList(attendeesList);

            changes.setFrom(getCurrentDate() + "" + "T" + startTime + ":" + "00" + "." + "000" + "Z");
            changes.setSubject(subject);
            changes.setRequest(false);
            changes.setTo(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");

            changesets.setChanges(changes);

            List<MeetingRoomEditRequest.Changesets> changesetList = new ArrayList<>();
            changesetList.add(changesets);

            meetingRoomEditRequest.setChangesetsList(changesetList);

            List<MeetingRoomEditRequest.Changesets.Changes.ExternalAttendees> externalAttendeesList = new ArrayList<>();

            //ExternalAttendess
            List<String> externalAttendeesListStr = new ArrayList<>();

            if (externalAttendeesEmail != null) {
                for (int i = 0; i < externalAttendeesEmail.size(); i++) {
                    externalAttendeesListStr.add(externalAttendeesEmail.get(i));
                }

            }
            changes.setExternalAttendeesList(externalAttendeesListStr);


            List<MeetingRoomEditRequest.DeleteIds> deleteIdsList = new ArrayList<>();
            meetingRoomEditRequest.setDeletedIds(deleteIdsList);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doRoomEdit(meetingRoomEditRequest);

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    chipList.clear();
                    bottomSheetDialog.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    locateMeetEditBottomSheet.dismiss();

                    locateResponseHandler(response, getResources().getString(R.string.booking_updated));

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    locateMeetEditBottomSheet.dismiss();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    @Override
    public void onDeskSelect(int id, String code, String desk) {

        if (desk.equals(AppConstants.DESK)) {
            System.out.println("BottomSelected " + id + " " + code);
            tv_desk_room_name.setText(code);
        } else if (desk.equals(AppConstants.CAR_PARKING)) {
            carSelectedName.setText(code);
            selectedCarId = id;
            System.out.println("CarSelectedName " + id + " " + code);
        }

    }


    //New...
    public void repeateBottomSheet() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat,
                new RelativeLayout(getContext()))));

        txtInterval = bottomSheetDialog.findViewById(R.id.txtInterval);

        txtInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatDataBottomSheet();
            }
        });
        bottomSheetDialog.show();


    }

    private void repeatDataBottomSheet() {
        repeatDataBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        repeatDataBottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat_data,
                new RelativeLayout(getContext()))));

        RecyclerView recyclerView;
        recyclerView = repeatDataBottomSheetDialog.findViewById(R.id.recyclerView);

        ArrayList<String> stringArrayList = new ArrayList<>();

        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        stringArrayList.add("4");
        stringArrayList.add("5");

        RepeateDataAdapter repeateDataAdapter = new RepeateDataAdapter(getActivity(), stringArrayList);
        recyclerView.setAdapter(repeateDataAdapter);

        repeatDataBottomSheetDialog.show();
    }

    @Override
    public void repeatDataClick(int pos, String data) {
        txtInterval.setText(data);
        repeatDataBottomSheetDialog.dismiss();
    }

    @Override
    public void showMyTeamLocation(DAOTeamMember.DayGroup.CalendarEntry calendarEntry, String name) {
        /*relativeLayout.leftMargin = i;
        relativeLayout.topMargin = i1;
        ivDesk.setLayoutParams(relativeLayout);*/

        /*String[] words = name.split(",");
        String firstName=words[0];
        String lastName=words[1];*/


        getUserContactDetails(name);

        //binding.locateMyTeamList.set
        binding.myTeamHeader.setVisibility(View.GONE);
        binding.myTeamContactBlock.setVisibility(View.VISIBLE);
        binding.bookNearByBlock.setVisibility(View.VISIBLE);

        //myteamBottomSheetBehavior.setPeekHeight(600);


        //View perSonView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        View perSonView = getLayoutInflater().inflate(R.layout.layout_item_myteam_sitting, null, false);
        //ImageView desk = perSonView.findViewById(R.id.ivDesk);

        ImageView ivPerson = perSonView.findViewById(R.id.ivUserImageMyTeam);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //desk.setVisibility(View.GONE);
        ivPerson.setVisibility(View.VISIBLE);
        relativeLayout.width = 60;
        relativeLayout.height = 60;

        ivPerson.setLayoutParams(relativeLayout);

        //binding.myTeamSittingLayout.removeView(perSonView);


        binding.bookNearByBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.bookNearByBlock.setVisibility(View.GONE);

                binding.firstLayout.removeView(ivPerson);
                binding.firstLayout.removeView(perSonView);

                binding.myTeamHeader.setVisibility(View.VISIBLE);
                binding.myTeamContactBlock.setVisibility(View.GONE);

                //myteamBottomSheetBehavior.setPeekHeight(300);


            }
        });


        if (calendarEntry.getBooking() == null) {
            Toast.makeText(getContext(), "Booking Not Avaliable", Toast.LENGTH_LONG).show();
        } else if (calendarEntry.getBooking() != null) {
            //for (int i = 0; i < dayGroups.get(0).getCalendarEntries().size(); i++) {
            if (calendarEntry.getBooking() != null) {

                int selectedFloorId = calendarEntry.getBooking().getLocationBuildingFloor().getFloorID();
                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
                if (selectedFloorId == parentId) {
                    binding.locateMyTeamUserName.setText(name);
                    binding.tvLocateMyTeamLocationView.setText(calendarEntry.getBooking().getLocationBuildingFloor().getBuildingName() + "," + calendarEntry.getBooking().getLocationBuildingFloor().getfLoorName());
                    binding.locateMyTeamDeskName.setText(calendarEntry.getBooking().getDeskCode());
                    binding.myTeamTvStartTime.setText(Utils.splitTime(calendarEntry.getFrom()));
                    binding.myTeamTvEndTime.setText(Utils.splitTime(calendarEntry.getMyto()));
                    getFloorAndDeskDetailsToPlaceUser(calendarEntry.getBooking(), relativeLayout, perSonView, ivPerson);
                } else {
                    Toast.makeText(getContext(), "Selected user is not avaliable in this floor", Toast.LENGTH_LONG).show();
                }

            }
            //}
        }


    /*    if(dayGroups!=null) {

            if (dayGroups.get(0).getCalendarEntries() != null) {
                int selectedFloorId=dayGroups.get(0).getCalendarEntries().get(0).getBooking().getLocationBuildingFloor().getFloorID();
                int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);

                if(selectedFloorId==parentId) {
                    myTeam_tv_start_time.setText(Utils.splitTime(dayGroups.get(0).getCalendarEntries().get(0).getFrom()));
                    myTeam_tv_end_time.setText(Utils.splitTime(dayGroups.get(0).getCalendarEntries().get(0).getMyto()));
                    getFloorAndDeskDetailsToPlaceUser(dayGroups.get(0).getCalendarEntries().get(0).getBooking(), relativeLayout, perSonView, ivPerson);
                }else {
                    Toast.makeText(getContext(),"Selected user is not avaliable in this floor",Toast.LENGTH_LONG).show();
                }


            }

        }*/

        //relativeLayout.leftMargin = i;
        //relativeLayout.topMargin = i1;
        //ivPerson.setLayoutParams(relativeLayout);
        //binding.firstLayout.addView(perSonView);


    }

    private void getUserContactDetails(String name) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GlobalSearchResponse> call = apiService.getGlobalSearchData(40, name);
        call.enqueue(new Callback<GlobalSearchResponse>() {
            @Override
            public void onResponse(Call<GlobalSearchResponse> call, Response<GlobalSearchResponse> response) {

                GlobalSearchResponse globalSearchResponse = response.body();

                if (globalSearchResponse.getResults().size() > 0) {
                    binding.tvMyTeamEmail.setText(globalSearchResponse.getResults().get(0).getEmail());
                    binding.tvMyTeamTeams.setText(globalSearchResponse.getResults().get(0).getTeam());
                    binding.tvmyTeamPhone.setText(globalSearchResponse.getResults().get(0).getPhoneNumber());
                }

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
/*
                for (int i = 0; i <globalSearchResponse.getResults().size() ; i++) {

                    System.out.println("TeamContactDetails "+globalSearchResponse.getResults().get(i).getEmail());

                }*/

            }

            @Override
            public void onFailure(Call<GlobalSearchResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void loadMyTeamLocation(int floorID, int deskId) {

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);

        boolean floorFoundStatus = false;

        if (floorID == parentId) {

            for (int i = 0; i < locateCountryResposeList.size(); i++) {

                for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getDesks().size(); j++) {
                    if (deskId == locateCountryResposeList.get(i).getLocationItemLayout().getDesks().get(j).getDesksId()) {
                        int getFloorPosition = i;
                        if (getFloorPosition == floorPosition) {
                            floorFoundStatus = true;

                            break;
                            //myTeamBottomSheet.dismiss();
                        }
                    }
                }

            }

            if (!floorFoundStatus) {
                //Same building but different Floor
                doFindAndLoadFloorPosition(floorID, deskId);

            } else {
                //used to check default location
                defaultLocationcheck = 1;

                callInitView();
            }


        } else {

            doFindAndLoadFloorPosition(floorID, deskId);


        }

    }

    private void doFindAndLoadFloorPosition(int floorID, int deskId) {

        //SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);
        SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, floorID);

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(floorID);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                for (int i = 0; i < locateCountryResposeList.size(); i++) {

                    for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getDesks().size(); j++) {

                        if (deskId == locateCountryResposeList.get(i).getLocationItemLayout().getDesks().get(j).getDesksId()) {
                            SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_POSITION, i);
                            System.out.println("SelectedFloorPositionInLocate " + i);

                            //used to check default location
                            defaultLocationcheck = 1;

                            callInitView();
                            break;

                        }


                    }


                }


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

            }
        });


    }

    //New...
    public void checkVeichleReg() {

        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Boolean> call = apiService.getIsVehicleReg();
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                    if (response.body() != null && response.code() == 200) {
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

    private void getFloorAndDeskDetailsToPlaceUser(DAOTeamMember.DayGroup.CalendarEntry.Booking booking, RelativeLayout.LayoutParams relativeLayout, View perSonView, ImageView ivPerson) {


        int selectedFloorIdInTeam = booking.getLocationBuildingFloor().getFloorID();

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);

        //if(selectedFloorIdInTeam==parentId) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(selectedFloorIdInTeam);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                if (selectedFloorIdInTeam == parentId) {

                    for (int i = 0; i < locateCountryResposeList.size(); i++) {

                        for (int j = 0; j < locateCountryResposeList.get(i).getLocationItemLayout().getDesks().size(); j++) {
                            if (booking.getDeskId() == locateCountryResposeList.get(i).getLocationItemLayout().getDesks().get(j).getDesksId()) {
                                int getSelectTeamFloorPosition = i;

                                if (floorPosition == getSelectTeamFloorPosition) {

                                    for (String key : locateCountryResposeList.get(getSelectTeamFloorPosition).getItems().keySet()) {

                                        String[] words = key.split("_");

                                        int id = Integer.parseInt(words[0]);

                                        if (id == booking.getDeskId()) {
                                            List<String> coordinateList = new ArrayList<>();
                                            coordinateList = locateCountryResposeList.get(getSelectTeamFloorPosition).getItems().get(key);

                                            System.out.println("CoordinatesSlected " + coordinateList.get(0) + " " + coordinateList.get(1));

                                            relativeLayout.leftMargin = Integer.parseInt(coordinateList.get(0));
                                            relativeLayout.topMargin = Integer.parseInt(coordinateList.get(1));


                                            ivPerson.setLayoutParams(relativeLayout);

                                            //https://github.com/alexvasilkov/GestureViews/wiki/Usage#viewpager

                                           /* binding.zoomView.getController().getSettings()
                                                    .setZoomEnabled(true)
                                                    .setMaxZoom(120f);*/
                                            //.setGravity(Gravity.CENTER);


                                            // binding.firstLayout.setGravity(Gravity.CENTER);

                                            binding.firstLayout.addView(perSonView);


                                            binding.myTeamBookNearBy.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    bookNearByToMyTeam(booking.getDeskId(), locateCountryResposeList.get(0).getLocationItemLayout());
                                                }
                                            });


                                        }


                                    }
                                } else {
                                    Toast.makeText(getContext(), "Selected user is not avaliable in this floor", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    }

                }

                //checkSelectedAndLoadedFloor(locateCountryResposeList);

                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        /*}else {
            binding.locateProgressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(getContext(),"Selected user is not avaliable in this floor",Toast.LENGTH_LONG).show();
        }*/

    }

    private void bookNearByToMyTeam(int deskId, LocateCountryRespose.LocationItemLayout locationItemLayout) {

        /*for (int i = 0; i < deskStatusModelList.size(); i++) {

            System.out.println("AvaliableAllDesk "+deskStatusModelList.get(i).getKey()+" "+deskStatusModelList.get(i).getId()+" "+deskStatusModelList.get(i).getStatus());
        }*/

        boolean checkStatus = false;
        String selctedCode = "";

        for (int i = 0; i < locationItemLayout.getDesks().size(); i++) {

            if (deskId == locationItemLayout.getDesks().get(i).getDesksId()) {

                checkStatus = true;

            }

            //Should not same ID
            if (checkStatus && deskId != locationItemLayout.getDesks().get(i).getDesksId()) {

                for (int j = 0; j < deskStatusModelList.size(); j++) {

                    if (locationItemLayout.getDesks().get(i).getDesksId() == deskStatusModelList.get(j).getId()) {
                        if (deskStatusModelList.get(j).getStatus() == 1) {
                            selctedCode = locationItemLayout.getDesks().get(i).getDeskCode();
                            System.out.println("BookNearBy " + deskStatusModelList.get(j).getId());
                            checkStatus = false;
                            System.out.println("AvaliableAllDesk " + deskStatusModelList.get(j).getKey() + " " + deskStatusModelList.get(j).getId() + " " + deskStatusModelList.get(j).getStatus() + " " + deskStatusModelList.get(j).getCode());
                            //77_3 77 1 3

                            //callDeskBookingnBottomSheet(selctedCode, key, id, code, 0, 0, 1);
                            editLastEndTime = "";
                            callDeskBookingnBottomSheet(selctedCode, deskStatusModelList.get(j).getKey(), deskStatusModelList.get(j).getId(), deskStatusModelList.get(j).getCode(), 0, 0, 1, 0);
                            //maddy-10 79_3 79 3
                        }
                    }

                }

            }


        }

        /*for (String key : desks.keySet()) {

            String[] words = key.split("_");

            int id = Integer.parseInt(words[0]);

            if (deskId == id) {

                List<String> stringList=desks.get(key);
            }

        }*/
    }

    private void getLocateAmenitiesFilterData(boolean calledFromFilter) {
        if (Utils.isNetworkAvailable(getContext())) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<AmenitiesResponse>> call = apiService.getAmenities();
            call.enqueue(new Callback<List<AmenitiesResponse>>() {
                @Override
                public void onResponse(Call<List<AmenitiesResponse>> call, Response<List<AmenitiesResponse>> response) {

                    //Used to show Amenities in Meeting Booking BottomSheet
                    //List<AmenitiesResponse> amenitiesResponseList=response.body();
                    List<AmenitiesResponse> amenitiesResponseList = response.body();
                    amenitiesListToShowInMeetingRoomList = amenitiesResponseList;

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    //If true which is called from filter so call bottomsheet
                    if (calledFromFilter) {

                        meetingAmenityStatusList.clear();
                        callLocateFilterBottomSheet(amenitiesResponseList);
                    }


                }

                @Override
                public void onFailure(Call<List<AmenitiesResponse>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), getResources().getString(R.string.enable_internet));
        }


    }

    public void setLanguage() {

        logoinPage = getLoginScreenData(getContext());
        appKeysPage = getAppKeysPageScreenData(getContext());
        resetPage = getResetPasswordPageScreencreenData(getContext());
        actionOverLays = getActionOverLaysPageScreenData(getContext());
        bookindata = getBookingPageScreenData(getContext());
        global = getGlobalScreenData(getContext());
        meetingRoomsLanguage = getMeetingRoomsPageScreenData(getContext());

        binding.searchLocate.setText(appKeysPage.getChooseLocation());
        binding.tvStartLocate.setText(appKeysPage.getStart());
        binding.tvLocateEndTime.setText(appKeysPage.getEnd());
        binding.avaliableTxt.setText(global.getAvailable());
        binding.unavaliableTxt.setText(global.getUnavailable());
        binding.bookedByMeTxt.setText(global.getBookedByMe());
        binding.bookedTxt.setText(appKeysPage.getBooked());
        binding.byRequestTxt.setText(appKeysPage.getByRequest());


        //tvPMOOffice.setText(appKeysPage);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SessionHandler.getInstance().saveInt(getContext(), AppConstants.FLOOR_ICON_BLINK, 0);

    }


    public void checkTeamsCheckBox() {
        if (Utils.isNetworkAvailable(getContext())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<String> call = apiService.getSettingData("GraphAPIEnabled");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        if (response.body().equalsIgnoreCase("true")) {
                            teamsCheckBoxStatus = true;
                        } else {
                            teamsCheckBoxStatus = false;
                        }
                    } else if (response.code() == 403) {
                        teamsCheckBoxStatus = false;
                    } else {
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
    @Override
    public void clickCount(ArrayList<DataModel> mList, int pos) {

        if (mList != null && mList.size() > 0) {

            ArrayList<ValuesPOJO> nestedList = mList.get(pos).getNestedList();

            if (nestedList != null && nestedList.size() > 0) {

                ArrayList<ValuesPOJO> inComingList = new ArrayList<>();
                inComingList = (ArrayList<ValuesPOJO>) nestedList.stream().filter(val -> val.isChecked()).collect(Collectors.toList());

                if (inComingList != null && inComingList.size() > 0) {

                    if (filterTotalSize != null) {
                        filterTotalSize.setText("(" + String.valueOf(inComingList.size()) + ")");
                    }

                } else {
                    if (filterTotalSize != null) {
                        filterTotalSize.setText("(0)");
                    }
                }

            }

        }

    }



    //New...
    private void setIconColor(ImageView icon,int color) {

        try {
            DrawableCompat.setTint(
                    DrawableCompat.wrap(icon.getDrawable()),
                    ContextCompat.getColor(getActivity(),color)
            );
        }catch (Exception e){

        }

    }

    private void countryColor() {

        setIconColor(img_bsCountry,R.color.blue);
        setIconColor(img_bsState,R.color.black);
        setIconColor(img_bsStreet,R.color.black);

    }

    private void cityColor() {

        setIconColor(img_bsCountry,R.color.black);
        setIconColor(img_bsState,R.color.blue);
        setIconColor(img_bsStreet,R.color.black);

    }

    private void buildingcolor() {

        setIconColor(img_bsCountry,R.color.black);
        setIconColor(img_bsState,R.color.black);
        setIconColor(img_bsStreet,R.color.blue);

    }

    private void floorColor() {
        setIconColor(img_bsCountry,R.color.black);
        setIconColor(img_bsState,R.color.black);
        setIconColor(img_bsStreet,R.color.black);
    }

}
