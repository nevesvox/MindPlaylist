package com.example.mindplaylist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MusicDAO {

    @Query("SELECT * FROM playlist")
    List<Music> getPlaylist();

    @Insert
    void addMusic(Music music);

    @Update
    void  updateMusic(Music music);

    @Delete
    void deleteMusic(Music music);
}
