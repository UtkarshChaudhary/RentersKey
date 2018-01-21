package com.example.lenovo.renterskey.Activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.lenovo.renterskey.ExtraClasses.HorizontalScrollViewAdapter;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.db.ProductDao;
import com.example.lenovo.renterskey.db.RentersKeyDb;
import com.example.lenovo.renterskey.vo.HelperJson;
import com.example.lenovo.renterskey.vo.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   private DrawerLayout drawer;
//    MaterialSearchView searchView;
    ProductDao productDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        productDao=RentersKeyDb.getINSTANCE(this).productDao();
        clearPreviousList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(drawer==null) {
            drawer = findViewById(R.id.drawer_layout);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView=findViewById(R.id.horizontal_recycle_view);
        HorizontalScrollViewAdapter adapter=new HorizontalScrollViewAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fetchProductListForSearching();
        //search_bar

//        searchView=(MaterialSearchView)findViewById(R.id.search_bar_main_activity);
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText!=null&&newText.trim().isEmpty()){
//                    newText=newText.trim().toLowerCase();
//                    List<String> listFound=new ArrayList<>();
//
//
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if(drawer==null) {
            drawer = findViewById(R.id.drawer_layout);
        }
       if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem item=menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

//        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(MainActivity.this,SearchEngineActivity.class)));
//            }
//        });
      //  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
     //   searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this,SearchEngineActivity.class)));
     //   searchView.setQueryHint(getResources().getString(R.string.search_title));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.;
        switch (item.getItemId()){
            case R.id.action_search :{
                Intent i=new Intent(this,SearchEngineActivity.class);
                startActivityForResult(i, IntentConstant.REQUEST_CODE_MAIN_ACTIVITY_SEARCH_ENGINE);
                return true;
            }

            case R.id.bell_icon :{

                return true;
            }
            case R.id.cart_icon :{

                return true;
            }
            default: return false;
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

        if(drawer==null) {
            drawer = findViewById(R.id.drawer_layout);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fetchProductListForSearching(){
        Call<HelperJson<Object,List<Products>>> call= ClientService.createService().getProductsForSearch();
        call.enqueue(new Callback<HelperJson<Object,List<Products>>>() {
            @Override
            public void onResponse(Call<HelperJson<Object,List<Products>>> call, Response<HelperJson<Object,List<Products>>> response) {
//               Log.e("abcdef","onresponse");
                if(response!=null&&response.body()!=null&&response.isSuccessful()){
//                    Log.e("abcdef","success"+response.body().toString());
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            productDao.insertProducts(response.body().data2);
                            return null;
                        }
                    }.execute();

//                    Log.e("abcdef",productsList.toString());
//                    Log.e("abcdef",productsList.size()+"");
                }
            }

            @Override
            public void onFailure(Call<HelperJson<Object,List<Products>>> call, Throwable t) {
 //               Log.e("abcdef","failure"+t.toString());
            }
        });
    }

  private void clearPreviousList(){
      new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... voids) {
              productDao.deleteListOfProducts();
              return null;
          }
      }.execute();
  }
}
