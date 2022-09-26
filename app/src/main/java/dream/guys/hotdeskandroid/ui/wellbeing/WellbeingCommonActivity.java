package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.PersonalHelpAdapter;
import dream.guys.hotdeskandroid.adapter.WellbeingCommonAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.WellbeingConfigResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;

public class WellbeingCommonActivity extends AppCompatActivity
{

    TextView profile_edit, tvHeader, tvContent;
    RecyclerView rvCommon;
    ImageView ReportBack;

    LinearLayoutManager mLayoutManager;
    WellbeingCommonAdapter wellbeingCommonAdapter;
    List<WellbeingConfigResponse.Link> linksArray = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellbeing_common);
        Intent intent=getIntent();

        profile_edit = findViewById(R.id.profile_edit);
        tvHeader = findViewById(R.id.tvHeader);
        rvCommon = findViewById(R.id.rvCommon);
        tvContent = findViewById(R.id.tvContent);
        ReportBack = findViewById(R.id.ReportBack);

        profile_edit.setText(intent.getStringExtra(AppConstants.WELLBEING_TYPE));
        tvContent.setText(intent.getStringExtra(AppConstants.WELLBEING_TYPE));
        if(!intent.getStringExtra(AppConstants.PERSONAL_CONTENT).isEmpty()){
            tvHeader.setText(intent.getStringExtra(AppConstants.PERSONAL_CONTENT));
        } else {
            tvHeader.setText("No Description");
        }


        ReportBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        linksArray= (List<WellbeingConfigResponse.Link>) intent.getSerializableExtra(AppConstants.PERSONAL_LINKS);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCommon.setLayoutManager(mLayoutManager);
        wellbeingCommonAdapter = new WellbeingCommonAdapter(this, linksArray);
        rvCommon.setAdapter(wellbeingCommonAdapter);


        //setLanguage();

    }

    private void setLanguage() {
        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(this);
        profile_edit.setText(wellBeingPage.getTabTitle());
        tvContent.setText(wellBeingPage.getTabTitle());
    }
}
