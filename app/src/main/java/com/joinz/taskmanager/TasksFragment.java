package com.joinz.taskmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    public static final int NEW_TASK_ACTIVITY_KEY = 101;
    private View rlEmptyPage;
    private RecyclerView rv;
    private FloatingActionButton fabAddTask;
    private List<Task> tasks = new ArrayList<>();
    private TaskAdapter taskAdapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        initRecyclerView(view);
        initFab(view);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK_ACTIVITY_KEY && resultCode == Activity.RESULT_OK && data != null) {
            Task task = (Task) data.getParcelableExtra(NewTaskActivity.NEW_TASK_KEY);
            taskAdapter.addTask(task);
            isEmptyPage();
        }
    }

    private void initRecyclerView(View view) {
        rlEmptyPage = view.findViewById(R.id.rl_empty_page);
        rv = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(getContext(), new OnTaskClickListener() {
            @Override
            public void onClick(Task task) {
                Toast.makeText(getContext(), task.getName(), Toast.LENGTH_LONG).show();
            }
        }, tasks);
        rv.setAdapter(taskAdapter);
//        tasks.add(new Task("Fragments", 0));

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());
        rv.addItemDecoration(did);
        rv.setHasFixedSize(true);
        isEmptyPage();
    }

    private void initFab(View view) {
        fabAddTask = view.findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), NewTaskActivity.class), NEW_TASK_ACTIVITY_KEY);
            }
        });
    }

    public void isEmptyPage() {
        if (taskAdapter.getItemCount() < 1) {
            rlEmptyPage.setVisibility(View.VISIBLE);
        } else {
            rlEmptyPage.setVisibility(View.INVISIBLE);
        }
    }
}
