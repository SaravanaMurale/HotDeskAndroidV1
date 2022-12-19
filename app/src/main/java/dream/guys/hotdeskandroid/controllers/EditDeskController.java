package dream.guys.hotdeskandroid.controllers;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ActiveTeamsAdapter;
import dream.guys.hotdeskandroid.adapter.*;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.NewDeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParkingSpotListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.RoomListRecyclerAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.ui.home.HomeFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDeskController implements DeskListBookAdapter.OnChangeSelected {
    String TAG="DeskController";
    String isFrom;
    Activity activityContext;
    Context context;
    Dialog dialog;

    int changedTeamId=0;
    int changedDeskId=0;
    int selectedTeamId=0;
    int selectedDeskId=0;
    boolean repeatActvieStatus=false;
    public int selectedTeamAutoApproveStatus = 0;
    boolean isGlobalLocationSetUP = false;
    
    
    boolean startDisabled = false;
    boolean endDisabled = false;
    boolean selectDisabled = false;
    boolean tvTeamNameDisabled = false;
    TextView startTime,endTime,repeat,date,deskRoomName,tv_description,locationAddress,continueEditBook;
    TextView country, state, street, floor, back, bsApply,deskStatusText,deskStatusDot;
    TextView tvRepeat, tvTeamName,tvcapacityCount;
    EditText bsGeneralSearch;
    LinearLayoutManager linearLayoutManager;

    BottomSheetDialog deskBottomSheet;
    BottomSheetDialog deskListBottomSheet;
    BottomSheetDialog activeTeamsBottomSheet;

    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList = new ArrayList<>();
    //teams list and Desk List
    List<ActiveTeamsResponse> activeTeamsList = new ArrayList<>();
    String selectedTeamName="";
    RecyclerView rvDeskRecycler, rvActiveTeams;
    DeskListBookAdapter newdeskListRecyclerAdapter;
    ActiveTeamsAdapter activeTeamsAdapter;


    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;

    public EditDeskController(Activity activityContext, Context context, EditBookingDetails editDeskBookingDetails,String isFrom) {
        this.activityContext = activityContext;
        this.context = context;
        this.isFrom = isFrom;

        setLanguage();
        getActiveTeams();
        editDeskBooking(editDeskBookingDetails);
    }
    private void getActiveTeams() {

        if (Utils.isNetworkAvailable(activityContext)) {
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
            Utils.toastMessage(activityContext, activityContext.getResources().getString(R.string.enable_internet));
        }

    }
    public void editDeskBooking(EditBookingDetails editDeskBookingDetails){
        dialog= new Dialog(activityContext);

        if (editDeskBookingDetails.getRequestedTeamId()>0){
            selectedTeamId=editDeskBookingDetails.getRequestedTeamId();
            editDeskBookingDetails.setRequestedTeamId(editDeskBookingDetails.getRequestedTeamId());
        } else {
            selectedTeamId=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID);
            editDeskBookingDetails.setRequestedTeamId(0);
        }

        if (editDeskBookingDetails.getUsageTypeId() == 7){
            getRequestDeskDeskList(editDeskBookingDetails,"edit");
        } else{
            editDeskBottomSheet(editDeskBookingDetails,1,0,"edit");
        }

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

    private void getRequestDeskDeskList(EditBookingDetails editDeskBookingDetails, String edit) {
        if (Utils.isNetworkAvailable(context)) {
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
//                                selectedTeamId = deskList.get(i).getTeamId();
                                editDeskBookingDetails.setDeskCode(deskList.get(i).getDeskCode());
                                editDeskBookingDetails.setTimeZone(deskList.get(i).getTimeZones().get(0).getTimeZoneId());
                                editDeskBookingDetails.setDesktId(deskList.get(i).getTeamDeskId());
                                editDeskBookingDetails.setDeskTeamId(deskList.get(i).getTeamId());
                                break loop;
                            }
                        }
                    }

