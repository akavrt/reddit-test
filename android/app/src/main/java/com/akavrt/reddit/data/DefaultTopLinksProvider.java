package com.akavrt.reddit.data;

import com.akavrt.reddit.api.RedditApiService;
import com.akavrt.reddit.models.Link;

import java.util.List;

import rx.Observable;

public class DefaultTopLinksProvider implements TopLinksProvider {
    private static final int DEFAULT_LIMIT = 10;

    private final RedditApiService apiService;
    private final int limit;


    public DefaultTopLinksProvider(RedditApiService apiService) {
        this(apiService, DEFAULT_LIMIT);
    }

    public DefaultTopLinksProvider(RedditApiService apiService, int limit) {
        this.apiService = apiService;
        this.limit = limit;
    }

    @Override
    public Observable<List<Link>> getLinks(String after) {
        return apiService.getTopLinks(after, limit);
    }
}
