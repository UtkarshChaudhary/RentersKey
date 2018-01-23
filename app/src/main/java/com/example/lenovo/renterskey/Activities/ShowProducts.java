package com.example.lenovo.renterskey.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.ExtraClasses.NavigationController;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ShowProducts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    NavigationController navigationController;

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_showProducts);
        setSupportActionBar(toolbar);

        if(drawer==null){
            drawer = findViewById(R.id.drawer_layout_showProduct);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_showProduct);
        navigationView.setNavigationItemSelectedListener(this);
        Intent i=getIntent();
        String category=i.getStringExtra(IntentConstant.CATEGORY);
        if(category==null){
            category="electronics";
        }
        if(savedInstanceState==null){
            navigationController.navigateToShowProducts(category);
        }
        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
        TextView tv=navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        if(sharedPreferences.getString(SharedPreferencesConstant.FIRSTNAME,null)!=null) {
            tv.setText(sharedPreferences.getString(SharedPreferencesConstant.FIRSTNAME,null));
        }
        TextView tv1=navigationView.getHeaderView(0).findViewById(R.id.nav_emailId);
        if(sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null)!=null){
            tv1.setText(sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null));
        }

        FloatingActionButton fab=findViewById(R.id.fab_show_products);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ShowProducts.this,PostAdd.class);
                startActivityForResult(i,IntentConstant.SHOWPRODUCTS_POSTADD);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_products_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if(drawer==null){
            drawer = findViewById(R.id.drawer_layout_showProduct);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.;
        switch (item.getItemId()){
            case R.id.show_products_search : {
                Intent i = new Intent(this,SearchEngineActivity.class);
                startActivityForResult(i, IntentConstant.SHOWPRODUCTS_SEARCHENGINE);
                return true;
            }
            case R.id.show_products_filter :{
                Intent i=new Intent(this,SearchFilter.class);
                startActivityForResult(i,IntentConstant.SHOWPRODUCTS_SEARCHFILTER);
                return true;
            }


            default:return false;
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle postadd_bottomnavigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_mobiles :{

            }
            break;

            case R.id.nav_tv:{

            }
            break;

            case R.id.nav_laptop :{

            }
            break;

            case R.id.nav_appliances:{

            }
            break;

            case R.id.nav_furniture:{

            }
            break;

            case R.id.nav_fashion:{

            }
            break;

            case R.id.nav_more:{

            }
            break;

            case R.id.nav_cart:{
                Intent i=new Intent(this,CartActivity.class);
                startActivityForResult(i, IntentConstant.SHOWPRODUCTS_CARTACTIVITY);
                return true;
            }

            case R.id.nav_setting:{

            }
            break;
            case R.id.nav_logout :{
                SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent i=new Intent(this,StartingActivity.class);
                startActivity(i);
            }
        }

        if(drawer==null){
            drawer = findViewById(R.id.drawer_layout_showProduct);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
