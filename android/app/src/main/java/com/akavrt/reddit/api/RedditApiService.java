package com.akavrt.reddit.api;

import com.akavrt.reddit.models.Link;

import java.util.List;

import rx.Observable;

public interface RedditApiService {
    Observable<List<Link>> getTopLinks(String after, int limit);
}
