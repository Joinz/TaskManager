package com.joinz.taskmanager.features.tasklist;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.joinz.taskmanager.db.PersistantStorage;
import com.joinz.taskmanager.features.productivity.ProductivityChangedListener;
import com.joinz.taskmanager.features.productivity.ProductivityFragment;
import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.AppDatabase;
import com.joinz.taskmanager.db.Task;
import com.joinz.taskmanager.features.newtask.NewTaskActivity;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements RecyclerItemTouchHelperListener {

    private View rlEmptyPage;
    private RecyclerView rv;
    private SwipeRefreshLayout srTasks;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        initSwipeRefresh(view);
        initFab(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTasksFromDb();
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

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), linearLayoutManager.getOrientation());
        rv.addItemDecoration(did);
        rv.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);
    }

    private void initSwipeRefresh(View view) {
        srTasks = view.findViewById(R.id.srTasks);
        srTasks.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTasksFromDb();
            }
        });
    }

    private void initFab(View view) {
        fabAddTask = view.findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    startActivity(new Intent(getActivity(), NewTaskActivity.class));
                }
            }
        });
    }

    private void loadTasksFromDb() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            AppDatabase db = App.getInstance().getDatabase();
            taskAdapter.setTasks(db.taskDao().getAll());
            isEmptyPage();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    srTasks.setRefreshing(false);
                }
            }, 2000);
        }
    }

    public void isEmptyPage() {
        if (taskAdapter.getItemCount() < 1) {
            rlEmptyPage.setVisibility(View.VISIBLE);
        } else {
            rlEmptyPage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (getActivity() != null) {
            PersistantStorage.init(getActivity());
            Task task = taskAdapter.getTask(position);
            App.getInstance().getDatabase().taskDao().delete(task);
            taskAdapter.removeTask(viewHolder.getAdapterPosition());

            isEmptyPage();
            addDoneTaskPref();
        }
    }

    private void addDoneTaskPref() {
        int tasksDone = PersistantStorage.getProperty(ProductivityFragment.TASKS_DONE);
        PersistantStorage.addProperty(ProductivityFragment.TASKS_DONE, ++tasksDone);
        if (getActivity() instanceof ProductivityChangedListener) {
            ((ProductivityChangedListener) getActivity()).onProductivityChanged();
            Toast.makeText(getContext(), "addDoneTaskPref() from TasksFragment", Toast.LENGTH_SHORT).show();
        }
    }
}
