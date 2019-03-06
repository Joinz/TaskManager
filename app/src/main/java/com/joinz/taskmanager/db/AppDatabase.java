package com.joinz.taskmanager.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, TasksDone.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract TasksDoneDao tasksDoneDao();
}
