package dream.guys.hotdeskandroid.utils;

import android.app.Application;
import android.content.Context;

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

    }

}