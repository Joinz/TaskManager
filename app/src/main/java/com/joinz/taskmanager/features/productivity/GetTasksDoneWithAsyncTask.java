package com.joinz.taskmanager.features.productivity;

import android.os.AsyncTask;

import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.TasksDone;
import com.joinz.taskmanager.db.TasksDoneDao;

import java.util.Arrays;
import java.util.List;

public class GetTasksDoneWithAsyncTask extends AsyncTask<Void, Void, List<Integer>> {
    private ProductivityFragment productivityFragment;

    public GetTasksDoneWithAsyncTask(ProductivityFragment productivityFragment) {
        this.productivityFragment = productivityFragment;
    }

    @Override
    protected List<Integer> doInBackground(Void... voids) {
        List<Integer> tasksDonePerDay = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        TasksDoneDao tasksDoneDao = App.getInstance().getDatabase().tasksDoneDao();
        List<TasksDone> tasksDone = tasksDoneDao.getAll();
        for (int i = 0; i < tasksDone.size(); i++) {
            if (tasksDone.get(i) != null) {
                int count = tasksDone.get(i).getCount();
                int day = tasksDone.get(i).getDate();
                tasksDonePerDay.set(day, count);
            }
        }
        return tasksDonePerDay;
    }

    @Override
    protected void onPostExecute(List<Integer> tasksDonePerDay) {
        productivityFragment.getGraphView().initList(tasksDonePerDay);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        productivityFragment = null;
    }
}
