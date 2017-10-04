package com.mvvm.todomvvm.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Leon on 19.9.2017..
 */
@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    Flowable<List<Todo>> getTodos();

    @Query("SELECT * FROM todo WHERE user_email LIKE :email")
    Flowable<List<Todo>> getTodosByUser(String email);

    @Insert
    void insertAll(List<Todo> todos);

    @Insert
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);

    @Update
    void update(Todo todo);

    @Query("DELETE FROM todo")
    public void nukeTable();
}
