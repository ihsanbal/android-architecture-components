package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.data.Article;
import com.example.arc.model.db.AppDatabase;
import com.example.arc.model.db.ArticleDao;

import javax.inject.Inject;

/**
 * @author ihsan on 12/28/17.
 */

public class DetailViewModel extends ViewModel {

    private final ArticleDao articleDao;

    @Inject
    DetailViewModel(AppDatabase database) {
        this.articleDao = database.articleDao();
    }

    public LiveData<Article> getArticle(int id) {
        return articleDao.get(id);
    }
}