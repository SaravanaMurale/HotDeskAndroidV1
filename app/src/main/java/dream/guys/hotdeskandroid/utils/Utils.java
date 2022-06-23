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
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.example.DummyActivity;

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

    public static void toastMessage(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
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


}
