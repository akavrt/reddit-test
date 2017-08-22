package com.akavrt.reddit.models.converters;

import java.util.List;

public interface Converter<F, T> {
    T convert(F from);
    List<T> convertAll(Iterable<F> from);
}
