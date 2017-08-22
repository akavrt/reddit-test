package com.akavrt.reddit.models.converters;

import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.models.raw.RawLink;
import com.akavrt.reddit.models.raw.RawListing;

import java.util.ArrayList;
import java.util.List;

public class ListingConverter extends BaseConverter<RawListing, List<Link>> {
    private final LinkConverter linkConverter;


    public ListingConverter() {
        linkConverter = new LinkConverter();
    }

    @Override
    public List<Link> convert(RawListing source) {
        List<Link> result = new ArrayList<>();

        if (source != null && source.data != null && source.data.items != null ) {
            for (RawLink rawLink : source.data.items) {
                Link link = linkConverter.convert(rawLink);
                result.add(link);
            }
        }

        return result;
    }
}
