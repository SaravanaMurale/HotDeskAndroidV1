package dream.guys.hotdeskandroid.controllers;

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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ActiveTeamsAdapter;
import dream.guys.hotdeskandroid.adapter.ActiveTeamsAdapterNew;
import dream.guys.hotdeskandroid.adapter.DeskListBookAdapter;
import dream.guys.hotdeskandroid.adapter.NewDeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.ParkingSpotListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.RoomListRecyclerAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDeskController implements
        ActiveTeamsAdapter.OnActiveTeamsSelected,
        DeskListBookAdapter.OnChangeSelected
{
    String TAG="BookDeskController";
    String isFrom;
    Activity activityContext;
    Context context;
    Dialog dialog;

    int changedTeamId=0;
    int changedDeskId=0;
    int selectedTeamId=0;
    public int globalSelectedTeamId = 0;
    int selectedDeskId=0;

    int enableCurrentWeek=-1;
    boolean repeatActvieStatus=false;
    String type="none";
    public int selectedTeamAutoApproveStatus = 0;
    boolean isGlobalLocationSetUP = false;


    boolean startDisabled = false;
    boolean endDisabled = false;
    boolean selectDisabled = false;
    boolean tvTeamNameDisabled = false;
    TextView startTime,endTime,repeat,date,deskRoomName,tv_description,desc,locationAddress,continueEditBook;
    TextView country, state, street, floor, back, bsApply,deskStatusText,deskStatusDot;
    TextView tvRepeat, tvTeamName,tvcapacityCount;
    EditText bsGeneralSearch;
    LinearLayoutManager linearLayoutManager;

    BottomSheetDialog deskBottomSheet;
    BottomSheetDialog deskListBottomSheet;
    BottomSheetDialog repeatBottomSheetDialog;
    BottomSheetDialog activeTeamsBottomSheet;

    //teams list and Desk List
    List<ActiveTeamsResponse> activeTeamsList = new ArrayList<>();
    String selectedTeamName="";
    RecyclerView rvDeskRecycler, rvActiveTeams;
    DeskListBookAdapter newdeskListRecyclerAdapter;
    ActiveTeamsAdapterNew activeTeamsAdapter;
    String calSelectedDate;
    String locationGlobal;

    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;

    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk=new ArrayList<>();
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList = new ArrayList<>();
    List<DeskAvaliabilityResponse.LocationDesks> locationDeskList = new ArrayList<>();

    EditBookingDetails editBookingDetailsGlobal;
    public BookDeskController(Activity activityContext, Context context,BookingForEditResponse bookingForEditResponse,
                              String isFrom,
                              boolean isGlobalLocationSetUP, String calSelectedDate,String locationGlobal) {
        this.activityContext = activityContext;
        this.context = context;
        this.locationGlobal = locationGlobal;
        this.isFrom = isFrom;
        this.calSelectedDate = calSelectedDate;
        this.isGlobalLocationSetUP = isGlobalLocationSetUP;

        selectedTeamId = SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID);
        setLanguage();
        getActiveTeams();

        if (isGlobalLocationSetUP)
            getAvaliableDeskDetails("3",calSelectedDate,bookingForEditResponse);
        else
            getDeskList("3",calSelectedDate,"new",bookingForEditResponse);
//        editDeskBooking(editDeskBookingDetails);
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

    public void newDeskBookingSheet(BookingForEditResponse bookingForEditResponse, String code) {
        bookingForEditResponseDesk.clear();
        changedTeamId=0;
        changedDeskId=0;
        selectedDeskId=0;

        if (isGlobalLocationSetUP && bookingDeskList!=null && bookingDeskList.size()>0){
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
        }
        else {
            try{
                if (bookingDeskList!=null && bookingDeskList.size()>0){
                    for (int i=0; i<bookingDeskList.size(); i++) {
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
            }catch (Exception e){

            }


        }


        editBookingDetailsGlobal = new EditBookingDetails();
        loop:
        for (int i=0; i<bookingForEditResponseDesk.size();i++) {
            if (bookingForEditResponse.getUserPreferences().getTeamDeskId()
                    == bookingForEditResponseDesk.get(i).getTeamDeskId()) {
//                        Toast.makeText(context, " "+bookingForEditResponseDesk.get(i).getDeskCode(), Toast.LENGTH_SHORT).show();
                if (bookingForEditResponse.getBookings().size() > 0){
                    editBookingDetailsGlobal.setEditStartTTime(Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                            .getMyto()));
                    if (Utils.compareTimeIfCheckInEnable(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()),
                            Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                            .getMyto())
                            )){
                        editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));

                    } else {
                        editBookingDetailsGlobal.setEditEndTime("23:59");
                    }
//                    editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
//                            .getMyto(),2)));
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

            if (Utils.compareTimeIfCheckInEnable(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()),
                    Utils.splitTime(bookingForEditResponse.getBookings().get(bookingForEditResponse.getBookings().size()-1)
                            .getMyto())
                    )){
                editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(bookingForEditResponse.getUserPreferences().getWorkHoursTo()));
            } else {
                editBookingDetailsGlobal.setEditEndTime("23:59");
            }
