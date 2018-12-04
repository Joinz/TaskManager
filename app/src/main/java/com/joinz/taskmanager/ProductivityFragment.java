package com.joinz.taskmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProductivityFragment extends Fragment {
    public static final String TASKS_DONE = "tasksDone";
    private TextView tvTasksDone;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTasksDone = view.findViewById(R.id.tvTasksDone);
        setTasksDone();
    }

    public void setTasksDone() {
        if (getActivity() != null) {
            PersistantStorage.init(getActivity());
            int value = PersistantStorage.getProperty(TASKS_DONE);
            String taskDoneText = String.valueOf(value) + " " + getString(R.string.tv_tasks_done);
            tvTasksDone.setText(taskDoneText);
        }
    }
}
