package com.joinz.taskmanager;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.priority);
    }

    protected Task(Parcel in) {
        this.name = in.readString();
        this.priority = in.readInt();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
