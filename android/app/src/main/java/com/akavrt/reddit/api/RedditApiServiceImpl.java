package com.akavrt.reddit.api;

import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.models.converters.ListingConverter;
import com.akavrt.reddit.models.raw.RawListing;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RedditApiServiceImpl implements RedditApiService {
    private static final String BASE_URL = "https://www.reddit.com/";
    private final RedditApi api;


    public RedditApiServiceImpl(OkHttpClient client, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(RedditApi.class);
    }

    @Override
    public Observable<List<Link>> getTopItems(String after, int limit) {
        return api.topItems(after, limit, 1)
                .map(new Func1<RawListing, List<Link>>() {

                    @Override
                    public List<Link> call(RawListing rawListing) {
                        return new ListingConverter().convert(rawListing);
                    }
                });
    }
}
