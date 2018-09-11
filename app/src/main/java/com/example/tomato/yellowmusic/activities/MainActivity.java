package com.example.tomato.yellowmusic.activities;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tomato.yellowmusic.Adapter.MainAdapter;
import com.example.tomato.yellowmusic.R;
import com.example.tomato.yellowmusic.constants.Constants;
import com.example.tomato.yellowmusic.models.Song;
import com.example.tomato.yellowmusic.services.BoundService;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    public static MainActivity instance;

    Intent intent;          // intent gọi boundService phát nhạc, tắt nhạc
    Boolean startPlaying = true;
    private BoundService boundService;
    private boolean isBound = false;
    private ServiceConnection connection;

    RelativeLayout rlPlayingBar;
    TextView tvSongtitlePlayingBar, tvArtitsPlayingBar;
    ImageView ivPausePlayingBar;
    ArrayList<Song> arrSong;
    String path;
    ViewPager vpMain;
    TabLayout tabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        init();
        initEvent();
        instance = this;
        initBoundService();

    }

    private void initBoundService() {
        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BoundService.MyBinder binder = (BoundService.MyBinder) service;
                boundService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };
    }

    private void initEvent() {
        rlPlayingBar.setOnClickListener(this);
        ivPausePlayingBar.setOnClickListener(this);
    }

    private void init() {
        FragmentManager manager = getSupportFragmentManager();
        MainAdapter adapter = new MainAdapter(manager);
        arrSong = new ArrayList<>();
        arrSong = getListSong();
        vpMain.setAdapter(adapter);
        tabMain.setupWithViewPager(vpMain);
        vpMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
        tabMain.setTabsFromPagerAdapter(adapter);
    }

    private void initView() {
        vpMain = findViewById(R.id.vp_main);
        tabMain = findViewById(R.id.tab_main);

        rlPlayingBar = findViewById(R.id.rl_playing_bar);
        tvSongtitlePlayingBar = findViewById(R.id.tv_song_title_playing_bar);
        tvArtitsPlayingBar = findViewById(R.id.tv_artist_playing_bar);
        ivPausePlayingBar = findViewById(R.id.iv_pause_playing_bar);
    }


    public ArrayList<Song> getListSong() {
        ArrayList<Song> lstSong = new ArrayList<>();


        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID}
                , null, null, MediaStore.Audio.Media.TITLE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String songId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                int albumID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String albumPath = getCoverArtPath(albumID);
                Song item = new Song(songId, title, album, artist, albumPath, duration, path);

                lstSong.add(item);

            } while (cursor.moveToNext());
        }
        return lstSong;
    }

    public String getCoverArtPath(long albumId) {
        Cursor albumCursor = getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    public void setPlayingBar(int position) {
        tvSongtitlePlayingBar.setText(arrSong.get(position).getTitle());
        tvArtitsPlayingBar.setText(arrSong.get(position).getArtist());

        path = arrSong.get(position).getPath();

        playMusic(path);
    }

    public void playMusic(final String path) {
        Intent intent = new Intent(MainActivity.this, BoundService.class);
        intent.putExtra("play", path);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_playing_bar:
                Intent intent = new Intent(this, PlayMusicAvtivity.class);
                startActivity(intent);
                break;
            case R.id.iv_pause_playing_bar:
                if (isBound) {
                    isBound = false;
                    boundService.onPause();

                    startPlaying = false;
                    ivPausePlayingBar.setImageResource(R.drawable.ic_play);
                } else {
                    boundService.onPlayContinue();
                    isBound = true;
                    startPlaying = true;
                    ivPausePlayingBar.setImageResource(R.drawable.ic_pause);
                }
                break;
            default:
                break;
        }
    }
}
