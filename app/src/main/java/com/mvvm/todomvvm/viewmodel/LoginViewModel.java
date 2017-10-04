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
import android.widget.TextView;

import com.mvvm.todomvvm.R;
import com.mvvm.todomvvm.helper.TodoListHelper;
import com.mvvm.todomvvm.model.Todo;

import java.util.List;


/**
 * Created by Leon on 9.9.2017..
 */

public class LoginViewModel extends ViewModel {


    public final EditText emailEditText;
    public final EditText passwordEditText;
    public final Button loginButton;
    public final TextView registerTextView;

    public LoginViewModel(Activity activity, View view) {
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        registerTextView = view.findViewById(R.id.dontHaveAccount);

    }


}
