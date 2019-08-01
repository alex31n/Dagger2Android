package com.alex.dagger2android.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.dagger2android.R;
import com.alex.dagger2android.model.User;
import com.alex.dagger2android.Data.AuthResource;
import com.alex.dagger2android.ui.main.MainActivity;
import com.alex.dagger2android.viewmodel.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {
    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText userId;
    private ProgressBar progressBar;

    @Inject
    String someString;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        //Log.d(TAG, "onCreate: "+someString);
        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        setLogo();

        subscribeObservers();

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userId.getText().toString())) {
            return;
        }

        viewModel.authenticateWithId(
                Integer.parseInt(userId.getText().toString())
        );
    }

    private void subscribeObservers() {
        /*viewModel.observerUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user !=null ){
                    Log.d(TAG, "onChanged: "+user.getEmail());
                }
            }
        });*/

        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS "+ userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }

                        case ERROR:{
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message
                                    +"\nPlease enter a number between 1 to 10", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case NOT_AUTHENTICATED:{
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showProgressBar(boolean isVisible){
        progressBar.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }
}
