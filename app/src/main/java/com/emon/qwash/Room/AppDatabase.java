package com.emon.qwash.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.emon.qwash.Room.Dao.ProfileDao;
import com.emon.qwash.Room.EntityClass.ProfileEntity;


@Database(entities = {ProfileEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;

    public abstract ProfileDao profileDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "qwash.db")
                            .fallbackToDestructiveMigration() // clears data if structure changes
                            .build();
                }
            }
        }
        return instance;
    }
}
