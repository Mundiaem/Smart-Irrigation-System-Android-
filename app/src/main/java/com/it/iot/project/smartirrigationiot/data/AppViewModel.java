package com.it.iot.project.smartirrigationiot.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class AppViewModel extends AndroidViewModel {
    private Application mApplication;


    public AppViewModel(Application application) {
        super(application);
    }
}