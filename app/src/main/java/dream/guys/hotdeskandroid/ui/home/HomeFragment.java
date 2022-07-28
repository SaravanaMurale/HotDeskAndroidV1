package dream.guys.hotdeskandroid.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.HomeBookingListAdapter;

import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.request.BookingsRequest;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeBookingListAdapter.OnCheckInClickable, DeskListRecyclerAdapter.OnSelectSelected {

    FragmentHomeBinding binding;
    TextView text;
    TextView userCurrentStatus;
    ImageView userProfile;
    ImageView profile;
    ImageView tenantProfile;
    Toolbar toolbar;
    LinearLayout headBlock,homeLayout,searchLayout;
    FrameLayout qrLayout;

    //Header
    ImageView notiIcon,closeSearch;
    ImageView userStatus;

    //HomeBooking
    RecyclerView rvHomeBooking,rvDeskRecycler;
    HomeBookingListAdapter homeBookingListAdapter;
    DeskListRecyclerAdapter deskListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    NestedScrollView nestedScrollView;
    LinearLayoutManager desklinearLayoutManager;
    List<BookingListResponse> bookingListResponseList;

    //EditBooking
    TextView startTime,endTime,repeat,date,deskRoomName;
    String repeatValue="None";

    View view;
    Dialog dialog;
    int teamId=0,teamMembershipId=0,selectedDeskId=0;
    ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList;
    ArrayList<IncomingRequestResponse.Result> notiList;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse;
    boolean qrEnabled = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        dialog = new Dialog(getContext());

        System.out.println("Seesin userId"+SessionHandler.getInstance().getInt(getActivity(),AppConstants.USER_ID));
        userProfile = root.findViewById(R.id.user_profile_pic);
        notiIcon = root.findViewById(R.id.noti_icon);
        profile = root.findViewById(R.id.profile);
        userCurrentStatus = root.findViewById(R.id.user_current_status);
        headBlock = root.findViewById(R.id.HeadBlock);
        nestedScrollView = root.findViewById(R.id.nestedView);
        qrLayout = root.findViewById(R.id.nestedView);
        userStatus=root.findViewById(R.id.user_status);
        tenantProfile=root.findViewById(R.id.tentant_image_view);
        rvHomeBooking=root.findViewById(R.id.rvHomeBooking);
        homeLayout=root.findViewById(R.id.home_layout);
        searchLayout=root.findViewById(R.id.search_layout);
        closeSearch=root.findViewById(R.id.close);

        if (SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS)!=null && SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS).equalsIgnoreCase("checked in")){
            userCurrentStatus.setText("Checked In");
            userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.teal_200), android.graphics.PorterDuff.Mode.MULTIPLY);
        }else if (SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS)!=null && SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS).equalsIgnoreCase("checked out")){
            userCurrentStatus.setText("Checked Out");
            userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
//            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
        }else {
            if (SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS)!=null){
                userCurrentStatus.setText(SessionHandler.getInstance().get(getActivity(),AppConstants.USER_CURRENT_STATUS));
                userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else {
                userCurrentStatus.setText("In Office");
                userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaBlue), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }
       binding.searchIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchLayout.setVisibility(View.VISIBLE);
           }
       });
       binding.close.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchLayout.setVisibility(View.GONE);
           }
       });
       userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.bottomSheetEditYourBooking(getContext(),getActivity(),"message","dad");
            }
        });

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        binding.homeUserName.setText(SessionHandler.getInstance().get(getContext(),AppConstants.USERNAME));
        binding.homeTeamName.setText(SessionHandler.getInstance().get(getContext(),AppConstants.CURRENT_TEAM));
