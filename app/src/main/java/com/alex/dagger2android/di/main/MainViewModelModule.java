package com.alex.dagger2android.di.main;

import androidx.lifecycle.ViewModel;

import com.alex.dagger2android.di.ViewModelKey;
import com.alex.dagger2android.ui.main.posts.PostsViewModel;
import com.alex.dagger2android.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);


}
