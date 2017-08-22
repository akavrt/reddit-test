package com.akavrt.reddit.dagger;

import com.akavrt.reddit.api.RedditApiService;
import com.akavrt.reddit.data.DefaultTopLinksProvider;
import com.akavrt.reddit.controller.LinkListController;
import com.akavrt.reddit.data.TopLinksProvider;

import dagger.Module;
import dagger.Provides;

@DataScope
@Module
public class DataModule {

    @Provides
    TopLinksProvider linkListProvider(RedditApiService apiService) {
        return new DefaultTopLinksProvider(apiService);
    }

    @Provides
    LinkListController linkListController(TopLinksProvider linkListProvider) {
        return new LinkListController(linkListProvider);
    }
}
