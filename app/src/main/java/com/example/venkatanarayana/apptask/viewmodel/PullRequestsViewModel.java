package com.example.venkatanarayana.apptask.viewmodel;


import android.view.View;

import com.example.venkatanarayana.apptask.R;
import com.example.venkatanarayana.apptask.GithubApp;
import com.example.venkatanarayana.apptask.adapter.PullRequestsAdapter;
import com.example.venkatanarayana.apptask.model.PullRequest;
import com.example.venkatanarayana.apptask.model.User;
import com.example.venkatanarayana.apptask.network.service.GetDataService;
import com.example.venkatanarayana.apptask.room.PullRequestDataSource;
import com.example.venkatanarayana.apptask.room.PullRequestEntity;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PullRequestsViewModel extends ViewModel {
    @Inject
    Retrofit retrofit;
    @Inject
    GetDataService service;
    @Inject
    PullRequestDataSource pullRequestDataSource;
    private LiveData<List<PullRequestEntity>> pullRequestList;
    private PullRequestsAdapter adapter;
    public ObservableInt loading, showEmpty;
    String user = "zulip", repo = "zulip-mobile", state = "open";
    private CompositeDisposable disposable;

    public void init() {
        GithubApp.getAppInstance().getApplicationComponent().inject(this);
        disposable = new CompositeDisposable();
        adapter = new PullRequestsAdapter(R.layout.view_pull_request, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void setPullRequestsInAdapter(List<PullRequestEntity> pullRequests) {
        this.adapter.setPullRequestList(pullRequests);
        this.adapter.notifyDataSetChanged();
    }


    public void fetchList() {
        service.getPullRequestList(user, repo, state).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).flatMapIterable(new Function<List<PullRequest>, List<PullRequest>>() {
            @Override
            public List<PullRequest> apply(List<PullRequest> pullRequestList) throws Exception {
                return pullRequestList;
            }
        }).map(new Function<PullRequest, PullRequestEntity>() {
            @Override
            public PullRequestEntity apply(PullRequest pullRequest) throws Exception {
                return new PullRequestEntity(pullRequest.getId(), pullRequest.getDescription(), pullRequest.getUser().getHandle(), pullRequest.getUser().getAvatarUrl(),pullRequest.getCreatedTime());
            }
        }).toList().subscribe(new SingleObserver<List<PullRequestEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(List<PullRequestEntity> pullRequestEntities) {
                if (pullRequestEntities == null || pullRequestEntities.size() == 0)
                    return;
                PullRequestEntity[] pullRequests = new PullRequestEntity[pullRequestEntities.size()];
                pullRequestDataSource.insertPullRequests(pullRequestEntities.toArray(pullRequests));
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    public LiveData<List<PullRequestEntity>> getPullRequests() {
        this.pullRequestList = pullRequestDataSource.getPullRequests();
        return pullRequestList;
    }

    public PullRequestsAdapter getAdapter() {
        return adapter;
    }

    public void onItemClick(Integer index) {
    }

    public PullRequestEntity getPullRequestAt(Integer index) {
        if (pullRequestList.getValue() != null &&
                index != null &&
                pullRequestList.getValue().size() > index) {
            return pullRequestList.getValue().get(index);
        }
        return null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
