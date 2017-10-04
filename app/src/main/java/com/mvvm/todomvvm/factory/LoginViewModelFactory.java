package com.mvvm.todomvvm.factory;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.view.View;

import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.viewmodel.LoginViewModel;
import com.mvvm.todomvvm.viewmodel.MainViewModel;

import javax.inject.Inject;

/**
 * Created by Leon on 10.9.2017..
 */
@ActivityScope
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final Activity activity;
    private final View view;



    @Inject
    public LoginViewModelFactory(Activity activity, View view) {
        this.activity = activity;
        this.view = view;

    }

    @Override
    public LoginViewModel create(Class modelClass) {
        return new LoginViewModel(activity, view);
    }
}
