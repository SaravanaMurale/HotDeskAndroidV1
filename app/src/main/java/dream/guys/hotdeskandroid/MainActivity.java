package dream.guys.hotdeskandroid;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;

import android.app.Dialog;
import android.content.Intent;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.adapter.SearchRecyclerAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityMainBinding;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.ui.chat.FreshChatActivity;
import dream.guys.hotdeskandroid.ui.locate.LocateFragment;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.ui.login.SignInActivity;
import dream.guys.hotdeskandroid.ui.teams.ShowProfileActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SearchRecyclerAdapter.GlobalSearchOnClickable, ParticipantNameShowAdapter.OnParticipantSelectable {
    ActivityMainBinding binding;
    BottomNavigationView navView;
    NavController navController;


    private ScaleGestureDetector mScaleGestureDetector;
    GestureDetector gestureDetector;
    private float mScale = 1f;

    LinearLayout searchLayout;
    LinearLayoutManager linearLayoutManager;
    Dialog dialog;
    SearchRecyclerAdapter searchRecyclerAdapter;
    List<GlobalSearchResponse.Results> list = new ArrayList<>();

    BottomSheetDialog bookEditBottomSheet;
    BottomSheetDialog roomBottomSheet;
    TextView startTime,endTime,repeat,date,deskRoomName,locationAddress;
    int teamId=0,teamMembershipId=0,selectedDeskId=0,selectedicon=0;

    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList=new ArrayList<>();
    //room bookings
    ChipGroup participantChipGroup;
    ParticipantDetsilResponse participantDetsilResponse;
    List<ParticipantDetsilResponse> chipList = new ArrayList<>();
    String calSelectedDate="";
    String calSelectedMont="";
    List<ParkingSpotModel> parkingSpotModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new Dialog(this);
        if (SessionHandler.getInstance().getBoolean(MainActivity.this,AppConstants.LOGIN_CHECK) ){
//            Toast.makeText(this, "toast daaaa", Toast.LENGTH_SHORT).show();
            uiInit();
            nightModeConfig();

            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            binding.searchRecycler.setLayoutManager(linearLayoutManager);
            binding.searchRecycler.setHasFixedSize(true);
            searchRecyclerAdapter=new SearchRecyclerAdapter(getApplicationContext(),MainActivity.this,list,this);
            binding.searchRecycler.setAdapter(searchRecyclerAdapter);
        } else {
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            intent.putExtra("qr_deep_link",true);
            startActivity(intent);
        }

        // homeBookingListAdapter=new HomeBookingListAdapter(getContext(), getActivity(), recyclerModelArrayList);

        binding.serachBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0){
                    list.clear();
                    searchRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(binding.serachBar.getWindowToken(), 0);
                callSearchRecyclerData(s.toString());
            }
        });
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.serachBar.clearComposingText();
                binding.searchLayout.setVisibility(View.GONE);
            }
        });

        binding.fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, FreshChatActivity.class);
                startActivity(intent);

            }
        });

        gestureDetector = new GestureDetector(this, new GestureListener());

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                System.out.println("DectorValue"+detector.getScaleFactor());

                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                System.out.println("ZoomMuraliScaleValue"+mScale);
                System.out.println("ZoomScale"+scale);

                if(mScale>0) {

                    if (mScale > 10f)
                        mScale = 10f;

                    ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                    scaleAnimation.setDuration(0);
                    scaleAnimation.setFillAfter(true);
                    /*ScrollView layout = findViewById(R.id.scrollView);
                    if (layout != null) {
                        layout.startAnimation(scaleAnimation);
                    }*/

                }else {
                    System.out.println("You Cant zoom more than this");
                }
                return true;

            }
        });

    }

    private void deepLinking() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if(appLinkData != null) {
            AppConstants.FIRSTREFERAL=false;

            //NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
//            navController.navigate(R.id.navigation_book);
            String data1= appLinkData.getQueryParameter("typekey"); // you will get the value "value1" from application 1

            navView.setSelectedItemId(R.id.navigation_book);

        }

    }
    private void getRoomlist(EditBookingDetails editBookingDetails) {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(this);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserAllowedMeetingResponse>> call = apiService.userAllowedMeetings();
            call.enqueue(new Callback<List<UserAllowedMeetingResponse>>() {
                @Override
                public void onResponse(Call<List<UserAllowedMeetingResponse>> call, Response<List<UserAllowedMeetingResponse>> response) {
                    userAllowedMeetingResponseList=response.body();

                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                    editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<UserAllowedMeetingResponse>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                    editBookingUsingBottomSheet(editBookingDetails,2,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }

    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails, int dskRoomParkStatus, int position,String newEditStatus) {

        roomBottomSheet = new BottomSheetDialog(MainActivity.this, R.style.AppBottomSheetDialogTheme);
        roomBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(this))));

        startTime = roomBottomSheet.findViewById(R.id.start_time);
        endTime = roomBottomSheet.findViewById(R.id.end_time);
        repeat = roomBottomSheet.findViewById(R.id.repeat);
        deskRoomName=roomBottomSheet.findViewById(R.id.tv_desk_room_name);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        date=roomBottomSheet.findViewById(R.id.date);
        TextView back=roomBottomSheet.findViewById(R.id.editBookingBack);
        TextView select=roomBottomSheet.findViewById(R.id.select_desk_room);
        TextView continueEditBook=roomBottomSheet.findViewById(R.id.editBookingContinue);
        TextView tvComments=roomBottomSheet.findViewById(R.id.tv_comments);
        EditText commentRegistration=roomBottomSheet.findViewById(R.id.ed_registration);
        RelativeLayout repeatBlock=roomBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=roomBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=roomBottomSheet.findViewById(R.id.rl_teams_layout);
        LinearLayout statusCheckLayout=roomBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=roomBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=roomBottomSheet.findViewById(R.id.capacity_layout);

        ChipGroup chipGroup = roomBottomSheet.findViewById(R.id.list_item);

        if (editDeskBookingDetails.getDeskStatus() == 1){
            startTime.setTextColor(this.getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(this.getResources().getColor(R.color.figmaGrey));
            select.setTextColor(this.getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.GONE);
//            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(this.getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(this.getResources().getColor(R.color.figmaBlue));
            select.setTextColor(this.getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
//            chipGroup.setVisibility(View.VISIBLE);
        } else {
            startTime.setTextColor(this.getResources().getColor(R.color.figmaBlue));
            endTime.setTextColor(this.getResources().getColor(R.color.figmaBlue));
            statusCheckLayout.setVisibility(View.GONE);
//            chipGroup.setVisibility(View.GONE);
        }
        select.setVisibility(View.GONE);
        if (dskRoomParkStatus == 1) {
            repeatBlock.setVisibility(View.VISIBLE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");
            capacitylayout.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            deskRoomName.setText(editDeskBookingDetails.getDeskCode());
            selectedDeskId=editDeskBookingDetails.getDesktId();

        }else if (dskRoomParkStatus==2) {
            llDeskLayout.setVisibility(View.VISIBLE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.VISIBLE);
            deskRoomName.setText(""+editDeskBookingDetails.getRoomName());
            selectedDeskId = editDeskBookingDetails.getMeetingRoomtId();
//            if (userAllowedMeetingResponseList.size() > 0){
////                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
//                deskRoomName.setText(""+userAllowedMeetingResponseList.get(0).getName());
//                selectedDeskId = userAllowedMeetingResponseList.get(0).getId();
//                locationAddress.setText(""+userAllowedMeetingResponseList.get(0).getLocationMeeting().getName());
//            }
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
            deskRoomName.setText(""+editDeskBookingDetails.getParkingSlotCode());
            selectedDeskId = editDeskBookingDetails.getParkingSlotId();

//            System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
//            if (parkingSpotModelList.size() > 0){
////                System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
//                deskRoomName.setText(""+parkingSpotModelList.get(0).getCode());
//                selectedDeskId = parkingSpotModelList.get(0).getId();
//                locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());
//            }

        }

        startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
        endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
        date.setText(""+Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));

//        System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
//        Log.d(TAG, "editBookingUsingBottomSheet: chip"+editDeskBookingDetails.getAmenities().size());
        if (editDeskBookingDetails.getAmenities()!=null){
            for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                Chip chip = new Chip(this);
                chip.setId(i);
                chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                chip.setChipBackgroundColorResource(R.color.figmaGrey);
                chip.setCloseIconVisible(false);
                chip.setTextColor(this.getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                chipGroup.addView(chip);
            }
        }

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1 && editDeskBookingDetails.getDeskStatus() != 2)
                    Utils.bottomSheetTimePicker(MainActivity.this,MainActivity.this,startTime,"Start Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
//                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus() != 1)
                    Utils.bottomSheetTimePicker(MainActivity.this,MainActivity.this,endTime,"End Time",Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
//                    Utils.popUpTimePicker(getActivity(),endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                repeatBottomSheetDialog();
            }
        });
        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedicon==1 && newEditStatus.equalsIgnoreCase("new")) {
                    callMeetingRoomBookingBottomSheet(editDeskBookingDetails, startTime, endTime, selectedDeskId, deskRoomName.getText().toString(), false);
                }
                if (selectedicon==1) {
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
                    jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00Z");
                    switch (dskRoomParkStatus){
                        case 1:
                            jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(MainActivity.this,AppConstants.TEAM_ID));
                            jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(MainActivity.this,AppConstants.TEAMMEMBERSHIP_ID));
                            if (!commentRegistration.getText().toString().isEmpty() &&
                                    !commentRegistration.getText().toString().equalsIgnoreCase(""))
                                jsonChangesObject.addProperty("comments",commentRegistration.getText().toString());

