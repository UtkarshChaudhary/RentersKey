package com.example.lenovo.renterskey.di;

import android.app.Application;

import com.example.lenovo.renterskey.RentersKeyApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by lenovo on 28-12-2017.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }

    void inject(RentersKeyApp rentersKeyApp);
}
