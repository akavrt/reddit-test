package com.akavrt.reddit.models;

import java.util.ArrayList;
import java.util.List;

public class Image {
    private final ImageResource source;
    private final List<ImageResource> resolutions;

    public Image(ImageResource source, List<ImageResource> resolutions) {
        this.source = source;

        this.resolutions = new ArrayList<>();
        if (resolutions != null) {
            this.resolutions.addAll(resolutions);
        }
    }

    public ImageResource getSource() {
        return source;
    }

    public List<ImageResource> getResolutions() {
        return resolutions;
    }
}
