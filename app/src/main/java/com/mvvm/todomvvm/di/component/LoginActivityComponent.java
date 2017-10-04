package com.mvvm.todomvvm.di.component;




import com.mvvm.todomvvm.MainActivity;
import com.mvvm.todomvvm.di.module.ActivityModule;
import com.mvvm.todomvvm.di.module.LoginActivityModule;
import com.mvvm.todomvvm.di.module.MainActivityModule;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.ui.LoginActivity;

import dagger.Component;

/**
 * Created by janisharali on 08/12/16.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginActivityModule.class})
public interface LoginActivityComponent {

    void inject(LoginActivity loginActivity);

}
