package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.listener.QuestionListEditListener;
import dream.guys.hotdeskandroid.listener.QuestionListListener;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.AssessmentAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.QuestionListRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DeskResponseNew;
import dream.guys.hotdeskandroid.model.response.QuestionListResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkspaceAssessmentActivity extends AppCompatActivity implements QuestionListListener
{
    private static final String TAG = "WorkspaceAssessment";

    LinearLayoutManager mLayoutManager;
    AssessmentAdapter assessmentAdapter;
    RecyclerView rvWorkspaceHeader;
    TextView etDate, reportCancel, reportSubmit, etLocation;
    EditText etDescription;
    ImageView ReportBack;
    int deskId=0;

    List<QuestionListResponse> questionListResponse = new ArrayList<>();

    LanguagePOJO.AppKeys appKeysPage;
    TextView mTitle,tvLocation,tvDescription;

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

        setLanguage();

        reportSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(assessmentAdapter!=null)
                {
                    for(QuestionListResponse item:questionListResponse)
                    {
                        List<QuestionListResponse.ChecklistQuestions> checklistQuestions = item.getChecklistQuestions();

                        validateData(questionListResponse);
                       /* for(QuestionListResponse.ChecklistQuestions subItem:checklistQuestions)
                        {
                            Log.d(TAG, "CategoryName" + subItem.getCategoryName());
                            Log.d(TAG, "Question" + subItem.getRiskFactors());
                            Log.d(TAG, "Answer" + subItem.isAnswer());
                            Log.d(TAG, "Action" + subItem.getActionToTake());
                        }*/
                    }
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

        getLocationRI();
        getQuestionList();
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


            questionListRequest.setAssessmentDate(etDate.getText().toString());
            questionListRequest.setTemplate("");
            questionListRequest.setLocation(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME));

            fromDetails.setComment(etDescription.getText().toString());
            fromDetails.setLocationId(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_ID));
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
        Call<BaseResponse> call = apiService.completeAndSign(questionListRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                Log.d(TAG, "onResponse: ");
                Toast.makeText(getApplicationContext(),"Successfully Reported",Toast.LENGTH_LONG).show();
//                finish();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
                finish();
                t.printStackTrace();
            }
        });



    }

    private void getLocationRI()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeskResponseNew> call = apiService.getDesk(AppConstants.BASE_URL+"api/wellness/desks"+"?locationId=20");
        call.enqueue(new Callback<DeskResponseNew>() {
            @Override
            public void onResponse(Call<DeskResponseNew> call, Response<DeskResponseNew> response)
            {
                if (response.body()!=null && response.code() == 200) {

                    if (response.body().getDesk()!=null && response.body().getDesk().size()>0) {

                        deskId = response.body().getDesk().get(0).getId();
                        etLocation.setText(SessionHandler.getInstance().get(WorkspaceAssessmentActivity.this, AppConstants.DEFAULT_LOCATION_NAME)+" Desk-"+response.body().getDesk().get(0).getCode());

                    }else {

                    }

                }

            }

            @Override
            public void onFailure(Call<DeskResponseNew> call, Throwable t) {

            }


        });
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
            etDate.setText(appKeysPage.getSelectDate());
            reportCancel.setText(appKeysPage.getCancel());
            reportSubmit.setText(appKeysPage.getSubmit());
            tvDescription.setText(appKeysPage.getDescription());

        }

    }

}