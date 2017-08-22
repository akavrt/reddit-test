package com.akavrt.reddit.models.converters;

import com.akavrt.reddit.models.Image;
import com.akavrt.reddit.models.raw.RawImage;

public class ImageConverter extends BaseConverter<RawImage, Image> {

    @Override
    public Image convert(RawImage source) {
        return new Image(source.source, source.resolutions);
    }
}
