package com.mvvm.todomvvm;

import android.app.Application;
import android.content.Context;


import com.mvvm.todomvvm.di.component.ApplicationComponent;
import com.mvvm.todomvvm.di.component.DaggerApplicationComponent;
import com.mvvm.todomvvm.di.module.ApplicationModule;
import com.mvvm.todomvvm.di.module.DatabaseModule;
import com.mvvm.todomvvm.utils.DataManager;

import javax.inject.Inject;

/**
 * Created by janisharali on 25/12/16.
 */

public class DemoApplication extends Application {

    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                                            .builder()
                                            .applicationModule(new ApplicationModule(this))
                                            .build();
                applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
