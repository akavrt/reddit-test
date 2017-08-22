package com.akavrt.reddit.models.raw;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RawImagePreview {
    @SerializedName("images") public List<RawImageHolder> imageHolders;
    public boolean enabled;


    public RawImagePreview() {
        // used by Gson
    }
}
