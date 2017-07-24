package com.bk.bestfind.preferences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrt.breakfast.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

/**
 * Created by mrt on 24/06/2017.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter{
    private LayoutInflater inflater;
    private Context context;
    public CustomInfoWindow(LayoutInflater inflater, Context context){
        this.context = context;
        this.inflater = inflater;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        // Getting view
        View v = inflater.inflate(R.layout.custom_info_window, null);

        ImageView product_image = (ImageView)v.findViewById(R.id.product_image);
        TextView product_name = (TextView)v.findViewById(R.id.product_name);

        Picasso.with(context).load(marker.getTitle()).into(product_image);
        product_name.setText(marker.getSnippet());

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
