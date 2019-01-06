package com.example.venkatanarayana.apptask.viewmodel;



import android.view.View;

import com.example.venkatanarayana.apptask.R;
import com.example.venkatanarayana.apptask.GithubApp;
import com.example.venkatanarayana.apptask.adapter.PullRequestsAdapter;
import com.example.venkatanarayana.apptask.model.PullRequest;
import com.example.venkatanarayana.apptask.network.service.GetDataService;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PullRequestsViewModel extends ViewModel {
    @Inject
    Retrofit retrofit;
    @Inject
    GetDataService service;
    private MutableLiveData<List<PullRequest>> pullRequestList;
    private PullRequestsAdapter adapter;
    public ObservableInt loading,showEmpty;
    String user = "zulip",repo = "zulip-mobile",state="open";
    private CompositeDisposable disposable;

    public void init() {
        GithubApp.getAppInstance().getApplicationComponent().inject(this);
        disposable = new CompositeDisposable();
        pullRequestList = new MutableLiveData<>();
        adapter =  new PullRequestsAdapter(R.layout.view_pull_request, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void setPullRequestsInAdapter(List<PullRequest> pullRequests) {
        this.adapter.setPullRequestList(pullRequests);
        this.adapter.notifyDataSetChanged();
    }


    public void fetchList() {
        disposable.add(service.getPullRequestList(user,repo,state).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<PullRequest>>() {
                    @Override
                    public void onSuccess(List<PullRequest> value) {
                        pullRequestList.setValue(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle
                    }
                }));
    }

    public MutableLiveData<List<PullRequest>> getPullRequests() {
        return pullRequestList;
    }

    public PullRequestsAdapter getAdapter() {
        return adapter;
    }

    public void onItemClick(Integer index) {
        PullRequest db = getPullRequestAt(index);
    }

    public PullRequest getPullRequestAt(Integer index) {
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
