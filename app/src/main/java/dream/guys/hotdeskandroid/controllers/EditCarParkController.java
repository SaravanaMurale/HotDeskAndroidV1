package dream.guys.hotdeskandroid.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.CarParkLocationsModel;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCarParkController {
    String TAG="DeskController";
    String isFrom;
    String calSelectedDate;
    Activity activityContext;
    Context context;
    Dialog dialog;
    BottomSheetDialog roomBottomSheet;

    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;
    List<ActiveTeamsResponse> activeTeamsList = new ArrayList<>();
    List<ParkingSpotModel> parkingSpotModelList=new ArrayList<>();


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
    String selectedTeamName="";

    TextView startTime,endTime,repeat,date,deskRoomName,tv_description,locationAddress,continueEditBook;
    TextView country, state, street, floor, back, bsApply,deskStatusText,deskStatusDot;
    TextView tvRepeat, tvTeamName,tvcapacityCount;
    EditText bsGeneralSearch;
    LinearLayoutManager linearLayoutManager;

    boolean isVehicleReg=false;
    public EditCarParkController(Activity activityContext, Context context, EditBookingDetails editDeskBookingDetails, String isFrom,String calSelectedDate) {
        this.activityContext = activityContext;
        this.context = context;
        this.isFrom = isFrom;
        this.calSelectedDate = calSelectedDate;

        setLanguage();
        checkVeichleReg();

        getActiveTeams();
        getParkingSpotList(""+ SessionHandler.getInstance().getInt(activityContext, AppConstants.DEFAULT_CAR_PARK_LOCATION_ID),editDeskBookingDetails,"edit");
//        editDeskBooking(editDeskBookingDetails);
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

    private void getParkingSpotList(String id, EditBookingDetails editBookingDetails,String newEdit) {
        if (Utils.isNetworkAvailable(activityContext)) {
//            dialog= ProgressDialog.showProgressBar(context);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ParkingSpotModel>> call = apiService.getParkingSpotModels(id);
            call.enqueue(new Callback<List<ParkingSpotModel>>() {
                @Override
                public void onResponse(Call<List<ParkingSpotModel>> call, Response<List<ParkingSpotModel>> response) {
//                    ProgressDialog.dismisProgressBar(context,dialog);

                    parkingSpotModelList.clear();
                    try {
                        if(response.code()==200 && response.body()!=null && response.body().size()>0){
                            for(int i=0;i<response.body().size();i++){
                                if(response.body().get(i).isActive()){
                                    parkingSpotModelList.add(response.body().get(i));
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
                            getCarParkLocationsList(editBookingDetails,"new");
                        }

                    } catch (Exception exception){

                    }

                }

                @Override
                public void onFailure(Call<List<ParkingSpotModel>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(context,dialog);
//                    Toast.makeText(activityContext, "Api Failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(activityContext, "Please Enable Internet");
        }

    }
    private void getCarParkLocationsList(EditBookingDetails editBookingDetails, String status) {
        if (Utils.isNetworkAvailable(activityContext)) {
//            dialog= ProgressDialog.showProgressBar(context);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<CarParkLocationsModel>> call = apiService.getCarParkLocation();
            call.enqueue(new Callback<List<CarParkLocationsModel>>() {
                @Override
                public void onResponse(Call<List<CarParkLocationsModel>> call, Response<List<CarParkLocationsModel>> response) {
//                    ProgressDialog.dismisProgressBar(context,dialog);
                    try {

                        if (response.code()==200 && response.body().size()>0){
                            getParkingSpotList(""+response.body().get(0).getId(),editBookingDetails,status);
                        } else {
                            Toast.makeText(context, "No parking locations Aavilable", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception exception){

                    }

                }

                @Override
                public void onFailure(Call<List<CarParkLocationsModel>> call, Throwable t) {
//                    ProgressDialog.dismisProgressBar(context,dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
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
        TextView desc=roomBottomSheet.findViewById(R.id.tvDesc);
        locationAddress=roomBottomSheet.findViewById(R.id.tv_location_details);
        //New...

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

        showcheckInDate.setText(Utils.showBottomSheetDate(calSelectedDate));
        checkInDate.setText("");

        locationAddress.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
        tv_description.setVisibility(View.GONE);


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

        if (editDeskBookingDetails.getLocationAddress()!=null &&
                !editDeskBookingDetails.getLocationAddress().isEmpty()
                && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase(""))
            locationAddressTop.setText(""+editDeskBookingDetails.getLocationAddress());

        if (dskRoomParkStatus == 1) {
//            Toast.makeText(context, ""+editDeskBookingDetails.getLocationAddress(), Toast.LENGTH_SHORT).show();
            if (editDeskBookingDetails.getLocationAddress()!=null &&
                    !editDeskBookingDetails.getLocationAddress().isEmpty()
                    && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase("null"))
                locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());

            if(editDeskBookingDetails.getDescription()!= null &&
                    !editDeskBookingDetails.getDescription().equalsIgnoreCase("null"))
                tv_description.setText(""+editDeskBookingDetails.getDescription());


          //  tv_description.setText(editDeskBookingDetails.getDescription());

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
            
        }else {
            try {

                if (editDeskBookingDetails.getStatus().getBookingType().equalsIgnoreCase("REQ")) {
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));
                }
            } catch (Exception e){

            }
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
                try {
                   // locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());

                    if(parkingSpotModelList.get(0).getDescription()!= null &&
                            !parkingSpotModelList.get(0).getDescription().equalsIgnoreCase("null"))
                        tv_description.setText(""+parkingSpotModelList.get(0).getDescription());

                    if(parkingSpotModelList.get(0).getLocation().getName()!= null &&
                            !parkingSpotModelList.get(0).getLocation().getName().equalsIgnoreCase("null"))
                        locationAddress.setText(""+parkingSpotModelList.get(0).getLocation().getName());


                  //  locationAddressTop.setText(""+parkingSpotModelList.get(0).getLocation().getName());
                  //  tv_description.setText(""+parkingSpotModelList.get(0).getDescription());
                } catch (Exception e){

                }
            } else {
                select.setVisibility(View.GONE);
//                title.setText("Edit Parking Details");
                deskRoomName.setText(""+editDeskBookingDetails.getParkingSlotCode());
                selectedDeskId = editDeskBookingDetails.getParkingSlotId();
                try {
                  //  locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());
                    //tv_description.setText(""+editDeskBookingDetails.getDescription());


                    if(editDeskBookingDetails.getLocationAddress()!= null &&
                            !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase("null"))
                        locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());


                    if(editDeskBookingDetails.getDescription()!= null &&
                            !editDeskBookingDetails.getDescription().equalsIgnoreCase("null"))
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
                    Chip chip = new Chip(context);
                    chip.setId(i);
                    chip.setTextAppearance(R.style.chipText);
                    chip.setShapeAppearanceModel(shapeAppearanceModel);
                    chip.setText(""+editDeskBookingDetails.getAmenities().get(i));
                    chip.setChipBackgroundColorResource(R.color.figmaBgGrey);
                    chip.setCloseIconVisible(false);
                    chip.setTextColor(context.getResources().getColor(R.color.figmaBlack));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
//                    chipGroup.addView(chip);
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
                Utils.bottomSheetDatePicker(context, activityContext, "", "", checkInDate,showcheckInDate);
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!startDisabled){
                        if(editDeskBookingDetails.getStatus().getBookingType().equalsIgnoreCase("REQ"))
                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,startTime,"Start Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                        else
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
                } catch (Exception e){

                }
            }

//                Toast.makeText(context, "fsf"+editDeskBookingDetails.getRequestedTeamDeskId(), Toast.LENGTH_SHORT).show();

//                    Utils.popUpTimePicker(activityContext,startTime,Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));

        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!endDisabled) {
                        if (editDeskBookingDetails.getStatus().getBookingType().equalsIgnoreCase("REQ"))
                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                        else
                            Utils.bottomSheetTimePicker24Hrs(context,activityContext,endTime,"End Time",
                                    Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()),true);
                    }
                } catch (Exception c){

                }

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

                if (true){
                    if (repeatActvieStatus) {

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
                                jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAM_ID));
                                jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(activityContext,AppConstants.TEAMMEMBERSHIP_ID));
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
                                    jsonChangesObject.addProperty("bookedForUser",SessionHandler.getInstance().getInt(activityContext,AppConstants.USER_ID));
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
                            Toast.makeText(activityContext, "Enter Registration Number", Toast.LENGTH_SHORT).show();
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
                    if (isFrom.equalsIgnoreCase(AppConstants.HOMEFRAGMENTINSTANCESTRING)){
                        ((MainActivity) activityContext).callHomeFragment();
                    }
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
                                    resultString = bookindata.getInvalidFrom();
                                } else if (response.body().getResultCode().toString().equals("INVALID_TO")) {
                                    resultString = bookindata.getInvalidTo();
                                } else if (response.body().getResultCode().toString().equals("INVALID_TIMEZONE_ID")) {
                                    resultString = bookindata.getInvalidTimeZoneId();
                                } else if (response.body().getResultCode().toString().equals("INVALID_TIMEPERIOD")) {
                                    resultString = bookindata.getInvalidTimePeriod();
                                } else if (response.body().getResultCode().toString().equals("USER_TIME_OVERLAP")) {
                                    resultString =  bookindata.getTimeOverlap();
                                } else if(response.body().getResultCode().toString().equals("COVID_SYMPTOMS")){
                                    resultString = bookindata.getCOVID_SYMPTOMS();
                                }else if(response.body().getResultCode().toString().equals("DESK_UNAVAILABLE")){
                                    resultString =  bookindata.getDeskUnavailable();
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
        if (roomBottomSheet!=null)
            roomBottomSheet.dismiss();

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

    public void checkVeichleReg() {

        if (Utils.isNetworkAvailable(activityContext)) {

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
            Utils.toastMessage(activityContext, "Please Enable Internet");
        }

    }

}
