package com.example.arc.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.arc.model.data.Article;

import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */
@Dao
public interface ArticleDao {

    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Article> articles);

    @Query("DELETE FROM article")
    void clear();

    @Query("SELECT * FROM article WHERE id == :id")
    LiveData<Article> get(int id);
}
