package com.joinz.taskmanager.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PersistantStorage {
    public static final String STORAGE_NAME = "StorageName";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private static Context context = null;

    public static void init(Context cntxt) {
        context = cntxt;
    }

    @SuppressLint("CommitPrefEdits")
    private static void init() {
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static void addProperty(String name, int value) {
        if (settings == null) {
            init();
        }
        editor.putInt(name, value).commit();
    }

    public static int getProperty(String name) {
        if (settings == null) {
            init();
        }
        return settings.getInt(name, 0);
    }
}

