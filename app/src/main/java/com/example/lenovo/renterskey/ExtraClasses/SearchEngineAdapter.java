package com.example.lenovo.renterskey.ExtraClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.renterskey.R;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

/**
 * Created by lenovo on 17-01-2018.
 */

public class SearchEngineAdapter extends SuggestionsAdapter<String,SearchEngineAdapter.SuggestionHolder> {


    public SearchEngineAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void onBindSuggestionHolder(String suggestion, SuggestionHolder holder, int position) {
     holder.title.setText(suggestion);
    }

    @Override
    public int getSingleViewHeight() {
        return 80;
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=getLayoutInflater().inflate(R.layout.search_engine_list_item,parent,false);
        return new SuggestionHolder(view);
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
     protected TextView title;

        public SuggestionHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.listText);
        }
    }
}
