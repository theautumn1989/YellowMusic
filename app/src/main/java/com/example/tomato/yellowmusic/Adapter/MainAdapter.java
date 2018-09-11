package com.example.tomato.yellowmusic.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tomato.yellowmusic.fragments.FragmentAlbum;
import com.example.tomato.yellowmusic.fragments.FragmentArist;
import com.example.tomato.yellowmusic.fragments.FragmentSong;


public class MainAdapter extends FragmentStatePagerAdapter {
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new FragmentSong();
                break;
            case 1:
                frag = new FragmentAlbum();
                break;
            case 2:
                frag = new FragmentArist();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "SONGS";
                break;
            case 1:
                title = "ALBUMS";
                break;
            case 2:
                title = "ARISTS";
                break;
        }

        return title;
    }
}
