package com.joinz.taskmanager.features.tasklist;

import android.os.AsyncTask;
import android.util.Log;

import com.joinz.taskmanager.db.Task;

import java.util.List;

public class LoadFromDbWithAsyncTask extends AsyncTask<Void, Void, List<Task>> {
    private TasksFragment tasksFragment;

    public LoadFromDbWithAsyncTask(TasksFragment tasksFragment) {
        this.tasksFragment = tasksFragment;
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return tasksFragment.loadTasksFromDb();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        tasksFragment.setTasks(tasks);
    }

    @Override
    protected void onCancelled() {
        tasksFragment = null;
        super.onCancelled();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        tasksFragment = null;
    }
}
