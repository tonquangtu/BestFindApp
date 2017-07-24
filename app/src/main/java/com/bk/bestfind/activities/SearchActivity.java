package com.bk.bestfind.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bk.bestfind.constant.AppConstant;
import com.bk.bestfind.utils.Utils;
import com.example.mrt.breakfast.R;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Dell on 24-Jun-17.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.cropImageView)
    CropImageView cropImageView;

    @Bind(R.id.btn_search)
    Button btnSearch;

    @Bind(R.id.spinner_catelogy)
    Spinner spinnerCategory;

    Uri photoUri;

    int choosePosition = 0;

    String [] categories;


    @Override
    public int setContentViewId() {
        return R.layout.search_activity;
    }

    @Override
    public void initView() {

        initToolBar();
        initSpinner();

    }

    @Override
    public void initData() {

    }

    @Override
    public void handleIntent(Intent intent) {

        Bundle bundleData = intent.getBundleExtra(AppConstant.DATA);
        photoUri = (Uri)bundleData.getParcelable(AppConstant.PHOTO_URI);
        setImageToCrop();
    }

    public void initToolBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Tìm kiếm");
    }

    public void initSpinner() {

        categories =getResources().getStringArray(R.array.categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                choosePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void setImageToCrop() {

        cropImageView.setImageUriAsync(photoUri);
    }

    @OnClick(R.id.btn_search)
    public void handleSearch(View v) {

        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

                Bitmap cropBitmap = cropImageView.getCroppedImage();
                String pathFile = Utils.savePhotoLocal(cropBitmap);
                Log.e("tu", pathFile);
                doSearch(pathFile);

            }
        });
        cropImageView.getCroppedImageAsync();
    }

    public void doSearch(String pathFile) {

        String category = "";
        if (choosePosition != 0) {
            category = categories[choosePosition];
        }

        Bundle bundleData = new Bundle();
        bundleData.putString(AppConstant.PHOTO_PATH, pathFile);
        bundleData.putString(AppConstant.CATEGORY, category);

        Intent resultIntent = new Intent(this, ResultsActivity.class);
        resultIntent.putExtra(AppConstant.DATA, bundleData);
        startActivity(resultIntent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
//        searchService.search(pathFile, searchListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id ==android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }


}
