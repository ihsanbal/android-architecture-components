package com.example.arc.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.arc.model.data.Source;

import java.util.List;

/**
 * @author ihsan on 12/19/17.
 */
@Dao
public interface SourceDao {

    @Query("SELECT * FROM source")
    List<Source> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Source... sources);

    @Query("DELETE FROM source WHERE news_id == :news_id")
    void delete(String news_id);
}
