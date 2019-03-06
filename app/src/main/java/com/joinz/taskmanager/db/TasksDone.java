package com.joinz.taskmanager.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TasksDone {
    @PrimaryKey
    int date;
    int count;

    public TasksDone(int date, int count) {
        this.date = date;
        this.count = count;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
