package com.example.venkatanarayana.apptask.di;

import android.content.Context;

import com.example.venkatanarayana.apptask.GithubApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final GithubApp mApplication;

    public ApplicationModule(GithubApp app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    GithubApp provideApplication() {
        return mApplication;
    }


}