package com.joinz.taskmanager.features.newtask;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.AppDatabase;
import com.joinz.taskmanager.db.Task;

public class LoadTaskToDbWithAsyncTask extends AsyncTask<Void, Void, Void> {
    private NewTaskFragment newTaskFragment;
    private Task task;

    public LoadTaskToDbWithAsyncTask(NewTaskFragment newTaskFragment, Task task) {
        this.newTaskFragment = newTaskFragment;
        this.task = task;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDatabase db = App.getInstance().getDatabase();
        db.taskDao().insert(task);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        FragmentActivity activity = newTaskFragment.getActivity();
        if (activity != null && activity.getClass().getSimpleName().equals(NewTaskActivity.class.getSimpleName())) {
            activity.finish();
            Toast.makeText(newTaskFragment.getContext(), "Задача " + task.name + " добавлена ", Toast.LENGTH_SHORT).show();
        }
        newTaskFragment = null;
    }
}
