package com.akavrt.reddit.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5;

    private int previousTotalItemCount = 0;
    private boolean loading = true;

    private LinearLayoutManager layoutManager;


    protected abstract void onLoadMore();


    public EndlessScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + VISIBLE_THRESHOLD) > totalItemCount) {
            onLoadMore();
            loading = true;
        }
    }

    public void resetState() {
        this.previousTotalItemCount = 0;
        this.loading = true;
    }
}