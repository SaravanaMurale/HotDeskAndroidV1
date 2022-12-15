package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
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
import dream.guys.hotdeskandroid.adapter.WellbeingCommonAdapter;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.WellbeingConfigResponse;

public class WellbeingCommonActivity extends AppCompatActivity {

    TextView profile_edit, tvHeader, tvContent, tvLinks;
    RecyclerView rvCommon;
    ImageView ReportBack;
    ImageView toolbarIcon;

    LinearLayoutManager mLayoutManager;
    WellbeingCommonAdapter wellbeingCommonAdapter;
    List<WellbeingConfigResponse.Link> linksArray = new ArrayList<>();
    LanguagePOJO.AppKeys appKeysPage;


    public String WELLBEING_TYPE = "WELLBEING_TYPE";
    public String PERSONAL_CONTENT = "PERSONAL_CONTENT";
    public String PERSONAL_LINKS = "PERSONAL_LINKS";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellbeing_common);
        Intent intent = getIntent();

        profile_edit = findViewById(R.id.profile_edit);
        tvHeader = findViewById(R.id.tvHeader);
        rvCommon = findViewById(R.id.rvCommon);
        tvContent = findViewById(R.id.tvContent);
        ReportBack = findViewById(R.id.ReportBack);
        tvLinks = findViewById(R.id.tvLinks);
        toolbarIcon = findViewById(R.id.profile_update);
        setLanguage();

        profile_edit.setText(getIntent().getStringExtra(WELLBEING_TYPE));
        tvContent.setText(intent.getStringExtra(WELLBEING_TYPE));
        if (!intent.getStringExtra(PERSONAL_CONTENT).isEmpty()) {
            tvHeader.setText(intent.getStringExtra(PERSONAL_CONTENT));
        } else {
            tvHeader.setText("No Description");
        }

        try {
            switch (getIntent().getStringExtra("type")) {
                case "Health Tips":
                    toolbarIcon.setBackgroundResource(R.drawable.healthtips);
                    break;
                case "Mental Health":
                    toolbarIcon.setBackgroundResource(R.drawable.person);
                    break;
                case "Notice":
                    toolbarIcon.setBackgroundResource(R.drawable.icon_notices);
                    break;
                case "Rewards":
                    toolbarIcon.setBackgroundResource(R.drawable.icon_rewards);
                    break;
                case "Benefits":
                    toolbarIcon.setBackgroundResource(R.drawable.ic_benefits);
                    break;
                case "Events":
                    toolbarIcon.setBackgroundResource(R.drawable.icon_events);
                    break;
                case "menu":
                    toolbarIcon.setBackgroundResource(R.drawable.icon_menu);
                    break;
                default:
                    toolbarIcon.setBackgroundResource(R.drawable.ic_personal);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        ReportBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        linksArray = (List<WellbeingConfigResponse.Link>) intent.getSerializableExtra(PERSONAL_LINKS);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCommon.setLayoutManager(mLayoutManager);
        wellbeingCommonAdapter = new WellbeingCommonAdapter(this, linksArray);
        rvCommon.setAdapter(wellbeingCommonAdapter);


    }

    private void setLanguage() {
        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(this);
        appKeysPage = getAppKeysPageScreenData(this);
        //  profile_edit.setText(wellBeingPage.getTabTitle());
        tvContent.setText(wellBeingPage.getTabTitle());
        tvLinks.setText(appKeysPage.getLinks());
    }
}
