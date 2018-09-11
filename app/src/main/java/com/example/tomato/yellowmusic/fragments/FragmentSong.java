package com.example.tomato.yellowmusic.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tomato.yellowmusic.Adapter.SongAdapter;
import com.example.tomato.yellowmusic.R;
import com.example.tomato.yellowmusic.activities.MainActivity;
import com.example.tomato.yellowmusic.models.Song;

import java.util.ArrayList;


public class FragmentSong extends Fragment {

    public static FragmentSong instance;


    View view;
    RecyclerView rvSong;
    ArrayList<Song> arrSong;
    SongAdapter songAdapter;
    LinearLayoutManager layoutManager;
    LoadListSong loadListSong;

    public FragmentSong() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song, container, false);
        initView();
        init();
        instance = this;
        return view;
    }

    private void initView() {
        rvSong = view.findViewById(R.id.rv_song);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSong.setLayoutManager(layoutManager);

        rvSong.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        rvSong.addItemDecoration(dividerItemDecoration);
    }

    private void init() {

        arrSong = new ArrayList<>();
        showListSong();

    }

    private void showListSong() {
        loadListSong = new LoadListSong();
        loadListSong.execute();
    }


    private class LoadListSong extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {

            if (MainActivity.instance.getListSong() == null) {

            } else {
                arrSong = MainActivity.instance.getListSong();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            songAdapter = new SongAdapter(arrSong, getActivity());
            rvSong.setAdapter(songAdapter);

        }
    }

    public void playingMusicBotombar(int position) {
    }

}
