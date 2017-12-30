package com.example.arc.api;

import android.arch.lifecycle.LiveData;

import com.example.arc.model.Article;
import com.example.arc.model.Source;
import com.example.arc.model.db.AppDatabase;
import com.example.arc.model.db.ArticleDao;
import com.example.arc.model.db.SourceDao;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/28/17.
 */

public class DataRepository {

    private final List<Source> data;
    private ArticleDao articleDao;
    private SourceDao sourceDao;

    @Inject
    DataRepository(AppDatabase database) {
        this.articleDao = database.articleDao();
        this.sourceDao = database.sourceDao();
        data = sourceDao.getAllList();
    }

    public LiveData<List<Article>> getAllArticle() {
        return articleDao.getAll();
    }

    public LiveData<List<Source>> getAllSource() {
        return sourceDao.getAll();
    }

    public void insertSource(Source item) {
        sourceDao.insert(item);
    }

    public void deleteSource(String id) {
        sourceDao.delete(id);
    }

    public void insertArticleList(List<Article> articles) {
        articleDao.insert(articles);
    }

    public void clearDao() {
        articleDao.clear();
    }

    public String getQuery() {
        StringBuilder builder = new StringBuilder();
        if (data != null && data.size() > 0) {
            for (Source item : data) {
                builder.append(item.getId()).append(",");
            }
            builder.deleteCharAt(builder.lastIndexOf(","));
        }
        return builder.toString();
    }
}
