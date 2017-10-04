package com.mvvm.todomvvm.di.module;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.factory.LoginViewModelFactory;
import com.mvvm.todomvvm.factory.RegisterViewModelFactory;
import com.mvvm.todomvvm.viewmodel.LoginViewModel;
import com.mvvm.todomvvm.viewmodel.RegisterViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 6.9.2017..
 */
@Module(includes = {ActivityModule.class, DatabaseModule.class})
public class RegisterActivityModule {

    @Provides
    @ActivityScope
    View provideMainView(Activity activity) {
        return LayoutInflater.from(activity).inflate(R.layout.activity_register, null, false);
    }


    @Provides
    @ActivityScope
    RegisterViewModel provideLoginModel(Activity activity, RegisterViewModelFactory registerViewModelFactory) {
        return ViewModelProviders.of((FragmentActivity) activity, registerViewModelFactory).get(RegisterViewModel.class);
    }


    @Provides
    @ActivityScope
    LifecycleOwner provideLifecycleOwner(Activity activity) {
        return (LifecycleOwner)activity;
    }

}