//                    editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"edit");
                    editDeskBottomSheet(editDeskBookingDetails,1,0,"edit");
                }

                @Override
                public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {

                }
            });


        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void editDeskBottomSheet(EditBookingDetails editDeskBookingDetails, 
                                     int dskRoomParkStatus, int position,String newEditStatus) {
        endDisabled=false;
        startDisabled=false;
        selectDisabled=false;

        deskBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        deskBottomSheet.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_calendar_bottom_sheet_edit_booking,
//                new RelativeLayout(context))));

        View view = View.inflate(context, R.layout.dialog_calendar_bottom_sheet_edit_booking, null);
        deskBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //Language
        TextView tv_start=deskBottomSheet.findViewById(R.id.tv_start);
        TextView tv_end=deskBottomSheet.findViewById(R.id.tv_end);
        TextView tv_comment=deskBottomSheet.findViewById(R.id.tv_comment);
        TextView tv_repeat=deskBottomSheet.findViewById(R.id.tv_repeat);
        TextView tv_delete=deskBottomSheet.findViewById(R.id.delete_text);
        locationAddress=deskBottomSheet.findViewById(R.id.locationAddress);
        tv_delete.setVisibility(View.VISIBLE);
        repeat = deskBottomSheet.findViewById(R.id.repeat);
        deskStatusText = deskBottomSheet.findViewById(R.id.desk_status_text);
        deskStatusDot = deskBottomSheet.findViewById(R.id.user_status_dot);
        continueEditBook=deskBottomSheet.findViewById(R.id.editBookingContinue);
        TextView back=deskBottomSheet.findViewById(R.id.editBookingBack);


        tv_start.setText(appKeysPage.getStart());
        tv_end.setText(appKeysPage.getEnd());
        tv_comment.setText(appKeysPage.getComments());
        tv_repeat.setText(appKeysPage.getRepeat());
        continueEditBook.setText(appKeysPage.getContinue());
        back.setText(appKeysPage.getBack());



        startTime = deskBottomSheet.findViewById(R.id.start_time);
        endTime = deskBottomSheet.findViewById(R.id.end_time);

        deskRoomName=deskBottomSheet.findViewById(R.id.tv_desk_room_name);
        locationAddress=deskBottomSheet.findViewById(R.id.tv_location_details);
        //New...
        locationAddress.setVisibility(View.VISIBLE);

        date=deskBottomSheet.findViewById(R.id.date);
        TextView title=deskBottomSheet.findViewById(R.id.title);
        TextView checkInDate=deskBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=deskBottomSheet.findViewById(R.id.showCheckInDate);

        TextView select=deskBottomSheet.findViewById(R.id.select_desk_room);

        TextView tvComments=deskBottomSheet.findViewById(R.id.tv_comments);
        TextView tv_description=deskBottomSheet.findViewById(R.id.tv_description);
        EditText edComments=deskBottomSheet.findViewById(R.id.comments);
        EditText commentRegistration=deskBottomSheet.findViewById(R.id.ed_registration);
        EditText edRegistration=deskBottomSheet.findViewById(R.id.et_registration_num);
        RelativeLayout repeatBlock=deskBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=deskBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=deskBottomSheet.findViewById(R.id.rl_teams_layout);
        CheckBox teamsCheckBox = deskBottomSheet.findViewById(R.id.teams_check_box);
        RelativeLayout dateBlock = deskBottomSheet.findViewById(R.id.bookingDateBlock);
        LinearLayout statusCheckLayout=deskBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=deskBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=deskBottomSheet.findViewById(R.id.capacity_layout);
        tvcapacityCount=deskBottomSheet.findViewById(R.id.tv_capacity_no);

//        chipGroup = deskBottomSheet.findViewById(R.id.list_item);
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

//            select.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
            dateBlock.setVisibility(View.GONE);
        } else {
            dateBlock.setVisibility(View.GONE);
//            select.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
        }


        if (dskRoomParkStatus == 1) {
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
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
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

            date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));

            if (newEditStatus.equalsIgnoreCase("edit")
                    && !(editDeskBookingDetails.getUsageTypeId()== 2 ||
                    editDeskBookingDetails.getUsageTypeId()== 7)){
                select.setVisibility(View.GONE);
            } else {
//                select.setVisibility(View.VISIBLE);
                select.setVisibility(View.GONE);
            }

            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getUsageTypeId()==2
                        && editDeskBookingDetails.getRequestedTeamId()>0){
                    select.setTextColor((activityContext.getResources().getColor(R.color.figmaGrey)));
                    selectDisabled=true;
                } else if(editDeskBookingDetails.getUsageTypeId()==7){
                    select.setTextColor((activityContext.getResources().getColor(R.color.figmaBlueText)));
                    selectDisabled=false;
                } else {
                    select.setTextColor((activityContext.getResources().getColor(R.color.figmaBlueText)));
                    selectDisabled=false;
                }
            }


            teamsBlock.setVisibility(View.GONE);
//            commentBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");
            capacitylayout.setVisibility(View.GONE);
