package com.showcase.todoapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utility
{
    private Utility()
    {
    }

    public static String getDate(Calendar calendar)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(calendar.getTime());
    }

    public static float dpToPx(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


}
