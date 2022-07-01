package dream.guys.hotdeskandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import dream.guys.hotdeskandroid.R;

public class ProgressDialog {

    public static Dialog showProgressBar(Context context) {

        Dialog csprogress;
        csprogress = new Dialog(context, R.style.MyAlertDialogStyle);
        csprogress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        csprogress.setCancelable(false);
        csprogress.setContentView(R.layout.layout_progressbar);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();
        return csprogress;
    }

    public static void dismisProgressBar(Context context,Dialog dialog) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

}
