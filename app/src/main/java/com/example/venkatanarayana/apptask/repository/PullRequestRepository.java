package com.example.venkatanarayana.apptask.repository;

import com.example.venkatanarayana.apptask.model.PullRequest;
import com.example.venkatanarayana.apptask.model.Resource;
import com.example.venkatanarayana.apptask.network.service.GetDataService;
import com.example.venkatanarayana.apptask.room.PullRequestDataSource;
import com.example.venkatanarayana.apptask.room.PullRequestEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class PullRequestRepository {

    GetDataService service;
    PullRequestDataSource pullRequestDataSource;
    NetworkBoundSource<PullRequestEntity, PullRequest> networkBoundSource;

    @Inject
    public PullRequestRepository(GetDataService service, PullRequestDataSource pullRequestDataSource) {
        this.pullRequestDataSource = pullRequestDataSource;
        this.service = service;
    }

    public void fetch(final String user, final String repo, String state) {
        networkBoundSource.fetch(user, repo, state);
    }


    public Flowable<Resource<List<PullRequestEntity>>> getPullRequests() {
        return Flowable.create(new FlowableOnSubscribe<Resource<List<PullRequestEntity>>>() {
            @Override
            public void subscribe(FlowableEmitter<Resource<List<PullRequestEntity>>> emitter) throws Exception {
                networkBoundSource = new NetworkBoundSource<PullRequestEntity, PullRequest>(emitter) {

                    @Override
                    public Flowable<List<PullRequest>> getRemote(String user, String repo, String state) {
                        return service.getPullRequestList(user, repo, state);
                    }

                    @Override
                    public Flowable<List<PullRequestEntity>> getLocal(String path) {
                        return pullRequestDataSource.getPullRequests(path);
                    }

                    @Override
                    public void saveCallResult(List<PullRequestEntity> data) {
                        pullRequestDataSource.insertPullRequests(data);
                    }

                    @Override
                    public Function<List<PullRequest>, List<PullRequest>> flatMapIterablemapper() {
                        return new Function<List<PullRequest>, List<PullRequest>>() {
                            @Override
                            public List<PullRequest> apply(List<PullRequest> pullRequestList) throws Exception {
                                return pullRequestList;
                            }
                        };
                    }

                    @Override
                    public Function<PullRequest, PullRequestEntity> mapper(String path) {
                        return new Function<PullRequest, PullRequestEntity>() {
                            @Override
                            public PullRequestEntity apply(PullRequest pullRequest) throws Exception {
                                return new PullRequestEntity(pullRequest.getId(), pullRequest.getDescription(), pullRequest.getUser().getHandle(), pullRequest.getUser().getAvatarUrl(), pullRequest.getCreatedTime(), path);
                            }
                        };
                    }

                    @Override
                    public Predicate<List<PullRequestEntity>> filter(String path) {
                        return new Predicate<List<PullRequestEntity>>() {
                            @Override
                            public boolean test(List<PullRequestEntity> localTypes) throws Exception {
                                return path.equals(localTypes.get(0).getRepo()) ? true : false;
                            }
                        };
                    }
                };
            }
        }, BackpressureStrategy.BUFFER);
    }
}




