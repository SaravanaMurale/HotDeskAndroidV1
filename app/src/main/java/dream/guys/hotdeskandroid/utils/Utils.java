/*
// STANDSPOT, LLC CONFIDENTIAL
// Unpublished Copyright (c) 2021 STANDSPOT, LLC, All Rights Reserved.
//
// NOTICE:  All information contained herein is, and remains the property of Standspot, LLC. The intellectual and technical concepts contained
// herein are proprietary to Standspot, LLC and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
// Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained
// from Standspot, LLC.  Access to the source code contained herein is hereby forbidden to anyone except current Standspot, LLC employees, managers or contractors who have executed
// Confidentiality and Non-disclosure agreements explicitly covering such access.
//
// The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code, which includes
// information that is confidential and/or proprietary, and is a trade secret, of  Standspot, LLC.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
// OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF Standspot, LLC IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE
// LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
// TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
//
*/


package dream.guys.hotdeskandroid.utils;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.ui.login.SignInActivity;

/**
 * Created by bala on 17/06/2022.
 */

public class Utils {


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static int hour;
    private static int minutes;

    public static void closeKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValiedText(String text){
        return !TextUtils.isEmpty(text) && !text.equals("") && text!=null;
    }

    public static void toastMessage(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String getToken() {
        String token = "";
        if (SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.USERTOKEN) != null) {
            token = SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.USERTOKEN);
        }
        return token;
    }


    //Bottom Sheet Designs
    public static void bottomSheetTimePicker(Context mContext, Activity activity, String title, String date) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));
        TimePicker simpleTimePicker = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        titleTv.setText(title);
        dateTv.setText(date);

        simpleTimePicker.setCurrentHour(5); // before api level 23
//        simpleTimePicker.setHour(5); // from api level 23
//        pickerint hours =simpleTimePicker.getCurrentHour(); // before api level 23
//        int hours =simpleTimePicker.getHour();
        bottomSheetDialog.show();
    }
    public static void bottomSheetEditYourBooking(Context mContext, Activity activity, String title, String date) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(activity)));

        TextView startTime = bottomSheetDialog.findViewById(R.id.start_time);
        TextView endTime = bottomSheetDialog.findViewById(R.id.end_time);
        ChipGroup chipGroup = bottomSheetDialog.findViewById(R.id.list_item);
        for (int i=0; i<5; i++){
            Chip chip = new Chip(activity);
            chip.setId(i);
            chip.setText("ABC "+i);
            chip.setChipBackgroundColorResource(R.color.figmaGrey);
            chip.setCloseIconVisible(false);
            chip.setTextColor(activity.getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
            chipGroup.addView(chip);
        }

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(activity,startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(activity,endTime);
            }
        });
        bottomSheetDialog.show();
    }

    private static void popUpTimePicker(Activity activity, TextView v) {
//        TextView startTime = v;
        TimePickerDialog timePickerDialog=new TimePickerDialog(activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hour=hourOfDay;
                        minutes=minute;
                        String time=hourOfDay+":"+minute;

                        SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");

                        try {
                            Date date=f24hours.parse(time);
                            SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");
//                            return String.valueOf(f12hours.format(date));
                            v.setText(""+f12hours.format(date));
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

    public static String getCurrentDate(){
         String date= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            date=dtf.format(now);

        }
        return date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getCurrentDateFormat(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
//        System.out.println(dtf.format(now));
        return now;
    }

    public static String getCurrentTime(){
         String time= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            time=dtf.format(now);
        }
        return time;
    }

    public static boolean checkTokenExpiry() {

        boolean tokenExpiryStatus = false;
        String date=SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.EXPIRY_TOKEN_DATE);

        if (getToken() != null && date!=null) {
            //if (date.equals(getCurrentDate()))
            if (date.equals(getCurrentDate())) {

                tokenExpiryStatus = true;
            } else {
                SessionHandler.getInstance().remove(MyApp.getContext(), AppConstants.USERTOKEN);
                tokenExpiryStatus = false;
            }
        }


        return  tokenExpiryStatus;

    }

    public static void saveTokenDateAndTimeInPreference(String token){

        String expiryTokenDate="",expiryTokenTime="",expiryTokenTimeWithZ="";
        String[] words=token.split("T");

        for (int i = 0; i <words.length ; i++) {

            if(i==0){
                expiryTokenDate=words[i];
            }

            if(i==1){
                expiryTokenTimeWithZ=words[i];
            }

            String[] tokenTime=expiryTokenTimeWithZ.split("Z");
            System.out.println("TokenSplitData"+tokenTime[0]);

        }

        System.out.println("FinalTokenDateAndTime"+expiryTokenDate+" "+expiryTokenTime);

        SessionHandler.getInstance().save(MyApp.getContext(),AppConstants.EXPIRY_TOKEN_DATE,expiryTokenDate);
        SessionHandler.getInstance().save(MyApp.getContext(),AppConstants.EXPIRY_TOKEN_TIME,expiryTokenTime);

    }

    public static void finishAllActivity(Context context){
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
