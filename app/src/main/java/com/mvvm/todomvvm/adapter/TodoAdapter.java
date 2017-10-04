package com.mvvm.todomvvm.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.viewmodel.MainViewModel;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder>{



    private final TodoListHelper mTodoList;
    private List<Todo> todos = new ArrayList<>();


    @Inject
    public TodoAdapter(MainViewModel viewModel, LifecycleOwner lifecycleOwner, TodoListHelper todoList) {
        mTodoList = todoList;

        viewModel.getTodos().observe(lifecycleOwner, todos1 -> {
            todos = todos1;
            notifyDataSetChanged();
        });


    }

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int position) {

        View  viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);





        return new TodoHolder(viewItem);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        Todo item = todos.get(position);

        holder.checkBox.setText(item.getDescription());
        holder.checkBox.setChecked(item.isCompleted());

        RxCompoundButton.checkedChanges(holder.checkBox)
                .filter(aBoolean -> holder.checkBox.isPressed())
                .map(aBoolean -> {
                    item.setCompleted(aBoolean);
                    return item;
                })
                .subscribe(mTodoList.toggleTodoConsumer);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onViewDetachedFromWindow(TodoHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public List<Todo> getTodos() {
        return todos;
    }


    public class TodoHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;

        public TodoHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox)itemView;
        }
    }
}
