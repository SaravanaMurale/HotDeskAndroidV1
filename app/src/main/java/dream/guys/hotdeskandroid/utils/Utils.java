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

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.listener.QuestionListEditListener;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
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

    public static Boolean isThemeChanged(Context mContext) {
        Boolean appTheme = false;
        if (SessionHandler.getInstance().get(mContext, AppConstants.APPTHEME) != null
                && !SessionHandler.getInstance().get(mContext, AppConstants.APPTHEME).isEmpty()) {
            appTheme = true;
        }
        return appTheme;
    }

    public static int getAppTheme(Context mContext) {
        int appTheme = 0;
        if (SessionHandler.getInstance().get(mContext, AppConstants.APPTHEME) != null &&
                !SessionHandler.getInstance().get(mContext, AppConstants.APPTHEME).isEmpty()) {
            appTheme = Color.parseColor(SessionHandler.getInstance().get(mContext, AppConstants.APPTHEME));
        } else {
            appTheme = mContext.getColor(R.color.figmaBlueText);
        }


        return appTheme;
    }

    public static void closeKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String checkStringParms(String text) {
        if (text != null && !text.equalsIgnoreCase("null"))
            return text;
        else
            return "";
    }

    public static Integer checkStringParms(Integer text) {
        if (text != null)
            return text;
        else
            return 0;
    }

    public static boolean isValidPassword(String password) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[.@#$%^&+=])"
                + "(?=\\S+$).{6,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);
        // Return if the password
        // matched the ReGex
        return m.matches();
    }
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValiedCompanyName(String text) {
        return !TextUtils.isEmpty(text) && !text.equals("");
    }

    public static boolean isValiedText(String text) {
        return !TextUtils.isEmpty(text) && !text.equals("") && text != null;
    }

    public static void toastMessage(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void toastShortMessage(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String getToken() {
        String token = "";
        if (SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.USERTOKEN) != null
                && !SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.USERTOKEN).isEmpty()) {
            token = SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.USERTOKEN);
        }
        return token;
    }

    //Locate Booking TimerPicker BottomSheet
    public static void bottomSheetTimePickerInBooking(Context mContext, Activity activity, TextView tv, String title, String date) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));

        TimePicker simpleTimePicker24Hours = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        simpleTimePicker24Hours.setIs24HourView(true);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);

        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(mContext);
        continueTv.setText(appKeysPage.getContinue());
        backTv.setText(appKeysPage.getBack());

        if(title.equalsIgnoreCase("start"))
            titleTv.setText(appKeysPage.getStart());
        else
            titleTv.setText(appKeysPage.getEnd());
        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }
        //keText(mContext, "tv.ge"+tv.getText(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(mContext, "tv.ge"+tv.getText(), Toast.LENGTH_SHORT).show();
        String[] parts = tv.getText().toString().split(":");
        simpleTimePicker24Hours.setHour(Integer.parseInt(parts[0]));
        simpleTimePicker24Hours.setMinute(Integer.parseInt(parts[1]));

        int oldHour = Integer.parseInt(parts[0]);
        int oldMinutes = Integer.parseInt(parts[1]);
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556
        simpleTimePicker24Hours.setHour(Integer.parseInt(part1));
        simpleTimePicker24Hours.setMinute(Integer.parseInt(part2));


        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setData(simpleTimePicker24Hours,tv);

                String hour = null, minute = null;
                String getHHour = String.valueOf(simpleTimePicker24Hours.getHour());
                String getMMinute = String.valueOf(simpleTimePicker24Hours.getMinute());

                if (getHHour.length() == 1) {
                    hour = "0" + getHHour;
                } else {
                    hour = getHHour;
                }


                if (getMMinute.length() == 1) {
                    minute = "0" + getMMinute;
                } else {
                    minute = getMMinute;
                }

                System.out.println("GETDATATATATA" + hour + " " + minute);

                tv.setText(hour + ":" + minute);

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

    }

    public static String setData(TimePicker simpleTimePicker24Hours, TextView tv) {

        String hour = null, minute = null;
        String getHHour = String.valueOf(simpleTimePicker24Hours.getHour());
        String getMMinute = String.valueOf(simpleTimePicker24Hours.getMinute());

        if (getHHour.length() == 1) {
            hour = "0" + getHHour;
        } else {
            hour = getHHour;
        }


        if (getMMinute.length() == 1) {
            minute = "0" + getMMinute;
        } else {
            minute = getMMinute;
        }

        System.out.println("GETDATATATATA" + hour + " " + minute);

        tv.setText(hour + ":" + minute);

        return hour + ":" + minute;
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


    public static void bottomSheetTimePickerMeetingRoom(Context mContext, Activity activity, TextView st,
                                                        TextView et, String title, String date,
                                                        Boolean isrequested) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));
        TimePicker simpleTimePicker = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(true);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);
        titleTv.setText(title);
        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(mContext);
        continueTv.setText(appKeysPage.getContinue());
        backTv.setText(appKeysPage.getBack());
        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }

        String[] parts = st.getText().toString().split(":");
        simpleTimePicker.setHour(Integer.parseInt(parts[0]));
        simpleTimePicker.setMinute(Integer.parseInt(parts[1]));

        int oldHour = Integer.parseInt(parts[0]);
        int oldMinutes = Integer.parseInt(parts[1]);
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
                hour = simpleTimePicker.getHour();
                minutes = simpleTimePicker.getMinute();
//                Toast.makeText(mContext, "ih"+isrequested+title, Toast.LENGTH_SHORT).show();


                String time = hour + ":" + minutes;

                SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
                try {
                    float oldsttime= Float.parseFloat(""+oldHour+"."+oldMinutes);
                    float sttime= Float.parseFloat(""+hour+"."+minutes);
                    float ettime= Float.parseFloat(et.getText().toString().replace(":","."));

                    Date date = f24hours.parse(time);
                    SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
//                    cal.add(Calendar.MINUTE, 30);
//                    String endTime = Utils.setStartNearestFiveMinToMeeting(f24hours.format(cal.getTime()));
//                    String endTime = Utils.setStartNearestThirtyMinToMeeting(f24hours.format(cal.getTime()));
                    if(sttime>ettime || sttime<oldsttime){
                        cal.add(Calendar.MINUTE, 30);
                        String endTime = Utils.setStartNearestFiveMinToMeeting(f24hours.format(cal.getTime()));
                        et.setText("" + f24hours.format(f24hours.parse(endTime)));
//                        updateEndtTimeforMeeting(et,sttime,ettime);
                    }

//                            return String.valueOf(f12hours.format(date));
                    st.setText("" + f24hours.format(date));
                    System.out.println("ReceivedDate" + f12hours.format(date));
                    bottomSheetDialog.dismiss();

                } catch (ParseException e) {
                    Log.d("Utils", "onClick: Meeting Lofic"+e.getLocalizedMessage());
                    e.printStackTrace();
                }

            }
        });

        bottomSheetDialog.show();
    }

    private static void updateEndtTimeforMeeting(TextView et, float sttime, float ettime) {

    }

    //Bottom Sheet TimePicker
    public static void bottomSheetTimePicker24Hrs(Context mContext, Activity activity,
                                                  TextView tv, String title, String date, Boolean isrequested) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));
        TimePicker simpleTimePicker = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(true);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);
        titleTv.setText(title);
        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(mContext);
        continueTv.setText(appKeysPage.getContinue());
        backTv.setText(appKeysPage.getBack());
        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }

        String[] parts = tv.getText().toString().split(":");
        simpleTimePicker.setHour(Integer.parseInt(parts[0]));
        simpleTimePicker.setMinute(Integer.parseInt(parts[1]));

        int oldHour = Integer.parseInt(parts[0]);
        int oldMinutes = Integer.parseInt(parts[1]);
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
                hour = simpleTimePicker.getHour();
                minutes = simpleTimePicker.getMinute();
