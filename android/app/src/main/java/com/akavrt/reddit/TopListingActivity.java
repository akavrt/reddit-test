package com.akavrt.reddit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akavrt.reddit.adapter.AdapterItemRenderer;
import com.akavrt.reddit.adapter.ListingAdapter;
import com.akavrt.reddit.controller.ItemListView;
import com.akavrt.reddit.controller.LinkListController;
import com.akavrt.reddit.dagger.DaggerDataComponent;
import com.akavrt.reddit.dagger.DataComponent;
import com.akavrt.reddit.dagger.DataModule;
import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.test.R;
import com.akavrt.reddit.utils.EndlessScrollListener;
import com.akavrt.reddit.utils.ItemSpacingDecoration;
import com.akavrt.reddit.utils.NavigationListener;
import com.akavrt.reddit.utils.ThumbnailSelector;

import java.util.List;

import javax.inject.Inject;

public class TopListingActivity extends AppCompatActivity implements ItemListView<Link> {
    private static final String REDDIT_BASE_URL = "https://reddit.com";

    @Inject LinkListController controller;

    private ProgressBar globalProgressView;
    private ProgressBar nextPageProgressView;
    private SwipeRefreshLayout swipeRefreshView;
    private View emptyView;
    private View errorView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private EndlessScrollListener scrollListener;

    private ListingAdapter adapter;

    private Toast cachedToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViews();
        setupController();
    }

    private void setupController() {
        ControllerViewModel holder = ViewModelProviders.of(this).get(ControllerViewModel.class);
        if (holder.getController() == null) {
            DataComponent component = DaggerDataComponent.builder()
                    .appComponent(((CustomApplication) getApplication()).component())
                    .dataModule(new DataModule())
                    .build();

            component.inject(this);
            holder.setController(controller);
        } else {
            controller = holder.getController();
        }

        controller.bindView(this);
    }

    private void setupViews() {
        setContentView(R.layout.activity_listing);

        setupToolbar();

        globalProgressView = findViewById(R.id.progress);
        nextPageProgressView = findViewById(R.id.progress_next_page);
        if (!hasLollipop()) {
            int tintColor = getResources().getColor(R.color.colorAccent);
            globalProgressView.getIndeterminateDrawable().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            nextPageProgressView.getIndeterminateDrawable().setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        }

        swipeRefreshView = findViewById(R.id.swipe_refresh);
        swipeRefreshView.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshView.setEnabled(false);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                reloadData();
            }
        });

        errorView = findViewById(R.id.error_views);
        Button retryButton = findViewById(R.id.error_retry);
        retryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                retry();
            }
        });

        emptyView = findViewById(R.id.empty_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        recyclerView = findViewById(R.id.recycler_view);
        adjustRecyclerViewPadding(recyclerView, screenWidth);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemSpacingDecoration itemDecoration = new ItemSpacingDecoration(this, R.dimen.item_spacing);
        recyclerView.addItemDecoration(itemDecoration);

        scrollListener = new EndlessScrollListener(layoutManager) {

            @Override
            public void onLoadMore() {
                loadNextPage();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        ThumbnailSelector thumbnailSelector = createThumbnailSelector(recyclerView, screenWidth);
        AdapterItemRenderer itemRenderer = new AdapterItemRenderer(this, thumbnailSelector);

        adapter = new ListingAdapter(this, itemRenderer);
        adapter.setNavigationListener(navigationListener);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.listing_title);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                scrollToTop();
            }
        });
    }

    private void scrollToTop() {
        if (adapter.getItemCount() > 0) {
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            if (firstVisiblePosition > 5) {
                recyclerView.scrollToPosition(0);
            } else {
                recyclerView.smoothScrollToPosition(0);
            }
        }
    }

    private void retry() {
        errorView.setVisibility(View.GONE);
        controller.retry();
    }

    private void adjustRecyclerViewPadding(RecyclerView recyclerView, int screenWidth) {
        Resources res = getResources();
        int maxWidth = res.getDimensionPixelSize(R.dimen.recycler_view_content_max_width);
        int defaultPadding = res.getDimensionPixelSize(R.dimen.recycler_view_min_side_padding);

        int sidePadding = (screenWidth - maxWidth) / 2;

        if (sidePadding < defaultPadding) {
            sidePadding = defaultPadding;
        }

        int topPadding = recyclerView.getPaddingTop();
        int bottomPadding = recyclerView.getPaddingBottom();

        recyclerView.setPadding(sidePadding, topPadding, sidePadding, bottomPadding);
    }

    private ThumbnailSelector createThumbnailSelector(RecyclerView recyclerView, int screenWidth) {
        int itemWidthInPixels = screenWidth - recyclerView.getPaddingLeft() - recyclerView.getPaddingRight();
        return new ThumbnailSelector(itemWidthInPixels);
    }

    private void loadNextPage() {
        controller.loadNextPage();
    }

    private void reloadData() {
        adapter.clear();
        scrollListener.resetState();
        controller.invalidate();
        controller.loadNextPage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.unbindView();

        if (isFinishing()) {
            controller.dispose();
        }
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshView.isRefreshing()) {
            globalProgressView.setVisibility(View.VISIBLE);
        }

        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        swipeRefreshView.setRefreshing(false);
        globalProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadNextProgress() {
        nextPageProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadNextProgress() {
        nextPageProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable error) {
        hideProgress();
        swipeRefreshView.setEnabled(false);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadNextError(Throwable error) {
        hideLoadNextProgress();
        showErrorToast();
    }

    private void showErrorToast() {
        if (cachedToast != null) {
            cachedToast.cancel();
        }

        cachedToast = Toast.makeText(this, R.string.load_next_error_message, Toast.LENGTH_SHORT);
        cachedToast.show();
    }

    @Override
    public void showEmpty() {
        swipeRefreshView.setEnabled(true);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(List<Link> items) {
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        swipeRefreshView.setEnabled(true);

        adapter.clear();
        adapter.addLinks(items);
    }

    @Override
    public void addDataToEnd(List<Link> items) {
        adapter.addLinks(items);
    }

    private void openUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            // ignore
        }
    }

    private NavigationListener navigationListener = new NavigationListener() {

        @Override
        public void openImageUrl(String url) {
            openUrl(url);
        }

        @Override
        public void openRelativeUrl(String relativeUrl) {
            openUrl(REDDIT_BASE_URL + relativeUrl);
        }
    };

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