//            chipGroup.setVisibility(View.INVISIBLE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);

            deskRoomName.setText(editDeskBookingDetails.getDeskCode());
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
                continueEditBook.setText("Save changes");
                back.setText("Back");
            }

        }
        /*else if (dskRoomParkStatus==2) {
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
         *//*   if(teamsCheckBoxStatus)
                teamsBlock.setVisibility(View.VISIBLE);
            else
                teamsBlock.setVisibility(View.GONE);
            *//*
//            chipGroup.setVisibility(View.VISIBLE);
            capacitylayout.setVisibility(View.VISIBLE);
            tvcapacityCount.setText(editDeskBookingDetails.getCapacity());
            *//*if (userAllowedMeetingResponseListUpdated.size() > 0) {
//                //System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseListUpdated.get(0).getName());
                selectedDeskId = userAllowedMeetingResponseListUpdated.get(0).getId();
                locationAddress.setText(""+userAllowedMeetingResponseListUpdated.get(0).getLocationMeeting().getName());
            }*//*
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
        }else {
            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));

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
            llDeskLayout.setVisibility(View.VISIBLE);
//            repeatBlock.setVisibility(View.VISIBLE);
            teamsBlock.setVisibility(View.GONE);
            edRegistration.setHint("Registration");
//            commentBlock.setVisibility(View.GONE);
//            commentRegistration.setHint("Registration Number");
//            tvComments.setText("Regitration Number");
//            if (profileData != null)
//                commentRegistration.setText(profileData.getVehicleRegNumber());
//            chipGroup.setVisibility(View.INVISIBLE);
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
            if (parkingSpotModelList.size() > 0 && !newEditStatus.equalsIgnoreCase("edit")){
//                //System.out.println("tim else"+
                select.setVisibility(View.VISIBLE);
                deskRoomName.setText(""+parkingSpotModelList.get(0).getCode());
                selectedDeskId = parkingSpotModelList.get(0).getId();
                locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());
            } else {
                select.setVisibility(View.GONE);
//                title.setText("Edit Parking Details");
                deskRoomName.setText(""+editDeskBookingDetails.getParkingSlotCode());
                selectedDeskId = editDeskBookingDetails.getParkingSlotId();
                locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());
            }
        }
*/
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
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBooking(editDeskBookingDetails);
            }
        });
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
                                && newEditStatus.equalsIgnoreCase("edit")) {
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

                            Utils.bottomSheetTimePicker(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                        } else {

                            Utils.bottomSheetTimePicker(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);
                        }
                    } else
                        Utils.bottomSheetTimePicker(context,activityContext,endTime,"End Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),false);

                }
