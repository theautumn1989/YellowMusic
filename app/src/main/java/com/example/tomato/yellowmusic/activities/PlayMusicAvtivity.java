package com.example.tomato.yellowmusic.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tomato.yellowmusic.Adapter.SongPlayingAdapter;
import com.example.tomato.yellowmusic.R;
import com.example.tomato.yellowmusic.models.Song;

import java.util.ArrayList;

public class PlayMusicAvtivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBeats, ivNext, ivpre, ivRepeat, ivshuffle, ivPlay;
    TextView tvTime;
    RecyclerView rvListSongPlaying;
    LinearLayoutManager layoutManager;
    ArrayList<Song> arrSong;
    SongPlayingAdapter songAdapter;
    LoadListSong loadListSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music_avtivity);


        initView();
        init();
        initEvent();
    }

    private void init() {
        arrSong = new ArrayList<>();
        loadListSong = new LoadListSong();
        loadListSong.execute();
    }

    private void initEvent() {
    ivshuffle.setOnClickListener(this);
    ivRepeat.setOnClickListener(this);
    ivpre.setOnClickListener(this);
    ivPlay.setOnClickListener(this);
    ivNext.setOnClickListener(this);
    }

    private void initView() {
        ivBeats = findViewById(R.id.iv_beats);
        ivNext = findViewById(R.id.iv_next);
        ivPlay = findViewById(R.id.iv_play);
        ivpre = findViewById(R.id.iv_pre);
        ivRepeat = findViewById(R.id.iv_repeat_one);
        ivshuffle = findViewById(R.id.iv_shuffle_on);
        rvListSongPlaying = findViewById(R.id.rv_list_song_playing);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListSongPlaying.setLayoutManager(layoutManager);

        rvListSongPlaying.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rvListSongPlaying.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_pre:
                break;
            case R.id.iv_repeat_one:
                break;
            case R.id.iv_shuffle_on:
                break;
        }
    }

    private class LoadListSong extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {

            if(MainActivity.instance.getListSong() == null){

            }else {
                arrSong = MainActivity.instance.getListSong();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            songAdapter = new SongPlayingAdapter(arrSong, PlayMusicAvtivity.this);
            rvListSongPlaying.setAdapter(songAdapter);

        }
    }
}
