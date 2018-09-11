package com.example.tomato.yellowmusic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomato.yellowmusic.R;


public class FragmentArist extends Fragment {

    public FragmentArist() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_arist, container, false);
    }
}
