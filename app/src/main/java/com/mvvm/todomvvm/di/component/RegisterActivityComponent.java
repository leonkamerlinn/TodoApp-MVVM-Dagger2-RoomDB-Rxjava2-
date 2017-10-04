package com.mvvm.todomvvm.di.component;




import com.mvvm.todomvvm.di.module.ActivityModule;
import com.mvvm.todomvvm.di.module.LoginActivityModule;
import com.mvvm.todomvvm.di.module.RegisterActivityModule;
import com.mvvm.todomvvm.di.scopes.ActivityScope;
import com.mvvm.todomvvm.ui.LoginActivity;
import com.mvvm.todomvvm.ui.RegisterActivity;

import dagger.Component;

/**
 * Created by janisharali on 08/12/16.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, RegisterActivityModule.class})
public interface RegisterActivityComponent {

    void inject(RegisterActivity registerActivity);

}
