package com.example.myapp.function.logon;

import com.example.myapp.function.Student;
import com.example.myapp.http.RetrofitManager;

public class LogonModel implements LogonContact.OnModelListener {


    @Override
    public void onLogo(Student student, LogonContact.Callback callback) {
        //RetrofitManager.getInstance().getRetrofit().
    }

    @Override
    public void onDestroy() {

    }
}
