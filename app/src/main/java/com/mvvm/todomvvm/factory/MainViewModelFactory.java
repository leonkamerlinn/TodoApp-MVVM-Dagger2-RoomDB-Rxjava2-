package com.mvvm.todomvvm.factory;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.view.View;


import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.viewmodel.MainViewModel;

import javax.inject.Inject;

/**
 * Created by Leon on 10.9.2017..
 */
@ActivityScope
public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Activity activity;
    private final View view;
    private final TodoListHelper todoList;


    @Inject
    public MainViewModelFactory(Activity activity, View view, TodoListHelper todoList) {
        this.activity = activity;
        this.view = view;
        this.todoList = todoList;
    }

    @Override
    public MainViewModel create(Class modelClass) {
        return new MainViewModel(activity, view, todoList);
    }
}
