package com.spbstu.application.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

public class App extends Application implements LifecycleObserver {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public static App getInstance() {
        return instance;
    }

    public static void toast(@StringRes final int resId) {
        Toast.makeText(instance.getApplicationContext(), resId, Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        ((NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
    }
}
