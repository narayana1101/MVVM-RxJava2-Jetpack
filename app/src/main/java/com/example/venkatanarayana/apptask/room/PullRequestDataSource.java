package com.example.venkatanarayana.apptask.room;

import com.example.venkatanarayana.apptask.model.PullRequest;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.Flowable;

public class PullRequestDataSource {

    private final PullRequestDao pullRequestDao;

    @Inject
    public PullRequestDataSource(PullRequestDao pullRequestDao) {
        this.pullRequestDao = pullRequestDao;
    }

    public LiveData<List<PullRequestEntity>> getPullRequests() {
        return pullRequestDao.getPullRequests();
    }

    public void insertPullRequests(PullRequestEntity... pullRequests) {
        if (pullRequests == null || pullRequests.length==0)
            return;
        pullRequestDao.insertPullRequest(pullRequests);
    }

    public void deleteAllPullRequests(){
        pullRequestDao.delete();
    }
}
