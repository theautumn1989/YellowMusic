package com.example.tomato.yellowmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tomato.yellowmusic.R;
import com.example.tomato.yellowmusic.activities.MainActivity;
import com.example.tomato.yellowmusic.fragments.FragmentSong;
import com.example.tomato.yellowmusic.models.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    int positionArrSong;

    ArrayList<Song> arrSong;
    Context context;


    public SongAdapter(ArrayList<Song> arrSong, Context context) {
        this.arrSong = arrSong;
        this.context = context;


    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {

        View itemView = LayoutInflater.from(group.getContext()).inflate(R.layout.item_fragment_song, group, false);
        if (itemView != null) {
            return new SongViewHolder(itemView);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int i) {

        holder.ivIconSong.setImageResource(R.drawable.ic_song_fragment);
        holder.tvNameSong.setText(arrSong.get(i).getTitle());
        holder.tvArist.setText(arrSong.get(i).getArtist());
    }

    @Override
    public int getItemCount() {
        return arrSong.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemClickListener {

        ImageView ivIconSong;
        TextView tvNameSong, tvArist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIconSong = itemView.findViewById(R.id.iv_song);
            tvNameSong = itemView.findViewById(R.id.tv_name_song);
            tvArist = itemView.findViewById(R.id.tv_arist);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            MainActivity.instance.setPlayingBar(getAdapterPosition());
            FragmentSong.instance.playingMusicBotombar(getAdapterPosition());

        }

        @Override
        public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
            Toast.makeText(context, "aaaaaaaaa", Toast.LENGTH_SHORT).show();
        }
    }


}
