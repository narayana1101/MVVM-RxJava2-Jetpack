package com.example.venkatanarayana.apptask.viewmodel;


import android.view.View;

import com.example.venkatanarayana.apptask.GithubApp;
import com.example.venkatanarayana.apptask.R;
import com.example.venkatanarayana.apptask.adapter.PullRequestsAdapter;
import com.example.venkatanarayana.apptask.model.Resource;
import com.example.venkatanarayana.apptask.repository.PullRequestRepository;
import com.example.venkatanarayana.apptask.room.PullRequestEntity;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PullRequestsViewModel extends ViewModel {

    @Inject
    PullRequestRepository pullRequestRepository;
    private LiveData<Resource<List<PullRequestEntity>>> pullRequestList;
    private PullRequestsAdapter adapter;
    public ObservableInt loading, showEmpty,recyclerViewVisible;
    String path = "square/okhttp";
    String fetch = "Fetch";
    public ObservableField<String> pathField;
    public ObservableField<String> fetchButton;
    String state = "open";
    private CompositeDisposable disposable;

    public void init() {
        GithubApp.getAppInstance().getApplicationComponent().inject(this);
        disposable = new CompositeDisposable();
        pullRequestList = new MutableLiveData<>();
        adapter = new PullRequestsAdapter(R.layout.view_pull_request, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.VISIBLE);
        pathField = new ObservableField<>(path);
        fetchButton = new ObservableField<>(fetch);

        pullRequestList = LiveDataReactiveStreams.fromPublisher(pullRequestRepository.getPullRequests()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()));
    }

    public void setPullRequestsInAdapter(List<PullRequestEntity> pullRequests) {
        this.adapter.setPullRequestList(pullRequests);
        this.adapter.notifyDataSetChanged();
    }


    public LiveData<Resource<List<PullRequestEntity>>> getPullRequests() {
        return pullRequestList;
    }

    public PullRequestsAdapter getAdapter() {
        return adapter;
    }

    public void onItemClick() {
        loading.set(View.VISIBLE);
        pullRequestRepository.fetch(pathField.get().split("/")[0], pathField.get().split("/")[1], state);
    }

    public PullRequestEntity getPullRequestAt(Integer index) {
        if (pullRequestList.getValue() != null &&
                index != null &&
                pullRequestList.getValue().getValue().size() > index) {
            return pullRequestList.getValue().getValue().get(index);
        }
        return null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
