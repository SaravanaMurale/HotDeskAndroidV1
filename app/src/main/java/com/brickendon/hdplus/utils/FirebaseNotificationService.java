package com.brickendon.hdplus.utils;

import static android.app.Notification.DEFAULT_ALL;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.request.TokenRequest;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseNotificationService extends FirebaseMessagingService {

    NotificationManager notificationManager;

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        //System.out.println("NewTokenReceived "+token);

        if(token!=null) {

            SessionHandler.getInstance().save(getApplicationContext(), AppConstants.SAVETOKEN, token);
            String tokenInSharedPreference=SessionHandler.getInstance().get(getApplicationContext(),AppConstants.SAVETOKEN);
            System.out.println("SharedPreferenceToken "+tokenInSharedPreference);

            saveTokenInserver(token);
        }
    }




    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        if (message.getNotification() != null) {

            String title = message.getNotification().getTitle();
            String messageBody = message.getNotification().getBody();

            System.out.println("NotificationMessageReceived "+title+" "+messageBody);

            showSmallNotification(title, messageBody);
        }
    }

    private void showSmallNotification(String title, String messageBody) {
        int notificationID = 0;
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new
                NotificationCompat.Builder(getApplicationContext(), "channel_01");
        int icon = R.drawable.notification_icon;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channel_01",
                    getApplicationContext().getResources().getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("channel_01");
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
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
                .setSmallIcon(R.drawable.notification_icon)
                .setColor(ContextCompat.getColor(getApplicationContext(),R.color.white))
                .setChannelId("channel_01")
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                .setContentText(messageBody)
                .build();

        notificationManager.notify(notificationID, notification);


    }


    public static void saveTokenInserver(String token) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        TokenRequest tokenRequest=new TokenRequest();
        tokenRequest.setToken(token);
        //System.out.println("SendingToken "+token);
        Call<BaseResponse> call = apiService.saveFirebaseToken(tokenRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                System.out.println("TokenSentServerResponse"+response.body());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

}


