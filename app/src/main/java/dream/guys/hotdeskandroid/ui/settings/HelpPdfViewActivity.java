package dream.guys.hotdeskandroid.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpPdfViewActivity extends AppCompatActivity {


    @BindView(R.id.helpPdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_pdf_view);

        ButterKnife.bind(this);

        /*String strData=getIntent().getStringExtra("PDFDATA");
        InputStream stream = new ByteArrayInputStream(strData.getBytes(StandardCharsets.UTF_8));
        pdfView.fromStream(stream);*/

       /* File file=new File(Environment.getExternalStorageDirectory() + "/" + "hotdesk");

        System.out.println("GetAbsoluteFilePath "+file.getAbsolutePath());
        System.out.println("GetAbsoluteFilePath1 "+file.getAbsolutePath()+ "/" +"Help.pdf");

        pdfView.fromFile(new File(file.getAbsolutePath()+ "/" +"Help.pdf"));*/

        //getHelpAndPDFHandler();

        //file:///storage/emulated/0/hotdesk/Help.pdf

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + "hotdesk" + "/" + "Help.pdf");
        pdfView.fromFile(pdfFile);

        System.out.println("GetAbsoluteFilePath "+pdfFile.getAbsolutePath());
        System.out.println("GetAbsoluteFilePath1 "+pdfFile.getAbsolutePath()+ "/" +"Help.pdf");

        //Uri path = Uri.fromFile(pdfFile);
        /*Uri path = Uri.parse("content://" + pdfFile.getAbsolutePath());
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pdfIntent);*/

    }


    private void getHelpAndPDFHandler() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call=apiService.downloadPdf();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                File apkStorage = null;
                File outputFile = null;
                if(response!=null) {

                    ResponseBody responseBody = response.body();
                    InputStream is = responseBody.byteStream();

                    pdfView.fromStream(is);

                    /*IOUtils.readInputStreamToString(is, StandardCharsets.UTF_8);
                    Intent intent=new Intent(SettingsActivity.this,HelpPdfViewActivity.class);
                    intent.putExtra("PDFDATA",is.toString());
                    startActivity(intent);*/


/*
                    try {
                        //Get File if SD card is present
                        if (isSDCardPresent()) {
                            apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "hotdesk");
                            System.out.println("FileCreated");
                        } else
                            Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                        //If File is not present create directory
                        if (!apkStorage.exists()) {
                            apkStorage.mkdir();
                            System.out.println("DirectorFileCreated");
                            //Log.e(TAG, "Directory Created.");
                        }

                        outputFile = new File(apkStorage, "Help.pdf");//Create Output file in Main File

                        //Create New File if not present
                        if (!outputFile.exists()) {
                            outputFile.createNewFile();
                            //Log.e(TAG, "File Created");


                            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                            //InputStream is = c.getInputStream();//Get InputStream for connection

                            byte[] buffer = new byte[1024];//Set buffer type
                            int len1 = 0;//init length
                            while ((len1 = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len1);//Write new file
                            }

                            //Close all connection after doing task
                            fos.close();
                            is.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        outputFile = null;

                    }*/



                }


               // Intent intent=new Intent(SettingsActivity.this,HelpPdfViewActivity.class);
               // startActivity(intent);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}