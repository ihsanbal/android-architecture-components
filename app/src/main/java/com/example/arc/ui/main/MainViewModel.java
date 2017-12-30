package com.example.arc.ui.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.arc.api.Api;
import com.example.arc.api.DataRepository;
import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.model.Article;
import com.example.arc.model.Articles;
import com.example.arc.model.Source;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/27/17.
 */

public class MainViewModel extends ViewModel implements LifecycleOwner {

    private final LiveData<List<Article>> articleList;
    private final LiveData<List<Source>> sourceList;
    private final Api api;
    private final AppSchedulerProvider schedulerProvider;
    private final DataRepository repository;
    private final MutableLiveData<List<Article>> listMutableLiveData;

    @Inject
    MainViewModel(DataRepository repository, Api api, AppSchedulerProvider schedulerProvider) {
        articleList = repository.getAllArticle();
        sourceList = repository.getAllSource();
        this.repository = repository;
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        listMutableLiveData = new MutableLiveData<>();
        articleList.observe(this, listMutableLiveData::setValue);
    }

    LiveData<List<Article>> getArticleList() {
        return articleList;
    }

    LiveData<List<Source>> getSourceList() {
        return sourceList;
    }

    LiveData<List<Article>> getArticleLiveList() {
        api.topHeadlines(repository.getQuery())
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(articles -> {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                            Locale.getDefault());
                    for (Article article : articles.getArticles()) {
                        if (!TextUtils.isEmpty(article.getPublishedAt())) {
                            Date date = simpleDateFormat.parse(article.getPublishedAt());
                            article.setPublishedAt(new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date));
                        }
                    }
                    return articles;
                })
                .subscribe(new Observer<Articles>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Articles articles) {
                        repository.clearDao();
                        repository.insertArticleList(articles.getArticles());
                        listMutableLiveData.postValue(articles.getArticles());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return listMutableLiveData;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return new Lifecycle() {
            @Override
            public void addObserver(@NonNull LifecycleObserver observer) {

            }

            @Override
            public void removeObserver(@NonNull LifecycleObserver observer) {

            }

            @Override
            public State getCurrentState() {
                return State.CREATED;
            }
        };
    }
}
