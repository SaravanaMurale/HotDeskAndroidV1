package dream.guys.hotdeskandroid.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.google.zxing.Result;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentBookingDetailBinding;
import dream.guys.hotdeskandroid.databinding.FragmentQrBinding;
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

public class QrFragment extends Fragment {
    FragmentQrBinding fragmentQrBinding;
    @BindView(R.id.tvSkip)
    TextView skip;
    @BindView(R.id.scanner_view)
    CodeScannerView scannerView;
    CodeScanner mCodeScanner;
    Activity activity;
    String action, bookName, bookAdddress, bookChecInTime, bookCheckOutTime, date;
    int deskId,teamId, teamMembershipId, calendarId;

    Dialog dialog;
    View view;
    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        fragmentQrBinding = FragmentQrBinding.inflate(inflater, container, false);
        View root = fragmentQrBinding.getRoot();
        dialog = new Dialog(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            action = bundle.getString("ACTION", null);
            bookName = bundle.getString("BOOK_NAME", null);
            bookAdddress = bundle.getString("BOOK_ADDRESS", null);
            bookChecInTime = bundle.getString("CHECK_IN_TIME", null);
            bookCheckOutTime = bundle.getString("CHECK_OUT_TIME", null);

            teamId = bundle.getInt("TEAM_ID", 0);
            deskId = bundle.getInt("DESK_ID", 0);
            teamMembershipId = bundle.getInt("TEAM_MEMBERSHIP_ID",0);
            calendarId = bundle.getInt("ID",0);
            date = bundle.getString("DATE","");

        }
        mCodeScanner = new CodeScanner(getActivity(), fragmentQrBinding.scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deskId == Integer.parseInt(result.getText())){
                            System.out.println("daidjas"+result.getText());
                            changeCheckIn();
                        }
                        else{
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog_validation);
                            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            TextView text = dialog.findViewById(R.id.tv_err_msg);
                            text.setText("You have scanned Wrong Desk");
                            TextView dialogButton = dialog.findViewById(R.id.tv_ok);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    navController.navigate(R.id.navigation_home);
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        }
                    }
                });
            }
        });
        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                Toast.makeText(activity, "calId "+thrown.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("calId calId erorr"+calendarId+" "+thrown.getMessage());


            }
        });
        fragmentQrBinding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        fragmentQrBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });

        fragmentQrBinding.tvChangeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(view);
                navController.navigate(R.id.action_change_schedule);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view =view;
        navController= Navigation.findNavController(view);


    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
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
                    if (response.code()==200 && response.body().getResultCode().equalsIgnoreCase("ok")){
                        Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                        SessionHandler.getInstance().save(getActivity(), AppConstants.USER_CURRENT_STATUS,"Checked IN");
                    } else {
                        if (response.code() == 200)
                            Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Error Check In", Toast.LENGTH_SHORT).show();

                    }

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

        dialog.setContentView(R.layout.layout_checkin_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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