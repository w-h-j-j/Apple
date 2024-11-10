package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

public class UIStyleManager {

    private static final String TAG = "UIStyleManager";

    public static int uiStyle = 0;

    /**
     * 通过字符串查找布局
     */
    public static int getLayoutIdByName(Context context, String layoutName) {
        // 将布局名称转换为资源ID的形式，例如 "main_activity" 转换为 "main_activity"
        final String packageName = context.getPackageName();
        int resourceId = context.getResources().getIdentifier(layoutName, "layout", packageName);
        if (resourceId == 0) {
            throw new IllegalArgumentException("Layout not found with name: " + layoutName);
        }
        return resourceId;
    }

    public static void setUiStyle(Activity activity) {
        WindowManager windowManager = activity.getWindow().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        //屏幕实际宽度（像素个数）
        int width = metrics.widthPixels;
        //屏幕实际高度（像素个数）
        int height = metrics.heightPixels;
        Logger.t(TAG).i("宽：" + width + "   高：" + height);
    }

}
