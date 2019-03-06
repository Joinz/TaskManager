package com.joinz.taskmanager.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface TasksDoneDao {

    @Query("SELECT * FROM TasksDone")
    Observable<List<TasksDone>> getAllReactively();

    @Query("SELECT * FROM TasksDone WHERE date = :date")
    Single<TasksDone> getByDateReactively(int date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertReactively(TasksDone tasksDone);
}
