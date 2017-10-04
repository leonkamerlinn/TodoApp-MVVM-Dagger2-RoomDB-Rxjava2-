package com.mvvm.todomvvm.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.model.Todo;

import java.util.List;


/**
 * Created by Leon on 9.9.2017..
 */

public class MainViewModel extends ViewModel {

    private final TodoListHelper mTodoList;
    public final Toolbar toolbar;
    public final Spinner spinner;
    public final EditText addTodoEditText;
    public final RecyclerView recyclerView;
    public final Button addTodoBtn;

    public MainViewModel(Activity activity, View view, TodoListHelper todoList) {
        mTodoList = todoList;
        toolbar = view.findViewById(R.id.toolbar);
        spinner = view.findViewById(R.id.spinner);
        addTodoEditText = view.findViewById(R.id.add_todo_edittext);
        recyclerView = view.findViewById(R.id.recyclerview);
        addTodoBtn = view.findViewById(R.id.add_todo_btn);
    }


    public LiveData<List<Todo>> getTodos() {
        return mTodoList;
    }


}
