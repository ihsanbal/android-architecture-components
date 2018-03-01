package com.example.arc.view.ui;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.arc.R;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.databinding.ActivityMainBinding;
import com.example.arc.model.data.Article;
import com.example.arc.view.adapter.NewsAdapter;
import com.example.arc.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * @author ihsan on 12/19/17.
 */

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements Toolbar.OnMenuItemClickListener, NewsAdapter.ItemSelectedListener {

    private NewsAdapter mAdapter;

    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle instance, MainViewModel viewModel, ActivityMainBinding binding) {
        mAdapter = new NewsAdapter();
        mAdapter.setOnItemClickListener(this);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        binding.recyclerView.setAdapter(mAdapter);
        binding.toolbar.setTitle(R.string.str_feed);
        binding.toolbar.inflateMenu(R.menu.main_menu);
        binding.toolbar.setOnMenuItemClickListener(this);
        init(viewModel);
    }

    private void init(MainViewModel viewModel) {
        viewModel.getArticleList().observe(this, articles -> mAdapter.setData((ArrayList<Article>) articles));
        viewModel.getSourceList().observe(this, sources -> {
            if (sources == null || sources.size() < 1) {
                SourcesActivity.start(this);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        SourcesActivity.start(this);
        return false;
    }

    @Override
    public void onItemSelected(View view, Article item) {
        View viewAnimation = view.findViewById(R.id.imageView);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, viewAnimation, getString(R.string.trans_shared_image));
        DetailActivity.start(this, item.getId(), options);
    }
}
