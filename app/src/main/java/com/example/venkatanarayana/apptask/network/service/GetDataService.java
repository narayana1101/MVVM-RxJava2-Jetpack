package com.example.venkatanarayana.apptask.network.service;

import com.example.venkatanarayana.apptask.model.PullRequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {


    @GET("repos/{user}/{repo}/pulls")
    Single<List<PullRequest>> getPullRequestList(@Path("user") String user, @Path("repo") String repo, @Query("state") String state);

}