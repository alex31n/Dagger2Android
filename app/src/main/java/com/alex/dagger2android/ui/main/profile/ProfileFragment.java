package com.alex.dagger2android.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alex.dagger2android.R;
import com.alex.dagger2android.model.User;
import com.alex.dagger2android.Data.AuthResource;
import com.alex.dagger2android.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;
    private TextView tvEmail, tvUsername, tvWebsite;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ProfileFragment was created...");

        tvEmail = view.findViewById(R.id.email);
        tvUsername = view.findViewById(R.id.username);
        tvWebsite = view.findViewById(R.id.website);

        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);

        subscribeObservers();
    }


    private void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {

                    switch (userAuthResource.status) {
                        case AUTHENTICATED: {
                            setUserDetails(userAuthResource.data);
                            break;
                        }
                        case ERROR: {
                            setErrorDetails(userAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        tvEmail.setText(message);
        tvUsername.setText("error");
        tvWebsite.setText("error");
    }

    private void setUserDetails(User user) {
        tvEmail.setText(user.getEmail());
        tvUsername.setText(user.getUsername());
        tvWebsite.setText(user.getWebsite());
    }
}
