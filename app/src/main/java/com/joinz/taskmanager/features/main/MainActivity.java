package com.joinz.taskmanager.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.joinz.taskmanager.features.productivity.ProductivityChangedListener;
import com.joinz.taskmanager.features.productivity.ProductivityFragment;
import com.joinz.taskmanager.R;

public class MainActivity extends AppCompatActivity implements ProductivityChangedListener {

    Toolbar myToolbar;
    private ViewPager vpTabs;
    private TabLayout tlTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setSupportActionBar(myToolbar);
        initTabs();
    }

    private void initViews() {
        myToolbar = findViewById(R.id.my_toolbar);
        vpTabs = findViewById(R.id.vpTabs);
        tlTabs = findViewById(R.id.tlTabs);
    }

    private void initTabs() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(supportFragmentManager, this);
        vpTabs.setAdapter(adapter);
        tlTabs.setupWithViewPager(vpTabs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_writeMail:
                Toast.makeText(this, "Write Mail clicked", Toast.LENGTH_SHORT).show();
                sendMail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int getTaskColor(int priority) {
        switch (priority) {
            case 4: return this.getResources().getColor(R.color.orangey_red);
            case 3: return this.getResources().getColor(R.color.sun_yellow);
            case 2: return this.getResources().getColor(R.color.viridian);
            case 1: return this.getResources().getColor(R.color.clear_blue);
            default: return this.getResources().getColor(R.color.clear_blue);
        }
    }
    private void sendMail() {
        String text = "Test message from toolbar";
        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.putExtra(Intent.EXTRA_EMAIL, "developer@gmai.com");
        sendMail.putExtra(Intent.EXTRA_SUBJECT, "Message from toolbar");
        sendMail.putExtra(Intent.EXTRA_TEXT, text);
        sendMail.setType("message/rfc822");
        startActivity(Intent.createChooser(sendMail, "Send mail with:"));
    }

    @Override
    public void onProductivityChanged() {
        TabsFragmentAdapter adapter = (TabsFragmentAdapter) vpTabs.getAdapter();
        ProductivityFragment fragment = (((ProductivityFragment) adapter.getItem(1)));
        fragment.setTasksDone();
    }

}
