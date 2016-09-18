package com.showcase.todoapp;

import android.app.Application;

import com.squareup.leakcanary.RefWatcher;

public class SupportLibs
{
    public static void initStetho(Application application)
    {
    }

    public static RefWatcher getRefWatcher(Application application)
    {
        return RefWatcher.DISABLED;
    }
}
