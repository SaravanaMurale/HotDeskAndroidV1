package com.brickendon.hdplus.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;

import com.brickendon.hdplus.R;

public class FreshChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_chat);

        FreshchatConfig config = new FreshchatConfig("YOUR-APP-ID","YOUR-APP-KEY");
        config.setDomain("YOUR-DOMAIN");
        config.setCameraCaptureEnabled(true);
        config.setGallerySelectionEnabled(true);
        config.setResponseExpectationEnabled(true);
        Freshchat.getInstance(getApplicationContext()).init(config);
    }
}