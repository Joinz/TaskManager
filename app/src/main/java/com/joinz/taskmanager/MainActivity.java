package com.joinz.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpTabs;
    private TabLayout tlTabs;
    public SparseArray<String> tabNames;

    public MainActivity() {
        tabNames = new SparseArray<>();
        tabNames.put(0, "Задачи");
        tabNames.put(1, "Продуктивность");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initViews();
        initTabs();
    }

    private void initToolbar() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void initViews() {
        vpTabs = findViewById(R.id.vpTabs);
        tlTabs = findViewById(R.id.tlTabs);
    }

    private void initTabs() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(supportFragmentManager);
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

    private void sendMail() {
        String text = "Test message from toolbar";
        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.putExtra(Intent.EXTRA_EMAIL, "developer@gmai.com");
        sendMail.putExtra(Intent.EXTRA_SUBJECT, "Message from toolbar");
        sendMail.putExtra(Intent.EXTRA_TEXT, text);
        sendMail.setType("message/rfc822");
        startActivity(Intent.createChooser(sendMail, "Send mail with:"));
    }
}
