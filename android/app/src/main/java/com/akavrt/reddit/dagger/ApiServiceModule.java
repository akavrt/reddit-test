package com.akavrt.reddit.dagger;

import android.content.Context;

import com.akavrt.reddit.api.RedditApiService;
import com.akavrt.reddit.api.RedditApiServiceImpl;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApiServiceModule {
    private static final long CACHE_SIZE_IN_BYTES = 1024 * 1024;
    private static final long TIMEOUT_IN_SECONDS = 20;


    @Provides
    @Singleton
    Cache cache(Context context) {
        return new Cache(context.getCacheDir(), CACHE_SIZE_IN_BYTES);
    }

    @Provides
    @Singleton
    CookieJar cookieJar(Context context) {
        return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }

    @Provides
    @Singleton
    Interceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient okHttpClient(Cache cache, CookieJar cookieJar, Interceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Gson gson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public RedditApiService apiService(OkHttpClient client, Gson gson) {
        return new RedditApiServiceImpl(client, gson);
    }
}