//                Toast.makeText(mContext, "ih"+isrequested+title, Toast.LENGTH_SHORT).show();
                if (title.equalsIgnoreCase("end time") && isrequested) {
                    if (hour > oldHour || (hour == oldHour && minutes > oldMinutes)) {
                        Toast.makeText(mContext, "For Request end time cant be more that approved hours", Toast.LENGTH_SHORT).show();
                        hour = oldHour;
                        minutes = oldMinutes;
                    }
                } else if (title.equalsIgnoreCase("start time") && isrequested) {
                    if (hour < oldHour || (hour == oldHour && minutes < oldMinutes)) {
                        Toast.makeText(mContext, "For Request start time cant be less that approved hours", Toast.LENGTH_SHORT).show();
                        hour = oldHour;
                        minutes = oldMinutes;
                    }
                }


                String time = hour + ":" + minutes;

                SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");

                try {
                    Date date = f24hours.parse(time);
                    SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
//                            return String.valueOf(f12hours.format(date));
                    tv.setText("" + f24hours.format(date));
                    System.out.println("ReceivedDate" + f12hours.format(date));
                    bottomSheetDialog.dismiss();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        bottomSheetDialog.show();
    }
    public static void bottomSheetTimePicker(Context mContext, Activity activity, TextView tv, String title, String date, Boolean isrequested) {
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
        //New...
        if (!(date.equalsIgnoreCase(""))) {
            String dateTime = Utils.dateWithDayString(date);
            if (dateTime.equalsIgnoreCase("")) {
                dateTv.setText(date);
            } else {
                dateTv.setText(dateTime);
            }
        } else {
            dateTv.setText(date);
        }

        String[] parts = tv.getText().toString().split(":");
        simpleTimePicker.setHour(Integer.parseInt(parts[0]));
        simpleTimePicker.setMinute(Integer.parseInt(parts[1]));

        int oldHour = Integer.parseInt(parts[0]);
        int oldMinutes = Integer.parseInt(parts[1]);
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
                hour = simpleTimePicker.getHour();
                minutes = simpleTimePicker.getMinute();
//                Toast.makeText(mContext, "ih"+isrequested+title, Toast.LENGTH_SHORT).show();
                if (title.equalsIgnoreCase("end time") && isrequested) {
                    if (hour > oldHour || (hour == oldHour && minutes > oldMinutes)) {
                        Toast.makeText(mContext, "For Request end time cant be more that approved hours", Toast.LENGTH_SHORT).show();
                        hour = oldHour;
                        minutes = oldMinutes;
                    }
                } else if (title.equalsIgnoreCase("start time") && isrequested) {
                    if (hour < oldHour || (hour == oldHour && minutes < oldMinutes)) {
                        Toast.makeText(mContext, "For Request start time cant be less that approved hours", Toast.LENGTH_SHORT).show();
                        hour = oldHour;
                        minutes = oldMinutes;
                    }
                }


                String time = hour + ":" + minutes;

                SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");

                try {
                    Date date = f24hours.parse(time);
                    SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
//                            return String.valueOf(f12hours.format(date));
                    tv.setText("" + f24hours.format(date));
                    System.out.println("ReceivedDate" + f12hours.format(date));
                    bottomSheetDialog.dismiss();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        bottomSheetDialog.show();
    }

    //BotomSheet DatePicker
    public static void bottomSheetDatePicker(Context mContext, Activity activity, String title, String date,
                                             TextView locateCheckInDate, TextView showLocateCheckInDate) {

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView = bottomSheetDatePicker.findViewById(R.id.datePicker);

        Calendar c = Calendar.getInstance();
        calendarView.setMinDate(c.getTimeInMillis() - 1000);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString = String.valueOf(year);

                int actualMonth = month + 1;
                String monthInStringFormat, dateInStringFormat;

                if (actualMonth >= 10) {
                    monthInStringFormat = String.valueOf(actualMonth);
                } else {
                    String monthInString = String.valueOf(actualMonth);
                    monthInStringFormat = "0" + monthInString;
                }

                //New...
                if (dayOfMonth >= 10) {
                    dateInStringFormat = String.valueOf(dayOfMonth);
                } else {
                    String monthInString = String.valueOf(dayOfMonth);
                    dateInStringFormat = "0" + dayOfMonth;
                }

                String dayInString = String.valueOf(dayOfMonth);
                String dateInString = yearInString + "-" + monthInStringFormat + "-" + dateInStringFormat;
                System.out.println("PickedDate" + dateInString);
                //locateCheckInDate.setText(dateInString+"T"+getCurrentTimeIn24HourFormat()+".000"+"Z");
                //locateCheckInDate.setText(dateInString+"T"+"00:00:00.000"+"Z");
                locateCheckInDate.setText("" + dateInString);

                showLocateCheckInDate.setText(showBottomSheetDate(dateInString));


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("ContinuPrintHere"+locateCheckInDate.getText());
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


    public long addHoursToTime(String time, int hours) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date myDate = parser.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.HOUR_OF_DAY, hours); // this will add two hours
        myDate = cal.getTime();
        return myDate.getTime();
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

    public static void showCustomAlertWithEditTextDialog(final Activity mContext, String aMessage, QuestionListEditListener listener) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_with_edit_text);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText(aMessage);
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView etDescription = dialog.findViewById(R.id.etDescription);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getText(etDescription.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public static void showCustomTokenExpiredDialog(final Activity mContext, String aMessage) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_validation);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText(aMessage);
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishAllActivity(mContext);
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
        for (int i = 0; i < 5; i++) {
            Chip chip = new Chip(activity);
            chip.setId(i);
            chip.setText("ABC " + i);
            chip.setChipBackgroundColorResource(R.color.figmaGrey);
            chip.setCloseIconVisible(false);
            chip.setTextColor(activity.getResources().getColor(R.color.white));
//            chip.setTextAppearance(R.style.ChipTextAppearance);
            chipGroup.addView(chip);
        }

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(activity, startTime, "");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(activity, endTime, "");
            }
        });
        bottomSheetDialog.show();
    }

    public static String convert24HrsTO12Hrs(String time) {
        time.replace(".", ":");
        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
        SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
        try {
            Date date = f24hours.parse(time);
//                            return String.valueOf(f12hours.format(date));
//            v.setText(""+f12hours.format(date));
            System.out.println("ReceivedDate Bala" + f12hours.format(date));
            return "" + f12hours.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }

    }

    public static String convert12HrsTO24Hrs(String time) {
        time.replace(".", ":");
        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
        SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
        try {
            Date date = f12hours.parse(time);
//                            return String.valueOf(f12hours.format(date));
//            v.setText(""+f12hours.format(date));
            System.out.println("ReceivedDate Bala" + f24hours.format(date));
            return "" + f24hours.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }

    }

    public static void popUpTimePicker(Activity activity, TextView v, String tilte) {
//        TextView startTime = v;
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hour = hourOfDay;
                        minutes = minute;
                        String time = hourOfDay + ":" + minute;

                        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");

                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat("hh:mm aa");
//                            return String.valueOf(f12hours.format(date));
                            v.setText("" + f12hours.format(date));
                            System.out.println("ReceivedDate" + f12hours.format(date));


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, 12, 0, false);


        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continue", timePickerDialog);
        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Back", timePickerDialog);

        //timePickerDialog.setContentView(R.layout.layout_sso);

        timePickerDialog.setTitle(HtmlCompat.fromHtml("Start <br>" + tilte, HtmlCompat.FROM_HTML_MODE_LEGACY));


        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hour, minutes);
        timePickerDialog.show();

    }

    public static String getDayFromDate(Date date) {
        DateFormat df = new SimpleDateFormat("EEE");
        return df.format(date);
    }

    public static String getDateFromDate(Date date) {
        DateFormat df = new SimpleDateFormat("d");
        return df.format(date);
    }

    public static String getDayAndDate(Date date) {
        DateFormat df = new SimpleDateFormat("EEE,d");
        return df.format(date);
    }

    public static String calendarDay10thMonthYearformat(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day=cal.get(Calendar.DATE);

        if(!((day>10) && (day<19))) {
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("EEE, d'st' MMMM, yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("EEE, d'nd' MMMM, yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("EEE, d'rd'  MMMM, yyyy").format(date);
                default:
                    return new SimpleDateFormat("EEE, d'th' MMMM, yyyy").format(date);

            }
        }
        return new SimpleDateFormat("EEE, d'th' MMMM, yyyy").format(date);

    }
    public static String calendarDay10thMonthformat(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day=cal.get(Calendar.DATE);

        if(!((day>10) && (day<19))){
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("EEE, d'st' MMMM").format(date);
                case 2:
                    return new SimpleDateFormat("EEE, d'nd' MMMM").format(date);
                case 3:
                    return new SimpleDateFormat("EEE, d'rd' MMMM").format(date);
                default:
                    return new SimpleDateFormat("EEE, d'th' MMMM").format(date);
            }
        }
        return new SimpleDateFormat("EEE, d'th' MMMM").format(date);

    }
    public static String dayDateMonthFormat(Date date) {
        DateFormat df = new SimpleDateFormat("EEE,d MMMM yyyy");
        return df.format(date);
    }

    public static String getISO8601format(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

        return df.format(date);
    }

    public static String getISO8601formatSss(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm.sss'Z'");

        return df.format(date);
    }

    public static String getYearMonthDateFormat(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String getCurrentDate() {
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            date = dtf.format(now);

        }
        return date;
    }

    public static String getCurrentTimeIn24HourFormat() {
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String str = sdf.format(new Date());
        System.out.println("sout" + str);
        return str;

    }

    public static String getCurrentTime() {
        String time = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            time = dtf.format(now);
        }
        return time;
    }

    public static boolean checkTokenExpiry() {

        boolean tokenExpiryStatus = false;
        String date = SessionHandler.getInstance().get(MyApp.getContext(), AppConstants.EXPIRY_TOKEN_DATE);

        if (getToken() != null && date != null) {
            //if (date.equals(getCurrentDate()))
            if (date.equals(getCurrentDate())) {

                tokenExpiryStatus = true;
            } else {
                SessionHandler.getInstance().remove(MyApp.getContext(), AppConstants.USERTOKEN);
                tokenExpiryStatus = false;
            }
        }


        return tokenExpiryStatus;

    }

    public static void saveTokenDateAndTimeInPreference(String token) {

        String expiryTokenDate = "", expiryTokenTime = "", expiryTokenTimeWithZ = "";
        String[] words = token.split("T");

        for (int i = 0; i < words.length; i++) {

            if (i == 0) {
                expiryTokenDate = words[i];
            }

            if (i == 1) {
                expiryTokenTimeWithZ = words[i];
            }

            String[] tokenTime = expiryTokenTimeWithZ.split("Z");
//            System.out.println("TokenSplitData"+tokenTime[0]);

        }

        System.out.println("FinalTokenDateAndTime" + expiryTokenDate + " " + expiryTokenTime);

        SessionHandler.getInstance().save(MyApp.getContext(), AppConstants.EXPIRY_TOKEN_DATE, expiryTokenDate);
        SessionHandler.getInstance().save(MyApp.getContext(), AppConstants.EXPIRY_TOKEN_TIME, expiryTokenTime);

    }


    public static String getDayAndDateFromDateFormat(Date date) {

        String dayInTextWithComma = "";
        String dayInText = "";
        String dayInNumber = "";

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);

        formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        strDate = formatter.format(date);
        System.out.println("Date Format with E, dd MMM yyyy HH:mm:ss z : " + strDate);

        String[] words = strDate.split(" ");

        for (int i = 0; i < words.length; i++) {

            if (i == 0) {
                dayInTextWithComma = words[i];
            }

            if (i == 1) {
                dayInNumber = words[i];
            }

        }

        String[] day = dayInTextWithComma.split(",");

        dayInText = day[0];

        return dayInText + " " + dayInNumber;

    }


    public static boolean compareTimeIfCheckInEnable(String startTime, String endTime) {
        startTime = startTime.replace(":", ".");
        endTime = endTime.replace(":", ".");

        System.out.println(startTime + " balas " + endTime);
        if (Double.parseDouble(startTime) >= Double.parseDouble(endTime))
            return true;
        else
            return false;
    }

    public static String splitDate(String dateWithTandZ) {
        String date = "";
        String[] words = dateWithTandZ.split("T");

        for (int i = 0; i < words.length; i++) {

            if (i == 0) {
                date = words[i];
            }

        }

        return date;

    }

    public static String splitTime(String dateWithTandZ) {

        Date date = null;
        String timeWithZ = "";
        String time = "";

        String[] words = dateWithTandZ.split("T");

        for (int i = 0; i < words.length; i++) {
            if (i == 1) {
                timeWithZ = words[i];
            }

            String[] tokenTime = timeWithZ.split("Z");
            time = tokenTime[0];
//            System.out.println("TokenSplitData"+tokenTime[0]);

        }

        String[] timeWithColon = time.split(":");

        String hour = "";
        String min = "";
        String hourMinFormet = "";
        for (int i = 0; i < timeWithColon.length; i++) {

            if (i == 0) {
                hour = timeWithColon[i];
            }
            if (i == 1) {
                min = timeWithColon[i];
            }
        }

        hourMinFormet = hour + ":" + min;
        return hourMinFormet;

    }

    public static void finishAllActivity(Context context) {
        Intent i = new Intent(context, SignInActivity.class);
        context.startActivity(i);
        //finishAllActivity(context);
    }

    public static String getCurrentDateInDateFormet() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("UtilDateFormat" + formatter.format(date));


        return formatter.format(date);

    }


    public static Date convertStringToDateFormet(String dateInString) {

        //String sDate1="31/12/1998";
        Date date1 = null;
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

            if (d1.compareTo(d) < 0) {// not expired
                return 3;
            } else if (d.compareTo(d1) == 0) {// both date are same
                if (d.getTime() < d1.getTime()) {// not expired
                    return 1;
                } else if (d.getTime() == d1.getTime()) {//expired
                    return 2;
                } else {//expired
                    return 3
                            ;
                }
            } else {//expired
                return 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 3;
        }

    }


    public static String removeTandZInDate(String date) {

        String result = date.replace("T", " ");
        String finalResult = result.replace("Z", "");

        return finalResult;

    }

    public static int compareTwoDatesandTime(String date1, String date2) {
        int dateCompare = -1;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date d1 = sdformat.parse(date1);
            Date d2 = sdformat.parse(date2);
            System.out.println("check compare String date bala" + d1.compareTo(d2));
            if (d1.compareTo(d2) < 0) {
                dateCompare = 1;
                System.out.println("CurrentTimeLessAddedTimeHigh");
            } else if (d1.compareTo(d2) > 0) {
                dateCompare = 2;
                System.out.println("CurrentTimeHihgAddedTimeLess");
            } else if (d1.compareTo(d2) == 0) {
                dateCompare = 0;
                System.out.println("CurrentTimeAndAddedTimeEqual");
            }/*else if(d1.compareTo(d2)<=0){
                System.out.println("CurrentTimeIsLessOrEqual");
                dateCompare=3;
            }*/


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateCompare;


    }
    public static int compareTwoDatesandTimeSSS(String date1, String date2) {
        int dateCompare = -1;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        try {
            Date d1 = sdformat.parse(date1);
            Date d2 = sdformat.parse(date2);
            System.out.println("check compare String date bala" + d1.compareTo(d2));
            if (d1.compareTo(d2) < 0) {
                dateCompare = 1;
                System.out.println("CurrentTimeLessAddedTimeHigh");
            } else if (d1.compareTo(d2) > 0) {
                dateCompare = 2;
                System.out.println("CurrentTimeHihgAddedTimeLess");
            } else if (d1.compareTo(d2) == 0) {
                dateCompare = 0;
                System.out.println("CurrentTimeAndAddedTimeEqual");
            }/*else if(d1.compareTo(d2)<=0){
                System.out.println("CurrentTimeIsLessOrEqual");
                dateCompare=3;
            }*/


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateCompare;


    }

    public static int compareTwoDates(String date1, String date2) {
        int dateCompare = -1;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = sdformat.parse(date1);
            Date d2 = sdformat.parse(date2);
            System.out.println("check compare String date bala" + d1.compareTo(d2));
            if (d1.compareTo(d2) < 0) {
                dateCompare = 1;
                System.out.println("CurrentTimeLessAddedTimeHigh");
            } else if (d1.compareTo(d2) > 0) {
                dateCompare = 2;
                System.out.println("CurrentTimeHihgAddedTimeLess");
            } else if (d1.compareTo(d2) == 0) {
                dateCompare = 0;
                System.out.println("CurrentTimeAndAddedTimeEqual");
            }/*else if(d1.compareTo(d2)<=0){
                System.out.println("CurrentTimeIsLessOrEqual");
                dateCompare=3;
            }*/


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateCompare;


    }

    public static String addMinuteWithCurrentTime(int id, int addMin) {
        String newTime = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date d = null;
        try {
            d = df.parse(Utils.getCurrentTimeIn24HourFormat());
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            if (id == 1) {
                cal.add(Calendar.MINUTE, addMin);
            } else if (id == 2) {
                cal.add(Calendar.HOUR, addMin);
            }
            newTime = df.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newTime;
    }


    public static String addingHoursToCurrentDate(int currentTimeZoneOffset) {
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

        return newTime;
    }

    public static String addHoursToDate(int currentTimeZoneOffset) {
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
        cal.add(Calendar.HOUR, currentTimeZoneOffset);
        String newTime = df.format(cal.getTime());

        return newTime;
    }

    public static String addingHoursToCurrentDateWithTZ(int currentTimeZoneOffset) {
        //Should add seconds

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
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

        return newTime;


    }

    public static String addingHoursToDate(String date, int currentTimeZoneOffset) {
        //Should add seconds

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND, currentTimeZoneOffset);
        String newTime = df.format(cal.getTime());

        return newTime;
    }


    public static String splitGetDate(String dateWithTime) {

        //Split key to get id and code
        String[] result = dateWithTime.split(" ");
        String dateAlone = result[0];

        return dateAlone;

    }


    public static int doCompareDateAlone(String strDate1, String strDate2) {

        int dateSelectedStatus = 0;
        Date date1 = null, date2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date1 = formatter.parse(strDate1);
            date2 = formatter.parse(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       /* if (date1.getDate() == date2.getDate()) {
            System.out.println("BothDateEqual");
            dateSelectedStatus = 1;
        } else if (date1.getDate() < date2.getDate()) {
            System.out.println("SelecctedDateIsHigh");
            dateSelectedStatus = 2;
        } else if (date1.getDate() > date2.getDate()) {
            System.out.println("SelecctedDateIsLow");
            dateSelectedStatus = 0;

        }*/


        if (date1.compareTo(date2) < 0) {
            System.out.println("SelecctedDateIsLow-yesterday");
            dateSelectedStatus = 2;
        } else if (date1.compareTo(date2) > 0) {
            System.out.println("SelecctedDateIsHigh-Tomorrow");
            dateSelectedStatus = 0;
        } else if (date1.compareTo(date2) == 0) {
            dateSelectedStatus = 1;
            System.out.println("BothDateEqual-Today");
        }


        return dateSelectedStatus;

    }

    public static int doDateCompareHere(String selectDate) {

        int dateSelectedStatus = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Date currrentDate = null, selectedDate = null;
        try {
            currrentDate = formatter.parse(formatter.format(date));
            String dateInString = selectDate;
            selectedDate = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

      /*  if (currrentDate.getDate() == selectedDate.getDate()) {
            System.out.println("BothDateEqual");
            dateSelectedStatus = 1;
        } else if (currrentDate.getDate() < selectedDate.getDate()) {
            System.out.println("SelecctedDateIsHigh");
            dateSelectedStatus = 2;
        } else if (currrentDate.getDate() > selectedDate.getDate()) {
            System.out.println("SelecctedDateIsLow");
            dateSelectedStatus = 0;

        }*/


        if (selectedDate.compareTo(currrentDate) < 0) {
            System.out.println("SelecctedDateIsLow-Yesterday");
            dateSelectedStatus = 0;
        } else if (selectedDate.compareTo(currrentDate) > 0) {
            System.out.println("SelecctedDateIsHigh-Tomorrow");
            dateSelectedStatus = 1;
        } else if (selectedDate.compareTo(currrentDate) == 0) {
            dateSelectedStatus = 2;
            System.out.println("BothDateEqual-Today");
        }



        return dateSelectedStatus;


    }


    public static int compareCurrentDateWithSelectedDate(String startDate) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date calDate, currrentDate = null;
        int dateComparsionResult = -1;
        try {
            calDate = sdformat.parse(startDate);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            currrentDate = formatter.parse(formatter.format(date));

            System.out.println("DateFormatInDate" + calDate + " " + currrentDate);
            System.out.println("GetDateAlsoeInDeskChecking" + calDate.getDate() + " " + currrentDate.getDate());

            if (calDate.compareTo(currrentDate) < 0) {
                System.out.println("D1IsLess");
                System.out.println("SelecctedDateIsLow-yesterday");
                dateComparsionResult = 1;
            } else if (calDate.compareTo(currrentDate) > 0) {
                System.out.println("D2IsLess");
                System.out.println("SelecctedDateIsHigh-Tomorrow");
            } else if (calDate.compareTo(currrentDate) == 0) {
                dateComparsionResult = 2;
                System.out.println("BothDateEqual-Today");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateComparsionResult;
    }


    //New...
    public static String dateWithDayString(String d) {
        String date = "";
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("E, dd'th' MMMM, yyyy");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static boolean checkCameraPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, AppConstants.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, AppConstants.MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA}, AppConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean checkExtPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(context, "teast", Toast.LENGTH_SHORT).show();
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //New...
    public static String getCurrentDateWithDay() {
        String date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd MMM");
            LocalDateTime now = LocalDateTime.now();
            //System.out.println(dtf.format(now));
            date = dtf.format(now);

        }
        return date;
    }

    public synchronized static void setLangInPref(LanguagePOJO myRes, Context mContext) {
        LanguagePOJO langData = myRes;
        if (langData != null) {
            try {
                if (langData.getGlobal() != null) {
                    String localeData = new Gson().toJson(langData.getGlobal());
                    SessionHandler.getInstance().save(mContext, AppConstants.GLOBAL_PAGE, localeData);
                }
            } catch (Exception e) {
            }
            try {
                if (langData.getWellBeing() != null) {
                    String localeData = new Gson().toJson(langData.getWellBeing());
                    SessionHandler.getInstance().save(mContext, AppConstants.WELLBEING_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getLogin() != null) {
                    String localeData = new Gson().toJson(langData.getLogin());
                    SessionHandler.getInstance().save(mContext, AppConstants.LOGIN_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getMe() != null) {
                    String localeData = new Gson().toJson(langData.getMe());
                    SessionHandler.getInstance().save(mContext, AppConstants.ME_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getBooking() != null) {
                    String localeData = new Gson().toJson(langData.getBooking());
                    SessionHandler.getInstance().save(mContext, AppConstants.BOOKING_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getCovidSelfCertification() != null) {
                    String localeData = new Gson().toJson(langData.getCovidSelfCertification());
                    SessionHandler.getInstance().save(mContext, AppConstants.COVIDSELFCERTIFICATION_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getPersonalHelp() != null) {
                    String localeData = new Gson().toJson(langData.getPersonalHelp());
                    SessionHandler.getInstance().save(mContext, AppConstants.PERSONAL_HELP_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getApprovals() != null) {
                    String localeData = new Gson().toJson(langData.getApprovals());
                    SessionHandler.getInstance().save(mContext, AppConstants.APPROVALS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getRequests() != null) {
                    String localeData = new Gson().toJson(langData.getRequests());
                    SessionHandler.getInstance().save(mContext, AppConstants.REQUESTS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getMeetingRooms() != null) {
                    String localeData = new Gson().toJson(langData.getMeetingRooms());
                    SessionHandler.getInstance().save(mContext, AppConstants.MEETING_ROOM_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getCarPark() != null) {
                    String localeData = new Gson().toJson(langData.getCarPark());
                    SessionHandler.getInstance().save(mContext, AppConstants.CARPARK_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getResetPassword() != null) {
                    String localeData = new Gson().toJson(langData.getResetPassword());
                    SessionHandler.getInstance().save(mContext, AppConstants.RESET_PASSWORD_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getAccountSettings() != null) {
                    String localeData = new Gson().toJson(langData.getAccountSettings());
                    SessionHandler.getInstance().save(mContext, AppConstants.ACCOUNTSETTINGS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getCommonTitles() != null) {
                    String localeData = new Gson().toJson(langData.getCommonTitles());
                    SessionHandler.getInstance().save(mContext, AppConstants.COMMON_TITLES_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getSearch() != null) {
                    String localeData = new Gson().toJson(langData.getSearch());
                    SessionHandler.getInstance().save(mContext, AppConstants.SEARCH_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getAppFeedback() != null) {
                    String localeData = new Gson().toJson(langData.getAppFeedback());
                    SessionHandler.getInstance().save(mContext, AppConstants.APP_FEEDBACK_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getActionOverLays() != null) {
                    String localeData = new Gson().toJson(langData.getActionOverLays());
                    SessionHandler.getInstance().save(mContext, AppConstants.ACTIONOVERLAYS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getSettings() != null) {
                    String localeData = new Gson().toJson(langData.getSettings());
                    SessionHandler.getInstance().save(mContext, AppConstants.SETTINGS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getWellBeing() != null) {
                    String localeData = new Gson().toJson(langData.getWellBeing());
                    SessionHandler.getInstance().save(mContext, AppConstants.ACTION_OVERLAYS_PAGE, localeData);
                }
            } catch (Exception e) {
            }

            try {
                if (langData.getAppKeys() != null) {
                    String localeData = new Gson().toJson(langData.getAppKeys());
                    SessionHandler.getInstance().save(mContext, AppConstants.APPKEYS_PAGE, localeData);
                }
            } catch (Exception e) {
                System.out.println("lang check util" + e.getMessage());
            }


            try {
                if (langData.getLogin() != null) {
                    String localeData = new Gson().toJson(langData.getLogin());
                    SessionHandler.getInstance().save(mContext, AppConstants.LOGIN_PAGE, localeData);
                }
            } catch (Exception e) {
            }
            try {
                if (langData.getBooking() != null) {
                    String localeData = new Gson().toJson(langData.getBooking());
                    SessionHandler.getInstance().save(mContext, AppConstants.BOOKING_PAGE, localeData);
                }
            } catch (Exception e) {
            }


        }
    }

    public static LanguagePOJO.WellBeing getWellBeingScreenData(Context mContext) {
        LanguagePOJO.WellBeing wellBeingPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.WELLBEING_PAGE), LanguagePOJO.WellBeing.class);
        return wellBeingPage;
    }


    public static LanguagePOJO.Global getGlobalScreenData(Context mContext) {
        LanguagePOJO.Global global = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.GLOBAL_PAGE), LanguagePOJO.Global.class);
        return global;
    }


    public static LanguagePOJO.Login getLoginScreenData(Context mContext) {
        LanguagePOJO.Login loginPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.LOGIN_PAGE), LanguagePOJO.Login.class);
        return loginPage;
    }

    public static LanguagePOJO.Me getMeScreenData(Context mContext) {
        LanguagePOJO.Me mePage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.ME_PAGE), LanguagePOJO.Me.class);
        return mePage;
    }

    public static LanguagePOJO.Booking getBookingPageScreenData(Context mContext) {
        LanguagePOJO.Booking bookingPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.BOOKING_PAGE), LanguagePOJO.Booking.class);
        return bookingPage;
    }

    public static LanguagePOJO.CovidSelfCertification getCovidSelfCertificationPageScreenData(Context mContext) {
        LanguagePOJO.CovidSelfCertification covidWellbeingPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.COVIDSELFCERTIFICATION_PAGE), LanguagePOJO.CovidSelfCertification.class);
        return covidWellbeingPage;
    }

    public static LanguagePOJO.PersonalHelp getPersonalPagesScreenData(Context mContext) {
        LanguagePOJO.PersonalHelp personalHelpPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.PERSONAL_HELP_PAGE), LanguagePOJO.PersonalHelp.class);
        return personalHelpPage;
    }

    public static LanguagePOJO.Approvals getApprovalsPageScreenData(Context mContext) {
        LanguagePOJO.Approvals approvalsPagedata = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.APPROVALS_PAGE), LanguagePOJO.Approvals.class);
        return approvalsPagedata;
    }

    public static LanguagePOJO.Requests getrequestsPageScreenData(Context mContext) {
        LanguagePOJO.Requests requestsPagedata = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.REQUESTS_PAGE), LanguagePOJO.Requests.class);
        return requestsPagedata;
    }

    public static LanguagePOJO.MeetingRooms getMeetingRoomsPageScreenData(Context mContext) {
        LanguagePOJO.MeetingRooms MeetingRoomsPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.MEETING_ROOM_PAGE), LanguagePOJO.MeetingRooms.class);
        return MeetingRoomsPage;
    }

    public static LanguagePOJO.CarPark getCarParkPageScreenData(Context mContext) {
        LanguagePOJO.CarPark CarParkPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.CARPARK_PAGE), LanguagePOJO.CarPark.class);
        return CarParkPage;
    }

    public static LanguagePOJO.ResetPassword getResetPasswordPageScreencreenData(Context mContext) {
        LanguagePOJO.ResetPassword ResetPasswocrdPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.RESET_PASSWORD_PAGE), LanguagePOJO.ResetPassword.class);
        return ResetPasswocrdPage;
    }

    public static LanguagePOJO.AccountSettings getAccountSettingsPageScreenData(Context mContext) {
        LanguagePOJO.AccountSettings AccountSettingsPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.ACCOUNTSETTINGS_PAGE), LanguagePOJO.AccountSettings.class);
        return AccountSettingsPage;
    }

    public static LanguagePOJO.Search getSearchPageScreenData(Context mContext) {
        LanguagePOJO.Search SearchPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.SEARCH_PAGE), LanguagePOJO.Search.class);
        return SearchPage;
    }

    public static LanguagePOJO.AppFeedback getAppFeedbackPageScreenData(Context mContext) {
        LanguagePOJO.AppFeedback wellBeingPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.APP_FEEDBACK_PAGE), LanguagePOJO.AppFeedback.class);
        return wellBeingPage;
    }

    public static LanguagePOJO.ActionOverLays getActionOverLaysPageScreenData(Context mContext) {
        LanguagePOJO.ActionOverLays wellBeingPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.ACTION_OVERLAYS_PAGE), LanguagePOJO.ActionOverLays.class);
        return wellBeingPage;
    }

    public static LanguagePOJO.Settings getSettingsPageScreenData(Context mContext) {
        LanguagePOJO.Settings SettingsPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.SETTINGS_PAGE), LanguagePOJO.Settings.class);
        return SettingsPage;
    }

    public static LanguagePOJO.AppKeys getAppKeysPageScreenData(Context mContext) {
        LanguagePOJO.AppKeys AppKeysPage = new Gson().fromJson(SessionHandler.getInstance().get(mContext, AppConstants.APPKEYS_PAGE), LanguagePOJO.AppKeys.class);
        //System.out.println("lang check util" + AppKeysPage);
        return AppKeysPage;
    }


    public static UserDetailsResponse getLoginData(Context context) {
        UserDetailsResponse profileData = null; //= new UserDetailsResponse();
        Gson gson = new Gson();
        String json = SessionHandler.getInstance().get(context, AppConstants.LOGIN_RESPONSE);
        if (json != null) {
            profileData = gson.fromJson(json, UserDetailsResponse.class);
        }

        return profileData;
    }

    public static String showCalendarWithFullMonth(String d) {
        String date = d;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("E, dd'th' MMMM");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String showCalendarDate(String d) {
        String date = d;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("E, dd'th' MMM");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String showBottomSheetDate(String d) {
        String date = d;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("dd MMM");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String showBottomSheetDateTime(String d) {
        String date = d;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("dd'th' MMM");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }



    public static String showBottomSheetDateTimeAMPM(String d) {
        String date = d;
        SimpleDateFormat spf = new SimpleDateFormat("HH:mm");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("hh:mm aa");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static void bottomSheetDatePickerWorkSpaceSurveyAsement(Context mContext, Activity activity, String title, String date, TextView showLocateCheckInDate) {

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView = bottomSheetDatePicker.findViewById(R.id.datePicker);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString = String.valueOf(year);

                int actualMonth = month + 1;
                String monthInStringFormat;

                if (actualMonth >= 10) {
                    monthInStringFormat = String.valueOf(actualMonth);
                } else {
                    String monthInString = String.valueOf(actualMonth);
                    monthInStringFormat = "0" + monthInString;
                }

                String dayInString = String.valueOf(dayOfMonth);
                String dateInString = yearInString + "-" + monthInStringFormat + "-" + dayInString;
                System.out.println("PickedDate" + dateInString);
                //locateCheckInDate.setText(dateInString+"T"+getCurrentTimeIn24HourFormat()+".000"+"Z");
                //locateCheckInDate.setText(dateInString+"T"+"00:00:00.000"+"Z");
//                locateCheckInDate.setText(""+dateInString);

                showLocateCheckInDate.setText("" + dateInString);


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("ContinuPrintHere" + showLocateCheckInDate.getText());
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

    //New...
    public static BitmapFactory.Options getImageSize(Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        return options;
    }

    //New...
    public static String MonthAndDateString(String d) {
        String date = "";
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("MMM, dd");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public static String MonthAndDateAndTwithZString(String d) {
        String date = "";

        System.out.println("2000-01-01T18:00:00Z");
        System.out.println("\"2000-01-01T18:00:00.000Z\",");
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date newDate = null;
        try {
            newDate = spf.parse(d);
            spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            if (newDate != null) {
                date = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public static Date getCurrentWeekEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date weekendDate = null;
        try {
            weekendDate = sdf.parse(sdf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weekendDate;
    }

    public static Date getWeekEndDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date weekendDate = null;
        try {
            weekendDate = sdf.parse(sdf.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weekendDate;
    }

    public static String getDateFormatToSetInRepeat(Date date) {

        System.out.println("DateInStringFormat " + String.valueOf(date));
        String strDate = String.valueOf(date);
        //Received Date Format
        //Sun Sep 18 00:00:00 GMT+05:30 2022
        String[] word = strDate.split(" ");

        String dayTxt = word[0];
        String month = word[1];
        String day = word[2];

        String dateFormats = dayTxt + " " + day + " " + month;

        return dateFormats;


    }

    public static int getDifferenceBetweenTwoDates(String selectedDateInLocate) {

        //selected Date In Locate
        //String[] words=selectedDateInLocate.split("-");
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        String str = formatterMonth.format(date);
        int currentDate_Month = Integer.parseInt(str);

        SimpleDateFormat formatterDate = new SimpleDateFormat("dd");
        String strDate = formatterDate.format(date);
        int currentDate_Date = Integer.parseInt(strDate);

        int selectedYear = c.getWeekYear();
        int selectedMonth = currentDate_Month;
        int selectedDay = currentDate_Date;

        //WeekEnd Date
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date weekendDate = null;
        try {
            weekendDate = sdf.parse(sdf.format(c.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Coming WeekendDate
        int Month = weekendDate.getMonth() + 1;
        LocalDate weekEndDate = LocalDate.of(c.getWeekYear(), Month, weekendDate.getDate());

        //Selected Date
        LocalDate currentSelectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);

        //Find Difference between 2 date
        Period difference = Period.between(currentSelectedDate, weekEndDate);


        return difference.getDays();

    }

    public static List<String> getCurrentWeekDateList(String selectedDate, int enableCurrentWeek) {
        List<String> dateList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(selectedDate));
            //Add Current Date
            String dateAfter1 = sdf.format(cal.getTime());
            dateList.add(dateAfter1);

            for (int i = 0; i < enableCurrentWeek; i++) {
                //It will add next day until end of the week
                cal.add(Calendar.DAY_OF_MONTH, 1);
                String dateAfter = sdf.format(cal.getTime());
                dateList.add(dateAfter);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dateList;

    }





  /*  public static void getCurrentWeekEndDate(){
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -1);
        System.out.println("CurrentWeekEndDate " + calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.DATE)+" "+calendar.get(Calendar.MONTH)+1);
    }*/


    public static boolean checkEditTime(String originalStartTime, String originalEndTime, String selectedTime) {

        //New...
        boolean valid;

        //Start Time
        //all times are from java.util.Date
        Date inTime = null;
        Calendar calendar1 = Calendar.getInstance();
        try {
            inTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(originalStartTime);
            if (inTime != null) {
                calendar1.setTime(inTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Current Time
        Date checkTime = null;
        Calendar calendar3 = Calendar.getInstance();
        try {
            checkTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(selectedTime);
            if (checkTime != null) {
                calendar3.setTime(checkTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //End Time
        Date finTime = null;
        Calendar calendar2 = Calendar.getInstance();
        try {
            finTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(originalEndTime);
            if (finTime != null) {
                calendar2.setTime(finTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (originalEndTime.compareTo(originalStartTime) < 0) {
            calendar2.add(Calendar.DATE, 1);
            calendar3.add(Calendar.DATE, 1);
        }

        try {

            Date actualTime = calendar3.getTime();

            if ((actualTime.after(calendar1.getTime()) ||
                    actualTime.compareTo(calendar1.getTime()) == 0) &&
                    actualTime.before(calendar2.getTime())) {

                valid = true;
                return valid;
            } else {
                valid = false;
                return valid;
            }
        } catch (Exception e) {
            valid = false;
            return valid;
        }



        /*
         String[] orgSTime = originalStartTime.split(":");
         String[] orgEndTime = originalEndTime.split(":");
         String[] sTime = selectedTime.split(":");

        if (orgSTime.length>0 && orgEndTime.length>0 && sTime.length>0){

            int oStartHr = Integer.parseInt(orgSTime[0]);
            int oStartMin = Integer.parseInt(orgSTime[1]);

            int oEndHr = Integer.parseInt(orgEndTime[0]);
            int oEndMin = Integer.parseInt(orgEndTime[1]);

            int sHr = Integer.parseInt(sTime[0]);
            int sMin = Integer.parseInt(sTime[1]);

            if (oStartHr == sHr || oEndHr == sHr) {

                if (oStartMin>=sMin || oEndMin>=sMin){
                    return true;
                }else {
                    return false;
                }

            }else {
                return false;
            }

        }else {
            return false;
        }*/

    }

    public static boolean compareStartEndTime(String originalStartTime, String originalEndTime) {

        //New...
        boolean valid;

        Date inTime = null;
        Calendar calendar1 = Calendar.getInstance();
        try {
            inTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(originalStartTime);
            if (inTime != null) {
                calendar1.setTime(inTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date checkTime = null;
        Calendar calendar3 = Calendar.getInstance();
        try {
            checkTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(originalEndTime);
            if (checkTime != null) {
                calendar3.setTime(checkTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*if (originalEndTime.compareTo(originalStartTime) < 0)
        {
            //calendar2.add(Calendar.DATE, 1);
            calendar3.add(Calendar.DATE, 1);
        }*/

        try {

            Date endTime = calendar3.getTime();

            if ((endTime.after(calendar1.getTime()))) {

                valid = true;
                return valid;
            } else {
                valid = false;
                return valid;
            }
        } catch (Exception e) {
            valid = false;
            return valid;
        }

    }

    public static boolean checkSelTimeWithCurrentTime(boolean isCurrentDate, String selTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat cTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();
        Date date = Calendar.getInstance().getTime();

        String formattedDate = sdf.format(c);
        String cTime = cTimeFormat.format(date); //like end time...

        if (isCurrentDate) {

            //return compareStartEndTime(cTime,selTime);

            Date inTime = null;
            Calendar calendar1 = Calendar.getInstance();
            try {
                inTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(cTime);
                if (inTime != null) {
                    calendar1.setTime(inTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date outTime = null;
            Calendar calendar2 = Calendar.getInstance();
            try {
                outTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(selTime);
                if (outTime != null) {
                    calendar2.setTime(outTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                if ((calendar2.getTime().after(calendar1.getTime()) ||
                        calendar2.getTime().compareTo(calendar1.getTime()) == 0)) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

        } else {
            return true;
        }

    }

    public static boolean checkIsCurrentDate(String sDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();
        String formattedDate = sdf.format(c);

        if (sDate.equals(formattedDate)) {
            return true;
        } else {
            return false;
        }

    }

    public static String currentTimeWithExtraMins(int minutes) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = tFormat.format(currentTime);

        Date d = null;
        String newTime = "";

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.MINUTE, minutes);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }

    public static String setStartNearestFiveMinToMeeting(String cTime) {
        String time = Utils.getCurrentDate() + "T" + cTime + ":00.000";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime ldt = LocalDateTime.parse(time).truncatedTo(ChronoUnit.MINUTES);
        int minute = ldt.getMinute();
        int remainder = minute % 15;
        if (remainder != 0) {
            ldt = ldt.withMinute(minute - remainder);
        }

        return "" + Utils.splitTime(ldt.format(dtf));
    }

    public static String setStartNearestThirtyMinToMeeting(String cTime) {
//        System.out.println("cTime bala"+ cTime);

        String fTime = "";
        String[] orgSTime = cTime.split(":");

        if (orgSTime.length > 0) {

            int min = Integer.parseInt(orgSTime[1]);

            if (min >= 0 && min <= 15) {

                fTime = cTime.replace(orgSTime[1], "45");

            } else if (min > 15 && min <= 30) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "00"));

            } else if (min > 30 && min <= 45) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "15"));

            } else if (min > 45 && min <= 59) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "30"));

            }

        } else {
            fTime = cTime;
        }

        return fTime;
    }
    public static String setStartNearestThirtyMinToMeetingElango(String cTime) {
//        System.out.println("cTime bala"+ cTime);

        String fTime = "";
        String[] orgSTime = cTime.split(":");

        if (orgSTime.length > 0) {

            int min = Integer.parseInt(orgSTime[1]);

            if (min >= 0 && min < 15) {

                fTime = cTime.replace(orgSTime[1], "30");

            } else if (min >= 15 && min < 30) {

                fTime = cTime.replace(orgSTime[1], "45");
//                fTime = roundOffHour(cTime.replace(orgSTime[1], "45"));

            } else if (min >= 30 && min < 45) {

                fTime = cTime.replace(orgSTime[1], "00");
//                fTime = roundOffHour(cTime.replace(orgSTime[1], "00"));

            } else if (min >= 45 && min <= 59) {

                fTime = cTime.replace(orgSTime[1], "15");
//                fTime = roundOffHour(cTime.replace(orgSTime[1], "15"));

            }

        } else {
            fTime = cTime;
        }

        return fTime;
    }


    public static String setMeetingRoundOfValue8thTime(String cTime) {

        String fTime = "";
        String[] orgSTime = cTime.split(":");

        if (orgSTime.length > 0) {

            int min = Integer.parseInt(orgSTime[1]);

            if (min >= 0 && min <= 14) {

                fTime = cTime.replace(orgSTime[1], "30");

            } else if (min > 14 && min <= 29) {

                fTime = cTime.replace(orgSTime[1], "45");

            } else if (min > 29 && min <= 44) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "00"));

            } else if (min > 44 && min <= 59) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "15"));

            }

        } else {
            fTime = cTime;
        }

        return fTime;
    }


    public static String setNearestThirtyMinToMeeting(String cTime) {

        String fTime = "";
        String[] orgSTime = cTime.split(":");

        if (orgSTime.length > 0) {

            int min = Integer.parseInt(orgSTime[1]);

            if (min >= 0 && min <= 10) {

                fTime = cTime.replace(orgSTime[1], "30");

            } else if (min > 10 && min <= 29) {

                fTime = cTime.replace(orgSTime[1], "45");

            } else if (min > 29 && min <= 44) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "00"));

            } else if (min > 44 && min <= 59) {

                fTime = roundOffHour(cTime.replace(orgSTime[1], "15"));

            }

        } else {
            fTime = cTime;
        }

        return fTime;
    }

    public static String roundOffHour(String selectedTime) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Date currentTime = Calendar.getInstance().getTime();
        String time = selectedTime; //tFormat.format(currentTime);

        Date d = null;
        String newTime = "";

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.HOUR, 1);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }
    public static String roundOffHour30(String selectedTime) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Date currentTime = Calendar.getInstance().getTime();
        String time = selectedTime; //tFormat.format(currentTime);

        Date d = null;
        String newTime = "";

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.MINUTE, 30);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }

    public static String selectedTimeWithExtraMins(String selTime, int minutes) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Date currentTime = Calendar.getInstance().getTime();
        String time = selTime; //tFormat.format(currentTime);

        Date d = null;
        String newTime = "";

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.MINUTE, minutes);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }

    public static String addHoursToSelectedTime(String selTime, int hr) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Date currentTime = Calendar.getInstance().getTime();
        String time = selTime; //tFormat.format(currentTime);

        Date d = null;
        String newTime = selTime;

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.HOUR, hr);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }

    public static String addHoursToSelectedTimeWithMinutes(String selTime, int minutes) {

        SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        //Date currentTime = Calendar.getInstance().getTime();
        String time = selTime; //tFormat.format(currentTime);

        Date d = null;
        String newTime = selTime;

        try {
            d = tFormat.parse(time);
            Calendar cal = Calendar.getInstance();
            if (d != null) {

                cal.setTime(d);
                cal.add(Calendar.MINUTE, minutes);

                newTime = tFormat.format(cal.getTime());

                return newTime;

            } else {
                return newTime;
            }
        } catch (ParseException e) {
            return newTime;
        }

    }

    public static String selectDateWithCurrentTimeTZFormat(String d) {

        //Current Time...
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        String dateWithTime = "", currentDateTime = d + " " + currentTime;

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(currentDateTime);
            spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            if (newDate != null) {
                dateWithTime = spf.format(newDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateWithTime;
    }


    public static void openMail(Activity activity, String toAddress) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + toAddress);
        intent.setData(data);
        activity.startActivity(intent);
    }

    public static void openDial(Activity activity, String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        activity.startActivity(intent);
    }

    public static boolean isAdminOrNot(Context context) {

        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)){
            return true;
        }else {
            return false;
        }

    }

    public static void setSPannableStringForParticipants(TextView textView,String s,int start,int end){
        //Set Font Size
        //String s= "Internal participants optional";
        try {
            SpannableString ss1=  new SpannableString(s);
            ss1.setSpan(new RelativeSizeSpan(1.1f), 0,start, 0);
            ss1.setSpan(new RelativeSizeSpan(0.8f), start,end, 0);// set size
            //ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
            textView.setHint(ss1);
        }catch (Exception e){
            e.printStackTrace();
            textView.setHint(s);
        }

    }

    public static void openKeyBoard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService
                (Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


}
