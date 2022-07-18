package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.GDPRrequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GDPRActivity extends AppCompatActivity {
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.decline)
    TextView decline;
    Dialog dialog;
    String tenantName,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdpr);
        ButterKnife.bind(this);
        dialog = new Dialog(getApplicationContext());
        tenantName = getIntent().getExtras().getString("tenantName");
        userName = getIntent().getExtras().getString("userName");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGDPR();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void callGDPR() {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(GDPRActivity.this);
            GDPRrequest gdpRrequest = new GDPRrequest();
            gdpRrequest.setTenantName(tenantName);
            gdpRrequest.setUserName(userName);
            gdpRrequest.setGdprAccepted(true);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Boolean> call = apiService.updateGDPR(gdpRrequest);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.code()==200){
                        Utils.toastMessage(getApplicationContext(),"Successfull, Please Login");
                    }
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Utils.toastMessage(getApplicationContext(),"Failure, Please Login"+t.getMessage());
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }

    }

    @Override
    public void onBackPressed() {

    }
}