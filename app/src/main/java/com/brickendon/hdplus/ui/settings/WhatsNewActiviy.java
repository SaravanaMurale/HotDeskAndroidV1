package com.brickendon.hdplus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.brickendon.hdplus.R;

public class WhatsNewActiviy extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new_activiy);

        webView=(WebView) findViewById(R.id.whatsNewWebview);

        webView.loadUrl("https://www.hybridhero.io/getting-started");
    }
}