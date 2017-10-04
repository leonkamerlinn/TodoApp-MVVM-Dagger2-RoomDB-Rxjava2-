package com.mvvm.todomvvm.utils;

import android.content.Context;


import com.mvvm.todomvvm.db.AppDatabase;
import com.mvvm.todomvvm.di.qualifier.ApplicationContext;
import com.mvvm.todomvvm.di.scopes.ApplicationScope;
import com.mvvm.todomvvm.helper.SharedPrefsHelper;
import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.model.User;

import org.reactivestreams.Subscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by janisharali on 25/12/16.
 */

@ApplicationScope
public class DataManager {


    private final AppDatabase mDatabase;
    private final SharedPrefsHelper mSharedPrefsHelper;
    private final Context mContext;


    @Inject
    public DataManager(@ApplicationContext Context context, SharedPrefsHelper sharedPrefsHelper, AppDatabase database) {
        mContext = context;
        mSharedPrefsHelper = sharedPrefsHelper;
        mDatabase = database;
    }

    public void saveAccessToken(String accessToken) {
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken(){
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, null);
    }

    public void setLoggedIn(Boolean loggedIn) {
        mSharedPrefsHelper.put(SharedPrefsHelper.IS_LOGGED_IN, loggedIn);
    }

    public boolean isLoggedIn() {
        return mSharedPrefsHelper.get(SharedPrefsHelper.IS_LOGGED_IN, false);
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }

    public Flowable<User> insertUser(User user) {

        return new Flowable<User>() {
            @Override
            protected void subscribeActual(Subscriber<? super User> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mDatabase.userDao().insert(user);
                        s.onNext(user);
                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }
                    return user;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
            }
        };
    }

    public Flowable<User> insertUsers(User... users) {
        return new Flowable<User>() {
            @Override
            protected void subscribeActual(Subscriber<? super User> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mDatabase.userDao().insertAll(users);
                        for (User user: users) {
                            s.onNext(user);
                        }

                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }

                    return users;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();

            }
        };
    }

    public Flowable<Boolean> deleteDatabase() {
        return new Flowable<Boolean>() {
            @Override
            protected void subscribeActual(Subscriber<? super Boolean> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mContext.deleteDatabase(AppDatabase.DB_NAME);
                        s.onNext(true);
                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }

                    return true;
                })
                        .observeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
            }
        };
    }

    public Flowable<List<User>> getAllUsers() {

        return mDatabase.userDao().getAll();
    }

    public Single<User> findUserByEmail(String email) {
        return mDatabase.userDao().findByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public Observable<Boolean> loggIn(String email, String password) {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                findUserByEmail(email).subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        String hashPassword = user.getPassword();
                        String salt = user.getSalt();

                        System.out.println(SecureUtils.sha512(password, salt));


                        if (hashPassword.equals(SecureUtils.sha512(password, salt))) {
                            observer.onNext(true);
                            observer.onComplete();
                        } else {
                            observer.onNext(false);
                            observer.onComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);

                    }
                });
            }
        }.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread());

    }
    public Flowable<Boolean> nukeTodoTable() {
        return new Flowable<Boolean>() {
            @Override
            protected void subscribeActual(Subscriber<? super Boolean> s) {
                Flowable.fromCallable(() -> {
                    mDatabase.todoDao().nukeTable();
                    s.onNext(true);
                    s.onComplete();
                    return true;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
            }
        };
    }
    public Flowable<Todo> insertTodo(Todo todo) {
        return new Flowable<Todo>() {
            @Override
            protected void subscribeActual(Subscriber<? super Todo> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mDatabase.todoDao().insert(todo);
                        s.onNext(todo);
                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }
                    return todo;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
            }
        };
    }

    public Flowable<Todo> updateTodo(Todo todo) {
        return new Flowable<Todo>() {
            @Override
            protected void subscribeActual(Subscriber<? super Todo> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mDatabase.todoDao().update(todo);
                        s.onNext(todo);
                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }
                    return todo;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe();
            }
        };
    }

    public Flowable<List<Todo>> getTodos() {
        return mDatabase.todoDao().getTodos();
    }

    public Flowable<List<Todo>> getTodosByUser(String email) {
        return mDatabase.todoDao().getTodosByUser(email);
    }

    public void insertTodos(List<Todo> todos) {
        for (Todo todo: todos) {
            todo.setUserEmail(getUserEmail());
        }
        Flowable.fromCallable(() -> {
            mDatabase.todoDao().insertAll(todos);
            return todos;
        });

        /*return new Flowable<List<Todo>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<Todo>> s) {
                Flowable.fromCallable(() -> {
                    try {
                        mDatabase.todoDao().insertAll(todos);
                        s.onNext(todos);
                        s.onComplete();
                    } catch (Exception e) {
                        s.onError(e);
                    }

                    return todos;
                });
            }
        };*/
    }

    public void setUserEmail(String email) {
        mSharedPrefsHelper.put(SharedPrefsHelper.USER_EMAIL, email);
    }

    public String getUserEmail() {
        return mSharedPrefsHelper.get(SharedPrefsHelper.USER_EMAIL, "");
    }
}
