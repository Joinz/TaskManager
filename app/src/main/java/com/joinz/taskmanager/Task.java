package com.joinz.taskmanager;

import android.graphics.Color;

public class Task {
    private final String name;
    private final int priority;


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

    public int getColor() {
        switch (this.priority) {
            case 1: return Color.RED;
            case 2: return Color.MAGENTA;
            case 3: return  Color.GREEN;
            case 4: return Color.GRAY;
            case 5: return Color.WHITE;
            default: return Color.GREEN;
        }
    }
}
