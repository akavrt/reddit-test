package com.akavrt.reddit.models.raw;

import com.google.gson.annotations.SerializedName;

public class RawLinkData {
    public String name;
    public String title;
    public String thumbnail;
    public String author;
    public int score;
    @SerializedName("created_utc") public long created;
    @SerializedName("num_comments") public int comments;
    @SerializedName("over_18") public boolean over18;
    public RawImagePreview preview;
    public String permalink;

    public RawLinkData() {
        // used by Gson
    }
}
