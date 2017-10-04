package com.mvvm.todomvvm.di.component;

import android.app.Application;
import android.content.Context;


import com.mvvm.todomvvm.DemoApplication;
import com.mvvm.todomvvm.di.module.ApplicationModule;
import com.mvvm.todomvvm.di.module.DatabaseModule;
import com.mvvm.todomvvm.di.qualifier.ApplicationContext;
import com.mvvm.todomvvm.di.scopes.ApplicationScope;
import com.mvvm.todomvvm.helper.SharedPrefsHelper;
import com.mvvm.todomvvm.utils.DataManager;

import dagger.Component;


/**
 * Created by janisharali on 08/12/16.
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();



    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();







}
