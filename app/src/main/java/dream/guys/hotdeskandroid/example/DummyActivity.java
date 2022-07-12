package dream.guys.hotdeskandroid.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dream.guys.hotdeskandroid.R;

public class DummyActivity extends AppCompatActivity {

    TextView timePicker;
    int hour,minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        timePicker = (TextView) findViewById(R.id.timePicker);

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog=new TimePickerDialog(DummyActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hour=hourOfDay;
                        minutes=minute;
                        String time=hourOfDay+":"+minute;

                        SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");

                        try {
                            Date date=f24hours.parse(time);
                            SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");

                            System.out.println("ReceivedDate"+f12hours.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },12,0,false);


                timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Continue",timePickerDialog);
                timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Back",timePickerDialog);

                //timePickerDialog.setContentView(R.layout.layout_sso);
                timePickerDialog.setTitle("Start\nWed, 10th August,2022");



                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour,minutes);
                timePickerDialog.show();

            }
        });


    }
}