package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.AdapterAdminNotificationReq;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationCenter;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationList;
import dream.guys.hotdeskandroid.model.request.DAODeskAccept;
import dream.guys.hotdeskandroid.model.request.DAODeskReject;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.notify.NotificationCenterActivity;
import dream.guys.hotdeskandroid.ui.notify.NotificationManageActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsListActivity extends AppCompatActivity implements AdapterNotificationList.AccRejInterface,
AdapterAdminNotificationReq.AccRejReqInterface{

    ViewPager mainViewpager;
    TabLayout tabLayout;
    InComingNotiFragment inComingNotiFragment;
    OutgoingNotiFragment outgoingNotiFragment;
    PastNotiFragment pastNotiFragment;

    ArrayList<IncomingRequestResponse.Result> notiList;
    ArrayList<IncomingRequestResponse.Result> IncomingList;
    ArrayList<IncomingRequestResponse.Result> OutGoingList;
    AdapterNotificationList adapterNotificationList;
    RecyclerView recyclerView;
    TextView tv_manage;
    ImageView profile_back;

    int cIncoming = 0,cOutGoing = 0;
    Context context;
    ProgressBar locateProgressBar;


    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponseDesk=new ArrayList<>();
    public List<BookingForEditResponse.TeamDeskAvailabilities> bookingDeskList=new ArrayList<>();

    AdapterAdminNotificationReq adminNotificationReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_list);

        uiInit();
        getDeskList("0",Utils.getCurrentDate());
        context = NotificationsListActivity.this;

        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)){
            tv_manage.setVisibility(View.VISIBLE);
        }else {
            tv_manage.setVisibility(View.GONE);
        }

        tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (notiList.size()>0){
                    ArrayList<IncomingRequestResponse.Result> manageList = new ArrayList<>();

                    manageList = (ArrayList<IncomingRequestResponse.Result>) notiList.stream().filter(val -> val.getStatus() == 0).collect(Collectors.toList());
                    IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                    ArrayList<IncomingRequestResponse.Result> inComingList = new ArrayList<>();
                    inComingList = (ArrayList<IncomingRequestResponse.Result>) manageList.stream().filter(val -> val.getIncoming().equalsIgnoreCase("incoming")).collect(Collectors.toList());

                    if (inComingList!=null && inComingList.size()>0){

                        int c = Collections.frequency(inComingList, result);
                        Intent intent = new Intent(NotificationsListActivity.this, NotificationManageActivity.class);
                        intent.putExtra(AppConstants.SHOWNOTIFICATION,inComingList);
                        intent.putExtra(AppConstants.INCOMING,c);
                        startActivity(intent);
                    }else {
                        Toast.makeText(context, "No Incoming Notifications", Toast.LENGTH_SHORT).show();
                    }

                /*for (int i=0;i<notiList.size();i++) {

                    if (notiList.get(i).getStatus() == 0){
                        manageList.add(notiList.get(i));
                    }

                }*/

                }else {
                    Toast.makeText(context, "No Incoming Notifications", Toast.LENGTH_SHORT).show();
                }

            }
        });

        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void uiInit() {
        tabLayout = findViewById(R.id.tabs);
        mainViewpager = findViewById(R.id.viewpager);
        recyclerView = findViewById(R.id.outgoing_recyclerview);
        tv_manage= findViewById(R.id.tv_manage);
        profile_back = findViewById(R.id.profile_back);
        locateProgressBar = findViewById(R.id.locateProgressBar);

        //Intent intent = getIntent();

        /*if (intent!=null) {

            notiList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra(AppConstants.SHOWNOTIFICATION);
            IncomingList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra("IncomingList");

            if (intent.getSerializableExtra("OutGoingList")!=null){
                OutGoingList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra("OutGoingList");

            }

            cIncoming = intent.getIntExtra(AppConstants.INCOMING,0);
            cOutGoing = intent.getIntExtra(AppConstants.OUTGOING,0);

            notiList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

            adapterNotificationList = new AdapterNotificationList(NotificationsListActivity.this,notiList,"",cIncoming,cOutGoing,this);
            recyclerView.setAdapter(adapterNotificationList);

        }*/


    }

    @Override
    public void clickEvents(int id, int reqTeamID, int entity, String Action) {

        switch (Action){
            case AppConstants.ACCEPT:

                if (entity == 3){
                    //Desk...
                    DAODeskAccept daoDeskAccept = new DAODeskAccept();
                    daoDeskAccept.setId(id);
                    daoDeskAccept.setRequestedTeamDeskId(reqTeamID);
                    callDeskAccept(daoDeskAccept);
                }else if(entity == 4){
                    callMeetingApprove(id);
                }else if (entity == 5) {
                    Map<String, String> params = new HashMap<>();
                    params.put("", String.valueOf(id));
                    callParkingAccept(String.valueOf(id));
                }
                break;

            case AppConstants.REJECT:
                rejectPopUp(id,entity);
                break;
        }

    }


    private void callMeetingApprove(Integer id) {

        locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiService.acceptMeeting(String.valueOf(id));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                locateProgressBar.setVisibility(View.INVISIBLE);

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
                locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callMeetingReject(Integer id, String reason) {

        locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> call = apiService.rejectMeeting(daoDeskReject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                locateProgressBar.setVisibility(View.INVISIBLE);

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
                locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callParkingAccept(String params) {
        locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BaseResponse> call = apiService.acceptParking(params);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                locateProgressBar.setVisibility(View.INVISIBLE);

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
                locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callDeskAccept(DAODeskAccept daoDeskAccept) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.acceptDesk(daoDeskAccept);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
            }
        });

    }

    private void rejectPopUp(int id,int entity) {
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

                    if (entity == 3){
                        callRejectAPI(id,reason);
                    }else if (entity == 4){
                        callMeetingReject(id,reason);
                    }else if (entity == 5){
                        callParkingReject(id,reason);
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

    private void callRejectAPI(int id, String reason) {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> calls = api.rejectDesk(daoDeskReject);

        calls.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
            }
        });
    }

    private void callParkingReject(Integer id,String reason) {
        locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        DAODeskReject daoDeskReject = new DAODeskReject();
        daoDeskReject.setId(id);
        daoDeskReject.setReason(reason);

        Call<BaseResponse> call = apiService.rejectParking(daoDeskReject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                locateProgressBar.setVisibility(View.INVISIBLE);

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
                locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    
    
    //New...
    
    @Override
    protected void onResume() {
        super.onResume();

        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)){
            //Call both API's...
            callIncomingNotification();
        }else {
            //Outgoing Only...
            callOutGoingNotification();
        }
    }

    
    private void callIncomingNotification() {

        locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    IncomingList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){
                        IncomingList.addAll(response.body().getResults());

                        IncomingList.replaceAll(val ->{
                            val.setIncoming("incoming");
                            return val;
                        });
                        IncomingList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        callOutGoingNotification();

                    }else {
                        callOutGoingNotification();
                    }
                }else if(response.code()==401){
                    locateProgressBar.setVisibility(View.INVISIBLE);
                    Utils.showCustomTokenExpiredDialog(NotificationsListActivity.this,"Token Expired");
                    SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getDeskList(String code,String date) {
        if (Utils.isNetworkAvailable(this)) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call = apiService.getTeamDeskAvailability(
                    SessionHandler.getInstance().getInt(NotificationsListActivity.this, AppConstants.TEAM_ID),
                    date,
                    date);

            call.enqueue(new Callback<List<BookingForEditResponse.TeamDeskAvailabilities>>() {
                @Override
                public void onResponse(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Response<List<BookingForEditResponse.TeamDeskAvailabilities>> response) {
                    bookingDeskList.clear();
                    bookingDeskList = response.body();
//                    bookingDeskList = response.body().getTeamDeskAvailabilities();
//                    ProgressDialog.dismisProgressBar(getContext(),dialog);
                    System.out.println("dasdadas"+bookingDeskList.size());
                }

                @Override
                public void onFailure(Call<List<BookingForEditResponse.TeamDeskAvailabilities>> call, Throwable t) {
//                    deepLinking();

                }
            });


        } else {
            Utils.toastMessage(NotificationsListActivity.this, "Please Enable Internet");
        }

    }
    private void callOutGoingNotification() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    locateProgressBar.setVisibility(View.INVISIBLE);
                    OutGoingList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){

                        OutGoingList.addAll(response.body().getResults());

                        OutGoingList.replaceAll(val ->{
                            val.setIncoming("outgoing");
                            return val;
                        });

                     //   OutGoingList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        setAdapter();

                    }else {
                        setAdapter();
                    }
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                locateProgressBar.setVisibility(View.INVISIBLE);
                setAdapter();
            }
        });
    }

    private void setAdapter() {

        //ArrayList<IncomingRequestResponse.Result> notyManageList = new ArrayList<>();
        //int cIncoming = 0,cOutGoing = 0;


        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)){

            notiList = new ArrayList<>();

            if (OutGoingList!=null && OutGoingList.size()>0){
                OutGoingList.get(0).setTitle(true);

                ArrayList<IncomingRequestResponse.Result> statusZerOutGoingList = new ArrayList<>();

                statusZerOutGoingList = (ArrayList<IncomingRequestResponse.Result>) OutGoingList.stream().filter(val -> val.getStatus() == 0).collect(Collectors.toList());
                //IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                //notiList.addAll(OutGoingList);
                notiList.addAll(statusZerOutGoingList);

                IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);
                cOutGoing = Collections.frequency(statusZerOutGoingList, result); //outgoing.size();
            }

            if (IncomingList!=null && IncomingList.size()>0){
                IncomingList.get(0).setTitle(true);

                ArrayList<IncomingRequestResponse.Result> statusZerIncomingList = new ArrayList<>();
                ArrayList<IncomingRequestResponse.Result> statusNonZerIncomingList = new ArrayList<>();

                statusZerIncomingList = (ArrayList<IncomingRequestResponse.Result>) IncomingList.stream().filter(val -> val.getStatus() == 0).collect(Collectors.toList());
                statusNonZerIncomingList = (ArrayList<IncomingRequestResponse.Result>) IncomingList.stream().filter(val -> val.getStatus() != 0).collect(Collectors.toList());

                if (statusNonZerIncomingList.size()>0) {

                    statusNonZerIncomingList.replaceAll(val ->{
                        val.setIncoming("old");
                        return val;
                    });

                }

                //notiList.addAll(IncomingList);
                notiList.addAll(statusZerIncomingList);
                notiList.addAll(statusNonZerIncomingList);

                if (statusZerIncomingList.size()>0){

                    if (statusNonZerIncomingList.size()>0){
                        //notiList.get(statusZerIncomingList.size()).setTitle(true);
                        notiList.get(notiList.size()-statusNonZerIncomingList.size()).setTitle(true);
                    }/*else {
                        notiList.get(statusZerIncomingList.size()-1).setTitle(true);
                    }*/

                }else {
                    notiList.get(0).setTitle(true);
                }



                IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                cIncoming = Collections.frequency(statusZerIncomingList, result); //incoming.size();
            }

            if (notiList.size()>0){

                //New...
                adminNotificationReq = new AdapterAdminNotificationReq(NotificationsListActivity.this,notiList,"",cIncoming,cOutGoing,this);
                recyclerView.setAdapter(adminNotificationReq);

                //adapterNotificationList = new AdapterNotificationList(NotificationsListActivity.this,notiList,"",cIncoming,cOutGoing,this);
                //recyclerView.setAdapter(adapterNotificationList);
            }

        }else {

            notiList = new ArrayList<>();

            if (OutGoingList!=null && OutGoingList.size()>0){
                OutGoingList.get(0).setTitle(true);
                notiList.addAll(OutGoingList);

                IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);
                cOutGoing = Collections.frequency(OutGoingList, result); //outgoing.size();

                if (notiList.size()>0){
                    adapterNotificationList = new AdapterNotificationList(NotificationsListActivity.this,notiList,"",cIncoming,cOutGoing,this);
                    recyclerView.setAdapter(adapterNotificationList);
                }

            }

        }

    }


    @Override
    public void reqClickEvents(int id, int reqTeamID, int entity, String Action) {
        switch (Action){
            case AppConstants.ACCEPT:

                if (entity == 3){
                    //Desk...
                    DAODeskAccept daoDeskAccept = new DAODeskAccept();
                    daoDeskAccept.setId(id);
                    daoDeskAccept.setRequestedTeamDeskId(reqTeamID);
                    callDeskAccept(daoDeskAccept);
                }else if(entity == 4){
                    callMeetingApprove(id);
                }else if (entity == 5) {
                    Map<String, String> params = new HashMap<>();
                    params.put("", String.valueOf(id));
                    callParkingAccept(String.valueOf(id));
                }
                break;

            case AppConstants.REJECT:
                rejectPopUp(id,entity);
                break;
        }
    }
}