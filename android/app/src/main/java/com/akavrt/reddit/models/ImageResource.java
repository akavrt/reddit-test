package com.akavrt.reddit.models;

public class ImageResource {
    public String url;
    public int width;
    public int height;


    public ImageResource() {
        // used by Gson
    }

    public ImageResource(String url) {
        this(url, 0, 0);
    }

    public ImageResource(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
