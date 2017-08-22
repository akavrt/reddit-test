package com.akavrt.reddit.data;

import com.akavrt.reddit.models.Link;

import java.util.List;

import rx.Observable;

public interface TopLinksProvider {
    Observable<List<Link>> getLinks(String after);
}
