package com.mvvm.todomvvm.di.module;

import android.app.Activity;
import android.content.Context;


import com.mvvm.todomvvm.di.qualifier.ActivityContext;
import com.mvvm.todomvvm.di.qualifier.DatabaseInfo;

import java.util.Random;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 08/12/16.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }


    @Provides
    @DatabaseInfo
    int provideRandom() {
        int min = 10;
        int max = 80;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }


}
