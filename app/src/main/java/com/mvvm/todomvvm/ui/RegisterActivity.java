package com.mvvm.todomvvm.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mvvm.todomvvm.DemoApplication;
import com.mvvm.todomvvm.MainActivity;
import com.mvvm.todomvvm.di.component.DaggerRegisterActivityComponent;
import com.mvvm.todomvvm.di.component.RegisterActivityComponent;
import com.mvvm.todomvvm.di.module.ActivityModule;
import com.mvvm.todomvvm.model.User;
import com.mvvm.todomvvm.utils.DataManager;
import com.mvvm.todomvvm.utils.SecureUtils;
import com.mvvm.todomvvm.viewmodel.RegisterViewModel;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;


public class RegisterActivity extends AppCompatActivity {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private RegisterActivityComponent activityComponent;

    @Inject
    View view;
    @Inject
    RegisterViewModel viewModel;
    @Inject
    LifecycleOwner lifecycleOwner;
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(view);

        viewModel.registerButton.setOnClickListener(view -> {
            String firstName = viewModel.firstNameEditText.getText().toString();
            String lastName = viewModel.lastNameEditText.getText().toString();
            String email = viewModel.emailEditText.getText().toString();
            String password = viewModel.passwordEditText.getText().toString();
            String repeatPassword = viewModel.repeatPasswordEditText.getText().toString();

            String salt = SecureUtils.randomString(15);
            if (password.equals(repeatPassword)) {

                dataManager.insertUser(new User(firstName, lastName, email, SecureUtils.sha512(password, salt), salt)).subscribe(new FlowableSubscriber<User>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(User user) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof SQLiteConstraintException) {
                            Toast.makeText(RegisterActivity.this, "User already exist with that email address", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onComplete() {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                });


            } else {
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
            }


        });
    }

    public RegisterActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerRegisterActivityComponent.builder()
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
}
