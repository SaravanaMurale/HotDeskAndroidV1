package dream.guys.hotdeskandroid.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import dream.guys.hotdeskandroid.R;

public class BottomFilterActivity extends AppCompatActivity {

    Button bottmSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_filter);

        bottmSheet=findViewById(R.id.bottmSheet);

        bottmSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callFilterBottomSheet();

            }
        });
    }

    private void callFilterBottomSheet() {



    }
}