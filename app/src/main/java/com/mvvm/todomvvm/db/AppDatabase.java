package com.mvvm.todomvvm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mvvm.todomvvm.db.dao.TodoDao;
import com.mvvm.todomvvm.db.dao.UserDao;
import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.model.User;


/**
 * Created by Leon on 17.9.2017..
 */

@Database(entities = {User.class, Todo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "db_name";
    public abstract UserDao userDao();
    public abstract TodoDao todoDao();
}
