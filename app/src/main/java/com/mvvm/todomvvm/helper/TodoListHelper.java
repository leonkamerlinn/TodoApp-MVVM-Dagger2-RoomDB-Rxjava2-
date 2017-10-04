package com.mvvm.todomvvm.helper;

import android.arch.lifecycle.LiveData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.interfaces.FilterPositions;
import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.utils.DataManager;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by Leon on 10.9.2017..
 */

@ActivityScope
public class TodoListHelper extends LiveData<List<Todo>> {
    private DataManager mDataManager = null;
    private ReplaySubject<TodoListHelper> todoListReplaySubject = ReplaySubject.create();
    private List<Todo> listTodo = new ArrayList<>();



    public Consumer<Todo> toggleTodoConsumer = todo -> {
        mDataManager.updateTodo(todo).subscribe();
        todoListReplaySubject.onNext(this);
    };

    public Consumer<String> addTodoConsumer = description -> {
        Todo todo = new Todo(description, false);
        todo.setUserEmail(mDataManager.getUserEmail());
        listTodo.add(todo);
        mDataManager.insertTodo(todo).subscribe();
        todoListReplaySubject.onNext(this);
    };

    public Consumer<List<Todo>> listTodoConsumer = todos -> {
        setValue(todos);
    };

    public Consumer<List<Todo>> listTodoDatabaseConsumer = todos -> {
        listTodo = todos;
        setValue(todos);
    };

    public List<Todo> getAllTodos() {
        return listTodo;
    }

    public List<Todo> getCompletedTodos() {
        List<Todo> todoList = new ArrayList<>();
        for(Todo todo: getAllTodos()) {
            if (todo.isCompleted()) {
                todoList.add(todo);
            }
        }
        return todoList;
    }

    public List<Todo> getIncopletedTodos() {
        List<Todo> todoList = new ArrayList<>();
        for(Todo todo: getAllTodos()) {
            if (!todo.isCompleted()) {
                todoList.add(todo);
            }
        }
        return todoList;
    }



    @Inject
    public TodoListHelper(View view, DataManager dataManager) {
        mDataManager = dataManager;
        Spinner spinner = view.findViewById(R.id.spinner);
        EditText addInput = view.findViewById(R.id.add_todo_edittext);
        Button btnAddTodo = view.findViewById(R.id.add_todo_btn);

        Observable<Integer> spinnerObservable = RxAdapterView.itemSelections(spinner);
        Observable<String> addTodoObservable = RxView.clicks(btnAddTodo)
                .map(o -> {
                    String str = addInput.getText().toString();
                    addInput.setText("");
                    return str;
                })
                .filter(str -> !str.isEmpty());

        Disposable addTodoDisposable = addTodoObservable.subscribe(addTodoConsumer);

        Disposable ll = Observable.combineLatest(spinnerObservable, asObservable(), (integer, todoList) -> {
            switch (integer) {
                case FilterPositions.ALL:
                    return todoList.getAllTodos();

                case FilterPositions.COMPLETE:
                    return todoList.getCompletedTodos();

                case FilterPositions.INCOMPLETE:
                    return todoList.getIncopletedTodos();

                default:
                    return todoList.getAllTodos();
            }
        }).subscribe(listTodoConsumer);
    }

    @Override
    protected void onActive() {

    }

    @Override
    protected void onInactive() {

    }

    public Observable<TodoListHelper> asObservable() {
        return todoListReplaySubject;
    }
}
