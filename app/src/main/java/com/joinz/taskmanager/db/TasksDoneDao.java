package com.joinz.taskmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TasksDoneDao {

    @Query("SELECT * FROM TasksDone")
    List<TasksDone> getAll();

    @Query("SELECT * FROM TasksDone WHERE date = :date")
    TasksDone getByDate(int date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TasksDone tasksDone);

    @Delete
    void delete(TasksDone tasksDone);
}
