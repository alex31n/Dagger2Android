package com.alex.dagger2android.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.dagger2android.Data.Resource;
import com.alex.dagger2android.R;
import com.alex.dagger2android.model.Post;
import com.alex.dagger2android.utils.VerticalSpacingItemDecoration;
import com.alex.dagger2android.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";

    private PostsViewModel viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    PostsRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this,providerFactory).get(PostsViewModel.class);

        subscribeObservers();

        initRecyclerView();
    }

    private void subscribeObservers(){
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource !=null){
                    Log.d(TAG, "onChanged: "+listResource.data);

                    switch (listResource.status){

                        case LOADING:
                            Log.d(TAG, "onChanged: LOADING...");
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: get posts...");
                            adapter.setPosts(listResource.data);
                            break;
                        case ERROR:
                            Log.e(TAG, "onChanged: ERROR... "+  listResource.message);
                            break;
                    }

                }
            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }
}
