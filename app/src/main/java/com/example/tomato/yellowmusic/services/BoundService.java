package com.example.tomato.yellowmusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class BoundService extends Service {

    private IBinder binder;
    MediaPlayer mediaPlayer;

    // phương thức khởi tạo
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Đã gọi onCreate()");

        binder = new MyBinder(); // do MyBinder được extends Binder
    }

    // Bắt đầu một Service
    @Override
    public IBinder onBind(Intent intent) {
        String path = intent.getExtras().getString("play");

        mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return binder;
    }

    // Kết thúc một Service
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Đã gọi onBind()");

        mediaPlayer.stop();
        return super.onUnbind(intent);
    }
    public void onPause(){
        mediaPlayer.pause();
    }

    public void onPlayContinue(){
        mediaPlayer.start();
    }

    public class MyBinder extends Binder {

        // phương thức này trả về đối tượng MyService
        public BoundService getService() {
            return BoundService.this;
        }
    }

}
