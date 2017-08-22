package com.akavrt.reddit.adapter;

public class AdapterItem {
    public final String title;
    public final String subtitle;
    public final String author;
    public final String score;
    public final String imagePreviewUrl;

    public AdapterItem(String title, String subtitle, String author, String score, String imagePreviewUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.score = score;
        this.imagePreviewUrl = imagePreviewUrl;
    }
}
