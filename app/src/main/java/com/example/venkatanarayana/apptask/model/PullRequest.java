package com.example.venkatanarayana.apptask.model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;

public class PullRequest extends BaseObservable {

    @SerializedName("id")
    long id;

    @SerializedName("title")
    String description;

    @SerializedName("user")
    User user;

    @SerializedName("created_at")
    String createdTime;


    public String getDescription() {
        return description;
    }


    public User getUser() {
        return user;
    }


    public long getId() {
        return id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public PullRequest() {
    }

    public PullRequest(long id, String description, User user,String createdTime) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.createdTime = createdTime;
    }
}
