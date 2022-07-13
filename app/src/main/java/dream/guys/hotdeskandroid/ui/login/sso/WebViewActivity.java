package dream.guys.hotdeskandroid.ui.login.sso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.utils.AppConstants;

public class WebViewActivity extends AppCompatActivity {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = findViewById(R.id.webView);
        mWebView.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);
//        String url="https://hdplusdev.b2clogin.com/hdplusdev.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1A_signup_signin_Multitenant&client_id=3558aa69-9b0b-442d-b81b-d30defd1c72a&response_type=code+id_token&redirect_uri=com.brickendon.hdplus://oauth/redirect&response_mode=query&scope=openid%20offline_access&state=12345";
        String url="https://hdplusdev.b2clogin.com/hdplusdev.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1A_signup_signin_Multitenant&client_id=3558aa69-9b0b-442d-b81b-d30defd1c72a&response_type=code+id_token&redirect_uri=com.brickendon.hdplus://oauth/redirect&response_mode=query&scope=openid%20offline_access&state=12345&domain_hint=google.com";
//        String url="https://www.google.com/";
        System.out.println("Baranding Url"+url);
        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

        // below line is setting toolbar color
        // for our custom chrome tab.
        customIntent.setToolbarColor(ContextCompat.getColor(WebViewActivity.this, R.color.purple_200));

        openCustomTab(this,customIntent.build(),Uri.parse(url));

    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}