package dream.guys.hotdeskandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.adapter.LanguageListAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityEditProfileBinding;
import dream.guys.hotdeskandroid.databinding.ActivityLanguageListBinding;
import dream.guys.hotdeskandroid.model.response.LanguageListResponse;

public class LanguageListActivity extends AppCompatActivity implements LanguageListAdapter.OnLanguageSelect {

    ActivityLanguageListBinding activityLanguageListBinding;
    LanguageListAdapter languageListAdapter;

    List<LanguageListResponse.Multilanguages.ExistingLanguages> languageListResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLanguageListBinding = ActivityLanguageListBinding.inflate(getLayoutInflater());
        setContentView(activityLanguageListBinding.getRoot());


        languageListResponseList=new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityLanguageListBinding.rvLanguage.setLayoutManager(linearLayoutManager);
        activityLanguageListBinding.rvLanguage.setHasFixedSize(true);

        LanguageListResponse languageListResponse=new LanguageListResponse();
        LanguageListResponse.Multilanguages multilanguages=languageListResponse.new Multilanguages();
        //LanguageListResponse.Multilanguages.ExistingLanguages existingLanguages=multilanguages.new ExistingLanguages();


        try {
            JSONObject jsonObject=new JSONObject(LoadJsonFromAsset());
            JSONObject multilanguagesObject=jsonObject.getJSONObject("multilanguages");
            JSONArray existingLanguagesArray =multilanguagesObject.getJSONArray("existingLanguages");

            for (int i = 0; i <existingLanguagesArray.length() ; i++) {

                JSONObject jsonObject1=existingLanguagesArray.getJSONObject(i);
                LanguageListResponse.Multilanguages.ExistingLanguages existingLanguages=multilanguages.new ExistingLanguages(jsonObject1.getString("name"),jsonObject1.getString("Text"),jsonObject1.getString("imagesrc"));
                languageListResponseList.add(existingLanguages);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        languageListAdapter=new LanguageListAdapter(LanguageListActivity.this,languageListResponseList,this);
        activityLanguageListBinding.rvLanguage.setAdapter(languageListAdapter);

    }

    private String LoadJsonFromAsset() {

        String json = null;
        try {
            InputStream is = this.getAssets().open("multilanguage.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onLanguageSelect() {

    }
}