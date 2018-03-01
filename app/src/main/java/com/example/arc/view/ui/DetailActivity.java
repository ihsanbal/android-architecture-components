package com.example.arc.view.ui;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.MenuItem;

import com.example.arc.R;
import com.example.arc.databinding.ActivityDetailBinding;
import com.example.arc.model.data.Article;
import com.example.arc.core.base.BaseActivity;
import com.example.arc.viewmodel.DetailViewModel;

/**
 * @author ihsan on 12/28/17.
 */

public class DetailActivity extends BaseActivity<DetailViewModel, ActivityDetailBinding> {

    private static final String KEY_ITEM_ID = "item:article";

    @Override
    protected Class<DetailViewModel> getViewModel() {
        return DetailViewModel.class;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle instance, DetailViewModel viewModel, ActivityDetailBinding binding) {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        LiveData<Article> article = viewModel.getArticle(getIntent().getIntExtra(KEY_ITEM_ID, -1));
        if (article != null) {
            article.observe(this, (Article item) -> {
                binding.setArticle(item);
                if (item != null) {
                    binding.webView.loadData(item.getDescription(), "text/html", "utf-8");
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityCompat.finishAfterTransition(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, int id, ActivityOptionsCompat options) {
        Intent starter = new Intent(context, DetailActivity.class);
        starter.putExtra(KEY_ITEM_ID, id);
        context.startActivity(starter, options.toBundle());
    }
}
