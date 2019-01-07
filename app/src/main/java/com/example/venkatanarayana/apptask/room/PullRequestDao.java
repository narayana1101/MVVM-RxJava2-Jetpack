package com.example.venkatanarayana.apptask.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface PullRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPullRequest(List<PullRequestEntity> pullRequests);

    @Query("DELETE FROM PullRequestEntity WHERE repo = :repo")
    void delete(String repo);

    @Query("SELECT * FROM PullRequestEntity WHERE repo = :repo ORDER BY created_at desc")
    Flowable<List<PullRequestEntity>> getPullRequests(String repo);
}
