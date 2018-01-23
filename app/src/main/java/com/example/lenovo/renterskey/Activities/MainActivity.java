package com.example.lenovo.renterskey.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.renterskey.ExtraClasses.HorizontalScrollViewAdapter;
import com.example.lenovo.renterskey.ExtraClasses.ProductListAdapter;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.IntentConstant;
import com.example.lenovo.renterskey.IntentAndSharedPreferences.SharedPreferencesConstant;
import com.example.lenovo.renterskey.Networking.ClientService;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.db.ProductDao;
import com.example.lenovo.renterskey.db.RentersKeyDb;
import com.example.lenovo.renterskey.vo.HelperJson;
import com.example.lenovo.renterskey.vo.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   private DrawerLayout drawer;
//    MaterialSearchView searchView;
    ProductDao productDao;
    RecyclerView recyclerViewFakeProducts;
    static List<Products> fakeProductsList;
    ProductListAdapter productListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(productDao==null) {
            productDao = RentersKeyDb.getINSTANCE(this).productDao();
            clearPreviousList();
            fetchProductListForSearching();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
        TextView tv=navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        if(sharedPreferences.getString(SharedPreferencesConstant.FIRSTNAME,null)!=null) {
            tv.setText(sharedPreferences.getString(SharedPreferencesConstant.FIRSTNAME,null));
        }
        TextView tv1=navigationView.getHeaderView(0).findViewById(R.id.nav_emailId);
        if(sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null)!=null){
            tv1.setText(sharedPreferences.getString(SharedPreferencesConstant.EMAIL,null));
        }

        RecyclerView recyclerView=findViewById(R.id.horizontal_recycle_view);
        HorizontalScrollViewAdapter adapter=new HorizontalScrollViewAdapter(recyclerView.getContext(), new HorizontalScrollViewAdapter.OnItemClicked() {
            @Override
            public void onClickFunction(View view, int pos) {
                Intent i=new Intent(MainActivity.this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewFakeProducts=findViewById(R.id.rec_view_main_acti_products);
        recyclerViewFakeProducts.setLayoutManager(new LinearLayoutManager(this));
        if(fakeProductsList==null||fakeProductsList.isEmpty()) {
            fakeProductsList = getFakeProductsList();
        }
        productListAdapter=new ProductListAdapter(fakeProductsList, this, new ProductListAdapter.ParticularProductClicked() {
            @Override
            public void onProductClicked(View view, int pos) {

            }
        });
        recyclerViewFakeProducts.setAdapter(productListAdapter);
        FloatingActionButton fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PostAdd.class);
                startActivityForResult(i,IntentConstant.MAINACTIVITY_POSTADD);
            }
        });
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
                startActivityForResult(i, IntentConstant.MAIN_ACTIVITY_SEARCH_ENGINE);
                return true;
            }

            case R.id.bell_icon :{

                return true;
            }
            case R.id.cart_icon :{
                Intent i=new Intent(this,CartActivity.class);
                startActivityForResult(i,IntentConstant.MAINACTIVITY_CARTACTIVITY);
                return true;
            }
            default: return false;
        }

    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle postadd_bottomnavigation view item clicks here.
       switch (item.getItemId()){
            case R.id.nav_mobiles :{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_tv:{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_laptop :{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_appliances:{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }


            case R.id.nav_furniture:{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_fashion:{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_more:{
                Intent i=new Intent(this,ShowProducts.class);
                i.putExtra(IntentConstant.CATEGORY,"electronics");
                startActivityForResult(i,IntentConstant.MAINACTIVITY_SHOWPRODUCTS);
                return true;
            }

            case R.id.nav_cart:{
                Intent i=new Intent(this,CartActivity.class);
                startActivityForResult(i,IntentConstant.MAINACTIVITY_CARTACTIVITY);
                return true;
            }


            case R.id.nav_setting:{
                     return true;
            }
           case R.id.nav_logout :{
                SharedPreferences sharedPreferences=getSharedPreferences(SharedPreferencesConstant.SHAREDPREFERENCE_NAME,MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent i=new Intent(this,StartingActivity.class);
               startActivity(i);
            }

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


  private List<Products> getFakeProductsList(){
      List<Products> productsList=new ArrayList<>();
      Resources resources=this.getResources();
      String[] type=resources.getStringArray(R.array.products_type);
      TypedArray array=resources.obtainTypedArray(R.array.products_images);
      Drawable[] picture=new Drawable[array.length()];
      for(int i=0;i<type.length;i++){
          picture[i]=array.getDrawable(i);
      }
      array.recycle();
      String[] price=new String[type.length];
      String[] rent=new String[type.length];
      for(int i=0;i<price.length;i++){
          Random rd=new Random();
          int p=rd.nextInt(10000);
          if(p<1000){
              p+=1000;
          }
          int r=rd.nextInt(1000);
          if(r<100){
              r+=100;
          }
          price[i]=""+p;
          rent[i]=""+r;
      }
      for(int i=0;i<type.length;i++){
          Products products=new Products("",price[i],type[i],rent[i],type[i],picture[i],type[i]);
          productsList.add(products);
      }
      return productsList;
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
