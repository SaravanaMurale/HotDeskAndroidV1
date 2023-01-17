package com.brickendon.hdplus.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.request.GDPRrequest;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GDPRActivity extends AppCompatActivity {
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.decline)
    TextView decline;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.notice)
    ImageView notice;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.webView2)
    WebView mWebView2;
    Dialog dialog;
    String tenantName,userName;
    String url, url2;
    CustomTabsIntent.Builder customIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdpr);
        ButterKnife.bind(this);
        dialog = new Dialog(getApplicationContext());
        tenantName = getIntent().getExtras().getString("tenantName");
        userName = getIntent().getExtras().getString("userName");
//        getUrlWebview();
        //webview implement
        mWebView.requestFocus();
        mWebView2.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView2.getSettings().setJavaScriptEnabled(true);
//        String url="https://hdplusdev.b2clogin.com/hdplusdev.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1A_signup_signin_Multitenant&client_id=3558aa69-9b0b-442d-b81b-d30defd1c72a&response_type=code+id_token&redirect_uri=com.brickendon.hdplus://oauth/redirect&response_mode=query&scope=openid%20offline_access&state=12345";
//        url="https://hdplusdev.b2clogin.com/hdplusdev.onmicrosoft.com/oauth2/v2.0/authorize?p=B2C_1A_signup_signin_Multitenant&client_id=3558aa69-9b0b-442d-b81b-d30defd1c72a&response_type=code+id_token&redirect_uri=com.brickendon.hdplus://oauth/redirect&response_mode=query&scope=openid%20offline_access&state=12345&domain_hint=google.com";
//        String url="https://www.google.com/";
        url = "https://docs.google.com/viewer?embedded=true&url="+"https://hybridhero.com/documents/EULA_Android.pdf";
        url2 = "https://docs.google.com/viewer?embedded=true&url="+"https://hybridhero.com/documents/collection_notice.pdf";
        mWebView.loadUrl(url);
        mWebView2.loadUrl(url2);
        System.out.println("Baranding Url"+url);
//        customIntent = new CustomTabsIntent.Builder();

        // below line is setting toolbar color
        // for our custom chrome tab.
//        customIntent.setToolbarColor(ContextCompat.getColor(GDPRActivity.this, R.color.purple_200));
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.GONE);
                mWebView2.setVisibility(View.VISIBLE);
                notice.setVisibility(View.GONE);
                header.setText("Collection Notice");
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notice.getVisibility() == View.VISIBLE){

                } else {
                    mWebView.setVisibility(View.VISIBLE);
                    mWebView2.setVisibility(View.GONE);
                    notice.setVisibility(View.VISIBLE);
                    header.setText("Terms of service");
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.getTitle().equals("")) {
                    view.reload();
                }
            }
        });
        mWebView2.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.getTitle().equals("")) {
                    view.reload();
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
//                    progressDialog.show();
//                    dialog=ProgressDialog.showProgressBar(GDPRActivity.this);
                }
                if (progress == 100) {
//                    progressDialog.dismiss();
//                    ProgressDialog.dismisProgressBar(GDPRActivity.this,dialog);
                }
            }
        });
        mWebView2.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
//                    progressDialog.show();
//                    dialog=ProgressDialog.showProgressBar(GDPRActivity.this);
                }
                if (progress == 100) {
//                    progressDialog.dismiss();
//                    ProgressDialog.dismisProgressBar(GDPRActivity.this,dialog);
                }
            }
        });



//        openCustomTab(this,customIntent.build(), Uri.parse(url));
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGDPR();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUrlWebview() {
        if (Utils.isNetworkAvailable(this)) {
            dialog=ProgressDialog.showProgressBar(GDPRActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JsonObject jsonObject = new JsonObject();
            if (!tenantName.equalsIgnoreCase(""))
                jsonObject.addProperty("tenantName",tenantName);
            Call<String> call = apiService.privacyPolicy(jsonObject);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code()==200){
                        ProgressDialog.dismisProgressBar(GDPRActivity.this,dialog);
                        url = response.body();
//                        url = "https://docs.google.com/viewer?embedded=true&url="+response.body();
                        url = "https://docs.google.com/viewer?embedded=true&url="+response.body();
                        mWebView.loadUrl(url);

                        System.out.println("check url string: "+url);
//                        openCustomTab(GDPRActivity.this,customIntent.build(), Uri.parse(url));

                    }else if (response.code() == 403){
                        ProgressDialog.dismisProgressBar(GDPRActivity.this,dialog);

                    }else {
                        ProgressDialog.dismisProgressBar(GDPRActivity.this,dialog);
                        Utils.toastShortMessage(GDPRActivity.this,"SSO Login is not setup for this email contact admin.");
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
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
    private void callGDPR() {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(GDPRActivity.this);
            GDPRrequest gdpRrequest = new GDPRrequest();
            gdpRrequest.setTenantName(tenantName);
            gdpRrequest.setUserName(userName);
            gdpRrequest.setGdprAccepted(true);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Void> call = apiService.updateGDPR(gdpRrequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code()==200){
                        Utils.toastMessage(getApplicationContext(),"Successfull, Please Login");
                    }
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Utils.toastMessage(getApplicationContext(),"Failure, Please Login"+t.getMessage());
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }

    }

    @Override
    public void onBackPressed() {

    }
}