package com.example.arc.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.core.BaseViewModel;
import com.example.arc.core.base.ArticleUtils;
import com.example.arc.model.api.Api;
import com.example.arc.model.data.Article;
import com.example.arc.model.data.Articles;
import com.example.arc.model.data.Source;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/28/17.
 */

public class DataRepository implements BaseViewModel {

    private final List<Source> dataList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private final Api api;
    private final MutableLiveData<List<Article>> listMutableLiveData;
    private final AppSchedulerProvider schedulerProvider;
    private final SourceDao sourceDao;
    private final ArticleDao articleDao;
    private final LiveData<List<Source>> sourceList;

    @Inject
    DataRepository(AppDatabase database, Api api, AppSchedulerProvider schedulerProvider) {
        this.api = api;
        this.schedulerProvider = schedulerProvider;
        sourceDao = database.sourceDao();
        articleDao = database.articleDao();
        dataList = sourceDao.getAllList();
        sourceList = sourceDao.getAll();
        listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData.postValue(articleDao.getAll());
        getArticleLiveList();
    }

    public LiveData<List<Article>> getAllArticle() {
        return listMutableLiveData;
    }

    public LiveData<List<Source>> getAllSource() {
        return sourceList;
    }

    public void insertSource(Source item) {
        sourceDao.insert(item);
    }

    public void deleteSource(String id) {
        sourceDao.delete(id);
    }

    private void getArticleLiveList() {
        api.topHeadlines(getQuery())
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(ArticleUtils::formatDate)
                .subscribe(new Observer<Articles>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Articles articles) {
                        articleDao.clear();
                        articleDao.insert(articles.getArticles());
                        listMutableLiveData.postValue(articleDao.getAll());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getQuery() {
        StringBuilder builder = new StringBuilder();
        if (dataList != null && dataList.size() > 0) {
            for (Source item : dataList) {
                builder.append(item.getId()).append(",");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        return builder.toString();
    }

    @Override
    public void onClear() {
        disposables.clear();
    }
}
