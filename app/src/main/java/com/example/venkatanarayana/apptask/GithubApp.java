package com.example.venkatanarayana.apptask;

import android.app.Application;

import com.example.venkatanarayana.apptask.di.ApplicationComponent;
import com.example.venkatanarayana.apptask.di.ApplicationModule;
import com.example.venkatanarayana.apptask.di.DaggerApplicationComponent;
import com.example.venkatanarayana.apptask.di.UtilsModule;

public class GithubApp extends Application{
    protected ApplicationComponent applicationComponent;
    private static GithubApp appInstance;


    public static GithubApp getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .utilsModule(new UtilsModule())
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
