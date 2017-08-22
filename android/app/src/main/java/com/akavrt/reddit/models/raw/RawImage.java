package com.akavrt.reddit.models.raw;

import com.akavrt.reddit.models.ImageResource;

import java.util.List;

public class RawImage {
    public ImageResource source;
    public List<ImageResource> resolutions;

    public RawImage() {
        // used by Gson
    }
}
