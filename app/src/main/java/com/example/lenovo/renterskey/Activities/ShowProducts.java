package com.example.lenovo.renterskey.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
        if(savedInstanceState==null){
            navigationController.navigateToShowProducts("electronics");
        }
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.;

        return super.onOptionsItemSelected(item);
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

            }
            break;

            case R.id.nav_order:{

            }
            break;

            case R.id.nav_products:{

            }
            break;

            case R.id.nav_setting:{

            }
            break;
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
