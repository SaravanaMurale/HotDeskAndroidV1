package dream.guys.hotdeskandroid.controllers;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ParticipantNameShowAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.ParticipantDetsilResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
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

public class EditMeetingRoomController implements ParticipantNameShowAdapter.OnParticipantSelectable{
    String TAG="DeskController";
    String isFrom;
    Activity activityContext;
    Context context;
    Dialog dialog;
    public int myTeamId = 0;

    boolean startDisabled = false;
    boolean endDisabled = false;
    boolean selectDisabled = false;
    boolean tvTeamNameDisabled = false;
    boolean isGlobalLocationSetUP = false;
    int selectedTeamId=0;
    public int selectedTeamAutoApproveStatus = 0;
    int selectedRoomId=0;

    int selectedicon=2;
    BottomSheetDialog roomBottomSheet;

    TextView startTime,endTime,repeat,date,deskRoomName,locationAddress,continueEditBook;
    TextView country, state, street, floor, back, bsApply,deskStatusText,deskStatusDot;
    TextView tvRepeat, tvTeamName,tvcapacityCount;
    EditText bsGeneralSearch;
    LinearLayoutManager linearLayoutManager;
    ChipGroup chipGroup,participantChipGroup;
    
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;
   
    List<UserAllowedMeetingResponse> allMeetingRoomList=new ArrayList<>();
    List<AmenitiesResponse> amenitiesList = new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseFilterList=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdated=new ArrayList<>();
    List<UserAllowedMeetingResponse> userAllowedMeetingResponseListUpdatedFilterList=new ArrayList<>();
    List<AmenitiesResponse> amenitiesListToShowInMeetingRoomList=new ArrayList<>();
    List<MeetingListToEditResponse.Attendees> attendeesListForEdit;

    List<ParticipantDetsilResponse> chipList = new ArrayList<>();
    ParticipantDetsilResponse participantDetsilResponse;

    String calSelectedDate;
    boolean isEditable;
    public EditMeetingRoomController(String isFrom, Activity activityContext, Context context,
                                     String calSelectedDate, int selectedRoomId,EditBookingDetails editDeskBookingDetails,boolean isEditable) {
        this.isFrom = isFrom;
        this.activityContext = activityContext;
        this.context = context;
        this.calSelectedDate = calSelectedDate;
        this.selectedRoomId = selectedRoomId;
        this.isEditable = isEditable;

        //New...
        if (chipList!=null)
            chipList.clear();
        if(attendeesListForEdit!=null && attendeesListForEdit.size()>0){
            attendeesListForEdit.clear();
        }

        setLanguage();
        getAmenities();
        myTeamId = SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID);

