package com.example.venkatanarayana.apptask.room;

import android.support.annotation.NonNull;

import com.example.venkatanarayana.apptask.model.User;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PullRequestEntity")
public class PullRequestEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "pr_id")
    long id;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "handle")
    String handle;

    @ColumnInfo(name = "avatar_url")
    String imageUrl;

    @ColumnInfo(name = "created_at")
    String createdTime;


    public PullRequestEntity(long id, String title,String handle, String imageUrl,String createdTime) {
        this.id = id;
        this.title = title;
        this.handle=handle;
        this.imageUrl=imageUrl;
        this.createdTime =createdTime;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHandle() {
        return handle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
