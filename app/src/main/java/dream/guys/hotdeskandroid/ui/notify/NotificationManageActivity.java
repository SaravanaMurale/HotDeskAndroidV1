package dream.guys.hotdeskandroid.ui.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationCenter;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationList;
import dream.guys.hotdeskandroid.databinding.ActivityNotificationManageBinding;
import dream.guys.hotdeskandroid.model.request.DAODeskAccept;
import dream.guys.hotdeskandroid.model.request.DAODeskReject;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationsListActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationManageActivity extends AppCompatActivity {

    ActivityNotificationManageBinding binding;
    ArrayList<IncomingRequestResponse.Result> notiList;
    Context context;
    AdapterNotificationList adapterNotificationList;
    int cIncoming;

    ArrayList<IncomingRequestResponse.Result> incomingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_manage);
        binding = ActivityNotificationManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = NotificationManageActivity.this;

        Intent intent = getIntent();

        if (intent!=null) {

            try {
                notiList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra(AppConstants.SHOWNOTIFICATION);
                cIncoming = intent.getIntExtra(AppConstants.INCOMING,0);
                setAdapter();
            }catch (Exception e){
                Utils.toastMessage(context,e.getMessage());
            }


        }

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.txtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<IncomingRequestResponse.Result> manageList = adapterNotificationList.getList();

                if (manageList!=null && manageList.size()>0){

                    if (manageList.get(0).getEntityType() == 3){
                        //Desk...
                        callDeskAccept(manageList.get(0).getId(),manageList.get(0).getRequestedTeamDeskId());
                    }else if(manageList.get(0).getEntityType() == 4){
                        callMeetingApprove(manageList.get(0).getId());
                    }else if (manageList.get(0).getEntityType() == 5) {
                        callParkingAccept(manageList.get(0).getId());
                    }

                }

            }
        });

        binding.txtReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rejectPopUp();
            }
        });

    }

    private void callMeetingApprove(Integer id) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiService.acceptMeeting(String.valueOf(id));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("ACCEPT","Logout");
                    }else {
                        Log.d("ACCEPT","No Response" + response.code());
                    }
                }else {
                    Log.d("ACCEPT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Utils.toastMessage(context,t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callMeetingReject(Integer id, String reason) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> call = apiService.rejectMeeting(daoDeskReject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("ACCEPT","Logout");
                    }else {
                        Log.d("ACCEPT","No Response" + response.code());
                    }
                }else {
                    Log.d("ACCEPT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Utils.toastMessage(context,t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callParkingAccept(Integer id) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> params = new HashMap<>();
        params.put("", String.valueOf(id));

        Call<BaseResponse> call = apiService.acceptParking(String.valueOf(id));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("ACCEPT","Logout");
                    }else {
                        Log.d("ACCEPT","No Response" + response.code());
                    }
                }else {
                    Log.d("ACCEPT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Utils.toastMessage(context,t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callParkingReject(Integer id,String reason) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> call = apiService.rejectParking(daoDeskReject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("ACCEPT","Logout");
                    }else {
                        Log.d("ACCEPT","No Response" + response.code());
                    }
                }else {
                    Log.d("ACCEPT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Utils.toastMessage(context,t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callDeskAccept(int id,int reqTeamID) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        DAODeskAccept daoDeskAccept = new DAODeskAccept();
        daoDeskAccept.setId(id);
        daoDeskAccept.setRequestedTeamDeskId(reqTeamID);

        Call<BaseResponse> call = apiService.acceptDesk(daoDeskAccept);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("ACCEPT","Logout");
                    }else {
                        Log.d("ACCEPT","No Response" + response.code());
                    }
                }else {
                    Log.d("ACCEPT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("ACCEPT",t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callDeskReject(int id,String reason) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> calls = api.rejectDesk(daoDeskReject);

        calls.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                if (response.body()!=null) {
                    if (response.code() == 200){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        callIncomingNotification();
                    }else if(response.code() == 400){
                        Log.d("REJECT","Logout");
                    }else {
                        Log.d("REJECT","No Response" + response.code());
                    }
                }else {
                    Log.d("REJECT","No Response");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("REJECT",t.getMessage());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setAdapter() {

        if (notiList!=null && notiList.size()>0){
            adapterNotificationList = new AdapterNotificationList(context,notiList,"Manage",cIncoming,0);
            binding.outgoingRecyclerview.setAdapter(adapterNotificationList);
        }

    }

    private void rejectPopUp() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reject_reason);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        //text.setText("The option to login using a pin is now available. \n To enable please select continue");
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView dialogButtonCancel = dialog.findViewById(R.id.tv_cancel);

        EditText edt_reason = dialog.findViewById(R.id.edt_reason);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reason = edt_reason.getText().toString().trim();

                if (reason.isEmpty()){
                    Toast.makeText(context, "Enter Reason", Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<IncomingRequestResponse.Result> manageList = adapterNotificationList.getList();

                    if (manageList!=null && manageList.size()>0){
                        if (manageList.get(0).getEntityType() == 3){
                            //Desk...
                            callDeskReject(manageList.get(0).getId(),reason);
                        }else if(manageList.get(0).getEntityType() == 4){
                            callMeetingReject(manageList.get(0).getId(),reason);
                        }else if (manageList.get(0).getEntityType() == 5) {
                            callParkingReject(manageList.get(0).getId(),reason);
                        }
                    }

                    dialog.dismiss();
                }
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


    private void callIncomingNotification() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    if (response.body()!=null && response.body().getResults()!=null){
                        incomingList = new ArrayList<>();
                        incomingList.addAll(response.body().getResults());

                        incomingList.replaceAll(val ->{
                            val.setIncoming("incoming");
                            return val;
                        });
                        //incomingList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        notiList = new ArrayList<>();
                        notiList = (ArrayList<IncomingRequestResponse.Result>) incomingList.stream().filter(val -> val.getStatus() == 0).collect(Collectors.toList());
                        IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                        if (notiList.size()>0){
                            cIncoming = Collections.frequency(notiList, result);
                            setAdapter();
                        }


                    }else {
                    }
                }else if(response.code()==401){
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Utils.showCustomTokenExpiredDialog(NotificationManageActivity.this,"Token Expired");
                    SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}