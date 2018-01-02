package com.example.arc.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.arc.R;
import com.example.arc.view.adapter.SourcesAdapter;
import com.example.arc.databinding.ActivitySourcesBinding;
import com.example.arc.model.data.Source;
import com.example.arc.model.data.Sources;
import com.example.arc.view.BaseBindingActivity;
import com.example.arc.viewmodel.SourceViewModel;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author ihsan on 12/3/17.
 */

public class SourcesActivity extends BaseBindingActivity<SourceViewModel, ActivitySourcesBinding> implements SourcesAdapter.ItemSelectedListener {

    private SourcesAdapter mAdapter;
    private int count;
    private Snackbar snackBar;
    private SourceViewModel viewModel;

    @Override
    protected Class<SourceViewModel> getViewModel() {
        return SourceViewModel.class;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sources;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SourcesActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@NonNull Bundle instance, SourceViewModel viewModel, ActivitySourcesBinding binding) {
        mAdapter = new SourcesAdapter();
        mAdapter.setItemSelectedListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(mAdapter);
        initSnackBar(binding.coordinatorLayout);
        this.viewModel = viewModel;
        viewModel.getSourceList().observe(this, sources -> viewModel.getSourceList(new Observer<Sources>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Sources sources) {
                mAdapter.setData((ArrayList<Source>) sources.getSources());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, sources));
    }

    private void initSnackBar(View root) {
        snackBar = Snackbar.make(root, "Let\'s check your feed", Snackbar.LENGTH_INDEFINITE).setAction("Start",
                view -> startActivity(new Intent(SourcesActivity.this, MainActivity.class)));
    }

    @Override
    public void onItemSelected(Source item) {
        if (item.isSelected()) {
            viewModel.insert(item);
            count++;
        } else {
            viewModel.delete(item.getId());
            count--;
        }
        if (count == 2) {
            snackBar.show();
        } else if (count < 2 && snackBar != null && snackBar.isShown()) {
            snackBar.dismiss();
        }
    }
}
