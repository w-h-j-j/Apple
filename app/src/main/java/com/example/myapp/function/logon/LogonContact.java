package com.example.myapp.function.logon;

import com.example.myapp.function.Student;

public interface LogonContact {

    interface Callback{
        void onSuccess(Student student);
        void onFair(String message);
    }

    interface OnModelListener{
        void onLogo(Student student, Callback callback);
        void onDestroy();
    }

    interface OnPresenterListener{
        void onLogo(Student student);
        void onDestroy();
    }

    interface OnViewListener{
        void onStartRequest();
        void onSuccess(Student student);
        void onFair(String message);
    }

}
