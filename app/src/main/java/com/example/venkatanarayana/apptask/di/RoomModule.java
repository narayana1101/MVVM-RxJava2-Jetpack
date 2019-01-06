package com.example.venkatanarayana.apptask.di;

import android.content.Context;

import com.example.venkatanarayana.apptask.room.PullRequestDao;
import com.example.venkatanarayana.apptask.room.PullRequestDataSource;
import com.example.venkatanarayana.apptask.room.PullRequestsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Provides
    @Singleton
    PullRequestDao providePullRequestDao(Context context) {
        return PullRequestsDatabase.getInstance(context).pullRequestDao();
    }

    @Provides
    @Singleton
    PullRequestDataSource provideDataSource(PullRequestDao pullRequestDao) {
        return new PullRequestDataSource(pullRequestDao);
    }

}