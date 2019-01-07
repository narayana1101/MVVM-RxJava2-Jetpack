package com.example.venkatanarayana.apptask.room;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class PullRequestDataSource {

    private final PullRequestDao pullRequestDao;

    @Inject
    public PullRequestDataSource(PullRequestDao pullRequestDao) {
        this.pullRequestDao = pullRequestDao;
    }

    public Flowable<List<PullRequestEntity>> getPullRequests(String repo) {
        return pullRequestDao.getPullRequests(repo);
    }

    public void insertPullRequests(List<PullRequestEntity> pullRequests) {
        pullRequestDao.insertPullRequest(pullRequests);
    }

    public void deleteAllPullRequests(String repo) {
        pullRequestDao.delete(repo);
    }
}
