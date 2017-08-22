package com.akavrt.reddit.controller;

import java.util.List;

public interface ItemListView<T> {
    void showProgress();
    void hideProgress();

    void showLoadNextProgress();
    void hideLoadNextProgress();

    void showError(Throwable error);
    void showLoadNextError(Throwable error);

    void showEmpty();

    void setData(List<T> items);
    void addDataToEnd(List<T> items);
}
