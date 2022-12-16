package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.adapter.CustomAdapter;
import dream.guys.hotdeskandroid.listener.QuestionListEditListener;
import dream.guys.hotdeskandroid.listener.QuestionListListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.AssessmentAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.QuestionListRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.model.response.QuestionListResponse;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.home.DefaultLocationActivity;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkspaceAssessmentActivity extends AppCompatActivity implements QuestionListListener , AdapterView.OnItemSelectedListener
{
    private static final String TAG = "WorkspaceAssessment";

    LinearLayoutManager mLayoutManager;
    AssessmentAdapter assessmentAdapter;
    RecyclerView rvWorkspaceHeader;
    TextView etDate, reportCancel, etLocation;
    Button reportSubmit;
    EditText etDescription;
    ImageView ReportBack;
    int deskId=0;

    List<QuestionListResponse> questionListResponse = new ArrayList<>();

    LanguagePOJO.AppKeys appKeysPage;
    TextView mTitle,tvLocation,tvDescription;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace_assessment);

        rvWorkspaceHeader = findViewById(R.id.rvWorkspaceHeader);
        etDate = findViewById(R.id.etDate);
        reportCancel = findViewById(R.id.reportCancel);
        reportSubmit = findViewById(R.id.reportSubmit);
        etLocation = findViewById(R.id.etLocation);
        ReportBack = findViewById(R.id.ReportBack);
        etDescription = findViewById(R.id.etDescription);

        mTitle = findViewById(R.id.profile_edit);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);

        etDesk = findViewById(R.id.etDesk);

        setLanguage();

        reportSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(assessmentAdapter!=null)
                {
                    validateData(questionListResponse);
                    /*for(QuestionListResponse item:questionListResponse)
                    {
                        List<QuestionListResponse.ChecklistQuestions> checklistQuestions = item.getChecklistQuestions();

                        validateData(questionListResponse);
                       *//* for(QuestionListResponse.ChecklistQuestions subItem:checklistQuestions)
                        {
                            Log.d(TAG, "CategoryName" + subItem.getCategoryName());
                            Log.d(TAG, "Question" + subItem.getRiskFactors());
                            Log.d(TAG, "Answer" + subItem.isAnswer());
                            Log.d(TAG, "Action" + subItem.getActionToTake());
                        }*//*
                    }*/
                }
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

        ReportBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.bottomSheetDatePickerWorkSpaceSurveyAsement(WorkspaceAssessmentActivity.this,WorkspaceAssessmentActivity.this,"","",etDate);
            }
        });

        //New...
        profileData = Utils.getLoginData(WorkspaceAssessmentActivity.this);
        if (profileData.getDefaultLocation()!=null){
            id = String.valueOf(profileData.getDefaultLocation().getId());
        }

        getLocationRI();
        getQuestionList();

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

                                        for (int i=0;i<selectFloors.size();i++) {

                                            if (id.equals(selectFloors.get(i).getId())) {
                                                floorPositon = i;
                                                break;
                                            }
                                        }

                                        /*defaultLocation.setId(finalLocationArrayList.get(position).getId());
                                        defaultLocation.setName(finalLocationArrayList.get(position).getName());
                                        defaultLocation.setDescription(finalLocationArrayList.get(position).getDescription());
                                        defaultLocation.setLeafLocation(finalLocationArrayList.get(position).getIsLeafLocation());
                                        defaultLocation.setLocationType(finalLocationArrayList.get(position).getLocationType());
                                        defaultLocation.setActive(finalLocationArrayList.get(position).getIsActive());
                                        defaultLocation.setTimeZoneId(finalLocationArrayList.get(position).getTimeZoneId());
                                        defaultLocation.setParentLocationId(floorParentID);

                                        profileData.setDefaultLocation(defaultLocation);*/

                                        ArrayList<DAOActiveLocation> buildingPlace = new ArrayList<>();
                                        ArrayList<DAOActiveLocation> cityList = new ArrayList<>();
                                        ArrayList<DAOActiveLocation> location = new ArrayList<>();

                                        etLocation.setText(floorName);

                                        buildingPlace.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == floorParentID).collect(Collectors.toList()));

                                        if (buildingPlace.size()>0) {
                                            cityPlaceID = buildingPlace.get(0).getId();
                                            cityPlaceParentID = buildingPlace.get(0).getParentLocationId();
                                            buildingName = buildingPlace.get(0).getName();
                                        }

                                        cityList.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == cityPlaceParentID).collect(Collectors.toList()));

                                        if (cityList.size()>0){
                                            cityID = cityList.get(0).getId();
                                            cityParentID = cityList.get(0).getParentLocationId();
                                            CityName = cityList.get(0).getName();
                                        }

                                        location.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == cityParentID).collect(Collectors.toList()));

                                        if (location.size()>0){
                                            locationID = location.get(0).getId();
                                            locationParentID = location.get(0).getParentLocationId();
                                            CountryName = location.get(0).getName();
                                        }

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

        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkspaceAssessmentActivity.this, DefaultLocationActivity.class);
                intent.putExtra(AppConstants.FROM,AppConstants.DefaultLocation);
                resultLauncher.launch(intent);
            }
        });

        etLocation.setText(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME));

    }

    private void validateData(List<QuestionListResponse> item)
    {
        if(etLocation.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Floor must not be Empty",Toast.LENGTH_LONG).show();
        }else if(etDate.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Date must not be Empty",Toast.LENGTH_LONG).show();
        }else if(questionListResponse.size()<0){
            Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG).show();
            finish();
        }else if(etDescription.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Description must not be Empty",Toast.LENGTH_LONG).show();
        }else {

            QuestionListRequest questionListRequest = new QuestionListRequest();
            List<QuestionListResponse> questionsList = new ArrayList<>();
            QuestionListRequest.FromDetails fromDetails = new QuestionListRequest.FromDetails();

            String dateTime = Utils.selectDateWithCurrentTimeTZFormat(etDate.getText().toString());

            questionListRequest.setAssessmentDate(dateTime);
            questionListRequest.setTemplate("");
            //questionListRequest.setLocation(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME));
            questionListRequest.setLocation(etLocation.getText().toString());

            fromDetails.setComment(etDescription.getText().toString());
            //fromDetails.setLocationId(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_ID));
            fromDetails.setLocationId(id);
            fromDetails.setDeskId(String.valueOf(deskId));
            /*questionListRequest.getFormDetails().setComment(etDescription.getText().toString());
            questionListRequest.getFormDetails().setLocationId(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_ID));
            questionListRequest.getFormDetails().setDeskId(String.valueOf(deskId));*/

            for(int i=0;i<item.size();i++){
                Log.d(TAG, "validateData: "+item.get(i));
                questionsList.add(item.get(i));
            }
            /*questionListRequest.getFormDetails().setQuestions(questionsList);*/
            fromDetails.setQuestions(questionsList);
            questionListRequest.setFormDetails(fromDetails);
            completeAndSign(questionListRequest);
        }
    }

    private void completeAndSign(QuestionListRequest questionListRequest)
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.completeAndSign(questionListRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "onResponse: ");
                Toast.makeText(getApplicationContext(),"Successfully Reported",Toast.LENGTH_LONG).show();
             finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
                finish();
                t.printStackTrace();
            }
        });



    }

    private void getLocationRI()
    {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeskResponseNew> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId="+id);
        call.enqueue(new Callback<DeskResponseNew>() {
            @Override
            public void onResponse(Call<DeskResponseNew> call, Response<DeskResponseNew> response)
            {
                deskList = new ArrayList<>();

                if (response.body()!=null && response.code() == 200) {

                    if (response.body().getDesk()!=null && response.body().getDesk().size()>0) {
                        deskList = response.body().getDesk();
                        setValuesToSpinner();
                        etDesk.setVisibility(View.VISIBLE);
                        //etDesk.setText(response.body().getDesk().get(0).getCode());
                    } else {
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
            public void onFailure(Call<DeskResponseNew> call, Throwable t) {
                hideSpinner();
            }


        });
    }

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

    private void getQuestionList()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<QuestionListResponse>> call = apiService.getQuestionList();
        call.enqueue(new Callback<List<QuestionListResponse>>() {
            @Override
            public void onResponse(Call<List<QuestionListResponse>> call, Response<List<QuestionListResponse>> response)
            {

                setAdapter(response);

            }

            @Override
            public void onFailure(Call<List<QuestionListResponse>> call, Throwable t)
            {

            }


        });
    }

    private void setAdapter(Response<List<QuestionListResponse>> response)
    {
        for(int i=0;i<response.body().size();i++){
            questionListResponse.add(response.body().get(i));
        }
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWorkspaceHeader.setLayoutManager(mLayoutManager);

        assessmentAdapter = new AssessmentAdapter(questionListResponse,WorkspaceAssessmentActivity.this);
        assessmentAdapter.setQuestionListListener(this);
        rvWorkspaceHeader.setAdapter(assessmentAdapter);
    }

    @Override
    public void updateAnswer(int categoryPosition, int questionPosition, boolean answer)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d(TAG, "updateAnswer: ");
                assessmentAdapter.itemsData.get(categoryPosition)
                        .getChecklistQuestions().get(questionPosition).setAnswer(answer);
                assessmentAdapter.notifyItemChanged(categoryPosition);
            }
        });
    }

    @Override
    public void showThingsToConsider(int catPosition, int qusPosition, String showThingsToConsider)
    {
        Utils.showCustomAlertWithEditTextDialog(WorkspaceAssessmentActivity.this, showThingsToConsider, new QuestionListEditListener()
        {
            @Override
            public void getText(String description)
            {
                questionListResponse.get(catPosition).getChecklistQuestions().get(qusPosition).setActionToTake(description);
            }
        });

    }

    public void setLanguage() {

        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(this);
        appKeysPage = getAppKeysPageScreenData(this);

        if (wellBeingPage!=null) {

            //New...

            mTitle.setText(wellBeingPage.getWorkPlaceAssessment());
            tvLocation.setText(appKeysPage.getLocation());
            etDate.setHint(appKeysPage.getSelectDate());
            reportCancel.setText(appKeysPage.getCancel());
            reportSubmit.setText(appKeysPage.getSubmit());
            tvDescription.setText(appKeysPage.getComments());

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        deskId = deskList.get(i).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}