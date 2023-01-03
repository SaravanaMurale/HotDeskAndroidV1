package dream.guys.hotdeskandroid.ui.book;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentBookingDetailBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.notify.NotificationCenterActivity;
import dream.guys.hotdeskandroid.ui.notify.UserNotificationActivity;
import dream.guys.hotdeskandroid.ui.settings.SettingsActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingDetailFragment extends Fragment {

    FragmentBookingDetailBinding fragmentBookingDetailBinding;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @BindView(R.id.bookDetailUserName)
    TextView bookDetailUserName;

    @BindView(R.id.bookingDetailDeskName)
    TextView bookingDetailDeskName;
    @BindView(R.id.bookingDetailAddress)
    TextView bookingDetailAddress;
    @BindView(R.id.bookingDetailCheckInTime)
    TextView bookingDetailCheckInTime;
    @BindView(R.id.bookingCheckOutTime)
    TextView bookingCheckOutTime;
    @BindView(R.id.checkInText)
    TextView checkInText;
    @BindView(R.id.tvSkip)
    TextView tvSkip;
    @BindView(R.id.user_current_status)
    TextView userCurrentStatus;

    @BindView(R.id.centerLayoutBlock)
    RelativeLayout centerLayoutBlock;
    @BindView(R.id.centerBlock)
    RelativeLayout centerBlock;
    @BindView(R.id.ivWorkingRemote)
    ImageView ivWorkingRemote;
    @BindView(R.id.noti_icon)
    ImageView notiIcon;
    @BindView(R.id.ivNotWorking)
    ImageView ivNotWorking;
    @BindView(R.id.user_status)
    ImageView userStatus;

    @BindView(R.id.bookingDetailsBlock)
    RelativeLayout bookingDetailsBlock;

    @BindView(R.id.btnCheckInNow)
    Button btnCheckInNow;

    Dialog dialog;

    String action, bookName, bookAdddress, bookChecInTime, bookCheckOutTime, date;
    int teamId, deskId, teamMembershipId, calendarId;

    //New...
    UserDetailsResponse profileData;
    ArrayList<IncomingRequestResponse.Result> notiList;


    View view;

    public BookingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view=inflater.inflate(R.layout.fragment_booking_detail, container, false);
        fragmentBookingDetailBinding = FragmentBookingDetailBinding.inflate(inflater, container, false);
        View root = fragmentBookingDetailBinding.getRoot();

        dialog = new Dialog(getContext());
        fragmentBookingDetailBinding.bookDetailUserName.setText(
                SessionHandler.getInstance().get(getContext(), AppConstants.USERNAME));
        fragmentBookingDetailBinding.teamName.setText(SessionHandler.getInstance().get(getContext(), AppConstants.CURRENT_TEAM));
        if (SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS) != null && SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS).equalsIgnoreCase("checked in")) {
            userCurrentStatus.setText("Checked In");
        } else if (SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS) != null && SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS).equalsIgnoreCase("checked out")) {
            userCurrentStatus.setText("Checked Out");
            userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
//            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
        } else {
            if (SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS) != null) {
                fragmentBookingDetailBinding.userCurrentStatus.setText(SessionHandler.getInstance().get(getActivity(), AppConstants.USER_CURRENT_STATUS));
                fragmentBookingDetailBinding.userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaGrey), android.graphics.PorterDuff.Mode.MULTIPLY);
            } else {
                fragmentBookingDetailBinding.userCurrentStatus.setText("In Office");
                fragmentBookingDetailBinding.userStatus.setColorFilter(ContextCompat.getColor(getActivity(), R.color.figmaBlueText), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
        }

        if (SessionHandler.getInstance().getBoolean(getActivity(), AppConstants.SHOWNOTIFICATION)) {
            fragmentBookingDetailBinding.notiIcon.setVisibility(View.VISIBLE);
        }

        loadTenantImage();
        fragmentBookingDetailBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });
        fragmentBookingDetailBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });
        fragmentBookingDetailBinding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        fragmentBookingDetailBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showSearch();
            }
        });
        fragmentBookingDetailBinding.bookDetailNotificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileData != null) {

                    Intent intent;
                    if (profileData.getHighestRole().equalsIgnoreCase(AppConstants.Administrator)
                            || profileData.getHighestRole().equalsIgnoreCase(AppConstants.FacilityManager)
                            || profileData.getHighestRole().equalsIgnoreCase(AppConstants.TeamManager)
                            || profileData.getHighestRole().equalsIgnoreCase(AppConstants.Manager)
                            || profileData.getHighestRole().equalsIgnoreCase(AppConstants.MeetingManager)) {
                        intent = new Intent(getActivity(), NotificationCenterActivity.class);
                    } else {
                        intent = new Intent(getActivity(), UserNotificationActivity.class);
                    }
                    intent.putExtra(AppConstants.SHOWNOTIFICATION, notiList);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), UserNotificationActivity.class);
                    intent.putExtra(AppConstants.SHOWNOTIFICATION, notiList);
                    startActivity(intent);
                }

            }
        });

        if (SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE) != null &&
                SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                || SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                || SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                || SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE).equalsIgnoreCase(AppConstants.Manager)
                || SessionHandler.getInstance().get(getActivity(), AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)) {
            loadNotification();
        } else {
            callOutGoingNotification();
        }


        Bundle bundle = getArguments();
        if (bundle != null) {
            action = bundle.getString("ACTION", null);
            bookName = bundle.getString("BOOK_NAME", null);
            bookAdddress = bundle.getString("BOOK_ADDRESS", null);
            bookChecInTime = bundle.getString("CHECK_IN_TIME", null);
            bookCheckOutTime = bundle.getString("CHECK_OUT_TIME", null);

            teamId = bundle.getInt("TEAM_ID", 0);
            deskId = bundle.getInt("DESK_ID", 0);
            teamMembershipId = bundle.getInt("TEAM_MEMBERSHIP_ID", 0);
            calendarId = bundle.getInt("ID", 0);
            date = bundle.getString("DATE", "");

        }

        if (action.equals(AppConstants.CHECKIN)) {
            fragmentBookingDetailBinding.bookingDetailDeskName.setText(bookName);
            fragmentBookingDetailBinding.bookingDetailAddress.setText(bookAdddress);
            fragmentBookingDetailBinding.bookingDetailCheckInTime.setText(bookChecInTime);
            fragmentBookingDetailBinding.bookingCheckOutTime.setText(bookCheckOutTime);
            fragmentBookingDetailBinding.centerLayoutBlock.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.centerBlock.setVisibility(View.GONE);
            fragmentBookingDetailBinding.checkInText.setText(AppConstants.OFFICE_TODAY);

        } else if (action.equals(AppConstants.REMOTE)) {
            fragmentBookingDetailBinding.btnCheckInNow.setVisibility(View.GONE);
            fragmentBookingDetailBinding.btnCheckInQr.setVisibility(View.GONE);
            fragmentBookingDetailBinding.bookingDetailDeskName.setText(bookName);
            fragmentBookingDetailBinding.bookingDetailCheckInTime.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.bookingCheckOutTime.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.bookingDetailAddress.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.checkInText.setVisibility(View.GONE);
            fragmentBookingDetailBinding.remoteText.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.remoteText.setText(bookName);
            fragmentBookingDetailBinding.centerLayoutBlock.setVisibility(View.GONE);
            fragmentBookingDetailBinding.centerBlock.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.ivWorkingRemote.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.ivNotWorking.setVisibility(View.GONE);
            fragmentBookingDetailBinding.bookingDetailsBlock.setVisibility(View.INVISIBLE);

            switch (bookName) {
                case "RQ":
                    fragmentBookingDetailBinding.remoteText.setText("Request for Desk In Progress");
                    Glide.with(this)
                            .load(R.drawable.building)
                            .placeholder(R.drawable.building)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    break;
                case "WFH":
                    fragmentBookingDetailBinding.remoteText.setText("You’re working remotely today");
                    Glide.with(this)
                            .load(R.drawable.working_remote)
                            .placeholder(R.drawable.working_remote)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    fragmentBookingDetailBinding.tvSkip.setText("Close");
                    break;
                case "WOO":
                    fragmentBookingDetailBinding.remoteText.setText("You're Working in alternative office");
                    Glide.with(this)
                            .load(R.drawable.building)
                            .placeholder(R.drawable.building)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    break;
                case "TR":
                    fragmentBookingDetailBinding.remoteText.setText("You're training");
                    Glide.with(this)
                            .load(R.drawable.building)
                            .placeholder(R.drawable.building)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    break;
                case "OO":
                    fragmentBookingDetailBinding.remoteText.setText("Out of office");
                    Glide.with(this)
                            .load(R.drawable.not_working)
                            .placeholder(R.drawable.not_working)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    break;
                case "SL":
                    fragmentBookingDetailBinding.remoteText.setText("You're Sick");
                    Glide.with(this)
                            .load(R.drawable.sick_remote)
                            .placeholder(R.drawable.sick_remote)
                            .into(fragmentBookingDetailBinding.ivWorkingRemote);
                    break;
            }
        }

        fragmentBookingDetailBinding.btnCheckInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCheckIn();
            }
        });
        fragmentBookingDetailBinding.btnCheckInQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changeCheckIn();
                if (checkPermission()) {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_qrFragment, bundle);
                } else
                    requestPermission();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    public void changeCheckIn() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog = ProgressDialog.showProgressBar(getContext());

            BookingStatusRequest bookingsRequest = new BookingStatusRequest();
            bookingsRequest.setCalendarEntryId(calendarId);
            bookingsRequest.setBookingStatus("IN");


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.bookingStatus(bookingsRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    if (response.code() == 200 && response.body().getResultCode().equalsIgnoreCase("ok")) {
                        SessionHandler.getInstance().save(getActivity(), AppConstants.USER_CURRENT_STATUS, "Checked IN");
                        openCheckoutDialog();
                    } else {
                        if (response.code() == 200)
                            Toast.makeText(getActivity(), "" + response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Error Check In", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "fail " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void openCheckoutDialog() {

        dialog.setContentView(R.layout.layout_checkin_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        NavController navController = Navigation.findNavController(view);

        TextView checkDialogClose = dialog.findViewById(R.id.checkDialogClose);
        TextView title = dialog.findViewById(R.id.title);

        title.setText("Check-in successful");
        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_home);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onResume() {
        super.onResume();
        //binding.homeUserName.setText(SessionHandler.getInstance().get(getContext(),AppConstants.USERNAME));
        profileData = Utils.getLoginData(getActivity());

    }

    private void loadNotification() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
            call.enqueue(new Callback<IncomingRequestResponse>() {
                @Override
                public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                    try {
                        if (response.code() == 200) {
                            notiList = new ArrayList<>();
                            if (response.body() != null && response.body().getResults() != null) {
                                notiList.addAll(response.body().getResults());
                                loo:
                                for (int i = 0; i < notiList.size(); i++) {
                                    if (notiList.get(i).getStatus() == 0) {
                                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION, true);
                                        notiIcon.setVisibility(View.VISIBLE);
                                        break loo;
                                    }
                                    SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION, false);
                                }
                            }
                        } else if (response.code() == 401) {
                            Utils.showCustomTokenExpiredDialog(getActivity(), "Token Expired");
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK, false);
//                        Utils.finishAllActivity(getContext());
                        }
                    } catch (Exception e){

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

    private void callOutGoingNotification() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                try {
                    if (response.code() == 200) {

                        notiList = new ArrayList<>();
                        if (response.body() != null && response.body().getResults() != null) {

                            notiList.addAll(response.body().getResults());
                            loo:
                            for (int i = 0; i < notiList.size(); i++) {
                                if (notiList.get(i).getStatus() == 0) {
                                    SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION, true);
                                    notiIcon.setVisibility(View.VISIBLE);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.SHOWNOTIFICATION, false);
                            }

                        } else {
                        }
                    }
                } catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {

            }
        });
    }

    private void loadTenantImage() {
        if (Utils.isNetworkAvailable(getActivity())) {

//            dialog= ProgressDialog.showProgressBar(getContext());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ImageResponse> call = apiService.getTenantImage();
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    try {

                        if (response.code() == 200) {
                            ImageResponse imageResponse = response.body();
                            if (imageResponse.getMessage() != null && !imageResponse.isStatus()) {
//                            Utils.toastMessage(getContext(),imageResponse.getMessage().getCode());
                                fragmentBookingDetailBinding.tenantProfile.setImageDrawable(ContextCompat.getDrawable(getContext(),
                                        R.drawable.default_company_logo));
                            }
                            if (imageResponse.getImage() != null) {
                                String cleanImage = imageResponse.getImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
                                SessionHandler.getInstance().save(getActivity(), AppConstants.TENANTIMAGE
                                        , cleanImage);
                                byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                fragmentBookingDetailBinding.tenantProfile.setImageBitmap(decodedByte);
                            }
                        } else if (response.code() == 401) {
//                        Utils.toastMessage(getActivity(),"Token Expired");
                            SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK, false);
                            Utils.showCustomTokenExpiredDialog(getActivity(), "Token Expired");
//                        Utils.finishAllActivity(getContext());
                        }
                    } catch (Exception e){

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

}