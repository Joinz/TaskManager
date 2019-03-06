package com.joinz.taskmanager.features.tasklist;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
    private final Context context;
    private final OnTaskClickListener onTaskClickListener;

    private List<Task> tasks;

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
                if (taskViewHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    int adapterPosition = taskViewHolder.getAdapterPosition();
                    Task task = tasks.get(adapterPosition);
                    onTaskClickListener.onClick(task);
                }
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

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public Task getTask(int position) {
        return tasks.get(position);
    }
}
