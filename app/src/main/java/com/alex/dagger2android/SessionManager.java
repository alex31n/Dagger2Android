package com.alex.dagger2android;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.alex.dagger2android.model.User;
import com.alex.dagger2android.Data.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResource<User>> user){
        if (this.cachedUser !=null){
            this.cachedUser.setValue(AuthResource.loading((User) null));

            this.cachedUser.addSource(user, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(user);
                }
            });
        }/*else{
            Log.d(TAG, "authenticateWithId:previous session detected. Retrieving user from cache.");
        }*/
    }

    public void logOut(){
        Log.d(TAG, "logOut: user logout...");
        cachedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUser;
    }
}
