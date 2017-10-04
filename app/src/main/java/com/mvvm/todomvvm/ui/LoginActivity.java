package com.mvvm.todomvvm.ui;

import android.Manifest;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.persistence.room.EmptyResultSetException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mvvm.todomvvm.DemoApplication;
import com.mvvm.todomvvm.MainActivity;
import com.mvvm.todomvvm.di.component.DaggerLoginActivityComponent;
import com.mvvm.todomvvm.di.component.LoginActivityComponent;
import com.mvvm.todomvvm.di.module.ActivityModule;
import com.mvvm.todomvvm.utils.DataManager;
import com.mvvm.todomvvm.utils.SecureUtils;
import com.mvvm.todomvvm.viewmodel.LoginViewModel;

import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.single.SingleZipArray;
import io.reactivex.schedulers.Schedulers;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;


public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_TAKE_PHOTO = 1;
    private static final int CAMERA_REQUEST = 2;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginActivityComponent activityComponent;


    @Inject
    View view;
    @Inject
    LoginViewModel viewModel;
    @Inject
    LifecycleOwner lifecycleOwner;
    @Inject
    DataManager dataManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(view);



        viewModel.loginButton.setOnClickListener(view -> {
            String email = viewModel.emailEditText.getText().toString();
            String password = viewModel.passwordEditText.getText().toString();


            dataManager.loggIn(email, password).subscribe(new Observer<Boolean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        dataManager.setLoggedIn(true);
                        dataManager.setUserEmail(email);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof EmptyResultSetException) {
                        Toast.makeText(LoginActivity.this, "User doesn't exist", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onComplete() {

                }
            });
        });

        viewModel.registerTextView.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });


    }


    public LoginActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerLoginActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }
 

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}
