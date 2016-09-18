package com.showcase.todoapp;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;

public class TodoApplication extends Application
{   // Leak Canary refWatcher
    private RefWatcher refWatcher;

    /**
     * @param context context
     * @return LeakCanary RefWatcher instance to watch on
     */
    public static RefWatcher getRefWatcher(Context context)
    {
        TodoApplication application = (TodoApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        SupportLibs.initStetho(this);
        refWatcher = SupportLibs.getRefWatcher(this);
    }
}
