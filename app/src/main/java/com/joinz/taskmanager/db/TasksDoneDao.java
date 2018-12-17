package com.joinz.taskmanager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TasksDoneDao {

    @Query("SELECT * FROM TasksDone")
    List<TasksDone> getAll();

    @Query("SELECT * FROM TasksDone ORDER BY id DESC LIMIT 7")
    List<TasksDone> getLastWeek();

    @Query("SELECT * FROM TasksDone WHERE id = :id")
    TasksDone getById(long id);

    @Insert
    void insert(TasksDone tasksDone);

    @Update
    void update(TasksDone tasksDone);

    @Delete
    void delete(TasksDone tasksDone);
}
