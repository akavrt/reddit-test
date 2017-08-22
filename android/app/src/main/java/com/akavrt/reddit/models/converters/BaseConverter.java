package com.akavrt.reddit.models.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseConverter<F, T> implements Converter<F, T> {

    @Override
    public List<T> convertAll(Iterable<F> from) {
        if (from == null) {
            return Collections.emptyList();
        }

        final List<T> result = new ArrayList<>();
        for (F item : from) {
            result.add(convert(item));
        }

        return result;
    }
}
