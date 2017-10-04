package com.mvvm.todomvvm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.firebase.database.FirebaseDatabase;
import com.mvvm.todomvvm.adapter.TodoAdapter;
import com.mvvm.todomvvm.di.component.DaggerMainActivityComponent;
import com.mvvm.todomvvm.di.component.MainActivityComponent;
import com.mvvm.todomvvm.di.module.ActivityModule;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.model.Todo;
import com.mvvm.todomvvm.model.User;
import com.mvvm.todomvvm.ui.LoginActivity;
import com.mvvm.todomvvm.utils.DataManager;
import com.mvvm.todomvvm.viewmodel.MainViewModel;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainActivityComponent activityComponent;

    @Inject
    View view;
    @Inject
    MainViewModel viewModel;
    @Inject
    LifecycleOwner lifecycleOwner;
    @Inject
    TodoAdapter todoAdapter;
    @Inject
    TodoListHelper todoList;

    @Inject
    DataManager dataManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);
        redirectIfIsNotLoggedIn();
        setContentView(view);

        setSupportActionBar(viewModel.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.todo_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        viewModel.spinner.setAdapter(adapter);


        viewModel.recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        viewModel.recyclerView.setLayoutManager(mLayoutManager);
        viewModel.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        viewModel.recyclerView.setItemAnimator(new DefaultItemAnimator());




        viewModel.recyclerView.setAdapter(todoAdapter);


        dataManager.getTodosByUser(dataManager.getUserEmail())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(todoList.listTodoDatabaseConsumer);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                dataManager.setLoggedIn(false);
                dataManager.setUserEmail("");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

            case R.id.menu_save:



                break;
        }

        return true;
    }

    private void redirectIfIsNotLoggedIn() {
        if (!dataManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }


    public MainActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerMainActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }


    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

}
