package com.akavrt.reddit.adapter;

import android.content.Context;
import android.content.res.Resources;

import com.akavrt.reddit.models.ImageResource;
import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.models.converters.BaseConverter;
import com.akavrt.reddit.test.R;
import com.akavrt.reddit.utils.ThumbnailSelector;

import java.util.concurrent.TimeUnit;

public class AdapterItemRenderer extends BaseConverter<Link, AdapterItem> {
    private final Context context;
    private final ThumbnailSelector thumbnailSelector;


    public AdapterItemRenderer(Context context, ThumbnailSelector thumbnailSelector) {
        this.context = context;
        this.thumbnailSelector = thumbnailSelector;
    }

    @Override
    public AdapterItem convert(Link link) {
        String time = renderTimeDifference(link.getCreationTime());

        int commentsCount = link.getCommentsCount();
        String comments = commentsCount + " " +
                context.getResources().getQuantityString(R.plurals.comments, commentsCount);
        String subtitle = time + " \u2022 " + comments;

        String score = String.format("%.1fk", 0.001 * link.getScore());

        ImageResource resource = thumbnailSelector.findThumbnail(link);
        String previewUrl = resource != null ? resource.getUrl() : null;

        return new AdapterItem(link.getTitle(), subtitle, link.getAuthor(), score, previewUrl);
    }

    private String renderTimeDifference(long timeMs) {
        long now = System.currentTimeMillis();
        long differenceMs = now - timeMs;

        String timeDiffText;

        Resources res = context.getResources();
        if (differenceMs < TimeUnit.MINUTES.toMillis(1)) {
            // use "X seconds ago"
            int secondsDiff = (int) TimeUnit.MILLISECONDS.toSeconds(differenceMs);
            timeDiffText = secondsDiff + " " + res.getQuantityString(R.plurals.seconds, secondsDiff);
        } else if (differenceMs < TimeUnit.HOURS.toMillis(1)) {
            // use "X minutes ago"
            int minutesDiff = (int) TimeUnit.MILLISECONDS.toMinutes(differenceMs);
            timeDiffText = minutesDiff + " " + res.getQuantityString(R.plurals.minutes, minutesDiff);

        } else if (differenceMs < TimeUnit.DAYS.toMillis(1)) {
            // use "X hours ago"
            int hoursDiff = (int) TimeUnit.MILLISECONDS.toHours(differenceMs);
            timeDiffText = hoursDiff + " " + res.getQuantityString(R.plurals.hours, hoursDiff);

        } else {
            // use "X days ago"
            int daysDiff = (int) TimeUnit.MILLISECONDS.toDays(differenceMs);
            timeDiffText = daysDiff + " " + res.getQuantityString(R.plurals.days, daysDiff);
        }

        String agoText = context.getString(R.string.ago);
        timeDiffText += " " + agoText;

        return timeDiffText;
    }
}
