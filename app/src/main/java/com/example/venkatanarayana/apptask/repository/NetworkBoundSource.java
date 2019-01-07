package com.example.venkatanarayana.apptask.repository;

import com.example.venkatanarayana.apptask.model.Resource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundSource<LocalType, RemoteType> {

    FlowableEmitter<Resource<List<LocalType>>> emitter;
    static boolean isCached;

    public NetworkBoundSource(FlowableEmitter<Resource<List<LocalType>>> emitter) {
        this.emitter = emitter;

    }

    public void fetch(String user, String repo, String state) {

        Disposable firstDataDisposable = getLocal(user + "/" + repo)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(filter(user + "/" + repo))
                .map((List<LocalType> value) -> {
                    return value.size() > 0 ? Resource.success(value) : Resource.notStarted(value);
                })
                .subscribe(new Consumer<Resource<List<LocalType>>>() {
                    @Override
                    public void accept(Resource<List<LocalType>> listResource) throws Exception {
                        emitter.onNext(listResource);
                        isCached = true;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        isCached = false;
                    }
                });

        getRemote(user, repo, state).flatMapIterable(flatMapIterablemapper()).map(mapper(user + "/" + repo)).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(filter(user + "/" + repo))
                .subscribe(new Consumer<List<LocalType>>() {
                    @Override
                    public void accept(List<LocalType> localTypeData) throws Exception {
                        NetworkBoundSource.this.saveCallResult(localTypeData);
                        NetworkBoundSource.this.getLocal(user + "/" + repo).map(Resource::success).subscribe(new Consumer<Resource<List<LocalType>>>() {
                            @Override
                            public void accept(Resource<List<LocalType>> listResource) throws Exception {
                                emitter.onNext(listResource);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                                emitter.onNext(Resource.failed());
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        if (!isCached)
                            emitter.onNext(Resource.failed());
                    }
                });
    }


    public abstract Flowable<List<RemoteType>> getRemote(String user, String repo, String state);

    public abstract Flowable<List<LocalType>> getLocal(String path);

    public abstract void saveCallResult(List<LocalType> data);

    public abstract Function<List<RemoteType>, List<RemoteType>> flatMapIterablemapper();

    public abstract Function<RemoteType, LocalType> mapper(String path);

    public abstract Predicate<List<LocalType>> filter(String path);


}