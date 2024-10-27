package com.example.myapp.function.logon;

import com.example.myapp.function.Student;

import java.lang.ref.WeakReference;

public class LogonPresenter implements LogonContact.OnPresenterListener{

    private WeakReference<LogonContact.OnViewListener> reference;
    private LogonModel model;

    public LogonPresenter(LogonContact.OnViewListener listener){
        reference = new WeakReference<>(listener);
        model= new LogonModel();
    }

    @Override
    public void onLogo(Student student) {
        LogonContact.OnViewListener listener = reference.get();
        if (listener!=null) listener.onStartRequest();
        model.onLogo(student, new LogonContact.Callback() {
            @Override
            public void onSuccess(Student student) {
                if (listener!=null) listener.onSuccess(student);
            }

            @Override
            public void onFair(String message) {
                if (listener!=null) listener.onFair(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        reference = null;
        model.onDestroy();
    }
}
