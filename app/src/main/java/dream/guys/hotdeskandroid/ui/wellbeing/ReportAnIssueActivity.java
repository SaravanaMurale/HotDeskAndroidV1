package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.ReportIssueRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;

import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportAnIssueActivity extends AppCompatActivity {

    private static final String TAG = "ReportAnIssueActivity";

    EditText reportDescr;
    ImageView ReportBack;
    TextView reportFromDate, reportToDate, reportCancel, reportSubmit, reportPastBooking;
    CalendarView calendar_view;
    CheckBox cb_anonymous;

    Calendar c = Calendar.getInstance();
    int deskId=0;
    String dateString="";
    String fromDate, toDate;
    long from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_an_issue);

        reportPastBooking = findViewById(R.id.reportPastBooking);
        reportFromDate = findViewById(R.id.reportFromDate);
        reportToDate = findViewById(R.id.reportToDate);
        reportCancel = findViewById(R.id.reportCancel);
        reportSubmit = findViewById(R.id.reportSubmit);
        reportDescr = findViewById(R.id.reportDescr);
        ReportBack = findViewById(R.id.ReportBack);
        cb_anonymous = findViewById(R.id.cb_anonymous);
        calendar_view = (CalendarView) findViewById(R.id.calendar_view);
        calendar_view.setVisibility(View.GONE);

        ReportBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        reportCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        reportSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                submitReport();
            }

        });
        reportFromDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dateString ="from";
                calendar_view.setVisibility(View.VISIBLE);
            }
        });

        reportToDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dateString ="to";
                calendar_view.setVisibility(View.VISIBLE);
            }
        });

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {

                try{
                    if(dateString.equalsIgnoreCase("from")){
                        reportFromDate.setText(date+ "/"+month+"/"+year);

                        c.set(year, month, date);
                        from = c.getTimeInMillis();
                        fromDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(from));
                        Log.d(TAG, "onSelectedDayChange: "+fromDate);
                        calendar_view.setVisibility(View.GONE);
                    } else {
                        reportToDate.setText(date+ "/"+month+"/"+year);
                        c.set(year, month, date);
                        to = c.getTimeInMillis();
                        toDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(to));
                        Log.d(TAG, "onSelectedDayChange: "+to);
                        calendar_view.setVisibility(View.GONE);
                    }
                }catch (Exception ex){

                }

            }
        });

        getLocationRI();
    }

    private void submitReport()
    {


        if(reportPastBooking.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Floor must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportFromDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "From Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportToDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "To Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportDescr.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Description must not be Empty",Toast.LENGTH_LONG).show();
        }else {



            ReportIssueRequest reportIssueRequest= new ReportIssueRequest();
            ReportIssueRequest.FeedBack feedBack= new ReportIssueRequest.FeedBack();
            List<ReportIssueRequest.Metric> metrics= new ArrayList<ReportIssueRequest.Metric>();

            reportIssueRequest.setMetrics(metrics);
            feedBack.setDeskId(deskId);
            feedBack.setLocationId(20);

            feedBack.setEffectedFrom(fromDate);
            feedBack.setEffectedTo(toDate);
            reportIssueRequest.setComments(reportDescr.getText().toString());
            reportIssueRequest.setAnonymous(cb_anonymous.isChecked());

            reportIssueRequest.setFeedbackDeskLocationInfo(feedBack);
            postSubmit(reportIssueRequest);
        }

    }

    private void postSubmit(ReportIssueRequest request)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.postFeedback(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "onResponse: ");
                Toast.makeText(getApplicationContext(),"Successfully Reported",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_LONG).show();
                finish();
                Log.d(TAG, "onFailure: "+t.getMessage());
                t.printStackTrace();
            }
        });



    }

    private void getLocationRI() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<DeskResponse> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId="+ SessionHandler.getInstance().get(ReportAnIssueActivity.this,AppConstants.DEFAULT_LOCATION_ID));
        Call<DeskResponseNew> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId=20");
        call.enqueue(new Callback<DeskResponseNew>() {
            @Override
            public void onResponse(Call<DeskResponseNew> call, Response<DeskResponseNew> response)
            {
                deskId = response.body().getDesk().get(0).getId();
                reportPastBooking.setText(SessionHandler.getInstance().get(ReportAnIssueActivity.this, AppConstants.DEFAULT_LOCATION_NAME)+" Room-"+response.body().getDesk().get(0).getCode());
            }

            @Override
            public void onFailure(Call<DeskResponseNew> call, Throwable t)
            {

            }


        });
    }
}