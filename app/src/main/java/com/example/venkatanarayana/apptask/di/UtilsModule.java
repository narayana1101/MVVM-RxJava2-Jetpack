package com.example.venkatanarayana.apptask.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.venkatanarayana.apptask.constants.APIConstants;
import com.example.venkatanarayana.apptask.network.service.GetDataService;
import com.example.venkatanarayana.apptask.repository.PullRequestRepository;
import com.example.venkatanarayana.apptask.room.PullRequestDataSource;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitInstance() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    GetDataService provideDataService(Retrofit retrofit) {
        return retrofit.create(GetDataService.class);
    }

    @Provides
    @Singleton
    Picasso providePicasso(Context context) {
        Picasso picasso = new Picasso.Builder(context).build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }

    @Provides
    @Singleton
    PullRequestRepository providePullRequestRepository(GetDataService service, PullRequestDataSource pullRequestDataSource) {
        return new PullRequestRepository(service, pullRequestDataSource);
    }
}
