package com.joinz.taskmanager.db;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;

import androidx.room.Room;

public class App extends Application {

    public static App instance;
    private AppDatabase database;
    private SharedPreferences sharedPreferences;
    private PersistantStorage persistantStorage;
    private String PrefStorageName = "StorageName";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();
        sharedPreferences = getApplicationContext().getSharedPreferences(PrefStorageName, MODE_PRIVATE);
        persistantStorage = new PersistantStorage();
        Stetho.initializeWithDefaults(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public PersistantStorage getPersistantStorage() {
        return persistantStorage;
    }
}