        callAmenitiesListForMeetingRoom(editDeskBookingDetails,
                editDeskBookingDetails.getEditStartTTime(),
                editDeskBookingDetails.getEditEndTime(),
                editDeskBookingDetails.getDate(),
                editDeskBookingDetails.getMeetingRoomtId(),
                0,"edit");
    }
    public void setLanguage(){

        logoinPage = Utils.getLoginScreenData(context);
        appKeysPage = Utils.getAppKeysPageScreenData(context);
        resetPage = Utils.getResetPasswordPageScreencreenData(context);
        actionOverLays = Utils.getActionOverLaysPageScreenData(context);
        bookindata = Utils.getBookingPageScreenData(context);
        global=Utils.getGlobalScreenData(context);
        meetingRoomsLanguage=Utils.getMeetingRoomsPageScreenData(context);



        //binding.tvPMOOffice.setText(appKeysPage);
        //binding.searchGlobal.setText(appKeysPage.getChooseLocation());
        //System.out.println("lang check global" + global);
        //System.out.println("lang check bookindata" + bookindata);
        //System.out.println("lang check" + appKeysPage);
        //System.out.println("lang check" + appKeysPage.getStart());
        //System.out.println("lang check" + binding.tvStartLocate);
//        binding.tvStartLocate.setText(appKeysPage.getStart());
//        binding.tvLocateEndTime.setText(appKeysPage.getEnd());


    }

    private void getAmenities() {
        if (Utils.isNetworkAvailable(activityContext)) {
//            dialog= ProgressDialog.showProgressBar(context);
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
                            Utils.showCustomTokenExpiredDialog(activityContext,"Token Expired");
                            SessionHandler.getInstance().saveBoolean(activityContext, AppConstants.LOGIN_CHECK,false);
                        }
                    } catch (Exception exception){

                    }

                }
                @Override
                public void onFailure(Call<List<AmenitiesResponse>> call, Throwable t) {

                }
            });
        } else {
            Utils.toastMessage(activityContext, "Please Enable Internet");
        }
    }

    private void callAmenitiesListForMeetingRoom(EditBookingDetails editDeskBookingDetails, String editStartTTime,
                                                 String editEndTime,
                                                 Date date,
                                                 int calId, int position, String newEditStatus) {
        if (Utils.isNetworkAvailable(activityContext)) {
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
//                            Toast.makeText(activityContext, "dasd", Toast.LENGTH_SHORT).show();

                            List<Integer> amenitiesIntList =new ArrayList<>();
                            List<String> amenitiesStringList =new ArrayList<>();
                            goo:
                            for (int i=0; i < allMeetingRoomList.size(); i++) {
                                if (allMeetingRoomList.get(i).getId() == calId) {
                                    editDeskBookingDetails.setCapacity(""+allMeetingRoomList.get(i).getNoOfPeople());
                                    editDeskBookingDetails.setLocationAddress(""+allMeetingRoomList.get(i).getLocationMeeting().getName());

                                    team:
                                    for (int x=0; x<allMeetingRoomList.get(i).getTeams().size(); x++){
                                        if(allMeetingRoomList.get(i).getTeams().get(x).getId() == myTeamId){
                                            editDeskBookingDetails.setMeetingRoomStatus(1);
                                            break team;
                                        } else {
                                            editDeskBookingDetails.setMeetingRoomStatus(2);
                                        }
                                    }
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


//                        Utils.toastMessage(activityContext,"welcom bala "+amenitiesStringList.size());
                            editDeskBookingDetails.setAmenities(amenitiesStringList);
//                        Log.d(TAG, "onResponse: amenitySize"+editDeskBookingDetails.getAmenities().size());

                            editBookingUsingBottomSheet(editDeskBookingDetails,2,position,newEditStatus);

                        } else if(response.code()==401){
                            //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(context,dialog);
                            SessionHandler.getInstance().saveBoolean(activityContext, AppConstants.LOGIN_CHECK,false);
                            Utils.showCustomTokenExpiredDialog(activityContext,"Token Expired");
                        } else {
//                            Toast.makeText(activityContext, "elseeee", Toast.LENGTH_SHORT).show();
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
            Utils.toastMessage(activityContext, "Please Enable Internet");
        }
    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails, int dskRoomParkStatus, int position,String newEditStatus) {
        endDisabled=false;
        startDisabled=false;
        selectDisabled=false;

        roomBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        roomBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_calendar_bottom_sheet_edit_booking,
//                new RelativeLayout(context))));
        View view = View.inflate(context, R.layout.dialog_calendar_bottom_sheet_edit_booking, null);
        roomBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels-150);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //Language
        TextView tv_start=roomBottomSheet.findViewById(R.id.tv_start);
        TextView tv_end=roomBottomSheet.findViewById(R.id.tv_end);
        TextView tv_comment=roomBottomSheet.findViewById(R.id.tv_comment);
        TextView tv_repeat=roomBottomSheet.findViewById(R.id.tv_repeat);
        TextView tvDelete = roomBottomSheet.findViewById(R.id.delete_text);
        tvDelete.setVisibility(View.VISIBLE);
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

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBookingCall(editDeskBookingDetails);
            }
        });

        startTime = roomBottomSheet.findViewById(R.id.start_time);
        endTime = roomBottomSheet.findViewById(R.id.end_time);

        deskRoomName=roomBottomSheet.findViewById(R.id.tv_desk_room_name);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        //New...
        locationAddress.setVisibility(View.VISIBLE);

        date=roomBottomSheet.findViewById(R.id.date);
        TextView title=roomBottomSheet.findViewById(R.id.title);
        TextView checkInDate=roomBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=roomBottomSheet.findViewById(R.id.showCheckInDate);

        TextView select=roomBottomSheet.findViewById(R.id.select_desk_room);

        TextView tvComments=roomBottomSheet.findViewById(R.id.tv_comments);
        EditText edComments=roomBottomSheet.findViewById(R.id.comments);
        EditText commentRegistration=roomBottomSheet.findViewById(R.id.ed_registration);
        EditText edRegistration=roomBottomSheet.findViewById(R.id.et_registration_num);
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
//        showcheckInDate.setText(Utils.showBottomSheetDate(calSelectedDate));
        checkInDate.setText("");

        if (editDeskBookingDetails.getDeskStatus() == 1){
            startTime.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            select.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=true;
            endDisabled=true;
            selectDisabled=true;
//            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(activityContext.getResources().getColor(R.color.figmaBlueText));
            endTime.setTextColor(activityContext.getResources().getColor(R.color.figmaBlueText));
            select.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=false;
            endDisabled=false;
            selectDisabled=true;
//            chipGroup.setVisibility(View.VISIBLE);
        } else {
            startTime.setTextColor(activityContext.getResources().getColor(R.color.figmaBlueText));
            endTime.setTextColor(activityContext.getResources().getColor(R.color.figmaBlueText));
            statusCheckLayout.setVisibility(View.VISIBLE);
            startDisabled=false;
            endDisabled=false;
//            chipGroup.setVisibility(View.GONE);
        }

        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditStartTTime()
        ) && newEditStatus.equalsIgnoreCase("edit")) {
            /*startTime.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            select.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            startDisabled=true;
            selectDisabled=true;*/
        }
        if (Utils.compareTwoDate(editDeskBookingDetails.getDate(), Utils.getCurrentDate())==2
                && Utils.compareTimeIfCheckInEnable(Utils.getCurrentTime(),
                editDeskBookingDetails.getEditEndTime()
        ) && newEditStatus.equalsIgnoreCase("edit")) {
            startTime.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
            startDisabled=true;

            endTime.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
//            select.setTextColor(activityContext.getResources().getColor(R.color.figmaGrey));
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
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_red));
                    continueEditBook.setVisibility(View.GONE);
                } else if(selectedTeamAutoApproveStatus != 2) {
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
                    continueEditBook.setVisibility(View.VISIBLE);
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
                    continueEditBook.setVisibility(View.VISIBLE);
                }
            } else{
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
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

        if (dskRoomParkStatus == 1) {

        }else if (dskRoomParkStatus==2) {
            if(editDeskBookingDetails.getMeetingRoomStatus() == 2){
                deskStatusText.setText("Available For Request");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
            } else {
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
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

            /*if(teamsCheckBoxStatus)
                teamsBlock.setVisibility(View.VISIBLE);
            else
                teamsBlock.setVisibility(View.GONE);
*/
            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.VISIBLE);
            tvcapacityCount.setText(editDeskBookingDetails.getCapacity());
            if (userAllowedMeetingResponseListUpdated.size() > 0) {
//                //System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseListUpdated.get(0).getName());
                selectedRoomId = userAllowedMeetingResponseListUpdated.get(0).getId();
                locationAddress.setText(""+userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getName());
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

        }

        //Logic for start time and end time of DESK and MEETING ROOM
        if(dskRoomParkStatus==2) {
            if(Utils.compareTwoDate(editDeskBookingDetails.getDate(),Utils.getCurrentDate())==2){
//                startTime.setText(Utils.convert24HrsTO12Hrs(Utils.currentTimeWithExtraMins(2)));
                startTime.setText(Utils.setStartNearestThirtyMinToMeeting(Utils.getCurrentTime()));
//                endTime.setText(Utils.setStartNearestFiveMinToMeeting(Utils.currentTimeWithExtraMins(32)));
                endTime.setText(Utils.setStartNearestThirtyMinToMeeting(startTime.getText().toString()));
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

        Log.d(TAG, "editBookingUsingBottomSheet: "+editDeskBookingDetails.getAmenities().size());
        if (editDeskBookingDetails.getAmenities()!=null)
            //System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
            if (editDeskBookingDetails.getAmenities()!=null){
                for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                    ShapeAppearanceModel shapeAppearanceModel= new ShapeAppearanceModel()
                            .toBuilder()
                            .setAllCorners(CornerFamily.ROUNDED,15)
                            .build();
                    Chip chip = new Chip(context);
                    chip.setId(i);
                    chip.setTextAppearance(R.style.chipText);
                    chip.setShapeAppearanceModel(shapeAppearanceModel);
                    chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                    chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                    chip.setCloseIconVisible(false);
                    chip.setTextColor(context.getResources().getColor(R.color.figmaBlack));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                    chipGroup.addView(chip);
                }
            }

        showcheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetDatePicker(context, activityContext, "", "", checkInDate,showcheckInDate);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!startDisabled){
                    if(dskRoomParkStatus==2){
                        Utils.bottomSheetTimePickerMeetingRoom(context,
                                activityContext,startTime,endTime,"Start Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                    } else {

                        if (dskRoomParkStatus == 1
                                && newEditStatus.equalsIgnoreCase("edit")){
                            if (editDeskBookingDetails.getUsageTypeId()==2
                                    && editDeskBookingDetails.getRequestedTeamId()>0) {
                                Utils.bottomSheetTimePicker24Hrs(context,activityContext,startTime,"Start Time",
                                        Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                            } else {

                                Utils.bottomSheetTimePicker24Hrs(context,activityContext,startTime,"Start Time",
                                        Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
                            }
                        } else
                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);

/*
                        if (editDeskBookingDetails.getDeskBookingType()!=null
                                && editDeskBookingDetails.getDeskStatus() != 1
                                && editDeskBookingDetails.getDeskStatus() != 2
                                && editDeskBookingDetails.getDeskBookingType().equalsIgnoreCase("req"))
                            Utils.bottomSheetTimePicker(context,activityContext,startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
*/


                    }
                }

//                Toast.makeText(context, "fsf"+editDeskBookingDetails.getRequestedTeamDeskId(), Toast.LENGTH_SHORT).show();

//                    Utils.popUpTimePicker(activityContext,startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!endDisabled){
                    if (dskRoomParkStatus == 1
                            && newEditStatus.equalsIgnoreCase("edit")){
                        if (editDeskBookingDetails.getUsageTypeId()==2
                                && editDeskBookingDetails.getRequestedTeamId()>0) {

                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                        } else {

                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
                        }
                    } else
                        Utils.bottomSheetTimePicker24Hrs(context,activityContext,endTime,"End Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);

                }
//                    Utils.popUpTimePicker(activityContext,endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (selectedicon==2 && newEditStatus.equalsIgnoreCase("new")){

                } else if (selectedicon==2) {
                    if (newEditStatus.equalsIgnoreCase("request")) {

                    } else if(newEditStatus.equalsIgnoreCase("edit")){
                        if(editDeskBookingDetails.getMeetingRoomBookingType().equalsIgnoreCase("req")) {
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedRoomId, deskRoomName.getText().toString(), true, editDeskBookingDetails.getCalId(), newEditStatus);
                        }
                        else {
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedRoomId, deskRoomName.getText().toString(), false,editDeskBookingDetails.getCalId(),newEditStatus);
                        }
                    }
                }else if(selectedicon==1){
                } else {

                }

            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectDisabled){
                    editDeskBookingDetails.setDisplayTime(startTime.getText().toString()+" to "+endTime.getText().toString());
                    if (dskRoomParkStatus==1){

                    } else {

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

    private void deleteBookingCall(EditBookingDetails editDeskBookingDetails) {

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("meetingRoomId",selectedRoomId);
        jsonOuterObject.addProperty("handleRecurring",false);
//        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();
//        list1.add(editDeskBookingDetails.getCalId());
        jsonDeletedIdsArray.add(editDeskBookingDetails.getCalId());
        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        //System.out.println("json un"+jsonOuterObject.toString());

        editBookingCall(jsonOuterObject,0,2,"delete");

    }
    public void editBookingCall(JsonObject data,int position,int dskRoomStatus,String newEditDelete) {
        if (Utils.isNetworkAvailable(activityContext)) {
            dialog= ProgressDialog.showProgressBar(context);
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
//                        Utils.showCustomAlertDialog(activityContext,"Update Success");
//                        Toast.makeText(activityContext, "Success Bala", Toast.LENGTH_SHORT).show();
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
//                                        tabToggleViewClicked(0);
                                        break;
                                    case 2:
//                                        tabToggleViewClicked(1);
                                        break;
                                    case 3:
//                                        tabToggleViewClicked(2);
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
                                Utils.showCustomAlertDialog(activityContext, resultString);
                            }
                        }else if (response.code() == 500){
                            Utils.showCustomAlertDialog(activityContext,""+response.message());
                        }else if (response.code() == 401){
                            Utils.showCustomTokenExpiredDialog(activityContext,"401 Error Response");
                            SessionHandler.getInstance().saveBoolean(activityContext, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(context);
                        }
                        else {
                            Toast.makeText(activityContext, "Response Failure", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception){
                        Log.e(TAG,exception.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    Toast.makeText(activityContext, "fail Bala"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    //System.out.println("resps"+t.getMessage());
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(activityContext, "Please Enable Internet");
        }
    }
    private void openCheckoutDeleteDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(activityContext);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        ImageView ivChecout = popDialog.findViewById(R.id.ivCheckoutSuccess);
//        ivChecout.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_red));
        // ivChecout.setImageTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_red));
        dialogMsg.setText(""+mesg);
/*
        if (dskRoomStatus==1 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else if(dskRoomStatus==3 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else{
            if (bookEditBottomSheet!=null)
                bookEditBottomSheet.dismiss();
*/
        if (roomBottomSheet!=null)
            roomBottomSheet.dismiss();


        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

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


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_participant_booking,
//                new RelativeLayout(context))));
        View view = View.inflate(context, R.layout.dialog_bottom_sheet_room_participant_booking, null);
        bottomSheetDialog.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels-150);
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

        if (editDeskBookingDetails.getAmenities()!=null){
            for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                ShapeAppearanceModel  shapeAppearanceModel = new ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED,15)
                        .build();
                Chip chip = new Chip(context);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setShapeAppearanceModel(shapeAppearanceModel);
                chip.setId(i);
                chip.setTextAppearance(R.style.chipText);
                chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                chip.setTextColor(context.getResources().getColor(R.color.figmaBlack));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
                chipGroup.addView(chip);
            }
        }

        //Set All Amenities Here
/*
        for (int i = 0; i < amenitiesList.size(); i++) {

            //System.out.println("RoomAmenitiesList " + amenitiesList.get(i));
            Chip chip = new Chip(context);
            chip.setText(amenitiesList.get(i));
            chip.setCheckable(false);
            chip.setTextAppearance(R.style.chipText);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }
*/

        //Language
//        editRoomBookingContinue.setText(appKeysPage.getContinue());
        editRoomBookingContinue.setText("Save changes");
        if (isEditable){
            editRoomBookingContinue.setVisibility(View.VISIBLE);
        } else {
            editRoomBookingContinue.setVisibility(View.GONE);
//        editRoomBookingBack.setText(appKeysPage.getBack())
        };
        editRoomBookingBack.setText("Back");
//        etComments.setHint(appKeysPage.getComments());
        etComments.setHint("Comments optional");
        etSubject.setHint(meetingRoomsLanguage.getSubject());

        linearLayoutManager = new LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false);
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
                    Toast.makeText(context, "Please Enter Subject", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }

                /*if (comment.isEmpty()
                        || comment.equals("")
                        || comment == null) {
                    Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_LONG).show();
                    status = false;
                    return;
                }*/

                if (status) {
                    bottomSheetDialog.dismiss();
                    bottomSheetDialog.dismiss();
                    doMeetingRoomBooking(meetingRoomId,
                                startTime.getText().toString(),
                                endTime.getText().toString(), subject, comment,
                                isRequest, editDeskBookingDetails.isTeamsChecked(),externalAttendeesEmail,id,newEditStatus);

                } else {
                    Toast.makeText(context, "Please Enter All Details", Toast.LENGTH_LONG).show();
                }

            }
        });





        //Get AttendeeList To Edit
        if (editDeskBookingDetails.getAttendeesList()!=null){
            attendeesListForEdit=editDeskBookingDetails.getAttendeesList();
        }


        linearLayoutManager = new LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false);
        rvParticipant.setLayoutManager(linearLayoutManager);
        rvParticipant.setHasFixedSize(true);




        try {

            if (editDeskBookingDetails.getExternalAttendeesList()!=null &&
                    editDeskBookingDetails.getExternalAttendeesList().size()>0){
                externalAttendeesChipGroup.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < editDeskBookingDetails.getExternalAttendeesList().size(); i++) {
                //System.out.println("ExternalAttendeesListInLoop " + editDeskBookingDetails.getExternalAttendeesList().get(i));

                Chip chip = new Chip(context);
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

                Chip chip = new Chip(context);
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

                    Chip chip = new Chip(context);
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
                        Utils.toastMessage(context, "Please enter a valid email address.");
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
        //dialog = ProgressDialog.showProgressBar(context);

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
    private void doMeetingRoomBooking(int meetingRoomId,
                                      String startRoomTime,
                                      String endRoomTime,
                                      String subject,
                                      String comment,
                                      boolean isRequest,
                                      boolean isTeamsChecked,List<String> externalAttendeesEmail,int id,String newEditStatus) {

//        binding.locateProgressBar.setVisibility(View.VISIBLE);
        dialog= ProgressDialog.showProgressBar(context);
        MeetingRoomRequest meetingRoomRequest = new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
//        if(teamsCheckBoxStatus && isTeamsChecked)
//            meetingRoomRequest.setMsTeams(true);
//        else
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

        //dialog = ProgressDialog.showProgressBar(context);
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
                        Utils.showCustomAlertDialog(activityContext, resultString);
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
                        Utils.showCustomAlertDialog(activityContext, resultString);
                    }

                } catch (Exception exception){
                    Log.e(TAG,exception.getMessage());
                }
                ProgressDialog.dismisProgressBar(context,dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ProgressDialog.dismisProgressBar(context,dialog);
//                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });


    }


    private void showParticipantNameInRecyclerView(List<ParticipantDetsilResponse> participantDetsilResponseList, RecyclerView rvParticipant) {

        //Get Saved UserId
        int userId=SessionHandler.getInstance().getInt(context, AppConstants.USER_ID);

        List<ParticipantDetsilResponse> result = new ArrayList<>();
        result = (List<ParticipantDetsilResponse>) participantDetsilResponseList.stream().filter(val -> val.getId() != userId).collect(Collectors.toList());

        rvParticipant.setVisibility(View.VISIBLE);
        ParticipantNameShowAdapter participantNameShowAdapter = new ParticipantNameShowAdapter(context, result, this,rvParticipant);
        rvParticipant.setAdapter(participantNameShowAdapter);
    }

    private void openCheckoutDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(activityContext);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(""+mesg);
        if (roomBottomSheet!=null)
            roomBottomSheet.dismiss();


        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

    @Override
    public void onParticipantSelect(ParticipantDetsilResponse participantDetsilResponse, RecyclerView recyclerView) {

        this.participantDetsilResponse= participantDetsilResponse;


        Chip chip = new Chip(context);

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



       /* Chip chip=new Chip(context);
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
}
