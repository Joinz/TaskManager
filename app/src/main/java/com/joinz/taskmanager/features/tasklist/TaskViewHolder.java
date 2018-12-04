package com.joinz.taskmanager.features.tasklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.Task;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTask;
    private TextView tvMarker;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTask = itemView.findViewById(R.id.tvTask);
        tvMarker = itemView.findViewById(R.id.tvMarker);
    }

    public void setData(Task task) {
        tvTask.setText(task.getName());
        tvMarker.setTextColor(task.getColor(task.priority));
    }
}
