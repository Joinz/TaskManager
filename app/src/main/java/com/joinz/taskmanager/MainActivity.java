package com.joinz.taskmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMockData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new TaskAdapter(this, new OnTaskClickListener() {
            @Override
            public void onClick(Task task) {
                Toast.makeText(MainActivity.this, task.getName(), Toast.LENGTH_LONG).show();
            }
        }, tasks));

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());
        rv.addItemDecoration(did);
    }

    private void createMockData() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            tasks.add(new Task("Выполнить задание номер " + (i+1), random.nextInt(5)));
        }
    }

    private interface OnTaskClickListener {
        void onClick(Task task);
    }

    private static class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
        private final Context context;
        private final OnTaskClickListener onTaskClickListener;

        private final List<Task> tasks;

        private TaskAdapter(Context context, OnTaskClickListener onTaskClickListener, List<Task> tasks) {
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

    private static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask;
        private TextView tvMarker;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            tvMarker = itemView.findViewById(R.id.tvMarker);
        }

        public void setData(Task task) {
            tvTask.setText(task.getName());
            tvMarker.setTextColor(task.getColor());
        }
    }
}
