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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
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
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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

    //Locate Booking TimerPicker BottomSheet
    public static void bottomSheetTimePickerInBooking(Context mContext, Activity activity,TextView tv, String title, String date) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));

        TimePicker simpleTimePicker24Hours = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        //simpleTimePicker24Hours.setIs24HourView(false);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);
        titleTv.setText(title);
        dateTv.setText(date);

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("GETDATATATATA"+simpleTimePicker24Hours.getHour()+":"+simpleTimePicker24Hours.getMinute()+":"+simpleTimePicker24Hours.getMinute()+":"+simpleTimePicker24Hours.getMinute());
                //tv.setText(getCurrentDate()+""+"T"+simpleTimePicker24Hours.getHour()+":"+simpleTimePicker24Hours.getMinute()+":"+"00"+"."+"000"+"Z");

                tv.setText(simpleTimePicker24Hours.getHour()+":"+simpleTimePicker24Hours.getMinute());

                //SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }
    public static void tokenExpiryAlert(final Context mContext, String msg) {
        final Activity activity = (Activity) mContext;

        AlertDialog.Builder userInactiveAlertBuilder = new AlertDialog.Builder(mContext);
        userInactiveAlertBuilder.setTitle("Warning!!!");
        userInactiveAlertBuilder.setCancelable(false);
        userInactiveAlertBuilder.setMessage("Sorry, Your account is inactive. Contact your administrator to activate it.");

        userInactiveAlertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callLogin = new Intent(mContext, LoginActivity.class);
                callLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(callLogin);
                activity.finish();
                dialog.dismiss();
            }
        });

        userInactiveAlertBuilder.show();
    }



    //Bottom Sheet TimePicker
    public static void bottomSheetTimePicker(Context mContext, Activity activity,TextView tv, String title, String date) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));
        TimePicker simpleTimePicker = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(false);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);
        titleTv.setText(title);
        dateTv.setText(date);

        String[] parts =Utils.convert12HrsTO24Hrs(tv.getText().toString()).split(":");
        simpleTimePicker.setHour(Integer.parseInt(parts[0]));
        simpleTimePicker.setMinute(Integer.parseInt(parts[1]));

        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        simpleTimePicker.setHour(Integer.parseInt(part1));
        simpleTimePicker.setMinute(Integer.parseInt(part2));

