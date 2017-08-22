package com.akavrt.reddit.api;

import com.akavrt.reddit.models.raw.RawListing;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RedditApi {

    @GET("top/.json")
    Observable<RawListing> topItems(@Query("after") String after, @Query("limit") int limit,
                                    @Query("raw_json") int rawJson);

}
