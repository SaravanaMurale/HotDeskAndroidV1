package dream.guys.hotdeskandroid.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import dream.guys.hotdeskandroid.ui.login.SignInActivity;

public class MyApp extends Application {

    private static MyApp instance;

    public static Context getContext() {
        //Singleton instance
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        setNightMode(this,
                SessionHandler.getInstance()
                        .getBoolean(this, AppConstants.DARK_MODE_CHECK));

    }
    public static void setNightMode(Context target , boolean state){
        int currentNightMode = getContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        System.out.println("nigt bala"+currentNightMode+" "+Configuration.UI_MODE_NIGHT_YES+" state = "+state);
        if (state || currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            SessionHandler.getInstance().saveBoolean(target, AppConstants.DARK_MODE_CHECK,true);
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
}
        /*UiModeManager uiManager = (UiModeManager) target.getSystemService(Context.UI_MODE_SERVICE);
        if (Build.VERSION.SDK_INT <= 22) {
            uiManager.enableCarMode(0);
        }

        if (state) {
            Toast.makeText(target, "Dark", Toast.LENGTH_SHORT).show();
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else {
            uiManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }*/
    }

}