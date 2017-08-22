package com.akavrt.reddit.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application app;


    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context context() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    public Application app() {
        return app;
    }
}
