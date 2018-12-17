package com.joinz.taskmanager.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class TasksDone {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public Date date;
    public int count;

    public TasksDone(Date date, int count) {
        this.date = date;
        this.count = count;
    }

    
}
