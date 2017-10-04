package com.mvvm.todomvvm.di.module;

import android.app.Application;
import android.content.Context;


import com.mvvm.todomvvm.di.qualifier.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 25/12/16.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }



}
