package com.bk.bestfind.application;

import android.app.Application;
import android.content.Context;


/**
 * Created by Hado on 30-Jul-16.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    private static Context mContext;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        //printHashKey();
    }

    public static Context getContext() {
        return mContext;
    }

}
