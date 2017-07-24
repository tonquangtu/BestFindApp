package com.bk.bestfind.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bk.bestfind.constant.AppConstant;
import com.bk.bestfind.models.Product;
import com.bk.bestfind.preferences.ProductsAdapter;
import com.bk.bestfind.preferences.SpacesItemDecoration;
import com.bk.bestfind.service.SearchListener;
import com.bk.bestfind.service.SearchService;
import com.example.mrt.breakfast.R;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    public static final String TAG = "ResultsActivity";
    RecyclerView products;
    ProductsAdapter adapter;
    MKLoader progressLoader;

    String pathFile;
    String category ="";

    SearchService searchService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        searchService = new SearchService();

        handleIntent();

        initView();

        searchTask.execute();

    }

    AsyncTask<Void, Void, Void> searchTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            handleSearch();
            return null;
        }
    };

    public void handleIntent(){
        Intent intent = getIntent();
        Bundle fromData = intent.getBundleExtra(AppConstant.DATA);
        pathFile = fromData.getString(AppConstant.PHOTO_PATH);
        category = fromData.getString(AppConstant.CATEGORY);
    }

    public void handleSearch() {

        if (pathFile != null) {

            searchService.search(pathFile, category, searchListener);
        }
    }

    public void initView() {
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("Danh sách sản phẩm");
        bar.setDisplayHomeAsUpEnabled(true);

        progressLoader = (MKLoader)findViewById(R.id.progress_load);

        products = (RecyclerView)findViewById(R.id.products);
        adapter = new ProductsAdapter(this);
        products.setAdapter(adapter);
        products.addItemDecoration(new SpacesItemDecoration(20));
        products.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(ResultsActivity.this, DetailsActivity.class);
                intent.putExtra("product", adapter.getData().get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ResultsActivity.this, itemView.findViewById(R.id.product_image), "detail");
                startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map_action) {
                Intent intent = new Intent(this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", (ArrayList<Product>)adapter.getData());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                return true;
        } else if (id ==android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    SearchListener searchListener = new SearchListener() {
        @Override
        public void complete(List<Product> products) {
            progressLoader.setVisibility(View.GONE);
            handleSearchResult(products);
        }

        @Override
        public void fail(Throwable t) {
            progressLoader.setVisibility(View.GONE);
            Log.e(TAG, "fail: " + t.toString());
        }
    };

    public void handleSearchResult(List<Product> products) {

        adapter.setData(products);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }


}
