package com.akavrt.reddit.models.raw;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RawListingData {
    public String modhash;
    @SerializedName("children") public List<RawLink> items;
    public String after;
    public String before;

    public RawListingData() {
        // used by Gson
    }
}
