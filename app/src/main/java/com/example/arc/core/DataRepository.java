package com.example.arc.core;

import android.arch.lifecycle.LiveData;

import com.bumptech.glide.load.engine.Resource;
import com.example.arc.api.Api;
import com.example.arc.model.Source;
import com.example.arc.model.db.SourceDao;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/27/17.
 */

public class DataRepository {
    private final Api api;
    private final SourceDao dao;

    @Inject
    public DataRepository(Api api, SourceDao dao) {
        this.api = api;
        this.dao = dao;
    }

    public LiveData<Resource<List<Source>>> loadPopularMovies() {
        return null;
    }

}
