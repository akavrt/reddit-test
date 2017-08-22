package com.akavrt.reddit.dagger;

import com.akavrt.reddit.TopListingActivity;

import dagger.Component;

@DataScope
@Component(dependencies = AppComponent.class, modules = DataModule.class)
public interface DataComponent {
    void inject(TopListingActivity activity);
}
