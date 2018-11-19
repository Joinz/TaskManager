package com.joinz.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    private final Context context;
    private final OnTaskClickListener onTaskClickListener;

    private final List<Task> tasks;

    TaskAdapter(Context context, OnTaskClickListener onTaskClickListener, List<Task> tasks) {
        this.context = context;
        this.onTaskClickListener = onTaskClickListener;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_item, parent, false);
        final TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        taskViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTaskClickListener.onClick(tasks.get(taskViewHolder.getAdapterPosition()));
            }
        });
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        if (position != RecyclerView.NO_POSITION) {
            taskViewHolder.setData(tasks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
