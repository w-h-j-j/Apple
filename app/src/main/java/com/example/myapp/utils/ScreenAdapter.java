package com.example.myapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * 屏幕适配
 */
public class ScreenAdapter {

    //宽度1080是按照UI设计图纸来填写，可根据实际情况进行修改， 例如图纸是按照1200x2000来设计的，此处的width就填写成1200
    private static final float width = 1080;
    private static float textDensity = 0;
    private static float textScaleDensity = 0;

    /**
     * 开始适配
     */
    public static void startAdapter(Activity activity) {
        Application application = activity.getApplication();
        DisplayMetrics metrics = application.getResources().getDisplayMetrics();
        System.out.println("ScreenAdapter 屏幕  width: " + metrics.widthPixels + "    height: " + metrics.heightPixels);
        if (textDensity == 0) {
            textDensity = metrics.density;
            textScaleDensity = metrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig.fontScale > 0) {
                        textScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = metrics.widthPixels / width;
        float targetScaleDensity = targetDensity * (textScaleDensity / textDensity);
        int targetDpi = (int) (160 * targetDensity);

        metrics.density = targetDensity;
        metrics.scaledDensity = targetScaleDensity;
        metrics.densityDpi = targetDpi;

        DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();
        activityMetrics.density = targetDensity;
        activityMetrics.scaledDensity = targetScaleDensity;
        activityMetrics.densityDpi = targetDpi;

    }

    /**
     * 取消适配
     */
    public static void cancelAdapter(Activity activity) {
        DisplayMetrics systemMetrics = Resources.getSystem().getDisplayMetrics();
        DisplayMetrics appMetrics = activity.getApplication().getResources().getDisplayMetrics();
        DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();

        activityMetrics.density = systemMetrics.density;
        activityMetrics.scaledDensity = systemMetrics.scaledDensity;
        activityMetrics.densityDpi = systemMetrics.densityDpi;

        appMetrics.density = systemMetrics.density;
        appMetrics.scaledDensity = systemMetrics.scaledDensity;
        appMetrics.densityDpi = systemMetrics.densityDpi;
    }

}
