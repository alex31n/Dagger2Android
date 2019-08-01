package com.alex.dagger2android.di;

import com.alex.dagger2android.di.auth.AuthModule;
import com.alex.dagger2android.di.auth.AuthViewModelModule;
import com.alex.dagger2android.di.main.MainFragmentBuilderModule;
import com.alex.dagger2android.di.main.MainModule;
import com.alex.dagger2android.di.main.MainViewModelModule;
import com.alex.dagger2android.ui.auth.AuthActivity;
import com.alex.dagger2android.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelModule.class,
                    AuthModule.class,
            }
    )
    abstract AuthActivity contributeAuthActivity();


    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuilderModule.class,
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMainAcitivity();


}
