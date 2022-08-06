package dream.guys.hotdeskandroid.ui.book;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

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
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.response.AmenitiesResponse;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DeskRoomCountResponse;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.CalendarView;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment implements MeetingListToEditAdapter.OnMeetingEditClickable {
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

    int selectedicon = 0;
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
                Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
                getMeetingBookingListToEdit(null,""+date,""+date);
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
    //Meeting room booking edit
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
}