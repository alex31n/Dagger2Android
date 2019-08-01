package com.alex.dagger2android.di.main;

import com.alex.dagger2android.ui.main.posts.PostsFragment;
import com.alex.dagger2android.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();


}
