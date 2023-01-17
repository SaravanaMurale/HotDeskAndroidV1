package com.brickendon.hdplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.brickendon.hdplus.adapter.LanguageListAdapter;
import com.brickendon.hdplus.databinding.ActivityEditProfileBinding;
import com.brickendon.hdplus.databinding.ActivityLanguageListBinding;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.response.LanguageListResponse;
import com.brickendon.hdplus.utils.Utils;

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
            JSONObject jsonObject=new JSONObject(LoadJsonFromAsset("multilanguage.json"));
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

        activityLanguageListBinding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private String LoadJsonFromAsset(String fileName) {

        String json = null;
        try {
            InputStream is = this.getAssets().open(fileName);
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
    public void onLanguageSelect(String key) {

        String fileName = key+".json";
        String json = LoadJsonFromAsset(fileName);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<LanguagePOJO>() { }.getType();
        LanguagePOJO langPOJO = gson.fromJson(json, listUserType);

        setLocale(key);
        Utils.setLangInPref(langPOJO,LanguageListActivity.this);

    }

    public void setLocale(String lang) {
        /*Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);*/
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
        finish();

    }

}