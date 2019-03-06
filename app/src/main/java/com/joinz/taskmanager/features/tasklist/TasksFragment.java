package com.joinz.taskmanager.features.tasklist;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.AppDatabase;
import com.joinz.taskmanager.db.Task;
import com.joinz.taskmanager.db.TasksDone;
import com.joinz.taskmanager.features.newtask.NewTaskActivity;
import com.joinz.taskmanager.features.productivity.ProductivityFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TasksFragment extends Fragment implements RecyclerItemTouchHelperListener {

    public static final String TAG = "DEBUG";
    private View rlEmptyPage;
    private RecyclerView rv;
    private SwipeRefreshLayout srTasks;
    private FloatingActionButton fabAddTask;
    private List<Task> tasks = new ArrayList<>();
    private TaskAdapter taskAdapter;

    private final AppDatabase database = App.getInstance().getDatabase();
    private CompositeDisposable compositeDisposable;

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
        initViews(view);
        initSwipeRefresh(view);
        initFab(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        loadTasksReactively();
    }
    //TODO ViewObservable.clicks()
    private void initViews(View view) {
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
                //todo sr...
            }
        });
    }

    private void initFab(View view) {
        fabAddTask = view.findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewTaskActivity.class));
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabAddTask.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabAddTask.isShown()) {
                    fabAddTask.hide();
                }
            }
        });
    }

    private void loadTasksReactively() {
        compositeDisposable.add(database.taskDao().getAllReactively()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        taskAdapter.setTasks(tasks);
                        srTasks.setRefreshing(false);
                        isEmptyPage();
                    }
                }));
    }

    private void deleteTasksReactively(Task task, int adapterPosition) {
        compositeDisposable.add(database.taskDao().deleteReactively(task)
                .subscribeOn(Schedulers.io())
                .subscribe());
        taskAdapter.removeTask(adapterPosition);
        isEmptyPage();
    }

    private void isEmptyPage() {
        if (taskAdapter.getItemCount() < 1) {
            rlEmptyPage.setVisibility(View.VISIBLE);
        } else {
            rlEmptyPage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Task task = taskAdapter.getTask(position);
        int adapterPosition = viewHolder.getAdapterPosition();
        deleteTasksReactively(task, adapterPosition);

        addCountAllTaskPrefReactively();
        addTasksDoneDbReactively();
        Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
    }

    private void addCountAllTaskPrefReactively() {
        compositeDisposable.add(App.getInstance().getPersistantStorage().addPropertyReactively(ProductivityFragment.TASKS_DONE)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    private void addTasksDoneDbReactively() {
        final int today = getToday();
        compositeDisposable.add(database.tasksDoneDao()
                .getByDateReactively(getToday())
                .map(new Function<TasksDone, TasksDone>() {
                    @Override
                    public TasksDone apply(TasksDone tasksDone) throws Exception {
                        return new TasksDone(today, tasksDone.getCount());
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<TasksDone>() {
                    @Override
                    public void accept(TasksDone tasksDone) throws Exception {
                        int count = tasksDone.getCount() + 1;
                        setTasksDoneDbReactively(new TasksDone(today, count));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, throwable.getMessage());
//                        if (throwable.getMessage().contains("null")) {
                        setTasksDoneDbReactively(new TasksDone(today, 1));
//                        }
                    }
                }));
    }

    private void setTasksDoneDbReactively(TasksDone tasksDone) {
        compositeDisposable.add(database.tasksDoneDao()
                .insertReactively(tasksDone)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    private int getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public void onStop() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onStop();
    }
}

