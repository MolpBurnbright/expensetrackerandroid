package com.kapirawan.financial_tracker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.activities.ActivitySettings;
import com.kapirawan.financial_tracker.helper.Refresher;

public class ActivityMain extends AppCompatActivity{

    private Refresher refresher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup the content layout
        setContentView(R.layout.activity_main);
        //Setup the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setup the refresher
        refresher = new Refresher(this.getApplication());
        //Setup the fragment
        if (findViewById(R.id.viewPager) != null){
            if(savedInstanceState != null)
                return;
            ((ViewPager)findViewById(R.id.viewPager)).setAdapter(
                    new PageAdapter(getSupportFragmentManager()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                if(this.refresher != null)
                    this.refresher.refresh();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, ActivitySettings.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}