package com.joinz.taskmanager.db;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    Flowable<List<Task>> getAllReactively();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task getById(long id);

    @Insert
    Completable insertReactively(Task ...task);

    @Delete
    Completable deleteReactively(Task task);
}
