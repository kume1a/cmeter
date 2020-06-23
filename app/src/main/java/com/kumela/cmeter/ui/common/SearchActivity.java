package com.kumela.cmeter.ui.common;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.kumela.cmeter.R;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    public static final String EXTRA_SEARCH_QUERY = "EXTRA_SEARCH_QUERY";

    private String previousQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
            searchView.setSearchableInfo(searchableInfo);
            searchView.setIconified(false);

            searchView.setMaxWidth(Integer.MAX_VALUE);

            ImageView ivClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            ivClose.setImageResource(R.drawable.ic_close);

            TextView tvSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            tvSearch.setTextColor(getResources().getColor(R.color.colorAccent));
            tvSearch.setHintTextColor(getResources().getColor(R.color.colorAccent));


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "onQueryTextSubmit: search query: " + query);
                    searchView.clearFocus();
                    getIntent().putExtra(EXTRA_SEARCH_QUERY, query);
                    setResult(Activity.RESULT_OK, getIntent());
                    finish();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > previousQuery.length()) {

                    }

                    previousQuery = newText;
                    return false;
                }
            });

            searchView.setOnCloseListener(() -> {
                finish();
                return true;
            });
        }
        return true;
    }
}