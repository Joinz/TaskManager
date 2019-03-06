package com.joinz.taskmanager.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.joinz.taskmanager.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

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
