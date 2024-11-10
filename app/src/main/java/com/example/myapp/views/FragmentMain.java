package com.example.myapp.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class FragmentMain extends Fragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private MyHandler handler = null;
    private static final int UPDATE_TIME = 0;
    private TextView tvTime;
    private TextView tvLocation;
    private MapView mapView;
    private AMap aMap;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null, false);

        handler = new MyHandler(this);
        handler.sendEmptyMessage(UPDATE_TIME);
        tvTime = view.findViewById(R.id.btn_search);
        tvLocation = view.findViewById(R.id.tv_location);
        tvTime.setOnClickListener(this);
        initMap(view, savedInstanceState);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    private void initMap(View view, Bundle bundle){
        //设置高德地图SDK隐私合规
        MapsInitializer.updatePrivacyAgree(getContext(),true);
        MapsInitializer.updatePrivacyShow(getContext(),true,true);
        //获取地图控件引用
        mapView = view.findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(bundle);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(bundle);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 权限未被授予，请求权限
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        123);
            } else {
                // 权限已被授予，可以进行定位操作
                Logger.i("定位权限已被授予！");
                // 有权限，获取用户当前位置
                LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    // 处理定位结果
                    String info = "纬度: "+latitude+"\n"+"经度: "+longitude;
                    tvLocation.setText(info);
                }
            }
        }
    }

    static class MyHandler extends Handler {

        private final WeakReference<FragmentMain> weakReference;

        public MyHandler(FragmentMain fragmentMain) {
            weakReference = new WeakReference<>(fragmentMain);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FragmentMain fragment = weakReference.get();
            if (fragment != null) {
                int what = msg.what;
                if (what == UPDATE_TIME) {
                    removeMessages(UPDATE_TIME);
                    sendEmptyMessageDelayed(UPDATE_TIME, 1000);
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);

                    @SuppressLint("DefaultLocale") String hourStr = String.format("%02d", hour);
                    @SuppressLint("DefaultLocale") String minStr = String.format("%02d", minute);
                    @SuppressLint("DefaultLocale") String secStr = String.format("%02d", second);

                    String value = hourStr + " : " + minStr + " : " + secStr;
                    Log.i("实时时间： " + value, "");

                    fragment.tvTime.setText(value);
                }
            }
        }
    }
}
