package com.example.myapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.fragment.app.Fragment;

import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.example.myapp.utils.ScreenAdapter;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class FragmentMain extends Fragment {

    private MainActivity mainActivity;
    private MyHandler handler = null;
    private static final int UPDATA_TIME = 0;
    TextView tvTime;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScreenAdapter.startAdapter(mainActivity);
        View view = inflater.inflate(R.layout.fragment_main, null, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvTime = view.findViewById(R.id.btn_search);
        tvTime.setOnClickListener(v -> {});

        handler = new MyHandler(this);
        handler.sendEmptyMessage(UPDATA_TIME);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    static class MyHandler extends Handler {

        private WeakReference<FragmentMain> weakReference;

        public MyHandler(FragmentMain fragmentMain) {
            weakReference = new WeakReference<>(fragmentMain);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FragmentMain fragment = weakReference.get();
            if (fragment != null) {
                int what = msg.what;
                switch (what) {
                    case UPDATA_TIME:
                        removeMessages(UPDATA_TIME);
                        sendEmptyMessageDelayed(UPDATA_TIME, 1000);
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
                        break;
                }
            }
        }
    }
}
