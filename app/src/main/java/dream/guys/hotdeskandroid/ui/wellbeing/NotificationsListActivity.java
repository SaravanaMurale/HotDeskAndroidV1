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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationList;
import dream.guys.hotdeskandroid.model.request.DAODeskAccept;
import dream.guys.hotdeskandroid.model.request.DAODeskReject;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.notify.NotificationCenterActivity;
import dream.guys.hotdeskandroid.ui.notify.NotificationManageActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsListActivity extends AppCompatActivity implements AdapterNotificationList.AccRejInterface{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_list);

        uiInit();
        context = NotificationsListActivity.this;

        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase("Administrator")){
            tv_manage.setVisibility(View.VISIBLE);
        }else {
            tv_manage.setVisibility(View.GONE);
        }

        tv_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<IncomingRequestResponse.Result> manageList = new ArrayList<>();

                manageList = (ArrayList<IncomingRequestResponse.Result>) notiList.stream().filter(val -> val.getStatus() == 0).collect(Collectors.toList());
                IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                ArrayList<IncomingRequestResponse.Result> inComingList = new ArrayList<>();
                inComingList = (ArrayList<IncomingRequestResponse.Result>) manageList.stream().filter(val -> val.getIncoming().equalsIgnoreCase("incoming")).collect(Collectors.toList());

                int c = Collections.frequency(inComingList, result);
                Intent intent = new Intent(NotificationsListActivity.this, NotificationManageActivity.class);
                intent.putExtra(AppConstants.SHOWNOTIFICATION,inComingList);
                intent.putExtra(AppConstants.INCOMING,c);
                startActivity(intent);

                /*for (int i=0;i<notiList.size();i++) {

                    if (notiList.get(i).getStatus() == 0){
                        manageList.add(notiList.get(i));
                    }

                }*/

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

        Intent intent = getIntent();

        if (intent!=null) {

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

        }


    }

    @Override
    public void clickEvents(int id, int reqTeamID, int Entity, String Action) {

        switch (Action){
            case AppConstants.ACCEPT:

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                DAODeskAccept daoDeskAccept = new DAODeskAccept();
                daoDeskAccept.setId(id);
                daoDeskAccept.setRequestedTeamDeskId(reqTeamID);

                Call<BaseResponse> call = apiService.acceptDesk(daoDeskAccept);

                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body()!=null) {
                            if (response.code() == 200){
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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
                break;

            case AppConstants.REJECT:
                rejectPopUp(id);
                break;
        }

    }

    private void rejectPopUp(int id) {
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
                    callRejectAPI(id,reason);
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

}