//                            jsonChangesObject.addProperty("teamDeskId",editDeskBookingDetails.getDesktId());
                            if (newEditStatus.equalsIgnoreCase("request")){
                                jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                jsonChangesObject.addProperty("usageTypeId", "7");
                                jsonChangesObject.addProperty("typeOfCheckIn", "7");
                            }else
                                jsonChangesObject.addProperty("teamDeskId",editDeskBookingDetails.getDesktId());


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
                                jsonChangesObject.addProperty("bookedForUser",SessionHandler.getInstance().getInt(MainActivity.this,AppConstants.USER_ID));
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
                    if (newEditStatus.equalsIgnoreCase("new")){
                        jsonChangesObject.addProperty("usageTypeId", "2");
                        jsonChangesObject.addProperty("timeZoneId", "India Standard Time");
                    }
                    if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()).equalsIgnoreCase(startTime.getText().toString()) || newEditStatus.equalsIgnoreCase("new")){
                        jsonChangesObject.addProperty("from", "2000-01-01T"+Utils.convert12HrsTO24Hrs(startTime.getText().toString())+":00.000Z");
                    }if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()).equalsIgnoreCase(endTime.getText().toString()) || newEditStatus.equalsIgnoreCase("new")){
                        jsonChangesObject.addProperty("to","2000-01-01T"+Utils.convert12HrsTO24Hrs(endTime.getText().toString())+":00.000Z");
                    }

                    jsonInnerObject.add("changes",jsonChangesObject);
                    jsonChangesetArray.add(jsonInnerObject);

                    jsonOuterObject.add("changesets", jsonChangesetArray);
                    jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

                    System.out.println("json un"+jsonOuterObject.toString());

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
//                callDeskBottomSheetDialog();
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
    //Room Booking Methods
    private void callMeetingRoomBookingBottomSheet(EditBookingDetails editDeskBookingDetails, TextView startTime, TextView endTime, int meetingRoomId, String meetingRoomName, boolean isRequest) {

        TextView startRoomTime, endTRoomime, editRoomBookingContinue, editRoomBookingBack, tvMeetingRoomDescription, roomTitle;
        EditText etParticipants, etSubject, etComments;
        RecyclerView rvParticipant;
        LinearLayoutManager linearLayoutManager;
        RelativeLayout startTimeLayout, endTimeLayout;


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
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

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
//
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
        dialog= ProgressDialog.showProgressBar(this);
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
                    Utils.showCustomAlertDialog(MainActivity.this, "Booking Not Successfull");
                }

                ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });


    }

    public void editBookingCall(JsonObject data,int position,int dskRoomStatus,String newEditDelete) {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(this);
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
                            if (newEditDelete.equalsIgnoreCase("new"))
                                openCheckoutDialog("Booking Successful",dskRoomStatus);
                            else if (newEditDelete.equalsIgnoreCase("edit"))
                                openCheckoutDialog("Booking Updated",dskRoomStatus);
                            else
                                openCheckoutDeleteDialog("Booking Deleted",dskRoomStatus);

                            switch (dskRoomStatus){
                                case 1:
//                                    tabToggleViewClicked(0);
                                    break;
                                case 2:
//                                    tabToggleViewClicked(1);
                                    break;
                                case 3:
//                                    tabToggleViewClicked(2);
                                    break;
                                default:
                                    break;
                            }

//                            openCheckoutDialog("Booking Updated");
                        }else {
                            Utils.showCustomAlertDialog(MainActivity.this,"Booking Not Updated "+response.body().getResultCode().toString());
                        }
                    }else if (response.code() == 500){
                        Utils.showCustomAlertDialog(MainActivity.this,"500 Response");
                    }else if (response.code() == 401){
                        Utils.showCustomTokenExpiredDialog(MainActivity.this,"401 Error Response");
                        SessionHandler.getInstance().saveBoolean(MainActivity.this, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Response Failure", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "fail Bala"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("resps"+t.getMessage());
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(MainActivity.this, "Please Enable Internet");
        }
    }
    private void openCheckoutDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(this);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(""+mesg);
        if (dskRoomStatus==1){

        }
        else if(dskRoomStatus==3){

        }
        else{
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
        Dialog popDialog = new Dialog(this);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        ImageView ivChecout = popDialog.findViewById(R.id.ivCheckoutSuccess);
//        ivChecout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_red));
        ivChecout.setImageTintList(ContextCompat.getColorStateList(this,R.color.figma_red));
        dialogMsg.setText(""+mesg);
        if (dskRoomStatus==1)
            bookEditBottomSheet.dismiss();
        else if(dskRoomStatus==3)
            bookEditBottomSheet.dismiss();
        else{
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

    private void getParkingSpotList(String id, EditBookingDetails editBookingDetails,String newEdit) {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(this);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ParkingSpotModel>> call = apiService.getParkingSpotModels(id);
            call.enqueue(new Callback<List<ParkingSpotModel>>() {
                @Override
                public void onResponse(Call<List<ParkingSpotModel>> call, Response<List<ParkingSpotModel>> response) {
                    parkingSpotModelList=response.body();

                    ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                        editBookingUsingBottomSheet(editBookingDetails,3,0,"new");

                }

                @Override
                public void onFailure(Call<List<ParkingSpotModel>> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                    editBookingUsingBottomSheet(editBookingDetails,3,0,"new");
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }

    }

    private void callSearchRecyclerData(String searchText) {
        if (Utils.isNetworkAvailable(this)) {
//            dialog= ProgressDialog.showProgressBar(this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GlobalSearchResponse> call = apiService.getGlobalSearchData(40,searchText);
            call.enqueue(new Callback<GlobalSearchResponse>() {
                @Override
                public void onResponse(Call<GlobalSearchResponse> call, Response<GlobalSearchResponse> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                        list.clear();
                        if (response.body().getResults()!=null)
                            list.addAll(response.body().getResults());
                        //Toast.makeText(MainActivity.this, "ls "+list.size(), Toast.LENGTH_SHORT).show();
                        binding.searchRecycler.setVisibility(View.VISIBLE);
                        //Toast.makeText(MainActivity.this, "200"+searchText, Toast.LENGTH_SHORT).show();
                        searchRecyclerAdapter.notifyDataSetChanged();
                        Log.d("Search", "onResponse: 200");
                    }else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);

                    } else {
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                        Log.d("Search", "onResponse: else");
                    }

                }
                @Override
                public void onFailure(Call<GlobalSearchResponse> call, Throwable t) {
                    //Toast.makeText(MainActivity.this, "on fail", Toast.LENGTH_SHORT).show();
//                    ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    public void showSearch(){
        binding.searchLayout.setVisibility(View.VISIBLE);
    }

    private void nightModeConfig() {
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
    }

    private void uiInit() {
        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);
        deepLinking();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        mScaleGestureDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
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


    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void getFloorCoordinatesInMain(List<List<Integer>> coordinateList, LinearLayout secondLayout){
        List<Point> pointList=new ArrayList<>();
        System.out.println("CoordinateSize" + coordinateList.size());

        for (int i = 0; i < coordinateList.size(); i++) {

            System.out.println("CoordinateData" + i + "position" + "size " + coordinateList.get(i).size());

            Point point = new Point(coordinateList.get(i).get(0) + 40, coordinateList.get(i).get(1) + 20);
            pointList.add(point);

        }

        if (pointList.size() > 0) {
            MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

            secondLayout.addView(myCanvasDraw);

        }

    }

    @Override
    public void onClickGlobalSearch(GlobalSearchResponse.Results results, View v) {


        if(results.getEntityType()==1){
            //Show Person Details

            Calendar startDate = Calendar.getInstance();

            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String currendate = String.valueOf(year + "-" + (month + 1) + "-" + day);

            try {
                currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DAOTeamMember daoTeamMember=new DAOTeamMember();
            daoTeamMember.setFirstName(results.getName());
            daoTeamMember.setUserId(results.getId());


            Intent intent = new Intent(MainActivity.this, ShowProfileActivity.class);
            intent.putExtra(AppConstants.USER_CURRENT_STATUS,daoTeamMember);
            intent.putExtra("DATE",currendate);
            startActivity(intent);

        }else if(results.getEntityType()==3|| results.getEntityType()==5 || results.getEntityType()==4){
            //Set Floor In Locate
            SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, results.getCurrentLocation().getParentLocationId());
            SessionHandler.getInstance().save(getContext(),AppConstants.FULLPATHLOCATION,results.getFullLocationPath());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call=apiService.getCountrysChild(results.getCurrentLocation().getParentLocationId());
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposeList=response.body();

                    for (int i = 0; i <locateCountryResposeList.size() ; i++) {

                        if(results.getCurrentLocation().getId()==locateCountryResposeList.get(i).getLocateCountryId()){

                            SessionHandler.getInstance().saveInt(MainActivity.this, AppConstants.FLOOR_POSITION,i);
                            binding.searchRecycler.setVisibility(View.GONE);
                            binding.serachBar.setText("");
                            binding.serachBar.clearComposingText();
                            binding.searchLayout.setVisibility(View.GONE);
                            binding.navView.setSelectedItemId(R.id.navigation_locate);

                        }/*else {
                        Utils.toastMessage(MainActivity.this,"Selected Floor Is Not Avaliable");
                    }*/



                    }

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                }
            });
        }

    }
    public void callLocateFragmentFromHomeFragment(){
        binding.navView.setSelectedItemId(R.id.navigation_locate);
    }
}