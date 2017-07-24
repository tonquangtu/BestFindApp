package com.bk.bestfind.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by mrt on 24/06/2017.
 */

public class MapUtil {

    public static void dropPinEffect(final Marker marker) {

        // Handler allows us to repeat a code block after a specified delay
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final Interpolator interpolator = new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;

                // Calculate t for bounce based on elapsed time
                float t = Math.max(1 - interpolator.getInterpolation((float)elapsed/duration)
                        , 0);

                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else {
                    // done elapsing, show window
                    //marker.showInfoWindow();
                }
            }
        });

    }


    public static void checkGPS(final Context context){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) { }


        if(!gps_enabled) {

            // notify user
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Bạn có muốn bật GPS không?");
            builder.setMessage("Bật GPS cho phép bạn tìm thấy những sản phẩm xung quanh vị trí của mình.");
            builder.setPositiveButton("Bật"
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
                        }
                    });
            builder.setNegativeButton("Hủy",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            paramDialogInterface.cancel();
                        }
                    });
            builder.create().show();
            Log.d("test", "checkGPS: ");
        }

    }
}
