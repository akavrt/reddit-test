package com.akavrt.reddit.models.raw;

import com.akavrt.reddit.models.ImageResource;

import java.util.List;

public class RawImageHolder {
    public ImageResource source;
    public List<ImageResource> resolutions;
    public RawImageVariants variants;

    public RawImageHolder() {
        // used by Gson
    }
}
