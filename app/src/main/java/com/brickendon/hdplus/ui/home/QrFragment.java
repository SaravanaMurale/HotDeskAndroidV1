package com.brickendon.hdplus.ui.home;

import android.app.Activity;
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
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.databinding.FragmentBookingDetailBinding;
import com.brickendon.hdplus.databinding.FragmentQrBinding;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.BookingStatusRequest;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
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
    LanguagePOJO.Booking bookindata ;
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
        bookindata = Utils.getBookingPageScreenData(activity);

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
//                Toast.makeText(activity, "calId "+thrown.getMessage(), Toast.LENGTH_SHORT).show();
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
                    String resultString="";
                    if (response.code()==200){
                        if (response.body().getResultCode()!=null && response.body().getResultCode().equalsIgnoreCase("ok")){
//                        Toast.makeText(getActivity(), ""+response.body().getResultCode(), Toast.LENGTH_SHORT).show();
                            Utils.toastShortMessage(getActivity(), "Checked IN");
                            SessionHandler.getInstance().save(getActivity(), AppConstants.USER_CURRENT_STATUS,"Checked IN");
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
                            Utils.toastShortMessage(getActivity(), resultString);
                        }
                    } else if (response.code() == 500){
                        Utils.toastShortMessage(getActivity(),""+response.message());
                    }else if (response.code() == 401){
                        Utils.showCustomTokenExpiredDialog(getActivity(),"401 Error Response");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                    } else {
                        Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
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