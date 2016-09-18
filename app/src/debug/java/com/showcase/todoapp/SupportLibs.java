package com.showcase.todoapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class SupportLibs
{
    public static void initStetho(Application application)
    {
        Stetho.initializeWithDefaults(application);
    }

    public static RefWatcher getRefWatcher(Application application)
    {
        return LeakCanary.install(application);
    }
}
