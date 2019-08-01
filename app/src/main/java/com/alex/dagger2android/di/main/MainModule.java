package com.alex.dagger2android.di.main;

import com.alex.dagger2android.Data.network.main.MainApi;
import com.alex.dagger2android.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostsRecyclerAdapter providePostsRecyclerAdapter(){
        return new PostsRecyclerAdapter();
    }
}