//            editBookingDetailsGlobal.setEditEndTime("23:59");
//            editBookingDetailsGlobal.setEditEndTime(Utils.splitTime(
//                    Utils.addingHoursToDate(bookingForEditResponse.getBookings().get(
//                            bookingForEditResponse.getBookings().size()-1)
//                            .getMyto(),2)));
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
        }

    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails, 
                                             int dskRoomParkStatus, int position,String newEditStatus) {
        endDisabled=false;
        startDisabled=false;
        selectDisabled=false;

        deskBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        deskBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_calendar_bottom_sheet_edit_booking,
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
        tv_description=deskBottomSheet.findViewById(R.id.tv_description);
        desc=deskBottomSheet.findViewById(R.id.tvDesc);
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
        TextView locationAddressTop=deskBottomSheet.findViewById(R.id.locationAddress);
        locationAddress=deskBottomSheet.findViewById(R.id.tv_location_details);
        //New...
//        locationAddress.setVisibility(View.GONE);
        locationAddress.setVisibility(View.VISIBLE);
        locationAddressTop.setVisibility(View.VISIBLE);
        if (locationGlobal!=null &&
                !locationGlobal.equalsIgnoreCase("choose location from the list")) {
            locationAddressTop.setText("" + locationGlobal);
            locationAddressTop.setVisibility(View.VISIBLE);
        } else {
            locationAddressTop.setVisibility(View.GONE);
        }

        date=deskBottomSheet.findViewById(R.id.date);
        TextView title=deskBottomSheet.findViewById(R.id.title);
        TextView checkInDate=deskBottomSheet.findViewById(R.id.checkInDate);
        TextView showcheckInDate=deskBottomSheet.findViewById(R.id.showCheckInDate);

        TextView select=deskBottomSheet.findViewById(R.id.select_desk_room);

        TextView tvDelete=deskBottomSheet.findViewById(R.id.delete_text);
        tvDelete.setVisibility(View.GONE);
        TextView tvComments=deskBottomSheet.findViewById(R.id.tv_comments);
        EditText edComments=deskBottomSheet.findViewById(R.id.comments);
        EditText commentRegistration=deskBottomSheet.findViewById(R.id.ed_registration);
        EditText edRegistration=deskBottomSheet.findViewById(R.id.et_registration_num);
        RelativeLayout registrationLayout=deskBottomSheet.findViewById(R.id.registrationLayout);
        RelativeLayout repeatBlock=deskBottomSheet.findViewById(R.id.rl_repeat_block);
        RelativeLayout commentBlock=deskBottomSheet.findViewById(R.id.rl_comment_block);
        RelativeLayout teamsBlock=deskBottomSheet.findViewById(R.id.rl_teams_layout);
        CheckBox teamsCheckBox = deskBottomSheet.findViewById(R.id.teams_check_box);
        RelativeLayout dateBlock = deskBottomSheet.findViewById(R.id.bookingDateBlock);
        LinearLayout statusCheckLayout=deskBottomSheet.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=deskBottomSheet.findViewById(R.id.ll_desk_layout);
        LinearLayout capacitylayout=deskBottomSheet.findViewById(R.id.capacity_layout);
        tvcapacityCount=deskBottomSheet.findViewById(R.id.tv_capacity_no);

        showcheckInDate.setText(Utils.showBottomSheetDate(calSelectedDate));
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
            } else {
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

/*
        if (editDeskBookingDetails.getLocationAddress()!=null &&
                !editDeskBookingDetails.getLocationAddress().isEmpty()
                && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase(""))
            locationAddressTop.setText(""+editDeskBookingDetails.getLocationAddress());
*/

        if (dskRoomParkStatus == 1) {
//            Toast.makeText(context, ""+editDeskBookingDetails.getLocationAddress(), Toast.LENGTH_SHORT).show();
            if (editDeskBookingDetails.getLocationAddress()!=null &&
                    !editDeskBookingDetails.getLocationAddress().isEmpty()
                    && !editDeskBookingDetails.getLocationAddress().equalsIgnoreCase(""))
                locationAddress.setText(""+editDeskBookingDetails.getLocationAddress());

            tv_description.setText(editDeskBookingDetails.getDescription());
            if (tv_description.getText().toString().equalsIgnoreCase("") || tv_description.getText().toString().isEmpty()){
                tv_description.setVisibility(View.GONE);
                desc.setVisibility(View.GONE);
            }

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

            if (editDeskBookingDetails.getDeskCode()!=null && !editDeskBookingDetails.getDeskCode().isEmpty()
                    && !editDeskBookingDetails.getDeskCode().equalsIgnoreCase(""))
                deskRoomName.setText(editDeskBookingDetails.getDeskCode());
            else
                deskRoomName.setText("No Available desk/Room");

            selectedDeskId=editDeskBookingDetails.getDesktId();
            if(newEditStatus.equalsIgnoreCase("new") || newEditStatus.equalsIgnoreCase("new_deep_link")
                    || newEditStatus.equalsIgnoreCase("request")) {
//                title.setText("Book a workspace");
//                continueEditBook.setText("Book");
//                back.setText("Close");
                title.setText(appKeysPage.getBookWorkSpace());
                continueEditBook.setText(appKeysPage.getBook());
                back.setText(appKeysPage.getClose());
            } else {
                if (editDeskBookingDetails.getComments() != null &&
                        !editDeskBookingDetails.getComments().equalsIgnoreCase("")&&
                        !editDeskBookingDetails.getComments().isEmpty())
                    edComments.setText(editDeskBookingDetails.getComments());
                continueEditBook.setText("Continue");
                back.setText("Back");
            }

        }else {

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


        
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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
                if(!endDisabled) {
                    if (dskRoomParkStatus == 1
                            && newEditStatus.equalsIgnoreCase("edit")) {
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
                if (repeatActvieStatus) {
                    doRepeatDeskBookingForAWeek(editDeskBookingDetails,newEditStatus);
                    
                } else {
                    if (newEditStatus.equalsIgnoreCase("new"))
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
    
    
    //Repeat Module
    private void repeatBottomSheetDialog(String code) {

        repeatBottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        repeatBottomSheetDialog.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_repeat_new,
                new RelativeLayout(context))));

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
    private void openUntil(String code) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_until,
                new RelativeLayout(context))));

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
        String selectedDate=calSelectedDate;
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
    private void openWeeks() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_week,
                new RelativeLayout(context))));
        bottomSheetDialog.show();
    }


    //Get Desk list with lcoation or Team
    private void getAvaliableDeskDetails(String code, String date,BookingForEditResponse bookingForEditResponse) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        String toDate = Utils.getCurrentDate();
        String fromTime = "2022-09-23T00:00:00Z";
        String toTime = "2022-09-23T23:59:00Z";

        int parentId = SessionHandler.getInstance().getInt(context,AppConstants.PARENT_ID);
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
                        newDeskBookingSheet(bookingForEditResponse,code);
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
    private void getDeskList(String code,String date,String newEditStatus,BookingForEditResponse bookingForEditResponse) {
        if (Utils.isNetworkAvailable(context)) {
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

                    newDeskBookingSheet(bookingForEditResponse,code);

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
    //when change desk
    private void selectedDeskList(int selectedTeamIdChange,String date, 
                                  EditBookingDetails editBookingDetails,String newEditStatus) {
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
//                    //System.out.println("Selecrt id"+selectedTeamId + bookingDeskList.get(0).getDeskCode());
                    callDeskListBottomSheetDialog(1,editBookingDetails,newEditStatus);

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

    //Booking Api Call
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
                    if (isFrom.equalsIgnoreCase(AppConstants.HOMEFRAGMENTINSTANCESTRING)){
                        ((MainActivity) activityContext).callHomeFragment();
                    }
                    String resultString="";
                    try {
                        if (response.code()==200 && response.body().getResultCode()!=null){
//                        Utils.toastShortMessage(activityContext,"Update Success");
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
                                Utils.toastShortMessage(activityContext, resultString);
                            }
                        }else if (response.code() == 500){
                            Utils.toastShortMessage(activityContext,""+response.message());
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
    private void doRepeatDeskBookingForAWeek(EditBookingDetails editBookingDetails, String newEditStatus) {

        String selectedDate = calSelectedDate.toString();
        List<String> dateList=Utils.getCurrentWeekDateList(calSelectedDate,enableCurrentWeek);

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(context, AppConstants.TEAMMEMBERSHIP_ID));

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

        if (Utils.isNetworkAvailable(activityContext)) {
//            binding.locateProgressBar.setVisibility(View.VISIBLE);
            dialog = ProgressDialog.showProgressBar(context);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call=apiService.doRepeatBookingForWeek(locateBookingRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(context,dialog);
                    locateResponseHandler(response,activityContext.getResources().getString(R.string.booking_success));
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    binding.locateProgressBar.setVisibility(View.GONE);
                    ProgressDialog.dismisProgressBar(context,dialog);
                }
            });

        }else {
            Utils.toastMessage(activityContext, activityContext.getResources().getString(R.string.enable_internet));
        }

    }

    private void locateResponseHandler(Response<BaseResponse> response, String string) {

        String resultString = "";

        if (response.code() == 200) {
            if (response.body().getResultCode() != null && response.body().getResultCode().equalsIgnoreCase("ok")) {
                openCheckoutDialog(string,3);
//                callInitView();
            } else {

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
                //Utils.toastShortMessage(activityContext, "Booking Not Updated " + resultString);
                Utils.toastShortMessage(activityContext, resultString);
            }
        } else if (response.code() == 500) {
            Utils.toastShortMessage(activityContext, ""+response.message());
        } else if (response.code() == 401) {
            Utils.showCustomTokenExpiredDialog(activityContext, "401 Error Response");
        } else {
            Toast.makeText(activityContext, "Error", Toast.LENGTH_SHORT).show();
        }


    }
    private void callDeskListBottomSheetDialog(int id,EditBookingDetails editBookingDetails,
                                               String newEditStatus) {
        for (int i=0; i<activeTeamsList.size(); i++) {
            if (selectedTeamId==activeTeamsList.get(i).getId()) {
                selectedTeamName = activeTeamsList.get(i).getName();
                selectedTeamAutoApproveStatus = activeTeamsList.get(i).getAutomaticApprovalStatus();
            }
        }

        deskListBottomSheet = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
//        deskListBottomSheet.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk_new,
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
        TextView tvLocationAddress= deskListBottomSheet.findViewById(R.id.tv_location_address);
        bsRepeatBack=deskListBottomSheet.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(activityContext, LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);
        sheetDate.setText(Utils.calendarDay10thMonthformat(Utils.convertStringToDateFormet(calSelectedDate)));
        if(editBookingDetails.getDisplayTime()!=null)
            sheetTime.setText(""+editBookingDetails.getDisplayTime());

        if (false){

        }else if (false){

        }else {
            selectDesk.setText("Book a workspace");
            if (selectedTeamId == SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                tvTeamName.setText(selectedTeamName+" (default)");
            } else {
                tvTeamName.setText(selectedTeamName);
            }

            if (locationGlobal!=null
                    && !locationGlobal.equalsIgnoreCase("")
                    && !locationGlobal.isEmpty()){
                tvLocationAddress.setVisibility(View.VISIBLE);
                tvLocationAddress.setText(""+locationGlobal);
            } else {
                tvLocationAddress.setVisibility(View.GONE);
            }

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

            deskListRecyclerAdapter =new DeskListRecyclerAdapter(context,this,
                    activityContext,bookingForEditResponseDesk,context,deskListBottomSheet);
            */
            /*if(newEditStatus.equalsIgnoreCase("edit")){
                newDeskListForEditRecyclerAdapter = new NewDeskListForEditRecyclerAdapter(context,this,
                        activityContext,bookingDeskList,this,deskListBottomSheet,
                        id,editBookingDetails,newEditStatus);
                rvDeskRecycler.setAdapter(newDeskListForEditRecyclerAdapter);
            } else {

            }
            */
            try {
                newdeskListRecyclerAdapter = new DeskListBookAdapter(context,this,
                        activityContext,bookingDeskList,deskListBottomSheet,
                        id,editBookingDetails,newEditStatus,
                        Utils.checkStringParms(deskRoomName.getText().toString()));
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

        activeTeamsAdapter =new ActiveTeamsAdapterNew(context,this,
                activeTeamsList,activeTeamsBottomSheet,
                id,editBookingDetails,newEditStatus,selectedTeamId);
        rvActiveTeams.setAdapter(activeTeamsAdapter);



        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTeamsBottomSheet.dismiss();
            }
        });

        activeTeamsBottomSheet.show();
    }
    private void openIntervalsDialog(String type) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activityContext.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_interval,
                new RelativeLayout(context))));

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

    @Override
    public void onActiveTeamsSelected(int teamId, String teamName, int typeId, EditBookingDetails editBookingDetails,
                                      String newEditStatus) {
        if (deskListBottomSheet!=null)
            deskListBottomSheet.dismiss();

        if (newEditStatus.equalsIgnoreCase("edit")) {
            selectedTeamId = teamId;
            tvTeamName.setText(teamName);

            selectedDeskList(selectedTeamId,
                    Utils.getISO8601format(editBookingDetails.getDate()),editBookingDetails,newEditStatus);
//            getDeskList("-1", calSelectedDate,newEditStatus);
        } else {
            selectedTeamId = teamId;
            tvTeamName.setText(teamName);
            /*if (editDeskBookingDetails.getRequestedTeamId()>0)
                selectedDeskList(editDeskBookingDetails.getRequestedTeamId(),
                        Utils.getISO8601format(editDeskBookingDetails.getDate()),editDeskBookingDetails,newEditStatus);
            else*/
            selectedDeskList(selectedTeamId,
                    Utils.getISO8601format(editBookingDetails.getDate()),editBookingDetails,newEditStatus);
        }
    }

    @Override
    public void onChangeDesk(BookingForEditResponse.TeamDeskAvailabilities deskList, int deskId, String deskName, String request, String timeZone, int typeId, EditBookingDetails editBookingDetails, String newEditStatus, int teamId) {

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
        if (desc != null &&tv_description.getText().toString().equalsIgnoreCase(""))
            desc.setVisibility(View.GONE);

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
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,
                            R.color.figma_orange));
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
                if(request.equalsIgnoreCase("request")){
                    deskStatusText.setText("Available For Request");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figma_orange));

                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                } else {
                    deskStatusText.setText("Available");
                    deskStatusDot.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaLiteGreen));

                    if(teamId!=SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)){
                        editBookingDetailsGlobal.setRequestedTeamDeskId(deskId);
                        editBookingDetailsGlobal.setRequestedTeamId(selectedTeamId);
                    } else {
                        editBookingDetailsGlobal.setRequestedTeamDeskId(0);
                        editBookingDetailsGlobal.setRequestedTeamId(0);
                    }
                }

                /*if(roomBottomSheet!=null)
                    roomBottomSheet.dismiss();
                if(request.equalsIgnoreCase("new"))
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
}
