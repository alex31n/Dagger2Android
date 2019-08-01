package com.alex.dagger2android.di.auth;

import androidx.lifecycle.ViewModel;

import com.alex.dagger2android.di.ViewModelKey;
import com.alex.dagger2android.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
