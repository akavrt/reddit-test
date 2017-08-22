package com.akavrt.reddit.models.converters;

import android.text.TextUtils;

import com.akavrt.reddit.models.Image;
import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.models.raw.RawImage;
import com.akavrt.reddit.models.raw.RawImageHolder;
import com.akavrt.reddit.models.raw.RawLink;
import com.akavrt.reddit.models.raw.RawLinkData;

import java.util.concurrent.TimeUnit;

public class LinkConverter extends BaseConverter<RawLink, Link> {
    private static final String THUMBNAIL_SELF = "self";
    private static final String THUMBNAIL_NSFW = "nsfw";
    private static final String THUMBNAIL_IMAGE = "image";
    private static final String THUMBNAIL_DEFAULT = "default";

    private final ImageConverter imageConverter;


    public LinkConverter() {
        imageConverter = new ImageConverter();
    }

    @Override
    public Link convert(RawLink source) {
        Link.Builder builder = new Link.Builder();
        if (source.data != null) {
            RawLinkData data = source.data;
            builder.setId(data.name);
            builder.setTitle(data.title);

            String thumbnail = data.thumbnail;
            if (isValidThumbnail(thumbnail)) {
                builder.setThumbnailUrl(thumbnail);
            }

            builder.setAuthor(data.author);
            long creationTimeMs = TimeUnit.SECONDS.toMillis(data.created);
            builder.setCreationTime(creationTimeMs);
            builder.setCommentsCount(data.comments);
            builder.setScore(data.score);
            builder.setAgeRestriction(data.over18);
            builder.setRelativeUrl(data.permalink);

            if (data.preview != null && data.preview.imageHolders != null &&
                    !data.preview.imageHolders.isEmpty()) {
                RawImageHolder imageHolder = data.preview.imageHolders.get(0);
                if (imageHolder.source != null) {
                    RawImage defaultRawImage = new RawImage();
                    defaultRawImage.source = imageHolder.source;
                    defaultRawImage.resolutions = imageHolder.resolutions;

                    Image defaultImage = imageConverter.convert(defaultRawImage);
                    builder.setDefaultImage(defaultImage);
                }

                if (imageHolder.variants != null) {
                    RawImage nsfwRawImage = imageHolder.variants.nsfw;
                    if (nsfwRawImage != null) {
                        Image nsfwImage = imageConverter.convert(nsfwRawImage);
                        builder.setNsfwImage(nsfwImage);
                    }

                    RawImage obfuscatedRawImage = imageHolder.variants.obfuscated;
                    if (obfuscatedRawImage != null) {
                        Image obfuscatedImage = imageConverter.convert(obfuscatedRawImage);
                        builder.setObfuscatedImage(obfuscatedImage);
                    }
                }
            }
        }

        return builder.build();
    }

    private static boolean isValidThumbnail(String thumbnail) {
        return !TextUtils.isEmpty(thumbnail)
                && !THUMBNAIL_SELF.equalsIgnoreCase(thumbnail)
                && !THUMBNAIL_NSFW.equalsIgnoreCase(thumbnail)
                && !THUMBNAIL_IMAGE.equalsIgnoreCase(thumbnail)
                && !THUMBNAIL_DEFAULT.equalsIgnoreCase(thumbnail);
    }
}
