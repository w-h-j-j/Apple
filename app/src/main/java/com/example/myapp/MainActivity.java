package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.adapter.MyViewPagerAdapter;
import com.example.myapp.utils.ScreenAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.vp);
        List<Fragment> list = new ArrayList<>();
        list.add(new AdapterFragment());
        list.add(new NoAdapterFragment());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list));

        RadioButton button1= findViewById(R.id.btn_adapter);
        RadioButton button2=findViewById(R.id.btn_cancel_adapter);
        button1.setChecked(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    button1.setChecked(true);
                }else {
                    button2.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        RadioGroup radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.btn_adapter:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.btn_cancel_adapter:
                    viewPager.setCurrentItem(1);
                    break;
            }
        });

    }

    public static class AdapterFragment extends Fragment{

        private MainActivity mainActivity;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            mainActivity = (MainActivity) context;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ScreenAdapter.startAdapter(mainActivity);
            return inflater.inflate(R.layout.fragment_adapter, null);
        }

        @Override
        public void onResume() {
            super.onResume();
            ScreenAdapter.cancelAdapter(mainActivity);
        }
    }

    public static class NoAdapterFragment extends Fragment{

        private MainActivity mainActivity;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            mainActivity = (MainActivity) context;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ScreenAdapter.cancelAdapter(mainActivity);
            return inflater.inflate(R.layout.fragment_no_adapter, null);
        }
    }
}