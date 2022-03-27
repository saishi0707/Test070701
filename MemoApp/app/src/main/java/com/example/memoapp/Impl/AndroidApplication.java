package com.example.memoapp.Impl;

import android.app.Application;
import android.content.res.Configuration;

public class AndroidApplication extends Application {

    private static AndroidApplication application;

    // get instance
    public static synchronized AndroidApplication getInstance() {
        return application;
    }

    /**
     * Applicationクラス開始時
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    /**
     * Applicationクラス終了時
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 端末の状態が変わった時
     * (画面サイズ、画面回転)
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
