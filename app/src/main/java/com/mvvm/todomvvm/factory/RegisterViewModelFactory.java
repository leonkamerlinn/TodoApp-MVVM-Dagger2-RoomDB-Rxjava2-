package com.mvvm.todomvvm.factory;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.view.View;

import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.viewmodel.LoginViewModel;
import com.mvvm.todomvvm.viewmodel.RegisterViewModel;

import javax.inject.Inject;

/**
 * Created by Leon on 10.9.2017..
 */
@ActivityScope
public class RegisterViewModelFactory implements ViewModelProvider.Factory {
    private final Activity activity;
    private final View view;



    @Inject
    public RegisterViewModelFactory(Activity activity, View view) {
        this.activity = activity;
        this.view = view;

    }

    @Override
    public RegisterViewModel create(Class modelClass) {
        return new RegisterViewModel(activity, view);
    }
}
