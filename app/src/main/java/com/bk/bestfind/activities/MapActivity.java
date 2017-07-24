package com.bk.bestfind.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.bk.bestfind.models.Product;
import com.bk.bestfind.preferences.CustomInfoWindow;
import com.bk.bestfind.utils.MapUtil;
import com.example.mrt.breakfast.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {
    public static final String TAG = "MapActivity";
    List<Product> data;
    TextView address;
    Handler handler = new Handler();

    boolean get_location_success = false;
    Location myLocation;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle("Bản đồ");
        bar.setDisplayHomeAsUpEnabled(true);


        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        address = (TextView) findViewById(R.id.address);

        Bundle bundle = getIntent().getExtras();
        data = bundle.getParcelableArrayList("data");


    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng HUST = new LatLng(21.005744, 105.843348);
        CameraUpdate init = CameraUpdateFactory.newLatLngZoom(HUST, 14);
        map.moveCamera(init);
        map.setInfoWindowAdapter(new CustomInfoWindow(getLayoutInflater(), this));
        map.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MapUtil.checkGPS(MapActivity.this);
            }
        }, 300);

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(!get_location_success){
                    myLocation = location;
                    run();
                    get_location_success = true;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        address.setText((String)marker.getTag());
        Log.d(TAG, "onMarkerClick: " + marker.getTag());
        return true;
    }

    public void run(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                .bearing(90f).zoom(11f).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        int interval = 300;
        for(final Product product: data){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(product.getShop().getLatitude(),
                                    product.getShop().getLongitude()))
                            .title(product.getImage_link())
                            .snippet(product.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_gift)));
                    MapUtil.dropPinEffect(marker);
                    marker.setTag(product.getShop().getAddress());
                }
            }, interval += 150);

        }
    }
}
