package com.example.venkatanarayana.apptask.model;

import com.google.gson.annotations.SerializedName;

import androidx.databinding.BaseObservable;

public class User extends BaseObservable {
@SerializedName("login")
    String handle;

    @SerializedName("avatar_url")
    String avatarUrl;

    public String getHandle() {
        return handle;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
