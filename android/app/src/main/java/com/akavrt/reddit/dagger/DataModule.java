package com.akavrt.reddit.dagger;

import com.akavrt.reddit.api.RedditApiService;
import com.akavrt.reddit.data.DefaultLinkListProvider;
import com.akavrt.reddit.controller.LinkListController;
import com.akavrt.reddit.data.LinkListProvider;

import dagger.Module;
import dagger.Provides;

@DataScope
@Module
public class DataModule {

    @Provides
    LinkListProvider linkListProvider(RedditApiService apiService) {
        return new DefaultLinkListProvider(apiService);
    }

    @Provides
    LinkListController linkListController(LinkListProvider linkListProvider) {
        return new LinkListController(linkListProvider);
    }
}
