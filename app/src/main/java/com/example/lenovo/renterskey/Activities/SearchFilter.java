package com.example.lenovo.renterskey.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.renterskey.ExtraClasses.SeekbarWithIntervals;
import com.example.lenovo.renterskey.R;

import java.util.ArrayList;

public class SearchFilter extends AppCompatActivity {

    private SeekbarWithIntervals seekbarWithIntervals = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()== R.id.apply_filter){
                setResult(RESULT_OK);
                finish();
                return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searchFilter);
        setSupportActionBar(toolbar);

        //to create back arror icon on toolbar
        toolbar.setNavigationIcon(getDrawable(R.drawable.arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //seekbar with intervals
        ArrayList<String> intervals=new ArrayList<String>(){{
            add("Auto");
            add("5");
            add("10");
            add("20");
            add("50");
            add("100+");
           }};
        getSeekbarWithIntervals().setIntervals(intervals);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_searchfilter);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        TextView textView=navigation.findViewById(R.id.apply_filter).findViewById(R.id.largeLabel);
        textView.setTextSize(20);
        textView.setPadding(0,0,0,30);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchfilter_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.clear_filter){

            return true;
        }
        return false;
    }

    private SeekbarWithIntervals getSeekbarWithIntervals() {
        if (seekbarWithIntervals == null) {
            seekbarWithIntervals = findViewById(R.id.seekbarWithIntervals);
        }

        return seekbarWithIntervals;
    }

}
