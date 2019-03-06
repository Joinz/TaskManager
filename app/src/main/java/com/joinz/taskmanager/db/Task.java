package com.joinz.taskmanager.db;

import android.graphics.Color;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task  {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public final String name;
    public final int priority;


    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public static int getColor(int priority) {
        switch (priority) {
            case 4: return Color.parseColor("#f44336");
            case 3: return  Color.parseColor("#fdd735");
            case 2: return Color.parseColor("#21897b");
            case 1: return Color.parseColor("#2396f3");
            default: return Color.parseColor("#fdd735");
        }
    }

}
