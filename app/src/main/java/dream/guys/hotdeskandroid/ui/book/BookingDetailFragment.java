package dream.guys.hotdeskandroid.ui.book;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentBookingDetailBinding;
import dream.guys.hotdeskandroid.model.request.BookingStatusRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
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

    @BindView(R.id.centerLayoutBlock)
    RelativeLayout centerLayoutBlock;
    @BindView(R.id.centerBlock)
    RelativeLayout centerBlock;
    @BindView(R.id.ivWorkingRemote)
    ImageView ivWorkingRemote;
    @BindView(R.id.ivNotWorking)
    ImageView ivNotWorking;

    @BindView(R.id.bookingDetailsBlock)
    RelativeLayout bookingDetailsBlock;

    @BindView(R.id.btnCheckInNow)
    Button btnCheckInNow;

    Dialog dialog;

    String action, bookName, bookAdddress, bookChecInTime, bookCheckOutTime, date;
    int teamId, teamMembershipId, calendarId;


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
        fragmentBookingDetailBinding.bookDetailUserName.setText(SessionHandler.getInstance().get(getContext(), AppConstants.USERNAME));

        Bundle bundle = getArguments();
        if (bundle != null) {
            action = bundle.getString("ACTION", null);
            bookName = bundle.getString("BOOK_NAME", null);
            bookAdddress = bundle.getString("BOOK_ADDRESS", null);
            bookChecInTime = bundle.getString("CHECK_IN_TIME", null);
            bookCheckOutTime = bundle.getString("CHECK_OUT_TIME", null);

            teamId = bundle.getInt("TEAM_ID", 0);
            teamMembershipId = bundle.getInt("TEAM_MEMBERSHIP_ID",0);
            calendarId = bundle.getInt("ID",0);
            date = bundle.getString("DATE","");

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
            fragmentBookingDetailBinding.bookingDetailDeskName.setText(bookName);
            fragmentBookingDetailBinding.bookingDetailCheckInTime.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.bookingCheckOutTime.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.bookingDetailAddress.setVisibility(View.INVISIBLE);
            fragmentBookingDetailBinding.checkInText.setText(AppConstants.WORKING_REMOTE);
            fragmentBookingDetailBinding.centerLayoutBlock.setVisibility(View.GONE);
            fragmentBookingDetailBinding.centerBlock.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.ivWorkingRemote.setVisibility(View.VISIBLE);
            fragmentBookingDetailBinding.ivNotWorking.setVisibility(View.GONE);
            fragmentBookingDetailBinding.bookingDetailsBlock.setVisibility(View.INVISIBLE);


        }


        fragmentBookingDetailBinding.btnCheckInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCheckIn();

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view =view;
    }

    public void changeCheckIn() {
        if (Utils.isNetworkAvailable(getActivity())) {
            dialog= ProgressDialog.showProgressBar(getContext());

            BookingStatusRequest bookingsRequest = new BookingStatusRequest();
            bookingsRequest.setCalendarEntryId(calendarId);
            bookingsRequest.setBookingStatus("IN");


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.bookingStatus(bookingsRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    openCheckoutDialog();
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

    private void openCheckoutDialog() {

        dialog.setContentView(R.layout.layout_checkout_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        NavController navController= Navigation.findNavController(view);

        TextView checkDialogClose = dialog.findViewById(R.id.checkDialogClose);

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_home);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}