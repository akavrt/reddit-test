package com.akavrt.reddit;


import android.app.Application;

import com.akavrt.reddit.dagger.ApiServiceModule;
import com.akavrt.reddit.dagger.AppComponent;
import com.akavrt.reddit.dagger.AppModule;
import com.akavrt.reddit.dagger.DaggerAppComponent;

public class CustomApplication extends Application {
    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }
}
