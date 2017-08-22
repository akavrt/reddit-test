package com.akavrt.reddit.utils;

import android.text.TextUtils;

import com.akavrt.reddit.models.Image;
import com.akavrt.reddit.models.ImageResource;
import com.akavrt.reddit.models.Link;

import java.util.List;

public class ThumbnailSelector {
    private final int itemWidthInPixels;

    public ThumbnailSelector(int itemWidthInPixels) {
        this.itemWidthInPixels = itemWidthInPixels;
    }

    public ImageResource findThumbnail(Link link) {
        Image image = null;
        if (link.hasAgeRestriction()) {
            if (link.hasNsfwImage()) {
                image = link.getNsfwImage();
            } else if (link.hasObfuscatedImage()) {
                image = link.getObfuscatedImage();
            }
        }

        if (image == null && link.hasDefaultImage()) {
            image = link.getDefaultImage();
        }

        ImageResource result = null;
        if (image != null) {
            result = findImageResource(image);
        }

        if (result == null && !TextUtils.isEmpty(link.getThumbnailUrl())) {
            result = new ImageResource(link.getThumbnailUrl());
        }

        return result;
    }

    private ImageResource findImageResource(Image image) {
        List<ImageResource> resolutions = image.getResolutions();
        int minDelta = 0;
        ImageResource result = null;
        for (int i = 0; i < resolutions.size(); i++) {
            ImageResource resolution = resolutions.get(i);
            int currentDelta = Math.abs(itemWidthInPixels - resolution.getWidth());

            if (i == 0 || currentDelta < minDelta) {
                minDelta = currentDelta;
                result = resolution;
            }
        }

        return result != null ? result : null;
    }
}
