package com.example.arc.ui.source;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.api.Api;
import com.example.arc.api.DataRepository;
import com.example.arc.core.AppSchedulerProvider;
import com.example.arc.model.Source;
import com.example.arc.model.Sources;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;

/**
 * @author ihsan on 12/10/17.
 */

public class SourceViewModel extends ViewModel {

    private final LiveData<List<Source>> sourceList;
    private final Api api;
    private final AppSchedulerProvider schedulerProvider;
    private final DataRepository repository;

    @Inject
    SourceViewModel(DataRepository repository, Api api, AppSchedulerProvider schedulerProvider) {
        sourceList = repository.getAllSource();
        this.repository = repository;
        this.api = api;
        this.schedulerProvider = schedulerProvider;
    }

    void getSourceList(Observer<Sources> observer, List<Source> sourceList) {
        api.sources()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .map(sources -> {
                    if (sourceList != null) {
                        for (Source item : sources.getSources()) {
                            for (Source data : sourceList) {
                                if (item.getId().equals(data.getId())) {
                                    item.setSelected(true);
                                }
                            }
                        }
                    }
                    return sources;
                })
                .subscribe(observer);
    }

    LiveData<List<Source>> getSourceList() {
        return sourceList;
    }

    void insert(Source item) {
        repository.insertSource(item);
    }

    void delete(String id) {
        repository.deleteSource(id);
    }
}
