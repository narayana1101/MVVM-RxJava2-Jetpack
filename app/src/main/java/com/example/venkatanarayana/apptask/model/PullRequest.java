package com.example.venkatanarayana.apptask.model;


import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;

public class PullRequest extends BaseObservable {
    @SerializedName("title")
    String description;

    @SerializedName("user")
    User user;

    @SerializedName("updated_at")
    String updatedTime;

    public String getDescription() {
        return description;
    }


    public User getUser() {
        return user;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }
}
