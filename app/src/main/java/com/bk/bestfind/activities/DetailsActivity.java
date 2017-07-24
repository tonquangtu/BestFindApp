package com.bk.bestfind.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.bestfind.models.Product;
import com.example.mrt.breakfast.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {
    ImageView product_image_detail;
    Product product;

    @Bind(R.id.product_name)
    TextView txtProductName;

    @Bind(R.id.product_price)
    TextView txtProductPrice;

    @Bind(R.id.product_description)
    TextView txtProductDes;

    @Bind(R.id.shop_name)
    TextView txtShopName;

    @Bind(R.id.shop_address)
    TextView txtShopAdd;

    @Bind(R.id.shop_email)
    TextView txtShopEmail;

    @Bind(R.id.shop_phone)
    TextView txtShopPhone;


    @Override
    public int setContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        product = getIntent().getParcelableExtra("product");

        actionBar.setTitle(product.getName());
        product_image_detail = (ImageView)findViewById(R.id.product_image_detail);

        Picasso.with(this).load(product.getImage_link()).fit().centerCrop().into(product_image_detail);

    }

    @Override
    public void initData() {

        txtProductName.setText(product.getName());
        txtProductPrice.setText(product.getPrice());
        txtProductDes.setText(product.getDescription());
        txtShopName.setText(product.getShop().getName());
        txtShopAdd.setText(product.getShop().getAddress());
        txtShopEmail.setText(product.getShop().getEmail());
        txtShopPhone.setText(product.getShop().getPhone());


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


    @OnClick(R.id.button_direction)
    public void direction() {

        Uri gmmIntentUri = Uri.parse("geo:" + product.getShop().getLatitude() + "," + product.getShop().getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @OnClick(R.id.button_link)
    public void buy() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(product.getProduct_link()));
        startActivity(i);
    }
}
