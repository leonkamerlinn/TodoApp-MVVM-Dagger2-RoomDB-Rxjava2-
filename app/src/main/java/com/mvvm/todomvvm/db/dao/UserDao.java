package com.mvvm.todomvvm.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.mvvm.todomvvm.model.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


/**
 * Created by Leon on 17.9.2017..
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    Single<User> findByEmail(String email);

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    Single<User> findUserByEmailAndPassword(String email, String password);

    @Query("SELECT COUNT(*) FROM user WHERE first_name LIKE :first_name")
    Flowable<Integer> countUsers(String first_name);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
