package com.example.arc.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivitySourcesBinding;
import com.example.arc.model.data.Source;
import com.example.arc.view.adapter.SourcesAdapter;
import com.example.arc.viewmodel.SourceViewModel;

import java.util.ArrayList;

/**
 * @author ihsan on 12/3/17.
 */

public class SourcesActivity extends BaseActivity<SourceViewModel, ActivitySourcesBinding> implements SourcesAdapter.ItemSelectedListener {

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
        viewModel.getSourceList().observe(this, sources1 -> mAdapter.setData((ArrayList<Source>) sources1));
    }

    private void initSnackBar(View root) {
        snackBar = Snackbar.make(root, "Let\'s check your feed", Snackbar.LENGTH_INDEFINITE).setAction("Start",
                view -> finish());
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
        if (count >= 1) {
            snackBar.show();
        } else if (count < 1 && snackBar != null && snackBar.isShown()) {
            snackBar.dismiss();
        }
    }
}
