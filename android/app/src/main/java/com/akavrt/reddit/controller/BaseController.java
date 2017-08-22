package com.akavrt.reddit.controller;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseController<V> {
    private final CompositeSubscription stoppableSubscriptions = new CompositeSubscription();
    private final CompositeSubscription disposableSubscriptions = new CompositeSubscription();

    private V view;
    private boolean started;


    public void bindView(V view) {
        this.view = view;
    }

    public void unbindView() {
        this.view = null;
    }

    protected V getView() {
        return view;
    }

    public void start() {
        started = true;
    }

    public void stop() {
        started = false;

        clearStoppableSubscriptions();
    }

    protected boolean isStarted() {
        return started;
    }

    protected void clearStoppableSubscriptions() {
        stoppableSubscriptions.clear();
    }

    protected void clearDisposableSubscriptions() {
        disposableSubscriptions.clear();
    }

    public void addStoppableSubscription(Subscription subscription) {
        stoppableSubscriptions.add(subscription);
    }

    public void addDisposableSubscription(Subscription subscription) {
        disposableSubscriptions.add(subscription);
    }

    public void dispose() {
        clearDisposableSubscriptions();
    }
}
