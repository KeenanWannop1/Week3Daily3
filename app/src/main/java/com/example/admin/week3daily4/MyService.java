package com.example.admin.week3daily4;

import android.annotation.SuppressLint;
import android.app.MediaRouteButton;
import android.app.Notification;
import android.app.Notification.MediaStyle;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.example.admin.week3daily4.App.CHANNEL_ID;

@SuppressLint("NewApi")
public class MyService extends Service{
    MediaPlayer player;
    MediaSession mediaSession;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        mediaSession = new MediaSession(getApplicationContext(), "TAG");
        mediaSession.setActive(true);
        mediaSession.setCallback(null);
        MediaSession.Token token = mediaSession.getSessionToken();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,
                notificationIntent,0);
          Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Music Service")
                    .setContentText(input)
                    .setSmallIcon(R.drawable.ic_android)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setContentIntent(pendingIntent)
                    .build();
        }


        player.start();
        player.setVolume(100,100);
        player.setLooping(true);
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this,R.raw.foolintherain);
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }
}
