package com.example.tomato.yellowmusic.utils;

import android.app.Application;
import android.database.Cursor;
import android.provider.MediaStore;
import com.example.tomato.yellowmusic.models.Song;

import java.util.ArrayList;

public class AppController extends Application {

    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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

    public static AppController getInstance() {
        return instance;
    }

    public static void setInstance(AppController instance) {
        AppController.instance = instance;
    }
}
