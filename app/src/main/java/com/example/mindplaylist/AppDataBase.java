package com.example.mindplaylist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Music.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MusicDAO musicDAO();
}
