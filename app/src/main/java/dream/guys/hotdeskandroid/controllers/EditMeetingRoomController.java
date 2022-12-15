package dream.guys.hotdeskandroid.controllers;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMeetingRoomController {
    String TAG="DeskController";
    String isFrom;
    Activity activityContext;
    Context context;
    Dialog dialog;
    List<UserAllowedMeetingResponse> allMeetingRoomList=new ArrayList<>();
    List<AmenitiesResponse> amenitiesList = new ArrayList<>();
    public int myTeamId = 0;

    boolean startDisabled = false;
    boolean endDisabled = false;
    boolean selectDisabled = false;
    boolean tvTeamNameDisabled = false;


    public EditMeetingRoomController(String isFrom, Activity activityContext, Context context) {
        this.isFrom = isFrom;
        this.activityContext = activityContext;
        this.context = context;
//        getAmenities();
        myTeamId = SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID);
    }
/*
    private void getAmenities() {
        if (Utils.isNetworkAvailable(activityContext)) {
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
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
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

        roomBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        roomBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_calendar_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        //Language
        TextView tv_start=roomBottomSheet.findViewById(R.id.tv_start);
        TextView tv_end=roomBottomSheet.findViewById(R.id.tv_end);
        TextView tv_comment=roomBottomSheet.findViewById(R.id.tv_comment);
        TextView tv_repeat=roomBottomSheet.findViewById(R.id.tv_repeat);
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
            */
/*startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            startDisabled=true;
            selectDisabled=true;*//*

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
            } else{
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

        if (dskRoomParkStatus == 1) {
            if (newEditStatus.equalsIgnoreCase("edit")){
                if (editDeskBookingDetails.getDate()!=null)
                    date.setText(""+Utils.calendarDay10thMonthYearformat(editDeskBookingDetails.getDate()));
                repeatBlock.setVisibility(View.GONE);
                commentBlock.setVisibility(View.VISIBLE);
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

                commentBlock.setVisibility(View.VISIBLE);
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
                continueEditBook.setText("Continue");
                back.setText("Back");
            }

        }else if (dskRoomParkStatus==2) {
            if(editDeskBookingDetails.getMeetingRoomStatus() == 2){
                deskStatusText.setText("Available For Request");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figma_orange));
            } else {
                deskStatusText.setText("Available");
                deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaLiteGreen));
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
            capacitylayout.setVisibility(View.VISIBLE);
            tvcapacityCount.setText(editDeskBookingDetails.getCapacity());
            if (userAllowedMeetingResponseListUpdated.size() > 0) {
//                //System.out.println("tim else"+parkingSpotModelList.get(0).getCode());
                deskRoomName.setText(""+userAllowedMeetingResponseListUpdated.get(0).getName());
                selectedDeskId = userAllowedMeetingResponseListUpdated.get(0).getId();
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


        if (editDeskBookingDetails.getAmenities()!=null)
            //System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
            if (editDeskBookingDetails.getAmenities()!=null){
                for (int i=0; i<editDeskBookingDetails.getAmenities().size(); i++){
                    Chip chip = new Chip(getContext());
                    chip.setId(i);
                    chip.setTextAppearance(R.style.chipText);
                    chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                    chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                    chip.setCloseIconVisible(false);
                    chip.setTextColor(getContext().getResources().getColor(R.color.figmaBlack));
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
                if (!startDisabled){
                    if(dskRoomParkStatus==2){
                        Utils.bottomSheetTimePickerMeetingRoom(getContext(),
                                getActivity(),startTime,endTime,"Start Time",
                                Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                    } else {

                        if (dskRoomParkStatus == 1
                                && newEditStatus.equalsIgnoreCase("edit")){
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

*/
/*
                        if (editDeskBookingDetails.getDeskBookingType()!=null
                                && editDeskBookingDetails.getDeskStatus() != 1
                                && editDeskBookingDetails.getDeskStatus() != 2
                                && editDeskBookingDetails.getDeskBookingType().equalsIgnoreCase("req"))
                            Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
*//*



                    }
                }

//                Toast.makeText(context, "fsf"+editDeskBookingDetails.getRequestedTeamDeskId(), Toast.LENGTH_SHORT).show();

//                    Utils.popUpTimePicker(getActivity(),startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
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

                if (selectedicon==2 && newEditStatus.equalsIgnoreCase("new")){
                    if (editDeskBookingDetails.getMeetingRoomStatus() == 2) {
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime, endTime, selectedDeskId,
                                deskRoomName.getText().toString(), true, editDeskBookingDetails.getCalId(), newEditStatus);
                    } else {
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime, endTime, selectedDeskId,
                                deskRoomName.getText().toString(), false, editDeskBookingDetails.getCalId(), newEditStatus);
                    }
                } else if (selectedicon==2) {
                    if (newEditStatus.equalsIgnoreCase("request")) {
                        callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                startTime,
                                endTime,
                                selectedDeskId, deskRoomName.getText().toString(), true, editDeskBookingDetails.getCalId(), newEditStatus);
                    } else if(newEditStatus.equalsIgnoreCase("edit")){
                        if(editDeskBookingDetails.getMeetingRoomBookingType().equalsIgnoreCase("req")) {
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedDeskId, deskRoomName.getText().toString(), true, editDeskBookingDetails.getCalId(), newEditStatus);
                        }
                        else {
                            callMeetingRoomBookingBottomSheet(editDeskBookingDetails,
                                    startTime,
                                    endTime,
                                    selectedDeskId, deskRoomName.getText().toString(), false,editDeskBookingDetails.getCalId(),newEditStatus);
                        }
                    }
                }else if(selectedicon==1){
                    if (repeatActvieStatus) {
                        if (dskRoomParkStatus==1)
                            doRepeatDeskBookingForAWeek(editDeskBookingDetails);
                        else if (dskRoomParkStatus==3)
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
                            doRepeatDeskBookingForAWeek(editDeskBookingDetails);
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
                                    */
/*if(selectedDeskId != editDeskBookingDetails.getDesktId()){
                                        jsonChangesObject.addProperty("requestedTeamDeskId",selectedDeskId);
//                                        jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                    }else {
                                        String team=null;
                                    }*//*

                                    jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
//                                jsonChangesObject.addProperty("typeOfCheckIn", "1");
                                }else if (isGlobalLocationSetUP){
                                    */
/*
                                        jsonChangesObject.addProperty("requestedTeamDeskId",editDeskBookingDetails.getDesktId());
                                        jsonChangesObject.addProperty("requestedTeamId",editDeskBookingDetails.getDeskTeamId());
                                        if(!newEditStatus.equalsIgnoreCase("edit"))
                                        jsonChangesObject.addProperty("usageTypeId", "7");
                                        jsonChangesObject.addProperty("timeZoneId", editDeskBookingDetails.getTimeZone());
                                    *//*

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
*/

}
