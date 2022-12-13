package dream.guys.hotdeskandroid.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import dream.guys.hotdeskandroid.R;

public class ProgressDialog {

    public static Dialog showProgressBar(Context context) {

        Dialog csprogress;
        csprogress = new Dialog(context, R.style.MyAlertDialogStyle);
        csprogress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        csprogress.setCancelable(true);
        csprogress.setContentView(R.layout.layout_progressbar);
        csprogress.setCanceledOnTouchOutside(true);
        csprogress.show();
        return csprogress;
    }

    public static void dismisProgressBar(Context context,Dialog dialog) {

        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }

    }
    public static void dismissProgressBar(Dialog dialog) {

        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }

    }

    public static void touchLock(Context context, Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void clearTouchLock(Context context, Activity activity){
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
