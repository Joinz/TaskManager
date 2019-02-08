package com.joinz.taskmanager.db;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.stetho.Stetho;

public class App extends Application {

    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").allowMainThreadQueries().build();
        Stetho.initializeWithDefaults(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
