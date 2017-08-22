package com.akavrt.reddit.controller;

import com.akavrt.reddit.data.LinkListProvider;
import com.akavrt.reddit.models.Link;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LinkListController extends BaseController<ItemListView<Link>> {
    private static final int MAX_DATA_SIZE = 50;

    private final LinkListProvider dataProvider;

    private List<Link> data;
    private Throwable forwardError;

    private int pageIndex;
    private String currentAfter;
    private boolean lastPageLoaded;
    private boolean pageLoading;

    private boolean isDataSet;


    public LinkListController(LinkListProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void start() {
        super.start();

        ItemListView<Link> view = getView();
        if (pageIndex == 0 && forwardError != null) {
            view.showError(forwardError);
        } else if (data != null) {
            if (data.isEmpty()) {
                view.showEmpty();
            } else {
                if (!isDataSet) {
                    isDataSet = true;
                    view.setData(data);
                }
            }
        } else {
            loadNextPage();
        }
    }

    @Override
    public void stop() {
        super.stop();
        pageLoading = false;
    }

    public void invalidate() {
        data = null;

        forwardError = null;

        pageIndex = 0;
        currentAfter = null;
        lastPageLoaded = false;
        pageLoading = false;
    }

    public void retry() {
        invalidate();
        loadNextPage();
    }

    @Override
    public void unbindView() {
        super.unbindView();
        isDataSet = false;
    }

    public void loadNextPage() {
        if (pageLoading || lastPageLoaded) {
            return;
        }

        requestPage(pageIndex);
    }

    private void requestPage(final int pageIndex) {
        if (pageIndex == 0) {
            clearStoppableSubscriptions();
        }

        ItemListView<Link> view = getView();
        if (pageIndex == 0) {
            pageLoading = true;
            view.showProgress();
        } else {
            pageLoading = true;
            view.showLoadNextProgress();
        }

        addStoppableSubscription(getPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Link>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable error) {
                        onPageLoadingError(pageIndex, error);
                    }

                    @Override
                    public void onNext(List<Link> items) {
                        onPageLoaded(pageIndex, items);
                    }
                }));
    }

    private Observable<List<Link>> getPage() {
        return dataProvider.getLinks(currentAfter);
    }

    private void onPageLoadingError(int pageIndex, Throwable error) {
        ItemListView<Link> view = getView();

        if (pageIndex == 0) {
            forwardError = error;
            pageLoading = false;
            isDataSet = false;
            view.showError(error);
        } else {
            forwardError = error;
            pageLoading = false;
            view.showLoadNextError(error);
        }
    }

    private void onPageLoaded(int loadedPageIndex, List<Link> items) {
        pageIndex++;

        if (items.isEmpty()) {
            currentAfter = null;
        } else {
            Link lastItem = items.get(items.size() - 1);
            currentAfter = lastItem.getId();
        }

        if (data == null) {
            data = new ArrayList<>();
        }
        data.addAll(items);

        forwardError = null;
        pageLoading = false;
        lastPageLoaded = items.isEmpty() || data.size() >= MAX_DATA_SIZE;

        ItemListView<Link> view = getView();
        if (loadedPageIndex == 0) {
            view.hideProgress();

            if (items.isEmpty()) {
                view.showEmpty();
            } else {
                isDataSet = true;
                view.setData(items);
            }
        } else {
            view.hideLoadNextProgress();

            if (!items.isEmpty()) {
                view.addDataToEnd(items);
            }
        }
    }
}