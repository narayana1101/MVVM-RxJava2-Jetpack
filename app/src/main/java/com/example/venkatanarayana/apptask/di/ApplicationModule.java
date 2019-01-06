package com.example.venkatanarayana.apptask.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.venkatanarayana.apptask.GithubApp;
import com.example.venkatanarayana.apptask.constants.APIConstants;
import com.example.venkatanarayana.apptask.network.service.GetDataService;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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