//                    Utils.popUpTimePicker(activityContext,endTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(dskRoomParkStatus==1)
                    repeatBottomSheetDialog("3");
                else if (dskRoomParkStatus==2)
                    repeatBottomSheetDialog("4");
                else
                    repeatBottomSheetDialog("5");*/
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
                
                if (repeatActvieStatus) {
                    /*if (dskRoomParkStatus==1)
                        doRepeatDeskBookingForAWeek(editDeskBookingDetails);
                    else if (dskRoomParkStatus==3)
                        doRepeatCarBookingForAWeek(edRegistration.getText().toString());*/
                } else {
                    if (newEditStatus.equalsIgnoreCase("edit"))
                        editBookingCallForDesk(editDeskBookingDetails,edComments);
                    else if (newEditStatus.equalsIgnoreCase("new"))
                        newBookingCallForDesk(editDeskBookingDetails,edComments);
                    else
                        requestBookingCallForDesk(editDeskBookingDetails,edComments);
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
//                        if (editDeskBookingDetails.getDeskStatus()!=1 && editDeskBookingDetails.getDeskStatus()!=2)
//                            callDeskBottomSheetDialog();
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
                deskBottomSheet.dismiss();
            }
        });

        deskBottomSheet.show();

    }

    private void deleteBooking(EditBookingDetails editDeskBookingDetails) {
//        EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
//        editDeskBookingDetails.setCalId(bookings.getId());
//        editBookingUsingBottomSheet(editDeskBookingDetails,1,0,"delete");

        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAMMEMBERSHIP_ID));

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

    private void editBookingCallForDesk(EditBookingDetails editDeskBookingDetails,EditText edComments) {
        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
        jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAMMEMBERSHIP_ID));
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
        deskBottomSheet.dismiss();

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

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAMMEMBERSHIP_ID));
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
        deskBottomSheet.dismiss();

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

        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAMMEMBERSHIP_ID));
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
        deskBottomSheet.dismiss();

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
    private void openCheckoutDialog(String mesg, int dskRoomStatus) {
        Dialog popDialog = new Dialog(activityContext);
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(""+mesg);
        if (deskBottomSheet!=null)
            deskBottomSheet.dismiss();

        /*
        if (dskRoomStatus==1 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else if(dskRoomStatus==3 && bookEditBottomSheet!=null)
            bookEditBottomSheet.dismiss();
        else{
            if (bookEditBottomSheet!=null)
                bookEditBottomSheet.dismiss();
            deskBottomSheet.dismiss();
        }*/


        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
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
            if (deskBottomSheet!=null)
                deskBottomSheet.dismiss();


        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

    private void selectedDeskList(int selectedTeamIdChange,String date, EditBookingDetails editBookingDetails,String newEditStatus) {
        if (Utils.isNetworkAvailable(context)) {
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
                    //System.out.println("Selecrt id"+selectedTeamId + bookingDeskList.get(0).getDeskCode());
                    deskListBottomSheet(1,editBookingDetails,newEditStatus);

                }

                @Override
                public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {
//                    deepLinking();

                }
            });


        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }

    }
    private void deskListBottomSheet(int id,EditBookingDetails editBookingDetails,String newEditStatus) {
        for (int i=0; i<activeTeamsList.size(); i++) {
            if (selectedTeamId==activeTeamsList.get(i).getId()) {
                selectedTeamName = activeTeamsList.get(i).getName();
                selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
            }
        }

        deskListBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        deskListBottomSheet.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk_new,
//                new RelativeLayout(context))));

        View view = View.inflate(context, R.layout.dialog_bottom_sheet_edit_select_desk_new, null);
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

        linearLayoutManager = new LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
//        sheetDate.setText(Utils.calendarDay10thMonthformat(Utils.convertStringToDateFormet(calSelectedDate)));

        if(editBookingDetails.getDisplayTime()!=null)
            sheetTime.setText(""+editBookingDetails.getDisplayTime());

        selectDesk.setText("Book a Workspace");
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
        newdeskListRecyclerAdapter = new DeskListBookAdapter(context,this,
                activityContext,bookingDeskList,this,deskListBottomSheet,
                id,editBookingDetails,newEditStatus);
        rvDeskRecycler.setAdapter(newdeskListRecyclerAdapter);

        tvTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvTeamNameDisabled){
                    /*if (newEditStatus.equalsIgnoreCase("edit")){
                        callActiveTeamsBottomSheet(id,editBookingDetails,newEditStatus);
                    }else {
//                        if(newEditStatus.equalsIgnoreCase("new")
//                                || newEditStatus.equalsIgnoreCase("request"))
                        callActiveTeamsBottomSheet(id,editBookingDetails,newEditStatus);
                    }*/
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

    @Override
    public void onChangeDesk(BookingForEditResponse.TeamDeskAvailabilities deskList,
                             int deskId, String deskName, String request, String timeZone,
                             int typeId, EditBookingDetails editBookingDetails, String newEditStatus, int teamId) {
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

//        Toast.makeText(context, "dsj"+changedDeskId, Toast.LENGTH_SHORT).show();
        if (typeId == 0) {

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
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
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
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
                    if (teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetails.setRequestedTeamDeskId(deskId);
                        editBookingDetails.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetails.setRequestedTeamDeskId(0);
                        editBookingDetails.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
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
                /*if(request.equalsIgnoreCase("request")){
                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                } else {
                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                }*/

                /*if(roomBottomSheet!=null)
                    roomBottomSheet.dismiss();*/

                /*if(request.equalsIgnoreCase("new"))
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"new");
                else
                    editBookingUsingBottomSheet(editBookingDetails,
                            1,0,"request");*/
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
/*
    private void callActiveTeamsBottomSheet(int id, EditBookingDetails editBookingDetails, String newEditStatus) {
        activeTeamsBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        activeTeamsBottomSheet.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_active_teams,
                new RelativeLayout(context))));

        TextView bsRepeatBack, selectDesk;
        rvActiveTeams= activeTeamsBottomSheet.findViewById(R.id.desk_list_select_recycler);
        selectDesk= activeTeamsBottomSheet.findViewById(R.id.sheet_name);
        bsRepeatBack=activeTeamsBottomSheet.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false);
        rvActiveTeams.setLayoutManager(linearLayoutManager);
        rvActiveTeams.setHasFixedSize(true);
        selectDesk.setText("Book from another team");

        activeTeamsAdapter =new ActiveTeamsAdapter(context,this,
                activityContext,activeTeamsList,this,activeTeamsBottomSheet,id,
                editBookingDetails,newEditStatus);
        rvActiveTeams.setAdapter(activeTeamsAdapter);



        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTeamsBottomSheet.dismiss();
            }
        });

        activeTeamsBottomSheet.show();
    }
*/

}
