package com.example.arc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.arc.model.data.Source;
import com.example.arc.model.db.DataRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * @author ihsan on 12/10/17.
 */

public class SourceViewModel extends ViewModel {

    private final DataRepository repository;
    private final LiveData<List<Source>> sourceList;

    @Inject
    SourceViewModel(DataRepository repository) {
        this.repository = repository;
        sourceList = repository.getSourceLiveList();
    }

    public LiveData<List<Source>> getSourceList() {
        return sourceList;
    }

    public void insert(Source item) {
        repository.insertSource(item);
    }

    public void delete(String id) {
        repository.deleteSource(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.onClear();
    }
}
