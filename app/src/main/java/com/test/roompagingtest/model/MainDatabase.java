package com.test.roompagingtest.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {
       TestEntity.class
}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    public abstract TestEntityDao getTestEntitiesDao();

    public static MainDatabase getDatabase(Context context) {
        return Room
                .databaseBuilder(context, MainDatabase.class, "main.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

}
