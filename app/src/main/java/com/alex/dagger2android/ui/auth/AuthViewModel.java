package com.alex.dagger2android.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.alex.dagger2android.SessionManager;
import com.alex.dagger2android.model.User;
import com.alex.dagger2android.Data.AuthResource;
import com.alex.dagger2android.Data.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";
    private AuthApi authApi;
    private SessionManager sessionManager;

    //private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;

    }


    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempting to login.");
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId){

        return LiveDataReactiveStreams
                .fromPublisher(

                        authApi.getUser(userId)

                                // instead of calling onError (error happens)
                                .onErrorReturn(new Function<Throwable, User>() {
                                    @Override
                                    public User apply(Throwable throwable) throws Exception {
                                        User errUser = new User();
                                        errUser.setId(-1);
                                        return errUser;
                                    }
                                })

                                .map(new Function<User, AuthResource<User>>() {
                                    @Override
                                    public AuthResource<User> apply(User user) throws Exception {

                                        // user not found
                                        if (user.getId() == -1) {
                                            return AuthResource.error("Could not Authenticate", (User) null);
                                        }

                                        // login authenticated
                                        return AuthResource.authenticated(user);
                                    }
                                })

                                .subscribeOn(Schedulers.io())


                );
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
