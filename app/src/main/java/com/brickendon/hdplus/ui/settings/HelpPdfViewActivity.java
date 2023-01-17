package com.brickendon.hdplus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpPdfViewActivity extends AppCompatActivity {


    @BindView(R.id.helpPdfView)
    PDFView pdfView;
    @BindView(R.id.locateProgressBar)
    ProgressBar locateProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_pdf_view);

        ButterKnife.bind(this);

        getHelpAndPDFHandler();

    }

    private void getHelpAndPDFHandler() {

        if (Utils.isNetworkAvailable(this)) {

            locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiService.downloadPdf();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.body() != null) {
                        ResponseBody responseBody = response.body();
                        InputStream is = responseBody.byteStream();
                        pdfView.fromStream(is).load();
                    } else {
                        Toast.makeText(HelpPdfViewActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    locateProgressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    locateProgressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }
}