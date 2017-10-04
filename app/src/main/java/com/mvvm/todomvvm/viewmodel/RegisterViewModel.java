package com.mvvm.todomvvm.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mvvm.todomvvm.R;


/**
 * Created by Leon on 9.9.2017..
 */

public class RegisterViewModel extends ViewModel {


    public final EditText firstNameEditText;
    public final EditText lastNameEditText;
    public final EditText emailEditText;
    public final EditText passwordEditText;
    public final EditText repeatPasswordEditText;
    public final Button registerButton;

    public RegisterViewModel(Activity activity, View view) {
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        repeatPasswordEditText = view.findViewById(R.id.repeatPasswordEditText);
        registerButton = view.findViewById(R.id.registerButton);
    }


}
