package com.example.abyteofbraille.methods;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Skill.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
        public abstract DBA skillDao();
}
