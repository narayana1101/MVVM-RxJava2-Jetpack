package com.example.venkatanarayana.apptask.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.venkatanarayana.apptask.GithubApp;
import com.example.venkatanarayana.apptask.network.service.GetDataService;
import com.example.venkatanarayana.apptask.viewmodel.PullRequestsViewModel;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class,UtilsModule.class,RoomModule.class})
public interface ApplicationComponent {

    void inject(GithubApp githubApp);

    void inject(PullRequestsViewModel pullRequestsViewModel);

    Context getContext();

    GithubApp getApplication();

    SharedPreferences getSharedPrefs();

    Retrofit getRetrofit();

    GetDataService getDataService();

}