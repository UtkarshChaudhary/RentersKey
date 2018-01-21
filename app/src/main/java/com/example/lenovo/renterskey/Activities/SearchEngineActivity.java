package com.example.lenovo.renterskey.Activities;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.renterskey.ExtraClasses.SearchEngineAdapter;
import com.example.lenovo.renterskey.R;
import com.example.lenovo.renterskey.db.ProductDao;
import com.example.lenovo.renterskey.db.RentersKeyDb;
import com.example.lenovo.renterskey.vo.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SimpleOnSearchActionListener;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

public class SearchEngineActivity extends AppCompatActivity {

    private List<String> listTobeShown;
    private List<String> productsIdList;
    private List<Products> productList;
    private List<String> suggestion;
    private ProductDao productDao;
    private SearchEngineAdapter searchAdapter;
    private MaterialSearchBar searchBar;
    private boolean suggestionAsListToBeShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        suggestionAsListToBeShown=true;
        productDao=RentersKeyDb.getINSTANCE(this).productDao();
        suggestion=new ArrayList<>();
        Collections.addAll(suggestion, getApplicationContext().getResources().getStringArray(R.array.products_type));
        productList=new ArrayList<>();
        try {
            productList= new AsyncTask<Void, Void, List<Products>>() {
                @Override
                protected List<Products> doInBackground(Void... voids) {
                    return productDao.loadAllProducts();
                }
           }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        listTobeShown=new ArrayList<>();
        productsIdList=new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine);
        searchBar=(MaterialSearchBar) findViewById(R.id.search_view_SearchEngine);
        searchBar.setHint(this.getResources().getString(R.string.search_title));
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        searchBar.enableSearch();
        searchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {

            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });


        searchBar.setOnSearchActionListener(new SimpleOnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                super.onSearchStateChanged(enabled);
                if(!enabled){
                    onBackPressed();
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                super.onSearchConfirmed(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {
                super.onButtonClicked(buttonCode);

            }
        });

        searchAdapter=new SearchEngineAdapter(inflater);
        searchAdapter.setSuggestions(suggestion);
        searchBar.setCustomSuggestionAdapter(searchAdapter);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 String query=charSequence.toString().trim().toLowerCase();
                 if(query==null||query.isEmpty()){
                     suggestionAsListToBeShown=true;
                     searchAdapter.setSuggestions(suggestion);
                     searchAdapter.notifyDataSetChanged();
                 }else{
                     doMySearch(query);
                     suggestionAsListToBeShown=false;
                     searchAdapter.setSuggestions(listTobeShown);
                     searchAdapter.notifyDataSetChanged();
                 }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void doMySearch(String query) {
        listTobeShown.clear();;
        productsIdList.clear();

        if(productList!=null&&!productList.isEmpty()) {
            for (int i = 0;i<productList.size();i++){
                if(productList.get(i).productName!=null&&productList.get(i).productName.toLowerCase().contains(query)){
                    listTobeShown.add(productList.get(i).productName);
                    productsIdList.add(productList.get(i).productId);
                }
                if(productList.get(i).category!=null&&productList.get(i).category.toLowerCase().contains(query)&&!listTobeShown.contains(productList.get(i).category)){
                    listTobeShown.add(productList.get(i).category);
                    productsIdList.add(productList.get(i).productId);
                }
                if(productList.get(i).description!=null&&productList.get(i).description.toLowerCase().contains(query)){
                    listTobeShown.add(productList.get(i).description);
                    productsIdList.add(productList.get(i).productId);
                }
                if(productList.get(i).type!=null&&productList.get(i).type.toLowerCase().contains(query)&&!listTobeShown.contains(productList.get(i).type)){
                    listTobeShown.add(productList.get(i).type);
                    productsIdList.add(productList.get(i).productId);
                }
            }

            if(listTobeShown==null||listTobeShown.isEmpty()){
               listTobeShown.add("No Match Found");
            }

        }
    }



}
//to set text in search view
//  searchView.setQuery(searchToken, false);