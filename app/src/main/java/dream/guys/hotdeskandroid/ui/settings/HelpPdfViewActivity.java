package dream.guys.hotdeskandroid.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
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

                    if (response != null) {

                        ResponseBody responseBody = response.body();
                        InputStream is = responseBody.byteStream();
                        pdfView.fromStream(is).load();
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