package com.akavrt.reddit.models;

public class Link {
    private final String id;
    private final String title;
    private final String thumbnailUrl;
    private final String author;
    private final long creationTimeMs;
    private final int commentsCount;
    private final int score;
    private final boolean ageRestriction;
    private final String relativeUrl;
    private final Image defaultImage;
    private final Image nsfwImage;
    private final Image obfuscatedImage;


    private Link(String id, String title, String thumbnailUrl, String author, long creationTimeMs,
                 int commentsCount, int score, boolean ageRestriction, String relativeUrl,
                 Image defaultImage, Image nsfwImage, Image obfuscatedImage) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.author = author;
        this.creationTimeMs = creationTimeMs;
        this.commentsCount = commentsCount;
        this.score = score;
        this.ageRestriction = ageRestriction;
        this.relativeUrl = relativeUrl;
        this.defaultImage = defaultImage;
        this.nsfwImage = nsfwImage;
        this.obfuscatedImage = obfuscatedImage;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthor() {
        return author;
    }

    public long getCreationTime() {
        return creationTimeMs;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getScore() {
        return score;
    }

    public boolean hasAgeRestriction() {
        return ageRestriction;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public Image getDefaultImage() {
        return defaultImage;
    }

    public boolean hasDefaultImage() {
        return defaultImage != null;
    }

    public Image getNsfwImage() {
        return nsfwImage;
    }

    public boolean hasNsfwImage() {
        return nsfwImage != null;
    }

    public Image getObfuscatedImage() {
        return obfuscatedImage;
    }

    public boolean hasObfuscatedImage() {
        return obfuscatedImage != null;
    }


    public static class Builder {
        private String id;
        private String title;
        private String thumbnailUrl;
        private String author;
        private long creationTimeMs;
        private int commentsCount;
        private int score;
        private boolean ageRestriction;
        private String relativeUrl;
        private Image defaultImage;
        private Image nsfwImage;
        private Image obfuscatedImage;


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setCreationTime(long creationTimeMs) {
            this.creationTimeMs = creationTimeMs;
            return this;
        }

        public Builder setCommentsCount(int comments) {
            this.commentsCount = comments;
            return this;
        }

        public Builder setScore(int score) {
            this.score = score;
            return this;
        }

        public Builder setAgeRestriction(boolean hasAgeRestriction) {
            this.ageRestriction = hasAgeRestriction;
            return this;
        }

        public Builder setRelativeUrl(String url) {
            this.relativeUrl = url;
            return this;
        }

        public Builder setDefaultImage(Image image) {
            this.defaultImage = image;
            return this;
        }

        public Builder setNsfwImage(Image image) {
            this.nsfwImage = image;
            return this;
        }

        public Builder setObfuscatedImage(Image image) {
            this.obfuscatedImage = image;
            return this;
        }

        public Link build() {
            return new Link(id, title, thumbnailUrl, author, creationTimeMs, commentsCount, score,
                    ageRestriction, relativeUrl, defaultImage, nsfwImage, obfuscatedImage);
        }
    }
}
