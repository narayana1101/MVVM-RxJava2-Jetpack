package com.example.venkatanarayana.apptask.room;

import com.example.venkatanarayana.apptask.model.PullRequest;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface PullRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPullRequest(PullRequestEntity... pullRequests);

    @Query("DELETE FROM PullRequestEntity")
    void delete();

    @Query("SELECT * FROM PullRequestEntity ORDER BY created_at desc")
    LiveData<List<PullRequestEntity>> getPullRequests();
}
