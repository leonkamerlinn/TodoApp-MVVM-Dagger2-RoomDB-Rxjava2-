package com.mvvm.todomvvm.di.module;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;


import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.factory.MainViewModelFactory;
import com.mvvm.todomvvm.viewmodel.MainViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 6.9.2017..
 */
@Module(includes = {ActivityModule.class, DatabaseModule.class})
public class MainActivityModule {

    @Provides
    @ActivityScope
    View provideMainView(Activity activity) {
        return LayoutInflater.from(activity).inflate(R.layout.activity_main, null, false);
    }


    @Provides
    @ActivityScope
    MainViewModel provideMainModel(Activity activity, MainViewModelFactory mainViewModelFactory) {
        return ViewModelProviders.of((FragmentActivity) activity, mainViewModelFactory).get(MainViewModel.class);
    }


    @Provides
    @ActivityScope
    LifecycleOwner provideLifecycleOwner(Activity activity) {
        return (LifecycleOwner)activity;
    }

}
