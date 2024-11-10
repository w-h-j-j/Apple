package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.views.FragmentMain;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    // 定义权限请求码
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    public FragmentManager fragmentManager;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIStyleManager.setUiStyle(this);
        changeFragment(new FragmentMain());
    }

    /**
     * 加载Fragment
     */
    public void changeFragment(Fragment fragment) {
        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_fragment, fragment);
        fragmentTransaction.commit();
        checkAndRequestLocationPermission();
    }

    // 检查和请求定位权限
    public void checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 权限未被授予，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            // 权限已被授予，可以进行定位操作
            Logger.i("定位权限已被授予！");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被用户允许
                Logger.i("定位权限已被授予！");
            } else {
                // 权限被用户拒绝
                Logger.e("定位权限已被拒绝！");
                handlePermissionDenied();
            }
        }
    }

    private void handlePermissionDenied() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // 之前请求过，且用户拒绝，展示解释信息
            Toast.makeText(this, "展示解释信息", Toast.LENGTH_SHORT).show();
            checkAndRequestLocationPermission();
        } else {
            // 用户选择了不再提示
            showSettingsDialog();
        }
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("权限必要")
                .setMessage("请在设置中开启位置权限。")
                .setPositiveButton("设置", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("取消", null)
                .show();
    }
}