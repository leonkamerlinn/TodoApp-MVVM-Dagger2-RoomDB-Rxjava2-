package com.mvvm.todomvvm.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;


import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.db.AppDatabase;
import com.mvvm.todomvvm.di.qualifier.ApplicationContext;
import com.mvvm.todomvvm.di.qualifier.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leon on 6.9.2017..
 */
@Module(includes = ApplicationModule.class)
public class DatabaseModule {


    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPrefs(@ApplicationContext Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    @Provides
    AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
    }


}
