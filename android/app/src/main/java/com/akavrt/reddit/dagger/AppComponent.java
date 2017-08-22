package com.akavrt.reddit.dagger;

import android.content.Context;

import com.akavrt.reddit.api.RedditApiService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiServiceModule.class})
public interface AppComponent {
    Context context();
    RedditApiService apiService();
}
