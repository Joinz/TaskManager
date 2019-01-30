package com.joinz.taskmanager.features.tasklist;

import android.os.AsyncTask;

import com.joinz.taskmanager.db.App;
import com.joinz.taskmanager.db.TasksDone;
import com.joinz.taskmanager.db.TasksDoneDao;

import java.util.Calendar;
import java.util.Date;

public class MakeTasksDoneInDbWithAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        TasksDoneDao tasksDoneDao = App.getInstance().getDatabase().tasksDoneDao();
        int tasksDoneCount = 0;
        if (tasksDoneDao.getByDate((getToday())) != null) {
            tasksDoneCount = tasksDoneDao.getByDate(getToday()).getCount();
            TasksDone tasksDoneNew = new TasksDone(getToday(), tasksDoneCount + 1);
            tasksDoneDao.insert(tasksDoneNew);
        } else {
            TasksDone tasksDoneNew = new TasksDone(getToday(), tasksDoneCount);
            tasksDoneDao.insert(tasksDoneNew);
        }
        return null;
    }

    private int getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
