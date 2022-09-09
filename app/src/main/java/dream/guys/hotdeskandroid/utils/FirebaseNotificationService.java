package dream.guys.hotdeskandroid.utils;

import static android.app.Notification.DEFAULT_ALL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseNotificationService extends FirebaseMessagingService {

    NotificationManager notificationManager;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        System.out.println("Firebase NewTokenReceived"+token);

        SessionHandler.getInstance().save(getApplicationContext(),AppConstants.SAVETOKEN,token);

        saveTokenInserver(token);

    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getNotification() != null) {

            String title = message.getNotification().getTitle();
            String messageBody = message.getNotification().getBody();

            showSmallNotification(title, messageBody);
        }
    }

    private void showSmallNotification(String title, String messageBody) {
        int notificationID = 0;
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_01");
        int icon = R.drawable.brickendon_logo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channel_01",
                    getApplicationContext().getResources().getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("channel_01");
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            //notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification;
        notification = mBuilder.setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title != null ? title.isEmpty() ?
                        getApplicationContext().getResources().getString(R.string.app_name) :
                        title :
                        getApplicationContext().getResources().getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setWhen(System.currentTimeMillis())
                .setDefaults(DEFAULT_ALL)
                .setShowWhen(true)
                .setSmallIcon(R.drawable.brickendon_logo)
                .setChannelId("channel_01")
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                .setContentText(messageBody)
                .build();

        notificationManager.notify(notificationID, notification);


    }


    private void saveTokenInserver(String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.saveFirebaseToken();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

}


