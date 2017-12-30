package com.example.arc.di;

import com.example.arc.ui.detail.DetailActivity;
import com.example.arc.ui.main.MainActivity;
import com.example.arc.ui.source.SourcesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author ihsan on 12/2/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SourcesActivity bindSourceActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract DetailActivity bindDetailActivity();

}
