package dream.guys.hotdeskandroid.ui.wellbeing;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.ReportIssueRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkspaceSurveyActivity extends AppCompatActivity
{
    TextView reportPastBooking, reportFromDate, reportToDate, reportCancel, reportSubmit;
    EditText etDescription;
    CheckBox cb_anonymous;
    SeekBar sb_Accessibility, sb_Air, sb_clean, sb_light, sb_noise, sb_temperature, sb_chair,
            sb_desk, sb_display, sb_keyboard, sb_mouse, sb_phone, sb_mobile, sb_wifi, sb_lan;
    int accessibility, air, clean, light, noise, temperature, chair, desk, display, keyboard, mouse, phone, mobile, wifi, lan;
    String strAccessibility, strAir, strClean, strLight, strNoise, strTemperature, strChair, strDesk, strDisplay,
            strKeyboard, strMouse, strPhone, strMobile, strWifi, strLan, type;
    ImageView ReportBack, iv_edit_accessibility, iv_edit_air, iv_edit_clean, iv_edit_light, iv_edit_noise, iv_edit_temperature, iv_edit_chair,
            iv_edit_desk, iv_edit_display, iv_edit_keyboard, iv_edit_mouse, iv_edit_phone, iv_edit_mobile, iv_edit_wifi, iv_edit_lan;
    int deskId=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace_survey);

        reportPastBooking = findViewById(R.id.reportPastBooking);
        reportFromDate = findViewById(R.id.reportFromDate);
        reportToDate = findViewById(R.id.reportToDate);
        etDescription = findViewById(R.id.etDescription);
        cb_anonymous = findViewById(R.id.cb_anonymous);
        reportSubmit = findViewById(R.id.reportSubmit);
        reportCancel = findViewById(R.id.reportCancel);
        ReportBack = findViewById(R.id.ReportBack);

        ReportBack.setOnClickListener(new View.OnClickListener()
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
                validateData();
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

        iv_edit_accessibility = findViewById(R.id.iv_edit_accessibility);
        iv_edit_air = findViewById(R.id.iv_edit_air);
        iv_edit_clean = findViewById(R.id.iv_edit_clean);
        iv_edit_light = findViewById(R.id.iv_edit_light);
        iv_edit_noise = findViewById(R.id.iv_edit_noise);
        iv_edit_temperature = findViewById(R.id.iv_edit_temperature);
        iv_edit_chair = findViewById(R.id.iv_edit_chair);
        iv_edit_desk = findViewById(R.id.iv_edit_desk);
        iv_edit_display = findViewById(R.id.iv_edit_display);
        iv_edit_keyboard = findViewById(R.id.iv_edit_keyboard);
        iv_edit_mouse = findViewById(R.id.iv_edit_mouse);
        iv_edit_phone = findViewById(R.id.iv_edit_phone);
        iv_edit_mobile = findViewById(R.id.iv_edit_mobile);
        iv_edit_wifi = findViewById(R.id.iv_edit_wifi);
        iv_edit_lan = findViewById(R.id.iv_edit_lan);

        sb_Accessibility = findViewById(R.id.sb_Accessibility);
        sb_Air = findViewById(R.id.sb_Air);
        sb_clean = findViewById(R.id.sb_clean);
        sb_light = findViewById(R.id.sb_light);
        sb_noise = findViewById(R.id.sb_noise);
        sb_temperature = findViewById(R.id.sb_temperature);
        sb_chair = findViewById(R.id.sb_chair);
        sb_desk = findViewById(R.id.sb_desk);
        sb_display = findViewById(R.id.sb_display);
        sb_keyboard = findViewById(R.id.sb_keyboard);
        sb_mouse = findViewById(R.id.sb_mouse);
        sb_phone = findViewById(R.id.sb_phone);
        sb_mobile = findViewById(R.id.sb_mobile);
        sb_wifi = findViewById(R.id.sb_wifi);
        sb_lan = findViewById(R.id.sb_lan);


        iv_edit_accessibility.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "accessibility";
                ShowAlertDialog(type);
            }
        });

        iv_edit_air.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "air";
                ShowAlertDialog(type);
            }
        });

        iv_edit_clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "clean";
                ShowAlertDialog(type);
            }
        });
        iv_edit_light.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "light";
                ShowAlertDialog(type);
            }
        });
        iv_edit_noise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "noise";
                ShowAlertDialog(type);
            }
        });
        iv_edit_temperature.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "temperature";
                ShowAlertDialog(type);
            }
        });
        iv_edit_chair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "chair";
                ShowAlertDialog(type);
            }
        });
        iv_edit_desk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "desk";
                ShowAlertDialog(type);
            }
        });
        iv_edit_display.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "display";
                ShowAlertDialog(type);
            }
        });
        iv_edit_keyboard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "keyboard";
                ShowAlertDialog(type);
            }
        });
        iv_edit_mouse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "mouse";
                ShowAlertDialog(type);
            }
        });
        iv_edit_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "phone";
                ShowAlertDialog(type);
            }
        });
        iv_edit_mobile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "mobile";
                ShowAlertDialog(type);
            }
        });
        iv_edit_wifi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "wifi";
                ShowAlertDialog(type);
            }
        });
        iv_edit_lan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                type = "lan";
                ShowAlertDialog(type);
            }
        });

        reportFromDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.bottomSheetDatePicker(WorkspaceSurveyActivity.this,WorkspaceSurveyActivity.this,"","",reportFromDate,null);
            }
        });

        reportToDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.bottomSheetDatePicker(WorkspaceSurveyActivity.this,WorkspaceSurveyActivity.this,"","",reportToDate,null);
            }
        });

        sb_Accessibility.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                accessibility = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        sb_Air.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                air = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        sb_clean.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                clean = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

      sb_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              light = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_noise.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              noise = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_temperature.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              temperature = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_chair.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              chair = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_desk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {

              desk = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_display.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              display= i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_keyboard.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              keyboard = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_mouse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              mouse = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_phone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              phone = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_mobile.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              mobile = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_wifi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              wifi = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

      sb_lan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      {
          @Override
          public void onProgressChanged(SeekBar seekBar, int i, boolean b)
          {
              lan = i;
          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar)
          {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar)
          {

          }
      });

        getLocationRI();
    }

    private void validateData()
    {
        if(reportPastBooking.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Floor must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportFromDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "From Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportToDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "To Date must not be Empty",Toast.LENGTH_LONG).show();
        }else {



            ReportIssueRequest reportIssueRequest= new ReportIssueRequest();
            ReportIssueRequest.FeedBack feedBack= new ReportIssueRequest.FeedBack();
            List<ReportIssueRequest.Metric> metrics= new ArrayList<ReportIssueRequest.Metric>();


            metrics.add(new ReportIssueRequest.Metric(10, accessibility/10, strAccessibility));
            metrics.add(new ReportIssueRequest.Metric(10, air/10, strAir));
            metrics.add(new ReportIssueRequest.Metric(10, clean/10, strClean));
            metrics.add(new ReportIssueRequest.Metric(10, light/10, strLight));
            metrics.add(new ReportIssueRequest.Metric(10, noise/10, strNoise));
            metrics.add(new ReportIssueRequest.Metric(10, chair/10, strChair));
            metrics.add(new ReportIssueRequest.Metric(10, desk/10, strDesk));
            metrics.add(new ReportIssueRequest.Metric(10, display/10, strDisplay));
            metrics.add(new ReportIssueRequest.Metric(10, keyboard/10, strKeyboard));
            metrics.add(new ReportIssueRequest.Metric(10, mouse/10, strMouse));
            metrics.add(new ReportIssueRequest.Metric(10, phone/10, strPhone));
            metrics.add(new ReportIssueRequest.Metric(10, mobile/10, strMobile));
            metrics.add(new ReportIssueRequest.Metric(10, wifi/10, strWifi));
            metrics.add(new ReportIssueRequest.Metric(10, lan/10, strLan));



            reportIssueRequest.setMetrics(metrics);
            feedBack.setDeskId(deskId);
            feedBack.setLocationId(20);

            feedBack.setEffectedFrom(reportFromDate.getText().toString());
            feedBack.setEffectedTo(reportToDate.getText().toString());
            reportIssueRequest.setComments(etDescription.getText().toString());
            reportIssueRequest.setAnonymous(cb_anonymous.isChecked());

            reportIssueRequest.setFeedbackDeskLocationInfo(feedBack);
            postSubmit(reportIssueRequest);
        }
    }

    private void postSubmit(ReportIssueRequest reportIssueRequest)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.postFeedback(reportIssueRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Toast.makeText(getApplicationContext(),"Successfully Reported",Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Successfully Reported",Toast.LENGTH_LONG).show();
                finish();
                t.printStackTrace();
            }
        });

    }

    private void ShowAlertDialog(String type)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (this.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (this.getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_comments);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView etDescription = dialog.findViewById(R.id.etDescription);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equalsIgnoreCase("accessibility")){
                    strAccessibility = etDescription.getText().toString();
                } else if(type.equalsIgnoreCase("air")){
                    strAir = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("clean")){
                    strClean = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("light")){
                    strLight = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("noise")){
                    strNoise = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("temperature")){
                    strTemperature = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("chair")){
                    strChair = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("desk")){
                    strDesk = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("display")){
                    strDisplay = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("keyboard")){
                   strKeyboard = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("mouse")){
                    strMouse = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("phone")){
                    strPhone = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("mobile")){
                    strMobile = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("wifi")){
                    strWifi = etDescription.getText().toString();
                }else if(type.equalsIgnoreCase("lan")){
                    strLan = etDescription.getText().toString();
                }

                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void getLocationRI()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<DeskResponse> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId="+ SessionHandler.getInstance().get(ReportAnIssueActivity.this,AppConstants.DEFAULT_LOCATION_ID));
        Call<DeskResponseNew> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId=20");
        call.enqueue(new Callback<DeskResponseNew>() {
            @Override
            public void onResponse(Call<DeskResponseNew> call, Response<DeskResponseNew> response)
            {
                deskId = response.body().getDesk().get(0).getId();
                reportPastBooking.setText(SessionHandler.getInstance().get(WorkspaceSurveyActivity.this, AppConstants.DEFAULT_LOCATION_NAME)+" Room-"+response.body().getDesk().get(0).getCode());
            }

            @Override
            public void onFailure(Call<DeskResponseNew> call, Throwable t)
            {

            }


        });
    }
}