/*
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePicker(getContext(),getActivity(),"Start","30th date");
            }
        });
*/
//        doTokenExpiryHere();
        if (!SessionHandler.getInstance().getBoolean(getActivity(),AppConstants.PIN_SETUP_DONE)){
            checkPinPopUp();
        }

        qrEnabledCall();
        loadUserImage();
        loadTenantImage();
        loadNotification();
        loadHomeList();

        return root;
    }

    private void qrEnabledCall() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Boolean> call = apiService.getQrEnabled();
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.code()==200){
                        qrEnabled = response.body();
                        qrEnabled = true;
                    }else if(response.code()==401){
                        Utils.showCustomAlertDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
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

    private void checkPinPopUp() {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
            int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.20);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_pin_pop_up);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView text = dialog.findViewById(R.id.tv_err_msg);
            text.setText("The option to login using a pin is now available. \n To enable please select continue");
            TextView dialogButton = dialog.findViewById(R.id.tv_ok);
            TextView dialogButtonCancel = dialog.findViewById(R.id.tv_cancel);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CreatePinActivity.class);
                    getActivity().startActivity(intent);
                    dialog.dismiss();
                }
            });
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void loadNotification() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<IncomingRequestResponse> call = apiService.getIncomingRequest(false);
            call.enqueue(new Callback<IncomingRequestResponse>() {
                @Override
                public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                    if(response.code()==200){
                        notiList = new ArrayList<>();
                        if (response.body().getResults()!=null){
                            notiList.addAll(response.body().getResults());
                            loo :
                            for (int i=0;i<notiList.size();i++){
                                if (notiList.get(i).getStatus()==0){
                                    SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION,true);
                                    notiIcon.setVisibility(View.VISIBLE);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION,false);
                            }
                        }
                    }else if(response.code()==401){
                        Utils.toastMessage(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
                    }
                }
                @Override
                public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void loadTenantImage() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ImageResponse> call = apiService.getTenantImage();
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response.code()==200){
                        ImageResponse imageResponse = response.body();
                        if (imageResponse.getMessage()!=null && !imageResponse.isStatus()){
//                            Utils.toastMessage(getContext(),imageResponse.getMessage().getCode());
                            tenantProfile.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.default_company_logo));
                        }
                        if (imageResponse.getImage()!=null){
                            String cleanImage = imageResponse.getImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
                            SessionHandler.getInstance().save(getActivity(), AppConstants.TENANTIMAGE
                                    , cleanImage);
                            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            tenantProfile.setImageBitmap(decodedByte);
                        }
                    }else if(response.code()==401){
                        Utils.toastMessage(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
                    }
                }
                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void loadUserImage() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ImageResponse> call = apiService.getUserImage();
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response.code()==200){
                        ImageResponse imageResponse = response.body();
                        if (imageResponse.getMessage()!=null && !imageResponse.isStatus()){
//                            Utils.toastMessage(getContext(),imageResponse.getMessage().getCode());
                            userProfile.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.mygirl));
                        }
                        if (imageResponse.getImage()!=null){
                            String cleanImage = imageResponse.getImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
                            SessionHandler.getInstance().save(getActivity(), AppConstants.USERIMAGE
                                    , cleanImage);
                            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            userProfile.setImageBitmap(decodedByte);
                        }
                    }else if(response.code()==401){
                        Utils.showCustomAlertDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
                    }
                }
                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //loadHomeList();
        this.view=view;
    }
    private void doTokenExpiryHere() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
                Utils.finishAllActivity(getContext());
            }
        },5000);
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
                            loadHomeList();
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

    public void changeCheckOut(BookingListResponse.DayGroup data, int pos) {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            BookingStatusRequest bookingsRequest = new BookingStatusRequest();
            bookingsRequest.setCalendarEntryId(data.getCalendarEntriesModel().getId());
            bookingsRequest.setBookingStatus("OUT");

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.bookingStatus(bookingsRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    BookingListResponse.DayGroup.CalendarEntry.Booking.Status calendarEntry = recyclerModelArrayList.get(pos).getCalendarEntriesModel().getBooking().getStatus();
                    calendarEntry.setId(1);
                    homeBookingListAdapter.notifyItemChanged(pos);
                    SessionHandler.getInstance().save(getActivity(),AppConstants.USER_CURRENT_STATUS,"Checked Out");
                    userCurrentStatus.setText("Checked Out");
                    userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaGrey), android.graphics.PorterDuff.Mode.MULTIPLY);

                    openCheckoutDialog("Checked Out Successfully");
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }
    private void loadHomeList(){

        if (Utils.isNetworkAvailable(getActivity())) {

            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(),true);
//            Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-07-18",true);
            //Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-07-04",true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                    if(response.code()==200){
                        BookingListResponse bookingListResponse  =response.body();
                        teamId = bookingListResponse.getTeamId();
                        teamMembershipId = bookingListResponse.getTeamMembershipId();
                        createRecyclerList(bookingListResponse);
                        loadDeskList();
                        ProgressDialog.dismisProgressBar(getContext(),dialog);

                    }else if(response.code()==401){
                        //Handle if token got expired
                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
//                        redirectToBioMetricAccess();

                    }
                }
                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void loadDeskList() {
        if (Utils.isNetworkAvailable(getActivity())) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(teamId,teamMembershipId,Utils.getCurrentDate(),Utils.getCurrentDate());
            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    if (response.code()==200){
                        bookingForEditResponse = response.body().getTeamDeskAvailabilities();
                        for (int i=0;i<bookingForEditResponse.size();i++){
                            System.out.println("desk Code Check"+bookingForEditResponse.get(i).getDeskCode());

                        }
                    }else if(response.code()==401){
                        Utils.showCustomAlertDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.finishAllActivity(getContext());
                    }
                    ProgressDialog.dismisProgressBar(getContext(),dialog);
//                    createRecyclerDeskList(response.body().getTeamDeskAvailabilities());
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


    private void createRecyclerDeskList(List<BookingForEditResponse.TeamDeskAvailabilities> body) {
        List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse = body;


//        ProgressDialog.dismisProgressBar(getContext(),dialog);

    }
    private void createRecyclerList(BookingListResponse body) {
        BookingListResponse bookingListResponses = body;
        recyclerModelArrayList = new ArrayList<>();
//        ArrayList<BookingListResponse> recyclerModelArrayList = new ArrayList<>();
        for (int i=0; i<bookingListResponses.getDayGroups().size(); i++){
            boolean dateCheck =true;
            System.out.println("bala time format"+bookingListResponses.getDayGroups().get(i).getDate());
            Date date = bookingListResponses.getDayGroups().get(i).getDate();
            System.out.println("bala time format"+date);
            ArrayList<BookingListResponse.DayGroup.CalendarEntry> calendarEntries = null;
            ArrayList<BookingListResponse.DayGroup.MeetingBooking> meetingEntries = null;
            ArrayList<BookingListResponse.DayGroup.CarParkBooking> carParkEntries = null;

            if (bookingListResponses.getDayGroups().get(i).getCalendarEntries()!=null){
                calendarEntries =
                        bookingListResponses.getDayGroups().get(i).getCalendarEntries();
            }
            if (bookingListResponses.getDayGroups().get(i).getMeetingBookings()!=null){
                meetingEntries =
                        bookingListResponses.getDayGroups().get(i).getMeetingBookings();
            }
            if (bookingListResponses.getDayGroups().get(i).getCarParkBookings()!=null){
                carParkEntries =
                        bookingListResponses.getDayGroups().get(i).getCarParkBookings();
            }

            if (calendarEntries!=null){
                for (int j=0; j < calendarEntries.size(); j++){
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
                    if (dateCheck){
                        momdel.setDateStatus(true);
                        momdel.setCalDeskStatus(1);
                        momdel.setDate(date);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                        dateCheck=false;
                    }else {
                        momdel.setDateStatus(false);
                        momdel.setCalDeskStatus(1);
                        momdel.setDate(date);
                        momdel.setCalendarEntriesModel(calendarEntries.get(j));
                    }
                    recyclerModelArrayList.add(momdel);
                }
            }
            if (meetingEntries!=null){
                for (int j=0; j < meetingEntries.size(); j++){
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
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
                    BookingListResponse.DayGroup momdel = new BookingListResponse.DayGroup();
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
            }

        }

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvHomeBooking.setLayoutManager(linearLayoutManager);
        rvHomeBooking.setHasFixedSize(true);

       // homeBookingListAdapter=new HomeBookingListAdapter(getContext(), getActivity(), recyclerModelArrayList);
        homeBookingListAdapter=new HomeBookingListAdapter(getContext(),this,recyclerModelArrayList,getActivity(),this);
        rvHomeBooking.setAdapter(homeBookingListAdapter);

        ProgressDialog.dismisProgressBar(getContext(),dialog);

    }


    private void redirectToBioMetricAccess() {
        SessionHandler.getInstance().saveBoolean(getContext(),AppConstants.TOKEN_EXPIRY_STATUS,true);
        Utils.finishAllActivity(getContext());
    }

    @Override
    public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click, Date date,int position) {
        if(click.equals(AppConstants.CHECKIN) || click.equals(AppConstants.REMOTE)){
            //Checkin
            System.out.println("BookingNameDest"+calendarEntriesModel.getUsageTypeName());
            NavController navController= Navigation.findNavController(view);
            Bundle bundle=new Bundle();
            if(click.equals(AppConstants.CHECKIN)){
                bundle.putString("ACTION",AppConstants.CHECKIN);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getBooking().getDeskCode());
                bundle.putString("BOOK_ADDRESS","address");
                bundle.putString("CHECK_IN_TIME",Utils.splitTime(calendarEntriesModel.getFrom()));
                bundle.putString("CHECK_OUT_TIME",Utils.splitTime(calendarEntriesModel.getMyto()));

                bundle.putInt("TEAM_ID",teamId);
                bundle.putInt("TEAM_MEMBERSHIP_ID",teamMembershipId);
                bundle.putString("DATE",""+Utils.getISO8601format(date));
                bundle.putInt("ID",calendarEntriesModel.getId());

            } else if(click.equals(AppConstants.REMOTE)){
                bundle.putString("ACTION",AppConstants.REMOTE);
                bundle.putString("BOOK_NAME",calendarEntriesModel.getUsageTypeName());

            }
            if (qrEnabled){
                navController.navigate(R.id.action_qrFragment,bundle);
            } else
                navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment,bundle);
        } else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("BookingEditClicked");
            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(calendarEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(calendarEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);
            editDeskBookingDetails.setCalId(calendarEntriesModel.getId());
            editDeskBookingDetails.setDeskCode(calendarEntriesModel.getBooking().getDeskCode());
            editDeskBookingDetails.setDeskStatus(calendarEntriesModel.getBooking().getStatus().getId());
            editBookingUsingBottomSheet(editDeskBookingDetails,1,position);

        }
    }

    @Override
    public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click, Date date, int position) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameMeeting" + meetingEntriesModel.getMeetingRoomName());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", meetingEntriesModel.getMeetingRoomName());
            bundle.putString("BOOK_ADDRESS", "Address");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(meetingEntriesModel.getFrom()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(meetingEntriesModel.getMyto()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("MeetingEditClicked");

            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(meetingEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(meetingEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);
            editDeskBookingDetails.setCalId(meetingEntriesModel.getId());
            editDeskBookingDetails.setMeetingRoomtId(meetingEntriesModel.getMeetingRoomId());

            editBookingUsingBottomSheet(editDeskBookingDetails,2,position);
        }
    }

    @Override
    public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click, Date date,int position) {

        if(click.equals(AppConstants.CHECKIN)) {
            //Checkin
            System.out.println("BookingNameCar" + carParkingEntriesModel.getParkingSlotCode());
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("BOOK_NAME", carParkingEntriesModel.getParkingSlotCode());
            bundle.putString("BOOK_ADDRESS", "Addresss");
            bundle.putString("CHECK_IN_TIME", Utils.splitTime(carParkingEntriesModel.getFrom()));
            bundle.putString("CHECK_OUT_TIME", Utils.splitTime(carParkingEntriesModel.getMyto()));
            navController.navigate(R.id.action_navigation_home_to_bookingDetailFragment, bundle);

        }else if(click.equals(AppConstants.EDIT)){
            //Edit
            System.out.println("CarParkingEditClicked");
            EditBookingDetails editDeskBookingDetails=new EditBookingDetails();
            editDeskBookingDetails.setCalId(carParkingEntriesModel.getId());
            editDeskBookingDetails.setParkingSlotId(carParkingEntriesModel.getParkingSlotId());
            editDeskBookingDetails.setEditStartTTime(Utils.splitTime(carParkingEntriesModel.getFrom()));
            editDeskBookingDetails.setEditEndTime(Utils.splitTime(carParkingEntriesModel.getMyto()));
            editDeskBookingDetails.setDate(date);
            editDeskBookingDetails.setVehicleRegNumber(carParkingEntriesModel.getVehicleRegNumber());
            editDeskBookingDetails.setParkingSlotCode(carParkingEntriesModel.getParkingSlotCode());
            editBookingUsingBottomSheet(editDeskBookingDetails,3,position);
        }
    }

    private void editBookingUsingBottomSheet(EditBookingDetails editDeskBookingDetails,int dskRoomParkStatus,int position) {

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
            chipGroup.setVisibility(View.GONE);
        }else if (editDeskBookingDetails.getDeskStatus() == 2){
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            select.setTextColor(getActivity().getResources().getColor(R.color.figmaGrey));
            statusCheckLayout.setVisibility(View.VISIBLE);
            chipGroup.setVisibility(View.GONE);
        } else {
            startTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            endTime.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
            statusCheckLayout.setVisibility(View.GONE);
            chipGroup.setVisibility(View.GONE);
        }

        if (dskRoomParkStatus == 1){
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Comments");
            tvComments.setText("Comments");

        }else if (dskRoomParkStatus==2){
            llDeskLayout.setVisibility(View.GONE);
        }else {
            llDeskLayout.setVisibility(View.GONE);
            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            commentRegistration.setHint("Registration Number");
            tvComments.setText("Regitration Number");
            commentRegistration.setText(editDeskBookingDetails.getVehicleRegNumber());
        }

        startTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()));
        endTime.setText(Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()));
        date.setText(""+Utils.dayDateMonthFormat(editDeskBookingDetails.getDate()));
        deskRoomName.setText(editDeskBookingDetails.getDeskCode());


        for (int i=0; i<5; i++){
            Chip chip = new Chip(getContext());
            chip.setId(i);
            chip.setText("ABC "+i);
            chip.setChipBackgroundColorResource(R.color.figmaGrey);
            chip.setCloseIconVisible(false);
            chip.setTextColor(getContext().getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
            chipGroup.addView(chip);
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
                        jsonOuterObject.addProperty("teamId",teamId);
                        jsonOuterObject.addProperty("teamMembershipId",teamMembershipId);
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
                if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditStartTTime()).equalsIgnoreCase(startTime.getText().toString())){
                    jsonChangesObject.addProperty("from", "2000-01-01T"+Utils.convert12HrsTO24Hrs(startTime.getText().toString())+":00.000Z");

                }if (!Utils.convert24HrsTO12Hrs(editDeskBookingDetails.getEditEndTime()).equalsIgnoreCase(endTime.getText().toString())){
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
                callRepeatBottomSheetDialog();
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

        deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,getActivity(),bookingForEditResponse,getContext(),bottomSheetDialog);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);


        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }
    private void callRepeatBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_repeat_booking,
                new RelativeLayout(getContext()))));

        RelativeLayout repeatNoneBlock,repeatDailyBlock,repeatWeeklyBlock,repeatMonthlyBlock,repeatYearlyBlock;
        ImageView noneTickIv,dailyTickIv,weeklyTickIv,monthlyTickIv,yearlyTickIv;
        TextView bsRepeatBack;

        repeatNoneBlock=bottomSheetDialog.findViewById(R.id.repeatNoneBlock);
        repeatDailyBlock=bottomSheetDialog.findViewById(R.id.repeatDailyBlock);
        repeatWeeklyBlock=bottomSheetDialog.findViewById(R.id.repeatWeeklyBlock);
        repeatMonthlyBlock=bottomSheetDialog.findViewById(R.id.repeatMonthlyBlock);
        repeatYearlyBlock=bottomSheetDialog.findViewById(R.id.repeatYearlyBlock);

        noneTickIv=bottomSheetDialog.findViewById(R.id.noneTickIv);
        dailyTickIv=bottomSheetDialog.findViewById(R.id.dailyTickIv);
        weeklyTickIv=bottomSheetDialog.findViewById(R.id.weeklyTickIv);
        monthlyTickIv=bottomSheetDialog.findViewById(R.id.monthlyTickIv);
        yearlyTickIv=bottomSheetDialog.findViewById(R.id.yearlyTickIv);

        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsRepeatBack);

        repeatNoneBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat.setText("None");
                noneTickIv.setVisibility(View.VISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatDailyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                repeat.setText("Daily");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.VISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatWeeklyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Weekly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.VISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatMonthlyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Monthly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.VISIBLE);
                yearlyTickIv.setVisibility(View.INVISIBLE);

                bottomSheetDialog.dismiss();
            }
        });

        repeatYearlyBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repeat.setText("Yearly");
                noneTickIv.setVisibility(View.INVISIBLE);
                dailyTickIv.setVisibility(View.INVISIBLE);
                weeklyTickIv.setVisibility(View.INVISIBLE);
                monthlyTickIv.setVisibility(View.INVISIBLE);
                yearlyTickIv.setVisibility(View.VISIBLE);

                bottomSheetDialog.dismiss();
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

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
    }


    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

    }
*/
}