package com.joinz.taskmanager;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;

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

    public int getPriority() {
        return priority;
    }

    public static int getColor(int priority) {
        switch (priority) {
            case 4: return Color.RED;
            case 3: return  Color.MAGENTA;
            case 2: return Color.GREEN;
            case 1: return Color.GRAY;
            default: return Color.GREEN;
        }
    }

}
