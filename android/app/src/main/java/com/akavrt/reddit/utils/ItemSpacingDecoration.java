package com.akavrt.reddit.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {
    private final int itemSpacing;


    public ItemSpacingDecoration(int itemOffsetInPx) {
        itemSpacing = itemOffsetInPx;
    }

    public ItemSpacingDecoration(Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemSpacing, itemSpacing, itemSpacing, 0);
    }
}

