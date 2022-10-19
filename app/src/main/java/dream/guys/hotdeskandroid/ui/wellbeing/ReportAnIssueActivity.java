package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getGlobalScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.CustomAdapter;
import dream.guys.hotdeskandroid.listener.QuestionListListener;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.ReportIssueRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.DeskResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.home.DefaultLocationActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;

import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportAnIssueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    LanguagePOJO.WellBeing wellBeingPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.Global global;

    TextView mTxtTitle,issue_content,txt_locate,txt_pastBooking,txt_date,txt_report,tvLight,tvPhone,
            tvNoise,tvTemperature,tvChair,tvDesk,tvDisplay,tvKeyboard,tvMouse,tvMobile,tvWifi,tvLan
            ,tvComments;

    //New...
    UserDetailsResponse profileData;
    String id;

    int floorParentID = 0, cityPlaceID = 0, cityPlaceParentID = 0,cityID = 0,cityParentID = 0,locationID = 0,locationParentID = 0,
            floorPositon;
    int carFloorParentID;

    String CountryName = "";
    String CityName = "";
    String buildingName = "";
    String floorName  = "";
    String fullPathLocation  = "";

    //TextView etDesk;
    Spinner etDesk;
    List<TeamDeskResponse.Desk> deskList = new ArrayList<>();
    TextView reportFromTime,reportToTime;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_an_issue);

        context = ReportAnIssueActivity.this;

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

        mTxtTitle = findViewById(R.id.profile_edit);
        issue_content = findViewById(R.id.issue_content);
        txt_locate = findViewById(R.id.txt_locate);
        txt_pastBooking = findViewById(R.id.txt_pastBooking);
        txt_date = findViewById(R.id.txt_date);
        txt_report = findViewById(R.id.txt_report);

        etDesk = findViewById(R.id.etDesk);
        reportFromTime = findViewById(R.id.reportFromTime);
        reportToTime = findViewById(R.id.reportToTime);

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
            public void onClick(View view){

                /*String fromTime = reportFromTime.getText().toString();
                String toTime = reportToTime.getText().toString();

                String fDT = fromDate + 'T' + fromTime + ":00.000Z";
                String tDT = toDate + 'T' + toTime + ":00.000Z";

                Log.d("DATEEE",fDT + " " + tDT);*/

                submitReport();
            }

        });
        reportFromDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //dateString ="from";
                //calendar_view.setVisibility(View.VISIBLE);
                bottomSheetLocateDatePickerInBooking(context,ReportAnIssueActivity.this, "From Date", "",reportFromDate);
            }
        });

        reportToDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //dateString ="to";
                //calendar_view.setVisibility(View.VISIBLE);
                bottomSheetLocateDatePickerInBooking(context,ReportAnIssueActivity.this, "To Date", "",reportToDate);
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
                        //fromDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(from));
                        fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(from));
                        Log.d(TAG, "onSelectedDayChange: "+fromDate);
                        calendar_view.setVisibility(View.GONE);
                    } else {
                        reportToDate.setText(date+ "/"+month+"/"+year);
                        c.set(year, month, date);
                        to = c.getTimeInMillis();
                        //toDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date(to));
                        toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(to));
                        Log.d(TAG, "onSelectedDayChange: "+to);
                        calendar_view.setVisibility(View.GONE);
                    }
                }catch (Exception ex){

                }

            }
        });

        //New...
        profileData = Utils.getLoginData(ReportAnIssueActivity.this);
        if (profileData.getDefaultLocation()!=null){
            id = String.valueOf(profileData.getDefaultLocation().getId());
        }

        getLocationRI();
        setLanguage();

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            ArrayList<DAOActiveLocation> finalLocationArrayList = new ArrayList<>();
                            ArrayList<DAOActiveLocation> cityPlaceFloorArrayList = new ArrayList<>();

                            Intent intent = result.getData();

                            if (intent!=null){

                                if (intent.getStringExtra("sFrom")!=null) {

                                    String from = intent.getStringExtra("sFrom");
                                    int position = intent.getIntExtra("Position",0);
                                    floorName = intent.getStringExtra("floorName");

                                    finalLocationArrayList = (ArrayList<DAOActiveLocation>)intent.getSerializableExtra("List");
                                    cityPlaceFloorArrayList = (ArrayList<DAOActiveLocation>)intent.getSerializableExtra("FloorList");

                                    floorParentID = finalLocationArrayList.get(position).getParentLocationId();
                                    id = String.valueOf(finalLocationArrayList.get(position).getId());

                                    if (from.equalsIgnoreCase(AppConstants.DefaultLocation)) {

                                        //UserDetailsResponse.DefaultLocation defaultLocation = new UserDetailsResponse.DefaultLocation();

                                        ArrayList<DAOActiveLocation> selectFloors = new ArrayList<>();
                                        selectFloors = (ArrayList<DAOActiveLocation>) cityPlaceFloorArrayList.stream().filter(val -> val.getParentLocationId() == floorParentID).collect(Collectors.toList());

                                        /*for (int i=0;i<selectFloors.size();i++) {

                                            if (id.equals(selectFloors.get(i).getId())) {
                                                floorPositon = i;
                                                break;
                                            }
                                        }*/

                                        /*defaultLocation.setId(finalLocationArrayList.get(position).getId());
                                        defaultLocation.setName(finalLocationArrayList.get(position).getName());
                                        defaultLocation.setDescription(finalLocationArrayList.get(position).getDescription());
                                        defaultLocation.setLeafLocation(finalLocationArrayList.get(position).getIsLeafLocation());
                                        defaultLocation.setLocationType(finalLocationArrayList.get(position).getLocationType());
                                        defaultLocation.setActive(finalLocationArrayList.get(position).getIsActive());
                                        defaultLocation.setTimeZoneId(finalLocationArrayList.get(position).getTimeZoneId());
                                        defaultLocation.setParentLocationId(floorParentID);

                                        profileData.setDefaultLocation(defaultLocation);*/

                                        reportPastBooking.setText(floorName);

                                        getLocationRI();

                                    }/*else {

                                        //Car Parking...
                                        binding.editPark.setText(floorName);

                                        UserDetailsResponse.DefaultCarParkLocation carPark = new UserDetailsResponse.DefaultCarParkLocation();
                                        carPark.setId(finalLocationArrayList.get(position).getId());
                                        carPark.setName(finalLocationArrayList.get(position).getName());
                                        carPark.setDescription(finalLocationArrayList.get(position).getDescription());
                                        carPark.setLeafLocation(finalLocationArrayList.get(position).getIsLeafLocation());
                                        carPark.setLocationType(finalLocationArrayList.get(position).getLocationType());
                                        carPark.setActive(finalLocationArrayList.get(position).getIsActive());
                                        carPark.setTimeZoneId(finalLocationArrayList.get(position).getTimeZoneId());
                                        carPark.setParentLocationId(floorParentID);

                                        profileData.setDefaultCarParkLocation(carPark);

                                    }*/
                                }

                            }
                        }

                    }
                });

        reportPastBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportAnIssueActivity.this, DefaultLocationActivity.class);
                intent.putExtra(AppConstants.FROM,AppConstants.DefaultLocation);
                resultLauncher.launch(intent);
            }
        });

        reportPastBooking.setText(SessionHandler.getInstance().get(ReportAnIssueActivity.this, AppConstants.DEFAULT_LOCATION_NAME));

        reportFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt = reportFromDate.getText().toString().trim();
                String date = "";

                if (txt.isEmpty()) {
                    date = txt;
                }else {
                    date = getCurrentDate();
                }

                bottomSheetLocateTimePickerInBooking(context, ReportAnIssueActivity.this, reportFromTime, "From",date, 0);
            }
        });
        reportToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt = reportToDate.getText().toString().trim();
                String date = "";

                if (txt.isEmpty()) {
                    date = txt;
                }else {
                    date = getCurrentDate();
                }
                bottomSheetLocateTimePickerInBooking(context, ReportAnIssueActivity.this, reportToTime, "To",date, 1);
            }
        });

    }

    private void submitReport()
    {


        if(reportPastBooking.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Floor must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportFromDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "From Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportToDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "To Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportFromTime.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "From Time must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportToTime.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "To Time must not be Empty",Toast.LENGTH_LONG).show();
        }else if(reportDescr.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Description must not be Empty",Toast.LENGTH_LONG).show();
        }else {



            ReportIssueRequest reportIssueRequest= new ReportIssueRequest();
            ReportIssueRequest.FeedBack feedBack= new ReportIssueRequest.FeedBack();
            List<ReportIssueRequest.Metric> metrics= new ArrayList<ReportIssueRequest.Metric>();

            reportIssueRequest.setMetrics(metrics);
            feedBack.setDeskId(deskId);
            feedBack.setLocationId(20);

            //New...
            String fromTime = reportFromTime.getText().toString();
            String toTime = reportToTime.getText().toString();

            String fDT = fromDate + 'T' + fromTime + ":00.000Z";
            String tDT = toDate + 'T' + toTime + ":00.000Z";

            feedBack.setEffectedFrom(fDT);
            feedBack.setEffectedTo(tDT);
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
        Call<DeskResponseNew> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId="+id);
        call.enqueue(new Callback<DeskResponseNew>() {
            @Override
            public void onResponse(Call<DeskResponseNew> call, Response<DeskResponseNew> response)
            {
                if (response.body()!=null && response.code() == 200) {

                    if (response.body().getDesk() != null && response.body().getDesk().size() > 0) {

                        //deskId = response.body().getDesk().get(0).getId();
                        //reportPastBooking.setText(SessionHandler.getInstance().get(ReportAnIssueActivity.this, AppConstants.DEFAULT_LOCATION_NAME)+" Room-"+response.body().getDesk().get(0).getCode());

                        deskList = response.body().getDesk();
                        setValuesToSpinner();
                        etDesk.setVisibility(View.VISIBLE);

                    }else {
                        hideSpinner();
                        //setValuesToSpinner();
                        //etLocation.setText(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME));
                    }

                }else {
                    hideSpinner();
                    //setValuesToSpinner();
                    //etLocation.setText(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME));
                }

            }

            @Override
            public void onFailure(Call<DeskResponseNew> call, Throwable t)
            {

            }


        });
    }


    private void setLanguage() {

        wellBeingPage = getWellBeingScreenData(this);
        appKeysPage = getAppKeysPageScreenData(this);
        global = getGlobalScreenData(this);

        mTxtTitle.setText(appKeysPage.getReportAnIssue());
        txt_locate.setText(appKeysPage.getLocation());
        txt_pastBooking.setText(appKeysPage.getPastBooking());
        issue_content.setText(appKeysPage.getReportIssueTitle());
        txt_date.setText(appKeysPage.getDateApplicable());

        reportCancel.setText(appKeysPage.getCancel());
        reportSubmit.setText(appKeysPage.getSubmit());
        cb_anonymous.setText(global.getAnonymous());
        reportFromDate.setHint(appKeysPage.getFrom());
        reportToDate.setHint(appKeysPage.getTo());
        txt_report.setText(appKeysPage.getDescription());

    }

    //New...
    private void hideSpinner() {

        etDesk.setVisibility(View.INVISIBLE);
        deskId = 0;
    }

    private void setValuesToSpinner() {

        /*ArrayList<String> subCategory = new ArrayList<>();
        subCategory.add("Select Desk");
        for (int i = 0; i < deskList.size(); i++) {
            subCategory.add(deskList.get(i).getCode().toString());
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,subCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etDesk.setAdapter(aa);*/

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),deskList);
        etDesk.setAdapter(customAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        deskId = deskList.get(i).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bottomSheetLocateTimePickerInBooking(Context mContext, Activity activity, TextView tv, String title, String date, int i) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));

        TimePicker simpleTimePicker24Hours = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        //simpleTimePicker24Hours.setIs24HourView(false);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);

        //Language
        backTv.setText(appKeysPage.getBack());
        continueTv.setText(appKeysPage.getContinue());

        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }

        titleTv.setText(title);
        if (title.equals("Start")) {
            titleTv.setText(appKeysPage.getStart());
        } else if (title.equals("End")) {
            titleTv.setText(appKeysPage.getEnd());
        }


        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hour = null, minute = null;
                String getHHour = String.valueOf(simpleTimePicker24Hours.getHour());
                String getMMinute = String.valueOf(simpleTimePicker24Hours.getMinute());

                if (getHHour.length() == 1) {
                    hour = "0" + getHHour;
                } else {
                    hour = getHHour;
                }

                if (getMMinute.length() == 1) {
                    minute = "0" + getMMinute;
                } else {
                    minute = getMMinute;
                }

                tv.setText(hour + ":" + minute);

                /*if (i == 1) {
                    String eTime = binding.locateEndTime.getText().toString();
                    checkStartEndtime(hour + ":" + minute, eTime);
                } else {
                    String sTime = binding.locateStartTime.getText().toString();
                    checkStartEndtime(sTime, hour + ":" + minute);
                }*/

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }

    public void bottomSheetLocateDatePickerInBooking(Context mContext, Activity activity, String title, String date,
                                                     TextView locateCheckInDateCal) {

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView tvSelectDate = bottomSheetDatePicker.findViewById(R.id.tvSelectDate);
        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView = bottomSheetDatePicker.findViewById(R.id.datePicker);

        //Language
        calContinue.setText(appKeysPage.getContinue());
        calBack.setText(appKeysPage.getBack());
        tvSelectDate.setText(appKeysPage.getSelectDate());

        Calendar c = Calendar.getInstance();
        calendarView.setMinDate(c.getTimeInMillis() - 1000);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString = String.valueOf(year);

                //MonthConversion
                int actualMonth = month + 1;
                String monthInStringFormat;
                if (actualMonth >= 10) {
                    monthInStringFormat = String.valueOf(actualMonth);
                } else {
                    String monthInString = String.valueOf(actualMonth);
                    monthInStringFormat = "0" + monthInString;
                }

                //DayConversion
                String dayInStringFormat;
                if (dayOfMonth < 10) {
                    String dayInString = String.valueOf(dayOfMonth);
                    dayInStringFormat = "0" + dayInString;
                } else {
                    dayInStringFormat = String.valueOf(dayOfMonth);
                }


                String dateInString = "";

                //System.out.println("ContinuPrintHere" + locateCheckInDateCal.getText());
                dateInString = yearInString + "-" + monthInStringFormat + "-" + dayInStringFormat;
                //System.out.println("PickedDate" + dateInString);

                if (title.equalsIgnoreCase("From Date")) {
                    fromDate = dateInString; //new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(dateInString));
                    locateCheckInDateCal.setText(fromDate);
                }else {
                    toDate = dateInString; //new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(dateInString));
                    locateCheckInDateCal.setText(toDate);
                }


                //binding.showCalendar.setText(Utils.showCalendarDate(dateInString));
                //checkIsCurrentDate(dateInString);


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date d1=null,d2=null;
                try {
                    d1=formatter.parse(formatter.format(date));
                    d2 = formatter.parse(dateInString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println("CurentDateSelected"+d1+" "+d2);*/

                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();

    }


}