//        simpleTimePicker.setCurrentHour(5); // before api level 23
//        simpleTimePicker.setHour(5); // from api level 23
//        pickerint hours =simpleTimePicker.getCurrentHour(); // before api level 23
//        int hours =simpleTimePicker.getHour();
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour=simpleTimePicker.getHour();
                minutes=simpleTimePicker.getMinute();
                String time=hour+":"+minutes;

                SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");

                try {
                    Date date=f24hours.parse(time);
                    SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");
//                            return String.valueOf(f12hours.format(date));
                    tv.setText(""+f12hours.format(date));
                    System.out.println("ReceivedDate"+f12hours.format(date));
                    bottomSheetDialog.dismiss();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        bottomSheetDialog.show();
    }

    //BotomSheet DatePicker
    public static void bottomSheetDatePicker(Context mContext, Activity activity, String title, String date, TextView locateCheckInDate){

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView=bottomSheetDatePicker.findViewById(R.id.datePicker);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString=String.valueOf(year);

                int actualMonth=month+1;
                String monthInStringFormat;

                if(actualMonth>=10){
                    monthInStringFormat=String.valueOf(actualMonth);
                }else {
                    String monthInString=String.valueOf(actualMonth);
                    monthInStringFormat="0"+monthInString;
                }


                String dayInString=String.valueOf(dayOfMonth);
                String dateInString= yearInString+"-"+monthInStringFormat+"-"+dayInString;
                System.out.println("PickedDate"+dateInString);
                //locateCheckInDate.setText(dateInString+"T"+getCurrentTimeIn24HourFormat()+".000"+"Z");
                //locateCheckInDate.setText(dateInString+"T"+"00:00:00.000"+"Z");
                locateCheckInDate.setText(""+dateInString);


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("ContinuPrintHere"+locateCheckInDate.getText());
                //Utils.dayDateMonthFormat();

                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();

    }

    public static void showCustomAlertDialog(final Activity mContext, String aMessage) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_validation);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText(aMessage);
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivityForResult(intent, 123);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
    public static void showCustomTokenExpiredDialog(final Activity mContext, String aMessage) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_validation);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText(aMessage);
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivityForResult(intent, 123);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
                popUpTimePicker(activity,startTime,"");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(activity,endTime,"");
            }
        });
        bottomSheetDialog.show();
    }

    public static String convert24HrsTO12Hrs(String time) {
        time.replace(".",":");
        SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");
        SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");
        try {
            Date date=f24hours.parse(time);
//                            return String.valueOf(f12hours.format(date));
//            v.setText(""+f12hours.format(date));
            System.out.println("ReceivedDate Bala"+f12hours.format(date));
            return ""+f12hours.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }

    }
    public static String convert12HrsTO24Hrs(String time) {
        time.replace(".",":");
        SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");
        SimpleDateFormat f12hours=new SimpleDateFormat("hh:mm aa");
        try {
            Date date=f12hours.parse(time);
//                            return String.valueOf(f12hours.format(date));
//            v.setText(""+f12hours.format(date));
            System.out.println("ReceivedDate Bala"+f24hours.format(date));
            return ""+f24hours.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }

    }
    public static void popUpTimePicker(Activity activity, TextView v, String tilte) {
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

        timePickerDialog.setTitle(HtmlCompat.fromHtml("Start <br>"+tilte,HtmlCompat.FROM_HTML_MODE_LEGACY));


        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hour,minutes);
        timePickerDialog.show();

    }

    public static String dayDateMonthFormat(Date date){
        DateFormat df = new SimpleDateFormat("EEE,d MMM yyyy");
        return df.format(date);
    }
    public static String getISO8601format(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        return df.format(date);
    }

    public static String getYearMonthDateFormat(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
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

    public static String getCurrentTimeIn24HourFormat(){
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str = sdf.format(new Date());
        System.out.println("sout"+str);
        return str;

    }

    public static String getCurrentTime(){
         String time= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
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
//            System.out.println("TokenSplitData"+tokenTime[0]);

        }

        System.out.println("FinalTokenDateAndTime"+expiryTokenDate+" "+expiryTokenTime);

        SessionHandler.getInstance().save(MyApp.getContext(),AppConstants.EXPIRY_TOKEN_DATE,expiryTokenDate);
        SessionHandler.getInstance().save(MyApp.getContext(),AppConstants.EXPIRY_TOKEN_TIME,expiryTokenTime);

    }


    public static String getDayAndDateFromDateFormat(Date date){

        String dayInTextWithComma=""; String dayInText=""; String dayInNumber="";

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);

        formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        strDate = formatter.format(date);
        System.out.println("Date Format with E, dd MMM yyyy HH:mm:ss z : "+strDate);

        String[] words=strDate.split(" ");

        for (int i = 0; i <words.length ; i++) {

            if(i==0){
                dayInTextWithComma=words[i];
            }

            if(i==1){
                dayInNumber=words[i];
            }

        }

        String[] day=dayInTextWithComma.split(",");

           dayInText=day[0];

        return dayInText+" "+dayInNumber;

    }

    public static void splitDate(String dateWithTandZ){
        String date="";
        String[] words=dateWithTandZ.split("T");

        for (int i = 0; i <words.length ; i++) {

            if (i == 0) {
                date = words[i];
            }

        }

    }

    public static boolean compareTimeIfCheckInEnable(String startTime, String endTime){
        startTime = startTime.replace(":",".");
        endTime = endTime.replace(":",".");

        System.out.println(startTime+" balas "+endTime);
        if (Double.parseDouble(startTime) >= Double.parseDouble(endTime))
            return true;
        else
            return false;
    }

    public static String splitTime(String dateWithTandZ){

        Date date=null;
        String timeWithZ="";
        String time="";

        String[] words=dateWithTandZ.split("T");

        for (int i = 0; i <words.length ; i++) {
            if(i==1){
                timeWithZ=words[i];
            }

            String[] tokenTime=timeWithZ.split("Z");
            time=tokenTime[0];
//            System.out.println("TokenSplitData"+tokenTime[0]);

        }

        String[] timeWithColon=time.split(":");

        String hour="";String min="";String hourMinFormet="";
        for (int i = 0; i <timeWithColon.length ; i++) {

            if(i==0){
                hour=timeWithColon[i];
            }
            if(i==1){
                min=timeWithColon[i];
            }
        }

        hourMinFormet=hour+":"+min;
        return hourMinFormet;

    }

    public static void finishAllActivity(Context context){
        Intent i = new Intent(context, SignInActivity.class);
        context.startActivity(i);
        //finishAllActivity(context);
    }

    public static String getCurrentDateInDateFormet(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("UtilDateFormat"+formatter.format(date));


        return formatter.format(date);

    }


    public static Date convertStringToDateFormet(String dateInString){

        //String sDate1="31/12/1998";
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(dateInString+"\t"+date1);

        return date1;

    }


    public static int compareTwoDate(Date date, String currentDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = formatter.parse(formatter.format(date));
            Date d1 = formatter.parse(currentDate);

            if(d1.compareTo(d) <0){// not expired
                return 3;
            }else if(d.compareTo(d1)==0){// both date are same
                if(d.getTime() < d1.getTime()){// not expired
                    return 1;
                }else if(d.getTime() == d1.getTime()){//expired
                    return 2;
                }else{//expired
                    return 2;
                }
            }else{//expired
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 3;
        }

    }


    public static String removeTandZInDate(String date){

        String result = date.replace("T", " ");
        String finalResult=result.replace("Z","");

        return finalResult;

    }

    public static int compareTwoDates(String date1,String date2){

        System.out.println("CurrentDate"+date1);
        System.out.println("OfsetAddedDate"+date2);

        int dateCompare=-1;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = sdformat.parse(date1);
            Date d2 = sdformat.parse(date2);


            if(d1.compareTo(d2)<0){
                dateCompare=1;
                System.out.println("CurrentTimeLessAddedTimeHigh");
            }else if(d1.compareTo(d2)>0){
                dateCompare=2;
                System.out.println("CurrentTimeHihgAddedTimeLess");
            }else if(d1.compareTo(d2)==0){
                dateCompare=0;
                System.out.println("CurrentTimeAndAddedTimeEqual");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateCompare;



    }


    public static String addingHoursToCurrentDate(int currentTimeZoneOffset){
        //Should add seconds

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(Utils.getCurrentTimeIn24HourFormat());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND, currentTimeZoneOffset);
        String newTime = df.format(cal.getTime());

        return  newTime;


    }



    public static  String splitGetDate(String dateWithTime){

        //Split key to get id and code
        String[] result = dateWithTime.split(" ");
        String dateAlone= result[0];

        return dateAlone;

    }
}
