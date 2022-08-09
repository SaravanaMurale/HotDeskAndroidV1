package dream.guys.hotdeskandroid.ui.book;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.request.SelectCode;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.CalendarView;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment implements MeetingListToEditAdapter.OnMeetingEditClickable, BookingListToEditAdapter.OnEditClickable, DeskListRecyclerAdapter.OnSelectSelected, CarListToEditAdapter.CarEditClickable {
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
    int selectedicon = 0;
    String calSelectedDate="";

    TextView startTime,endTime,repeat,date,deskRoomName;
    String repeatValue="None";

    int teamId=0,teamMembershipId=0,selectedDeskId=0;

    RecyclerView rvHomeBooking,rvDeskRecycler;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nestedScrollView;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk;

    BookingForEditResponse bookingForEditResponse;
    View view;
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

        binding.calendarView.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date, int pos) {
                Toast.makeText(getActivity(), ""+Utils.getISO8601format(date), Toast.LENGTH_SHORT).show();
                if (selectedicon==0){
                    getBookingListToEdit("3",Utils.getISO8601format(date));
                    calSelectedDate=Utils.getISO8601format(date);
                } else if (selectedicon==1) {
                    getMeetingBookingListToEdit("" + Utils.getISO8601format(date), "" + Utils.getISO8601format(date));
                    calSelectedDate=Utils.getISO8601format(date);
                } else {
                    getCarParListToEdit(""+Utils.getISO8601format(date),""+Utils.getISO8601format(date));
                    calSelectedDate=Utils.getISO8601format(date);
                }
            }

            @Override
            public void onPrevClicked(String month) {
                Toast.makeText(getActivity(), ""+month, Toast.LENGTH_LONG).show();
                getDeskCount(month);
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
                selectedicon=3;
                tabToggleViewClicked(3);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
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
                getDeskCount(Utils.getCurrentDate());
                break;
            case 1:
                binding.roomLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivRoom.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvRoom.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams roomParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                roomParams.weight = 1.0f;
                binding.roomLayout.setLayoutParams(roomParams);
                getDeskCount(Utils.getCurrentDate());
                break;
            case 2:
                binding.parkingLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivParking.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvParking.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams parkingParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parkingParams.weight = 1.0f;
                binding.parkingLayout.setLayoutParams(parkingParams);
                getDeskCount(Utils.getCurrentDate());
                break;
            case 3:
                binding.moreLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivMore.setImageTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvMore.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                moreParams.weight = 1.0f;
                binding.moreLayout.setLayoutParams(moreParams);
                getDeskCount(Utils.getCurrentDate());
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
    private void getMeetingBookingListToEdit(String startDate, String endDate) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<MeetingListToEditResponse>> call=apiService.getMeetingListToEdit(startDate,endDate);
            call.enqueue(new Callback<List<MeetingListToEditResponse>>() {
                @Override
                public void onResponse(Call<List<MeetingListToEditResponse>> call, Response<List<MeetingListToEditResponse>> response) {

                    List<MeetingListToEditResponse> meetingListToEditResponseList  =response.body();

                    dialog.dismiss();
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    callMeetingRoomEditListAdapterBottomSheet(meetingListToEditResponseList);


                }

                @Override
                public void onFailure(Call<List<MeetingListToEditResponse>> call, Throwable t) {
                    dialog.dismiss();
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
            Call<CarParkingForEditResponse> call = apiService.getCarParkingEditList(startDate, endDate, 0);
            call.enqueue(new Callback<CarParkingForEditResponse>() {
                @Override
                public void onResponse(Call<CarParkingForEditResponse> call, Response<CarParkingForEditResponse> response) {

                    CarParkingForEditResponse carParkingForEditResponse = response.body();

                    CallCarBookingEditList(carParkingForEditResponse, "5");

               /* for (int i = 0; i <carParkingForEditResponse.getCarParkBookings().size() ; i++) {
                    System.out.println("CarParkingEditListVeHicleNumber"+carParkingForEditResponse.getCarParkBookings().get(i).getVehicleRegNumber());

                }*/

                     ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<CarParkingForEditResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }

    }
    private void CallCarBookingEditList(CarParkingForEditResponse carParkingForEditResponse, String code) {

        RecyclerView rvCarEditList;
        TextView editClose, editDate, bookingName;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateCarEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCarEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvCarEditList = locateCarEditBottomSheet.findViewById(R.id.rvEditList);
        editClose = locateCarEditBottomSheet.findViewById(R.id.editClose);
        editDate = locateCarEditBottomSheet.findViewById(R.id.editDate);
        bookingName = locateCarEditBottomSheet.findViewById(R.id.bookingName);

        //New...
        editDate.setText(Utils.dateWithDayString(calSelectedDate));

        bookingName.setText("Car Booking");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCarEditList.setLayoutManager(linearLayoutManager);
        rvCarEditList.setHasFixedSize(true);

        CarListToEditAdapter carListToEditAdapter = new CarListToEditAdapter(getContext(), carParkingForEditResponse.getCarParkBookings(), this, code);
        rvCarEditList.setAdapter(carListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCarEditBottomSheet.dismiss();
            }
        });

        locateCarEditBottomSheet.show();
    }


    private void callMeetingRoomEditListAdapterBottomSheet(List<MeetingListToEditResponse> meetingListToEditResponseList) {
        RecyclerView rvMeeingEditList;
        TextView editClose,editDate;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateMeetEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateMeetEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvMeeingEditList = locateMeetEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=locateMeetEditBottomSheet.findViewById(R.id.editClose);
        editDate=locateMeetEditBottomSheet.findViewById(R.id.editDate);
        editDate.setText(Utils.dateWithDayString(calSelectedDate));
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMeeingEditList.setLayoutManager(linearLayoutManager);
        rvMeeingEditList.setHasFixedSize(true);

        MeetingListToEditAdapter meetingListToEditAdapter=new MeetingListToEditAdapter(getContext(),meetingListToEditResponseList,this);
        rvMeeingEditList.setAdapter(meetingListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMeetEditBottomSheet.dismiss();
            }
        });

        //getMeetingListToEdit


        locateMeetEditBottomSheet.show();
    }

    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {

    }
    private void getBookingListToEdit(String code,String date) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

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

                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                    callBottomSheetToEdit(bookingForEditResponse, code);

                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
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
        }


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBookingDetails editBookingDetails= new EditBookingDetails();
                bookingForEditResponseDesk = bookingForEditResponse.getTeamDeskAvailabilities();
                for (int i=0; i<bookingForEditResponseDesk.size();i++){
                    if (bookingForEditResponse.getUserPreferences().getTeamDeskId()==bookingForEditResponseDesk.get(i).getTeamDeskId()){
                        editBookingDetails.setEditStartTTime(Utils.splitTime(bookingForEditResponseDesk.get(i).getAvailableTimeSlots().get(0).getFrom()));
                        editBookingDetails.setEditEndTime(Utils.splitTime(bookingForEditResponseDesk.get(i).getAvailableTimeSlots().get(0).getTo()));
                        editBookingDetails.setDate(Utils.convertStringToDateFormet(calSelectedDate));
                        editBookingDetails.setCalId(0);
                        editBookingDetails.setDeskCode(bookingForEditResponseDesk.get(i).getDeskCode());
                        editBookingDetails.setDeskStatus(0);
                    }
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

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));


        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        repeat=bottomSheetDialog.findViewById(R.id.repeat);
        deskRoomName=bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        date=bottomSheetDialog.findViewById(R.id.date);
        TextView back=bottomSheetDialog.findViewById(R.id.editBookingBack);
        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);
        TextView continueEditBook=bottomSheetDialog.findViewById(R.id.editBookingContinue);
        TextView tvComments=bottomSheetDialog.findViewById(R.id.tv_comments);
        EditText commentRegistration=bottomSheetDialog.findViewById(R.id.ed_registration);
        RelativeLayout repeatBlock=bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout teamsBlock=bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        LinearLayout statusCheckLayout=bottomSheetDialog.findViewById(R.id.status_check_layout);
        LinearLayout llDeskLayout=bottomSheetDialog.findViewById(R.id.ll_desk_layout);

        ChipGroup chipGroup = bottomSheetDialog.findViewById(R.id.list_item);

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

        if (dskRoomParkStatus == 1) {
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");
            chipGroup.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
        }else if (dskRoomParkStatus==2) {
            llDeskLayout.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            chipGroup.setVisibility(View.VISIBLE);
        }else {
            llDeskLayout.setVisibility(View.GONE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Registration Number");
            tvComments.setText("Regitration Number");
            commentRegistration.setText(editDeskBookingDetails.getVehicleRegNumber());
            chipGroup.setVisibility(View.GONE);
        }

        startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
        endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
        date.setText(""+Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
        deskRoomName.setText(editDeskBookingDetails.getDeskCode());

//        System.out.println("chip check"+editDeskBookingDetails.getAmenities().size());
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
        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonOuterObject = new JsonObject();
                JsonObject jsonInnerObject = new JsonObject();
                JsonObject jsonChangesObject = new JsonObject();
                JsonArray jsonChangesetArray = new JsonArray();
                JsonArray jsonDeletedIdsArray = new JsonArray();
                jsonInnerObject.addProperty("id",editDeskBookingDetails.getCalId());
                jsonInnerObject.addProperty("date",""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");
                switch (dskRoomParkStatus){
                    case 1:
                        jsonOuterObject.addProperty("teamId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAM_ID));
                        jsonOuterObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getActivity(),AppConstants.TEAMMEMBERSHIP_ID));
                        if (!commentRegistration.getText().toString().isEmpty() &&
                                !commentRegistration.getText().toString().equalsIgnoreCase(""))
                            jsonChangesObject.addProperty("comments",commentRegistration.getText().toString());
                        break;
                    case 2:
                        jsonOuterObject.addProperty("meetingRoomId",editDeskBookingDetails.getMeetingRoomtId());
                        break;
                    case 3:
                        jsonOuterObject.addProperty("parkingSlotId",editDeskBookingDetails.getParkingSlotId());
                        if (!commentRegistration.getText().toString().isEmpty() &&
                                !commentRegistration.getText().toString().equalsIgnoreCase(""))
                            jsonChangesObject.addProperty("vehicleRegNumber",commentRegistration.getText().toString());
                        break;
                }

                BookingsRequest bookingsRequest = new BookingsRequest();
                ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
                ArrayList<Integer> list1 =new ArrayList<>();

                BookingsRequest.ChangeSets changeSets = new BookingsRequest.ChangeSets();
                changeSets.setId(editDeskBookingDetails.getCalId());
                changeSets.setDate(""+Utils.getYearMonthDateFormat(editDeskBookingDetails.getDate())+"T00:00:00.000Z");
                JsonObject jsonObject = new JsonObject();
                if (selectedDeskId!=0){
                    jsonChangesObject.addProperty("teamDeskId",selectedDeskId);
//                        jsonObject.put("teamDeskId",selectedDeskId);
                }
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
                    editBookingCall(jsonOuterObject,position,dskRoomParkStatus);
                }
                selectedDeskId=0;
                bottomSheetDialog.dismiss();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDeskBookingDetails.getDeskStatus()!=1 && editDeskBookingDetails.getDeskStatus()!=2)
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
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

    }
    private void callDeskBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        TextView bsRepeatBack;
        rvDeskRecycler= bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsDeskBack);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,getActivity(),bookingForEditResponseDesk,getContext(),bottomSheetDialog);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);


        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }
    public void editBookingCall(JsonObject data,int position,int dskRoomStatus) {
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
                            openCheckoutDialog("Booking Updated");
                            tabToggleViewClicked(0);
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
    private void openCheckoutDialog(String mesg) {
        Dialog popDialog = new Dialog(getActivity());
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText(""+mesg);
        bookEditBottomSheet.dismiss();
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

    }

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
    }

    @Override
    public void onCarEditClick(CarParkingForEditResponse.CarParkBooking carParkBooking) {

    }
}