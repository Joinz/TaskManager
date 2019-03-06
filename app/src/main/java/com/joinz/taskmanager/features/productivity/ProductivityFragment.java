package com.joinz.taskmanager.features.productivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joinz.taskmanager.R;
import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.AppDatabase;
import com.joinz.taskmanager.db.TasksDone;
import com.joinz.taskmanager.db.TasksDoneDao;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ProductivityFragment extends Fragment {

    public static final  String TAG = "DEBUG";
    public static final String TASKS_DONE = "tasksDone";
    private TextView tvTasksDone;
    private GraphView graphView;

    private final AppDatabase database = App.getInstance().getDatabase();
    private CompositeDisposable compositeDisposable;

    public GraphView getGraphView() {
        return graphView;
    }


    public ProductivityFragment() {
        // Required empty public constructor
    }

    public static ProductivityFragment newInstance() {
        return new ProductivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_productivity, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "ProductivityFragment.onResume");
        compositeDisposable = new CompositeDisposable();
        updateGraphReactively();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTasksDone = view.findViewById(R.id.tvTasksDone);
        graphView = view.findViewById(R.id.gv);
    }

    private void setTasksDoneReactively() {
        compositeDisposable.add(App.getInstance().getPersistantStorage().getPropertyReactively(TASKS_DONE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        String taskDoneText = String.valueOf(integer) + " " + getString(R.string.tv_tasks_done);
                        tvTasksDone.setText(taskDoneText);
                    }
                })
        );
    }

    private void updateGraphReactively() {
        final List<Integer> tasksDonePerDay = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        TasksDoneDao tasksDoneDao = database.tasksDoneDao();
        compositeDisposable.add(tasksDoneDao.getAllReactively()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TasksDone>>() {
                    @Override
                    public void accept(List<TasksDone> tasksDone) throws Exception {
                        for (int i = 0; i < tasksDone.size(); i++) {
                            if (tasksDone.get(i) != null) {
                                int count = tasksDone.get(i).getCount();
                                int day = tasksDone.get(i).getDate() - 1;
                                tasksDonePerDay.set(day, count);
                            }
                        }
                        ProductivityFragment.this.getGraphView().initList(tasksDonePerDay);
                        setTasksDoneReactively();
                    }
                }));
    }

    @Override
    public void onStop() {
        Log.d(TAG, "ProductivityFragment.onStop");
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onStop();
    }
}