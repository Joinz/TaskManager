package com.joinz.taskmanager.features.newtask;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.joinz.taskmanager.R;


public class NewTaskActivity extends AppCompatActivity implements PriorityDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (savedInstanceState == null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.add(R.id.flContainer, NewTaskFragment.newInstance(), NewTaskFragment.TAG);
            transaction.commit();
        }
    }

    @Override
    public void onPriorityChosen(int priority) {
        NewTaskFragment fragment = (NewTaskFragment) getSupportFragmentManager().findFragmentByTag(NewTaskFragment.TAG);
        if (fragment != null) {
            fragment.onPriorityChosen(priority);
        }
    }
}
